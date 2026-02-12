package com.calenderdeepening.calender.controller;

import com.calenderdeepening.calender.dto.*;
import com.calenderdeepening.calender.service.CalenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class CalenderController {

    private final CalenderService calenderService;

    // POST
    @PostMapping("/{usersId}/{calenders}")
    public ResponseEntity<CreateCalenderResponse> saveCalender(
            @PathVariable Long usersId,
            @RequestBody CreateCalenderRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(calenderService.save(usersId, request));
    }

    // 다 건 GET
    @GetMapping("/{usersId}/{calenders}")
    public ResponseEntity<List<GetCalenderResponse>> getCalenders(
            @PathVariable Long usersId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(calenderService.getAll(usersId));
    }

    // 단 건 GET
    @GetMapping(("/{usersId}/{calenders}/{calenderId}"))
    public ResponseEntity<GetCalenderResponse> getCalender(
            @PathVariable Long calenderId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body((calenderService.getOne(calenderId)));
    }

    // PUT
    @PutMapping("/{usersId}/{calenders}/{calenderId}")
    public ResponseEntity<UpdateCalenderResponse> updateCalender(
            @PathVariable Long usersId,
            @PathVariable Long calenderId,
            @RequestBody UpdateCalenderRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(calenderService.update(usersId, calenderId, request));
    }

    // DELETE
    @DeleteMapping("/{usersId}/{calenders}/{calenderId}")
    public ResponseEntity<Void> deleteCalender(
            @PathVariable Long usersId,
            @PathVariable Long calenderId
    ) {
        calenderService.delete(usersId, calenderId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
