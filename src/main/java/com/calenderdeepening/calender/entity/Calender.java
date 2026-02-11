package com.calenderdeepening.calender.entity;

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
@jakarta.persistence.Entity
@Table(name = "calender")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Calender {

    // 사용자 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 유저가 있어야만 게시글이 존재하기 때문에(optional은 false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    // optional과 nullable은 항상 짝이 맞아야하기 때문에 둘다 false(optional은 JPA에서 null의 허용 여부 nullable은 DB에서 null의 허용 여부)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 일정 제목(1~30사이의 텍스트 길이)
    @Column(length = 30, nullable = false)
    @Size(min = 1, max = 30)
    @NotBlank
    private String title;

    // 일정 내용(1~200사이의 텍스트 길이)
    @Column(length = 200, nullable = false)
    @Size(min = 1, max = 200)
    @NotBlank
    private String content;

    // 일정 작성일
    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    // 일정 수정일
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 생성자
    public Calender(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    // 행위 메서드
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }
}
