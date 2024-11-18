package com.examen.api.app.models.repository;

import com.examen.api.app.models.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IJobRepository extends JpaRepository<Job, Integer> {
  
}
