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
 * Controller that receives the REST requests for all actions on Job Seeker module
 */

@RestController
@RequestMapping(path="/careers/seeker")
public class JobSeekerController {

    private static final Logger logger = LogManager.getLogger(JobSeekerController.class);

    @Autowired
    private final JobSeekerRequestProcessorImpl jobSeekerRequestProcessorImpl;

    public JobSeekerController(JobSeekerRequestProcessorImpl jobSeekerRequestProcessorImpl){
        this.jobSeekerRequestProcessorImpl = jobSeekerRequestProcessorImpl;
    }

    /**
     *
     * @param
     * @param jobSeekerRequest
     * @return
     */
    @PostMapping (path="{"+APPLY+"}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> applyToJob(@ModelAttribute JobSeekerRequestDto jobSeekerRequest){
        logger.debug("inside method:applyToJob");
        return jobSeekerRequestProcessorImpl.submitApplication(jobSeekerRequest);
    }




}
