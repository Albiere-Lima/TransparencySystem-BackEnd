package br.ufpb.dcx.lima.albiere.OF_Web.dtos;

import java.time.LocalDateTime;

public record ReceiptResponseDTO(
        Long id,
        String originalFileName,
        String fileUrl,
        String contentType,
        Long fileSize,
        LocalDateTime createdAt
) {}
