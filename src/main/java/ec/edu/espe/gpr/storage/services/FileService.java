package ec.edu.espe.gpr.storage.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    private final Path rootFilesUploadsInvestigacion = Paths.get("uploads");
    private final Path rootFileGuiaInvestigacion = Paths.get("archivo_guia");

        
    public void init() {
        try {
            Files.createDirectory(rootFilesUploadsInvestigacion);
        } catch (IOException e) {
            throw new RuntimeException("No se puede inicializar la carpeta uploads");
        }
    }

    public void initFileGuia() {
        try {
            Files.createDirectory(rootFileGuiaInvestigacion);
        } catch (IOException e) {
            throw new RuntimeException("No se puede inicializar la carpeta uploads");
        }
    }
    
    public void deleteAllFileGuia() {
        FileSystemUtils.deleteRecursively(rootFileGuiaInvestigacion.toFile());
    }

    public void saveFileGuia(MultipartFile file, String nameFile) {
        try {
            Files.deleteIfExists(this.rootFileGuiaInvestigacion.resolve(nameFile));
            Files.copy(file.getInputStream(), this.rootFileGuiaInvestigacion.resolve(nameFile));
        } catch (IOException e) {
            throw new RuntimeException("No se puede guardar el archivo. Error " + e.getMessage());
        }
    }

     public void save(MultipartFile file) {
        try {
            // copy (que queremos copiar, a donde queremos copiar)
            Files.copy(file.getInputStream(), this.rootFilesUploadsInvestigacion.resolve(file.getOriginalFilename()));
        } catch (IOException e) {
            throw new RuntimeException("No se puede guardar el archivo. Error " + e.getMessage());
        }
    }

    public Resource load(String filename) {
        try {
            Path file = rootFilesUploadsInvestigacion.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if(resource.exists() || resource.isReadable()){
                return resource;
            }else{
                throw new RuntimeException("No se puede leer el archivo ");
            }

        }catch (MalformedURLException e){
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }


    public Resource loadFileTarea(String filename) {
        try {
            Path file = rootFileGuiaInvestigacion.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if(resource.exists() || resource.isReadable()){
                return resource;
            }else{
                throw new RuntimeException("No se puede leer el archivo ");
            }

        }catch (MalformedURLException e){
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootFilesUploadsInvestigacion.toFile());
    }

    public Stream<Path> loadAll(){
        try{
            return Files.walk(this.rootFilesUploadsInvestigacion,1).filter(path -> !path.equals(this.rootFilesUploadsInvestigacion))
                    .map(this.rootFilesUploadsInvestigacion::relativize);
        }catch (RuntimeException | IOException e){
            throw new RuntimeException("No se pueden cargar los archivos ");
        }
    }

}