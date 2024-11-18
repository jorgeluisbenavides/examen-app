package com.examen.api.app.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examen.api.app.models.entity.Employee;
import com.examen.api.app.models.entity.Gender;
import com.examen.api.app.models.entity.Job;
import com.examen.api.app.models.repository.IEmployeeRepository;
import com.examen.api.app.models.repository.IGenderRepository;
import com.examen.api.app.models.repository.IJobRepository;
import com.examen.api.dto.EmployeeGenderJobResponseDTO;
import com.examen.api.dto.EmployeeRequestDTO;
import com.examen.api.dto.EmployeeResponseDTO;
import com.examen.api.dto.EmployeesGroupingResponseDTO;
import com.examen.api.dto.EmployessResponseDTO;
import com.examen.api.dto.EmployessResquestDTO;
import com.examen.api.dto.GenderResponseDTO;
import com.examen.api.dto.JobRequestDTO;
import com.examen.api.dto.JobResponseDTO;
import com.examen.api.exceptions.BadRequestException;
import com.examen.api.utils.Utils;

import lombok.val;

@Service
public class EmployeeService {
  @Autowired
  private IEmployeeRepository EmployeeRepository;

  @Autowired
  private IJobRepository JobRepository;

  @Autowired
  private IGenderRepository GenderRepository;

  public List<Employee> getAllEmployees() {
    return EmployeeRepository.findAll();
  }

  public EmployeeResponseDTO save(EmployeeRequestDTO employeeDto) {
    Optional<Job> existingJob = JobRepository.findById(employeeDto.getJob_id());
    if (!existingJob.isPresent())
      throw new BadRequestException("El puesto asignado no existe.");

    Optional<Gender> existeGender = GenderRepository.findById(employeeDto.getGender_id());
    if (!existeGender.isPresent())
      throw new BadRequestException("El genero asignado no existe.");

    Optional<Employee> existingEmployee = EmployeeRepository
        .findByNameAndLastName(
            employeeDto.getName(),
            employeeDto.getLast_name());

    if (existingEmployee.isPresent())
      throw new BadRequestException("El empleado ya existe.");

    if (!Utils.validateIsOfLegalAge(employeeDto.getBirthdate()))
      throw new BadRequestException("El empleado debe ser mayor de edad.");

    Employee employee = Employee.builder()
        .genderId(employeeDto.getGender_id())
        .joId(employeeDto.getJob_id())
        .name(employeeDto.getName())
        .lastName(employeeDto.getLast_name())
        .birthDate(employeeDto.getBirthdate())
        .build();

    Employee employeeResponse = EmployeeRepository.save(employee);
    EmployeeResponseDTO response = EmployeeResponseDTO.builder()
        .id(employeeResponse.getEmployeeId())
        .success(true)
        .build();

    return response;
  }

  private EmployeeGenderJobResponseDTO getEmployee(Optional<Job> job, Employee employee) {
    Optional<Gender> gender = GenderRepository.findById(employee.getGenderId());

    return EmployeeGenderJobResponseDTO.builder()
        .id(employee.getEmployeeId())
        .name(employee.getName())
        .last_name(employee.getLastName())
        .birthdate(employee.getBirthDate())
        .gender(GenderResponseDTO.builder()
            .id(gender.get().getGenderId())
            .name(gender.get().getName())
            .build())
        .job(JobResponseDTO.builder()
            .id(job.get().getJobId())
            .name(job.get().getName())
            .salary(job.get().getSalary())
            .build())
        .build();
  }

  public EmployeesGroupingResponseDTO getEmployees(JobRequestDTO jobDto) {
    List<EmployeeGenderJobResponseDTO> employeesResponseDto = new ArrayList<EmployeeGenderJobResponseDTO>();

    Optional<Job> job = JobRepository.findById(jobDto.getJob_id());
    if (!job.isPresent())
      throw new BadRequestException("El puesto no existe.");

    List<Employee> employeesEntity = EmployeeRepository.findByJoId(jobDto.getJob_id());
    Collections.sort(employeesEntity, Comparator.comparing(Employee::getLastName));

    employeesEntity.forEach(employee -> {
      employeesResponseDto.add(getEmployee(job, employee));
    });

    Map<String, List<EmployeeGenderJobResponseDTO>> groupedByLastName = employeesResponseDto
        .stream()
        .collect(Collectors.groupingBy(EmployeeGenderJobResponseDTO::getLast_name));

    EmployeesGroupingResponseDTO EmployeesFilters = EmployeesGroupingResponseDTO.builder()
        .employees(groupedByLastName)
        .success(employeesResponseDto.size() > 0 ? true : false)
        .build();

    return EmployeesFilters;
  }

  private EmployeeGenderJobResponseDTO processEmployee(Integer employeeId, EmployessResquestDTO employessDto) {
    try {
      Optional<Employee> employee = EmployeeRepository.findByCreatedAtBetweenAndEmployeeId(
          Utils.setTimestamp(employessDto.getStart_date(), "00:00:00"),
          Utils.setTimestamp(employessDto.getEnd_date(), "20:59:59"),
          employeeId);

      if (!employee.isPresent()) {
        return null;
      }

      Optional<Job> job = JobRepository.findById(employee.get().getJoId());
      return getEmployee(job, employee.get());
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public EmployessResponseDTO getEmployeesByIds(EmployessResquestDTO employessDto) {
    List<CompletableFuture<EmployeeGenderJobResponseDTO>> futures = employessDto.getEmployee_ids().stream()
        .map(employeeId -> CompletableFuture.supplyAsync(() -> processEmployee(employeeId, employessDto)))
        .collect(Collectors.toList());

    List<EmployeeGenderJobResponseDTO> employeesResponseDto = futures.stream()
        .map(CompletableFuture::join)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());

    EmployessResponseDTO employeesResponse = EmployessResponseDTO.builder()
        .employees(employeesResponseDto)
        .success(!employeesResponseDto.isEmpty())
        .build();

    return employeesResponse;
  }

}
