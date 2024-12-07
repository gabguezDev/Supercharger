package org.gabriel_dominguez.superchargersystem.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import org.gabriel_dominguez.superchargersystem.MainApp;
import org.gabriel_dominguez.superchargersystem.models.Turno;
import org.gabriel_dominguez.superchargersystem.services.TurnoService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.Alert.AlertType;

public class GestionTurnosController {
  private final TurnoService turnoService = new TurnoService();

  @FXML
  private TableView<Turno> turnosTable;
  @FXML
  private TableColumn<Turno, String> fechaColumn;
  @FXML
  private TableColumn<Turno, String> clienteColumn;
  @FXML
  private TableColumn<Turno, String> vehiculoColumn;
  @FXML
  private TableColumn<Turno, String> mecanicoColumn;

  @FXML
  private void initialize() {
    configurarColumnas();
    cargarTurnos();
  }

  private void configurarColumnas() {
    // Configurar formato de fecha
    fechaColumn.setCellValueFactory(cellData -> {
      LocalDateTime fecha = cellData.getValue().getFechaHora();
      String fechaFormateada = fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
      return new SimpleStringProperty(fechaFormateada);
    });

    // Configurar columna cliente
    clienteColumn.setCellValueFactory(cellData ->
        new SimpleStringProperty(
            cellData.getValue().getCliente().getNombre() + " " +
                cellData.getValue().getCliente().getApellido()
        )
    );

    // Configurar columna vehículo
    vehiculoColumn.setCellValueFactory(cellData ->
        new SimpleStringProperty(
            cellData.getValue().getVehiculo().getMarca() + " " +
                cellData.getValue().getVehiculo().getModelo()
        )
    );

    // Configurar columna mecánico
    mecanicoColumn.setCellValueFactory(cellData ->
        new SimpleStringProperty(
            cellData.getValue().getMecanico().getNombre()
        )
    );
  }

  @FXML
  private void cargarTurnos() {
    try {
      var turnos = turnoService.obtenerTodosTurnos();
      turnosTable.setItems(FXCollections.observableArrayList(turnos));
    } catch (Exception e) {
      mostrarError("Error al cargar los turnos: " + e.getMessage());
    }
  }

  @FXML
  private void nuevoTurno() {
    MainApp.cargarEscena("NuevoTurno.fxml", "Nuevo Turno");
  }

  @FXML
  private void editarTurno() {
    Turno turnoSeleccionado = turnosTable.getSelectionModel().getSelectedItem();
    if (turnoSeleccionado == null) {
      mostrarError("Debe seleccionar un turno para editar");
      return;
    }
    // Aquí deberías implementar la lógica para editar el turno
    // Por ejemplo, abrir una nueva ventana con los datos del turno
  }

  @FXML
  private void eliminarTurno() {
    Turno turnoSeleccionado = turnosTable.getSelectionModel().getSelectedItem();
    if (turnoSeleccionado == null) {
      mostrarError("Debe seleccionar un turno para eliminar");
      return;
    }
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Confirmar eliminación");
    alert.setHeaderText("¿Está seguro que desea eliminar este turno?");
    alert.setContentText("Esta acción no se puede deshacer.");
    if (alert.showAndWait().get() == ButtonType.OK) {
      try {
        turnoService.cancelarTurno(turnoSeleccionado.getId());
        cargarTurnos();
        mostrarInfo("Turno eliminado correctamente");
      } catch (Exception e) {
        mostrarError("Error al eliminar el turno: " + e.getMessage());
      }
    }
  }

  private void mostrarError(String mensaje) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(mensaje);
    alert.showAndWait();
  }

  private void mostrarInfo(String mensaje) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Información");
    alert.setHeaderText(null);
    alert.setContentText(mensaje);
    alert.showAndWait();
  }
}