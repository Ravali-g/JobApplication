package com.mycompany.careers.service;

import com.mycompany.careers.model.*;
import com.mycompany.careers.util.EmailSenderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.client.RestTemplate;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.mycompany.careers.constants.JobSeekerControllerJsonConstants.SAVE_JOB_APPLICATION_URL;

public class JobSeekerService {

    private static final Logger logger = LogManager.getLogger(JobSeekerService.class);

    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    @Autowired
    private JobSeeker jobSeeker;

    @Autowired
    private EmailSenderUtil emailSenderUtil;


    public JobSeekerService(JobSeekerRepository jobSeekerRepository){
        this.jobSeekerRepository = jobSeekerRepository;
    }

    /**
     * Stores the job seeker information in DB.
     * It then calls JobApplicationController to store application related information to DB
     * @param jobSeekerRequest
     * @return
     */
    public ResponseEntity submitApplication(JobSeekerRequestDto jobSeekerRequest){

        logger.debug("inside method submitApplication");

        JobSeeker jobSeeker = new JobSeeker();
        JobSeeker jobSeekerSaved = new JobSeeker();

        try{
        jobSeeker.setFullName(jobSeekerRequest.getFullName());
        jobSeeker.setJobId(jobSeekerRequest.getJobId());
        jobSeeker.setLocation(jobSeekerRequest.getLocation());
        jobSeeker.setEmail(jobSeekerRequest.getEmail());
        jobSeeker.setPhone(jobSeekerRequest.getPhone());
        jobSeeker.setResume(jobSeekerRequest.getResume().getBytes());
        jobSeeker.setJobAlerts(jobSeekerRequest.getJobAlerts());

        List<JobSeeker> jobSeekerFound = jobSeekerRepository.findByEmail(jobSeekerRequest.getEmail());
        logger.debug("JobSeekerRepository- findByEmail() called. jobseekerId="+ jobSeekerFound);

        if(jobSeekerFound.isEmpty()) {
             jobSeekerSaved = jobSeekerRepository.save(jobSeeker);
        }else{
            Iterator jobSeekerIterator = jobSeekerFound.listIterator();
            jobSeekerSaved = (JobSeeker) jobSeekerIterator.next();
        }
        jobSeeker.setJobSeekerId(jobSeekerSaved.getJobSeekerId());
        logger.debug("JobSeekerRepository- save() called:"+ jobSeekerSaved);

        ResponseEntity jobApplicationResponse = saveJobApplication(jobSeeker);
        logger.debug("saveJobApplication() called - "+ jobApplicationResponse);

        //Send confirmation email
        JobApplicationResponseDto  jobApplicationInfo = (JobApplicationResponseDto) jobApplicationResponse.getBody();
        emailSenderUtil.sendConfirmationEmail(jobSeeker.getEmail(),jobSeeker.getFullName(),jobApplicationInfo.getJobApplicationId());

        return new ResponseEntity<>(jobApplicationResponse,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity(new String("Error:"+e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Synchronous REST call to JobApplicationController to store information
     * @param jobSeeker
     * @return
     */
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
                    = new RestTemplate().postForEntity(SAVE_JOB_APPLICATION_URL, entity, JobApplication.class);

            logger.debug("postForEntity() called:"+ response);


            return new ResponseEntity<>(convertToResponseDto(response.getBody()),headers,HttpStatus.OK);
        }

        catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity(new String("Error"+ex.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * updates job seeker information in DB
     * @param jobSeekerRequest
     * @return
     */

    public ResponseEntity updateApplication(JobSeekerRequestDto jobSeekerRequest){

       try{
               JobSeeker jobSeeker = new JobSeeker();
               JobSeeker jobSeekerSaved;

           if(jobSeekerRequest.getEmail() == null){
               return new ResponseEntity(new String("Email cannot be null"), HttpStatus.NOT_ACCEPTABLE);
           }

               List<JobSeeker> jobSeekerFound = jobSeekerRepository.findByEmail(jobSeekerRequest.getEmail());
               logger.debug("JobSeekerRepository- findByEmail() called. jobseekerId="+ jobSeekerFound);

               if(jobSeekerFound == null) {
                   return new ResponseEntity(new String("Cannot find record with the given ID"), HttpStatus.NO_CONTENT);
               }else{
                   Iterator jobSeekerIterator = jobSeekerFound.listIterator();
                   JobSeeker jobSeekerResult = (JobSeeker) jobSeekerIterator.next();
                   jobSeeker.setJobSeekerId(jobSeekerResult.getJobSeekerId());
                   jobSeeker.setEmail(jobSeekerRequest.getEmail());

                   if (jobSeekerRequest.getFullName() != null) {
                       jobSeeker.setFullName(jobSeekerRequest.getFullName());
                   } else {
                       jobSeeker.setFullName(jobSeekerResult.getFullName());
                   }

                   if(jobSeekerRequest.getJobId() != 0){
                       jobSeeker.setJobId(jobSeekerRequest.getJobId());
                   }else{
                       jobSeeker.setJobId(jobSeekerResult.getJobId());
                   }

                   if(jobSeekerRequest.getLocation() != null){
                       jobSeeker.setLocation(jobSeekerRequest.getLocation());
                   }else{
                       jobSeeker.setLocation(jobSeekerResult.getLocation());
                   }

                   if(jobSeekerRequest.getPhone() != null){
                       jobSeeker.setPhone(jobSeekerRequest.getPhone());
                   }else{
                       jobSeeker.setPhone(jobSeekerResult.getPhone());
                   }

                   if(jobSeekerRequest.getResume() != null){
                       jobSeeker.setResume(jobSeekerRequest.getResume().getBytes());
                   }else{
                       jobSeeker.setResume(jobSeekerResult.getResume());
                   }

                   logger.debug("Job Alert = "+jobSeekerRequest.getJobAlerts());
                       jobSeeker.setJobAlerts(jobSeekerRequest.getJobAlerts());


                   jobSeekerSaved = jobSeekerRepository.save(jobSeeker);
                   logger.debug("update successful!"+jobSeekerSaved);
                   return new ResponseEntity(new String("Information updated for - " +jobSeekerSaved.getFullName() ), HttpStatus.OK);
               }

       }
       catch(Exception e){
           e.printStackTrace();
           return new ResponseEntity(new String("Error!"+e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
       }

    }


    public JobApplicationResponseDto convertToResponseDto(JobApplication jobApplicationResponse) {
        return new JobApplicationResponseDto(
                jobApplicationResponse.getJobSeeker().getJobSeekerId(),
                jobApplicationResponse.getJobApplicationId(),
                jobApplicationResponse.getJobId(),
                jobApplicationResponse.getDateApplied(),
                jobApplicationResponse.getJobSeeker().getFullName()
        );
    }



    public List<String> getSubscribedJobApplicants(){
        logger.debug("inside getSubscribedJobApplicants()");
        List<String> applicantList = new ArrayList<String>();
        try{
            applicantList = jobSeekerRepository.getListOfSubscribedApplicants();
        }catch(Exception e){
            e.printStackTrace();
        }
        logger.debug("Number of SubscribedJobApplicants = " + applicantList.size());
        return applicantList;
    }

}