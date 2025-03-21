package com.chzzkzzal.zzal.domain.model.metadata;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class PicMetadataExtractor implements MetadataExtractor{

    public ImageInfo extract(MultipartFile file){
        byte[] fileBytes = new byte[0];
        try {
            fileBytes = file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (ByteArrayInputStream bais = new ByteArrayInputStream(fileBytes)) {
            BufferedImage image = ImageIO.read(bais);
            if (image == null) {
                throw new IllegalArgumentException("이미지 파일이 아닙니다.");
            }
            int width = image.getWidth();
            int height = image.getHeight();
            // 파일 크기는 MultipartFile에서 직접 가져올 수 있음
            return new ImageInfo(file.getSize(), width, height, file.getContentType(), file.getOriginalFilename());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
