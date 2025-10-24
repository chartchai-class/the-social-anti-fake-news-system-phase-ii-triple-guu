package se331.lab;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import se331.lab.entity.News;
import se331.lab.entity.Comment;
import se331.lab.repository.NewsRepository;
import se331.lab.repository.CommentRepository;

@Component
public class DataSeeder implements CommandLineRunner {
    @Autowired
    NewsRepository newsRepository;
    @Autowired
    CommentRepository commentRepository;

    @Override
    public void run(String... args) {
        if (newsRepository.count() == 0) { // Only seed if empty
            for (int i = 1; i <= 10; i++) {
                News news = News.builder()
                    .topic("Sample News " + i)
                    .shortDetail("Short detail for news " + i)
                    .fullDetail("This is a sample news full detail for item " + i)
                    .reporterName("Reporter " + i)
                    .dateTime(java.time.LocalDateTime.now())
                    .status(se331.lab.entity.NewsStatus.UNKNOWN)
                    .build();
                newsRepository.save(news);

                for (int j = 1; j <= 3; j++) {
                    Comment comment = Comment.builder()
                        .content("Sample comment " + j + " for news " + i)
                        .dateTime(java.time.LocalDateTime.now())
                        .news(news)
                        .build();
                    commentRepository.save(comment);
                }
            }
        }
    }
}
