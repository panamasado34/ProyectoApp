package cl.duoc.educacional.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "asignaciones")
public class Asignacion {
    @Column(name = "id_asignacion")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String docente;

    @Column(nullable = false, length = 30)
    private String curso;

    @Column(nullable = false, length = 50)
    private String asignatura;
}