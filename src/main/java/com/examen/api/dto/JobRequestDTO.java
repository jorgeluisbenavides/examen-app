package com.examen.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobRequestDTO {

  @NotNull(message = "El identificador de puesto es obligatorio")
  private Integer job_id;

}