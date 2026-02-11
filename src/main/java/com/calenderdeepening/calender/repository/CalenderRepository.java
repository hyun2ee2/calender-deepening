package com.calenderdeepening.calender.repository;

import com.calenderdeepening.calender.entity.CalenderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalenderRepository extends JpaRepository<CalenderEntity, Long> {

}
