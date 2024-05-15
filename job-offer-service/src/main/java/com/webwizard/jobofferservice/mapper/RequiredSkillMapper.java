package com.webwizard.jobofferservice.mapper;

import com.webwizard.jobofferservice.model.*;
import com.webwizard.jobofferservice.openapi.v1.model.RequiredSkillDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RequiredSkillMapper {
    @Mapping(source = "requiredSkillDto.level", target = "level")
    @Mapping(source = "skill", target = "skill")
    @Mapping(target = "id", ignore = true)
    RequiredSkill toEntity(RequiredSkillDto requiredSkillDto, Skill skill);
}
