package br.ufpb.dcx.lima.albiere.OF_Web.repositories;

import br.ufpb.dcx.lima.albiere.OF_Web.models.Ouvidoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OuvidoriaRepository extends JpaRepository<Ouvidoria, Long> {
    Optional<Ouvidoria> findByProtocol(String protocol);
    List<Ouvidoria> findAllByOrderByCreatedAtDesc();
    List<Ouvidoria> findAllByUserId(String id);
}