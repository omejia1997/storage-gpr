package ec.edu.espe.gpr.storage;

import ec.edu.espe.gpr.storage.model.FileSaveRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ec.edu.espe.gpr.storage.services.FileService;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class StorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(StorageApplication.class, args);
	}

	@Bean
	CommandLineRunner init(FileService fileService) {
		return (args) -> {

			Map<String, List<FileSaveRequest>> dataMapFilesGuia = fileService.getAllFilesGuia();
			Map<String, List<FileSaveRequest>> dataMapFiles = fileService.getAllFiles();
			fileService.deleteAll();
			fileService.initFilesInvestigacion();
			fileService.initFilesVinculacion();
			fileService.initFilesDocencia();
			fileService.saveFilesGuia(dataMapFilesGuia);
			fileService.saveFiles(dataMapFiles);
		};
	}
}