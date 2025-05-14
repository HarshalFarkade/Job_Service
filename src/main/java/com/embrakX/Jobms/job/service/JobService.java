package com.embrakX.Jobms.job.service;


import com.embrakX.Jobms.job.dto.JobDto;
import com.embrakX.Jobms.job.entity.Job;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JobService {

    List<JobDto> findAll();

     Job createJob(Job job);

    JobDto getJobById(Long id);

     boolean deleteById(Long id);

     Job UpdateJobById(Long id, Job updateJob);
}
