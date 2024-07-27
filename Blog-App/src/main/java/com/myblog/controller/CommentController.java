package com.myblog.controller;

import com.myblog.payload.CommentDto;
import com.myblog.payload.PostWithCommentDto;
import com.myblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;


    //http://localhost:8080/api/comments/{postId}
    @PostMapping("/{postId}")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable long postId){

        CommentDto dto = commentService.createComment(commentDto, postId);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostWithCommentDto> getAllCommentsByPostId(@PathVariable long postId){
        PostWithCommentDto allCommentsByPostId = commentService.getAllCommentsByPostId(postId);
        return new ResponseEntity<>(allCommentsByPostId,HttpStatus.OK);
    }


}
