package org.gabriel_dominguez.superchargersystem.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "turnos")
public class Turno {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "fecha_hora", nullable = false)
  private LocalDateTime fechaHora;

  @ManyToOne
  @JoinColumn(name = "cliente_id")
  private Cliente cliente;

  @ManyToOne
  @JoinColumn(name = "vehiculo_id")
  private Vehiculo vehiculo;

  @ManyToOne
  @JoinColumn(name = "mecanico_id")
  private Mecanico mecanico;

  public Turno() {
  }

  public Turno(LocalDateTime fechaHora, Cliente cliente, Vehiculo vehiculo, Mecanico mecanico) {
    this.fechaHora = fechaHora;
    this.cliente = cliente;
    this.vehiculo = vehiculo;
    this.mecanico = mecanico;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getFechaHora() {
    return fechaHora;
  }

  public void setFechaHora(LocalDateTime fechaHora) {
    this.fechaHora = fechaHora;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  public Vehiculo getVehiculo() {
    return vehiculo;
  }

  public void setVehiculo(Vehiculo vehiculo) {
    this.vehiculo = vehiculo;
  }

  public Mecanico getMecanico() {
    return mecanico;
  }

  public void setMecanico(Mecanico mecanico) {
    this.mecanico = mecanico;
  }
}