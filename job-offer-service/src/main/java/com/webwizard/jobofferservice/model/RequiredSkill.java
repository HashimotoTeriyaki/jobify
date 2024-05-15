package com.webwizard.jobofferservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "required_skill")
@Entity
public class RequiredSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "skill_id")
    private Skill skill;

    @Min(value = 1)
    @Max(value = 5)
    private int level;
}
