package br.com.robsonlmds.fipecar;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.robsonlmds.fipecar.principal.Principal;

@SpringBootApplication
public class FipecarApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FipecarApplication.class, args);
	}

	@Override
    public void run(String... args) throws Exception {
       Principal principal = new Principal();
	   principal.exibeMenu();
	}
}
