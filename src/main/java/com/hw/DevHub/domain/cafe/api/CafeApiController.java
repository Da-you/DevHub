package com.hw.DevHub.domain.cafe.api;

import com.hw.DevHub.infra.reader.CsvFileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cafe")
@RequiredArgsConstructor
public class CafeApiController {

    private final CsvFileUtils csvFileUtils;


    @PostMapping
    public void rewriteCSV(){
        csvFileUtils.rewriteCSV();
    }

    @PostMapping("/insert")
    public void insertRDB(){
        csvFileUtils.readCsv();
    }
}
