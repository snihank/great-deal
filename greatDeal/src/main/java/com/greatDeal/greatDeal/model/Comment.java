package com.greatDeal.greatDeal.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Comment implements Serializable {

    private static final long serialVersionUID = 34546474787L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Long id;
    private String username;
    @Column(columnDefinition = "text")
    private String content;
    @CreationTimestamp
    private Date postedDate;

}
