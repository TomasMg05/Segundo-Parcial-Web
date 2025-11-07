CREATE DATABASE creditos_ufps;

USE creditos_ufps;

CREATE TABLE IF NOT EXISTS persona (
    id INT AUTO_INCREMENT PRIMARY KEY,
    documento VARCHAR(10) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(50) NOT NULL,
    telefono VARCHAR(10),
    fecha_nacimiento DATE NOT NULL
);


CREATE TABLE IF NOT EXISTS estado (
    id INT AUTO_INCREMENT PRIMARY KEY,
    descripcion VARCHAR(20) NOT NULL UNIQUE
);


INSERT IGNORE INTO estado (id, descripcion) VALUES 
    (1, 'Solicitud'),
    (2, 'Aprobada'),
    (3, 'Rechazada'),
    (4, 'Finalizada');


CREATE TABLE IF NOT EXISTS solicitud (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    solicitante_id INT NOT NULL,
    codeudor_id INT NOT NULL,
    valor DECIMAL(10,0) NOT NULL,
    estado_id INT NOT NULL,
    observacion TEXT,
    codigo_radicado VARCHAR(10) NOT NULL UNIQUE,
    FOREIGN KEY (solicitante_id) REFERENCES persona(id),
    FOREIGN KEY (codeudor_id) REFERENCES persona(id),
    FOREIGN KEY (estado_id) REFERENCES estado(id)
);


CREATE TABLE IF NOT EXISTS validacion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(50) NOT NULL,
    documento VARCHAR(10) NOT NULL,
    fecha DATETIME NOT NULL,
    estado VARCHAR(20) NOT NULL,
    token VARCHAR(100) NOT NULL UNIQUE,
    codigo VARCHAR(10) NOT NULL UNIQUE
);
