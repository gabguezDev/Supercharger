package org.gabriel_dominguez.superchargersystem.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.gabriel_dominguez.superchargersystem.MainApp;
import org.gabriel_dominguez.superchargersystem.dao.ClienteDAO;
import org.gabriel_dominguez.superchargersystem.dao.MecanicoDAO;
import org.gabriel_dominguez.superchargersystem.dao.VehiculoDAO;
import org.gabriel_dominguez.superchargersystem.factory.DAOFactory;
import org.gabriel_dominguez.superchargersystem.models.Cliente;
import org.gabriel_dominguez.superchargersystem.models.Mecanico;
import org.gabriel_dominguez.superchargersystem.models.Turno;
import org.gabriel_dominguez.superchargersystem.models.Vehiculo;
import org.gabriel_dominguez.superchargersystem.services.TurnoService;

import java.time.LocalDateTime;

public class NuevoTurnoController {
  private final TurnoService turnoService = new TurnoService();
  private final ClienteDAO clienteDAO;
  private final VehiculoDAO vehiculoDAO;
  private final MecanicoDAO mecanicoDAO;

  public NuevoTurnoController() {
    // Antes:
    // this.clienteDAO = new ClienteDAO();
    // this.vehiculoDAO = new VehiculoDAO();
    // this.mecanicoDAO = new MecanicoDAO();

    // Después:
    DAOFactory factory = DAOFactory.getInstance();
    this.clienteDAO = factory.createClienteDAO();
    this.vehiculoDAO = factory.createVehiculoDAO();
    this.mecanicoDAO = factory.createMecanicoDAO();
  }

  @FXML
  private DatePicker fechaHora;
  @FXML
  private TextField clienteNombre;
  @FXML
  private TextField clienteApellido;
  @FXML
  private TextField clienteDocumento;
  @FXML
  private TextField vehiculoMarca;
  @FXML
  private TextField vehiculoModelo;
  @FXML
  private TextField mecanicoNombre;
  @FXML
  private Button guardarButton;
  @FXML
  private Button cancelarButton;

  @FXML
  private void initialize() {
    // Inicializar cualquier lógica de inicialización aquí
  }

  @FXML
  private void guardarTurno() {
    try {
      if (fechaHora.getValue() == null) {
        mostrarError("Debe seleccionar una fecha");
        return;
      }

      // Convertir la fecha a LocalDateTime
      LocalDateTime fechaHoraSeleccionada = LocalDateTime.of(
          fechaHora.getValue(),
          LocalDateTime.now().toLocalTime()
      );

      // Validar campos obligatorios
      if (clienteNombre.getText().isEmpty() || clienteApellido.getText().isEmpty() ||
          clienteDocumento.getText().isEmpty() || vehiculoMarca.getText().isEmpty() ||
          vehiculoModelo.getText().isEmpty() || mecanicoNombre.getText().isEmpty()) {
        mostrarError("Todos los campos son obligatorios");
        return;
      }

      // Crear o buscar cliente
      Cliente cliente = clienteDAO.buscarPorDocumento(clienteDocumento.getText());
      if (cliente == null) {
        cliente = new Cliente();
        cliente.setNombre(clienteNombre.getText());
        cliente.setApellido(clienteApellido.getText());
        cliente.setDocumento(clienteDocumento.getText());
        clienteDAO.guardar(cliente);
      }

      // Crear o buscar vehículo
      Vehiculo vehiculo = vehiculoDAO.buscarPorMarcaYModeloYCliente(
          vehiculoMarca.getText(),
          vehiculoModelo.getText(),
          cliente
      );
      if (vehiculo == null) {
        vehiculo = new Vehiculo();
        vehiculo.setMarca(vehiculoMarca.getText());
        vehiculo.setModelo(vehiculoModelo.getText());
        vehiculo.setCliente(cliente);
        vehiculoDAO.guardar(vehiculo);
      }

      // Crear o buscar mecánico
      Mecanico mecanico = mecanicoDAO.buscarPorNombre(mecanicoNombre.getText());
      if (mecanico == null) {
        mecanico = new Mecanico();
        mecanico.setNombre(mecanicoNombre.getText());
        mecanicoDAO.guardar(mecanico);
      }

      // Crear y guardar el turno
      Turno nuevoTurno = new Turno();
      nuevoTurno.setFechaHora(fechaHoraSeleccionada);
      nuevoTurno.setCliente(cliente);
      nuevoTurno.setVehiculo(vehiculo);
      nuevoTurno.setMecanico(mecanico);

      if (turnoService.crearTurno(nuevoTurno)) {
        mostrarInfo("Turno creado correctamente");
        cancelar();
      } else {
        mostrarError("No se pudo crear el turno");
      }
    } catch (Exception e) {
      mostrarError("Error al crear el turno: " + e.getMessage());
      e.printStackTrace();
    }
  }
  @FXML
  private void cancelar() {
    MainApp.cargarEscena("GestionTurnos.fxml", "Gestión de Turnos");
  }

  private void mostrarError(String mensaje) {
    MainApp.mostrarAlerta("Error", null, mensaje, Alert.AlertType.ERROR);
  }

  private void mostrarInfo(String mensaje) {
    MainApp.mostrarAlerta("Información", null, mensaje, Alert.AlertType.INFORMATION);
  }
}