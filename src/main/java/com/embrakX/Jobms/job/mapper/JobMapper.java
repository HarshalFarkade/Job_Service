package com.embrakX.Jobms.job.mapper;

import com.embrakX.Jobms.job.dto.JobDto;
import com.embrakX.Jobms.job.entity.Job;
import com.embrakX.Jobms.job.externial.Company;
import com.embrakX.Jobms.job.externial.Review;

import java.util.List;

public class JobMapper {

    public static JobDto mapToJobWithCompanyDto(Job job , Company company, List<Review> reviews){
        JobDto jobDto = new JobDto();
        jobDto.setId(job.getId());
        jobDto.setTitle(job.getTitle());
        jobDto.setDescription(job.getDescription());
        jobDto.setMinSalary(job.getMinSalary());
        jobDto.setMaxSalary(job.getMaxSalary());
        jobDto.setLocation(job.getLocation());
        jobDto.setCompany(company);
        jobDto.setReviews(reviews);
        return jobDto;
    }
}
