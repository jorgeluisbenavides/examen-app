package com.examen.api.app.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class HollaController {
  @GetMapping("/")
  String getSaludo() {
      return "Hola mundo desde vs code.!";
  }
  
}
