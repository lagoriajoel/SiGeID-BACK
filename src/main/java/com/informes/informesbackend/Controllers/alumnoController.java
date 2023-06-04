package com.informes.informesbackend.Controllers;

import com.informes.informesbackend.Models.Entities.*;
import com.informes.informesbackend.Security.DTO.NuevoUsuario;
import com.informes.informesbackend.Security.Entity.Usuario;
import com.informes.informesbackend.Security.Service.UsuarioService;
import com.informes.informesbackend.Services.*;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController()
@RequestMapping("/alumnos")
@CrossOrigin("*")
public class alumnoController {
    @Autowired
    private AlumnoService service;
    @Autowired
    private CursoService cursoService;
    @Autowired
    private informeService informeService;

    @Autowired
    private final PDFgeneradorService pdfGeneradorService;
    @Autowired
    private final  jasperReportService  jasperReportService;

    @Autowired
    private guardarUsuarioService guardarUsuarioService;

    @Autowired
    private UsuarioService usuarioService;

    public alumnoController(PDFgeneradorService pdfGeneradorService, jasperReportService jasperReportService) {

        this.pdfGeneradorService = pdfGeneradorService;

        this.jasperReportService= jasperReportService;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<List<Alumno>> listar(){
      return ResponseEntity.ok(service.listar());
  }

    @GetMapping("/list/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Alumno> usuarioOptional= service.listarporId(id);
        if (usuarioOptional.isPresent()){
            return ResponseEntity.ok(usuarioOptional.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/listOfDni/{dni}")
    public ResponseEntity<?> listarPorDNI(@PathVariable String dni){
        Optional<Alumno> usuarioOptional= service.listarporDni(dni);
        if (usuarioOptional.isPresent()){
            return ResponseEntity.ok(usuarioOptional.get());
        }
        return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", "El DNI ingresado no pertenece a un Alumno"));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listOfCurso/{id}")
    public ResponseEntity<?> listaPorCurso(@PathVariable Long id){
      return  ResponseEntity.ok(service.listarPorCurso(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<?> crearAlumno(@Valid @RequestBody Alumno alumno, BindingResult result) {
        if (!alumno.getEmail().isEmpty() && service.porEmail(alumno.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", "El email ingresado ya existe"));
        }
        if (!alumno.getDni().isEmpty() && service.listarporDni(alumno.getDni()).isPresent()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", "El Alumno ingresado ya existe"));
        }
        if (result.hasErrors()) {
            return validar(result);
        }
        Optional<Curso> optionalCurso = cursoService.porId(alumno.getCurso().getIdCurso());

        if (!optionalCurso.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        alumno.setCurso(optionalCurso.get());

        Alumno alumnoGuardado = service.guardar(alumno);

        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(alumnoGuardado.getId()).toUri();

        // crear el usuario para el alumno


        NuevoUsuario nuevoUsuario= new NuevoUsuario();
        nuevoUsuario.setNombre(alumno.getNombreCompleto());
        nuevoUsuario.setNombreUsuario(alumno.getDni());
        nuevoUsuario.setPassword(alumno.getDni());


        guardarUsuarioService.crearUsuario(nuevoUsuario);


        return ResponseEntity.created(ubicacion).body(alumnoGuardado);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> editarAlumno(@Valid @RequestBody Alumno alumno, @PathVariable Long id) {


        Optional<Curso> optionalCurso = cursoService.porId(alumno.getCurso().getIdCurso());

        if (!optionalCurso.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        Optional<Alumno> alumnoOptional= service.listarporId(id);
        if (!alumnoOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        alumno.setCurso(optionalCurso.get());
        alumno.setId(alumnoOptional.get().getId());
        service.guardar(alumno);

        return ResponseEntity.noContent().build();


    }




    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")

    public ResponseEntity<Alumno> eliminarAlumno(@PathVariable Long id){
        Optional<Alumno> alumnoOptional = service.listarporId(id);

        if(!alumnoOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }

        Optional<Usuario> usuario= usuarioService.getByNombreUsuario( alumnoOptional.get().getDni());

        usuarioService.delete(usuario.get().getId());

        service.eliminar(alumnoOptional.get().getId());
        return ResponseEntity.ok().build();
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")

    @GetMapping("/{id}/informes")
    public ResponseEntity<Collection<InformeDesempeño>> listarInformesDeAlumno(@PathVariable Long id){
        Alumno alumno = service.listarporId(id).get();

        if(alumno != null) {
            return new ResponseEntity<>(alumno.getInformeDesempenios(),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/pdf/generate/{informeId}/{dniAlumno}")
    public void generatePDF(HttpServletResponse response, @PathVariable Long informeId, @PathVariable String dniAlumno) throws IOException {

       Alumno alumno1=service.listarporDni(dniAlumno).get();

       Optional<InformeDesempeño> informe= informeService.listarporId(informeId);
       Set<Contenido> contenidosAdeudados=informe.get().getContenidosAdeudados();


        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());


        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        this.pdfGeneradorService.export(response,  contenidosAdeudados, alumno1);
    }

    //exportar pdf con jasper report

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/pdf/{informeId}/{dniAlumno}")
    public ResponseEntity<Resource> exportInvoice(@PathVariable Long informeId, @PathVariable String dniAlumno){


        Optional<Alumno> alumno1=service.listarporDni(dniAlumno);

        Optional<InformeDesempeño> informe= informeService.listarporId(informeId);
        Set<Contenido> contenidosAdeudados=informe.get().getContenidosAdeudados();

        return this.jasperReportService.exportInvoice(alumno1, contenidosAdeudados);
    }

    @PostMapping("/uploadFile/{idCurso}")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Long idCurso) throws IOException {
       Optional<Curso>optionalCurso=cursoService.porId(idCurso);
        List<Alumno> alumnos = new ArrayList<>();
       InputStream inputStream= file.getInputStream();
        CsvParserSettings settings=new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        CsvParser parser= new CsvParser(settings);
        List<Record> parseAllRecords = parser.parseAllRecords(inputStream);
        parseAllRecords.forEach(record -> {
            Alumno alumno=new Alumno();
            alumno.setDni(record.getString("dni_alumno"));
            alumno.setNombres(record.getString("nombres"));
            alumno.setApellido(record.getString("apellidos"));
            alumno.setEmail(record.getString("email"));
            alumno.setCurso(optionalCurso.get());
            alumnos.add(alumno);

            NuevoUsuario nuevoUsuario= new NuevoUsuario();
            nuevoUsuario.setNombre(alumno.getNombreCompleto());
            nuevoUsuario.setNombreUsuario(alumno.getDni());
            nuevoUsuario.setPassword(alumno.getDni());


            guardarUsuarioService.crearUsuario(nuevoUsuario);

        });



         return ResponseEntity.ok().body( service.guardarLista(alumnos));
    }


    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String,String> errores= new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "el campo "+err.getField()+" "+err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);}

}