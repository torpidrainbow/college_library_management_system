package com.librarymanagement.collegelibrarymanagementsystem.model.repository;

import com.librarymanagement.collegelibrarymanagementsystem.model.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRespository extends JpaRepository<Record, Long> {
}
