package org.gabriel_dominguez.superchargersystem.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.gabriel_dominguez.superchargersystem.utils.DatabaseManager;
import org.gabriel_dominguez.superchargersystem.models.FichaMecanica;

import java.time.LocalDateTime;
import java.util.List;

public class FichaMecanicaDAO {

  public void guardar(FichaMecanica fichaMecanica) {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      em.getTransaction().begin();
      em.persist(fichaMecanica);
      em.getTransaction().commit();
    } catch (Exception e) {
      if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
      }
      throw new RuntimeException("Error al guardar la ficha mecánica", e);
    } finally {
      em.close();
    }
  }

  public FichaMecanica buscarPorId(Long id) {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      return em.find(FichaMecanica.class, id);
    } finally {
      em.close();
    }
  }

  public List<FichaMecanica> buscarTodos() {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      return em.createQuery("SELECT f FROM FichaMecanica f", FichaMecanica.class)
          .getResultList();
    } finally {
      em.close();
    }
  }

  public FichaMecanica buscarPorTurno(Long turnoId) {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      return em.createQuery(
              "SELECT f FROM FichaMecanica f WHERE f.turno.id = :turnoId",
              FichaMecanica.class)
          .setParameter("turnoId", turnoId)
          .getSingleResult();
    } catch (NoResultException e) {
      return null;
    } catch (Exception e) {
      throw new RuntimeException("Error al buscar ficha mecánica por turno", e);
    } finally {
      em.close();
    }
  }

  public List<FichaMecanica> buscarPorEstado(String estado) {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      return em.createQuery(
              "SELECT f FROM FichaMecanica f WHERE f.estado = :estado",
              FichaMecanica.class)
          .setParameter("estado", estado)
          .getResultList();
    } finally {
      em.close();
    }
  }

  public List<FichaMecanica> buscarPorMecanico(Long mecanicoId) {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      return em.createQuery(
              "SELECT f FROM FichaMecanica f WHERE f.turno.mecanico.id = :mecanicoId",
              FichaMecanica.class)
          .setParameter("mecanicoId", mecanicoId)
          .getResultList();
    } finally {
      em.close();
    }
  }

  public void actualizar(FichaMecanica fichaMecanica) {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      em.getTransaction().begin();
      em.merge(fichaMecanica);
      em.getTransaction().commit();
    } catch (Exception e) {
      if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
      }
      throw new RuntimeException("Error al actualizar la ficha mecánica", e);
    } finally {
      em.close();
    }
  }

  public void eliminar(FichaMecanica fichaMecanica) {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      em.getTransaction().begin();
      fichaMecanica = em.merge(fichaMecanica);
      em.remove(fichaMecanica);
      em.getTransaction().commit();
    } catch (Exception e) {
      if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
      }
      throw new RuntimeException("Error al eliminar la ficha mecánica", e);
    } finally {
      em.close();
    }
  }

  public List<FichaMecanica> buscarPorFechas(LocalDateTime desde, LocalDateTime hasta) {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      return em.createQuery(
              "SELECT f FROM FichaMecanica f WHERE f.turno.fechaHora BETWEEN :desde AND :hasta",
              FichaMecanica.class)
          .setParameter("desde", desde)
          .setParameter("hasta", hasta)
          .getResultList();
    } finally {
      em.close();
    }
  }
}