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
@RequestMapping("/calenders")
public class CalenderController {

    private final CalenderService calenderService;

    // POST
    @PostMapping
    public ResponseEntity<CreateCalenderResponse> saveCalender(
            @RequestBody CreateCalenderRequest request,
            HttpSession session
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(calenderService.save(session, request));
    }

    // 다 건 GET
    @GetMapping
    public ResponseEntity<List<GetCalenderResponse>> getCalenders(
            HttpSession session
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(calenderService.getAll(session));
    }

    // 단 건 GET
    @GetMapping(("/{calenderId}"))
    public ResponseEntity<GetCalenderResponse> getCalender(
            @PathVariable Long calenderId,
            HttpSession session
    ) {
        return ResponseEntity.status(HttpStatus.OK).body((calenderService.getOne(session, calenderId)));
    }

    // PUT
    @PutMapping("/{calenderId}")
    public ResponseEntity<UpdateCalenderResponse> updateCalender(
            @PathVariable Long calenderId,
            @RequestBody UpdateCalenderRequest request,
            HttpSession session
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(calenderService.update(session, calenderId, request));
    }

    // DELETE
    @DeleteMapping("/{calenderId}")
    public ResponseEntity<Void> deleteCalender(
            @PathVariable Long calenderId,
            HttpSession session
    ) {
        calenderService.delete(session, calenderId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
