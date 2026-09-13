package br.ufpb.dcx.lima.albiere.OF_Web.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "tb_ouvidoria_message")
public class OuvidoriaMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sender;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ouvidoria_id", nullable = false)
    private Ouvidoria ouvidoria;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

}