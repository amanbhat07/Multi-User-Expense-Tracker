package com.project.expense.controller;

import com.project.expense.dto.APIResponse;
import com.project.expense.dto.ExpenseRequest;
import com.project.expense.dto.ExpenseResponse;
import com.project.expense.entity.Expense;
import com.project.expense.repository.ExpenseRepository;
import com.project.expense.service.ExpenseService;
import jakarta.servlet.http.HttpSession;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@Builder
@RequestMapping("/api/expense")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;
    private final ExpenseRepository expenseRepository;

    @GetMapping("/home")
    public String showHomePage(){
        return "home";
    }

    @GetMapping("/getAll")
    public String getAllExpense(Model model, Authentication authentication){
        List<ExpenseResponse> expenses = expenseService.getAllExpense();
        model.addAttribute("expenses", expenseService.getUserExpenses(authentication));
        return "expense";
    }

    @GetMapping("/add")
    public String showExpenseForm(Model model, HttpSession session){
        model.addAttribute("expenseRequest", new ExpenseRequest());
        session.removeAttribute("id");
        return "expense-form";
    }

    @PostMapping("/add")
    public String addExpense(@ModelAttribute("expenseRequest") ExpenseRequest expenseRequest, RedirectAttributes redirectAttributes, Authentication authentication){

        Expense savedExpense = expenseService.addExpense(expenseRequest, authentication);

        String successMsg = "Expense saved successfully";
        redirectAttributes.addFlashAttribute("successMsg", successMsg);
        return "redirect:/api/expense/home";
    }

    @GetMapping("/update/{expenseId}")
    public String showUpdateForm(@PathVariable Long expenseId, Model model, HttpSession session){
        Expense expense = expenseService.findByExpenseId(expenseId);
        model.addAttribute("expenseRequest", expense);
        session.setAttribute("expenseId", expense.getId());
        return "update-form";
    }

    @PostMapping("/update/{expenseId}")
    public String updateExpense(@PathVariable Long expenseId, @ModelAttribute("expenseRequest") ExpenseRequest expenseRequest, RedirectAttributes redirectAttributes){

        Expense updatedExpense = expenseService.updateExpense(expenseId, expenseRequest);
        String successMsg = "Expense update successfully";
        redirectAttributes.addFlashAttribute("successMsg", successMsg);
        return "redirect:/api/expense/home";
    }
}
