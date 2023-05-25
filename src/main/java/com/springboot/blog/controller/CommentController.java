package com.springboot.blog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.DTO.CommentDto;
import com.springboot.blog.service.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {

	private CommentService commentSerice;

	public CommentController(CommentService commentSerice) {
		this.commentSerice = commentSerice;
	}
// http://localhost:8080/api/posts/1/comments
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId,
			@RequestBody CommentDto commentDto) {
		return new ResponseEntity<>(commentSerice.createComment(postId, commentDto), HttpStatus.CREATED);
	}
	@GetMapping("/posts/{postId}/comments")
	public List<CommentDto> getAllCommentsByPostId(@PathVariable(value="postId") long postId){
		return commentSerice.getAllCommentsByPostId(postId);
	}
	@GetMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> getCommentsById(@PathVariable long postId,@PathVariable long commentId){
		return new ResponseEntity<>(commentSerice.getCommentsById(postId, commentId),HttpStatus.OK);
	}
	@PutMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> updateCommentsById(@PathVariable long postId,@PathVariable long commentId,
			@RequestBody CommentDto commentDto){
				return new ResponseEntity<>(commentSerice.updateCommentById(postId, commentId, commentDto),HttpStatus.OK);
		
	}
	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<String> deleteCommentById(@PathVariable(value="postId") long postId, @PathVariable(value="commentId") long commentId){
		commentSerice.deleteCommentById(postId, commentId);
		return new ResponseEntity<>("Deleted successfully",HttpStatus.OK);
	}
	
	
}
