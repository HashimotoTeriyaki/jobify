package com.webwizard.jobofferservice.service;

import com.webwizard.jobofferservice.exception.JobOfferNotFoundException;
import com.webwizard.jobofferservice.mapper.*;
import com.webwizard.jobofferservice.model.*;
import com.webwizard.jobofferservice.openapi.v1.model.*;
import com.webwizard.jobofferservice.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

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
    private final TypeOfWorkRepository typeOfWorkRepository;
    private final ContactRepository contactRepository;
    private final MessageRepository messageRepository;

    private final JobOfferMapper jobOfferMapper;
    private final EmploymentMapper employmentMapper;
    private final RequiredSkillMapper requiredSkillMapper;
    private final OperatingModeMapper operatingModeMapper;
    private final ContactMapper contactMapper;
    private final MessageMapper messageMapper;

    private final JobOfferCriteriaRepository jobOfferCriteriaRepository;

    @Transactional
    public JobOfferMetadataDto createJobOffer(JobOfferDto jobOfferDto) {
        log.info("Creating job offer with title: {}...", jobOfferDto.getTitle());
        JobOffer jobOffer = mapJobOfferToEntity(jobOfferDto);
        JobOffer savedJobOffer = jobOfferRepository.save(jobOffer);
        log.info("New job offer created! id: {}", savedJobOffer.getId());
        messageRepository.save(messageMapper.toEntity(jobOffer));
        return new JobOfferMetadataDto(savedJobOffer.getId());
    }

    public List<SimpleJobOfferDto> findOfferByFilters(
            Integer salaryMin, Integer salaryMax,
            String technology, String employmentType,
            String experience, String operatingMode,
            String typeOfWork, String orderBy,
            String sortBy, Integer page,
            Integer pageSize
    ) {
        log.info("Searching for job offers with filters...");
        List<JobOffer> jobOffers = jobOfferCriteriaRepository.findAllByFilters(
                salaryMin, salaryMax,
                technology, employmentType,
                experience, operatingMode,
                typeOfWork, orderBy,
                sortBy, page,
                pageSize
        );
        log.info("{} Job offer(s) found!", jobOffers.size());
        return jobOffers.stream()
                .map(jobOfferMapper::toSimpleDto)
                .toList();
    }

    public FetchedJobOfferDto findJobOfferById(Integer id) {
        log.info("Searching for job offer with id: {}", id);
        JobOffer jobOffer = jobOfferRepository
                .findById(id)
                .orElseThrow(() -> new JobOfferNotFoundException(404, "Job offer with id: " + id + " not found"));
        log.info("Job offer found! id: {}", id);
        return jobOfferMapper.toDto(jobOffer);
    }

    private JobOffer mapJobOfferToEntity(JobOfferDto jobOfferDto) {
        var mainTechnology = mainTechnologyRepository.findFirstByName(jobOfferDto.getMainTechnology().getValue().toUpperCase());
        var typeOfWork = typeOfWorkRepository.findFirstByName(jobOfferDto.getTypeOfWork().getValue().toUpperCase());
        var experienceLevel = experienceLevelRepository.findFirstByLevel(jobOfferDto.getExperienceLevel().getValue().toUpperCase());
        var operatingModes = mapOperatingModes(jobOfferDto.getOperatingModes());
        var requiredSkills = mapRequiredSkills(jobOfferDto.getRequiredSkills());
        var employments = mapEmployments(jobOfferDto.getEmployments());
        var contact = isNewContact(jobOfferDto.getContact());

        return jobOfferMapper.toEntity(
                jobOfferDto,
                mainTechnology,
                typeOfWork,
                experienceLevel,
                operatingModes,
                requiredSkills,
                employments,
                contact
        );
    }

    private Contact isNewContact(ContactDto contactDto) {
        Optional<Contact> contact = contactRepository.findAllByPhoneAndEmailAndName(contactDto.getPhone(), contactDto.getEmail(), contactDto.getName());
        return contact.orElseGet(() -> contactRepository.save(contactMapper.toEntity(contactDto)));
    }

    private List<Employment> mapEmployments(List<EmploymentDto> employments) {
        return employments.stream().map(
                employmentDto -> employmentMapper.toEntity(employmentDto,
                        currencyRepository.findFirstByName(employmentDto.getCurrency().getValue().toUpperCase()),
                        employmentTypeRepository.findFirstByName(employmentDto.getType()
                                .getValue().toUpperCase()
                        )
                )
        ).toList();
    }

    private List<RequiredSkill> mapRequiredSkills(List<RequiredSkillDto> requiredSkills) {
        return requiredSkills.stream().map(
                requiredSkillDto -> requiredSkillMapper.toEntity(requiredSkillDto,
                        skillRepository.findFirstByName(requiredSkillDto.getName()
                                .getValue().toUpperCase()
                        )
                )
        ).toList();
    }

    private List<OfferOperatingMode> mapOperatingModes(List<OperatingModeDto> operatingModes) {
        return operatingModes.stream().map(
                operatingModeDto -> operatingModeMapper.toEntity(
                        operatingModeRepository.findFirstByName(
                                operatingModeDto.getValue().toUpperCase())
                )
        ).toList();
    }
}
