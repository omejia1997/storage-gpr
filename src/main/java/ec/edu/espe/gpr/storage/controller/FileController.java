package ec.edu.espe.gpr.storage.controller;

import ec.edu.espe.gpr.storage.model.FileModel;
import ec.edu.espe.gpr.storage.model.FileRequest;
import ec.edu.espe.gpr.storage.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin("*")
public class FileController {

    @Autowired
    FileService fileService;

    @GetMapping("/getAllfilesTasksDocent/{modulo}")
    public ResponseEntity<List<String>> getAllfilesTasksDocent(@PathVariable String modulo) {
        List<String> fileNames = fileService.loadAll(modulo).map(path -> {
            String filename = path.getFileName().toString();
            return filename;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(fileNames);
    }

    @GetMapping("/getFileTasksDocent/{modulo}/{fileName}")
    public ResponseEntity<Resource> getFileTasksDocent(@PathVariable String modulo,@PathVariable String fileName) {
        Resource resource = fileService.load(modulo,fileName);
        return ResponseEntity.status(HttpStatus.OK).body(resource);
    }


    @GetMapping("/getAllfilesTasks/{modulo}")
    public ResponseEntity<List<String>> getAllfilesTasks(@PathVariable String modulo) {

        List<String> fileNames = fileService.loadAllFilesGuide(modulo).map(path -> {
            String filename = path.getFileName().toString();
            return filename;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(fileNames);
    }

    @GetMapping("/getFileTask/{modulo}/{fileName}")
    public ResponseEntity<Resource> getFileTask(@PathVariable String modulo,@PathVariable String fileName) {
        Resource resource = fileService.loadFileTarea(modulo,fileName);
        return ResponseEntity.status(HttpStatus.OK).body(resource);
    }

    @GetMapping("/getFileDocenteTarea/{modulo}/{filename}/{nombreArchivoTareaDocente}")
    public ResponseEntity<FileModel> getFileDocenteTarea(@PathVariable String modulo,@PathVariable String filename,
            @PathVariable String nombreArchivoTareaDocente) {
        String url = MvcUriComponentsBuilder.fromMethodName(FileController.class, "getFile",
                modulo,filename,nombreArchivoTareaDocente).build().toString();
        FileModel fileModel = new FileModel(filename, url);
        return ResponseEntity.status(HttpStatus.OK).body(fileModel);
    }

    @GetMapping("/files/{modulo}/{filename:.+}/{nombreArchivoTareaDocente:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String modulo,@PathVariable String filename,
            @PathVariable String nombreArchivoTareaDocente) {
        Resource file = fileService.load(modulo,nombreArchivoTareaDocente);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"").body(file);
    }

    @GetMapping("/getFileTarea/{modulo}/{filename}/{nombreArchivoTarea}")
    public ResponseEntity<FileModel> getFileTarea(@PathVariable String modulo,@PathVariable String filename,
            @PathVariable String nombreArchivoTarea) {
        String url = MvcUriComponentsBuilder.fromMethodName(FileController.class, "getFileTareaGuia",
                modulo,filename, nombreArchivoTarea).build().toString();
        FileModel fileModel = new FileModel(filename, url);
        return ResponseEntity.status(HttpStatus.OK).body(fileModel);
    }

    @GetMapping("/fileTareaGuia/{modulo}/{filename:.+}/{nombreArchivoTarea:.+}")
    public ResponseEntity<Resource> getFileTareaGuia(@PathVariable String modulo,@PathVariable String filename,
            @PathVariable String nombreArchivoTarea) {
        Resource file = fileService.loadFileTarea(modulo,nombreArchivoTarea);
        // String[] fileProperties = filename.split("\\.");
        // Tarea tarea = this.fileService.getTarea(Integer.parseInt(fileProperties[0]));
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"").body(file);
    }

    /*@PostMapping("/upload")
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
    }*/

    /*@PostMapping("/saveFileGuia/{modulo}")
    public ResponseEntity<String> saveFileGuia(@PathVariable String modulo,@RequestParam("file") MultipartFile file,
                                               @RequestParam("nameFile") String nameFile ) {
        String message = "";
        try {
            this.fileService.saveFileGuia(file, nameFile,modulo);
            message = "Se subieron los archivos correctamente ";
            //return ResponseEntity.status(HttpStatus.OK).body(message);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            message = "Fallo al subir los archivos";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }*/
    @PostMapping("/saveFileGuia/{modulo}")
    public ResponseEntity<String> saveFileGuia(@PathVariable String modulo, @RequestBody FileRequest fileRequest) {
        String message = "";
        try {
            this.fileService.saveFileGuia(fileRequest.getFileBase64(),fileRequest.getNameFile(),modulo,fileRequest.getPreviousNameFile());
            message = "Se subieron los archivos correctamente ";
            //return ResponseEntity.status(HttpStatus.OK).body(message);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            message = "Fallo al subir los archivos";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    /*@PostMapping("/saveFile/{modulo}")
    public ResponseEntity<String> saveFile(@PathVariable String modulo,@RequestParam("file") MultipartFile file,
                                           @RequestParam("nameFile") String nameFile ) {
        String message = "";
        try {
            this.fileService.saveFile(file, nameFile,modulo);
            message = "Se subieron los archivos correctamente ";
            //return ResponseEntity.status(HttpStatus.OK).body(message);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            message = "Fallo al subir los archivos";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }*/

    @PostMapping("/saveFile/{modulo}")
    public ResponseEntity<String> saveFile(@PathVariable String modulo, @RequestBody FileRequest fileRequest) {
        String message = "";
        try {
            this.fileService.saveFile(fileRequest.getFileBase64(),fileRequest.getNameFile(),modulo,fileRequest.getPreviousNameFile());
            message = "Se subieron los archivos correctamente ";
            //return ResponseEntity.status(HttpStatus.OK).body(message);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            message = "Fallo al subir los archivos";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

}