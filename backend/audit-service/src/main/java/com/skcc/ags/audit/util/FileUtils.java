package com.skcc.ags.audit.util;

import com.skcc.ags.audit.constant.AuditConstants;
import com.skcc.ags.audit.exception.AuditServiceException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for file operations.
 */
public final class FileUtils {
    
    private FileUtils() {
        // Prevent instantiation
    }

    /**
     * Validates and stores a file in the specified location.
     *
     * @param file The MultipartFile to store
     * @param documentType The type of document being stored
     * @return The path where the file was stored
     * @throws AuditServiceException if there's an error processing the file
     */
    public static String storeFile(MultipartFile file, String documentType) {
        validateFile(file);
        
        String fileName = generateFileName(file.getOriginalFilename(), documentType);
        Path targetLocation = Paths.get(AuditConstants.DOCUMENT_UPLOAD_PATH, fileName);
        
        try {
            Files.createDirectories(targetLocation.getParent());
            Files.copy(file.getInputStream(), targetLocation);
            return targetLocation.toString();
        } catch (IOException ex) {
            throw new AuditServiceException("Could not store file " + fileName, ex);
        }
    }

    private static void validateFile(MultipartFile file) {
        if (file.getSize() > AuditConstants.MAX_FILE_SIZE) {
            throw new AuditServiceException(AuditConstants.INVALID_FILE_SIZE);
        }
        
        String contentType = file.getContentType();
        if (contentType == null || !isValidFileType(contentType)) {
            throw new AuditServiceException(AuditConstants.INVALID_FILE_TYPE);
        }
    }

    private static boolean isValidFileType(String contentType) {
        return contentType.equals("application/pdf") ||
               contentType.equals("image/jpeg") ||
               contentType.equals("image/png");
    }

    private static String generateFileName(String originalFileName, String documentType) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String extension = getFileExtension(originalFileName);
        return String.format("%s_%s_%s%s", documentType, timestamp, 
            java.util.UUID.randomUUID().toString().substring(0, 8), extension);
    }

    private static String getFileExtension(String fileName) {
        int lastDot = fileName.lastIndexOf('.');
        return lastDot > 0 ? fileName.substring(lastDot) : "";
    }
} 