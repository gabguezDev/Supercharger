package org.gabriel_dominguez.superchargersystem.services;

import org.gabriel_dominguez.superchargersystem.dao.FichaMecanicaDAO;
import org.gabriel_dominguez.superchargersystem.factory.DAOFactory;
import org.gabriel_dominguez.superchargersystem.models.FichaMecanica;
import java.util.List;

public class ConformidadService {
  private final FichaMecanicaDAO fichaMecanicaDAO;

  public ConformidadService() {
    // Antes:
    // this.fichaMecanicaDAO = new FichaMecanicaDAO();

    // Después:
    this.fichaMecanicaDAO = DAOFactory.getInstance().createFichaMecanicaDAO();
  }

  public boolean registrarConformidad(Long fichaId, boolean conforme, String observaciones) {
    try {
      FichaMecanica ficha = fichaMecanicaDAO.buscarPorId(fichaId);
      if (ficha == null) {
        throw new IllegalArgumentException("Ficha mecánica no encontrada");
      }

      validarEstadoFicha(ficha);
      actualizarConformidad(ficha, conforme, observaciones);
      fichaMecanicaDAO.actualizar(ficha);
      return true;
    } catch (Exception e) {
      throw new RuntimeException("Error al registrar conformidad: " + e.getMessage(), e);
    }
  }

  private void validarEstadoFicha(FichaMecanica ficha) {
    if (!"COMPLETADO".equals(ficha.getEstado())) {
      throw new IllegalStateException("Solo se puede registrar conformidad en fichas completadas");
    }
  }

  private void actualizarConformidad(FichaMecanica ficha, boolean conforme, String observaciones) {
    ficha.setEstado(conforme ? "COMPLETADO_CONFORME" : "COMPLETADO_DISCONFORME");
    if (!conforme && observaciones != null && !observaciones.trim().isEmpty()) {
      String descripcionActual = ficha.getDescripcionTrabajo();
      ficha.setDescripcionTrabajo(descripcionActual + "\n\nObservaciones del cliente: " + observaciones);
    }
  }

  public List<FichaMecanica> obtenerFichasParaConformidad() {
    try {
      return fichaMecanicaDAO.buscarPorEstado("COMPLETADO");
    } catch (Exception e) {
      throw new RuntimeException("Error al obtener fichas para conformidad", e);
    }
  }
}