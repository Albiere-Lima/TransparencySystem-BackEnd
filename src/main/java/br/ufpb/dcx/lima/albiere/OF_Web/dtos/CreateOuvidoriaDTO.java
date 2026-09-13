package br.ufpb.dcx.lima.albiere.OF_Web.dtos;

import br.ufpb.dcx.lima.albiere.OF_Web.models.enums.ManifestationType;

public record CreateOuvidoriaDTO(
        ManifestationType type,
        String title,
        String description,
        boolean isAnonymous,
        String name,
        String email,
        String phone
) {}
