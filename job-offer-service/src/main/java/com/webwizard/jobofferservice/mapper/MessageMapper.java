package com.webwizard.jobofferservice.mapper;

import com.webwizard.jobofferservice.model.JobOffer;
import com.webwizard.jobofferservice.model.message.Message;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "jobOffer", target = "location", qualifiedByName = "location")
    @Mapping(source = "jobOffer.contact.email", target = "recipient")
    @Mapping(source = "jobOffer.contact.name", target = "contactName")
    @Mapping(target = "send", constant = "false")
    Message toEntity(JobOffer jobOffer);

    @Named("location")
    default String mapLocation(JobOffer jobOffer) {
        return jobOffer.getCity() + ", " + jobOffer.getStreet();
    }
}