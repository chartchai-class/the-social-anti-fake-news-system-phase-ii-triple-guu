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
        @Autowired
        se331.lab.repository.UserRepository userRepository;

    @Override
    public void run(String... args) {
        if (newsRepository.count() == 0) { // Only seed if empty
            // Add mock users
            if (userRepository.count() == 0) {
                userRepository.save(se331.lab.entity.UserEntity.builder()
                        .username("reader1")
                        .email("reader1@example.com")
                        .password("password1")
                        .name("Reader")
                        .surname("One")
                        .role(se331.lab.entity.RoleType.READER)
                        .build());
                userRepository.save(se331.lab.entity.UserEntity.builder()
                        .username("member1")
                        .email("member1@example.com")
                        .password("password2")
                        .name("Member")
                        .surname("One")
                        .role(se331.lab.entity.RoleType.MEMBER)
                        .build());
                userRepository.save(se331.lab.entity.UserEntity.builder()
                        .username("admin1")
                        .email("admin1@example.com")
                        .password("password3")
                        .name("Admin")
                        .surname("One")
                        .role(se331.lab.entity.RoleType.ADMIN)
                        .build());
            }
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
