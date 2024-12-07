package org.gabriel_dominguez.superchargersystem.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainControllers {
  @FXML
  private Label welcomeText;

  @FXML
  protected void onHelloButtonClick() {
    welcomeText.setText("Welcome to JavaFX Application!");
  }
}