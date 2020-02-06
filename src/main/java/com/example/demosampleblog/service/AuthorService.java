package com.example.demosampleblog.service;

import com.example.demosampleblog.domain.Author;

import java.util.List;

public interface AuthorService {
    Author create(Author author);
    Author findAuthor(int id);
    List<Author> findAll();
}
