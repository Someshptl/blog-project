package com.myblog.service;

import com.myblog.payload.ListPostDto;
import com.myblog.payload.PostDto;

public interface PostService {

    public PostDto createPost(PostDto postDto);

    void deletePost(long id);

    ListPostDto getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostsById(long id);
}
