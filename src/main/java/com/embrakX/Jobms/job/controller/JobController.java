package com.embrakX.jobms.job.controller;

import com.embrakX.jobms.job.entity.Job;
import com.embrakX.jobms.job.service.JobService;
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
    public ResponseEntity<List<Job>> findAll(){
        return new  ResponseEntity<>(jobService.findAll(),HttpStatus.OK);

    };

    @GetMapping("/getById/{id}")
    public ResponseEntity<Job> jobById(@PathVariable Long id){
        Job job = jobService.getJobById(id);
        if (job != null){
            return new ResponseEntity<>(job,HttpStatus.OK);
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
        Job job =jobService.getJobById(id);
        if (job != null){
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
