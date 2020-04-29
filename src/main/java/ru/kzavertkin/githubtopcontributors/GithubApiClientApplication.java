package ru.kzavertkin.githubtopcontributors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class GithubApiClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(GithubApiClientApplication.class, args);
    }
}
