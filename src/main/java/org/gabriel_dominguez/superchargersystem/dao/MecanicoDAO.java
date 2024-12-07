package org.gabriel_dominguez.superchargersystem.dao;

import jakarta.persistence.EntityManager;
import org.gabriel_dominguez.superchargersystem.utils.DatabaseManager;
import org.gabriel_dominguez.superchargersystem.models.Mecanico;
import java.util.List;

public class MecanicoDAO {

  public void guardar(Mecanico mecanico) {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      em.getTransaction().begin();
      em.persist(mecanico);
      em.getTransaction().commit();
    } catch (Exception e) {
      if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
      }
      throw new RuntimeException("Error al guardar mecánico", e);
    } finally {
      em.close();
    }
  }

  public Mecanico buscarPorId(Long id) {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      return em.find(Mecanico.class, id);
    } finally {
      em.close();
    }
  }

  public Mecanico buscarPorNombre(String nombre) {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      return em.createQuery("SELECT m FROM Mecanico m WHERE m.nombre = :nombre", Mecanico.class)
          .setParameter("nombre", nombre)
          .getSingleResult();
    } catch (Exception e) {
      return null;
    } finally {
      em.close();
    }
  }

  public List<Mecanico> buscarTodos() {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      return em.createQuery("SELECT m FROM Mecanico m", Mecanico.class)
          .getResultList();
    } finally {
      em.close();
    }
  }

  public List<Mecanico> buscarPorEspecialidad(String especialidad) {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      return em.createQuery("SELECT m FROM Mecanico m WHERE m.especialidad = :especialidad", Mecanico.class)
          .setParameter("especialidad", especialidad)
          .getResultList();
    } finally {
      em.close();
    }
  }

  public void actualizar(Mecanico mecanico) {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      em.getTransaction().begin();
      em.merge(mecanico);
      em.getTransaction().commit();
    } catch (Exception e) {
      if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
      }
      throw new RuntimeException("Error al actualizar mecánico", e);
    } finally {
      em.close();
    }
  }

  public void eliminar(Mecanico mecanico) {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      em.getTransaction().begin();
      mecanico = em.merge(mecanico);
      em.remove(mecanico);
      em.getTransaction().commit();
    } catch (Exception e) {
      if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
      }
      throw new RuntimeException("Error al eliminar mecánico", e);
    } finally {
      em.close();
    }
  }
}