package com.mycompany.careers.model;

import org.springframework.web.multipart.MultipartFile;

public class JobApplicationRequestDto {

    private MultipartFile resume;
    private int jobId;
    private int jobSeekerId;
    private String appliedDate;

    private JobSeeker jobSeeker;

    public MultipartFile getResume() {
        return resume;
    }

    public void setResume(MultipartFile resume) {
        this.resume = resume;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getJobSeekerId() {
        return jobSeekerId;
    }

    public void setJobSeekerId(int jobSeekerId) {
        this.jobSeekerId = jobSeekerId;
    }

    public String getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(String appliedDate) {
        this.appliedDate = appliedDate;
    }

    public JobSeeker getJobSeeker() {
        return jobSeeker;
    }

    public void setJobSeeker(JobSeeker jobSeeker) {
        this.jobSeeker = jobSeeker;
    }

    @Override
    public String toString() {
        return "JobApplicationRequestDto{" +
                "resume=" + resume +
                ", jobId=" + jobId +
                ", jobSeekerId=" + jobSeekerId +
                ", appliedDate='" + appliedDate + '\'' +
                ", jobSeeker=" + jobSeeker +
                '}';
    }

}
