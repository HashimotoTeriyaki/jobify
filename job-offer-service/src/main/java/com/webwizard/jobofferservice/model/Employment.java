package com.webwizard.jobofferservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employment")
@Entity
public class Employment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int salaryFrom;

    private int salaryTo;

    @NotNull(message = "Employment type must be provided")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "employment_type_id")
    private EmploymentType employmentType;

    @NotNull(message = "Currency must be provided")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "currency_id")
    private Currency currency;
}
