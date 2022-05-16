package com.mycompany.careers.processor;

import com.mycompany.careers.model.JobSeekerRequestDto;
import org.springframework.http.ResponseEntity;

public interface JobSeekerRequestProcessor {

    ResponseEntity<?> submitApplication(JobSeekerRequestDto jobSeekerRequest);
    ResponseEntity<?> updateJobApplication(JobSeekerRequestDto jobSeekerRequest);

}
