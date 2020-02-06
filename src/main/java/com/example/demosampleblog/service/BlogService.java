package com.example.demosampleblog.service;

import com.example.demosampleblog.domain.Blog;

import java.util.List;

public interface BlogService {
    Blog create(Blog blog);
    Blog findById(int id);
    List<Blog> findAll();
}
