package com.examen.api.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmployeeResponseDTO {
  private Integer id;
  private Boolean success; 
}
