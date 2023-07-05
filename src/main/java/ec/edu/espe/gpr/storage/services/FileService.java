package ec.edu.espe.gpr.storage.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import ec.edu.espe.gpr.storage.config.BaseURLValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import ec.edu.espe.gpr.storage.enums.ModulosEnum;

@Service
public class FileService {

    private final Path rootFilesUploadsInvestigacion = Paths.get("archivo_tareas_investigacion");
    private final Path rootFileGuiaInvestigacion = Paths.get("archivo_guia_investigacion");
    private final Path rootFilesVinculacion = Paths.get("archivo_tarea_vinculacion");
    private final Path rootFileGuiaVinculacion = Paths.get("archivo_guia_vinculacion");
    private final Path rootFilesDocencia = Paths.get("archivo_tarea_docencia");
    private final Path rootFileGuiaDocencia = Paths.get("archivo_guia_docencia");

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BaseURLValues baseURLs;

    public void initFilesInvestigacion() {
        try {
            Files.createDirectory(rootFilesUploadsInvestigacion);
            Files.createDirectory(rootFileGuiaInvestigacion);
        } catch (IOException e) {
            throw new RuntimeException("No se puede inicializar la carpeta uploads-investigacion");
        }
    }

    public void initFilesVinculacion() {
        try {
            Files.createDirectory(rootFilesVinculacion);
            Files.createDirectory(rootFileGuiaVinculacion);
        } catch (IOException e) {
            throw new RuntimeException("No se puede inicializar la carpeta uploads-vinculacion");
        }
    }

    public void initFilesDocencia() {
        try {
            Files.createDirectory(rootFilesDocencia);
            Files.createDirectory(rootFileGuiaDocencia);
        } catch (IOException e) {
            throw new RuntimeException("No se puede inicializar la carpeta uploads-docencia");
        }
    }

    public void saveFileGuia(MultipartFile file, String nameFile, String modulo) {
        if (modulo.equals(ModulosEnum.INVESTIGACION.getValue())) {//Modulo a guardar
            try {
                Files.deleteIfExists(this.rootFileGuiaInvestigacion.resolve(nameFile));
                Files.copy(file.getInputStream(), this.rootFileGuiaInvestigacion.resolve(nameFile));
            } catch (IOException e) {
                throw new RuntimeException("No se puede guardar el archivo. Error " + e.getMessage());
            }
        } else if (modulo.equals(ModulosEnum.VINCULACION.getValue())) {
            try {
                Files.deleteIfExists(this.rootFileGuiaVinculacion.resolve(nameFile));
                Files.copy(file.getInputStream(), this.rootFileGuiaVinculacion.resolve(nameFile));
            } catch (IOException e) {
                throw new RuntimeException("No se puede guardar el archivo. Error " + e.getMessage());
            }
        } else if (modulo.equals(ModulosEnum.DOCENCIA.getValue())) {
            try {
                Files.deleteIfExists(this.rootFileGuiaDocencia.resolve(nameFile));
                Files.copy(file.getInputStream(), this.rootFileGuiaDocencia.resolve(nameFile));
            } catch (IOException e) {
                throw new RuntimeException("No se puede guardar el archivo. Error " + e.getMessage());
            }
        }
    }

    public void saveFile(MultipartFile file, String nameFile,String modulo) {
        if (modulo.equals(ModulosEnum.INVESTIGACION.getValue())) {//Modulo a guardar
            try {
                Files.deleteIfExists(this.rootFilesUploadsInvestigacion.resolve(nameFile));
                Files.copy(file.getInputStream(), this.rootFilesUploadsInvestigacion.resolve(nameFile));
            } catch (IOException e) {
                throw new RuntimeException("No se puede guardar el archivo. Error " +
                        e.getMessage());
            }
        } else if (modulo.equals(ModulosEnum.VINCULACION.getValue())) {//Modulo a guardar
            try {
                Files.deleteIfExists(this.rootFilesVinculacion.resolve(nameFile));
                Files.copy(file.getInputStream(), this.rootFilesVinculacion.resolve(nameFile));
            } catch (IOException e) {
                throw new RuntimeException("No se puede guardar el archivo. Error " +
                        e.getMessage());
            }
        } else if (modulo.equals(ModulosEnum.DOCENCIA.getValue())) {//Modulo a guardar
            try {
                Files.deleteIfExists(this.rootFilesDocencia.resolve(nameFile));
                Files.copy(file.getInputStream(), this.rootFilesDocencia.resolve(nameFile));
            } catch (IOException e) {
                throw new RuntimeException("No se puede guardar el archivo. Error " +
                        e.getMessage());
            }
        }
    }

