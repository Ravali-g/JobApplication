package com.mycompany.careers.processor;

import com.mycompany.careers.model.JobApplicationRequestDto;
import org.springframework.http.ResponseEntity;

public interface JobApplicationRequestProcessor {

    ResponseEntity<?> storeJobApplication(final JobApplicationRequestDto jobApplicationRequestDto);
    ResponseEntity<?> withdrawApplication(final int jobId, final int jobSeekerId);
    ResponseEntity<?> getJobSeekersForJobId(final int jobId);
    ResponseEntity<?> deleteJobSeekersForJobId(final int jobId);

}
