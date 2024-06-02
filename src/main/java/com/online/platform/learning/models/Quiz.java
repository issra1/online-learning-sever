package com.online.platform.learning.models;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter
@JsonSerialize(using = Quiz.QuizSerializer.class) // Use custom serializer
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    private String description;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    private boolean isAccepted;
    private boolean isDeleted;


    public static class QuizSerializer extends JsonSerializer<Quiz> {
        @Override
        public void serialize(Quiz quiz, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("id", quiz.getId());
            jsonGenerator.writeStringField("title", quiz.getTitle());
            jsonGenerator.writeStringField("description", quiz.getDescription());
            jsonGenerator.writeBooleanField("isAccepted", quiz.isAccepted());
            jsonGenerator.writeBooleanField("isDeleted", quiz.isDeleted());
            // Serialize questions
            if (quiz.getQuestions() != null && !quiz.getQuestions().isEmpty()) {
                jsonGenerator.writeArrayFieldStart("questions");
                for (Question question : quiz.getQuestions()) {
                    jsonGenerator.writeStartObject();
                    jsonGenerator.writeNumberField("id", question.getId());
                    jsonGenerator.writeStringField("text", question.getText());
                    jsonGenerator.writeEndObject();
                }
                jsonGenerator.writeEndArray();
            }
            jsonGenerator.writeEndObject();
        }
    }


}
