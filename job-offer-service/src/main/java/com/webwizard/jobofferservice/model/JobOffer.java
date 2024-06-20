package com.webwizard.jobofferservice.model;

import jakarta.persistence.Id;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.LAZY;

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
    @Size(max = 100)
    private String title;

    @NotBlank
    @Size(max = 100)
    private String companyName;

    @NotBlank
    @Size(max = 50)
    private String city;

    @NotBlank
    @Size(max = 50)
    private String street;

    @NotBlank
    @Size(max = 4400)
    private String description;

    private boolean remoteInterview;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "contact_id")
    @Valid
    private Contact contact;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "main_technology_id")
    private MainTechnology mainTechnology;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "type_of_work_id")
    private TypeOfWork typeOfWork;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "experience_level_id")
    private ExperienceLevel experienceLevel;

    @OneToMany(cascade = PERSIST, fetch = LAZY)
    @JoinColumn(name = "job_offer_id")
    @NotEmpty
    @Valid
    private List<OfferOperatingMode> offerOperatingModes;

    @URL(message = "Invalid URL format")
    private String applyUrl;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime lastModifiedDate;

    @OneToMany(cascade = PERSIST, fetch = LAZY)
    @JoinColumn(name = "job_offer_id")
    @NotEmpty
    private List<RequiredSkill> requiredSkills;

    @OneToMany(cascade = PERSIST, fetch = LAZY)
    @JoinColumn(name = "job_offer_id")
    @NotEmpty
    @Valid
    private List<Employment> employments;
}
