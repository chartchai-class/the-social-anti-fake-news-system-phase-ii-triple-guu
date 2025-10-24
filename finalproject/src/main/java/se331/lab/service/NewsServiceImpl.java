package se331.lab.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import se331.lab.entity.News;
import se331.lab.entity.NewsStatus;
import se331.lab.entity.VoteType;
import se331.lab.repository.NewsRepository;
import se331.lab.repository.VoteRepository;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private static final Logger log = LoggerFactory.getLogger(NewsServiceImpl.class);
    private final NewsRepository newsRepository;
    private final VoteRepository voteRepository;

    @Override
    public Page<News> getAllNews(Pageable pageable) {
        return newsRepository.findAllNotDeleted(pageable);
    }

    @Override
    public Page<News> getAllNewsForAdmin(Pageable pageable) {
        return newsRepository.findAll(pageable);
    }

    @Override
    public News getNewsById(Long id) {
        return newsRepository.findByIdAndNotDeleted(id);
    }

    @Override
    public News getNewsByIdForAdmin(Long id) {
        return newsRepository.findById(id).orElse(null);
    }

    @Override
    public News addNews(News news) {
        log.info("Saving news: {}", news);
        if (news.getStatus() == null) news.setStatus(NewsStatus.UNKNOWN);
        News saved = newsRepository.save(news);
        log.info("Saved news with ID: {}", saved.getId());
        return saved;
    }

    @Override
    public void deleteNews(Long id) {
        News news = newsRepository.findById(id).orElse(null);
        if (news != null && !news.isDeleted()) {
            news.setDeleted(true);
            newsRepository.save(news);
        }
    }

    @Override
    public void recalculateNewsStatus(Long newsId) {
        Optional<News> maybe = newsRepository.findById(newsId);
        if (maybe.isEmpty()) return;
        News news = maybe.get();
        long total = news.getVotes() == null ? 0 : news.getVotes().size();
        long fake = news.getVotes() == null ? 0 : news.getVotes().stream().filter(v -> v.getVoteType() == VoteType.FAKE).count();
        long notFake = total - fake;
    if (total == 0) news.setStatus(NewsStatus.UNKNOWN);
    else if (fake > notFake) news.setStatus(NewsStatus.FAKE);
    else if (notFake > fake) news.setStatus(NewsStatus.NOT_FAKE);
    else news.setStatus(NewsStatus.NEUTRAL);
        newsRepository.save(news);
    }

    @Override
    public Page<News> searchNews(String query, Pageable pageable) {
        return newsRepository.searchNotDeleted(query, pageable);
    }

    @Override
    public Page<News> searchNewsForAdmin(String query, Pageable pageable) {
        return newsRepository.searchAll(query, pageable);
    }
}


