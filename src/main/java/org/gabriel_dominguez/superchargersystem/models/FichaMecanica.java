package org.gabriel_dominguez.superchargersystem.models;

import jakarta.persistence.*;

@Entity
@Table(name = "fichas_mecanicas")
public class FichaMecanica {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "turno_id")
  private Turno turno;

  @Column(length = 1000)
  private String descripcionTrabajo;
  private String repuestosUsados;
  private String estado; // PENDIENTE, EN_PROCESO, COMPLETADO, etc.

  // Constructor vacío
  public FichaMecanica() {}

  // Getters y Setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public Turno getTurno() { return turno; }
  public void setTurno(Turno turno) { this.turno = turno; }
  public String getDescripcionTrabajo() { return descripcionTrabajo; }
  public void setDescripcionTrabajo(String descripcionTrabajo) { this.descripcionTrabajo = descripcionTrabajo; }
  public String getRepuestosUsados() { return repuestosUsados; }
  public void setRepuestosUsados(String repuestosUsados) { this.repuestosUsados = repuestosUsados; }
  public String getEstado() { return estado; }
  public void setEstado(String estado) { this.estado = estado; }
}
