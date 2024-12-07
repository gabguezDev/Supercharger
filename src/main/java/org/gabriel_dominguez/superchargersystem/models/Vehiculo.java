package org.gabriel_dominguez.superchargersystem.models;

import jakarta.persistence.*;

@Entity
@Table(name = "vehiculos")
public class Vehiculo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String marca;
  private String modelo;
  private String numeroPoliza;

  @ManyToOne
  @JoinColumn(name = "cliente_id")
  private Cliente cliente;

  public Vehiculo() {
  }

  public Vehiculo(String marca, String modelo, Cliente cliente) {
    this.marca = marca;
    this.modelo = modelo;
    this.cliente = cliente;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMarca() {
    return marca;
  }

  public void setMarca(String marca) {
    this.marca = marca;
  }

  public String getModelo() {
    return modelo;
  }

  public void setModelo(String modelo) {
    this.modelo = modelo;
  }

  public String getNumeroPoliza() {
    return numeroPoliza;
  }

  public void setNumeroPoliza(String numeroPoliza) {
    this.numeroPoliza = numeroPoliza;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }
}