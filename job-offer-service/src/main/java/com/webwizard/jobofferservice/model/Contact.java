package com.webwizard.jobofferservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contact")
@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min = 3, max = 50, message = "Contact name must be between 3 and 50 characters")
    private String name;

    @NotNull
    @Size(min = 3, max = 50, message = "Contact surname must be between 3 and 50 characters")
    private String surname;

    @NotNull
    @Email
    @Size(min = 3, max = 100, message = "Contact e-mail must be between 3 and 100 characters")
    private String email;

    @NotNull
    @Pattern(regexp = "^\\+48\\d{9}$", message = "Invalid phone number format, example: +48 882 639 292")
    @Size(min = 12, max = 25, message = "Phone number must be between 12 and 25 characters")
    private String phone;
}
