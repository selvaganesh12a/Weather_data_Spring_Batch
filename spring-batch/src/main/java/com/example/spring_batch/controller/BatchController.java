package com.example.spring_batch.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/batch")
public class BatchController {
    private final JobLauncher jobLauncher;
    private final Job weatherJob;

    public BatchController(JobLauncher jobLauncher, Job weatherJob) {
        this.jobLauncher = jobLauncher;
        this.weatherJob = weatherJob;
    }

    @PostMapping("/upload")
    public String uploadAndRunJob() throws Exception{
        File resourceFile = new ClassPathResource("Weather_Data.csv").getFile();
        String absolutePath = resourceFile.getAbsolutePath();

        System.out.println("File path: " + absolutePath);

        //run Job
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(weatherJob,jobParameters);
        return "Job Started Successfully";
    }
}
