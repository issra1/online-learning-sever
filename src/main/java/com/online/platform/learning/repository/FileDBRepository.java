package com.online.platform.learning.repository;

import com.online.platform.learning.models.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {

    @Query("SELECT f.name FROM FileDB f")
    List<String> findAllFileNames();

    FileDB findByName(String name);

}
