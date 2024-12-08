package org.gabriel_dominguez.superchargersystem.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.assertions.api.Assertions;
import org.testfx.util.WaitForAsyncUtils;

public class ConsultaEstadosVistaTest extends ApplicationTest {

  @Override
  public void start(Stage stage) throws Exception {
    var loader = new FXMLLoader(getClass().getResource("/org/gabriel_dominguez/superchargersystem/fxml/ConsultaEstados.fxml"));
    var scene = new Scene(loader.load());
    stage.setScene(scene);
    stage.show();
  }

  @Test
  public void testVistaRenderizada() {
    WaitForAsyncUtils.waitForFxEvents();

    Assertions.assertThat(lookup("#busquedaField").queryAs(TextField.class)).isNotNull();
    Assertions.assertThat(lookup("#buscarButton").queryAs(Button.class)).isNotNull();
    Assertions.assertThat(lookup("#estadosTable").queryAs(TableView.class)).isNotNull();
  }
}