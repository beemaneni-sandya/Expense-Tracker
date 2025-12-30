package com.example.expense_tracker.dto;


public class SummaryDTO {
    private double total;
    private String label;

    public SummaryDTO() {}
    public SummaryDTO(double total, String label) { this.total = total; this.label = label; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
}
