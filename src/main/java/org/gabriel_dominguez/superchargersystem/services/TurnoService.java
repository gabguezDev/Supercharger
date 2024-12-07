package org.gabriel_dominguez.superchargersystem.services;

import org.gabriel_dominguez.superchargersystem.dao.TurnoDAO;
import org.gabriel_dominguez.superchargersystem.models.Turno;
import org.gabriel_dominguez.superchargersystem.models.Mecanico;
import java.time.LocalDateTime;
import java.util.List;

public class TurnoService {
  private final TurnoDAO turnoDAO;

  public TurnoService() {
    this.turnoDAO = new TurnoDAO();
  }

  public boolean crearTurno(Turno turno) {
    validarTurno(turno);
    try {
      if (!verificarDisponibilidadMecanico(turno.getMecanico(), turno.getFechaHora())) {
        throw new IllegalStateException("El mecánico no está disponible en ese horario");
      }
      turnoDAO.guardar(turno);
      return true;
    } catch (Exception e) {
      throw new RuntimeException("Error al crear el turno: " + e.getMessage(), e);
    }
  }

  public List<Turno> obtenerTodosTurnos() {
    try {
      return turnoDAO.buscarTodos();
    } catch (Exception e) {
      throw new RuntimeException("Error al obtener los turnos", e);
    }
  }

  public boolean modificarTurno(Turno turno) {
    validarTurno(turno);
    try {
      if (!verificarDisponibilidadMecanico(turno.getMecanico(), turno.getFechaHora())) {
        throw new IllegalStateException("El mecánico no está disponible en ese horario");
      }
      turnoDAO.actualizar(turno);
      return true;
    } catch (Exception e) {
      throw new RuntimeException("Error al modificar el turno: " + e.getMessage(), e);
    }
  }

  public boolean cancelarTurno(Long turnoId) {
    try {
      Turno turno = turnoDAO.buscarPorId(turnoId);
      if (turno == null) {
        throw new IllegalArgumentException("Turno no encontrado");
      }
      if (turno.getFechaHora().isBefore(LocalDateTime.now())) {
        throw new IllegalStateException("No se pueden cancelar turnos pasados");
      }
      turnoDAO.eliminar(turno);
      return true;
    } catch (Exception e) {
      throw new RuntimeException("Error al cancelar el turno: " + e.getMessage(), e);
    }
  }

  public List<Turno> obtenerTurnosPorMecanico(Long mecanicoId) {
    try {
      return turnoDAO.buscarPorMecanico(mecanicoId);
    } catch (Exception e) {
      throw new RuntimeException("Error al obtener turnos del mecánico", e);
    }
  }

  private boolean verificarDisponibilidadMecanico(Mecanico mecanico, LocalDateTime fecha) {
    List<Turno> turnosDelDia = turnoDAO.buscarPorFecha(fecha);
    return turnosDelDia.stream()
        .filter(t -> t.getMecanico().getId().equals(mecanico.getId()))
        .count() < 8; // Máximo 8 turnos por día
  }

  private void validarTurno(Turno turno) {
    if (turno == null) {
      throw new IllegalArgumentException("El turno no puede ser null");
    }
    if (turno.getFechaHora() == null) {
      throw new IllegalArgumentException("La fecha y hora son requeridas");
    }
    if (turno.getFechaHora().isBefore(LocalDateTime.now())) {
      throw new IllegalArgumentException("No se pueden agendar turnos en el pasado");
    }
    if (turno.getMecanico() == null) {
      throw new IllegalArgumentException("El mecánico es requerido");
    }
    if (turno.getCliente() == null) {
      throw new IllegalArgumentException("El cliente es requerido");
    }
  }
}