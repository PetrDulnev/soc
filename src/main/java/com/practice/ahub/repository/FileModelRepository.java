package com.practice.ahub.repository;

import com.practice.ahub.model.FileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileModelRepository extends JpaRepository<FileModel, Long> {
    void deleteByFileName(String fileName);
    List<FileModel> findByDeletedIsTrue();
    void deleteByDeletedIsTrue();
    Optional<FileModel> findByFileName(String fileName);
}
