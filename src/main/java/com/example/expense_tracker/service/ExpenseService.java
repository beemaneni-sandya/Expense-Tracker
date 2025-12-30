package com.example.expense_tracker.service;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.expense_tracker.dto.ExpenseDTO;
import com.example.expense_tracker.exception.NotFoundException;
import com.example.expense_tracker.model.Expense;
import com.example.expense_tracker.repository.ExpenseRepository;

@Service
public class ExpenseService {
    private final ExpenseRepository repo;

    public ExpenseService(ExpenseRepository repo) {
        this.repo = repo;
    }

    public ExpenseDTO create(ExpenseDTO dto) {
        Expense e = new Expense();
        e.setDate(dto.getDate() == null ? LocalDate.now() : dto.getDate());
        e.setDescription(dto.getDescription());
        e.setAmount(dto.getAmount());
        e.setCategory(dto.getCategory());
        Expense saved = repo.save(e);
        return toDto(saved);
    }

    public ExpenseDTO update(Long id, ExpenseDTO dto) {
        Expense e = repo.findById(id).orElseThrow(() -> new NotFoundException("Expense not found: " + id));
        if (dto.getDescription() != null) e.setDescription(dto.getDescription());
        if (dto.getAmount() != null) e.setAmount(dto.getAmount());
        if (dto.getDate() != null) e.setDate(dto.getDate());
        if (dto.getCategory() != null) e.setCategory(dto.getCategory());
        return toDto(repo.save(e));
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) throw new NotFoundException("Expense not found: " + id);
        repo.deleteById(id);
    }

    public List<ExpenseDTO> list(String category) {
        List<Expense> list = (category == null || category.isBlank()) ? repo.findAll() : repo.findByCategoryIgnoreCase(category);
        return list.stream().map(this::toDto).collect(Collectors.toList());
    }

    public double totalAllTime() {
        Double sum = repo.sumAmountBetween(Year.of(0).atDay(1), LocalDate.now()); // fallback
        // simpler: sum all in memory if query returns null
        if (sum == null) return repo.findAll().stream().mapToDouble(Expense::getAmount).sum();
        return sum;
    }

    public double totalForMonth(int year, int month) {
        YearMonth ym = YearMonth.of(year, month);
        LocalDate from = ym.atDay(1);
        LocalDate to = ym.atEndOfMonth();
        Double s = repo.sumAmountBetween(from, to);
        return s == null ? 0.0 : s;
    }

    public void exportCsv(PrintWriter writer) {
        writer.println("id,date,description,amount,category");
        repo.findAll().forEach(e -> {
            // naive CSV escape: wrap description in quotes and double any quotes
            String desc = e.getDescription() == null ? "" : e.getDescription().replace("\"", "\"\"");
            String cat = e.getCategory() == null ? "" : e.getCategory().replace("\"", "\"\"");
            writer.printf("%d,%s,\"%s\",%.2f,\"%s\"%n", e.getId(), e.getDate(), desc, e.getAmount(), cat);
        });
    }

    private ExpenseDTO toDto(Expense e) {
        return new ExpenseDTO(e.getId(), e.getDate(), e.getDescription(), e.getAmount(), e.getCategory());
    }
}
