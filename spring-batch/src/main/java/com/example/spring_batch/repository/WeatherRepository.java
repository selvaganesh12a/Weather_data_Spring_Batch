package com.example.spring_batch.repository;

import com.example.spring_batch.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<Weather,String> {
}
