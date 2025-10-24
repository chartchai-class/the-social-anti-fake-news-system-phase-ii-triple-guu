package se331.lab.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import se331.lab.dto.CommentDto;
import se331.lab.dto.NewsDto;
import se331.lab.dto.UserDto;
import se331.lab.entity.Comment;
import se331.lab.entity.News;
import se331.lab.entity.UserEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppMapper {
    AppMapper INSTANCE = Mappers.getMapper(AppMapper.class);

    @Mapping(source = "submittedBy.id", target = "submittedById")
    @Mapping(source = "fakeCount", target = "fakeCount")
    @Mapping(source = "notFakeCount", target = "notFakeCount")
    @Mapping(target = "commentCount", expression = "java(news.getCommentCount())")
    NewsDto newsToDto(News news);

    List<NewsDto> newsToDto(List<News> news);

    @Mapping(source = "user.id", target = "userId")
    CommentDto commentToDto(Comment comment);

    List<CommentDto> commentToDto(List<Comment> comments);

    UserDto userToDto(UserEntity user);
}
