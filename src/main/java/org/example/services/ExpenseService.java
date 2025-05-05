package org.example.services;

import org.example.entities.Expense;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ExpenseService {

    List<Expense> getAllUserExpenses(Long userId);

    List<Expense> getExpenseByDate(String date,Long userId );
    List<Expense> getExpenseByCategoryAndMonth(String category, String month, Long userId);
    List<String> getCategories(Long userId);
    Optional<Expense> getById(Long id, Long userId);
    Expense createExpense (Expense expense, Long userId);
    boolean updateExpense (Expense expense , Long userId);
    boolean deleteExpense (Long id, Long userId);

}
