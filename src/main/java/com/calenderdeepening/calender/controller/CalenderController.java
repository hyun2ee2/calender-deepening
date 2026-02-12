package com.calenderdeepening.calender.controller;

import com.calenderdeepening.calender.dto.*;
import com.calenderdeepening.calender.service.CalenderService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{calenders}")
public class CalenderController {

    private final CalenderService calenderService;

    // POST
    @PostMapping
    public ResponseEntity<CreateCalenderResponse> saveCalender(
            @RequestBody CreateCalenderRequest request,
            HttpSession session
    ) {
        Long usersId = getLoginUser(session);

        return ResponseEntity.status(HttpStatus.CREATED).body(calenderService.save(usersId, request));
    }

    // 다 건 GET
    @GetMapping
    public ResponseEntity<List<GetCalenderResponse>> getCalenders(
            HttpSession session
    ) {
        Long usersId = getLoginUser(session);
        return ResponseEntity.status(HttpStatus.OK).body(calenderService.getAll(usersId));
    }

    // 단 건 GET
    @GetMapping(("/{calenderId}"))
    public ResponseEntity<GetCalenderResponse> getCalender(
            @PathVariable Long calenderId,
            HttpSession session
    ) {
        Long userId = getLoginUser(session);
        return ResponseEntity.status(HttpStatus.OK).body((calenderService.getOne(userId, calenderId)));
    }

    // PUT
    @PutMapping("/{calenderId}")
    public ResponseEntity<UpdateCalenderResponse> updateCalender(
            @PathVariable Long calenderId,
            @RequestBody UpdateCalenderRequest request,
            HttpSession session
    ) {
        Long usersId = getLoginUser(session);
        return ResponseEntity.status(HttpStatus.OK).body(calenderService.update(usersId, calenderId, request));
    }

    // DELETE
    @DeleteMapping("/{calenderId}")
    public ResponseEntity<Void> deleteCalender(
            @PathVariable Long calenderId,
            HttpSession session
    ) {
        Long usersId = getLoginUser(session);
        calenderService.delete(usersId, calenderId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 로그인 체크 메서드
    private Long getLoginUser(HttpSession session) {
        Long userId = (Long) session.getAttribute("LOGIN_USER");

        if(userId == null) {
            throw new IllegalStateException("로그인이 필요한 서비스입니다.");
        }
        return userId;
    }
}
