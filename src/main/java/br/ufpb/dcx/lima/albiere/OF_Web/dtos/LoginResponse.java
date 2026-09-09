package br.ufpb.dcx.lima.albiere.OF_Web.dtos;

import lombok.Getter;
import lombok.Setter;

public class LoginResponse {
    @Setter
    @Getter
    private String token;
    private UserResponseDTO user;

    public LoginResponse(String token, UserResponseDTO user) {
        this.token = token;
        this.user = user;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }
}
