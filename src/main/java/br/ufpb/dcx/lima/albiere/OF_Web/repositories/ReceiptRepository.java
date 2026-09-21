package br.ufpb.dcx.lima.albiere.OF_Web.repositories;

import br.ufpb.dcx.lima.albiere.OF_Web.models.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
}
