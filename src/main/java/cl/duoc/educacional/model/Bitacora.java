package cl.duoc.educacional.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bitacoras")
public class Bitacora {
    @Column(name = "id_bitacora")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String docente;

    @Column(nullable = false, length = 30)
    private String curso;

    @Column(nullable = false, length = 50)
    private String asignatura;

    @Column(nullable = false, length = 20)
    private String mes;

    @Column(nullable = false, length = 4)
    private String anio;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(nullable = false, length = 2000)
    private String proposito;

    @Column(nullable = false, length = 2000)
    private String instrucciones;

    @Column(nullable = false, length = 2000)
    private String preguntas;

    @Column(nullable = false, length = 20)
    private String estado;

    @Column(length = 10)
    private String fechaPublicacion;

    @Column(length = 10)
    private String fechaCierre;
}
