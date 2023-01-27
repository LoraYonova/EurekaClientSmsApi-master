package mail.intellisoft.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Properties;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public GetPathBySystem getPathBySystem() {
        return new GetPathBySystem();
    }


    @Bean
    public DateAndTimeFormat dateAndTimeNow(){
        return new DateAndTimeFormat();
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("164.138.221.218");
        mailSender.setPort(465);
        mailSender.setUsername("support@intellisoft.guru");
        mailSender.setPassword("gx6Q3b3sXZgx6Q3b3sXZ");


        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtps.ssl.checkserveridentity", "true");
        props.put("mail.smtps.ssl.trust", "*");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
