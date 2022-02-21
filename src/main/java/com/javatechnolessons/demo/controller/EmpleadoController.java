package com.javatechnolessons.demo.controller;

import java.util.*;
import com.javatechnolessons.demo.model.Empleado;
import com.javatechnolessons.demo.model.Role;
import com.javatechnolessons.demo.repository.EmpleadoJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/Emp")
public class EmpleadoController {

    @Autowired
    EmpleadoJpaRepository empleadoJpaRepository;

    @GetMapping("/Empleado/{id}")
    public ResponseEntity<Empleado> getEmpleadoById(@PathVariable("id") long id) {
        Optional<Empleado> empleadoData = empleadoJpaRepository.findById(id);

        if (empleadoData.isPresent()) {
            return new ResponseEntity<>(empleadoData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/Empleados")
    public ResponseEntity<List<Empleado>> getAllEmpleados() {
        try {
            List<Empleado> empleado = new ArrayList<Empleado>();
            empleadoJpaRepository.findAll().forEach(empleado::add);

            if (empleado.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(empleado, HttpStatus.OK);
        } catch (Exception err) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/Empleado")
    public ResponseEntity<Empleado> createEmpleado(@RequestBody Empleado empleado) {
        try {
            Empleado empleado2 = empleadoJpaRepository.save(new Empleado(empleado.getFirstName(), empleado.getLastName(),
                    empleado.getEmployeeid(), empleado.getRole()));
            return new ResponseEntity<>(empleado2, HttpStatus.CREATED);
        } catch (Exception err) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }
    @PutMapping("/Empleado/{id}")
    public ResponseEntity<Empleado> updateEmpleado(@PathVariable("id") long id, @RequestBody Empleado empleado) {
        Optional<Empleado> tutorialData = empleadoJpaRepository.findById(id);

        if (tutorialData.isPresent()) {
            Empleado empleado2 = tutorialData.get();
            empleado2.setFirstName(empleado.getFirstName());
            empleado2.setLastName(empleado.getLastName());
            empleado2.setEmployeeid(empleado.getEmployeeid());
            return new ResponseEntity<>(empleadoJpaRepository.save(empleado2), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/Empleado/{id}")
    public ResponseEntity<String> deleteEmpleado(@PathVariable("id") long id) {
        try {
            empleadoJpaRepository.deleteById(id);
            return new ResponseEntity<>("Tutorials DELETE!! ", HttpStatus.NO_CONTENT);
        } catch (Exception err) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
