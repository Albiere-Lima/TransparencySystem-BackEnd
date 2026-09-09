package br.ufpb.dcx.lima.albiere.OF_Web.services;

import br.ufpb.dcx.lima.albiere.OF_Web.models.Expense;
import br.ufpb.dcx.lima.albiere.OF_Web.repositories.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    public void deleteAllExpenses() {
        expenseRepository.deleteAll();
    }
}
