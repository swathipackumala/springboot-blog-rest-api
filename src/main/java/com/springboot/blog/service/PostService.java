package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.DTO.PostDto;

public interface PostService {
	PostDto createPost(PostDto postDto);
	
    List<PostDto> getAllPosts();
    
    PostDto getPostByID(long id);
    
    PostDto updatePost(PostDto postDto,long id);
    
    void deletePostById(long id);

}
