package com.examen.api.dto;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmployeesFiltersResponseDTO {
  private Map<String, List<EmployeeGenderJobResponseDTO>> employees;
  private Boolean success;
}
