package br.com.amptech.projeto01ws.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import br.com.amptech.projeto01ws.services.DBService;

@Configuration
@Profile("test")
@Service
public class TestConfig {

	@Autowired
	DBService dbService;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException{
		
		dbService.instantiateTestDatabase();

		return true;
	}
	
}
