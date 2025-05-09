package com.embrakX.Jobms.job.service;


import com.embrakX.Jobms.job.dto.JobwithCompanyDto;
import com.embrakX.Jobms.job.entity.Job;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JobService {

    List<JobwithCompanyDto> findAll();

     Job createJob(Job job);

     Job getJobById(Long id);

     String deleteById(Long id);

     Job UpdateJobById(Long id, Job updateJob);
}
