package com.example.file_handler.controller;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;

@RestController
@RequestMapping("/files")
public class FileController {

    private static final String BASE_DIR = "uploads/";

    // 初始化目錄
    static {
        try {
            Files.createDirectories(Paths.get(BASE_DIR));
        } catch (IOException e) {
            throw new RuntimeException("初始化目錄失敗：" + e.getMessage());
        }
    }

 /**
     * 上傳檔案，並自動建立不存在的目錄
     * 
     * @param folder 使用者輸入的目錄名稱
     * @param file   上傳的檔案
     * @return 回傳上傳結果
     */
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("folder") String folder,
            @RequestParam("file") MultipartFile file) {
        try {
            // 檢查目錄名稱是否合法
            if (folder == null || folder.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("目錄名稱不可為空！");
            }

            // 確認目錄是否存在，若不存在則建立
            Path folderPath = Paths.get(BASE_DIR, folder);
            if (Files.notExists(folderPath)) {
                Files.createDirectories(folderPath); // 建立目錄
            }

            // 儲存檔案
            Path filePath = folderPath.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.ok("檔案已成功上傳至：" + filePath.toAbsolutePath());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("檔案上傳失敗：" + e.getMessage());
        }
    }
    /**
     * 檔案下載
     * 
     * @param folder 自訂目錄名稱
     * @param fileName 檔案名稱
     * @return 回傳檔案內容
     */
    @GetMapping("/download/{folder}/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String folder, @PathVariable String fileName) {
        try {
            Path filePath = Paths.get(BASE_DIR, folder, fileName);
            if (!Files.exists(filePath)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(("檔案不存在：" + filePath.toAbsolutePath()).getBytes());
            }

            byte[] fileContent = Files.readAllBytes(filePath);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(fileContent);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("檔案下載失敗：" + e.getMessage()).getBytes());
        }
    }
}

