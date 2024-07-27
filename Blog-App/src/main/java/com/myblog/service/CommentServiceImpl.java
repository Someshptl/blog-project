package com.myblog.service;

import com.myblog.entity.Comment;
import com.myblog.entity.Post;
import com.myblog.exception.ResourceNotFound;
import com.myblog.payload.CommentDto;
import com.myblog.payload.PostDto;
import com.myblog.payload.PostWithCommentDto;
import com.myblog.repository.CommentRepository;
import com.myblog.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PostRepository postRepository;

    @Override
    public CommentDto createComment(CommentDto commentDto,long postId) {

        Optional<Post> byId = postRepository.findById(postId);
        Post post = byId.get();
        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        CommentDto dto = mapToDto(savedComment);
        return dto;
    }

    public PostWithCommentDto getAllCommentsByPostId(long id){

        Post post = postRepository.findById(id).orElseThrow(
                ()->new ResourceNotFound("Comment not found with postId: "+ id)
        );

        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setContent(post.getContent());

        List<Comment> comments = commentRepository.findByPostId(id);
        List<CommentDto> dtos = comments.stream().map(c -> mapToDto(c)).collect(Collectors.toList());
        PostWithCommentDto postWithCommentDto = new PostWithCommentDto();
        postWithCommentDto.setCommentDto(dtos);
        postWithCommentDto.setPostDto(dto);

        return postWithCommentDto;
    }



        Comment mapToEntity(CommentDto commentDto){
        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;
    }

    CommentDto mapToDto(Comment comment){
        return modelMapper.map(comment, CommentDto.class);
    }
}
