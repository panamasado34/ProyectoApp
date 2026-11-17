package cl.duoc.educacional.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "registros")
public class Registro {
    @Column(name = "id_registro")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long bitacoraId;

    @Column(nullable = false, length = 30)
    private String estudiante;

    @Column(nullable = false, length = 10)
    private String fechaClase;

    @Column(nullable = false, length = 2000)
    private String contenido;

    @Column(nullable = false, length = 2000)
    private String actividad;

    @Column(nullable = false, length = 2000)
    private String aprendizaje;

    @Column(length = 2000)
    private String dudas;

    @Column(length = 2000)
    private String fotografias;

    @Column(length = 2000)
    private String archivos;

    @Column(length = 2000)
    private String enlaces;

    @Column(nullable = false, length = 20)
    private String estado;

    @Column(length = 2000)
    private String comentario;

    @Column(nullable = false, length = 10)
    private String fechaCreacion;

    @Column(length = 10)
    private String fechaEnvio;

    @Column(length = 10)
    private String fechaRetroalimentacion;

    @Column(length = 30)
    private String docenteRevisor;
}
