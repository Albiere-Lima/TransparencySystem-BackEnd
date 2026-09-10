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

    public User processGoogleLogin(String idTokenString) throws Exception {
        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken == null) {
            throw new IllegalArgumentException("Invalid or expired Google Token");
        }

        GoogleIdToken.Payload payload = idToken.getPayload();

        String googleId = payload.getSubject(); // Unique Google ID ('sub')
        String email = payload.getEmail();
        String name = (String) payload.get("name");

        return userRepository.findByEmail(email)
                .map(existingUser -> {
                    if (existingUser.getGoogleId() == null) {
                        existingUser.setGoogleId(googleId);
                        return userRepository.save(existingUser);
                    }
                    return existingUser;
                })
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setGoogleId(googleId);
                    newUser.setEmail(email);
                    newUser.setName(name);
                    newUser.setRole("ROLE_STUDENT");
                    newUser.setDepartment("Undergraduate / Student");
                    newUser.setAvatarColor("#1E3A8A");

                    String[] nameParts = name.split(" ");
                    String initials = nameParts.length > 1
                            ? (nameParts[0].substring(0, 1) + nameParts[nameParts.length - 1].substring(0, 1)).toUpperCase()
                            : name.substring(0, Math.min(2, name.length())).toUpperCase();
                    newUser.setInitials(initials);

                    return userRepository.save(newUser);
                });
    }
}
