package com.informes.informesbackend.Controllers;

import com.informes.informesbackend.Models.Entities.Alumno;
import com.informes.informesbackend.Models.Entities.Asignatura;
import com.informes.informesbackend.Models.Entities.Curso;
import com.informes.informesbackend.Services.AsignaturaService;
import com.informes.informesbackend.Services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/cursos")
@CrossOrigin(origins = "*")
public class cursoController {

    @Autowired
    private CursoService service;
    @Autowired
    private AsignaturaService asignaturaService;

    @GetMapping("/list")
    @CrossOrigin("*")
    public ResponseEntity<List<Curso>> listarCursos(){
    return ResponseEntity.ok(service.listar());
}

    @GetMapping("/list/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Curso> optionalCurso = service.porId(id);
        if (optionalCurso.isPresent()){
            return ResponseEntity.ok(optionalCurso.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/save")
    public ResponseEntity<?> crear(@Valid @RequestBody Curso curso, BindingResult result){


        if(result.hasErrors()){
            return validar(result);
        }

        Curso cursoDB = service.guardar(curso);
        crearAsignaturas(cursoDB);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoDB);
    }

    private void crearAsignaturas(Curso curso) {
       Set<String> asignaturas1año =new HashSet<>();
        asignaturas1año.add("Biología");
        asignaturas1año.add("Física");
        asignaturas1año.add("Química");
        asignaturas1año.add("Educación Física");
        asignaturas1año.add("Tecnología");
        asignaturas1año.add("Dibujo Técnico");
        asignaturas1año.add("Lengua");
        asignaturas1año.add("Matematica");
        asignaturas1año.add("Geografía");
        asignaturas1año.add("Lengua Extranjera");
        asignaturas1año.add("Taller");

        Set<Asignatura> asignaturasCreadas=new HashSet<>();

        asignaturas1año.forEach( asignatura ->{
            Asignatura asignaturaNueva=new Asignatura();
            asignaturaNueva.setNombre(asignatura);
            asignaturaNueva.setCurso(curso);
            asignaturaNueva.setAnioCurso(curso.getAnio());
            asignaturasCreadas.add(asignaturaNueva);
        });

        asignaturaService.GuardarAsignaturas(asignaturasCreadas);


    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> editarCurso(@RequestBody Curso curso, @PathVariable Long id){

        Optional<Curso> optionalCurso = service.porId(id);
        if(!optionalCurso.isPresent()){


            return ResponseEntity.unprocessableEntity().build();
        }

        curso.setIdCurso(optionalCurso.get().getIdCurso());
        service.guardar(curso);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/delete/{id}")
    @CrossOrigin("*")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Curso> optionalCurso = service.porId(id);
        if(optionalCurso.isPresent()){
            service.eliminar(optionalCurso.get().getIdCurso());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

         }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String,String> errores= new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "el campo "+err.getField()+" "+err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }

}
