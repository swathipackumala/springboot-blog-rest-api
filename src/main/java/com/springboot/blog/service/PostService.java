package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.DTO.PostDto;
import com.springboot.blog.DTO.PostResponse;

public interface PostService {
	PostDto createPost(PostDto postDto);
	
	PostResponse getAllPosts(int pageNo, int pageSize, String sortBy,String sortDir);
    
    PostDto getPostByID(long id);
    
    PostDto updatePost(PostDto postDto,long id);
    
    void deletePostById(long id);

}
