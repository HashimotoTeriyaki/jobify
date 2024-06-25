package com.webwizard.jobofferservice.service;

import com.webwizard.jobofferservice.exception.JobOfferNotFoundException;
import com.webwizard.jobofferservice.mapper.*;
import com.webwizard.jobofferservice.model.Currency;
import com.webwizard.jobofferservice.model.*;
import com.webwizard.jobofferservice.openapi.v1.model.*;
import com.webwizard.jobofferservice.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobOfferServiceTest {

    @Mock
    private SkillRepository skillRepository;
    @Mock
    private JobOfferRepository jobOfferRepository;
    @Mock
    private MainTechnologyRepository mainTechnologyRepository;
    @Mock
    private CurrencyRepository currencyRepository;
    @Mock
    private EmploymentTypeRepository employmentTypeRepository;
    @Mock
    private ExperienceLevelRepository experienceLevelRepository;
    @Mock
    private OperatingModeRepository operatingModeRepository;
    @Mock
    private TypeOfWorkRepository typeOfWorkRepository;
    @Mock
    private ContactRepository contactRepository;
    @Mock
    private JobOfferMapper jobOfferMapper;
    @Mock
    private EmploymentMapper employmentMapper;
    @Mock
    private RequiredSkillMapper requiredSkillMapper;
    @Mock
    private OperatingModeMapper operatingModeMapper;
    @Mock
    private ContactMapper contactMapper;
    @Mock
    private JobOfferCriteriaRepository criteriaRepository;

    @InjectMocks
    private JobOfferService jobOfferService;

    @Test
    void createJobOffer_withRequestBodyAndExistingContact_savesEntity() {
        JobOfferDto entityToSave = getJobOfferDto();

        MainTechnology mainTechnologyMock = getMainTechnology();

        when(mainTechnologyRepository.findFirstByName(
                entityToSave.getMainTechnology().getValue().toUpperCase()
        )).thenReturn(mainTechnologyMock);

        TypeOfWork typeOfWorkMock = getTypeOfWork();

        when(typeOfWorkRepository.findFirstByName(
                entityToSave.getTypeOfWork().getValue().toUpperCase()
        )).thenReturn(typeOfWorkMock);

        ExperienceLevel experienceMock = getExperience();

        when(experienceLevelRepository.findFirstByLevel(
                entityToSave.getExperienceLevel().getValue().toUpperCase()
        )).thenReturn(experienceMock);

        OperatingMode operatingModeMock = getOperatingMode();
        OfferOperatingMode offerOperatingModeMock = new OfferOperatingMode(1, operatingModeMock);

        when(operatingModeRepository.findFirstByName(
                entityToSave.getOperatingModes().get(0).getValue().toUpperCase())
        ).thenReturn(operatingModeMock);

        when(operatingModeMapper.toEntity(operatingModeMock))
                .thenReturn(offerOperatingModeMock);

        Skill skillMock = new Skill(494, "PYTHON");
        RequiredSkillDto requiredSkillDtoMock = new RequiredSkillDto(RequiredSkillDto.NameEnum.PYTHON, 5);
        RequiredSkill requiredSkillMock = new RequiredSkill(1, skillMock, requiredSkillDtoMock.getLevel());

        when(skillRepository.findFirstByName(
                entityToSave.getRequiredSkills().get(0).getName().getValue().toUpperCase())
        ).thenReturn(skillMock);

        when(requiredSkillMapper.toEntity(requiredSkillDtoMock, skillMock))
                .thenReturn(requiredSkillMock);

        Currency currencyMock = getCurrency();
        EmploymentType employmentTypeMock = getEmploymentType();
        Employment employmentMock = new Employment(1, 0, 0, employmentTypeMock, currencyMock);

        when(currencyRepository.findFirstByName(
                entityToSave.getEmployments().get(0).getCurrency().getValue().toUpperCase()
        )).thenReturn(currencyMock);

        when(employmentTypeRepository.findFirstByName(
                entityToSave.getEmployments().get(0).getType().getValue().toUpperCase()
        )).thenReturn(employmentTypeMock);

        when(employmentMapper.toEntity(
                entityToSave.getEmployments().get(0), currencyMock, employmentTypeMock
        )).thenReturn(employmentMock);

        Optional<Contact> contactMock = Optional.of(getContact());

        when(contactRepository.findAllByPhoneAndEmail(
                entityToSave.getContact().getPhone(),
                entityToSave.getContact().getEmail()
        )).thenReturn(contactMock);

        JobOffer jobOfferMock = getJobOffer();

        when(jobOfferMapper.toEntity(
                entityToSave,
                mainTechnologyMock,
                typeOfWorkMock,
                experienceMock,
                List.of(offerOperatingModeMock),
                List.of(requiredSkillMock),
                List.of(employmentMock),
                contactMock.get()
        )).thenReturn(jobOfferMock);

        when(jobOfferRepository.save(jobOfferMock)).thenReturn(jobOfferMock);


        JobOfferMetadataDto savedJobOffer = jobOfferService.createJobOffer(entityToSave);

        verify(jobOfferRepository).save(jobOfferMock);
        assertEquals(1, savedJobOffer.getId());
    }

    @Test
    void createJobOffer_withRequestBodyNotExistingContact_savesEntityAndCreatesContact() {
        JobOfferDto entityToSave = getJobOfferDto();

        MainTechnology mainTechnologyMock = getMainTechnology();

        when(mainTechnologyRepository.findFirstByName(
                entityToSave.getMainTechnology().getValue().toUpperCase()
        )).thenReturn(mainTechnologyMock);

        TypeOfWork typeOfWorkMock = getTypeOfWork();

        when(typeOfWorkRepository.findFirstByName(
                entityToSave.getTypeOfWork().getValue().toUpperCase()
        )).thenReturn(typeOfWorkMock);

        ExperienceLevel experienceMock = getExperience();

        when(experienceLevelRepository.findFirstByLevel(
                entityToSave.getExperienceLevel().getValue().toUpperCase()
        )).thenReturn(experienceMock);

        OperatingMode operatingModeMock = getOperatingMode();
        OfferOperatingMode offerOperatingModeMock = new OfferOperatingMode(1, operatingModeMock);

        when(operatingModeRepository.findFirstByName(
                entityToSave.getOperatingModes().get(0).getValue().toUpperCase())
        ).thenReturn(operatingModeMock);

        when(operatingModeMapper.toEntity(operatingModeMock))
                .thenReturn(offerOperatingModeMock);

        Skill skillMock = new Skill(494, "PYTHON");
        RequiredSkillDto requiredSkillDtoMock = new RequiredSkillDto(RequiredSkillDto.NameEnum.PYTHON, 5);
        RequiredSkill requiredSkillMock = new RequiredSkill(1, skillMock, requiredSkillDtoMock.getLevel());

        when(skillRepository.findFirstByName(
                entityToSave.getRequiredSkills().get(0).getName().getValue().toUpperCase())
        ).thenReturn(skillMock);

        when(requiredSkillMapper.toEntity(requiredSkillDtoMock, skillMock))
                .thenReturn(requiredSkillMock);

        Currency currencyMock = getCurrency();
        EmploymentType employmentTypeMock = getEmploymentType();
        Employment employmentMock = new Employment(1, 0, 0, employmentTypeMock, currencyMock);

        when(currencyRepository.findFirstByName(
                entityToSave.getEmployments().get(0).getCurrency().getValue().toUpperCase()
        )).thenReturn(currencyMock);

        when(employmentTypeRepository.findFirstByName(
                entityToSave.getEmployments().get(0).getType().getValue().toUpperCase()
        )).thenReturn(employmentTypeMock);

        when(employmentMapper.toEntity(
                entityToSave.getEmployments().get(0), currencyMock, employmentTypeMock
        )).thenReturn(employmentMock);

        when(contactRepository.findAllByPhoneAndEmail(
                entityToSave.getContact().getPhone(),
                entityToSave.getContact().getEmail()
        )).thenReturn(Optional.empty());

        Contact contactMock = getContact();

        when(contactMapper.toEntity(entityToSave.getContact())).thenReturn(contactMock);

        when(contactRepository.save(contactMock)).thenReturn(contactMock);

        JobOffer jobOfferMock = getJobOffer();

        when(jobOfferMapper.toEntity(
                entityToSave,
                mainTechnologyMock,
                typeOfWorkMock,
                experienceMock,
                List.of(offerOperatingModeMock),
                List.of(requiredSkillMock),
                List.of(employmentMock),
                contactMock
        )).thenReturn(jobOfferMock);

        when(jobOfferRepository.save(jobOfferMock)).thenReturn(jobOfferMock);


        JobOfferMetadataDto savedJobOffer = jobOfferService.createJobOffer(entityToSave);

        verify(contactRepository).save(contactMock);
        verify(jobOfferRepository).save(jobOfferMock);

        assertEquals(1, savedJobOffer.getId());
    }

    @Test
    void findOfferByFilters() {
        List<JobOffer> foundJobOffersMock = List.of(getJobOffer());

        when(criteriaRepository.findAllByFilters(
                null, null,
                "python", "permanent",
                "junior", null,
                null, "createdDate",
                "DESC", 1,
                10
        )).thenReturn(foundJobOffersMock);

        SimpleJobOfferDto simpleJobOfferMock = getSimpleJobOfferDto();
        when(jobOfferMapper.toSimpleDto(foundJobOffersMock.get(0))).thenReturn(simpleJobOfferMock);

        List<SimpleJobOfferDto> offerByFilters = jobOfferService.findOfferByFilters(null, null,
                "python", "permanent",
                "junior", null,
                null, "createdDate",
                "DESC", 1,
                10
        );

        verify(criteriaRepository).findAllByFilters(
                null, null,
                "python", "permanent",
                "junior", null,
                null, "createdDate",
                "DESC", 1,
                10
        );

        assertEquals("python", offerByFilters.get(0).getMainTechnology().getValue());
        assertEquals("permanent", offerByFilters.get(0).getEmployments().get(0).getType().getValue());
        assertTrue(offerByFilters.size() <= 10 && !offerByFilters.isEmpty());
    }

    @Test
    void findJobOfferById_withExistingId_returnsMatchingJobOffer() {
        Integer existingId = 1;

        JobOffer foundJobOffer = JobOffer.builder()
                .id(existingId)
                .build();

        FetchedJobOfferDto fetchedJobOfferDto = FetchedJobOfferDto.builder()
                .title("Senior java developer needed asap")
                .city("Warsaw")
                .build();

        when(jobOfferRepository.findById(existingId)).thenReturn(Optional.of(foundJobOffer));
        when(jobOfferMapper.toDto(foundJobOffer)).thenReturn(fetchedJobOfferDto);

        FetchedJobOfferDto actualJobOffer = jobOfferService.findJobOfferById(existingId);

        verify(jobOfferRepository).findById(existingId);

        assertEquals("Senior java developer needed asap", actualJobOffer.getTitle());
        assertEquals("Warsaw", actualJobOffer.getCity());
        assertInstanceOf(FetchedJobOfferDto.class, actualJobOffer);
    }

    @Test
    void findJobOfferById_withNotExistingId_throwsException() {
        Integer notExistingId = 321;

        when(jobOfferRepository.findById(notExistingId))
                .thenThrow(new JobOfferNotFoundException(
                        400,
                        "Job offer with id: " + notExistingId + " not found")
                );

        JobOfferNotFoundException exception = assertThrows(
                JobOfferNotFoundException.class,
                () -> jobOfferService.findJobOfferById(notExistingId)
        );

        verify(jobOfferRepository).findById(notExistingId);

        assertEquals("Job offer with id: 321 not found", exception.getMessage());
        assertEquals(400, exception.getCode());
    }

    private JobOfferDto getJobOfferDto() {
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

    private JobOffer getJobOffer() {
        return JobOffer.builder()
                .id(1)
                .title("Junior Python developer needed asap")
                .companyName("Some company name")
                .city("Some city")
                .street("Some street")
                .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit")
                .contact(getContact())
                .mainTechnology(getMainTechnology())
                .typeOfWork(getTypeOfWork())
                .experienceLevel(getExperience())
                .offerOperatingModes(List.of(
                        new OfferOperatingMode(1, getOperatingMode())
                ))
                .requiredSkills(List.of(new RequiredSkill(1, new Skill(494, "PYTHON"), 5)))
                .employments(List.of(new Employment(1, 0, 0, getEmploymentType(), getCurrency())))
                .build();
    }

    private SimpleJobOfferDto getSimpleJobOfferDto() {
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

    private TypeOfWork getTypeOfWork() {
        return new TypeOfWork(
                1,
                "FULL_TIME",
                "An employment in which workers work a minimum number of hours defined as such by their employer."
        );
    }

    private EmploymentType getEmploymentType() {
        return new EmploymentType(
                1,
                "PERMANENT",
                "Permanent employment is a long-term employment arrangement where an individual is hired " +
                        "without a predetermined end date, typically providing job security, benefits, and a stable income."
        );
    }

    private ExperienceLevel getExperience() {
        return new ExperienceLevel(
                1,
                "JUNIOR",
                "Entry-level position typically held by individuals with limited experience or fresh graduates."
        );
    }

    private OperatingMode getOperatingMode() {
        return new OperatingMode(
                3,
                "OFFICE",
                "Employees work primarily from the office location, adhering to a traditional in-office work setup."
        );
    }

    private Contact getContact() {
        return new Contact(
                1,
                "John",
                "Doe",
                "contac@email.com",
                "+48123456789"
        );
    }

    private MainTechnology getMainTechnology() {
        return new MainTechnology(5, "PYTHON");
    }

    private Currency getCurrency() {
        return new Currency(1, "PLN");
    }
}