    /*public void save(MultipartFile file) {
        try {
            // copy (que queremos copiar, a donde queremos copiar)
            Files.copy(file.getInputStream(), this.rootFilesUploadsInvestigacion.resolve(file.getOriginalFilename()));
        } catch (IOException e) {
            throw new RuntimeException("No se puede guardar el archivo. Error " + e.getMessage());
        }
    }*/
    public Resource loadFileTarea(String modulo,String filename) {
        if (modulo.equals(ModulosEnum.INVESTIGACION.getValue())) {
            try {
                Path file = rootFileGuiaInvestigacion.resolve(filename);
                Resource resource = new UrlResource(file.toUri());

                if (resource.exists() || resource.isReadable()) {
                    return resource;
                } else {
                    throw new RuntimeException("No se puede leer el archivo ");
                }

            } catch (MalformedURLException e) {
                throw new RuntimeException("Error: " + e.getMessage());
            }
        } else if (modulo.equals(ModulosEnum.VINCULACION.getValue())) {
            try {
                Path file = rootFileGuiaVinculacion.resolve(filename);
                Resource resource = new UrlResource(file.toUri());

                if (resource.exists() || resource.isReadable()) {
                    return resource;
                } else {
                    throw new RuntimeException("No se puede leer el archivo ");
                }

            } catch (MalformedURLException e) {
                throw new RuntimeException("Error: " + e.getMessage());
            }
        } else if (modulo.equals(ModulosEnum.DOCENCIA.getValue())) {
            try {
                Path file = rootFileGuiaDocencia.resolve(filename);
                Resource resource = new UrlResource(file.toUri());

                if (resource.exists() || resource.isReadable()) {
                    return resource;
                } else {
                    throw new RuntimeException("No se puede leer el archivo ");
                }

            } catch (MalformedURLException e) {
                throw new RuntimeException("Error: " + e.getMessage());
            }
        }
        return null;
    }

