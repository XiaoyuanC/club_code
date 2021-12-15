package club.server;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
@MapperScan(basePackages = {"club.server.dao"})
public class ClubServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClubServerApplication.class,args);
    }
}
