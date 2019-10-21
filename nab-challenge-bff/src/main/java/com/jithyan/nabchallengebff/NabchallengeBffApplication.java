package com.jithyan.nabchallengebff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class NabchallengeBffApplication {
   public static void main(String[] args) {
      SpringApplication.run(NabchallengeBffApplication.class, args);
   }

}
