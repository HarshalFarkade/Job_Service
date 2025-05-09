package com.embrakX.Jobms.job.service.impl;

import com.embrakX.Jobms.job.dto.JobwithCompanyDto;
import com.embrakX.Jobms.job.entity.Job;
import com.embrakX.Jobms.job.externial.Company;
import com.embrakX.Jobms.job.repository.JobRepository;
import com.embrakX.Jobms.job.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;


    @Override
    public List<JobwithCompanyDto> findAll() {
        List<Job> jobs = jobRepository.findAll();
        List<JobwithCompanyDto> jobwithCompanyDtos = new ArrayList<>();
        return jobs.stream().map(this::convertToDTo).collect(Collectors.toList());
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

    private JobwithCompanyDto convertToDTo(Job job){

            JobwithCompanyDto jobwithCompanyDto = new JobwithCompanyDto();
            jobwithCompanyDto.setJob(job);

        RestTemplate restTemplate = new RestTemplate();
            Company company= restTemplate.getForObject("http://localhost:8081/companies/"+job.getCompanyId(),
                    Company.class);
            jobwithCompanyDto.setCompany(company);

            return jobwithCompanyDto;


    }
}
