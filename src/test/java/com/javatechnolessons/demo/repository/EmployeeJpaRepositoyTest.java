package com.javatechnolessons.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.javatechnolessons.demo.model.Empleado;
import com.javatechnolessons.demo.model.Project;
import com.javatechnolessons.demo.model.Role;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)

// @AutoConfigureTestDatabase (connection = EmbeddedDatabaseConnection.H2)
public class EmployeeJpaRepositoyTest {
    @Autowired
    private EmpleadoJpaRepository repoEmpl;

    @Autowired
    private RoleJpaRepository repoRole;

    @Autowired
    private ProjectJpaRepository repoProj;

    @Test
    public void saveEmployee() {

        Role admin = new Role("ROLE_ADMIN");
        Role dev = new Role("ROLE_DEV");

        admin = repoRole.save(admin);
        dev = repoRole.save(dev);

        Project proj1 = new Project("proj1");
        Project proj2 = new Project("proj2");
        Project proj3 = new Project("proj3");

        proj1 = repoProj.save(proj1);
        proj2 = repoProj.save(proj2);
        proj3 = repoProj.save(proj3);

        Empleado john = new Empleado("John", "Smith", "empl123", dev);
        Empleado claire = new Empleado("Claire", "Simpson", "empl124", admin);

        john.getProjects().add(proj1);
        john.getProjects().add(proj2);

        claire.getProjects().add(proj1);
        claire.getProjects().add(proj2);
        claire.getProjects().add(proj3);

        repoEmpl.save(john);
        repoEmpl.save(claire);

        repoEmpl.flush();

        Empleado empl124 = repoEmpl.findByEmployeeid("empl124");
        assertEquals("Claire", empl124.getFirstName());
        assertEquals(2, repoEmpl.findAll().size());
        assertEquals(admin, empl124.getRole());

    }
}
