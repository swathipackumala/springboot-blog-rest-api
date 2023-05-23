package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.blog.DTO.PostDto;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
@Service
public class PostServiceImpl implements PostService{
  
	private PostRepository postRepository;
	
	@Autowired
	public PostServiceImpl(PostRepository postRepository) {
		this.postRepository = postRepository;
	}


	@Override
	public PostDto createPost(PostDto postDto) {
		//convert DTo to entity
		Post post = mapToEntity(postDto);
		Post newPost = postRepository.save(post);
		//convert entity to DTO
		PostDto postResponse=mapToDto(newPost);
		return postResponse;
	}


	@Override
	public List<PostDto> getAllPosts() {
		List<Post> posts= postRepository.findAll();
		return posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
	}
	


	@Override
	public PostDto getPostByID(long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		return mapToDto(post);
	}


	@Override
	public PostDto updatePost(PostDto postDto,long id) {
        Post post= postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		 Post updatedPost=postRepository.save(post);
        return mapToDto(updatedPost);
	}


	@Override
	public void deletePostById(long id) {
		Post post=postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		postRepository.delete(post);
		
	}
     
	
	//Convert entity to DTO
	private PostDto mapToDto(Post newPost) {
		PostDto postResponse = new PostDto();
		postResponse.setId(newPost.getId());
		postResponse.setTitle(newPost.getTitle());
		postResponse.setDescription(newPost.getDescription());
		postResponse.setContent(newPost.getContent());
		return postResponse;
	}

    //Convert DTO to Entity
	private Post mapToEntity(PostDto postDto) {
		Post post = new Post();
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		
		return post;
	}


	
}
