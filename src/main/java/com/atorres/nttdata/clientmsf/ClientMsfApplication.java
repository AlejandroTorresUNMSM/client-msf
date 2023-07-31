package com.atorres.nttdata.clientmsf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * .
 * Metodo principal
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ClientMsfApplication {
  /**
   * .
   * Ejecucion del proyecto
   *
   * @param args args
   */
  public static void main(String[] args) {
    SpringApplication.run(ClientMsfApplication.class, args);
  }

}
