package com.mycompany.careers.model;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface JobSeekerRepository extends JpaRepository<JobSeeker,Integer> {

    @Query(value="select * from job_seeker where email = ? AND is_deleted = 0",nativeQuery = true)
    List<JobSeeker> findByEmail(String email);

    @Query(value="select email from job_seeker where job_alerts = 1",nativeQuery = true)
    List<String> getListOfSubscribedApplicants();

}
