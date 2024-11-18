package com.examen.api.dto;

import java.sql.Date;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeRequestDTO {
  
  @NotNull(message = "El identificador de género es obligatorio")
  private Integer gender_id;

  @NotNull(message = "El identificador de puesto es obligatorio")
  private Integer job_id;

  @NotNull(message = "El nombre es obligatorio")
  private String name;

  @NotNull(message = "El apellido es obligatorio")
  private String last_name;

  @NotNull(message = "La fecha de nacimiento es obligatorio")
  @Past(message = "La fecha de nacimiento no válida")
  private Date birthdate;
}
