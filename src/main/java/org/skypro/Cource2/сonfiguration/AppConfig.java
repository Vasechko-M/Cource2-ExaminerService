package org.skypro.Cource2.сonfiguration;

import org.skypro.Cource2.service.QuestionServices;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppConfig {

    @Bean("questionServicesMap")
    public Map<String, QuestionServices> questionServicesMap(
            @Qualifier("javaQuestionService") QuestionServices javaService,
            @Qualifier("mathQuestionService") QuestionServices mathService) {
        Map<String, QuestionServices> map = new HashMap<>();
        map.put("javaQuestionService", javaService);
        map.put("mathQuestionService", mathService);
        return map;
    }
}