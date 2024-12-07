package org.gabriel_dominguez.superchargersystem.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DatabaseManager {
  private static final EntityManagerFactory emf;
  private static final String PERSISTENCE_UNIT_NAME = "SuperChargerPU";

  static {
    try {
      emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    } catch (Exception e) {
      System.err.println("Error inicializando EntityManagerFactory: " + e);
      throw new ExceptionInInitializerError(e);
    }
  }

  public static EntityManager getEntityManager() {
    if (emf == null) {
      throw new IllegalStateException("EntityManagerFactory no está inicializado");
    }
    return emf.createEntityManager();
  }

  public static void closeEntityManager(EntityManager em) {
    if (em != null && em.isOpen()) {
      try {
        em.close();
      } catch (Exception e) {
        System.err.println("Error al cerrar EntityManager: " + e);
      }
    }
  }

  public static void shutdown() {
    try {
      if (emf != null && emf.isOpen()) {
        emf.close();
      }
    } catch (Exception e) {
      System.err.println("Error al cerrar EntityManagerFactory: " + e);
    }
  }

  // Método para verificar el estado de la conexión
  public static boolean isConnected() {
    try {
      EntityManager em = getEntityManager();
      boolean isOpen = em.isOpen();
      closeEntityManager(em);
      return isOpen;
    } catch (Exception e) {
      return false;
    }
  }
}