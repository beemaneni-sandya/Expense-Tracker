package com.example.expense_tracker.dto;


import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class ExpenseDTO {
    private Long id;
    private LocalDate date;

    @NotBlank(message = "description is required")
    private String description;

    @NotNull(message = "amount is required")
    @PositiveOrZero(message = "amount must be >= 0")
    private Double amount;

    private String category;

    public ExpenseDTO() {}

    public ExpenseDTO(Long id, LocalDate date, String description, Double amount, String category) {
        this.id = id; this.date = date; this.description = description; this.amount = amount; this.category = category;
    }

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
