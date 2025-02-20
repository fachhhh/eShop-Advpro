package id.ac.ui.cs.advprog.eshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EShopApplication {

    private EShopApplication() {
        // Prevent instantiation
    }

    public static void main(String[] args) {
        SpringApplication.run(EShopApplication.class, args);
    }
}
