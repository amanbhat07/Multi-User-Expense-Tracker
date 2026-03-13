package com.project.expense.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ExpenseResponse {
    private Long id;
    private String expenseName;
    private Float amount;
    private LocalDate date;
    private String description;
}
