package cl.duoc.educacional.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Estudiante")
public class Estudiante {

        @Column(name = "id_estudiante")
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true, nullable = false, length = 30)
        private String usuario;

        @Column(nullable = false, length = 100)
        private String password;

        @Column(nullable = false, length = 50)
        private String nombre;

        @Column(nullable = false, length = 30)
        private String curso;

}
