package com.embrakX.Jobms.job.dto;


import com.embrakX.Jobms.job.entity.Job;
import com.embrakX.Jobms.job.externial.Company;

public class JobwithCompanyDto {

    private Job job;

    private Company company;

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
