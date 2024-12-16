package com.profit.ProFit.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "community")
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_id")
    private Integer communityId;

    private String tag;
    private String title;
    private String contents;

    @Column(name = "comment_count", nullable = false)
    private Integer commentCount = 0; // 기본값 0

    @ManyToOne
    @JoinColumn(name = "author", referencedColumnName = "user_id")
    private User author;

    public Community() {}

    public Community(String tag, String title, String contents,Integer commentCount,
        User author) {
        this.tag = tag;
        this.title = title;
        this.contents = contents;
        this.author = author;
        this.commentCount = 0;
    }

    // 댓글 수 증가 메서드
    public void incrementCommentCount() {
        if (this.commentCount == null) {
            this.commentCount = 0; // null이면 0으로 초기화
        }
        this.commentCount++;
    }

}
