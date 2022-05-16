package com.mycompany.careers.controller;

import com.mycompany.careers.model.JobSeekerRequestDto;
import com.mycompany.careers.service.JobSeekerRequestProcessorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.mycompany.careers.constants.JobSeekerControllerJsonConstants.*;

/**
 * Controller that receives the REST requests to create, delete, update and get information about Job Seeker
 */

@RestController
@RequestMapping(path="/careers/seeker")
public class JobSeekerController {
    private static final Logger logger = LogManager.getLogger(JobSeekerController.class);
    private final JobSeekerRequestProcessorImpl jobSeekerRequestProcessorImpl;

    @Autowired
    public JobSeekerController(JobSeekerRequestProcessorImpl jobSeekerRequestProcessorImpl){
        this.jobSeekerRequestProcessorImpl = jobSeekerRequestProcessorImpl;
    }

    /**
     *
     * Apply to a job.
     * This saves the job seeker details in DB and then invokes JobApplicationController to save information regarding the application
     * @param jobSeekerRequest
     * @return
     */
    @PostMapping (consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> applyToJob(@ModelAttribute JobSeekerRequestDto jobSeekerRequest){
        logger.debug("inside method:applyToJob");
        return jobSeekerRequestProcessorImpl.submitApplication(jobSeekerRequest);
    }

    /**
     * Updates the job seeker information in DB
     * @param jobSeekerRequest
     * @return
     */
    @PutMapping (consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateJobApplication(@ModelAttribute JobSeekerRequestDto jobSeekerRequest){
        logger.debug("inside method:updateJobApplication" + jobSeekerRequest.toString());
        return jobSeekerRequestProcessorImpl.updateJobApplication(jobSeekerRequest);
    }


}
