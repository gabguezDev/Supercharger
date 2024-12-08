package org.gabriel_dominguez.superchargersystem.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.assertions.api.Assertions;
import org.testfx.util.WaitForAsyncUtils;

public class NuevoTurnoVistaTest extends ApplicationTest {

  @Override
  public void start(Stage stage) throws Exception {
    var loader = new FXMLLoader(getClass().getResource("/org/gabriel_dominguez/superchargersystem/fxml/NuevoTurno.fxml"));
    var scene = new Scene(loader.load());
    stage.setScene(scene);
    stage.show();
  }

  @Test
  public void testVistaRenderizada() {
    WaitForAsyncUtils.waitForFxEvents();

    Assertions.assertThat(lookup("#fechaHora").queryAs(DatePicker.class)).isNotNull();
    Assertions.assertThat(lookup("#clienteNombre").queryAs(TextField.class)).isNotNull();
    Assertions.assertThat(lookup("#clienteApellido").queryAs(TextField.class)).isNotNull();
    Assertions.assertThat(lookup("#clienteDocumento").queryAs(TextField.class)).isNotNull();
    Assertions.assertThat(lookup("#vehiculoMarca").queryAs(TextField.class)).isNotNull();
    Assertions.assertThat(lookup("#vehiculoModelo").queryAs(TextField.class)).isNotNull();
    Assertions.assertThat(lookup("#mecanicoNombre").queryAs(TextField.class)).isNotNull();
    Assertions.assertThat(lookup("#guardarButton").queryAs(Button.class)).isNotNull();
    Assertions.assertThat(lookup("#cancelarButton").queryAs(Button.class)).isNotNull();
  }
}