package br.ufpb.dcx.lima.albiere.OF_Web.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {
    private String email;
    private String password;

}
