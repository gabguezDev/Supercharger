package org.gabriel_dominguez.superchargersystem;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.gabriel_dominguez.superchargersystem.utils.DatabaseManager;

import java.io.IOException;
import java.net.URL;

public class MainApp extends Application {
  private static Stage primaryStage;

  @Override
  public void start(Stage stage) {
    try {
      primaryStage = stage;

      // Obtener la URL del recurso FXML
      URL fxmlUrl = getClass().getResource("/org/gabriel_dominguez/superchargersystem/fxml/Login.fxml");
      if (fxmlUrl == null) {
        throw new IOException("No se pudo encontrar el archivo FXML");
      }

      // Cargar la interfaz
      FXMLLoader loader = new FXMLLoader(fxmlUrl);
      Scene scene = new Scene(loader.load(), 600, 400);

      stage.setTitle("SuperCharger - Sistema de Gestión");
      stage.setScene(scene);
      stage.setResizable(false);
      stage.show();

    } catch (IOException e) {
      mostrarError("Error de Interfaz", "No se pudo cargar la interfaz de usuario", e.getMessage());
      Platform.exit();
    } catch (Exception e) {
      mostrarError("Error Fatal", "Error al iniciar la aplicación", e.getMessage());
      Platform.exit();
    }
  }

  public static void main(String[] args) {
    try {
      // Inicializar la base de datos antes de lanzar la aplicación
      DatabaseManager.getEntityManager().close();
      launch(args);
    } catch (Exception e) {
      System.err.println("Error al inicializar la base de datos: " + e.getMessage());
      Platform.exit();
    }
  }

  public static Stage getPrimaryStage() {
    return primaryStage;
  }

  @Override
  public void stop() {
    try {
      DatabaseManager.shutdown();
    } catch (Exception e) {
      System.err.println("Error al cerrar la conexión con la base de datos: " + e.getMessage());
    }
  }

  public static void mostrarAlerta(String titulo, String header, String contenido, Alert.AlertType type) {
    Alert alert = new Alert(type);
    alert.setTitle(titulo);
    alert.setHeaderText(header);
    alert.setContentText(contenido);
    alert.showAndWait();
  }

  private void mostrarError(String titulo, String header, String contenido) {
    mostrarAlerta(titulo, header, contenido, Alert.AlertType.ERROR);
  }

  public static void cargarEscena(String fxml, String titulo) {
    try {
      URL fxmlUrl = MainApp.class.getResource("/org/gabriel_dominguez/superchargersystem/fxml/" + fxml);
      if (fxmlUrl == null) {
        throw new IOException("No se pudo encontrar el archivo: " + fxml);
      }

      FXMLLoader loader = new FXMLLoader(fxmlUrl);
      Scene scene = new Scene(loader.load());
      primaryStage.setTitle("SuperCharger - " + titulo);
      primaryStage.setScene(scene);
    } catch (IOException e) {
      mostrarAlerta("Error", "Error al cargar la pantalla", "No se pudo cargar la pantalla: " + fxml, Alert.AlertType.ERROR);
    }
  }
}