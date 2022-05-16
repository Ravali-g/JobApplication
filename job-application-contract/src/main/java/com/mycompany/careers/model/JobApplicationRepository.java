package com.mycompany.careers.model;


import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Integer> {

    @Query(value="UPDATE job_application SET is_deleted=1 where job_id = ? AND job_seeker_id = ?",nativeQuery = true)
    @Modifying
    void withdrawApplication(int jobId, int jobSeekerId);

    @Query(value = "select * from job_application where job_id = ? AND job_seeker_id = ? AND is_deleted=0", nativeQuery = true)
    JobApplication checkIfApplicationExists(int jobId, int jobSeekerId);

    @Query(value="select js.job_seeker_id, js.full_name, ja.date_applied from job_application ja INNER JOIN job_seeker js ON (ja.job_seeker_id = js.job_seeker_id) AND js.is_deleted = 0 AND ja.is_deleted = 0 WHERE ja.job_id = ?",nativeQuery = true)
    List<Object> getSeekersforJobId(int jobId);
}
