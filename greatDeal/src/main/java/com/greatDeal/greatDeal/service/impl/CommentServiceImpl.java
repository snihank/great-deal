package com.greatDeal.greatDeal.service.impl;

import com.greatDeal.greatDeal.model.Comment;
import com.greatDeal.greatDeal.model.Post;
import com.greatDeal.greatDeal.repository.CommentRepository;
import com.greatDeal.greatDeal.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

	@Autowired
	CommentRepository commentRepo;

	@Override
	public void saveComment(Post post, String username, String content) {
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setUsername(username);
		comment.setPostedDate(new Date());
		post.setComments(comment);
		commentRepo.save(comment);
	}

}
