package com.greatDeal.greatDeal.repository;


import com.greatDeal.greatDeal.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Long> {

}
