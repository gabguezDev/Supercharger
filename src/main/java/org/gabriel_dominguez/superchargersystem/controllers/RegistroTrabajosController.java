package org.gabriel_dominguez.superchargersystem.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import org.gabriel_dominguez.superchargersystem.models.Turno;
import org.gabriel_dominguez.superchargersystem.models.FichaMecanica;
import org.gabriel_dominguez.superchargersystem.services.TrabajoMecanicoService;
import org.gabriel_dominguez.superchargersystem.services.TurnoService;

public class RegistroTrabajosController {
  private final TrabajoMecanicoService trabajoService = new TrabajoMecanicoService();
  private final TurnoService turnoService = new TurnoService();

  @FXML
  private ComboBox<Turno> turnoComboBox;

  @FXML
  private TextArea descripcionArea;

  @FXML
  private TextField repuestosField;

  @FXML
  private ComboBox<String> estadoComboBox;

  @FXML
  private void initialize() {
    estadoComboBox.setItems(FXCollections.observableArrayList(
        "PENDIENTE", "EN_PROCESO", "COMPLETADO"
    ));

    configurarComboBoxTurno();
    cargarTurnos();
  }

  private void configurarComboBoxTurno() {
    turnoComboBox.setConverter(new javafx.util.StringConverter<Turno>() {
      @Override
      public String toString(Turno turno) {
        if (turno == null) return "";
        return String.format("%s - %s %s",
            turno.getCliente().getNombre(),
            turno.getVehiculo().getMarca(),
            turno.getVehiculo().getModelo());
      }

      @Override
      public Turno fromString(String string) {
        return null;
      }
    });
  }

  private void cargarTurnos() {
    try {
      turnoComboBox.setItems(FXCollections.observableArrayList(
          turnoService.obtenerTurnosPorMecanico(null)
      ));
    } catch (Exception e) {
      mostrarError("Error al cargar turnos: " + e.getMessage());
    }
  }

  @FXML
  private void guardarTrabajo() {
    if (!validarCampos()) {
      return;
    }

    try {
      FichaMecanica ficha = new FichaMecanica();
      ficha.setTurno(turnoComboBox.getValue());
      ficha.setDescripcionTrabajo(descripcionArea.getText());
      ficha.setRepuestosUsados(repuestosField.getText());
      ficha.setEstado(estadoComboBox.getValue());

      if (trabajoService.registrarTrabajo(ficha)) {
        mostrarInfo("Trabajo registrado exitosamente");
        limpiarFormulario();
      } else {
        mostrarError("No se pudo registrar el trabajo");
      }
    } catch (Exception e) {
      mostrarError("Error al guardar el trabajo: " + e.getMessage());
    }
  }

  private boolean validarCampos() {
    if (turnoComboBox.getValue() == null) {
      mostrarError("Debe seleccionar un turno");
      return false;
    }
    if (descripcionArea.getText().trim().isEmpty()) {
      mostrarError("Debe ingresar una descripción del trabajo");
      return false;
    }
    if (estadoComboBox.getValue() == null) {
      mostrarError("Debe seleccionar un estado");
      return false;
    }
    return true;
  }

  private void limpiarFormulario() {
    turnoComboBox.setValue(null);
    descripcionArea.clear();
    repuestosField.clear();
    estadoComboBox.setValue(null);
  }

  private void mostrarError(String mensaje) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(mensaje);
    alert.showAndWait();
  }

  private void mostrarInfo(String mensaje) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Información");
    alert.setHeaderText(null);
    alert.setContentText(mensaje);
    alert.showAndWait();
  }
}