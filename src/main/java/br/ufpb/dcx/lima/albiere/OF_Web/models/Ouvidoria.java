package br.ufpb.dcx.lima.albiere.OF_Web.models;

import br.ufpb.dcx.lima.albiere.OF_Web.models.enums.ManifestationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tb_ouvidoria")
public class Ouvidoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String protocol;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ManifestationType type;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    private boolean isAnonymous;

    private String name;
    private String email;
    private String phone;

    @Column(nullable = false)
    private String status;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "ouvidoria", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OuvidoriaMessage> messages = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) this.status = "PENDENTE";
    }

}