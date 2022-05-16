package com.mycompany.careers.config;

import com.mycompany.careers.model.JobSeeker;
import com.mycompany.careers.model.JobSeekerRepository;
import com.mycompany.careers.service.JobSeekerRequestProcessorImpl;
import com.mycompany.careers.service.JobSeekerService;
import com.mycompany.careers.util.EmailSenderUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
@Configuration
public class JobSeekerConfiguration {

    private JobSeekerRepository jobSeekerRepository;



    @Bean
    JobSeekerService jobSeekerService(){

        return new JobSeekerService(jobSeekerRepository);
    }

    @Bean
    JobSeekerRequestProcessorImpl jobSeekerRequestProcessorImpl(){

        return new JobSeekerRequestProcessorImpl(jobSeekerService());
    }

    @Bean
    JobSeeker jobSeeker(){

            return new JobSeeker();
    }


    @Bean
    EmailSenderUtil emailSenderUtil(){

        return new EmailSenderUtil();
    }

}
