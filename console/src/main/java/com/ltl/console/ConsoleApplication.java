package com.ltl.console;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationContextFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;

import java.lang.reflect.Constructor;

/**
 * @author tyler
 */
@Slf4j
@Profile({"console"})
@SpringBootApplication
public class ConsoleApplication implements CommandLineRunner {
    @Value("${clazz}")
    private String clazz;

    public static void main(String[] args) {
        SpringApplication.run(ConsoleApplication.class, args);
    }

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void run(String... args) throws Exception {
        log.info("Running");
        log.info(clazz);
        Runnable job  = (Runnable) applicationContext.getBean(Class.forName(clazz));
        job.run();
        System.exit(SpringApplication.exit(applicationContext));
    }
}
