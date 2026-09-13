package br.ufpb.dcx.lima.albiere.OF_Web.dtos;

import br.ufpb.dcx.lima.albiere.OF_Web.models.enums.ManifestationType;
import java.time.LocalDateTime;
import java.util.List;

public record OuvidoriaResponseDTO(
        Long id,
        String protocol,
        ManifestationType type,
        String title,
        String description,
        boolean isAnonymous,
        String name,
        String email,
        String phone,
        String status,
        LocalDateTime createdAt,
        List<OuvidoriaMessageDTO> messages
) {}