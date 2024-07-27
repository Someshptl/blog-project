package com.myblog.controller;

import com.myblog.payload.ListPostDto;
import com.myblog.payload.PostDto;
import com.myblog.service.PostService;
import javax    .validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;


    //http://localhost:8080/api/posts
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult  bindingResult){

        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    //localhost:8080/api/posts/2 (since we are not using ? here so this becomes a Path Parameter @PathParam)

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id){

        postService.deletePost(id);
        return new ResponseEntity<>("Post is deleted",HttpStatus.OK);
    }
    //http://localhost:8080/api/posts?pageNo=0&pageSize=5&sortBy=title&sortDir=asc
    @GetMapping
    public ResponseEntity<ListPostDto> getAllPosts(
            @RequestParam(name = "pageNo",defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize",defaultValue = "5", required = false) int pageSize,
            @RequestParam(name = "sortBy",defaultValue = "id",required = false) String sortBy,
            @RequestParam(name = "sortDir",defaultValue = "asc",required = false) String sortDir
    ){
        ListPostDto listPostDto = postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(listPostDto,HttpStatus.OK);
    }

    //http://localhost:8080/api/posts/{id}
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostsById(@PathVariable long id){
        PostDto dto = postService.getPostsById(id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

}
