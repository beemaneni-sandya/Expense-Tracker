package com.example.expense_tracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "budgets",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"year", "month"})})
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int year;
    private int month;
    private double amount;

    public Budget() {}

    public Budget(int year, int month, double amount) {
        this.year = year; this.month = month; this.amount = amount;
    }

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public int getMonth() { return month; }
    public void setMonth(int month) { this.month = month; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}
