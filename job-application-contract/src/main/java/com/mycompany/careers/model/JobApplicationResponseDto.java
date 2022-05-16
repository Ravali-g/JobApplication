package com.mycompany.careers.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.io.Serializable;
import java.time.LocalDate;

public class JobApplicationResponseDto implements Serializable {


    private static final String JOB_SEEKER_ID = "job_seeker_id";
    private static final String JOB_APPLICATION_ID = "job_application_id";
    private static final String DATE_APPLIED = "date_applied";
    private static final String JOB_ID = "job_id";
    private static final String LOCAL_DATE_PATTERN = "mm-dd-yyyy";
    private static final String FULL_NAME = "full_name";

    private static final String JOB_TITLE = "job_title";

    @JsonProperty(JOB_SEEKER_ID)
    private final Integer jobSeekerId;

    @JsonProperty(JOB_APPLICATION_ID)
    private final Integer jobApplicationId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LOCAL_DATE_PATTERN)
    @JsonProperty(DATE_APPLIED)
    private final String dateApplied;

    @JsonProperty(JOB_ID)
    private final Integer jobId;

    @JsonProperty(FULL_NAME)
    private final String fullName;


    @JsonCreator
    public JobApplicationResponseDto(
            @JsonProperty(value = JOB_SEEKER_ID) Integer jobSeekerId,
            @JsonProperty(value = JOB_APPLICATION_ID) Integer jobApplicationId,
            @JsonProperty(value = JOB_ID) Integer jobId,
            @JsonProperty(value = DATE_APPLIED) String dateApplied,
            @JsonProperty(value = FULL_NAME) String fullName) {

        this.jobSeekerId = jobSeekerId;
        this.jobApplicationId = jobApplicationId;
        this.jobId = jobId;
        this.dateApplied = dateApplied;
        this.fullName = fullName;

    }


    public Integer getJobSeekerId() {
        return jobSeekerId;
    }

    public Integer getJobApplicationId() {
        return jobApplicationId;
    }

    public String getDateApplied() {
        return dateApplied;
    }

    public Integer getJobId() {
        return jobId;
    }

    public String getFullName() {
        return fullName;
    }




}
