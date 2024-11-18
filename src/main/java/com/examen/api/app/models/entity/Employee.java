package com.examen.api.app.models.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import com.examen.api.utils.Utils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees")
public class Employee implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer employeeId;

  @Column(name = "gender_id", nullable = false)
  private Integer genderId;

  @Column(name = "job_id", nullable = false)
  private Integer joId;

  @Column(name = "name", length = 100, nullable = false)
  private String name;

  @Column(name = "last_name", length = 100, nullable = false)
  private String lastName;

  @Column(name = "birthdate", nullable = false)
  private Date birthDate;

  @Column(name = "created_at", nullable = false)
  private Timestamp createdAt;

  @Column(name = "updated_at", nullable = false)
  private Timestamp updatedAt;

  @PrePersist
    public void prePersist() {
      createdAt = Utils.getDateNow();
      updatedAt = createdAt;
    }

    @PreUpdate
    public void preUpdate() {
      updatedAt = Utils.getDateNow();
    }
}
