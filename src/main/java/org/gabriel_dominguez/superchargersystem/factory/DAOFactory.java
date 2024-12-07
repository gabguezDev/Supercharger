package org.gabriel_dominguez.superchargersystem.factory;

import org.gabriel_dominguez.superchargersystem.dao.*;

public class DAOFactory {
  private static DAOFactory instance;

  private DAOFactory() {}

  public static DAOFactory getInstance() {
    if (instance == null) {
      instance = new DAOFactory();
    }
    return instance;
  }

  public ClienteDAO createClienteDAO() {
    return new ClienteDAO();
  }

  public TurnoDAO createTurnoDAO() {
    return new TurnoDAO();
  }

  public VehiculoDAO createVehiculoDAO() {
    return new VehiculoDAO();
  }

  public MecanicoDAO createMecanicoDAO() {
    return new MecanicoDAO();
  }

  public FichaMecanicaDAO createFichaMecanicaDAO() {
    return new FichaMecanicaDAO();
  }
}
