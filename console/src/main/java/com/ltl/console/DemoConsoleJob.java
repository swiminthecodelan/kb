package com.ltl.console;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DemoConsoleJob implements Runnable {
    @Value("${jsonOptions}")
    private String jsonOptions;


    @Override
    public void run() {
        log.info("run console job");
        log.info(jsonOptions);
        DemoJobOptions options = JsonUtils.jsonToObject(jsonOptions, DemoJobOptions.class);
        log.info(String.valueOf(options));
    }

    @Data
    public static class DemoJobOptions {

        private String name;
        private Integer age;
    }
}

