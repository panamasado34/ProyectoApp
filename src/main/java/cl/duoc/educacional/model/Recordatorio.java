package cl.duoc.educacional.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recordatorios")
public class Recordatorio {
    @Column(name = "id_recordatorio")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String docente;

    @Column(nullable = false, length = 50)
    private String tipo;

    @Column(nullable = false, length = 10)
    private String fechaProgramada;

    @Column(nullable = false, length = 20)
    private String estado;
}
