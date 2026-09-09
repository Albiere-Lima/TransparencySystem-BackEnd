package br.ufpb.dcx.lima.albiere.OF_Web.repositories;

import br.ufpb.dcx.lima.albiere.OF_Web.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}