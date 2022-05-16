package com.mycompany.careers.config;

import com.mycompany.careers.model.JobApplication;
import com.mycompany.careers.model.JobApplicationRepository;
import com.mycompany.careers.service.JobApplicationRequestProcessorImpl;
import com.mycompany.careers.service.JobApplicationRequestService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobApplicationConfiguration {

    private JobApplicationRepository jobApplicationRepository;

    @Bean
    JobApplicationRequestService jobApplicationRequestService(){
        return new JobApplicationRequestService( jobApplicationRepository);
    }

    @Bean
    JobApplicationRequestProcessorImpl JobApplicationRequestProcessorImpl(){
        return new JobApplicationRequestProcessorImpl(jobApplicationRequestService());
    }

    @Bean
    JobApplication jobApplication(){
        return new JobApplication();
    }
    }

