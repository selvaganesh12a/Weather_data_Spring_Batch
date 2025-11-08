package com.example.spring_batch.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Weather {
    @Id
    private String datetime_utc;
    private String conds;
    private Integer dewptm;
    private Integer fog;
    private Integer hail;
    private Float heatindexm;
    private Integer hum;
    private Integer pressurem;
    private Integer rain;
    private Integer snow;
    private Integer tempm;
    private Integer thunder;
    private Integer tornado;
    private Float vism;
    private Integer wdird;
    private String wdire;
    private Float wgustm;
    private Float windchillm;
    private Float wspdm;
}