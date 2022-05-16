package com.mycompany.careers.service;

import com.mycompany.careers.model.JobSeekerRequestDto;
import com.mycompany.careers.processor.JobSeekerRequestProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class JobSeekerRequestProcessorImpl implements JobSeekerRequestProcessor {

    @Autowired
    private JobSeekerService jobSeekerService;

    public JobSeekerRequestProcessorImpl(JobSeekerService jobSeekerService){
        this.jobSeekerService = jobSeekerService;
    }

    @Override
    public ResponseEntity submitApplication( JobSeekerRequestDto jobSeekerRequest){
        return jobSeekerService.submitApplication(jobSeekerRequest);

    }

    @Override
    public ResponseEntity updateJobApplication( JobSeekerRequestDto jobSeekerRequest){
        return jobSeekerService.updateApplication(jobSeekerRequest);

    }

}
