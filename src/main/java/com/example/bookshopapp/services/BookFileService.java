package com.example.bookshopapp.services;

import com.example.bookshopapp.data.BookFile;
import com.example.bookshopapp.repositories.BookFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookFileService {

    private BookFileRepository bookFileRepository;

    @Autowired
    public BookFileService(BookFileRepository bookFileRepository) {
        this.bookFileRepository = bookFileRepository;
    }

    public BookFile getBookFileById(Long id){
        return bookFileRepository.getReferenceById(id);
    }
}
