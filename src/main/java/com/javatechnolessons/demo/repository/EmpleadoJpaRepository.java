package com.javatechnolessons.demo.repository;

import java.util.List;

import com.javatechnolessons.demo.model.Empleado;
import com.javatechnolessons.demo.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Employee JPA Interface
 * @author javatechnolessons
 * @version 1.0
 */
@Repository
public interface EmpleadoJpaRepository extends JpaRepository<Empleado, Long> {
    // select fields from employee where employeeid='[param]'
    Empleado findByEmployeeid(String employeeid);

    List<Empleado> findByFirstName(String firstName);

    List<Empleado> findByLastName(String lastName);

    List<Empleado> findByRole(Role role);

}
