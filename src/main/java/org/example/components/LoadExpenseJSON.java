package org.example.components;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.example.Main;
import org.example.entities.Expense;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Data
@Component
public class LoadExpenseJSON {

    private List<Expense> expenses;

    @PostConstruct
    public void loadInit(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            InputStream is = Main.class.getResourceAsStream("/expenses.json");
            expenses = objectMapper.readValue(is, new TypeReference<List<Expense>>(){});
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

}
