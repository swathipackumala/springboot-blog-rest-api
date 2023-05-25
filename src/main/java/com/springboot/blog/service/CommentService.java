package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.DTO.CommentDto;

public interface CommentService {
CommentDto createComment(long postId, CommentDto commentDto);
List<CommentDto> getAllCommentsByPostId(long postId);
CommentDto getCommentsById(long postId,long commentId);
CommentDto updateCommentById(long postId, long commentId,CommentDto commentDto);
void deleteCommentById(long postId,long commentId);
}
