package com.example.demosampleblog.controller;

import com.example.demosampleblog.domain.Blog;
import com.example.demosampleblog.service.AuthorService;
import com.example.demosampleblog.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
public class BlogController {
    private final BlogService blogService;
    private final AuthorService authorService;
    int bid;

    public BlogController(BlogService blogService, AuthorService authorService) {
        this.blogService = blogService;
        this.authorService = authorService;
    }
    @GetMapping("/blog")
    public String create(Model model){
        model.addAttribute("blog",new Blog());
        model.addAttribute("authors",authorService.findAll());
        return "admin/blogForm";
    }

    @PostMapping("/blog")
    public String process(@Valid Blog blog, BindingResult result, RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            return "admin/blogForm";
        }
        blogService.create(blog);
        redirectAttributes.addFlashAttribute("success",true);
        return "redirect:/blogs";
    }

    @GetMapping("/blogs")
    public String showAllBlog(Model model){
        model.addAttribute("blogs",blogService.findAll());
        model.addAttribute("success",model.containsAttribute("success"));
        model.addAttribute("delete",model.containsAttribute("delete"));
        model.addAttribute("update",model.containsAttribute("update"));
        return "admin/blogs";
    }

    @ModelAttribute(name = "categories")
    public List<String> categoryNames(){
        return Arrays.asList("Horror",
                "Comedy",
                "Tragedy",
                "Romance",
                "Adventure",
                "Action",
                "Travelling");
    }
    @GetMapping("/test")
    public String test(@ModelAttribute("categories")List<String> myList){
        myList.forEach(System.out::println);
        return "admin/authors";
    }

    @GetMapping("/blog/{cid}")
    public String showBlogDetails(@PathVariable("cid") int id, Model model){
        model.addAttribute("blog",blogService.findById(id));
        return "admin/blog";
    }

    @GetMapping("/blog/delete/{id}")
    public  String deleteBlog(@PathVariable int id,Model model,RedirectAttributes redirectAttributes){
        blogService.deleteById(id);
        //model.addAttribute("blogs",blogService.findAll());
        redirectAttributes.addFlashAttribute("delete",true);
        return "redirect:/blogs";
    }
    @GetMapping("/blog/update/{id}")
    public String updateBlog(@PathVariable int id,Model model){
        this.bid = id;
        model.addAttribute("blog",blogService.findById(id));
        model.addAttribute("authors",authorService.findAll());
        return "admin/updateForm";
    }
    @PostMapping("blog/update")
    public String processUpdateBlog(Blog blog,RedirectAttributes redirectAttributes){
        blogService.update(bid,blog);
        redirectAttributes.addFlashAttribute("update",true);
        return "redirect:/blogs";
    }
}
