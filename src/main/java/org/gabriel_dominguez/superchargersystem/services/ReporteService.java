package org.gabriel_dominguez.superchargersystem.services;

import org.gabriel_dominguez.superchargersystem.dao.FichaMecanicaDAO;
import org.gabriel_dominguez.superchargersystem.models.FichaMecanica;
import org.gabriel_dominguez.superchargersystem.models.Mecanico;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReporteService {
  private final FichaMecanicaDAO fichaMecanicaDAO;

  public ReporteService() {
    this.fichaMecanicaDAO = new FichaMecanicaDAO();
  }

  public Map<Mecanico, Integer> generarReporteTrabajosporMecanico(LocalDateTime desde, LocalDateTime hasta) {
    validarFechas(desde, hasta);
    try {
      return fichaMecanicaDAO.buscarTodos().stream()
          .filter(f -> fechaEnRango(f.getTurno().getFechaHora(), desde, hasta))
          .collect(Collectors.groupingBy(
              f -> f.getTurno().getMecanico(),
              Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
          ));
    } catch (Exception e) {
      throw new RuntimeException("Error al generar reporte por mecánico", e);
    }
  }

  public List<FichaMecanica> generarReporteTrabajosPendientes() {
    try {
      return fichaMecanicaDAO.buscarPorEstado("EN_PROCESO");
    } catch (Exception e) {
      throw new RuntimeException("Error al generar reporte de trabajos pendientes", e);
    }
  }

  public Map<String, Long> generarEstadisticasConformidad() {
    try {
      return fichaMecanicaDAO.buscarTodos().stream()
          .filter(f -> f.getEstado().startsWith("COMPLETADO"))
          .collect(Collectors.groupingBy(
              FichaMecanica::getEstado,
              Collectors.counting()
          ));
    } catch (Exception e) {
      throw new RuntimeException("Error al generar estadísticas de conformidad", e);
    }
  }

  private void validarFechas(LocalDateTime desde, LocalDateTime hasta) {
    if (desde == null || hasta == null) {
      throw new IllegalArgumentException("Las fechas no pueden ser null");
    }
    if (hasta.isBefore(desde)) {
      throw new IllegalArgumentException("La fecha final no puede ser anterior a la inicial");
    }
  }

  private boolean fechaEnRango(LocalDateTime fecha, LocalDateTime desde, LocalDateTime hasta) {
    return !fecha.isBefore(desde) && !fecha.isAfter(hasta);
  }
}