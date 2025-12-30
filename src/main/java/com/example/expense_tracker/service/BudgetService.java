package com.example.expense_tracker.service;


import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.expense_tracker.model.Budget;
import com.example.expense_tracker.repository.BudgetRepository;

@Service
public class BudgetService {
    private final BudgetRepository repo;

    public BudgetService(BudgetRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public Budget setBudget(int year, int month, double amount) {
        Optional<Budget> existing = repo.findByYearAndMonth(year, month);
        Budget b = existing.orElse(new Budget(year, month, amount));
        b.setAmount(amount);
        b.setMonth(month);
        b.setYear(year);
        return repo.save(b);
    }

    public Optional<Budget> getBudget(int year, int month) {
        return repo.findByYearAndMonth(year, month);
    }
}
