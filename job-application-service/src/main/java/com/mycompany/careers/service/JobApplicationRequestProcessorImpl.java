package com.mycompany.careers.service;

import com.mycompany.careers.model.JobApplicationRequestDto;
import com.mycompany.careers.processor.JobApplicationRequestProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class JobApplicationRequestProcessorImpl implements JobApplicationRequestProcessor {

    @Autowired
    JobApplicationRequestService jobApplicationRequestService;

    public JobApplicationRequestProcessorImpl(JobApplicationRequestService jobApplicationRequestService){
        this.jobApplicationRequestService = jobApplicationRequestService;
    }

    @Override
    public ResponseEntity<?> storeJobApplication(final JobApplicationRequestDto jobApplicationRequestDto){
         return jobApplicationRequestService.submitApplication(jobApplicationRequestDto);

    }


    @Override
    public ResponseEntity<?> withdrawApplication(int jobId, int jobSeekerId){
        return jobApplicationRequestService.withdrawApplication(jobId, jobSeekerId);

    }
    @Override
    public ResponseEntity getJobSeekersForJobId( int jobId){
        return jobApplicationRequestService.getJobSeekersForJobId(jobId);

    }

}
