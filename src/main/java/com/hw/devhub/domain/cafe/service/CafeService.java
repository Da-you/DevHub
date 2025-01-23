package com.hw.devhub.domain.cafe.service;

import com.hw.devhub.domain.cafe.dao.CafeQueryRepository;
import com.hw.devhub.domain.cafe.domain.Cafe;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CafeService {

    private final CafeQueryRepository cafeQueryRepository;

    public List<Cafe> getSearchCafe(String keyword) {
        return cafeQueryRepository.searchCafe(keyword);
    }


}
