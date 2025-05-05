package org.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data @Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int expenseType;
    private String date;
    private Double amount;
    private String category;
    private String account;
    private String note;

    @ManyToOne(fetch = FetchType.LAZY) //User loaded when needed
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private AppUser user;
}
