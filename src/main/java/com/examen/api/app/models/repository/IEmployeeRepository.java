package com.examen.api.app.models.repository;

import com.examen.api.app.models.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Long> {

  Optional<Employee> findByNameAndLastName(String name, String lastName);

  List<Employee> findByJoId(Integer joId);
}
