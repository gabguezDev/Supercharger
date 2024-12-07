package org.gabriel_dominguez.superchargersystem.dao;

import jakarta.persistence.EntityManager;
import org.gabriel_dominguez.superchargersystem.utils.DatabaseManager;
import org.gabriel_dominguez.superchargersystem.models.Turno;
import java.time.LocalDateTime;
import java.util.List;

public class TurnoDAO {

  public void guardar(Turno turno) {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      em.getTransaction().begin();
      if (turno.getFechaHora() == null) {
        throw new IllegalArgumentException("La fecha y hora del turno no pueden ser nulas.");
      }
      em.persist(turno);
      em.getTransaction().commit();
    } catch (Exception e) {
      if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
      }
      throw new RuntimeException("Error al guardar turno: " + e.getMessage(), e);
    } finally {
      em.close();
    }
  }

  public Turno buscarPorId(Long id) {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      return em.find(Turno.class, id);
    } finally {
      em.close();
    }
  }

  public List<Turno> buscarTodos() {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      return em.createQuery("SELECT t FROM Turno t", Turno.class)
          .getResultList();
    } finally {
      em.close();
    }
  }

  public List<Turno> buscarPorMecanico(Long mecanicoId) {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      return em.createQuery("SELECT t FROM Turno t WHERE t.mecanico.id = :mecanicoId", Turno.class)
          .setParameter("mecanicoId", mecanicoId)
          .getResultList();
    } finally {
      em.close();
    }
  }

  public List<Turno> buscarPorCliente(Long clienteId) {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      return em.createQuery("SELECT t FROM Turno t WHERE t.cliente.id = :clienteId", Turno.class)
          .setParameter("clienteId", clienteId)
          .getResultList();
    } finally {
      em.close();
    }
  }

  public List<Turno> buscarPorFecha(LocalDateTime fecha) {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      return em.createQuery(
              "SELECT t FROM Turno t WHERE DATE(t.fechaHora) = DATE(:fecha)",
              Turno.class)
          .setParameter("fecha", fecha)
          .getResultList();
    } finally {
      em.close();
    }
  }

  public void actualizar(Turno turno) {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      em.getTransaction().begin();
      em.merge(turno);
      em.getTransaction().commit();
    } catch (Exception e) {
      if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
      }
      throw new RuntimeException("Error al actualizar turno", e);
    } finally {
      em.close();
    }
  }

  public void eliminar(Turno turno) {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      em.getTransaction().begin();
      turno = em.merge(turno);
      em.remove(turno);
      em.getTransaction().commit();
    } catch (Exception e) {
      if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
      }
      throw new RuntimeException("Error al eliminar turno", e);
    } finally {
      em.close();
    }
  }
}