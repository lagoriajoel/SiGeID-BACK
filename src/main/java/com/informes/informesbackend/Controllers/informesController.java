package com.informes.informesbackend.Controllers;

import com.informes.informesbackend.Models.Entities.*;
import com.informes.informesbackend.Models.Entities.EntitiesDTO.InformesDTO;
import com.informes.informesbackend.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController()
@RequestMapping("/informes")
@CrossOrigin("*")
public class informesController {

    @Autowired
    private informeService service;
    @Autowired
    private AlumnoService alumnoService;
    @Autowired
    private ContenidoAdeudadoService contenidoAdeudadoService;
    @Autowired
    private CriterioInformeImpl criterioInformeService;
    @Autowired
    private EstrategiaInformeServiceImpl estrategiaInformeService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR')")
    @GetMapping("/list")
    @CrossOrigin("*")
    public ResponseEntity<List<InformeDesempenio>> listarInformes(){
        return (ResponseEntity<List<InformeDesempenio>>) ResponseEntity.ok(service.listar());
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<InformeDesempenio> informeDesempeñoOptional = service.listarporId(id);
        if (informeDesempeñoOptional.isPresent()){
            return ResponseEntity.ok(informeDesempeñoOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/listOfAsignatura/{idAsignatura}")
    public ResponseEntity<?> listarPorAsignatura(@PathVariable Long idAsignatura){
        List<InformeDesempenio> informeDesempenioOptional = service.listarPorAsignatura(idAsignatura);
        if (!informeDesempenioOptional.isEmpty()){
            return ResponseEntity.ok(informeDesempenioOptional);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/listOfNombreAsignatura/{nombre}/{anio}")
    public ResponseEntity<?> listarPorNombreAsignatura(@PathVariable String nombre, @PathVariable String anio){
       return ResponseEntity.ok(service.listarPorNombreAsignatura(nombre, anio));
    }
    @GetMapping("/listOfAnioCurso/{anio}")
    public ResponseEntity<?> listarPorAnioCurso( @PathVariable String anio){
        return ResponseEntity.ok(service.listarPorAnio(anio));
    }
    @GetMapping("/numInformesMateria/{materia}/{anio}")
    public ResponseEntity<?> listarPorAnioCurso( @PathVariable String materia, @PathVariable String anio){
        return ResponseEntity.ok(service.InformesPorAsignaturasAnio(materia,anio));
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR')")
    @PostMapping("/save")
    public ResponseEntity<?> crearInforme(@Valid @RequestBody InformesDTO informeDto, BindingResult result){

     if (service.encontrarAlumno(informeDto.getAlumno().getId(), informeDto.getAsignatura().getAsignatura_id()).isPresent()) {
         return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", "El Informe para este alumno ya existe"));
       }
     if (informeDto.getCriteriosEvaluacion().isEmpty() || informeDto.getEstrategiasEvaluacion().isEmpty()) {
         return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", "El informe debe contener los criterios y estrategias de evaluación"));
        }
        Calendar rightNow =  Calendar.getInstance();
        InformeDesempenio nuevoInforme= new InformeDesempenio();
        System.out.println(informeDto.getCriteriosEvaluacion().stream().iterator().toString());
        nuevoInforme.setAlumno(informeDto.getAlumno());
        nuevoInforme.setAsignatura(informeDto.getAsignatura());
        nuevoInforme.setProfesorNombre(informeDto.getProfesorNombre());
        nuevoInforme.setFecha((rightNow.getTime()));
        InformeDesempenio informeDB = service.guardar(nuevoInforme);
       /** se crea un conjunto de tipo contenidoInforme y se instancian cada contenidoInforme **/
        Set<Contenido> contenidosInformeDto=informeDto.getContenidosAdeudados();
        Set<ContenidoAdeudado> contenidoAdeudados=new HashSet<>();
        contenidosInformeDto.forEach(contenido -> {
            ContenidoAdeudado nuevoContenido=new ContenidoAdeudado();
            nuevoContenido.setNombre(contenido.getNombre());
            nuevoContenido.setDescripcion(contenido.getDescripcion());
            nuevoContenido.setAprobado(false);
            nuevoContenido.setInformeDesempenio(informeDB);
            contenidoAdeudados.add(nuevoContenido);
        });
        List<ContenidoAdeudado> ListaContenidos= contenidoAdeudadoService.guardarTodos(contenidoAdeudados); //se mapean todos los contenidos que vienen del front como contenidosAdeudados
       /** se crea un conjunto de tipo criterioInfomre y se instancian cada criterioInforme **/
        Set<criteriosEvaluacion> criterioInformeDto=informeDto.getCriteriosEvaluacion();
        Set<criterioInforme> criterioInformeSet=new HashSet<>();
        criterioInformeDto.forEach(criterio -> {
            criterioInforme nuevoCriterio=new criterioInforme();
            nuevoCriterio.setCriterio(criterio.getCriterio());
          nuevoCriterio.setInformeDesempenio(informeDB);
            criterioInformeSet.add(nuevoCriterio);
        });
        List<criterioInforme> ListaCriterios=criterioInformeService.guardarTodos(criterioInformeSet);

        Set<criterioInforme> listaCriteriosSet= new HashSet<>(ListaCriterios);
        /** se crea un conjunto de tipo estrategiaInfomre y se instancian cada estrategiaInforme **/
        Set<estrategiasEvaluacion> estrategiaInformeDto=informeDto.getEstrategiasEvaluacion();
        Set<estrategiaInforme> estrategiaInformeSet=new HashSet<>();
        estrategiaInformeDto.forEach(estrategia -> {
            estrategiaInforme nuevaEstrategia=new estrategiaInforme();
            nuevaEstrategia.setEstrategia(estrategia.getEstrategias());
            nuevaEstrategia.setInformeDesempenio(informeDB);
            estrategiaInformeSet.add(nuevaEstrategia);
        });
        List<estrategiaInforme> ListaEstrategias=estrategiaInformeService.guardarTodos(estrategiaInformeSet);

        Set<estrategiaInforme> ListaEstrategiasSet= new HashSet<>(ListaEstrategias);


        informeDB.setContenidosAdeudados(ListaContenidos);
        informeDB.setCriteriosEvaluacion((Set<criterioInforme>) listaCriteriosSet);
        informeDB.setEstrategiasEvaluacion(ListaEstrategiasSet);
        InformeDesempenio informeDesempenio= service.guardar(informeDB);
        if(result.hasErrors()){
            return validar(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(informeDesempenio));
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR')")
    @PutMapping("agregarContenidos/{idInforme}")
    public ResponseEntity<?> agregarContenidos(@RequestBody InformeDesempenio informe, @PathVariable Long idInforme){

        Optional<InformeDesempenio> informeDesempeñoOptional = service.listarporId(idInforme);
        if (!informeDesempeñoOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        List<ContenidoAdeudado> contenidosInforme=informe.getContenidosAdeudados();
        Set<ContenidoAdeudado> contenidoAdeudados=new HashSet<>();
        contenidosInforme.forEach(contenido -> {
            ContenidoAdeudado nuevoContenido=new ContenidoAdeudado();
            nuevoContenido.setNombre(contenido.getNombre());
            nuevoContenido.setDescripcion(contenido.getDescripcion());
            nuevoContenido.setAprobado(false);
            contenidoAdeudados.add(nuevoContenido);
        });
        List<ContenidoAdeudado> ListaContenidos= contenidoAdeudadoService.guardarTodos(contenidoAdeudados);


        informe.setId(informeDesempeñoOptional.get().getId());
        service.guardar(informe);

        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasRole('PROFESOR')")
    @PutMapping("update/{id}")
    public ResponseEntity<?> editarInforme(@RequestBody InformeDesempenio informe, @PathVariable Long id){

        Optional<InformeDesempenio> informeDesempeñoOptional = service.listarporId(id);
        if (!informeDesempeñoOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }


        informe.setId(informeDesempeñoOptional.get().getId());
        service.guardar(informe);

        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR')")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<InformeDesempenio> informeDesempeñoOptional = service.listarporId(id);


        if(!informeDesempeñoOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }

        service.eliminarInforme( informeDesempeñoOptional.get().getId());
        return ResponseEntity.noContent().build();



    }

    @PreAuthorize("hasRole('PROFESOR')")
    @PutMapping("/asignarContenidos/{idInforme}")
    public InformeDesempenio asignarContenidoAdeudado(
            @PathVariable Long idInforme,
           @RequestBody List<ContenidoAdeudado> contenidos
    ){
        return  service.asignarContenidoAdeudado(idInforme, contenidos);
    }

    @PreAuthorize("hasRole('PROFESOR')")
    @PutMapping("/actualizarContenidoDiciembre/")
    public ResponseEntity<?> actualizarContenidoDiciembre(@RequestBody List<ContenidoAdeudado> contenidos){

        contenidos.forEach(contenido->{

                Optional<ContenidoAdeudado> contenidoAdeudado = contenidoAdeudadoService.listarporId(contenido.getId());
            if (!contenidoAdeudado.get().isAprobado()) {
                contenidoAdeudado.get().setInstanciaEvaluacion_diciembre(contenido.getInstanciaEvaluacion_diciembre());
                contenidoAdeudado.get().setAprobado(contenido.isAprobado());

                contenidoAdeudadoService.guardar(contenidoAdeudado.get());
            }
        });

        return ResponseEntity.ok().build();


    }
    @PreAuthorize("hasRole('PROFESOR')")
    @PutMapping("/actualizarContenidoFebrero/")
    public ResponseEntity<?> actualizarContenidoFebrero(@RequestBody List<ContenidoAdeudado> contenidos){

        contenidos.forEach(contenido->{

                Optional<ContenidoAdeudado> contenidoAdeudado = contenidoAdeudadoService.listarporId(contenido.getId());
                if (!contenidoAdeudado.get().isAprobado()) {
                    contenidoAdeudado.get().setInstanciaEvaluacion_febrero(contenido.getInstanciaEvaluacion_febrero());
                    contenidoAdeudado.get().setAprobado(contenido.isAprobado());
                    contenidoAdeudadoService.guardar(contenidoAdeudado.get());
                }
                else{
                    contenidoAdeudado.get().setInstanciaEvaluacion_febrero("---");
                    contenidoAdeudadoService.guardar(contenidoAdeudado.get());
                }

        });

        return ResponseEntity.ok().build();





    }


    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String,String> errores= new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "el campo "+err.getField()+" "+err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }





}
