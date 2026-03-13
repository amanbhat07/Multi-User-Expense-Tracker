package com.project.expense.service;

import com.project.expense.dto.APIResponse;
import com.project.expense.dto.ExpenseRequest;
import com.project.expense.dto.ExpenseResponse;
import com.project.expense.entity.Expense;
import com.project.expense.entity.User;
import com.project.expense.repository.ExpenseRepository;
import com.project.expense.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public List<ExpenseResponse> getAllExpense() {
        List<ExpenseResponse> expenseResponseList = expenseRepository.findAll()
                .stream()
                .map(e -> ExpenseResponse.builder()
                        .id(e.getId())
                        .expenseName(e.getExpenseName())
                        .amount(e.getAmount())
                        .date(e.getDate())
                        .description(e.getDescription())
                        .build())
                .collect(Collectors.toList());

        return expenseResponseList;
    }

    public Expense addExpense(ExpenseRequest expenseRequest, Authentication authentication) {

        String username = authentication.getName();

        User user = userRepository.findByUserName(username)
                .orElseThrow(()->new RuntimeException("User not found."));

        Expense expense = Expense.builder()
                .expenseName(expenseRequest.getExpenseName())
                .amount(expenseRequest.getAmount())
                .date(expenseRequest.getDate())
                .description(expenseRequest.getDescription())
                .user(user)
                .build();
        expenseRepository.save(expense);

        return expense;
    }


    public Expense updateExpense(Long expenseId, ExpenseRequest expenseRequest) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(()->new RuntimeException("Expense does not exist for this id"));
        expense.setId(expenseId);
        expense.setExpenseName(expenseRequest.getExpenseName());
        expense.setAmount(expenseRequest.getAmount());
        expense.setDate(expenseRequest.getDate());
        expense.setDescription(expenseRequest.getDescription());
        expenseRepository.save(expense);
        return expense;
    }

    public Expense findByExpenseId(Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(()->new RuntimeException("Expense not found"));
        return Expense.builder()
                .id(expense.getId())
                .expenseName(expense.getExpenseName())
                .amount(expense.getAmount())
                .date(expense.getDate())
                .description(expense.getDescription())
                .build();
    }

    public List<Expense> getUserExpenses(Authentication authentication) {
        String username = authentication.getName();

        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return expenseRepository.findByUser(user);
    }
}
