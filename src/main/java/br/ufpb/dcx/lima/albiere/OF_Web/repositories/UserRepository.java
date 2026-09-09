package br.ufpb.dcx.lima.albiere.OF_Web.repositories;

import br.ufpb.dcx.lima.albiere.OF_Web.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
