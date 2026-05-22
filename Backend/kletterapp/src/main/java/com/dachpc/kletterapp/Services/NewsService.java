package com.dachpc.kletterapp.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dachpc.kletterapp.Dtos.NewsCreateDTO;
import com.dachpc.kletterapp.Dtos.NewsResponseDTO;
import com.dachpc.kletterapp.Entities.News;
import com.dachpc.kletterapp.Entities.User;
import com.dachpc.kletterapp.Mappers.NewsMapper;
import com.dachpc.kletterapp.Repositories.HallenRepository;
import com.dachpc.kletterapp.Repositories.NewsRepository;
import com.dachpc.kletterapp.Repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class NewsService {
    
    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HallenRepository hallenRepository;

    public List<NewsResponseDTO> getGeneralNews() {
        List<News> news = newsRepository.findGeneral();
        return news.stream().map(newsMapper::toResponseDTO).toList();
    }

    public List<NewsResponseDTO> getNewsByHalle(int hallenId) {
        List<News> news = newsRepository.findByHalle_id(hallenId);
        return news.stream().map(newsMapper::toResponseDTO).toList();
    }

    public NewsResponseDTO addNews(NewsCreateDTO dto) {
        System.out.println("Received NewsCreateDTO: " + dto);
        News news = newsMapper.toEntity(dto);
        User user = userRepository.findById(dto.getAutorId()).orElseThrow(() -> new EntityNotFoundException("Autor nicht gefunden"));
        news.setUser(user);
        news.setDatum(java.time.LocalDateTime.now());
        if (dto.getHallenId() != null) {
            news.setHalle(hallenRepository.findById(dto.getHallenId()).orElseThrow(() -> new EntityNotFoundException("Halle nicht gefunden")));
        }
        News added = newsRepository.save(news);
        return newsMapper.toResponseDTO(added);
    }

    public NewsResponseDTO updateNews(int id, NewsCreateDTO dto) {
        News prevNews = newsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Zu aktualisierende News nicht gefunden"));
        newsMapper.updateEntity(dto, prevNews);
        News updated = newsRepository.save(prevNews);
        return newsMapper.toResponseDTO(updated);
    }

    public void deleteNews(int id) {
        newsRepository.deleteById(id);
    }
}
