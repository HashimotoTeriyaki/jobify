package com.webwizard.jobofferservice.service;

import com.webwizard.jobofferservice.mapper.*;
import com.webwizard.jobofferservice.model.*;
import com.webwizard.jobofferservice.openapi.v1.model.*;
import com.webwizard.jobofferservice.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class JobOfferService {

    private final SkillRepository skillRepository;
    private final JobOfferRepository jobOfferRepository;
    private final MainTechnologyRepository mainTechnologyRepository;
    private final CurrencyRepository currencyRepository;
    private final EmploymentTypeRepository employmentTypeRepository;
    private final ExperienceLevelRepository experienceLevelRepository;
    private final OperatingModeRepository operatingModeRepository;

    private EmploymentMapper employmentMapper;
    private RequiredSkillMapper requiredSkillMapper;
    private OperatingModeMapper operatingModeMapper;
    private JobOfferMapper jobOfferMapper;


    @Transactional
    public JobOfferMetadataDto createJobOffer(JobOfferDto jobOfferDto) {
        log.info("Creating job offer...");
        JobOffer jobOffer = mapJobOfferToEntity(jobOfferDto);
        JobOffer savedJobOffer = jobOfferRepository.save(jobOffer);
        return new JobOfferMetadataDto(String.valueOf(savedJobOffer.getId()));
    }

    private JobOffer mapJobOfferToEntity(JobOfferDto jobOfferDto) {
        MainTechnology mainTechnology = mainTechnologyRepository.findFirstByName(jobOfferDto.getMainTechnology().toUpperCase());
        ExperienceLevel experienceLevel = experienceLevelRepository.findFirstByLevel(jobOfferDto.getExperienceLevel());
        List<OfferOperatingMode> operatingModes = mapOperatingModes(jobOfferDto.getOperatingModes());
        List<Employment> employments = mapEmployments(jobOfferDto.getEmployments());
        List<RequiredSkill> requiredSkills = mapRequiredSkills(jobOfferDto.getRequiredSkills());

        return jobOfferMapper.mapToEntity(
                jobOfferDto,
                mainTechnology,
                experienceLevel,
                operatingModes,
                requiredSkills,
                employments
        );
    }

    private List<Employment> mapEmployments(List<EmploymentDto> employments) {
        return employments.stream().map(
                employmentDto -> employmentMapper.toEntity(employmentDto,
                        currencyRepository.findFirstByName(employmentDto.getCurrency().toUpperCase()),
                        employmentTypeRepository.findFirstByName(employmentDto.getType().toUpperCase())
                )
        ).toList();
    }

    private List<RequiredSkill> mapRequiredSkills(List<RequiredSkillDto> requiredSkills) {
        return requiredSkills.stream().map(
                requiredSkillDto -> requiredSkillMapper.toEntity(requiredSkillDto,
                        skillRepository.findFirstByName(requiredSkillDto.getName().toUpperCase())
                )
        ).toList();
    }

    private List<OfferOperatingMode> mapOperatingModes(List<String> operatingModes) {
        return operatingModes.stream().map(
                operatingModeDto -> operatingModeMapper.toEntity(
                        operatingModeRepository.findFirstByName(operatingModeDto.toUpperCase())
                )
        ).toList();
    }
}
