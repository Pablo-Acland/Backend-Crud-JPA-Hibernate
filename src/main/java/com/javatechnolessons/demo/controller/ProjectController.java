package com.javatechnolessons.demo.controller;

import com.javatechnolessons.demo.model.Project;
import com.javatechnolessons.demo.repository.ProjectJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/Pro")
public class ProjectController {

    @Autowired
    ProjectJpaRepository projectJpaRepository;

    @GetMapping("/Project/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable("id") long id) {
        Optional<Project> projectData = projectJpaRepository.findById(id);

        if (projectData.isPresent()) {
            return new ResponseEntity<>(projectData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/Projects")
    public ResponseEntity<List<Project>> getAllProjects() {
        try {
            List<Project> project = new ArrayList<Project>();
            projectJpaRepository.findAll().forEach(project::add);

            if (project.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(project, HttpStatus.OK);
        } catch (Exception err) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/Project")
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        try {
            Project project2 = projectJpaRepository.save(new Project(project.getName()));
            return new ResponseEntity<>(project2, HttpStatus.CREATED);
        } catch (Exception err) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }
    @PutMapping("/Project/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable("id") long id, @RequestBody Project project) {
        Optional<Project> tutorialData = projectJpaRepository.findById(id);

        if (tutorialData.isPresent()) {
            Project project2 = tutorialData.get();
            project2.setName(project.getName());
            return new ResponseEntity<>(projectJpaRepository.save(project2), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/Project/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable("id") long id) {
        try {
            projectJpaRepository.deleteById(id);
            return new ResponseEntity<>("Tutorials DELETE!! ", HttpStatus.NO_CONTENT);
        } catch (Exception err) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
