package com.mycompany.careers.controller;

import com.mycompany.careers.model.JobApplicationRequestDto;
import com.mycompany.careers.service.JobApplicationRequestProcessorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.mycompany.careers.constants.JobApplicationJsonConstants.STORE;
import static com.mycompany.careers.constants.JobSeekerControllerJsonConstants.*;
import static com.mycompany.careers.constants.JobSeekerControllerJsonConstants.SEEKER_ID;


    /**
     * Controller that receives the REST requests to create, view, update and delete information about job application
     */

    @RestController
    @RequestMapping(path="/careers/application")
    public class JobApplicationController
    {
        private static final Logger logger = LogManager.getLogger(JobSeekerController.class);
        public JobApplicationRequestProcessorImpl jobApplicationRequestProcessorImpl;

        @Autowired
        public JobApplicationController(JobApplicationRequestProcessorImpl jobApplicationRequestProcessorImpl) {
            this.jobApplicationRequestProcessorImpl = jobApplicationRequestProcessorImpl;
        }

        /**
         * Stores the job application in DB. This method is not exposed by the API.
         * This is called from JobSeekerController after saving the JobSeeker information successfully
         *
         * @param jobApplicationRequest
         * @return
         */
        @PostMapping(path="/"+STORE,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.ALL_VALUE)
        public ResponseEntity<?> storeJobApplication(@RequestBody JobApplicationRequestDto jobApplicationRequest){
            return jobApplicationRequestProcessorImpl.storeJobApplication(jobApplicationRequest);
        }

        /**
         * Soft deletes the application of a job seeker for a given job_id
         * @param jobId
         * @param jobSeekerId
         * @return
         */
        @DeleteMapping (path="/{"+JOB_ID+"}"+"/"+USER+"/{"+SEEKER_ID+"}")
        public ResponseEntity<?> withdrawApplication(@PathVariable(JOB_ID) int jobId, @PathVariable(SEEKER_ID) int jobSeekerId){
            logger.debug("inside method:withdrawApplication");
            return jobApplicationRequestProcessorImpl.withdrawApplication(jobId,jobSeekerId);
        }

        /**
         * Get the list of active applicants for the job_id
         * @param jobId
         * @return
         */
        @GetMapping (path="/{"+JOB_ID+"}", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> getJobSeekersForJobId(@PathVariable(JOB_ID) int jobId){
            logger.debug("inside method:getJobSeekersForJobId");
            return jobApplicationRequestProcessorImpl.getJobSeekersForJobId(jobId);
        }

        /**
         * Deletes all the active applicants for a job_id
         * @param jobId
         * @return
         */
        @DeleteMapping (path="/{"+JOB_ID+"}", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> deleteJobSeekersForJobId(@PathVariable(JOB_ID) int jobId){
            logger.debug("inside method:deleteJobSeekersForJobId - jobID="+jobId);
            return jobApplicationRequestProcessorImpl.deleteJobSeekersForJobId(jobId);
        }


    }


