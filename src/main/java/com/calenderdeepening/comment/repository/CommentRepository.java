package com.calenderdeepening.comment.repository;

import com.calenderdeepening.calender.entity.Calender;
import com.calenderdeepening.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByCalender(Calender calender);
}
