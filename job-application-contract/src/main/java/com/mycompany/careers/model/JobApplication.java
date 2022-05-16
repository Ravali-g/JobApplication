package com.mycompany.careers.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class JobApplication implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="JobApplicationId", insertable = true, updatable = false, nullable = false)
    private int jobApplicationId;

    @Basic
    @Column(name = "dateApplied", insertable = true, updatable = true, nullable = false)
    private String dateApplied;

    @Basic
    @Column(name = "IsDeleted", insertable = true, updatable = true, nullable = false)
    private Boolean deleted = Boolean.FALSE;

    @Basic
    @Column(name = "JobId", insertable = true, updatable = true, nullable = false)
    private int jobId;

    @OneToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="jobSeekerId", referencedColumnName ="jobSeekerId", nullable = false)
    private JobSeeker jobSeeker;

    /*@Basic
    @Column(name = "JobSeekerId", insertable = true, updatable = true, nullable = false)
    private int jobSeekerId;

    public int getJobSeekerId() {
        return jobSeekerId;
    }

    public void setJobSeekerId(int jobSeekerId) {
        this.jobSeekerId = jobSeekerId;
    }
*/
    public JobSeeker getJobSeeker() {
        return jobSeeker;
    }

    public void setJobSeeker(JobSeeker jobSeeker) {
        this.jobSeeker = jobSeeker;
    }

    public int getJobApplicationId() {
        return jobApplicationId;
    }

    public void setJobApplicationId(int jobApplicationId) {
        this.jobApplicationId = jobApplicationId;
    }

    public String getDateApplied() {
        return dateApplied;
    }

    public void setDateApplied(String dateApplied) {
        this.dateApplied = dateApplied;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    @Override
    public String toString() {
        return "JobApplication{" +
                "jobApplicationId=" + jobApplicationId +
                ", dateApplied='" + dateApplied + '\'' +
                ", deleted=" + deleted +
                ", jobId='" + jobId + '\'' +
                '}';
    }
}
