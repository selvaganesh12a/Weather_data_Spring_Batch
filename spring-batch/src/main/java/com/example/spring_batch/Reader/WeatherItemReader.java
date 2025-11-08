package com.example.spring_batch.Reader;

import com.example.spring_batch.entity.Weather;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class WeatherItemReader {

    @Value("${batch.file.path}")
    private String filePath;

    @Bean
    public FlatFileItemReader<Weather> reader() {
        FlatFileItemReader<Weather> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("Weather_Data.csv"));

        reader.setLinesToSkip(1);

        DefaultLineMapper<Weather> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(new DelimitedLineTokenizer() {{
            setNames("datetime_utc", "conds", "dewptm", "fog", "hail", "heatindexm", "hum",
                    "pressurem", "rain", "snow", "tempm", "thunder", "tornado", "vism",
                    "wdird", "wdire", "wgustm", "windchillm", "wspdm");
        }});

        lineMapper.setFieldSetMapper(fieldSet -> {
            Weather w = new Weather();

            w.setDatetime_utc(fieldSet.readString("datetime_utc"));
            w.setConds(fieldSet.readString("conds"));
            w.setDewptm(readNullableInt(fieldSet, "dewptm"));
            w.setFog(readNullableInt(fieldSet, "fog"));
            w.setHail(readNullableInt(fieldSet, "hail"));
            w.setHeatindexm(readNullableFloat(fieldSet, "heatindexm"));
            w.setHum(readNullableInt(fieldSet, "hum"));
            w.setPressurem(readNullableInt(fieldSet, "pressurem"));
            w.setRain(readNullableInt(fieldSet, "rain"));
            w.setSnow(readNullableInt(fieldSet, "snow"));
            w.setTempm(readNullableInt(fieldSet, "tempm"));
            w.setThunder(readNullableInt(fieldSet, "thunder"));
            w.setTornado(readNullableInt(fieldSet, "tornado"));
            w.setVism(readNullableFloat(fieldSet, "vism"));
            w.setWdird(readNullableInt(fieldSet, "wdird"));
            w.setWdire(fieldSet.readString("wdire"));
            w.setWgustm(readNullableFloat(fieldSet, "wgustm"));
            w.setWindchillm(readNullableFloat(fieldSet, "windchillm"));
            w.setWspdm(readNullableFloat(fieldSet, "wspdm"));

            return w;
        });

        reader.setLineMapper(lineMapper);
        return reader;
    }

    private Integer readNullableInt(FieldSet fieldSet, String field) {
        String value = fieldSet.readString(field);
        if (value == null || value.isEmpty() || value.equalsIgnoreCase("N/A")) return null;
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return (int) Float.parseFloat(value); // handle cases like 1.2
        }
    }

    private Float readNullableFloat(FieldSet fieldSet, String field) {
        String value = fieldSet.readString(field);
        if (value == null || value.isEmpty() || value.equalsIgnoreCase("N/A")) return null;
        return Float.parseFloat(value);
    }
}
