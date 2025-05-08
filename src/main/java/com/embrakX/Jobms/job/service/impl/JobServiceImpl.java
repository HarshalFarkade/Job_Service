package com.embrakX.Jobms.job.service.impl;

import com.embrakX.jobms.job.entity.Job;
import com.embrakX.jobms.job.repository.JobRepository;
import com.embrakX.jobms.job.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;


    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Override
    public Job createJob(Job job) {
        Job saveJob = new Job();
       saveJob.setTitle(job.getTitle());
        saveJob.setDescription(job.getDescription());
        saveJob.setMinSalary(job.getMinSalary());
        saveJob.setMaxSalary(job.getMaxSalary());
        saveJob.setLocation(job.getLocation());
        saveJob.setCompanyId(job.getCompanyId());
        return jobRepository.save(saveJob);

    }

    @Override
    public Job getJobById(Long id) {
       return jobRepository.findById(id).get();
    }

    @Override
    public String deleteById(Long id) {
      Optional<Job> job = jobRepository.findById(id);
       if (job.isPresent()){
           jobRepository.deleteById(id);
           return "Job Deleted!!";
       }
       return "Job With Id is not Found";

    }

    @Override
    public Job UpdateJobById(Long id, Job updateJob) {
        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isPresent()){
            Job job = jobOptional.get();
            job.setTitle(updateJob.getTitle());
            job.setDescription(updateJob.getDescription());
            job.setMinSalary(updateJob.getMinSalary());
            job.setMaxSalary(updateJob.getMaxSalary());
            job.setLocation(updateJob.getLocation());
            return jobRepository.save(job);
        }

       return null;
    }
}
