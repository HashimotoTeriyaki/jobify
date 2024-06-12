package com.webwizard.jobofferservice.mapper;

import com.webwizard.jobofferservice.model.*;
import com.webwizard.jobofferservice.openapi.v1.model.RequiredSkillDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RequiredSkillMapper {
    @Mapping(source = "requiredSkillDto.level", target = "level")
    @Mapping(source = "skill", target = "skill")
    @Mapping(target = "id", ignore = true)
    RequiredSkill toEntity(RequiredSkillDto requiredSkillDto, Skill skill);

    @Mapping(source = "skill", target = "name", qualifiedByName = "skill")
    RequiredSkillDto toDto(RequiredSkill requiredSkill);

    List<RequiredSkillDto> toDto(List<RequiredSkill> requiredSkills);

    @Named("skill")
    default RequiredSkillDto.NameEnum mapSkill(Skill skill) {
        return RequiredSkillDto.NameEnum.fromValue(skill.getName().toLowerCase());
    }

    List<String> requiredSkillsToDto(List<RequiredSkill> requiredSkills);

    default String mapRequiredSkill(RequiredSkill requiredSkill) {
        return requiredSkill.getSkill()
                .getName()
                .toLowerCase();
    }
}
