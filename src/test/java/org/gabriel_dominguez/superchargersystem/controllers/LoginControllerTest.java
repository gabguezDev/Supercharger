package org.gabriel_dominguez.superchargersystem.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.gabriel_dominguez.superchargersystem.MainApp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
public class LoginControllerTest {
  private LoginController controller;

  @Start
  private void start(Stage stage) throws Exception {
    FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/org/gabriel_dominguez/superchargersystem/fxml/Login.fxml"));
    Parent loginView = loader.load();
    Scene scene = new Scene(loginView);
    stage.setScene(scene);
    stage.show();
    controller = loader.getController();
  }

  @Test
  void testLoginExitoso(FxRobot robot) {
    // Simular ingreso de credenciales correctas
    robot.clickOn("#usuarioField").write("admin");
    robot.clickOn("#passwordField").write("admin");
    robot.clickOn("#loginButton");

    // Verificar que se cargó la escena de Gestión de Turnos
    assertThat(robot.lookup("#nuevoTurnoButton").queryButton()).isNotNull();
    assertThat(robot.lookup("#turnosTable").queryTableView()).isNotNull();
  }

  @Test
  void testLoginFallido(FxRobot robot) {
    // Simular ingreso de credenciales incorrectas
    robot.clickOn("#usuarioField").write("usuario");
    robot.clickOn("#passwordField").write("contraseña");
    robot.clickOn("#loginButton");

    // Verificar que se muestra el mensaje de error
    Label mensajeError = robot.lookup("#mensajeError").queryAs(Label.class);
    assertThat(mensajeError.getText()).isEqualTo("Usuario o contraseña incorrectos");
  }

  @Test
  void testCamposVacios(FxRobot robot) {
    // Simular click en el botón sin ingresar credenciales
    robot.clickOn("#loginButton");

    // Verificar que se muestra el mensaje de error
    Label mensajeError = robot.lookup("#mensajeError").queryAs(Label.class);
    assertThat(mensajeError.getText()).isEqualTo("Usuario o contraseña incorrectos");
  }
}