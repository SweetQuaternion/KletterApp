package com.dachpc.kletterapp.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import tools.jackson.databind.json.JsonMapper;

@Service
public class FlachwitzService {

    private final List<FlachwitzData> witze;

    public FlachwitzService(@Value("classpath:flachwitze.json") Resource file) throws Exception {
        JsonMapper mapper = new JsonMapper();
        witze = mapper.readValue(file.getInputStream(),
            mapper.getTypeFactory().constructCollectionType(List.class, FlachwitzData.class));
    }
    
    public FlachwitzData getWitzOfTheDay() {
        long day = LocalDate.now().toEpochDay();
        return witze.get(new Random(day).nextInt(witze.size()));
    }
}
