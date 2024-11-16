package com.examen.api.app.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examen.api.app.models.entity.Gender;

@Repository
public interface IGenderRepository extends JpaRepository<Gender, Integer> {

}