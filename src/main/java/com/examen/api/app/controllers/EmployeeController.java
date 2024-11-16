package com.examen.api.app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examen.api.app.models.entity.Employee;
import com.examen.api.app.services.EmployeeService;
import com.examen.api.dto.EmployeeGenderJobResponseDTO;
import com.examen.api.dto.EmployeeRequestDTO;
import com.examen.api.dto.EmployeeResponseDTO;
import com.examen.api.dto.EmployeesFiltersResponseDTO;
import com.examen.api.dto.JobRequestDTO;
import com.examen.api.exceptions.BadRequestException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

  @Autowired
  private EmployeeService employeeService;

  @PostMapping()
  public ResponseEntity<?> create(@RequestBody @Validated EmployeeRequestDTO employeeDto) {
    try {
      return new ResponseEntity<EmployeeResponseDTO>(employeeService.save(employeeDto),
          HttpStatus.CREATED);
    } catch (BadRequestException e) {
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
    } catch (Exception e) {
      return new ResponseEntity<String>(e.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping(value = "/filters")
  public ResponseEntity<?> getEmployees(@RequestBody @Validated JobRequestDTO jobDto) {
    try {
      return new ResponseEntity<EmployeesFiltersResponseDTO>(employeeService.getEmployees(jobDto),
          HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<String>(e.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
