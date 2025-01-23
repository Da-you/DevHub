package com.hw.devhub.domain.cafe.api;

import com.hw.devhub.domain.cafe.domain.Cafe;
import com.hw.devhub.domain.cafe.service.CafeService;
import com.hw.devhub.global.response.ApiResponse;
import com.hw.devhub.infra.reader.CsvFileUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cafe")
@RequiredArgsConstructor
public class CafeApiController {

    private final CsvFileUtils csvFileUtils;
    private final CafeService cafeService;

    @PostMapping
    public void rewriteCSV() {
        csvFileUtils.rewriteCSV();
    }

    @PostMapping("/insert")
    public void insertRDB() {
        csvFileUtils.readCsv();
    }

    @GetMapping("/search")
    public ApiResponse<List<Cafe>> getSearch(@RequestParam("name") String keyword) {
        return ApiResponse.success(cafeService.getSearchCafe(keyword));

    }
}
