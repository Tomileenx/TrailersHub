package com.example.Trailers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TrailersApplication {

  public static void main(String[] args) {
    SpringApplication.run(TrailersApplication.class, args);
  }

}
