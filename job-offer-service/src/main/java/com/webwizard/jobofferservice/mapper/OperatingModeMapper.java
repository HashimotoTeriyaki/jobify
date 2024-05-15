package com.webwizard.jobofferservice.mapper;

import com.webwizard.jobofferservice.model.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OperatingModeMapper {

    @Mapping(source = "operatingMode", target = "operatingMode")
    @Mapping(target = "id", ignore = true)
    OfferOperatingMode toEntity(OperatingMode operatingMode);
}
