package com.dachpc.kletterapp.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dachpc.kletterapp.Entities.News;

public interface NewsRepository extends JpaRepository<News, Integer> {

    News getReferenceById(int id);
    List<News> findByHalle_id(int hallenId);
    @Query("SELECT n FROM News n WHERE n.halle IS NULL")
    List<News> findGeneral();
    void deleteById(int id);
    
}
