package com.project.expense.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class APIResponse<T> {
    private T data;
    private String message;
    private String error;
}
