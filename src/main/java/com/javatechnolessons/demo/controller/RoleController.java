package com.javatechnolessons.demo.controller;

import com.javatechnolessons.demo.model.Empleado;
import com.javatechnolessons.demo.model.Role;
import com.javatechnolessons.demo.repository.EmpleadoJpaRepository;
import com.javatechnolessons.demo.repository.RoleJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/Rol")
public class RoleController {

    @Autowired
    RoleJpaRepository roleJpaRepository;

    @GetMapping("/Role/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable("id") long id) {
        Optional<Role> roleData = roleJpaRepository.findById(id);

        if (roleData.isPresent()) {
            return new ResponseEntity<>(roleData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/Roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        try {
            List<Role> role = new ArrayList<Role>();
            roleJpaRepository.findAll().forEach(role::add);

            if (role.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(role, HttpStatus.OK);
        } catch (Exception err) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/Role")
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        try {
            Role role2 = roleJpaRepository.save(new Role(role.getName()));
            return new ResponseEntity<>(role2, HttpStatus.CREATED);
        } catch (Exception err) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }
    @PutMapping("/Role/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable("id") long id, @RequestBody Role role) {
        Optional<Role> tutorialData = roleJpaRepository.findById(id);

        if (tutorialData.isPresent()) {
            Role role2 = tutorialData.get();
            role2.setName(role.getName());
            return new ResponseEntity<>(roleJpaRepository.save(role2), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/Role/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable("id") long id) {
        try {
            roleJpaRepository.deleteById(id);
            return new ResponseEntity<>("Tutorials DELETE!! ", HttpStatus.NO_CONTENT);
        } catch (Exception err) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
