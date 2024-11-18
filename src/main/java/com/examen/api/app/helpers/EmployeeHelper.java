package com.examen.api.app.helpers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.examen.api.app.models.entity.Employee;
import com.examen.api.app.models.entity.Gender;
import com.examen.api.app.models.entity.Job;
import com.examen.api.app.models.repository.IEmployeeRepository;
import com.examen.api.app.models.repository.IGenderRepository;
import com.examen.api.app.models.repository.IJobRepository;
import com.examen.api.dto.EmployeeGenderJobResponseDTO;
import com.examen.api.dto.EmployessResquestDTO;
import com.examen.api.dto.GenderResponseDTO;
import com.examen.api.dto.JobResponseDTO;
import com.examen.api.utils.Utils;

@Component
public class EmployeeHelper {

  @Autowired
  private IEmployeeRepository EmployeeRepository;
  
  @Autowired
  private IGenderRepository GenderRepository;

  @Autowired
  private IJobRepository JobRepository;

  public EmployeeGenderJobResponseDTO getEmployee(Optional<Job> job, Employee employee) {
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

  public EmployeeGenderJobResponseDTO processEmployee(Integer employeeId, EmployessResquestDTO employessDto) {
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
}
