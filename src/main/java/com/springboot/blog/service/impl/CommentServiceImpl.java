package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.DTO.CommentDto;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
@Service
public class CommentServiceImpl implements CommentService{

	private CommentRepository commentRepository;
	
	private PostRepository postRepository;
	
	
	public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository) {
		this.commentRepository = commentRepository;
		this.postRepository=postRepository;
	}


	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {
		//Convert the DTo to Entity
		
		Comment comment= mapToEntity(commentDto);
		Post post=postRepository.findById(postId).orElseThrow(
				() -> new ResourceNotFoundException("Post","id",postId));
        comment.setPost(post);
		Comment newComment=commentRepository.save(comment);
		return mapToDto(newComment);
	}
	
	@Override
	public List<CommentDto> getAllCommentsByPostId(long postId) {
		List<Comment> comments=commentRepository.findByPostId(postId);
		//Convert Comment entity into the Dto using the streams
		return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
	}
	
	@Override
	public CommentDto getCommentsById(long postId, long commentId) {
		 Post post=postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		 Comment comment=commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment","id", commentId));
		 	if(!comment.getPost().getId().equals(post.getId())) {
		 		throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comments does not belong to the post");
		 	}
		 return mapToDto(comment);
	}

	
	@Override
	public CommentDto updateCommentById(long postId, long commentId, CommentDto commentDto) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comments does not belong to the post");
		}
		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());

		Comment updatedComment = commentRepository.save(comment);
		return mapToDto(updatedComment);
	}

	@Override
	public void deleteCommentById(long postId, long commentId) {
		Post post=postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		Comment comment=commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
	    if(!comment.getPost().getId().equals(post.getId())) {
	    	throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Commnets does not belongs to the post");
	    }
	    commentRepository.delete(comment);
	}

	
	//Convert an Entity into Dto
	private CommentDto mapToDto(Comment comment) {
		CommentDto commentDto= new CommentDto();
		commentDto.setId(comment.getId());
		commentDto.setName(comment.getName());
		commentDto.setEmail(comment.getEmail());
		commentDto.setBody(comment.getBody());
		return commentDto;
	}
	
	//Convert a DTO into an Entity
	private Comment mapToEntity(CommentDto commentDto) {
		Comment comment = new Comment();
		comment.setId(commentDto.getId());
		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());
		return comment;
		
	}



	


	


	
}
