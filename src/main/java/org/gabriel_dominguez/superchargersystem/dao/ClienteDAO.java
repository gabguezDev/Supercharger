package org.gabriel_dominguez.superchargersystem.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.gabriel_dominguez.superchargersystem.utils.DatabaseManager;
import org.gabriel_dominguez.superchargersystem.models.Cliente;
import java.util.List;

public class ClienteDAO {

  public void guardar(Cliente cliente) {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      em.getTransaction().begin();
      em.persist(cliente);
      em.getTransaction().commit();
    } catch (Exception e) {
      if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
      }
      throw new RuntimeException("Error al guardar cliente", e);
    } finally {
      em.close();
    }
  }

  public Cliente buscarPorId(Long id) {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      return em.find(Cliente.class, id);
    } finally {
      em.close();
    }
  }

  public Cliente buscarPorDocumento(String documento) {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      return em.createQuery(
              "SELECT c FROM Cliente c WHERE c.documento = :documento",
              Cliente.class)
          .setParameter("documento", documento)
          .getSingleResult();
    } catch (NoResultException e) {
      return null;
    } catch (Exception e) {
      throw new RuntimeException("Error al buscar cliente por documento", e);
    } finally {
      em.close();
    }
  }

  public List<Cliente> buscarTodos() {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      return em.createQuery("SELECT c FROM Cliente c", Cliente.class)
          .getResultList();
    } finally {
      em.close();
    }
  }

  public void actualizar(Cliente cliente) {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      em.getTransaction().begin();
      em.merge(cliente);
      em.getTransaction().commit();
    } catch (Exception e) {
      if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
      }
      throw new RuntimeException("Error al actualizar cliente", e);
    } finally {
      em.close();
    }
  }

  public void eliminar(Cliente cliente) {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      em.getTransaction().begin();
      cliente = em.merge(cliente);
      em.remove(cliente);
      em.getTransaction().commit();
    } catch (Exception e) {
      if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
      }
      throw new RuntimeException("Error al eliminar cliente", e);
    } finally {
      em.close();
    }
  }

  public Cliente buscarPorNombreApellidoYDocumento(String nombre, String apellido, String documento) {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      return em.createQuery(
              "SELECT c FROM Cliente c WHERE c.nombre = :nombre " +
                  "AND c.apellido = :apellido AND c.documento = :documento",
              Cliente.class)
          .setParameter("nombre", nombre)
          .setParameter("apellido", apellido)
          .setParameter("documento", documento)
          .getSingleResult();
    } catch (NoResultException e) {
      return null;
    } catch (Exception e) {
      throw new RuntimeException("Error al buscar cliente por nombre, apellido y documento", e);
    } finally {
      em.close();
    }
  }
}