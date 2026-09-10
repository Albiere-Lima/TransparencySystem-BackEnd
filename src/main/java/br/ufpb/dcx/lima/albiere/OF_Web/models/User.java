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

    @Column(name = "google_id", unique = true)
    private String googleId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true)
    private String password;

    @Column(nullable = false)
    private String role;

    private String initials;

    private String avatarColor;

    private String department;

    @Column(length = 500)
    private String bio;

    private String picture;

    public User() {}
}