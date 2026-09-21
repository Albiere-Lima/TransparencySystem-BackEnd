package br.ufpb.dcx.lima.albiere.OF_Web.services;

import br.ufpb.dcx.lima.albiere.OF_Web.dtos.ReceiptResponseDTO;
import br.ufpb.dcx.lima.albiere.OF_Web.models.Expense;
import br.ufpb.dcx.lima.albiere.OF_Web.models.Receipt;
import br.ufpb.dcx.lima.albiere.OF_Web.repositories.ExpenseRepository;
import br.ufpb.dcx.lima.albiere.OF_Web.repositories.ReceiptRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final ExpenseRepository expenseRepository;

    @Value("${app.storage.directory:./uploads/receipts}")
    private String storageDir;

    @Value("${app.baseUrl:http://localhost:8080}")
    private String baseUrl;;

    @Transactional()
    public List<ReceiptResponseDTO> getAll() {
        List<ReceiptResponseDTO> nova = new ArrayList<>();
        receiptRepository.findAll().forEach(a -> {
            nova.add(new ReceiptResponseDTO(
                    a.getId(),
                    a.getOriginalFileName(),
                    a.getFileUrl(),
                    a.getContentType(),
                    a.getFileSize(),
                    a.getCreatedAt()
            ));
        });
        return nova;
    }

    @Transactional()
    public ReceiptResponseDTO findByExpenseId(Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found with ID: " + expenseId));

        if (expense.getReceipt() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Receipt not found for expense ID: " + expenseId);
        }

        return mapToDTO(expense.getReceipt());
    }

    @Transactional
    public void deleteReceipt(Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found with ID: " + expenseId));

        Receipt receipt = expense.getReceipt();
        if (receipt == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Receipt not found for expense ID: " + expenseId);
        }

        deletePhysicalFile(receipt.getStoredFileName());
        expense.setReceipt(null);
        receiptRepository.delete(receipt);
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Uploaded file cannot be empty");
        }
    }

    private String generateUniqueFileName(String originalFilename) {
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        return UUID.randomUUID() + extension;
    }

    private void storeFileOnDisk(MultipartFile file, String storedFileName) {
        try {
            Path uploadPath = Paths.get(storageDir).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);

            Path targetLocation = uploadPath.resolve(storedFileName).normalize();

            // Path Traversal check
            if (!targetLocation.startsWith(uploadPath)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid file destination path");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to store file on disk", e);
        }
    }

    private void deletePhysicalFile(String storedFileName) {
        try {
            Path filePath = Paths.get(storageDir).resolve(storedFileName).normalize();
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            // Silently log or handle if file was already removed
        }
    }

    private ReceiptResponseDTO mapToDTO(Receipt receipt) {
        return new ReceiptResponseDTO(
                receipt.getId(),
                receipt.getOriginalFileName(),
                receipt.getFileUrl(),
                receipt.getContentType(),
                receipt.getFileSize(),
                receipt.getCreatedAt()
        );
    }

    @Transactional
    public ReceiptResponseDTO saveReceipt(Long expenseId, MultipartFile file) {
        validateFile(file);

        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found with ID: " + expenseId));

        if (expense.getReceipt() != null) {
            deletePhysicalFile(expense.getReceipt().getStoredFileName());
        }

        String storedFileName = generateUniqueFileName(file.getOriginalFilename());
        storeFileOnDisk(file, storedFileName);

        String fileUrl = baseUrl + "/uploads/receipts/" + storedFileName;

        Receipt receipt = expense.getReceipt();
        if (receipt == null) {
            receipt = new Receipt();
            receipt.setExpense(expense);
        }

        receipt.setOriginalFileName(file.getOriginalFilename());
        receipt.setStoredFileName(storedFileName);
        receipt.setFileUrl(fileUrl);
        receipt.setContentType(file.getContentType());
        receipt.setFileSize(file.getSize());

        Receipt savedReceipt = receiptRepository.save(receipt);

        return mapToDTO(savedReceipt);
    }
}
