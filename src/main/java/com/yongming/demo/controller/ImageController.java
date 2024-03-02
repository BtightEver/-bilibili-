package com.yongming.demo.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@CrossOrigin
@RequestMapping("/images")
public class ImageController {
    @GetMapping("")
    public ResponseEntity<Resource> getImage(@RequestParam String uid,@RequestParam String imageName) {
        System.out.println("执行到了imagecontroller，获得的imageName是"+imageName);
        try {
            Resource resource = new ClassPathResource("static/" + uid + "/images/" + imageName);
            // 检查资源是否存在
            if (resource.exists()) {
                // 禁用缓存
                CacheControl cacheControl = CacheControl.noCache().mustRevalidate();
                // 返回图片资源，并设置响应类型为图片类型
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
