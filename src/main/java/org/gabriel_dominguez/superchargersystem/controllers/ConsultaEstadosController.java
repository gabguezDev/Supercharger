package org.gabriel_dominguez.superchargersystem.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import org.gabriel_dominguez.superchargersystem.models.FichaMecanica;
import org.gabriel_dominguez.superchargersystem.services.TrabajoMecanicoService;

public class ConsultaEstadosController {
  private final TrabajoMecanicoService trabajoService = new TrabajoMecanicoService();

  @FXML
  private TextField busquedaField;

  @FXML
  private TableView<FichaMecanica> estadosTable;

  @FXML
  private TableColumn<FichaMecanica, String> fechaColumn;

  @FXML
  private TableColumn<FichaMecanica, String> clienteColumn;

  @FXML
  private TableColumn<FichaMecanica, String> vehiculoColumn;

  @FXML
  private TableColumn<FichaMecanica, String> estadoColumn;

  @FXML
  private void initialize() {
    configurarColumnas();
    configurarBusqueda();
    cargarTrabajos();
  }

  private void configurarColumnas() {
    fechaColumn.setCellValueFactory(cellData ->
        javafx.beans.binding.Bindings.createStringBinding(
            () -> cellData.getValue().getTurno().getFechaHora().toString()
        )
    );

    clienteColumn.setCellValueFactory(cellData ->
        javafx.beans.binding.Bindings.createStringBinding(
            () -> cellData.getValue().getTurno().getCliente().getNombre() + " " +
                cellData.getValue().getTurno().getCliente().getApellido()
        )
    );

    vehiculoColumn.setCellValueFactory(cellData ->
        javafx.beans.binding.Bindings.createStringBinding(
            () -> cellData.getValue().getTurno().getVehiculo().getMarca() + " " +
                cellData.getValue().getTurno().getVehiculo().getModelo()
        )
    );

    estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));
  }

  private void configurarBusqueda() {
    busquedaField.textProperty().addListener((observable, oldValue, newValue) -> {
      buscar();
    });
  }

  private void cargarTrabajos() {
    try {
      estadosTable.setItems(FXCollections.observableArrayList(
          trabajoService.obtenerTrabajosPendientes()
      ));
    } catch (Exception e) {
      mostrarError("Error al cargar los trabajos: " + e.getMessage());
    }
  }

  @FXML
  private void buscar() {
    String filtro = busquedaField.getText().toLowerCase();
    estadosTable.setItems(FXCollections.observableArrayList(
        trabajoService.obtenerTrabajosPendientes().stream()
            .filter(f -> f.getTurno().getCliente().getNombre().toLowerCase().contains(filtro) ||
                f.getTurno().getCliente().getApellido().toLowerCase().contains(filtro) ||
                f.getEstado().toLowerCase().contains(filtro))
            .toList()
    ));
  }

  private void mostrarError(String mensaje) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(mensaje);
    alert.showAndWait();
  }
}