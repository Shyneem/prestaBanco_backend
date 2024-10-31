package Prestabanco.Backend.controllers;

import Prestabanco.Backend.entities.FileUploadEntity;
import Prestabanco.Backend.entities.LoanRequestEntity;
import Prestabanco.Backend.services.FileUploadService;
import Prestabanco.Backend.services.LoanRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/upload")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private LoanRequestService loanRequestService;

    private static final String UPLOAD_DIR = "./uploads/";
    @PostMapping("/")
    public ResponseEntity<List<FileUploadEntity>> uploadFiles(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("loanRequestId") Long loanRequestId) {

        System.out.println("Files received: " + files.size());

        Path uploadDirPath = Paths.get(UPLOAD_DIR);
        if (Files.notExists(uploadDirPath)) {
            try {
                Files.createDirectories(uploadDirPath);
            } catch (IOException e) {
                return ResponseEntity.status(500).body(null);
            }
        }

        LoanRequestEntity loanRequest = loanRequestService.getLoanRequestById(loanRequestId);
        List<FileUploadEntity> uploadedFiles = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                // Guardar archivo en el servidor
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
                Files.write(path, bytes);

                // Guardar la información del archivo en la base de datos
                FileUploadEntity fileEntity = new FileUploadEntity();
                fileEntity.setFileName(file.getOriginalFilename());
                fileEntity.setFilePath(path.toString());
                fileEntity.setLoanRequest(loanRequest);
                FileUploadEntity fileUploaded = fileUploadService.saveFileUpload(fileEntity);

                uploadedFiles.add(fileUploaded);

            } catch (IOException e) {
                e.printStackTrace();
                // Retornar error si alguno de los archivos falla
                return ResponseEntity.status(500).body(uploadedFiles);
            }
        }

        return ResponseEntity.ok(uploadedFiles);  // Retorna todos los archivos subidos exitosamente
    }
}