package org.gabriel_dominguez.superchargersystem.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import org.gabriel_dominguez.superchargersystem.MainApp;

public class LoginController {
  @FXML
  private TextField usuarioField;

  @FXML
  private PasswordField passwordField;

  @FXML
  private Label mensajeError;

  @FXML
  private void handleLogin() {
    String usuario = usuarioField.getText();
    String password = passwordField.getText();

    // Por ahora, credenciales hardcodeadas
    if (usuario.equals("admin") && password.equals("admin")) {
      MainApp.cargarEscena("GestionTurnos.fxml", "Gestión de Turnos");
    } else {
      mensajeError.setText("Usuario o contraseña incorrectos");
    }
  }

  public String getUsuario() {
    return usuarioField.getText();
  }

  public void setUsuario(String usuario) {
    usuarioField.setText(usuario);
  }

  public String getPassword() {
    return passwordField.getText();
  }

  public void setPassword(String password) {
    passwordField.setText(password);
  }
}