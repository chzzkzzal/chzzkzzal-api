package com.chzzkzzal.zzal.domain.model;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.chzzkzzal.zzal.domain.model.AllowedFileExtension;

@Component
public class FileValidator {

    public void validateExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "파일 확장자가 올바르지 않습니다.");
        }
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (!AllowedFileExtension.isAllowed(extension)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "지원하지 않는 파일 확장자입니다.");
        }
    }
}
