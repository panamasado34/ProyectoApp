package cl.duoc.educacional.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Entity
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Table(name = "docentes")
    public class Docente {
        @Column(name = "id_docente")
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true, nullable = false, length = 30)
        private String usuario;

        @Column(nullable = false, length = 100)
        private String password;

        @Column(nullable = false, length = 50)
        private String nombre;
    }
