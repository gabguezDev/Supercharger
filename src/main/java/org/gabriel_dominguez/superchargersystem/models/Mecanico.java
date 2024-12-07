package org.gabriel_dominguez.superchargersystem.models;

import jakarta.persistence.*;

@Entity
@Table(name = "mecanicos")
public class Mecanico {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nombre;
  private String especialidad;
  private String horarios;

  public Mecanico() {
  }

  public Mecanico(String nombre, String especialidad, String horarios) {
    this.nombre = nombre;
    this.especialidad = especialidad;
    this.horarios = horarios;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getEspecialidad() {
    return especialidad;
  }

  public void setEspecialidad(String especialidad) {
    this.especialidad = especialidad;
  }

  public String getHorarios() {
    return horarios;
  }

  public void setHorarios(String horarios) {
    this.horarios = horarios;
  }
}