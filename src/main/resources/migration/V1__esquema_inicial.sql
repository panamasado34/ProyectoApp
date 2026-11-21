-- ====================================================================
-- V1__esquema_inicial.sql
-- Estructura de tablas basada estrictamente en las entidades JPA
-- ====================================================================

-- 1. Tabla docentes (Entidad: Docente)
CREATE TABLE docentes (
    id_docente BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nombre VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 2. Tabla Estudiante (Entidad: Estudiante - nombre con mayúscula según @Table)
CREATE TABLE Estudiante (
    id_estudiante BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    curso VARCHAR(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 3. Tabla asignaciones (Entidad: Asignacion)
CREATE TABLE asignaciones (
    id_asignacion BIGINT AUTO_INCREMENT PRIMARY KEY,
    docente VARCHAR(30) NOT NULL,
    curso VARCHAR(30) NOT NULL,
    asignatura VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 4. Tabla bitacoras (Entidad: Bitacora)
CREATE TABLE bitacoras (
    id_bitacora BIGINT AUTO_INCREMENT PRIMARY KEY,
    docente VARCHAR(30) NOT NULL,
    curso VARCHAR(30) NOT NULL,
    asignatura VARCHAR(50) NOT NULL,
    mes VARCHAR(20) NOT NULL,
    anio VARCHAR(4) NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    proposito VARCHAR(2000) NOT NULL,
    instrucciones VARCHAR(2000) NOT NULL,
    preguntas VARCHAR(2000) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    fecha_publicacion VARCHAR(10),
    fecha_cierre VARCHAR(10)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 5. Tabla registros (Entidad: Registro)
CREATE TABLE registros (
    id_registro BIGINT AUTO_INCREMENT PRIMARY KEY,
    bitacora_id BIGINT NOT NULL,
    estudiante VARCHAR(30) NOT NULL,
    fecha_clase VARCHAR(10) NOT NULL,
    contenido VARCHAR(2000) NOT NULL,
    actividad VARCHAR(2000) NOT NULL,
    aprendizaje VARCHAR(2000) NOT NULL,
    dudas VARCHAR(2000),
    fotografias VARCHAR(2000),
    archivos VARCHAR(2000),
    enlaces VARCHAR(2000),
    estado VARCHAR(20) NOT NULL,
    comentario VARCHAR(2000),
    fecha_creacion VARCHAR(10) NOT NULL,
    fecha_envio VARCHAR(10),
    fecha_retroalimentacion VARCHAR(10),
    docente_revisor VARCHAR(30)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 6. Tabla recordatorios (Entidad: Recordatorio)
CREATE TABLE recordatorios (
    id_recordatorio BIGINT AUTO_INCREMENT PRIMARY KEY,
    docente VARCHAR(30) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    fecha_programada VARCHAR(10) NOT NULL,
    estado VARCHAR(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
