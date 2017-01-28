CREATE DATABASE domov1 WITH ENCODING='UTF8' CONNECTION LIMIT=-1;
COMMENT ON DATABASE domov1 IS 'Primera base de datos para domótica';

CREATE TABLE usuarios(
 id_usuario varchar(20) PRIMARY KEY,
 password varchar(60) NOT NULL,
 tipo_usuario varchar(50) NOT NULL DEFAULT 'admin',
 nombre varchar(50) NOT NULL,
 status varchar(50) NOT NULL
);
--ALTER TABLE usuarios ADD COLUMN tipo_usuario varchar(50) NOT NULL;

CREATE TABLE areas(
 id_area serial PRIMARY KEY,
 tipo_area varchar(50) NOT NULL,
 nombre varchar(50) NOT NULL,
 fecha_agregado timestamp NOT NULL DEFAULT NOW()
);

DROP TABLE dispositivos;
CREATE TABLE dispositivos(
 id_disp serial PRIMARY KEY,
 id_area int NOT NULL REFERENCES areas ON UPDATE CASCADE ON DELETE RESTRICT,
 tipo_disp varchar(50) NOT NULL,
 ip inet NOT NULL,
 puerto int NOT NULL,
 fecha_agregado timestamp NOT NULL DEFAULT NOW()
);