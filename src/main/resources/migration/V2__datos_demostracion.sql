-- ====================================================================
-- V2__datos_demostracion.sql
-- Datos iniciales y de prueba (extraídos de DatosPrueba.kt y DataInitializer.java)
-- ====================================================================

-- 1. Docentes de demostración (clave1234 en texto plano para compatibilidad con AuthService)
INSERT INTO docentes (id_docente, usuario, password, nombre) VALUES
(1, 'docente1', 'clave1234', 'Docente Prueba 1'),
(2, 'docente2', 'clave1234', 'Docente Prueba 2');

-- 2. Estudiantes de demostración
INSERT INTO Estudiante (id_estudiante, usuario, password, nombre, curso) VALUES
(1, 'estudiante1', 'clave1234', 'Angie', '7.º Básico A'),
(2, 'estudiante2', 'clave1234', 'Leon S Kennedy', '7.º Básico A'),
(3, 'estudiante3', 'clave1234', 'Estudiante Prueba 3', '7.º Básico A'),
(4, 'estudiante4', 'clave1234', 'Estudiante Prueba 4', '8.º Básico B');

-- 3. Asignaciones de demostración
INSERT INTO asignaciones (id_asignacion, docente, curso, asignatura) VALUES
(1, 'docente1', '7.º Básico A', 'Lenguaje'),
(2, 'docente1', '7.º Básico A', 'Matemática'),
(3, 'docente2', '8.º Básico B', 'Ciencias');

-- 4. Bitácora de demostración (publicada)
INSERT INTO bitacoras (id_bitacora, docente, curso, asignatura, mes, anio, titulo, proposito, instrucciones, preguntas, estado, fecha_publicacion, fecha_cierre) VALUES
(1, 'docente1', '7.º Básico A', 'Lenguaje', 'Noviembre', '2026', 
 'Bitácora de Lenguaje - Noviembre', 
 'Registrar lo aprendido en cada clase de Lenguaje.', 
 'Después de cada clase completa tu registro y envíalo.', 
 '¿Qué trabajamos hoy? ¿Qué aprendí? ¿Qué dudas me quedaron?', 
 'publicada', '02/11/2026', '');

-- 5. Recordatorio de demostración
INSERT INTO recordatorios (id_recordatorio, docente, tipo, fecha_programada, estado) VALUES
(1, 'docente1', 'Cierre mensual', '30/11/2026', 'pendiente');

INSERT INTO registros (
    bitacora_id,
    estudiante,
    fecha_clase,
    contenido,
    actividad,
    aprendizaje,
    dudas,
    estado,
    comentario,
    fecha_creacion
) VALUES (
             1,
             'estudiante1',
             '20/11/2026',
             'Durante la clase trabajamos la comprensión lectora.',
             'Leímos un texto narrativo e identificamos sus personajes.',
             'Aprendí a identificar las ideas principales de un texto.',
             'Me gustaría practicar más la identificación del conflicto.',
             'borrador',
             '',
             '20/11/2026'
         );