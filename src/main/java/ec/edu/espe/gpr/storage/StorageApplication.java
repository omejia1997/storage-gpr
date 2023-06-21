package ec.edu.espe.gpr.storage;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ec.edu.espe.gpr.storage.services.FileService;

@SpringBootApplication
public class StorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(StorageApplication.class, args);
	}

	@Bean
	CommandLineRunner init(FileService fileService) {
		return (args) -> {
			fileService.deleteAllFileGuia();
			fileService.deleteAll();
			fileService.init();
			fileService.initFileGuia();
		};
	}
}