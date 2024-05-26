package com.webwizard.jobofferservice.mapper;

import com.webwizard.jobofferservice.model.*;
import com.webwizard.jobofferservice.openapi.v1.model.OperatingModeDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OperatingModeMapper {

    @Mapping(target = "id", ignore = true)
    OfferOperatingMode toEntity(OperatingMode operatingMode);

    List<OperatingModeDto> toDto(List<OfferOperatingMode> operatingModes);

    default OperatingModeDto mapOperatingMode(OfferOperatingMode operatingMode) {
        return OperatingModeDto.fromValue(operatingMode.getOperatingMode().getName().toLowerCase());
    }
}
