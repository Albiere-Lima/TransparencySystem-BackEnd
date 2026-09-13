package br.ufpb.dcx.lima.albiere.OF_Web.dtos;

import java.time.LocalDateTime;

public record OuvidoriaMessageDTO(
        Long id,
        String sender,
        String content,
        LocalDateTime createdAt
) {}
