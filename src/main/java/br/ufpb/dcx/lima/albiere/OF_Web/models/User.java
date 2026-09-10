package br.ufpb.dcx.lima.albiere.OF_Web.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Data
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "google_id", unique = true)
    private String googleId;

    @Column(nullable = false, unique = true)
    private String email;

    private String name;

    private String initials;

    private String avatarColor;

    @Column(nullable = false)
    private String role; // Standard: "ROLE_STUDENT", "ROLE_ADMIN", etc.

    private String department;

    @Column(length = 500)
    private String bio;

    public User() {}
}