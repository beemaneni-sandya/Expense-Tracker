package com.example.expense_tracker.repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.expense_tracker.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByCategoryIgnoreCase(String category);

    // find expenses between dates
    List<Expense> findByDateBetween(LocalDate start, LocalDate end);

    // sum amounts in date range
    @Query("select sum(e.amount) from Expense e where e.date between ?1 and ?2")
    Double sumAmountBetween(LocalDate start, LocalDate end);
}
