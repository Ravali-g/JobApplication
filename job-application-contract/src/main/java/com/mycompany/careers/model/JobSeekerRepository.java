package com.mycompany.careers.model;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface JobSeekerRepository extends JpaRepository<JobSeeker,Integer> {

    @Query(value="select job_seeker_id from job_seeker where email = ? AND is_deleted = 0",nativeQuery = true)
    JobSeeker findByEmail(String email);




}
