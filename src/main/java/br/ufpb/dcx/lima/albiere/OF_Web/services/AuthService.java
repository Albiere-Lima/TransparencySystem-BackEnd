package br.ufpb.dcx.lima.albiere.OF_Web.services;

import br.ufpb.dcx.lima.albiere.OF_Web.models.User;
import br.ufpb.dcx.lima.albiere.OF_Web.repositories.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final GoogleIdTokenVerifier verifier;

    public AuthService(
            UserRepository userRepository,
            @Value("${google.client-id}") String clientId
    ) {
        this.userRepository = userRepository;
        this.verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(),
                GsonFactory.getDefaultInstance()
        )
                .setAudience(Collections.singletonList(clientId))
                .build();
    }

    public User processGoogleLogin(String googleToken) throws Exception {
        GoogleIdToken idToken = verifier.verify(googleToken);
        if (idToken == null) {
            throw new IllegalArgumentException("Token do Google inválido");
        }

        GoogleIdToken.Payload payload = idToken.getPayload();
        String email = payload.getEmail();
        String name = (String) payload.get("name");
        String pictureUrl = (String) payload.get("picture");

        return userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(name);
            newUser.setGoogleId(payload.getSubject());

            newUser.setPassword(UUID.randomUUID().toString());
            newUser.setRole("ROLE_STUDENT");

            newUser.setPicture(pictureUrl);

            return userRepository.save(newUser);
        });
    }
}
