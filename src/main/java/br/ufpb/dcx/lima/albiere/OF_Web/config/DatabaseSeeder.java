package br.ufpb.dcx.lima.albiere.OF_Web.config;

import br.ufpb.dcx.lima.albiere.OF_Web.models.User;
import br.ufpb.dcx.lima.albiere.OF_Web.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DatabaseSeeder {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String adminEmail = "admin@ufpb.br";

            if (userRepository.findByEmail(adminEmail).isEmpty()) {
                User admin = new User();
                admin.setName("Administrador UFPB");
                admin.setEmail(adminEmail);
                admin.setRole("ROLE_ADMIN");
                admin.setPassword(passwordEncoder.encode("admin123"));

                userRepository.save(admin);
                System.out.println("Usuário admin criado com sucesso!");
            }
        };
    }
}