package br.ufpb.dcx.lima.albiere.OF_Web.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponse {
    private String token;
    private UserResponseDTO user;

    public LoginResponse(String token, UserResponseDTO user) {
        this.token = token;
        this.user = user;
    }

}
