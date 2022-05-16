package com.mycompany.careers.util;

import com.mycompany.careers.service.JobSeekerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobAlertConsumer {

    @Autowired
    private JobSeekerService jobSeekerService;

    @Autowired
    private EmailSenderUtil emailSenderUtil;
    private static final Logger logger = LogManager.getLogger(JobAlertConsumer.class);

    @RabbitListener(queues = {"${queue.name}"})
    public void receive(@Payload String jobs) {

        logger.debug("inside JobAlertConsumer - receive() - Consumed Message - " + jobs);
        List<String> jobApplicantList = jobSeekerService.getSubscribedJobApplicants();
        logger.debug("inside JobAlertConsumer. Count of alert subscribers - " + jobApplicantList.size());

        try{
            logger.debug("inside JobAlertConsumer: Sending email ");
            emailSenderUtil.sendJobAlertEmail(jobApplicantList,jobs);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
