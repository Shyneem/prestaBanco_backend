package Prestabanco.Backend.controllers;


import Prestabanco.Backend.entities.FileUploadEntity;
import Prestabanco.Backend.services.FileUploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/upload")
public class FileUploadController {

    private FileUploadService fileUploadService;
    private static String UPLOAD_DIR = "./uploads/";

    @GetMapping("/")
    public ResponseEntity<ArrayList<FileUploadEntity>> getAllFiles(@RequestParam ("rut") String rut) {
        ArrayList<FileUploadEntity> files = fileUploadService.findAllByRut(rut);
        return (ResponseEntity<ArrayList<FileUploadEntity>>) ResponseEntity.ok();
    }


    @PostMapping("/up")
    public ResponseEntity<FileUploadEntity> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("rut") String rut) {
        try {
            // Guardar archivo en servidor
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
            Files.write(path, bytes);

            // Guardar la información del archivo en la base de datos
            FileUploadEntity fileEntity = new FileUploadEntity();
            fileEntity.setFileName(file.getOriginalFilename());
            fileEntity.setFilePath(path.toString());
            fileEntity.setRut(rut);
            FileUploadEntity fileUploaded = fileUploadService.saveFileUpload(fileEntity);

            return ResponseEntity.ok(fileUploaded);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}