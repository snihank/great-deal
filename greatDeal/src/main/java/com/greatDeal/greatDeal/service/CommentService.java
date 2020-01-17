package com.greatDeal.greatDeal.service;


import com.greatDeal.greatDeal.model.Post;

public interface CommentService {

	public void saveComment(Post post, String username, String content);

}
