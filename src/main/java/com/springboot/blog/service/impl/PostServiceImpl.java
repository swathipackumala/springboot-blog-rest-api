package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.blog.DTO.PostDto;
import com.springboot.blog.DTO.PostResponse;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
@Service
public class PostServiceImpl implements PostService{
  
	private PostRepository postRepository;
	
	private ModelMapper modelMapper;
	
	
public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
		
		this.postRepository = postRepository;
		this.modelMapper = modelMapper;
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
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy,String sortDir) {
		
		//create pageable instance
		Sort sort= sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? 
				Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
		Pageable pagable= PageRequest.of(pageNo, pageSize, sort);
		Page<Post> posts= postRepository.findAll(pagable);
		List<Post> listofPosts=posts.getContent();
		List<PostDto> content= listofPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
	   PostResponse postResponse= new PostResponse();
	    postResponse.setContent(content);
	    postResponse.setPageNo(posts.getNumber());
	    postResponse.setPageSize(posts.getSize());
	    postResponse.setTotalPages(posts.getTotalPages());
	    postResponse.setLast(posts.isLast());
	    return postResponse;
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
		PostDto postResponse=modelMapper.map(newPost,PostDto.class);
//		PostDto postResponse = new PostDto();
//		postResponse.setId(newPost.getId());
//		postResponse.setTitle(newPost.getTitle());
//		postResponse.setDescription(newPost.getDescription());
//		postResponse.setContent(newPost.getContent());
		return postResponse;
	}

    //Convert DTO to Entity
	private Post mapToEntity(PostDto postDto) {
		Post post=modelMapper.map(postDto,Post.class);
//		Post post = new Post();
//		post.setTitle(postDto.getTitle());
//		post.setDescription(postDto.getDescription());
//		post.setContent(postDto.getContent());
		
		return post;
	}


	
}
