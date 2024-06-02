package com.online.platform.learning.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class OptionQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private boolean correct;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    // Other fields, getters, setters
}
