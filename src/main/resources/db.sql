    -- Crear la base de datos
    CREATE DATABASE IF NOT EXISTS supercharger_db;
    USE supercharger_db;

    -- Crear tabla clientes
    CREATE TABLE clientes (
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        nombre VARCHAR(100) NOT NULL,
        apellido VARCHAR(100) NOT NULL,
        documento VARCHAR(20) NOT NULL,
        telefono VARCHAR(20)
    );

    -- Crear tabla vehiculos
    CREATE TABLE vehiculos (
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        marca VARCHAR(50) NOT NULL,
        modelo VARCHAR(50) NOT NULL,
        numero_poliza VARCHAR(50) NOT NULL,
        cliente_id BIGINT,
        FOREIGN KEY (cliente_id) REFERENCES clientes(id)
    );

    -- Crear tabla mecanicos
    CREATE TABLE mecanicos (
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        nombre VARCHAR(100) NOT NULL,
        especialidad VARCHAR(50) NOT NULL,
        horarios VARCHAR(200)
    );

    -- Crear tabla turnos
    CREATE TABLE turnos (
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        fecha_hora DATETIME NOT NULL,
        cliente_id BIGINT,
        vehiculo_id BIGINT,
        mecanico_id BIGINT,
        FOREIGN KEY (cliente_id) REFERENCES clientes(id),
        FOREIGN KEY (vehiculo_id) REFERENCES vehiculos(id),
        FOREIGN KEY (mecanico_id) REFERENCES mecanicos(id)
    );

    -- Crear tabla fichas_mecanicas
    CREATE TABLE fichas_mecanicas (
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        turno_id BIGINT UNIQUE,
        descripcion_trabajo TEXT,
        repuestos_usados TEXT,
        estado VARCHAR(20) NOT NULL,
        FOREIGN KEY (turno_id) REFERENCES turnos(id)
    );