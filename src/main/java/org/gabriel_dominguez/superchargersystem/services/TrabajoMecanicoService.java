package org.gabriel_dominguez.superchargersystem.services;

import org.gabriel_dominguez.superchargersystem.dao.FichaMecanicaDAO;
import org.gabriel_dominguez.superchargersystem.models.FichaMecanica;
import java.util.List;

public class TrabajoMecanicoService {
  private final FichaMecanicaDAO fichaMecanicaDAO;

  public TrabajoMecanicoService() {
    this.fichaMecanicaDAO = new FichaMecanicaDAO();
  }

  public boolean registrarTrabajo(FichaMecanica ficha) {
    validarFicha(ficha);
    try {
      ficha.setEstado("EN_PROCESO");
      fichaMecanicaDAO.guardar(ficha);
      return true;
    } catch (Exception e) {
      throw new RuntimeException("Error al registrar el trabajo: " + e.getMessage(), e);
    }
  }

  public boolean actualizarTrabajo(FichaMecanica ficha) {
    validarFicha(ficha);
    try {
      fichaMecanicaDAO.actualizar(ficha);
      return true;
    } catch (Exception e) {
      throw new RuntimeException("Error al actualizar el trabajo: " + e.getMessage(), e);
    }
  }

  public List<FichaMecanica> obtenerTrabajosPendientes() {
    try {
      return fichaMecanicaDAO.buscarPorEstado("EN_PROCESO");
    } catch (Exception e) {
      throw new RuntimeException("Error al obtener trabajos pendientes", e);
    }
  }

  private void validarFicha(FichaMecanica ficha) {
    if (ficha == null) {
      throw new IllegalArgumentException("La ficha no puede ser null");
    }
    if (ficha.getTurno() == null) {
      throw new IllegalArgumentException("El turno es requerido");
    }
    if (ficha.getDescripcionTrabajo() == null || ficha.getDescripcionTrabajo().trim().isEmpty()) {
      throw new IllegalArgumentException("La descripción del trabajo es requerida");
    }
  }
}