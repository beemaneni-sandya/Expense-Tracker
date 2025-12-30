package com.example.expense_tracker.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.expense_tracker.model.Budget;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Optional<Budget> findByYearAndMonth(int year, int month);
}
