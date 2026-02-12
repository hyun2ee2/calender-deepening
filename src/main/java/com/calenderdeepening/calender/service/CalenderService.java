package com.calenderdeepening.calender.service;

import com.calenderdeepening.calender.dto.*;
import com.calenderdeepening.calender.entity.Calender;
import com.calenderdeepening.calender.repository.CalenderRepository;
import com.calenderdeepening.user.entity.User;
import com.calenderdeepening.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CalenderService {

    private final CalenderRepository calenderRepository;
    private final UserRepository userRepository;

    // 생성
    @Transactional
    public CreateCalenderResponse save(HttpSession session, CreateCalenderRequest request) {
        User user = getLoginUser(session);

        Calender calender = new Calender(request.getTitle(), request.getContent(), user);
        Calender savedCalender = calenderRepository.save(calender);
        return new CreateCalenderResponse(
                savedCalender.getId(),
                savedCalender.getTitle(),
                savedCalender.getContent()
        );
    }

    // 다 건 조회
    @Transactional(readOnly = true)
    public List<GetCalenderResponse> getAll(HttpSession session) {
        User user = getLoginUser(session);
        return calenderRepository.findByUserId(user.getId())
                .stream()
                .map(calender -> new GetCalenderResponse(
                        calender.getId(),
                        calender.getTitle(),
                        calender.getContent()
                ))
                .toList();
    }

    // 단 건 조회
    @Transactional(readOnly = true)
    public GetCalenderResponse getOne(HttpSession session, Long calenderId) {
        User user = getLoginUser(session);
        Calender calender = calenderRepository.findByIdAndUserId(user.getId(), calenderId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 일정이거나 일정을 조회할 권한이 없습니다."));
        return new GetCalenderResponse(calender.getId(), calender.getTitle(), calender.getContent());
    }

    // 수정
    @Transactional
    public UpdateCalenderResponse update(HttpSession session, Long calenderId, UpdateCalenderRequest request) {
        User user = getLoginUser(session);
        Calender calender = calenderRepository.findByIdAndUserId(user.getId(), calenderId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 일정이거나 일정을 수정할 권한이 없습니다.")
        );
        calender.update(request.getTitle(), request.getContent());
        return new UpdateCalenderResponse(calender.getId(), calender.getTitle(), calender.getContent());
    }

    // 삭제
    @Transactional
    public void delete(HttpSession session, Long calenderId) {
        User user = getLoginUser(session);

        calenderRepository.findByIdAndUserId(calenderId, user.getId()).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 일정이거나 일정을 삭제할 권한이 없습니다.")
        );

        calenderRepository.deleteById(calenderId);
    }

    private User getLoginUser(HttpSession session) {
        Long loginUserId = (Long) session.getAttribute("LOGIN_USER");
        if (loginUserId == null) {
            throw new IllegalStateException("로그인이 필요한 서비스입니다.");
        }

        return userRepository.findById(loginUserId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 유저입니다.")
        );
    }
}
