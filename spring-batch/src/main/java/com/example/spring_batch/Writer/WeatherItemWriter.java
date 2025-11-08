package com.example.spring_batch.Writer;

import com.example.spring_batch.entity.Weather;
import com.example.spring_batch.repository.WeatherRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class WeatherItemWriter implements ItemWriter<Weather> {
    private final WeatherRepository weatherRepository;

    public WeatherItemWriter(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }


    @Override
    public void write(Chunk<? extends Weather> chunk) throws Exception {
        System.out.println("Chunk size: " + chunk.size());
        weatherRepository.saveAll(chunk.getItems());
    }
}
