package ec.edu.espe.gpr.storage.model;

import org.springframework.core.io.Resource;

public class FileSaveRequest {

    private String nameFile;
    private Resource file;

    public FileSaveRequest() {
    }

    public FileSaveRequest(String nameFile, Resource file) {
        this.nameFile = nameFile;
        this.file = file;
    }

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public Resource getFile() {
        return file;
    }

    public void setFile(Resource file) {
        this.file = file;
    }
}