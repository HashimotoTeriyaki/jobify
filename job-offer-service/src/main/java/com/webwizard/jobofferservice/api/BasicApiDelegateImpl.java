package com.webwizard.jobofferservice.api;

import com.webwizard.jobofferservice.openapi.v1.BasicApiDelegate;
import com.webwizard.jobofferservice.openapi.v1.model.*;
import com.webwizard.jobofferservice.service.JobOfferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class BasicApiDelegateImpl implements BasicApiDelegate {

    private final JobOfferService jobOfferService;

    @Override
    public ResponseEntity<JobOfferMetadataDto> createJobOffer(JobOfferDto jobOfferDto) {
        var jobOfferMetaData = jobOfferService.createJobOffer(jobOfferDto);
        return ResponseEntity
                .created(URI.create("job-offer/" + jobOfferMetaData.getId()))
                .body(jobOfferMetaData);
    }

    @Override
    public ResponseEntity<List<SimpleJobOfferDto>> findJobOffers(
            Integer salaryMin, Integer salaryMax,
            String technology, String employmentType,
            String experience, String operatingMode,
            String typeOfWork, String orderBy,
            String sortBy, Integer page,
            Integer pageSize
    ) {
        List<SimpleJobOfferDto> jobOffers = jobOfferService.findOfferByFilters(
                salaryMin, salaryMax,
                technology, employmentType,
                experience, operatingMode,
                typeOfWork, orderBy,
                sortBy, page,
                pageSize
        );
        return ResponseEntity
                .ok()
                .body(jobOffers);
    }

    @Override
    public ResponseEntity<FetchedJobOfferDto> findJobOffer(Integer id) {
        FetchedJobOfferDto fetchedJobOfferDto = jobOfferService.findJobOfferById(id);
        return ResponseEntity
                .ok()
                .body(fetchedJobOfferDto);
    }
}
