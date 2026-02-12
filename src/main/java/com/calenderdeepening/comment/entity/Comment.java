package com.calenderdeepening.comment.entity;

import com.calenderdeepening.calender.entity.Calender;
import com.calenderdeepening.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 댓글 내용(1~200글자 사이의 텍스트 길이)
    @Column(length = 200, nullable = false)
    @Size(min = 1, max = 200)
    @NotBlank
    private String content;

    // 일정 고유 식별자
    // 일정이 있어야만 댓글이 존재할 수 있음
    @ManyToOne(fetch = FetchType.LAZY, optional = false) //
    @JoinColumn(name = "calender_id", nullable = false)
    private Calender calender;

    // 유저 고유 식별자
    // 유저가 있어야만 댓글이 존재할 수 있음
    @ManyToOne(fetch = FetchType.LAZY, optional = false) //
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 댓글 생성일
    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    // 댓글 수정일
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Comment(String content, User user, Calender calender) {
        this.content = content;
        this.user = user;
        this.calender = calender;
    }

    public void update(String content) {
        this.content = content;
    }
}
