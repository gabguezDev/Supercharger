package org.gabriel_dominguez.superchargersystem.models;

import jakarta.persistence.*;

@Entity
@Table(name = "clientes")
public class Cliente {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String nombre;

  @Column(nullable = false)
  private String apellido;

  @Column(nullable = false, unique = true)
  private String documento;

  private String telefono;

  // Constructor vacío requerido por JPA
  public Cliente() {
  }

  // Constructor completo
  public Cliente(String nombre, String apellido, String documento, String telefono) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.documento = documento;
    this.telefono = telefono;
  }

  // Getters y Setters
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

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public String getDocumento() {
    return documento;
  }

  public void setDocumento(String documento) {
    this.documento = documento;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }
}