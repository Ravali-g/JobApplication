package com.mycompany.careers.service;

import com.mycompany.careers.model.*;
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

    /**
     * Saves job application information to DB
     * @param jobApplicationRequestDto
     * @return
     */

    public ResponseEntity submitApplication(JobApplicationRequestDto jobApplicationRequestDto){

        JobApplication jobApplication = new JobApplication();
        JobApplication savedJobApplication = null;
        jobApplication.setDateApplied(jobApplicationRequestDto.getAppliedDate());
        jobApplication.setJobId(jobApplicationRequestDto.getJobId());
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

    /**
     * Soft deletes a job application, if exists
     * @param jobId
     * @param jobSeekerId
     * @return
     */
    public ResponseEntity withdrawApplication(int jobId, int jobSeekerId){

        logger.debug("inside withdrawApplication() method. JobId="+jobId + "jobSeekerId="+jobSeekerId);
        try{
            JobApplication jobApplicationExists = jobApplicationRepository.checkIfApplicationExists(jobId,jobSeekerId);
            logger.debug("Job Application Exists?:"+jobApplicationExists);

            if(jobApplicationExists == null){
                return new ResponseEntity<>(new String("No Job Application found"),HttpStatus.NOT_FOUND);
            }

            jobApplicationRepository.withdrawApplication(jobId,jobSeekerId);
            logger.debug("withdrawal successful!");
            return new ResponseEntity<>(new String("withdrawal successful!"),HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(new String("Error!"+e.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Get the list of active job seekers for a job
     * @param jobId
     * @return
     */

    public ResponseEntity getJobSeekersForJobId(int jobId){

        List<Object> jobSeekersList;

        try{
             jobSeekersList = jobApplicationRepository.getSeekersforJobId(jobId);
            if(jobSeekersList.isEmpty()){
                return new ResponseEntity(new String("No Applicants found for the job ID: "+ jobId),HttpStatus.NO_CONTENT);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity(new String("Error!"+ex.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(jobSeekersList,HttpStatus.OK);

    }

    /**
     * Delete all the job seekers for a given job_id
     * @param jobId
     * @return
     */
    public ResponseEntity deleteJobSeekersForJobId(int jobId){
        logger.debug("inside deleteJobSeekersForJobId");
        try{
            jobApplicationRepository.deleteJobSeekersForJobId(jobId);
            logger.debug("safe delete records for the job ID: "+jobId+" done");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity(new String("Error: "+e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
