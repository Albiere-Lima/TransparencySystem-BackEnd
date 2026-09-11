package br.ufpb.dcx.lima.albiere.OF_Web.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "tb_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", name = "google_id", unique = true)
    private String googleId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false, unique = true)
    private String email;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String password;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String role;

    @Column(columnDefinition = "TEXT")
    private String initials;

    @Column(columnDefinition = "TEXT")
    private String avatarColor;

    @Column(columnDefinition = "TEXT")
    private String department;

    @Column(columnDefinition = "TEXT", length = 500)
    private String bio;

    @Column(columnDefinition = "TEXT", length = 1000000000)
    private String picture;

    public User() {}
}