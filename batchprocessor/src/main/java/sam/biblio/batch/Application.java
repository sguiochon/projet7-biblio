package sam.biblio.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"sam.biblio"}) // Requis pour permettre la prise en compte des WebClient
@EnableBatchProcessing
public class Application {

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = new SpringApplicationBuilder(Application.class).web(WebApplicationType.NONE).run(args);
    }
}
