package org.example.controllers;
import org.example.components.LoadExpenseJSON;
import org.example.entities.AppUser;
import org.example.entities.Expense;
import org.example.services.AppUserService;
import org.example.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ExpensesController {

    private final ExpenseService expService;
    private final AppUserService appUserService;

    public ExpensesController(ExpenseService expService, AppUserService appUserService) {
        this.expService = expService;
        this.appUserService = appUserService;
    }


    @GetMapping("/expenses/day/{date}")
    public ResponseEntity<List<Expense>> getByDate(@PathVariable String date, Authentication authentication){

        String username = authentication.getName();
        AppUser user = appUserService.findByUsername(username);

        List<Expense> filteredExpenses = expService.getExpenseByDate(date, user.getId());

        return new ResponseEntity<>(filteredExpenses,HttpStatus.OK);
    }

    @GetMapping("/expenses/{id}")
    public ResponseEntity<Optional<Expense>> getById(@PathVariable long id, Authentication authentication){

        String username = authentication.getName();
        AppUser user = appUserService.findByUsername(username);

        Optional<Expense> expById = expService.getById(id, user.getId());

        if(expById.isEmpty()){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        else return new ResponseEntity<>(expById,HttpStatus.OK);

    }

    @GetMapping("/expenses/category/{category}/month")
    public ResponseEntity<List<Expense>> getByCategoriesAndMonth(
            @PathVariable String category ,@RequestParam String month, Authentication authentication){

        String username = authentication.getName();
        AppUser user = appUserService.findByUsername(username);

        List<Expense> filteredExpenses = expService.getExpenseByCategoryAndMonth(category,month,user.getId());

        return new ResponseEntity<>(filteredExpenses,HttpStatus.OK);

    }

    @GetMapping("/expenses/categories")
    public ResponseEntity<List<String>> getCategories(Authentication authentication){

        String username = authentication.getName();
        AppUser user = appUserService.findByUsername(username);

        List<String> categories = expService.getCategories(user.getId());

        return new ResponseEntity<>(categories,HttpStatus.OK);
        
    }

    @GetMapping("/expenses")
    public ResponseEntity<List<Expense>> getAllExpenses(Authentication authentication){

        String username = authentication.getName();
        AppUser user = appUserService.findByUsername(username);

        List<Expense> expenses = expService.getAllUserExpenses(user.getId());

        return new ResponseEntity<>(expenses,HttpStatus.OK);
    }

    @PutMapping("/expenses/{id}")
    public ResponseEntity<Expense> updateGroceriesById(@PathVariable long id,@RequestBody Expense expense, Authentication authentication){

        String username = authentication.getName();
        AppUser user = appUserService.findByUsername(username);

        expense.setId(id);

        if (expService.updateExpense(expense, user.getId())){
            return new ResponseEntity<>(expense,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

    }

    @PostMapping("/expenses")
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense, Authentication authentication){

        String username = authentication.getName();
        AppUser user = appUserService.findByUsername(username);

        Expense newExp = expService.createExpense(expense, user.getId());

        return new ResponseEntity<>(newExp , HttpStatus.OK);

    }

    @DeleteMapping("/expenses/{id}")
    public ResponseEntity deleteExpense(@PathVariable long id, Authentication authentication){

        String username = authentication.getName();
        AppUser user = appUserService.findByUsername(username);

        Optional<Expense> toRemove = expService.getById(id, user.getId());

        if(toRemove.isPresent()){

            if(expService.deleteExpense(toRemove.get().getId(), user.getId())){
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }

        }

        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
