package com.example.newsblog.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Article extends AbstractEntity {

    private String title;

    private String content;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @PrePersist
    public void createdAt() {
        this.createdAt = LocalDateTime.now();
    }

}
