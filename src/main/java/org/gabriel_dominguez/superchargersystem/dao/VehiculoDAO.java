package org.gabriel_dominguez.superchargersystem.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.gabriel_dominguez.superchargersystem.utils.DatabaseManager;
import org.gabriel_dominguez.superchargersystem.models.Cliente;
import org.gabriel_dominguez.superchargersystem.models.Vehiculo;
import java.util.List;

public class VehiculoDAO {

  public void guardar(Vehiculo vehiculo) {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      em.getTransaction().begin();
      em.persist(vehiculo);
      em.getTransaction().commit();
    } catch (Exception e) {
      if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
      }
      throw new RuntimeException("Error al guardar vehículo", e);
    } finally {
      em.close();
    }
  }

  public Vehiculo buscarPorId(Long id) {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      return em.find(Vehiculo.class, id);
    } finally {
      em.close();
    }
  }

  public Vehiculo buscarPorMarcaYModeloYCliente(String marca, String modelo, Cliente cliente) {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      return em.createQuery("SELECT v FROM Vehiculo v WHERE v.marca = :marca AND v.modelo = :modelo AND v.cliente = :cliente", Vehiculo.class)
          .setParameter("marca", marca)
          .setParameter("modelo", modelo)
          .setParameter("cliente", cliente)
          .getSingleResult();
    } catch (NoResultException e) {
      return null;
    } finally {
      em.close();
    }
  }

  public List<Vehiculo> buscarTodos() {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      return em.createQuery("SELECT v FROM Vehiculo v", Vehiculo.class)
          .getResultList();
    } finally {
      em.close();
    }
  }

  public List<Vehiculo> buscarPorCliente(Long clienteId) {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      return em.createQuery("SELECT v FROM Vehiculo v WHERE v.cliente.id = :clienteId", Vehiculo.class)
          .setParameter("clienteId", clienteId)
          .getResultList();
    } finally {
      em.close();
    }
  }

  public void actualizar(Vehiculo vehiculo) {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      em.getTransaction().begin();
      em.merge(vehiculo);
      em.getTransaction().commit();
    } catch (Exception e) {
      if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
      }
      throw new RuntimeException("Error al actualizar vehículo", e);
    } finally {
      em.close();
    }
  }

  public void eliminar(Vehiculo vehiculo) {
    EntityManager em = DatabaseManager.getEntityManager();
    try {
      em.getTransaction().begin();
      vehiculo = em.merge(vehiculo);
      em.remove(vehiculo);
      em.getTransaction().commit();
    } catch (Exception e) {
      if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
      }
      throw new RuntimeException("Error al eliminar vehículo", e);
    } finally {
      em.close();
    }
  }
}