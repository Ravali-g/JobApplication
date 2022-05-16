package com.mycompany.careers.service;

import com.mycompany.careers.model.JobApplication;
import com.mycompany.careers.model.JobApplicationRepository;
import com.mycompany.careers.model.JobApplicationRequestDto;
import com.mycompany.careers.model.JobSeeker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class JobApplicationRequestService {
    private static final Logger logger = LogManager.getLogger(JobApplicationRequestService.class);

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    public JobApplicationRequestService(JobApplicationRepository jobApplicationRepository){
        this.jobApplicationRepository = jobApplicationRepository;

    }

    public ResponseEntity submitApplication(JobApplicationRequestDto jobApplicationRequestDto){

        JobApplication jobApplication = new JobApplication();
        JobApplication savedJobApplication = null;
        jobApplication.setDateApplied(jobApplicationRequestDto.getAppliedDate());
        jobApplication.setJobId(jobApplicationRequestDto.getJobId());
        //jobApplication.setJobSeekerId(jobApplicationRequestDto.getJobSeekerId());
        jobApplication.setJobSeeker(jobApplicationRequestDto.getJobSeeker());

        logger.debug("jobApplicationRepository: save() called for Job ID"+jobApplicationRequestDto.getJobId() + "and Job SeekerId"+jobApplicationRequestDto.getJobSeeker());
        try {
             savedJobApplication = jobApplicationRepository.save(jobApplication);
        }

        catch(Exception e){
            return new ResponseEntity("Encountered an error:"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.debug("Job Application submission status: "+savedJobApplication);
        return new ResponseEntity<>(savedJobApplication,HttpStatus.OK);
    }

    public ResponseEntity withdrawApplication(int jobId, int jobSeekerId){

        logger.debug("inside withdrawApplication() method. JobId="+jobId + "jobSeekerId="+jobSeekerId);
        try{
            JobApplication jobApplicationExists = jobApplicationRepository.checkIfApplicationExists(jobId,jobSeekerId);

            logger.debug("Job Application Exists:"+jobApplicationExists);

            if(jobApplicationExists == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            jobApplicationRepository.withdrawApplication(jobId,jobSeekerId);
            logger.debug("withdrawal successful!");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    public ResponseEntity getJobSeekersForJobId(int jobId){

        List<Object> jobSeekersList;

        try{
             jobSeekersList = jobApplicationRepository.getSeekersforJobId(jobId);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(jobSeekersList,HttpStatus.OK);

    }

}
