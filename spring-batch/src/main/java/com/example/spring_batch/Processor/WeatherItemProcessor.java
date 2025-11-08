package com.example.spring_batch.Processor;

import com.example.spring_batch.entity.Weather;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class WeatherItemProcessor implements ItemProcessor<Weather,Weather> {

    @Override
    public Weather process(Weather item) throws Exception {
        return item;
    }
}
