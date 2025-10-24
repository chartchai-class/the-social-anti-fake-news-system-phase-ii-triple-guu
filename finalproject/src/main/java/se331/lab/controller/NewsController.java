package se331.lab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import se331.lab.dto.NewsDto;
import se331.lab.entity.News;
import se331.lab.service.NewsService;
import se331.lab.util.AppMapper;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/news")
public class NewsController {
    private final NewsService newsService;
    private final AppMapper mapper;


    @GetMapping
    @PreAuthorize("hasAnyRole('READER','MEMBER','ADMIN')")
    public ResponseEntity<?> list(@RequestParam(value = "_limit", required = false) Integer perPage,
                                  @RequestParam(value = "_page", required = false) Integer page,
                                  @RequestHeader(value = "Role", required = false) String role) {
        perPage = perPage == null ? 10 : perPage;
        page = page == null ? 1 : page;
        Page<News> result;
        // If role is ADMIN, show all news including deleted
        if (role != null && role.equalsIgnoreCase("ADMIN")) {
            result = newsService.getAllNewsForAdmin(PageRequest.of(page-1, perPage));
        } else {
            result = newsService.getAllNews(PageRequest.of(page-1, perPage));
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-total-count", String.valueOf(result.getTotalElements()));
        List<NewsDto> dtos = mapper.newsToDto(result.getContent());
        return new ResponseEntity<>(dtos, headers, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('READER','MEMBER','ADMIN')")
    public ResponseEntity<?> get(@PathVariable Long id, @RequestHeader(value = "Role", required = false) String role) {
        News news;
        if (role != null && role.equalsIgnoreCase("ADMIN")) {
            news = newsService.getNewsByIdForAdmin(id);
        } else {
            news = newsService.getNewsById(id);
        }
        if (news == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(mapper.newsToDto(news));
    }

    @PostMapping
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> add(@RequestBody News news) {
        News created = newsService.addNews(news);
        return ResponseEntity.ok(mapper.newsToDto(created));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        newsService.deleteNews(id);
        return ResponseEntity.noContent().build();
    }
}
