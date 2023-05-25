package com.springboot.blog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.DTO.PostDto;
import com.springboot.blog.DTO.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;

@RestController
@RequestMapping("/api/posts")
public class PostController {
	
	private PostService postService;
	
	
	
	public PostController(PostService postService) {
		this.postService = postService;
	}


    @PostMapping
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
		return new ResponseEntity<>(postService.createPost(postDto),HttpStatus.OK);
	}
	
    @GetMapping
    public PostResponse getAllPosts(@RequestParam(value="pageNo", defaultValue=AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
    		                         @RequestParam(value="pageSize", defaultValue=AppConstants.DEFAULT_PAGE_SIZE, required= false) int pageSize,
    		                         @RequestParam(value="sortBy", defaultValue=AppConstants.DEFAULT_SORT_BY, required=false) String sortBy,
    		                         @RequestParam(value="sortDir", defaultValue=AppConstants.DEFAULT_SORT_DIR, required=false) String sortDir){
    	return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
    }
    @GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostByID(@PathVariable(name="id") long id){
		return ResponseEntity.ok(postService.getPostByID(id));
		
	}
	
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable(name="id") long id){
    	return new ResponseEntity<>(postService.updatePost(postDto, id),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable(name="id") long id){
    	postService.deletePostById(id);
    	return new ResponseEntity<>("Sucessfully deleted",HttpStatus.OK);
    }
	
	

}
