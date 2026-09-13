package br.ufpb.dcx.lima.albiere.OF_Web.controllers;

import br.ufpb.dcx.lima.albiere.OF_Web.dtos.*;
import br.ufpb.dcx.lima.albiere.OF_Web.services.OuvidoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/ouvidoria")
public class OuvidoriaAdminController {

    private final OuvidoriaService ouvidoriaService;

    public OuvidoriaAdminController(OuvidoriaService ouvidoriaService) {
        this.ouvidoriaService = ouvidoriaService;
    }

    @GetMapping
    public ResponseEntity<List<OuvidoriaResponseDTO>> getAll() {
        return ResponseEntity.ok(ouvidoriaService.getAllManifestations());
    }

    @PatchMapping("/{protocol}/status")
    public ResponseEntity<OuvidoriaResponseDTO> updateStatus(
            @PathVariable String protocol,
            @RequestBody Map<String, String> body) {

        String newStatus = body.get("status");
        return ResponseEntity.ok(ouvidoriaService.updateStatus(protocol, newStatus));
    }

    @PostMapping("/{protocol}/messages")
    public ResponseEntity<OuvidoriaMessageDTO> sendAdminMessage(
            @PathVariable String protocol,
            @RequestBody CreateMessageDTO dto) {

        OuvidoriaMessageDTO response = ouvidoriaService.sendAdminMessage(protocol, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}