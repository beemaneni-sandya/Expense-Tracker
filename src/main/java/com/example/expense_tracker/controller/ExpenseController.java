package com.example.expense_tracker.controller;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.http.HttpHeaders;
import java.time.YearMonth;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.expense_tracker.dto.ExpenseDTO;
import com.example.expense_tracker.dto.SummaryDTO;
import com.example.expense_tracker.model.Budget;
import com.example.expense_tracker.service.BudgetService;
import com.example.expense_tracker.service.ExpenseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class ExpenseController {
    private final ExpenseService expenseService;
    private final BudgetService budgetService;

    public ExpenseController(ExpenseService expenseService, BudgetService budgetService) {
        this.expenseService = expenseService;
        this.budgetService = budgetService;
    }

    @Operation(summary = "Add a new expense", description = "Creates a new expense entry")
    @PostMapping("/expenses")
    public ExpenseDTO addExpense(@Valid @RequestBody ExpenseDTO dto) {
        return expenseService.create(dto);
    }

    @Operation(summary = "Update an expense", description = "Updates an existing expense by ID")
    @PutMapping("/expenses/{id}")
    public ExpenseDTO updateExpense(
            @Parameter(description = "ID of the expense to update") @PathVariable Long id,
            @Valid @RequestBody ExpenseDTO dto) {
        return expenseService.update(id, dto);
    }

    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable Long id) {
        expenseService.delete(id);
        return ResponseEntity.ok().body("Expense deleted");
    }

    @GetMapping("/expenses")
    public List<ExpenseDTO> listExpenses(@RequestParam(required = false) String category) {
        return expenseService.list(category);
    }

    @GetMapping("/expenses/summary")
    public SummaryDTO summary(@RequestParam(required = false) Integer month,
                              @RequestParam(required = false) Integer year) {
        if (month == null) {
            double total = expenseService.totalAllTime();
            return new SummaryDTO(total, "All time");
        } else {
            int y = (year == null) ? YearMonth.now().getYear() : year;
            double total = expenseService.totalForMonth(y, month);
            YearMonth ym = YearMonth.of(y, month);
            return new SummaryDTO(total, ym.toString());
        }
    }

    @PostMapping("/budgets")
    public Budget setBudget(@RequestParam int year, @RequestParam int month, @RequestParam double amount) {
        return budgetService.setBudget(year, month, amount);
    }

    @GetMapping("/budgets/{year}/{month}")
    public ResponseEntity<?> showBudget(@PathVariable int year, @PathVariable int month) {
        return budgetService.getBudget(year, month)
                .map(b -> ResponseEntity.ok(b))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/expenses/export")
    public ResponseEntity<String> exportCsv() {
        StringWriter sw = new StringWriter();
        expenseService.exportCsv(new PrintWriter(sw));
        String csv = sw.toString();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=expenses.csv");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.TEXT_PLAIN)
                .body(csv);
    }
}
