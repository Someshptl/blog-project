package com.myblog.service;

import com.myblog.payload.CommentDto;
import com.myblog.payload.PostWithCommentDto;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto,long postId);

    PostWithCommentDto getAllCommentsByPostId(long postId);
;
}
