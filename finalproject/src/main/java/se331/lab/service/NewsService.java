package se331.lab.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se331.lab.entity.News;

public interface NewsService {
    Page<News> getAllNews(Pageable pageable);
    Page<News> getAllNewsForAdmin(Pageable pageable);
    News getNewsById(Long id);
    News getNewsByIdForAdmin(Long id);
    News addNews(News news);
    void deleteNews(Long id);
    void recalculateNewsStatus(Long newsId);

    // Search methods
    Page<News> searchNews(String query, Pageable pageable);
    Page<News> searchNewsForAdmin(String query, Pageable pageable);
}
