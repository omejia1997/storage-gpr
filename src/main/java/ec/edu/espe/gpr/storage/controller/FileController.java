package ec.edu.espe.gpr.storage.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import ec.edu.espe.gpr.storage.model.FileModel;
import ec.edu.espe.gpr.storage.services.FileService;

@Controller
@CrossOrigin("*")
public class FileController {

    @Autowired
    FileService fileService;

    @GetMapping("/files")
    public ResponseEntity<List<FileModel>> getFiles() {
        List<FileModel> fileInfos = fileService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder.fromMethodName(FileController.class, "getFile",
                    path.getFileName().toString()).build().toString();
            return new FileModel(filename, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/fileDocenteTarea/{filename}/{nombreArchivoTareaDocente}")
    public ResponseEntity<FileModel> getFileDocenteTarea(@PathVariable String filename,
            @PathVariable String nombreArchivoTareaDocente) {
        String url = MvcUriComponentsBuilder.fromMethodName(FileController.class, "getFile",
                filename, nombreArchivoTareaDocente).build().toString();
        FileModel fileModel = new FileModel(filename, url);
        return ResponseEntity.status(HttpStatus.OK).body(fileModel);
    }

    @GetMapping("/files/{filename:.+}/nombreArchivoTareaDocente")
    public ResponseEntity<Resource> getFile(@PathVariable String filename,
            @PathVariable String nombreArchivoTareaDocente) {
        Resource file = fileService.load(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + nombreArchivoTareaDocente + "\"").body(file);
    }

    @GetMapping("/fileTarea/{filename}/{nombreArchivoTarea}")
    public ResponseEntity<FileModel> getFileTarea(@PathVariable String filename,
            @PathVariable String nombreArchivoTareaDocente) {

        String url = MvcUriComponentsBuilder.fromMethodName(FileController.class, "getFileTareaGuia",
                filename, nombreArchivoTareaDocente).build().toString();
        FileModel fileModel = new FileModel(filename, url);

        return ResponseEntity.status(HttpStatus.OK).body(fileModel);
    }

    @GetMapping("/fileTareaGuia/{filename:.+}/nombreArchivoTarea")
    public ResponseEntity<Resource> getFileTareaGuia(@PathVariable String filename,
            @PathVariable String nombreArchivoTarea) {
        Resource file = fileService.loadFileTarea(filename);
        // String[] fileProperties = filename.split("\\.");
        // Tarea tarea = this.fileService.getTarea(Integer.parseInt(fileProperties[0]));
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + nombreArchivoTarea + "\"").body(file);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFiles(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            fileService.save(file);
            message = "Se subieron los archivos correctamente ";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Fallo al subir los archivos";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

}