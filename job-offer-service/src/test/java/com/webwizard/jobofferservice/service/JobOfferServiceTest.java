package com.webwizard.jobofferservice.service;

import com.webwizard.jobofferservice.exception.JobOfferNotFoundException;
import com.webwizard.jobofferservice.mapper.*;
import com.webwizard.jobofferservice.model.Currency;
import com.webwizard.jobofferservice.model.*;
import com.webwizard.jobofferservice.model.message.Message;
import com.webwizard.jobofferservice.openapi.v1.model.*;
import com.webwizard.jobofferservice.repository.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static com.webwizard.jobofferservice.testUtils.TestUtils.*;
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
    private MessageRepository messageRepository;
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
    private MessageMapper messageMapper;
    @Mock
    private JobOfferCriteriaRepository criteriaRepository;

    @InjectMocks
    private JobOfferService jobOfferService;

    private JobOffer jobOfferMock;
    private JobOfferDto entityToSave;
    private MainTechnology mainTechnologyMock;
    private TypeOfWork typeOfWorkMock;
    private ExperienceLevel experienceMock;
    private OperatingMode operatingModeMock;
    private OfferOperatingMode offerOperatingModeMock;

    @BeforeEach
    void setUp() {
        jobOfferMock = getJobOffer(1, "java", "full time");
        entityToSave = getJobOfferDto();
        mainTechnologyMock = getMainTechnology("java");
        typeOfWorkMock = getTypeOfWork("full time");
        experienceMock = getExperience();
        operatingModeMock = getOperatingMode();
        offerOperatingModeMock = new OfferOperatingMode(1, operatingModeMock);
    }

    @Test
    void createJobOffer_withRequestBodyAndExistingContact_savesEntity() {

        when(mainTechnologyRepository.findFirstByName(
                entityToSave.getMainTechnology().getValue().toUpperCase()
        )).thenReturn(mainTechnologyMock);

        when(typeOfWorkRepository.findFirstByName(
                entityToSave.getTypeOfWork().getValue().toUpperCase()
        )).thenReturn(typeOfWorkMock);

        when(experienceLevelRepository.findFirstByLevel(
                entityToSave.getExperienceLevel().getValue().toUpperCase()
        )).thenReturn(experienceMock);

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

        when(contactRepository.findAllByPhoneAndEmailAndName(
                entityToSave.getContact().getPhone(),
                entityToSave.getContact().getEmail(),
                entityToSave.getContact().getName()
        )).thenReturn(contactMock);

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

        Message messageMock = getMessage();

        when(messageMapper.toEntity(jobOfferMock)).thenReturn(messageMock);

        JobOfferMetadataDto savedJobOffer = jobOfferService.createJobOffer(entityToSave);

        verify(messageRepository).save(messageMock);
        verify(jobOfferRepository).save(jobOfferMock);
        assertEquals(1, savedJobOffer.getId());
    }

    @Test
    void createJobOffer_withRequestBodyNotExistingContact_savesEntityAndCreatesContact() {

        when(mainTechnologyRepository.findFirstByName(
                entityToSave.getMainTechnology().getValue().toUpperCase()
        )).thenReturn(mainTechnologyMock);

        when(typeOfWorkRepository.findFirstByName(
                entityToSave.getTypeOfWork().getValue().toUpperCase()
        )).thenReturn(typeOfWorkMock);

        when(experienceLevelRepository.findFirstByLevel(
                entityToSave.getExperienceLevel().getValue().toUpperCase()
        )).thenReturn(experienceMock);

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

        when(contactRepository.findAllByPhoneAndEmailAndName(
                entityToSave.getContact().getPhone(),
                entityToSave.getContact().getEmail(),
                entityToSave.getContact().getName()
        )).thenReturn(Optional.empty());

        Contact contactMock = getContact();

        when(contactMapper.toEntity(entityToSave.getContact())).thenReturn(contactMock);

        when(contactRepository.save(contactMock)).thenReturn(contactMock);

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

        Message messageMock = getMessage();

        when(messageMapper.toEntity(jobOfferMock)).thenReturn(messageMock);

        JobOfferMetadataDto savedJobOffer = jobOfferService.createJobOffer(entityToSave);

        verify(messageRepository).save(messageMock);
        verify(contactRepository).save(contactMock);
        verify(jobOfferRepository).save(jobOfferMock);

        assertEquals(1, savedJobOffer.getId());
    }

    @Test
    void findOfferByFilters() {
        List<JobOffer> foundJobOffersMock = List.of(jobOfferMock);

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

        List<SimpleJobOfferDto> offerByFilters = jobOfferService.findOfferByFilters(
                null, null,
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

        FetchedJobOfferDto fetchedJobOfferDto = getFetchedJobOfferDto();

        when(jobOfferRepository.findById(existingId)).thenReturn(Optional.of(jobOfferMock));
        when(jobOfferMapper.toDto(jobOfferMock)).thenReturn(fetchedJobOfferDto);

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
}