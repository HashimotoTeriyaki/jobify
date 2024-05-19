package com.webwizard.jobofferservice.mapper;

import com.webwizard.jobofferservice.model.*;
import com.webwizard.jobofferservice.openapi.v1.model.JobOfferDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = EmploymentMapper.class)
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
    JobOffer mapToEntity(
            JobOfferDto jobOfferDto,
            MainTechnology mainTechnology,
            TypeOfWork typeOfWork,
            ExperienceLevel experienceLevel,
            List<OfferOperatingMode> offerOperatingModes,
            List<RequiredSkill> requiredSkills,
            List<Employment> employments
    );
}
