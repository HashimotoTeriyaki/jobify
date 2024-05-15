package com.webwizard.jobofferservice.model;

import jakarta.persistence.Id;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "job_offer")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class JobOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @NotNull
    @Size(max = 20, message = "Company name must be less than or equal to 20 characters")
    private String companyName;

    @NotBlank
    @NotNull
    @Size(max = 20, message = "City name must be less than or equal to 20 characters")
    private String city;

    @NotBlank
    @NotNull
    @Size(max = 20, message = "City name must be less than or equal to 20 characters")
    private String street;

    @NotBlank
    @NotNull
    @Size(max = 4400, message = "Description must be less than or equal to 4400 characters")
    private String description;

    private boolean remoteInterview;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "main_technology_id")
    private MainTechnology mainTechnology;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "experience_level_id")
    private ExperienceLevel experienceLevel;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "job_offer_id")
    @NotNull
    private List<OfferOperatingMode> offerOperatingModes = new ArrayList<>();

    @URL(message = "Invalid URL format")
    private String applyUrl;

    @NotBlank
    @NotNull
    @Email
    private String contactEmail;

    private String contactPhone;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(insertable = false, nullable = false)
    private LocalDateTime lastModifiedDate;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "job_offer_id")
    @NotNull
    private List<RequiredSkill> requiredSkills = new ArrayList<>();

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "job_offer_id")
    @NotNull
    private List<Employment> employments = new ArrayList<>();
}
