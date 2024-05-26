package com.webwizard.jobofferservice.mapper;

import com.webwizard.jobofferservice.model.*;
import com.webwizard.jobofferservice.openapi.v1.model.*;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmploymentMapper {

    @Mapping(source = "employmentDto.from", target = "salaryFrom")
    @Mapping(source = "employmentDto.to", target = "salaryTo")
    @Mapping(source = "currency", target = "currency")
    @Mapping(source = "employmentType", target = "employmentType")
    @Mapping(target = "id", ignore = true)
    Employment toEntity(EmploymentDto employmentDto, Currency currency, EmploymentType employmentType);

    @Mapping(source = "employmentType", target = "type", qualifiedByName = "employmentType")
    @Mapping(source = "salaryFrom", target = "from")
    @Mapping(source = "salaryTo", target = "to")
    @Mapping(source = "currency", target = "currency", qualifiedByName = "currency")
    EmploymentDto toDto(Employment employment);

    List<EmploymentDto> toDto(List<Employment> employments);

    @Named("currency")
    default CurrencyDto mapCurrency(Currency currency) {
        return CurrencyDto.fromValue(currency.getName().toLowerCase());
    }

    @Named("employmentType")
    default EmploymentDto.TypeEnum mapCurrency(EmploymentType employmentType) {
        return EmploymentDto.TypeEnum.fromValue(employmentType.getName().toLowerCase());
    }
}