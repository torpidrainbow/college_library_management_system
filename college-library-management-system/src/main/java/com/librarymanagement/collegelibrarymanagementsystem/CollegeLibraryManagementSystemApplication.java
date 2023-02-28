package com.librarymanagement.collegelibrarymanagementsystem;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CollegeLibraryManagementSystemApplication {

	@Bean
	public ModelMapper modelMapper() {

//		mapper.getConfiguration()
//				.setMatchingStrategy(MatchingStrategies.STANDARD);

		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(CollegeLibraryManagementSystemApplication.class, args);
	}

}
