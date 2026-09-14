package br.ufpb.dcx.lima.albiere.OF_Web.services;

import br.ufpb.dcx.lima.albiere.OF_Web.dtos.*;
import br.ufpb.dcx.lima.albiere.OF_Web.models.Ouvidoria;
import br.ufpb.dcx.lima.albiere.OF_Web.models.OuvidoriaMessage;
import br.ufpb.dcx.lima.albiere.OF_Web.models.enums.ManifestationType;
import br.ufpb.dcx.lima.albiere.OF_Web.repositories.OuvidoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OuvidoriaService {

    private final OuvidoriaRepository ouvidoriaRepository;

    public OuvidoriaService(OuvidoriaRepository ouvidoriaRepository) {
        this.ouvidoriaRepository = ouvidoriaRepository;
    }

    @Transactional
    public OuvidoriaResponseDTO createManifestation(CreateOuvidoriaDTO dto) {
        Ouvidoria ouvidoria = new Ouvidoria();
        ouvidoria.setType(dto.type());
        ouvidoria.setTitle(dto.title());
        ouvidoria.setDescription(dto.description());
        ouvidoria.setAnonymous(dto.isAnonymous());

        if (!dto.isAnonymous()) {
            ouvidoria.setName(dto.name());
            ouvidoria.setEmail(dto.email());
            ouvidoria.setPhone(dto.phone());
        }

        String protocol = "OUV-" + Year.now().getValue() + "-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        ouvidoria.setProtocol(protocol);

        if (dto.type() == ManifestationType.RECLAMACAO || dto.type() == ManifestationType.DENUNCIA) {

            OuvidoriaMessage sysMsg = new OuvidoriaMessage();
            sysMsg.setSender("SYSTEM");
            sysMsg.setContent("Manifestação registrada sob o protocolo " + protocol + ". Um atendente analisará seu relato.");
            sysMsg.setOuvidoria(ouvidoria);
            ouvidoria.getMessages().add(sysMsg);

            OuvidoriaMessage userMsg = new OuvidoriaMessage();
            userMsg.setSender("USER");
            userMsg.setContent("[" + dto.type() + "] " + dto.title() + "\n\n" + dto.description());
            userMsg.setOuvidoria(ouvidoria);
            ouvidoria.getMessages().add(userMsg);

            OuvidoriaMessage initialResponse = new OuvidoriaMessage();
            initialResponse.setSender("OUVIDORIA");
            initialResponse.setContent("Olá! Recebemos sua manifestação. Caso tenha novas informações ou comprovantes, você pode enviar por este chat.");
            initialResponse.setOuvidoria(ouvidoria);
            ouvidoria.getMessages().add(initialResponse);
        }

        Ouvidoria saved = ouvidoriaRepository.save(ouvidoria);
        return mapToDTO(saved);
    }

    @Transactional
    public OuvidoriaMessageDTO sendMessage(String protocol, CreateMessageDTO dto, String sender) {
        Ouvidoria ouvidoria = ouvidoriaRepository.findByProtocol(protocol)
                .orElseThrow(() -> new RuntimeException("Protocolo não encontrado: " + protocol));

        OuvidoriaMessage message = new OuvidoriaMessage();
        message.setSender(sender);
        message.setContent(dto.content());
        message.setOuvidoria(ouvidoria);

        ouvidoria.getMessages().add(message);
        ouvidoriaRepository.save(ouvidoria);

        return new OuvidoriaMessageDTO(message.getId(), message.getSender(), message.getContent(), message.getCreatedAt());
    }

    @Transactional(readOnly = true)
    public OuvidoriaResponseDTO getByProtocol(String protocol) {
        Ouvidoria ouvidoria = ouvidoriaRepository.findByProtocol(protocol)
                .orElseThrow(() -> new RuntimeException("Protocolo não encontrado: " + protocol));
        return mapToDTO(ouvidoria);
    }

    private OuvidoriaResponseDTO mapToDTO(Ouvidoria ouvidoria) {
        var messagesDto = ouvidoria.getMessages().stream()
                .map(m -> new OuvidoriaMessageDTO(m.getId(), m.getSender(), m.getContent(), m.getCreatedAt()))
                .collect(Collectors.toList());

        return new OuvidoriaResponseDTO(
                ouvidoria.getId(),
                ouvidoria.getProtocol(),
                ouvidoria.getType(),
                ouvidoria.getTitle(),
                ouvidoria.getDescription(),
                ouvidoria.isAnonymous(),
                ouvidoria.getName(),
                ouvidoria.getEmail(),
                ouvidoria.getPhone(),
                ouvidoria.getStatus(),
                ouvidoria.getCreatedAt(),
                messagesDto
        );
    }

    @Transactional(readOnly = true)
    public List<OuvidoriaResponseDTO> getAllManifestations() {
        return ouvidoriaRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public OuvidoriaResponseDTO updateStatus(String protocol, String newStatus) {
        Ouvidoria ouvidoria = ouvidoriaRepository.findByProtocol(protocol)
                .orElseThrow(() -> new RuntimeException("Protocolo não encontrado: " + protocol));

        ouvidoria.setStatus(newStatus.toUpperCase());

        // Notifica no chat que o status mudou
        OuvidoriaMessage statusMsg = new OuvidoriaMessage();
        statusMsg.setSender("SYSTEM");
        statusMsg.setContent("O status da manifestação foi alterado para: " + newStatus.toUpperCase());
        statusMsg.setOuvidoria(ouvidoria);
        ouvidoria.getMessages().add(statusMsg);

        Ouvidoria saved = ouvidoriaRepository.save(ouvidoria);
        return mapToDTO(saved);
    }

    @Transactional
    public OuvidoriaMessageDTO sendAdminMessage(String protocol, CreateMessageDTO dto) {
        return sendMessage(protocol, dto, "OUVIDORIA");
    }

    @Transactional
    public List<OuvidoriaResponseDTO> getMySupportChats(String email) {
        return convertOuvidoriaListToDTO(ouvidoriaRepository.findByEmailOrderByCreatedAtDesc(email));
    }


    private List<OuvidoriaResponseDTO> convertOuvidoriaListToDTO(List<Ouvidoria> a) {
        List<OuvidoriaResponseDTO> c = new ArrayList<>();
        a.forEach(o -> c.add(convertOuvidoriaToDTO(o)));
        return c;
    }

    private OuvidoriaResponseDTO convertOuvidoriaToDTO(Ouvidoria a) {
        return new OuvidoriaResponseDTO(
                a.getId(),
                a.getProtocol(),
                a.getType(),
                a.getTitle(),
                a.getDescription(),
                a.isAnonymous(),
                a.getName(),
                a.getEmail(),
                a.getPhone(),
                a.getStatus(),
                a.getCreatedAt(),
                convertOuvidoriaMessageListToDTO(a.getMessages())
        );
    }

    public OuvidoriaMessageDTO convertOuvidoriaMessageToDTO(OuvidoriaMessage a) {
        return new OuvidoriaMessageDTO(
           a.getId(),
           a.getSender(),
           a.getContent(),
           a.getCreatedAt()
        );
    }

    public List<OuvidoriaMessageDTO> convertOuvidoriaMessageListToDTO(List<OuvidoriaMessage> a) {
        List<OuvidoriaMessageDTO> c = new ArrayList<>();
        a.forEach(o -> c.add(convertOuvidoriaMessageToDTO(o)));
        return c;
    }

}