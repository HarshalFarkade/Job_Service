package com.embrakX.jobms.job.service;

import com.embrakX.jobms.job.entity.Job;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JobService {

    List<Job> findAll();

     Job createJob(Job job);

     Job getJobById(Long id);

     String deleteById(Long id);

     Job UpdateJobById(Long id, Job updateJob);
}
