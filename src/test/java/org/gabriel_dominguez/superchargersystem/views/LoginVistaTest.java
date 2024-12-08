package org.gabriel_dominguez.superchargersystem.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.assertions.api.Assertions;

public class LoginVistaTest extends ApplicationTest {

  @Override
  public void start(Stage stage) throws Exception {
    // Cargar el FXML del Login directamente desde el test
    var loader = new FXMLLoader(getClass().getResource("/org/gabriel_dominguez/superchargersystem/fxml/Login.fxml"));
    var scene = new Scene(loader.load());

    // Establecer la escena en el stage
    stage.setScene(scene);
    stage.show();
  }

  @Test
  public void testVistaRenderizada() {
    // Verificar que los elementos clave estén presentes
    Assertions.assertThat(lookup("#usuarioField").queryAs(TextField.class)).isNotNull();
    Assertions.assertThat(lookup("#passwordField").queryAs(PasswordField.class)).isNotNull();
    Assertions.assertThat(lookup("#ingresarButton").queryAs(Button.class)).isNotNull();
    Assertions.assertThat(lookup("#mensajeError").queryAs(Label.class)).isNotNull();
  }
}