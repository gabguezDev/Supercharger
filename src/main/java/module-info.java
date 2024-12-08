module org.gabriel_dominguez.superchargersystem {
  requires javafx.controls;
  requires javafx.fxml;
  requires jakarta.persistence;
  requires org.hibernate.orm.core;
  requires org.controlsfx.controls;
  requires com.dlsc.formsfx;
  requires org.kordamp.bootstrapfx.core;
  requires java.sql;

  // Abrir el paquete de modelos a Hibernate
  opens org.gabriel_dominguez.superchargersystem.models to org.hibernate.orm.core, javafx.base;

  // Exportar paquetes necesarios
  exports org.gabriel_dominguez.superchargersystem;
  exports org.gabriel_dominguez.superchargersystem.controllers;
  exports org.gabriel_dominguez.superchargersystem.models;
}