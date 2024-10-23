package net.engineeringdigest.journalApp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
public class JournalApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(JournalApplication.class, args);
        System.out.println(run.getEnvironment());

    }

    @Bean
    public PlatformTransactionManager add (MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }

}