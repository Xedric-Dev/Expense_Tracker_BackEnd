package org.example.services.implementations;

import org.example.entities.AppUser;
import org.example.entities.Expense;
import org.example.repositories.ExpenseRepository;
import org.example.services.AppUserService;
import org.example.services.ExpenseService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImplDb implements ExpenseService {


    private final ExpenseRepository expenseRepository;
    private final AppUserService appUserService;

    public ExpenseServiceImplDb(ExpenseRepository expenseRepository, AppUserService appUserService) {
        this.expenseRepository = expenseRepository;
        this.appUserService = appUserService;
    }


    @Override
    public List<Expense> getAllUserExpenses(Long userId) {
        return new ArrayList<>(expenseRepository.findByUserIdOrderByDateDesc(userId));
    }

    @Override
    public List<Expense> getExpenseByDate(String date, Long userId) {

        return expenseRepository.findByUserIdOrderByDateDesc(userId).stream().filter(
                expense -> expense.getDate().equals(date)).toList();

    }

    @Override
    public List<Expense> getExpenseByCategoryAndMonth(String category, String month, Long userId) {
        if (!month.equalsIgnoreCase("null")){

            return expenseRepository.findByUserIdOrderByDateDesc(userId).stream().filter(e->
                            e.getCategory().equalsIgnoreCase(category) && e.getDate().startsWith(month))
                    .collect(Collectors.toList());

        }else{

            return expenseRepository.findByUserIdOrderByDateDesc(userId).stream().filter(e->
                            e.getCategory().equalsIgnoreCase(category))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<String> getCategories(Long userId) {
        return  expenseRepository.findByUserIdOrderByDateDesc(userId).stream().map(Expense::getCategory).distinct().collect(Collectors.toList());
    }

    @Override
    public Optional<Expense> getById(Long id, Long userId) {

        return expenseRepository.findByIdAndUserId(id,userId);

    }

    @Override
    public Expense createExpense(Expense expense, Long userId) {

        Optional<AppUser> userOpt = appUserService.findById(userId);
        if (userOpt.isPresent()){
            AppUser user = userOpt.get();
            expense.setUser(user);
            return expenseRepository.save(expense);
        }
        throw new RuntimeException("User not found");
    }

    @Override
    public boolean updateExpense(Expense updatedExpense, Long userId) {

        Optional<Expense> expenseToUpdate = expenseRepository.findByIdAndUserId(updatedExpense.getId(), userId);
        if(expenseToUpdate.isPresent()){
            updatedExpense.setUser(expenseToUpdate.get().getUser());
            expenseRepository.save(updatedExpense);
            return true;
        }
        return false;
    }


    @Override
    public boolean deleteExpense(Long id, Long userId) {

        Optional<Expense> expenseToDelete = expenseRepository.findByIdAndUserId(id,userId);

        if(expenseToDelete.isPresent()){
            expenseRepository.deleteById(id);
            return true;
        }
        return false;

    }


}
