package com.mycompany.careers.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity

public class JobSeeker implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="JobSeekerId", insertable = true, updatable = false, nullable = false)
    private int jobSeekerId;

    @Basic
    @Column(name = "FullName", insertable = true, updatable = true, nullable = false)
    private String fullName;

    @Basic
    @Column(name = "Email", insertable = true, updatable = true, nullable = false)
    private String email;

    @Basic
    @Column(name = "Phone", insertable = true, updatable = true, nullable = false)
    private String phone;

    @Basic
    @Column(name = "Location", insertable = true, updatable = true, nullable = false)
    private String location;


    private int jobId;

    @Lob
    @Basic
    @Column(name = "Resume", insertable = true, updatable = true, nullable = false)
    private byte[] resume;

    @Basic
    @Column(name = "IsDeleted", insertable = true, updatable = true, nullable = false)
    private Boolean isDeleted = Boolean.FALSE;

    @Basic
    @Column(name = "jobAlerts", insertable = true, updatable = true, nullable = true)
    private Boolean jobAlerts = Boolean.TRUE;

    @OneToOne (mappedBy = "jobSeeker")
    private JobApplication jobApplication;


    public JobApplication getJobApplication() {
        return jobApplication;
    }

    public void setJobApplication(JobApplication jobApplication) {
        this.jobApplication = jobApplication;
    }

    public int getJobSeekerId() {

        return jobSeekerId;
    }

    public void setJobSeekerId(int jobSeekerId) {

        this.jobSeekerId = jobSeekerId;
    }

    public String getFullName() {

        return fullName;
    }

    public void setFullName(String fullName) {

        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public byte[] getResume() {
        return resume;
    }

    public void setResume(byte[] resume) {
        this.resume = resume;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getJobAlerts() {
        return jobAlerts;
    }

    public void setJobAlerts(Boolean jobAlerts) {
        this.jobAlerts = jobAlerts;
    }

    @Override
    public String toString() {
        return "JobSeeker{" +
                "jobSeekerId=" + jobSeekerId +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", location='" + location + '\'' +
                ", jobId=" + jobId +
                ", isDeleted=" + isDeleted +
                ", jobAlerts=" + jobAlerts +
                '}';
    }
}
