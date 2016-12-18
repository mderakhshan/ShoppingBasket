package com.shopping;

import com.shopping.service.ConsoleCli;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.ApplicationContext;


/**
 *  * Author Mir on 30/09/2016.
 *
 * The Spring Boot bootstrap class that runs the app. The EmbeddedServletContainer and WebMvc auto configuration
 * modules of Spring Boot are not needed for the purpose of this coding exercise, so they are excluded.
 *
 */
@SpringBootApplication(exclude = {EmbeddedServletContainerAutoConfiguration.class, WebMvcAutoConfiguration.class})
public class Shopping {
    private static ApplicationContext context;

    @Autowired
    public void context(ApplicationContext appContext) {
        context = appContext;
    }
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Shopping.class, args);
        ConsoleCli consoleCli = context.getBean(ConsoleCli.class);
        consoleCli.runCli(args);
    }
}
