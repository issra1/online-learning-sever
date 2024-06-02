package com.online.platform.learning.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor
@JsonSerialize(using = Course.CourseSerializer.class) // Use custom serializer
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String courseName;

    @JsonInclude(JsonInclude.Include.NON_NULL) // Include field only if not null
    private LocalDateTime enrolledDate;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private User instructor;

    private String instructorInstitution;
    private int enrolledCount;
    private String youtubeUrl;
    private String websiteUrl;
    private String courseType;
    private String price;
    private String skillLevel;
    private String language;
    private String description;

    private boolean paiedOrNo;

    private boolean isInPanel;

    private boolean isAccepted;
    private boolean isDeleted;

    @OneToMany(mappedBy = "course")
    @JsonIgnoreProperties("course") // Exclude course reference from serialization in UserCourseFavorite
    private Set<UserCourseFavorite> userFavorites = new HashSet<>();

    @OneToMany(mappedBy = "course")
    @JsonIgnoreProperties("course") // Exclude course reference from serialization in UserCourseFavorite
    private Set<CoursePaymentStatus> coursePaymentStatuses = new HashSet<>();


    @OneToMany(mappedBy = "course")
    @JsonIgnoreProperties("course") // Exclude course reference from serialization in UserCourseFavorite
    private Set<UserCoursePanel> userCoursePanels = new HashSet<>();


    public static class CourseSerializer extends JsonSerializer<Course> {
        @Override
        public void serialize(Course course, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("id", course.getId());
            jsonGenerator.writeStringField("courseName", course.getCourseName());
            if (course.getEnrolledDate() != null) {
                jsonGenerator.writeStringField("enrolledDate", course.getEnrolledDate().toString());
            }
            jsonGenerator.writeStringField("instructorInstitution", course.getInstructorInstitution());
            jsonGenerator.writeNumberField("enrolledCount", course.getEnrolledCount());
            jsonGenerator.writeStringField("youtubeUrl", course.getYoutubeUrl());
            jsonGenerator.writeStringField("websiteUrl", course.getWebsiteUrl());
            jsonGenerator.writeStringField("courseType", course.getCourseType());
            jsonGenerator.writeStringField("price", course.getPrice());
            jsonGenerator.writeStringField("skillLevel", course.getSkillLevel());
            jsonGenerator.writeStringField("language", course.getLanguage());
            jsonGenerator.writeStringField("description", course.getDescription());
            jsonGenerator.writeBooleanField("paiedOrNo", course.isPaiedOrNo());
            jsonGenerator.writeBooleanField("isInPanel", course.isInPanel());
            jsonGenerator.writeBooleanField("isAccepted", course.isAccepted());
            jsonGenerator.writeBooleanField("isDeleted", course.isDeleted());

            // Serialize userFavorites if needed
            if (course.getUserFavorites() != null && !course.getUserFavorites().isEmpty()) {
                jsonGenerator.writeArrayFieldStart("userFavorites");
                for (UserCourseFavorite favorite : course.getUserFavorites()) {
                    jsonGenerator.writeStartObject();
                    jsonGenerator.writeNumberField("id", favorite.getId());
                    jsonGenerator.writeStringField("courseName", favorite.getCourse().getCourseName());
                    jsonGenerator.writeStringField("userName", favorite.getUser().getFullName());
                    // Serialize other fields of UserCourseFavorite if needed
                    jsonGenerator.writeEndObject();
                }
                jsonGenerator.writeEndArray();
            }

            if (course.getCoursePaymentStatuses() != null && !course.getCoursePaymentStatuses().isEmpty()) {
                jsonGenerator.writeArrayFieldStart("coursePaymentStatuses");
                for (CoursePaymentStatus coursePaymentStatus : course.getCoursePaymentStatuses()) {
                    jsonGenerator.writeStartObject();
                    jsonGenerator.writeNumberField("id", coursePaymentStatus.getId());
                    jsonGenerator.writeStringField("courseName", coursePaymentStatus.getCourse().getCourseName());
                    jsonGenerator.writeStringField("userName", coursePaymentStatus.getUser().getFullName());
                    // Serialize other fields of UserCourseFavorite if needed
                    jsonGenerator.writeEndObject();
                }
                jsonGenerator.writeEndArray();
            }


            jsonGenerator.writeEndObject();
        }
    }
}
