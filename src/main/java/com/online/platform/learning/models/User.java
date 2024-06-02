package com.online.platform.learning.models;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize(using = User.UserSerializer.class)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;
  private String email;
  private String address;
  private String fullName;
  private String phoneNumber;
  private String gender;
  private String password;
  private String institutionname;
  private String department;
  private String experience;
  private String profession;
  private String token;
  private String status;
  @OneToMany(mappedBy = "user")
  @JsonIgnoreProperties("user") // Exclude user reference from serialization in UserCourseFavorite
  private Set<UserCourseFavorite> favoriteCourses = new HashSet<>();

  @OneToMany(mappedBy = "user")
  @JsonIgnoreProperties("user") // Exclude user reference from serialization in UserCourseFavorite
  private Set<CoursePaymentStatus> coursePaymentStatuses = new HashSet<>();

  @OneToMany(mappedBy = "user")
  @JsonIgnoreProperties("user") // Exclude user reference from serialization in UserCourseFavorite
  private Set<UserCoursePanel> userCoursePanels = new HashSet<>();

  // Constructor, getters, setters, and builder methods as before

  public static class UserSerializer extends JsonSerializer<User> {
    @Override
    public void serialize(User user, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, IOException {
      jsonGenerator.writeStartObject();
      jsonGenerator.writeNumberField("id", user.getId());
      jsonGenerator.writeStringField("username", user.getUsername());
      jsonGenerator.writeStringField("email", user.getEmail());
      jsonGenerator.writeStringField("address", user.getAddress());
      jsonGenerator.writeStringField("fullName", user.getFullName());
      jsonGenerator.writeStringField("phoneNumber", user.getPhoneNumber());
      jsonGenerator.writeStringField("gender", user.getGender());
      jsonGenerator.writeStringField("password", user.getPassword());
      jsonGenerator.writeStringField("institutionname", user.getInstitutionname());
      jsonGenerator.writeStringField("department", user.getDepartment());
      jsonGenerator.writeStringField("experience", user.getExperience());
      jsonGenerator.writeStringField("profession", user.getProfession());
      jsonGenerator.writeStringField("token", user.getToken());
      jsonGenerator.writeStringField("status", user.getStatus());

      // Serialize favoriteCourses if needed
      if (user.getFavoriteCourses() != null && !user.getFavoriteCourses().isEmpty()) {
        jsonGenerator.writeArrayFieldStart("favoriteCourses");
        for (UserCourseFavorite favorite : user.getFavoriteCourses()) {
          jsonGenerator.writeStartObject();
          jsonGenerator.writeNumberField("id", favorite.getId());
          jsonGenerator.writeStringField("courseName", favorite.getCourse().getCourseName());
          // Serialize other fields of UserCourseFavorite if needed
          jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
      }

      // Serialize userCoursePanels if needed
      if (user.getUserCoursePanels() != null && !user.getUserCoursePanels().isEmpty()) {
        jsonGenerator.writeArrayFieldStart("userCoursePanels");
        for (UserCoursePanel coursePanel : user.getUserCoursePanels()) {
          jsonGenerator.writeStartObject();
          jsonGenerator.writeNumberField("id", coursePanel.getId());
          jsonGenerator.writeStringField("courseName", coursePanel.getCourse().getCourseName());
          // Serialize other fields of UserCourseFavorite if needed
          jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
      }


      if (user.getCoursePaymentStatuses() != null && !user.getCoursePaymentStatuses().isEmpty()) {
        jsonGenerator.writeArrayFieldStart("coursePaymentStatuses");
        for (CoursePaymentStatus cpStatus : user.getCoursePaymentStatuses()) {
          jsonGenerator.writeStartObject();
          jsonGenerator.writeNumberField("id", cpStatus.getId());
          jsonGenerator.writeStringField("courseName", cpStatus.getCourse().getCourseName());
          // Serialize other fields of UserCourseFavorite if needed
          jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
      }


/*      // Serialize roles if needed
      if (user.getRoles() != null && !user.getRoles().isEmpty()) {
        jsonGenerator.writeArrayFieldStart("roles");
        for (Role role : user.getRoles()) {
          jsonGenerator.writeStartObject();
          jsonGenerator.writeNumberField("id", role.getId());
          jsonGenerator.writeStringField("roleName", role.getName().toString());
          // Serialize other fields of Role if needed
          jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
      }*/

      jsonGenerator.writeEndObject();
    }
  }

  @JsonCreator
  public User(String fullName) {
    this.fullName = fullName;
  }


  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  private User(Builder builder) {
    this.username = builder.username;
    this.email = builder.email;
    this.address = builder.address;
    this.fullName = builder.fullName;
    this.phoneNumber = builder.phoneNumber;
    this.gender = builder.gender;
    this.password = builder.password;
    this.institutionname = builder.institutionname;
    this.department = builder.department;
    this.experience = builder.experience;
    this.status = builder.status;
    this.profession = builder.profession;
  }



  // Builder class for User
  public static class Builder {
    private String username;
    private String email;
    private String address;
    private String fullName;
    private String phoneNumber;

    private String gender;
    private String password;
    private String institutionname;
    private String department;
    private String experience;
    private String profession;
    private String status;


    public Builder username(String username) {
      this.username = username;
      return this;
    }


    public Builder email(String email) {
      this.email = email;
      return this;
    }

    public Builder fullName(String fullName) {
      this.fullName = fullName;
      return this;
    }

    public Builder address(String address) {
      this.address = address;
      return this;
    }

    public Builder phoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
      return this;
    }

    public Builder gender(String gender) {
      this.gender = gender;
      return this;
    }

    public Builder password(String password) {
      this.password = password;
      return this;
    }

    public Builder institutionname(String institutionname) {
      this.institutionname = institutionname;
      return this;
    }

    public Builder department(String department) {
      this.department = department;
      return this;
    }

    public Builder experience(String experience) {
      this.experience = experience;
      return this;
    }

    public Builder status(String status) {
      this.status = status;
      return this;
    }

    public Builder profession(String profession) {
      this.profession = profession;
      return this;
    }


    // Build method to create User instance
    public User build() {
      return new User(this);
    }
  }

}
