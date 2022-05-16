import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.mycompany.careers")
@EnableJpaRepositories
@EntityScan("com.mycompany.careers")
@EnableRabbit
public class JobApplicationMain {

    public static void main(String[] args) {
        SpringApplication.run(JobApplicationMain.class, args);
    }

}