package com.embrakX.Jobms.job.service.impl;

import com.embrakX.Jobms.job.clients.CompanyClient;
import com.embrakX.Jobms.job.clients.ReviewClient;
import com.embrakX.Jobms.job.dto.JobDto;
import com.embrakX.Jobms.job.entity.Job;
import com.embrakX.Jobms.job.externial.Company;
import com.embrakX.Jobms.job.externial.Review;
import com.embrakX.Jobms.job.mapper.JobMapper;
import com.embrakX.Jobms.job.repository.JobRepository;
import com.embrakX.Jobms.job.service.JobService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CompanyClient companyClient;

    @Autowired
    private ReviewClient reviewClient;

    int attempt =0;


    @Override
//    @CircuitBreaker(name="companyBreaker" ,
//            fallbackMethod ="companyBreakerFallback")
//    @Retry(name ="companyBreaker",
//            fallbackMethod ="companyBreakerFallback")
    @RateLimiter(name = "companyBreaker",
    fallbackMethod = "companyBreakerFallback")
    public List<JobDto> findAll() {
        System.out.println("Attempt: "+ ++attempt);
        List<Job> jobs = jobRepository.findAll();
        List<JobDto> jobDtos = new ArrayList<>();
        return jobs.stream().map(this::convertToDTo).collect(Collectors.toList());
    }

    public List<String> companyBreakerFallback(Exception e){
        List<String> list = new ArrayList<>();
        list.add("dummy");
        return list;
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
    public JobDto getJobById(Long id) {
       Job job= jobRepository.findById(id).get();
       return convertToDTo(job);
    }

    @Override
    public boolean deleteById(Long id) {
      try{
          jobRepository.deleteById(id);
          return true;
      }catch (Exception e){
          return false;
      }

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

    private JobDto convertToDTo(Job job){

//        RestTemplate restTemplate = new RestTemplate();
            Company company= companyClient.getCompany(job.getCompanyId());
//       ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange(
//                "http://REVIEW-SERVICE:8083/reviews?companyId=" + job.getCompanyId(),
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<Review>>() {
//                });
       List<Review> reviews =reviewClient.getReviews(job.getCompanyId());
            JobDto jobDto = JobMapper
                    .mapToJobWithCompanyDto(job,company,reviews);
            return jobDto;
    }
}