    public Resource load(String modulo,String filename) {
        if (modulo.equals(ModulosEnum.INVESTIGACION.getValue())) {
            try {
                Path file = rootFilesUploadsInvestigacion.resolve(filename);
                Resource resource = new UrlResource(file.toUri());
                if (resource.exists() || resource.isReadable()) {
                    return resource;
                } else {
                    throw new RuntimeException("No se puede leer el archivo ");
                }

            } catch (MalformedURLException e) {
                throw new RuntimeException("Error: " + e.getMessage());
            }
        } else if (modulo.equals(ModulosEnum.VINCULACION.getValue())) {
            try {
                Path file = rootFilesVinculacion.resolve(filename);
                Resource resource = new UrlResource(file.toUri());
                if (resource.exists() || resource.isReadable()) {
                    return resource;
                } else {
                    throw new RuntimeException("No se puede leer el archivo ");
                }

            } catch (MalformedURLException e) {
                throw new RuntimeException("Error: " + e.getMessage());
            }
        } else if (modulo.equals(ModulosEnum.DOCENCIA.getValue())) {
            try {
                Path file = rootFilesDocencia.resolve(filename);
                Resource resource = new UrlResource(file.toUri());
                if (resource.exists() || resource.isReadable()) {
                    return resource;
                } else {
                    throw new RuntimeException("No se puede leer el archivo ");
                }

            } catch (MalformedURLException e) {
                throw new RuntimeException("Error: " + e.getMessage());
            }
        }
        return null;
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootFilesUploadsInvestigacion.toFile());
        FileSystemUtils.deleteRecursively(rootFileGuiaInvestigacion.toFile());
        FileSystemUtils.deleteRecursively(rootFilesVinculacion.toFile());
        FileSystemUtils.deleteRecursively(rootFileGuiaVinculacion.toFile());
        FileSystemUtils.deleteRecursively(rootFilesDocencia.toFile());
        FileSystemUtils.deleteRecursively(rootFileGuiaDocencia.toFile());
    }

    public Stream<Path> loadAll(String modulo) {
        if(modulo.equals(ModulosEnum.INVESTIGACION.getValue())){
            try {
                return Files.walk(this.rootFilesUploadsInvestigacion, 1)
                        .filter(path -> !path.equals(this.rootFilesUploadsInvestigacion))
                        .map(this.rootFilesUploadsInvestigacion::relativize);
            } catch (RuntimeException | IOException e) {
                throw new RuntimeException("No se pueden cargar los archivos ");
            }
        } else if (modulo.equals(ModulosEnum.VINCULACION.getValue())) {
            try {
                return Files.walk(this.rootFilesVinculacion, 1)
                        .filter(path -> !path.equals(this.rootFilesVinculacion))
                        .map(this.rootFilesVinculacion::relativize);
            } catch (RuntimeException | IOException e) {
                throw new RuntimeException("No se pueden cargar los archivos ");
            }
        } else if (modulo.equals(ModulosEnum.DOCENCIA.getValue())) {
            try {
                return Files.walk(this.rootFilesDocencia, 1)
                        .filter(path -> !path.equals(this.rootFilesDocencia))
                        .map(this.rootFilesDocencia::relativize);
            } catch (RuntimeException | IOException e) {
                throw new RuntimeException("No se pueden cargar los archivos ");
            }
        }

        return null;
    }

    public Stream<Path> loadAllFilesGuide(String modulo) {
        if(modulo.equals(ModulosEnum.INVESTIGACION.getValue())){
            try {
                return Files.walk(this.rootFileGuiaInvestigacion, 1)
                        .filter(path -> !path.equals(this.rootFileGuiaInvestigacion))
                        .map(this.rootFileGuiaInvestigacion::relativize);
            } catch (RuntimeException | IOException e) {
                throw new RuntimeException("No se pueden cargar los archivos ");
            }
        } else if (modulo.equals(ModulosEnum.VINCULACION.getValue())) {
            try {
                return Files.walk(this.rootFileGuiaVinculacion, 1)
                        .filter(path -> !path.equals(this.rootFileGuiaVinculacion))
                        .map(this.rootFileGuiaVinculacion::relativize);
            } catch (RuntimeException | IOException e) {
                throw new RuntimeException("No se pueden cargar los archivos ");
            }
        } else if (modulo.equals(ModulosEnum.DOCENCIA.getValue())) {
            try {
                return Files.walk(this.rootFileGuiaDocencia, 1)
                        .filter(path -> !path.equals(this.rootFileGuiaDocencia))
                        .map(this.rootFileGuiaDocencia::relativize);
            } catch (RuntimeException | IOException e) {
                throw new RuntimeException("No se pueden cargar los archivos ");
            }
        }

        return null;
    }

    private void saveFileGuia(Resource file, String modulo) {
        if (modulo.equals(ModulosEnum.INVESTIGACION.getValue())) {//Modulo a guardar
            try {
                //Files.deleteIfExists(this.rootFileGuiaInvestigacion.resolve(nameFile));
                Files.copy(file.getInputStream(), this.rootFileGuiaInvestigacion.resolve(file.getFilename()));
            } catch (IOException e) {
                throw new RuntimeException("No se puede guardar el archivo. Error " + e.getMessage());
            }
        } else if (modulo.equals(ModulosEnum.VINCULACION.getValue())) {
            try {
                //Files.deleteIfExists(this.rootFileGuiaVinculacion.resolve(nameFile));
                Files.copy(file.getInputStream(), this.rootFileGuiaVinculacion.resolve(file.getFilename()));
            } catch (IOException e) {
                throw new RuntimeException("No se puede guardar el archivo. Error " + e.getMessage());
            }
        } else if (modulo.equals(ModulosEnum.DOCENCIA.getValue())) {
            try {
                //Files.deleteIfExists(this.rootFileGuiaDocencia.resolve(nameFile));
                Files.copy(file.getInputStream(), this.rootFileGuiaDocencia.resolve(file.getFilename()));
            } catch (IOException e) {
                throw new RuntimeException("No se puede guardar el archivo. Error " + e.getMessage());
            }
        }
    }

    private void saveFile(Resource file,String modulo) {
        if (modulo.equals(ModulosEnum.INVESTIGACION.getValue())) {//Modulo a guardar
            try {
                //Files.deleteIfExists(this.rootFilesUploadsInvestigacion.resolve(file.getFilename()));
                Files.copy(file.getInputStream(), this.rootFilesUploadsInvestigacion.resolve(file.getFilename()));
            } catch (IOException e) {
                throw new RuntimeException("No se puede guardar el archivo. Error " +
                        e.getMessage());
            }
        } else if (modulo.equals(ModulosEnum.VINCULACION.getValue())) {//Modulo a guardar
            try {
                //Files.deleteIfExists(this.rootFilesVinculacion.resolve(nameFile));
                Files.copy(file.getInputStream(), this.rootFilesVinculacion.resolve(file.getFilename()));
            } catch (IOException e) {
                throw new RuntimeException("No se puede guardar el archivo. Error " +
                        e.getMessage());
            }
        } else if (modulo.equals(ModulosEnum.DOCENCIA.getValue())) {//Modulo a guardar
            try {
                //Files.deleteIfExists(this.rootFilesDocencia.resolve(file.getFilename()));
                Files.copy(file.getInputStream(), this.rootFilesDocencia.resolve(file.getFilename()));
            } catch (IOException e) {
                throw new RuntimeException("No se puede guardar el archivo. Error " +
                        e.getMessage());
            }
        }
    }

    public void getAllFilesAndSaveAll(){
        //Investigación
        ResponseEntity<Resource[]> response = this.restTemplate.getForEntity(
                baseURLs.getGprStorageURL() + "/getAllfilesTasksDocent/"
                        + ModulosEnum.INVESTIGACION.getValue(),
                Resource[].class);
        Resource[] objectArray = response.getBody();
        List<Resource> resources = Arrays.asList(objectArray);

        for (Resource resource: resources) {
            this.saveFile(resource,ModulosEnum.INVESTIGACION.getValue());
        }

        response = this.restTemplate.getForEntity(
                baseURLs.getGprStorageURL() + "/getAllfilesTasks/"
                        + ModulosEnum.INVESTIGACION.getValue(),
                Resource[].class);
        objectArray = response.getBody();
        resources = Arrays.asList(objectArray);

        for (Resource resource: resources) {
            this.saveFileGuia(resource,ModulosEnum.INVESTIGACION.getValue());
        }

        //Vinculación
        response = this.restTemplate.getForEntity(
                baseURLs.getGprStorageURL() + "/getAllfilesTasksDocent/"
                        + ModulosEnum.VINCULACION.getValue(),
                Resource[].class);
        objectArray = response.getBody();
        resources = Arrays.asList(objectArray);

        for (Resource resource: resources) {
            this.saveFile(resource,ModulosEnum.VINCULACION.getValue());
        }

        response = this.restTemplate.getForEntity(
                baseURLs.getGprStorageURL() + "/getAllfilesTasks/"
                        + ModulosEnum.VINCULACION.getValue(),
                Resource[].class);
        objectArray = response.getBody();
        resources = Arrays.asList(objectArray);

        for (Resource resource: resources) {
            this.saveFileGuia(resource,ModulosEnum.VINCULACION.getValue());
        }
        //Docencia
        response = this.restTemplate.getForEntity(
                baseURLs.getGprStorageURL() + "/getAllfilesTasksDocent/"
                        + ModulosEnum.DOCENCIA.getValue(),
                Resource[].class);
        objectArray = response.getBody();
        resources = Arrays.asList(objectArray);

        for (Resource resource: resources) {
            this.saveFile(resource,ModulosEnum.DOCENCIA.getValue());
        }

        response = this.restTemplate.getForEntity(
                baseURLs.getGprStorageURL() + "/getAllfilesTasks/"
                        + ModulosEnum.DOCENCIA.getValue(),
                Resource[].class);
        objectArray = response.getBody();
        resources = Arrays.asList(objectArray);

        for (Resource resource: resources) {
            this.saveFileGuia(resource,ModulosEnum.DOCENCIA.getValue());
        }
    }
}