package com.examen.api.dto;

import java.sql.Date;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployessResquestDTO {
  @NotNull(message = "El identificadores de empleados son obligatorios")
  private List<Integer> employee_ids;

  @NotNull(message = "La fecha inicial es obligatorio")
  @Past(message = "La fecha inicial no válida")
  private Date start_date;

  @NotNull(message = "La fecha final es obligatorio")
  @Past(message = "La fecha final no válida")
  private Date end_date;
}
