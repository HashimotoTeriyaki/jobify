package com.webwizard.jobofferservice.mapper;

import com.webwizard.jobofferservice.model.*;
import com.webwizard.jobofferservice.openapi.v1.model.JobOfferDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = EmploymentMapper.class)
public interface JobOfferMapper {

    @Mapping(source = "mainTechnology", target = "mainTechnology")
    @Mapping(source = "experienceLevel", target = "experienceLevel")
    @Mapping(source = "offerOperatingModes", target = "offerOperatingModes")
    @Mapping(source = "requiredSkills", target = "requiredSkills")
    @Mapping(source = "employments", target = "employments")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "description", source = "jobOfferDto.description")
    @Mapping(target = "createdDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "lastModifiedDate", expression = "java(java.time.LocalDateTime.now())")
    JobOffer mapToEntity(
            JobOfferDto jobOfferDto,
            MainTechnology mainTechnology,
            ExperienceLevel experienceLevel,
            List<OfferOperatingMode> offerOperatingModes,
            List<RequiredSkill> requiredSkills,
            List<Employment> employments
    );
}
