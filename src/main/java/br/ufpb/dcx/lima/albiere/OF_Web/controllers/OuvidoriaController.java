package br.ufpb.dcx.lima.albiere.OF_Web.controllers;

import br.ufpb.dcx.lima.albiere.OF_Web.dtos.*;
import br.ufpb.dcx.lima.albiere.OF_Web.services.OuvidoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ouvidoria")
public class OuvidoriaController {

    private final OuvidoriaService ouvidoriaService;

    public OuvidoriaController(OuvidoriaService ouvidoriaService) {
        this.ouvidoriaService = ouvidoriaService;
    }

    @PostMapping
    public ResponseEntity<OuvidoriaResponseDTO> create(@RequestBody CreateOuvidoriaDTO dto) {
        OuvidoriaResponseDTO response = ouvidoriaService.createManifestation(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{protocol}")
    public ResponseEntity<OuvidoriaResponseDTO> getByProtocol(@PathVariable String protocol) {
        return ResponseEntity.ok(ouvidoriaService.getByProtocol(protocol));
    }

    @PostMapping("/{protocol}/messages")
    public ResponseEntity<OuvidoriaMessageDTO> sendMessage(
            @PathVariable String protocol,
            @RequestBody CreateMessageDTO dto) {

        OuvidoriaMessageDTO response = ouvidoriaService.sendMessage(protocol, dto, "USER");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/users/{email}")
    public ResponseEntity<List<OuvidoriaResponseDTO>> getMyManifestations(@PathVariable String email) {
        System.out.println(">>> BUSCANDO MANIFESTAÇÕES PARA O EMAIL: [" + email + "]");
        List<OuvidoriaResponseDTO> list = ouvidoriaService.getMySupportChats(email);
        System.out.println(">>> TOTAL ENCONTRADO: " + list.size());
        return ResponseEntity.ok(list);
    }
}