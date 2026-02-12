package com.calenderdeepening.calender.service;

import com.calenderdeepening.calender.dto.*;
import com.calenderdeepening.calender.entity.Calender;
import com.calenderdeepening.calender.repository.CalenderRepository;
import com.calenderdeepening.user.entity.User;
import com.calenderdeepening.user.repository.UserRepository;
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
    public CreateCalenderResponse save(Long userId, CreateCalenderRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 사용자입니다.(사용자를 생성해주세요.)")
        );

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
    public List<GetCalenderResponse> getAll(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 사용자입니다.")
        );

        List<Calender> calenders = calenderRepository.findByUser(user);
        return calenders.stream()
                .map(calender -> new GetCalenderResponse(
                        calender.getId(),
                        calender.getTitle(),
                        calender.getContent()
                ))
                .toList();
    }

    // 단 건 조회
    @Transactional(readOnly = true)
    public GetCalenderResponse getOne(Long calenderId) {
        Calender calender = calenderRepository.findById(calenderId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 일정입니다."));
        return new GetCalenderResponse(calender.getId(), calender.getTitle(), calender.getContent());
    }

    // 수정
    @Transactional
    public UpdateCalenderResponse update(Long userId, Long calenderId, UpdateCalenderRequest request) {
        Calender calender = calenderRepository.findById(calenderId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 일정입니다.")
        );

        // 인가된 사용자인지 검증
        if(!calender.getUser().getId().equals(userId)) {
            throw new IllegalStateException("일정을 수정할 권한이 없습니다.");
        }
        calender.update(request.getTitle(), request.getContent());
        return new UpdateCalenderResponse(calender.getId(), calender.getTitle(), calender.getContent());
    }

    // 삭제
    @Transactional
    public void delete(Long userId, Long calenderId) {
        Calender calender = calenderRepository.findById(calenderId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 일정입니다.")
        );

        // 인가된 사용자인지 검증
        if(!calender.getUser().getId().equals(userId)) {
            throw new IllegalStateException("일정을 삭제할 권한이 없습니다.");
        }
        calenderRepository.deleteById(calenderId);
    }


}
