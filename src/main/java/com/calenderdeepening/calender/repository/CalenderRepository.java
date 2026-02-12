package com.calenderdeepening.calender.repository;

import com.calenderdeepening.calender.entity.Calender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CalenderRepository extends JpaRepository<Calender, Long> {

    List<Calender> findByUserId(Long userId);

    Optional<Calender> findByIdAndUserId(Long calenderId, Long userId);
}
