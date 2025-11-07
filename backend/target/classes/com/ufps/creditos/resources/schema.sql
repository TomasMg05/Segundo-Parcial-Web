
CREATE TABLE IF NOT EXISTS persona (
    id SERIAL PRIMARY KEY,
    documento VARCHAR(10) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(50) NOT NULL,
    telefono VARCHAR(10),
    fecha_nacimiento DATE NOT NULL
);


CREATE TABLE IF NOT EXISTS estado (
    id SERIAL PRIMARY KEY,
    descripcion VARCHAR(20) NOT NULL UNIQUE
);

INSERT INTO estado (id, descripcion) VALUES 
    (1, 'Solicitud'),
    (2, 'Aprobada'),
    (3, 'Rechazada'),
    (4, 'Finalizada')
ON CONFLICT DO NOTHING;


CREATE TABLE IF NOT EXISTS solicitud (
    id SERIAL PRIMARY KEY,
    fecha DATE NOT NULL,
    solicitante_id INTEGER NOT NULL,
    codeudor_id INTEGER NOT NULL,
    valor NUMERIC(10,0) NOT NULL,
    estado_id INTEGER NOT NULL,
    observacion TEXT,
    codigo_radicado VARCHAR(10) NOT NULL UNIQUE,
    FOREIGN KEY (solicitante_id) REFERENCES persona(id),
    FOREIGN KEY (codeudor_id) REFERENCES persona(id),
    FOREIGN KEY (estado_id) REFERENCES estado(id)
);


CREATE TABLE IF NOT EXISTS validacion (
    id SERIAL PRIMARY KEY,
    email VARCHAR(50) NOT NULL,
    documento VARCHAR(10) NOT NULL,
    fecha DATE NOT NULL,
    estado VARCHAR(20) NOT NULL,
    token VARCHAR(100) NOT NULL UNIQUE,
    codigo VARCHAR(10) NOT NULL UNIQUE
);
