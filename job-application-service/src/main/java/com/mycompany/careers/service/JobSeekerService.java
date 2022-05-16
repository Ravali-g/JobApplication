package com.mycompany.careers.service;

import com.mycompany.careers.controller.JobSeekerController;
import com.mycompany.careers.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class JobSeekerService {

    private static final Logger logger = LogManager.getLogger(JobSeekerService.class);

    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    @Autowired
    private JobSeeker jobSeeker;

    public JobSeekerService(JobSeekerRepository jobSeekerRepository){
        this.jobSeekerRepository = jobSeekerRepository;
    }

    public ResponseEntity submitApplication(JobSeekerRequestDto jobSeekerRequest){

        logger.debug("inside method submitApplication");

        JobSeeker jobSeeker = new JobSeeker();
        //JobSeeker jobSeekerSaved = new JobSeeker();

        try{

        jobSeeker.setFullName(jobSeekerRequest.getFullName());
        jobSeeker.setJobId(jobSeekerRequest.getJobId());
        jobSeeker.setLocation(jobSeekerRequest.getLocation());
        jobSeeker.setEmail(jobSeekerRequest.getEmail());
        jobSeeker.setPhone(jobSeekerRequest.getPhone());
        jobSeeker.setResume(jobSeekerRequest.getResume().getBytes());

            JobSeeker jobSeekerSaved = jobSeekerRepository.findByEmail(jobSeekerRequest.getEmail());
        logger.debug("JobSeekerRepository- findByEmail() called. jobseekerId="+ jobSeekerSaved);

        if(jobSeekerSaved == null) {
             jobSeekerSaved = jobSeekerRepository.save(jobSeeker);
            //jobSeeker.setJobSeekerId(jobSeekerSaved.getJobSeekerId());
        }//else{
           // jobSeeker.setJobSeekerId(jobSeekerId);
        //}
        logger.debug("JobSeekerRepository- save() called:"+ jobSeeker);

            ResponseEntity jobApplication = saveJobApplication(jobSeekerSaved);
            logger.debug("saveJobApplication() called - "+ jobApplication);
            return new ResponseEntity<>(jobApplication,HttpStatus.OK);

        }
        catch(Exception e){
            return new ResponseEntity(jobSeeker, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private ResponseEntity saveJobApplication(JobSeeker jobSeeker){

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(date);

        JobApplicationRequestDto jobApplication = new JobApplicationRequestDto();
        jobApplication.setAppliedDate(strDate);
        jobApplication.setJobSeekerId(jobSeeker.getJobSeekerId());
        jobApplication.setJobId(jobSeeker.getJobId());
        jobApplication.setJobSeeker(jobSeeker);

        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity entity = new HttpEntity<>(jobApplication, headers);

            ResponseEntity<JobApplication> response
                    = new RestTemplate().postForEntity(
                    "http://localhost:8080/careers/application/store", entity,
                    JobApplication.class);
            logger.debug("postForEntity() called:"+ jobApplication);
            return response;
        }

        catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity(jobApplication, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
