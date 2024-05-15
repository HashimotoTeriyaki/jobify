package com.webwizard.jobofferservice.api;

import com.webwizard.jobofferservice.openapi.v1.BasicApiDelegate;
import com.webwizard.jobofferservice.openapi.v1.model.*;
import com.webwizard.jobofferservice.service.JobOfferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
@RequiredArgsConstructor
@Slf4j
public class BasicApiDelegateImpl implements BasicApiDelegate {

    private final JobOfferService jobOfferService;

    @Override
    public ResponseEntity<JobOfferMetadataDto> createJobOffer(JobOfferDto jobOfferDto) {
        var jobOfferMetaData = jobOfferService.createJobOffer(jobOfferDto);
        log.info("New job offer created! id:{}", jobOfferMetaData.getId());
        return ResponseEntity
                .created(URI.create("job-offer/" + jobOfferMetaData.getId()))
                .body(jobOfferMetaData);
    }
}
