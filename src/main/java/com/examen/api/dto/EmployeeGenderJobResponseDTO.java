package com.examen.api.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmployeeGenderJobResponseDTO {
  private Integer id;
  private String name;
  private String last_name;
  private Date birthdate;
  private GenderResponseDTO gender;
  private JobResponseDTO job;
}
