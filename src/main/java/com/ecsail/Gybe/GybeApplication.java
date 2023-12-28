package com.ecsail.Gybe;

import com.ecsail.Gybe.dto.AppSettingDTO;
import com.ecsail.Gybe.repository.interfaces.SettingsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class GybeApplication {


	public static void main(String[] args) {
		SpringApplication.run(GybeApplication.class, args);
	}

//	@Bean
//	CommandLineRunner run(SettingsRepository settingsRepository){
//		return args -> {
//			appSettings = settingsRepository.getAppSettingsByGroupName("2023_Gybe");
//		};
//	}
//
//	public List<AppSettingDTO> getAppSettings() {
//		return appSettings;
//	}

}
