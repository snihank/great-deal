package com.greatDeal.greatDeal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Post implements Serializable {

    private static final long serialVersionUID = 34546474787L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Long id;
    private String name;
    @Column(columnDefinition = "text")
    private String caption;
    private String username;
    private String location;
    private int likes;
    @CreationTimestamp
    private Date postedDate;
    private Long userImageId;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name="post_id")
    private List<Comment> commentList;


    public Stream<Comment> getCommentList() {
        if (commentList != null) {
            return commentList.stream().sorted(Comparator.comparing(Comment::getPostedDate));
        }
        return null;
    }

    @JsonIgnore
    public void setComments(Comment comment) {
        if (comment != null) {
            this.commentList.add(comment);
        }
    }

    public void setLikes(int likes) {
        this.likes += likes;
    }


}
