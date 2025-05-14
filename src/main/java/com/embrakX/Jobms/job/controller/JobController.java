package com.embrakX.Jobms.job.controller;


import com.embrakX.Jobms.job.dto.JobDto;
import com.embrakX.Jobms.job.entity.Job;
import com.embrakX.Jobms.job.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/jobs")
@RestController
public class JobController {

    @Autowired
    private JobService jobService;


    @GetMapping("/getAll")
    public ResponseEntity<List<JobDto>> findAll(){
        return new  ResponseEntity<>(jobService.findAll(),HttpStatus.OK);

    };

    @GetMapping("/getById/{id}")
    public ResponseEntity<JobDto> jobById(@PathVariable Long id){
        JobDto jobDto = jobService.getJobById(id);
        if (jobDto != null){
            return new ResponseEntity<>(jobDto,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<String>  createJob(@RequestBody Job job){
        jobService.createJob(job);

        return new  ResponseEntity<>("Job Added Successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteJobById(@PathVariable Long id){
       boolean deleted=jobService.deleteById(id);
        if (deleted){
            jobService.deleteById(id);
            return new ResponseEntity<>("Job Deleted !",HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Job>  updateJobById(@PathVariable Long id ,@RequestBody Job updateJob){
        Job updated = jobService.UpdateJobById(id,updateJob);
        if (updated!=null){
            return new ResponseEntity<>(updated,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
