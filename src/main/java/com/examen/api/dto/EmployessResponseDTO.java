package com.examen.api.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmployessResponseDTO {
  private List<EmployeeGenderJobResponseDTO> employees;
  private Boolean success;
}
