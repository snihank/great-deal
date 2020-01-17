package com.greatDeal.greatDeal.service;

import com.greatDeal.greatDeal.model.AppUser;
import com.greatDeal.greatDeal.model.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

public interface PostService {

    public Post savePost(AppUser appUser, HashMap<String, String> request, String postImageName);

    public List<Post> postList();

    public Post getPostById(Long id);

    public List<Post> findPostByUsername(String username);

    public Post deletePost(Post post);

    public String savePostImage(MultipartFile multipartFile, String fileName);

}
