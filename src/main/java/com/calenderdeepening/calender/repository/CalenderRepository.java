package com.calenderdeepening.calender.repository;

import com.calenderdeepening.calender.entity.Calender;
import com.calenderdeepening.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CalenderRepository extends JpaRepository<Calender, Long> {
    List<Calender> findAllByCalender(Calender calender);

    List<Calender> findByUser(User user);
}
