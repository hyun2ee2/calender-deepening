package com.calenderdeepening.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // 사용자명(1~10글자 사이의 텍스트 길이)
    @Column(length = 10, nullable = false)
    @Size(min = 1, max = 10)
    @NotBlank
    private String author;
    
    // 사용자 이메일
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @Column(length = 100, nullable = false, unique = true)
    @Size(min = 1, max = 100)
    @NotBlank
    private String email;
    
    // 사용자 생성일
    @CreatedDate
    @Column(name = "created_user", updatable = false, nullable = false)
    private LocalDateTime createdUser;
    
    // 사용자 수정일
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedUser;

    // 생성자
    public User(String author, String email) {
        this.author = author;
        this.email = email;
    }

    // 행위 메서드
    public void updateProfile(String author, String email) {
        this.author = author;
        this.email = email;
    }
}
