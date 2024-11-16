package com.examen.api.app.models.entity;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeWorkedHour {
  private Integer employeeWorkedHourId;

  private Integer employeeId;

  private Integer workerdHours;

  private Date workerdDate;

  private Boolean status;

  private Timestamp createdAt;

  private Timestamp updatedAt;
}
