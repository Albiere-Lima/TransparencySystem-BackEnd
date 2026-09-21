package br.ufpb.dcx.lima.albiere.OF_Web.controllers;

import br.ufpb.dcx.lima.albiere.OF_Web.dtos.ReceiptResponseDTO;
import br.ufpb.dcx.lima.albiere.OF_Web.services.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/expenses/")
@RequiredArgsConstructor
public class ReceiptController {


    private final ReceiptService receiptService;

    @PostMapping(path = "{expenseId}/receipt", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReceiptResponseDTO> uploadReceipt(
            @PathVariable Long expenseId,
            @RequestParam("file") MultipartFile file) {

        ReceiptResponseDTO dto = receiptService.saveReceipt(expenseId, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping(path = "{expenseId}/receipt")
    public ResponseEntity<ReceiptResponseDTO> getReceipt(@PathVariable String expenseId) {
        if(!Objects.equals(expenseId, "all")) {
            ReceiptResponseDTO dto = receiptService.findByExpenseId(expenseId);
            return ResponseEntity.ok(dto);
        }
    }

    @GetMapping(path = "all/receipt")
    public ResponseEntity<List<ReceiptResponseDTO>> getAllReceipt() {
        List<ReceiptResponseDTO> dtos = receiptService.getAll();
        return ResponseEntity.ok(dtos);
    }

    @DeleteMapping(path = "{expenseId}/receipt")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteReceipt(@PathVariable String expenseId) {
        receiptService.deleteReceipt(expenseId);
        return ResponseEntity.noContent().build();
    }
}
