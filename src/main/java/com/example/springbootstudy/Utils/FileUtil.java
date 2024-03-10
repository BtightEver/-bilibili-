package com.example.springbootstudy.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtil {

    // 复制文件
    public static void copyFile(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }

    // 删除文件
    public static void deleteFile(File file) throws IOException {
        Files.delete(file.toPath());
    }

    // 检查文件是否存在
    public static boolean exist(File file) {
        return file.exists();
    }

    // 创建文件夹
    public static void createDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    // 读取文件内容
    public static String readFile(File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath()));
    }

    // 写入内容到文件
    public static void writeFile(File file, String content) throws IOException {
        Files.write(file.toPath(), content.getBytes());
    }

    public static byte[] readFileBytes(String filePath) throws IOException{
        File readFile = new File(filePath);
        return Files.readAllBytes(readFile.toPath());
    }
}

