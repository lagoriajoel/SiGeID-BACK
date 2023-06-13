package com.informes.informesbackend.Models.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "contenido_adeudado")
public class ContenidoAdeudado {
    @Id
    @Column(name = "id_contenido_adeudado")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String nombre;
    private String descripcion;
    private boolean aprobado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Informe_id", nullable = false)
    private InformeDesempenio informeDesempenio;

}
