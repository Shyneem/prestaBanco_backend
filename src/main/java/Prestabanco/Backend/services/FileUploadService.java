package Prestabanco.Backend.services;

import Prestabanco.Backend.entities.FileUploadEntity;
import Prestabanco.Backend.entities.UserEntity;
import Prestabanco.Backend.repositories.FileUploadRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;


@Service
public class FileUploadService {
    @Autowired
    FileUploadRepository fileUploadRepository;


    public FileUploadEntity saveFileUpload(FileUploadEntity file){
        if(file == null){
            throw new NullPointerException("File cannot be null");
        }
        return fileUploadRepository.save(file);}

    public FileUploadEntity updateUser(FileUploadEntity file) {
        if (file == null){
            throw new NullPointerException("File cannot be null");
        }
        return fileUploadRepository.save(file);
    }

    public boolean deleteFileUpload(Long id) throws Exception {
        try{
            fileUploadRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }
}
