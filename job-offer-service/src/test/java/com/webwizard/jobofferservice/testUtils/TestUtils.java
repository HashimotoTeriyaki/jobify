package com.webwizard.jobofferservice.testUtils;

import com.webwizard.jobofferservice.model.*;
import com.webwizard.jobofferservice.model.message.Message;
import com.webwizard.jobofferservice.openapi.v1.model.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestUtils {

    public static JobOfferDto getJobOfferDto() {
        return JobOfferDto.builder()
                .title("Junior Python developer needed asap")
                .companyName("Some company name")
                .city("Some city")
                .street("Some street")
                .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit")
                .experienceLevel(ExperienceLevelDto.JUNIOR)
                .mainTechnology(MainTechnologyDto.PYTHON)
                .typeOfWork(TypeOfWorkDto.FULL_TIME)
                .employments(List.of(
                        EmploymentDto.builder().type(EmploymentDto.TypeEnum.PERMANENT).currency(CurrencyDto.PLN).build()
                ))
                .operatingModes(List.of(OperatingModeDto.OFFICE))
                .requiredSkills(List.of(new RequiredSkillDto(RequiredSkillDto.NameEnum.PYTHON, 5)))
                .contact(new ContactDto("John", "Doe", "contac@email.com", "+48123456789"))
                .build();
    }

    public static JobOffer getJobOffer(Integer id, String mainTechnology, String typeOfWork) {
        return JobOffer.builder()
                .id(id)
                .title("Junior " + mainTechnology + " developer needed asap")
                .companyName("Some company name")
                .city("Some city")
                .street("Some street")
                .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit")
                .contact(getContact())
                .mainTechnology(getMainTechnology(mainTechnology))
                .typeOfWork(getTypeOfWork(typeOfWork))
                .experienceLevel(getExperience())
                .offerOperatingModes(List.of(
                        new OfferOperatingMode(1, getOperatingMode())
                ))
                .requiredSkills(List.of(
                        new RequiredSkill(1, new Skill(494, "PYTHON"), 5),
                        new RequiredSkill(2, new Skill(494, "ANGULAR"), 5)
                ))
                .employments(List.of(new Employment(1, 0, 0, getEmploymentType(), getCurrency())))
                .build();
    }

    public static SimpleJobOfferDto getSimpleJobOfferDto() {
        return SimpleJobOfferDto.builder()
                .title("Junior Python developer needed asap")
                .companyName("Some company name")
                .city("Some city")
                .mainTechnology(MainTechnologyDto.PYTHON)
                .employments(List.of(
                        EmploymentDto.builder().type(EmploymentDto.TypeEnum.PERMANENT).currency(CurrencyDto.PLN).build()
                ))
                .operatingModes(List.of(OperatingModeDto.OFFICE))
                .requiredSkills(List.of("python"))
                .build();
    }

    public static TypeOfWork getTypeOfWork(String typeOfWork) {
        return new TypeOfWork(
                1,
                typeOfWork.toUpperCase(),
                "An employment in which workers work a minimum number of hours defined as such by their employer."
        );
    }

    public static EmploymentType getEmploymentType() {
        return new EmploymentType(
                1,
                "PERMANENT",
                "Permanent employment is a long-term employment arrangement where an individual is hired " +
                        "without a predetermined end date, typically providing job security, benefits, and a stable income."
        );
    }

    public static ExperienceLevel getExperience() {
        return new ExperienceLevel(
                1,
                "JUNIOR",
                "Entry-level position typically held by individuals with limited experience or fresh graduates."
        );
    }

    public static OperatingMode getOperatingMode() {
        return new OperatingMode(
                3,
                "OFFICE",
                "Employees work primarily from the office location, adhering to a traditional in-office work setup."
        );
    }

    public static Contact getContact() {
        return new Contact(
                1,
                "John",
                "Doe",
                "contac@email.com",
                "+48123456789"
        );
    }

    public static MainTechnology getMainTechnology(String mainTechnology) {
        return new MainTechnology(5, mainTechnology.toUpperCase());
    }

    public static Currency getCurrency() {
        return new Currency(1, "PLN");
    }

    public static Message getMessage() {
        return new Message(
                1,
                "Junior Python developer needed asap",
                "Some city, Some street",
                "contac@email.com",
                java.time.LocalDateTime.now(),
                "John",
                false
        );
    }

    public static FetchedJobOfferDto getFetchedJobOfferDto() {
        return FetchedJobOfferDto.builder()
                .title("Senior java developer needed asap")
                .city("Warsaw")
                .build();
    }
}