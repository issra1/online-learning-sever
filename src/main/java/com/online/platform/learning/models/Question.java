package com.online.platform.learning.models;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@Setter
@JsonSerialize(using = Question.QuestionSerializer.class) // Use custom serializer
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OptionQuestion> options = new ArrayList<>();


    public static class QuestionSerializer extends JsonSerializer<Question> {
        @Override
        public void serialize(Question question, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("id", question.getId());
            jsonGenerator.writeStringField("title", question.getText());
            // Serialize questions
            if (question.getOptions() != null && !question.getOptions().isEmpty()) {
                jsonGenerator.writeArrayFieldStart("options");
                for (OptionQuestion optionQuestion : question.getOptions()) {
                    jsonGenerator.writeStartObject();
                    jsonGenerator.writeNumberField("id", optionQuestion.getId());
                    jsonGenerator.writeStringField("text", optionQuestion.getText());
                    jsonGenerator.writeBooleanField("correct", optionQuestion.isCorrect());
                    jsonGenerator.writeEndObject();
                }
                jsonGenerator.writeEndArray();
            }
            jsonGenerator.writeEndObject();
        }
    }


}
