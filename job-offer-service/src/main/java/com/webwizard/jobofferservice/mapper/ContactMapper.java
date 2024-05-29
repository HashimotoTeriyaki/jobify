package com.webwizard.jobofferservice.mapper;

import com.webwizard.jobofferservice.model.Contact;
import com.webwizard.jobofferservice.openapi.v1.model.ContactDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactMapper {

    Contact toEntity(ContactDto contactDto);

}