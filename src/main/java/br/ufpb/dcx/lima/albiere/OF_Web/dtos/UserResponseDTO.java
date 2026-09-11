package br.ufpb.dcx.lima.albiere.OF_Web.dtos;

public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String role;
    private String department;
    private String initials;
    private String bio;
    private String avatarColor;
    private String picture; // ou pictureUrl

    public UserResponseDTO(Long id, String name, String email, String role, String department, String initials, String bio, String avatarColor, String picture) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.department = department;
        this.initials = initials;
        this.bio = bio;
        this.avatarColor = avatarColor;
        this.picture = picture;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public String getDepartment() { return department; }
    public String getInitials() { return initials; }
    public String getBio() { return bio; }
    public String getAvatarColor() { return avatarColor; }
    public String getPicture() { return picture; }
}