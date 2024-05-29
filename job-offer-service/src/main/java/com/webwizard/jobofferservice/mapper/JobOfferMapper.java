package com.webwizard.jobofferservice.mapper;

import com.webwizard.jobofferservice.model.*;
import com.webwizard.jobofferservice.openapi.v1.model.*;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EmploymentMapper.class, OperatingModeMapper.class, RequiredSkillMapper.class})
public interface JobOfferMapper {

    @Mapping(source = "mainTechnology", target = "mainTechnology")
    @Mapping(source = "typeOfWork", target = "typeOfWork")
    @Mapping(source = "experienceLevel", target = "experienceLevel")
    @Mapping(source = "offerOperatingModes", target = "offerOperatingModes")
    @Mapping(source = "requiredSkills", target = "requiredSkills")
    @Mapping(source = "employments", target = "employments")
    @Mapping(source = "jobOfferDto.title", target = "title")
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "jobOfferDto.description", target = "description")
    @Mapping(expression = "java(java.time.LocalDateTime.now())", target = "createdDate")
    @Mapping(expression = "java(java.time.LocalDateTime.now())", target = "lastModifiedDate")
    @Mapping(source = "contact", target = "contact")
    @Mapping(source = "jobOfferDto.remoteInterview", target = "remoteInterview", defaultValue = "false")
    JobOffer toEntity(
            JobOfferDto jobOfferDto,
            MainTechnology mainTechnology,
            TypeOfWork typeOfWork,
            ExperienceLevel experienceLevel,
            List<OfferOperatingMode> offerOperatingModes,
            List<RequiredSkill> requiredSkills,
            List<Employment> employments,
            Contact contact
    );

    @Mapping(source = "mainTechnology", target = "mainTechnology", qualifiedByName = "mainTechnology")
    @Mapping(source = "experienceLevel", target = "experienceLevel", qualifiedByName = "experienceLevel")
    @Mapping(source = "typeOfWork", target = "typeOfWork", qualifiedByName = "typeOfWork")
    @Mapping(source = "offerOperatingModes", target = "operatingModes")
    FetchedJobOfferDto toDto(JobOffer jobOffer);

    List<FetchedJobOfferDto> toDto(List<JobOffer> requiredSkills);

    @Named("mainTechnology")
    default String mapMainTechnology(MainTechnology mainTechnology) {
        return mainTechnology.getName().toUpperCase();
    }

    @Named("experienceLevel")
    default ExperienceLevelDto mapExperienceLevel(ExperienceLevel experienceLevel) {
        return ExperienceLevelDto.fromValue(experienceLevel.getLevel().toLowerCase());
    }

    @Named("typeOfWork")
    default TypeOfWorkDto mapTypeOfWork(TypeOfWork typeOfWork) {
        return TypeOfWorkDto.fromValue(typeOfWork.getName().toLowerCase());
    }
}
