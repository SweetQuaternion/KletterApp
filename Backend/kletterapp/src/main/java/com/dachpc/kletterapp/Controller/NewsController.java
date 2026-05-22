package com.dachpc.kletterapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dachpc.kletterapp.Dtos.NewsCreateDTO;
import com.dachpc.kletterapp.Dtos.NewsResponseDTO;
import com.dachpc.kletterapp.Services.NewsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/news")
public class NewsController {
    
    @Autowired
    private NewsService newsService;

    @GetMapping(produces = "application/json")
    public List<NewsResponseDTO> getNews(@RequestParam(required = false) Integer hallenId) {
        if (hallenId == null) {
            return newsService.getGeneralNews();
        } else {
            return newsService.getNewsByHalle(hallenId);
        }
    }

    @PostMapping(produces = "application/json")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public NewsResponseDTO addNews(@RequestBody NewsCreateDTO news) {
        return newsService.addNews(news);
    }

    @PutMapping(produces = "application/json")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public NewsResponseDTO updateNews(@PathVariable String id, @RequestBody NewsCreateDTO news) {
        return newsService.updateNews(Integer.parseInt(id), news);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNews(@PathVariable String id) {
        newsService.deleteNews(Integer.parseInt(id));
    }
    
}
