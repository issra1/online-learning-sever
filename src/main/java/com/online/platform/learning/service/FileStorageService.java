package com.online.platform.learning.service;

import com.online.platform.learning.models.FileDB;
import com.online.platform.learning.repository.FileDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileStorageService {

    @Autowired
    private FileDBRepository videoFileRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public FileDB store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());

        return videoFileRepository.save(FileDB);
    }

    public FileDB getFile(String id) {
        return videoFileRepository.findById(id).get();
    }

    public Stream<FileDB> loadAll() {
        return videoFileRepository.findAll().stream();
    }


    public List<String> getAllFileNames() {
        List<FileDB> fileDBList = videoFileRepository.findAll();
        return fileDBList.stream()
                .map(FileDB::getName)
                .collect(Collectors.toList());
    }

    public Path load(String filename) {
        return Paths.get(uploadDir).resolve(filename);
    }

    public Resource loadAsResource(String filename) {
        FileDB fileDB = videoFileRepository.findByName(filename);
        if (fileDB == null) {
            throw new RuntimeException("File not found: " + filename);
        }
        // Convert file content to a Resource
        return new ByteArrayResource(fileDB.getData());
    }

}
