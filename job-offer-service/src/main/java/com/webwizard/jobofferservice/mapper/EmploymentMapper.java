package com.webwizard.jobofferservice.mapper;

import com.webwizard.jobofferservice.model.*;
import com.webwizard.jobofferservice.openapi.v1.model.EmploymentDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface EmploymentMapper {

    @Mapping(source = "employmentDto.from", target = "salaryFrom")
    @Mapping(source = "employmentDto.to", target = "salaryTo")
    @Mapping(source = "currency", target = "currency")
    @Mapping(source = "employmentType", target = "employmentType")
    @Mapping(target = "id", ignore = true)
    Employment toEntity(EmploymentDto employmentDto, Currency currency, EmploymentType employmentType);
}