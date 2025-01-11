package com.hw.DevHub.infra.reader;

import static com.hw.DevHub.global.constant.PathConst.PATH_ROOT;

import com.hw.DevHub.domain.cafe.dao.CafeRepository;
import com.hw.DevHub.domain.cafe.domain.Cafe;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.transaction.Transactional;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CsvFileReader {

    private final CafeRepository cafeRepository;
    public String PATH = PATH_ROOT + "/cafe_list_seoul_202409.csv";

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void readCsv() {
        log.info("데이터 저장 시작");
        CSVReader reader = null;
        long startTime = System.currentTimeMillis();
        try {
            reader = new CSVReader(new FileReader(PATH));
            String[] headers = reader.readNext(); // 헤더를 읽음
            List<String[]> rows = reader.readAll();
            List<Cafe> cafeList = new ArrayList<>();
            for (int i = 0; i < rows.size(); i++) {
                if ("카페".equals(rows.get(i)[8])) {
                    Cafe cafe = convertCsvToCafe(rows.get(i));
                    cafeList.add(cafe);
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime; // 소요 시간 계산
            log.info("총 데이터 수 : {}, 데이터 수: {}, 소요 시간: {} ms", rows.size(), cafeList.size(),
                elapsedTime);
            cafeRepository.saveAll(cafeList);
        } catch (FileNotFoundException | CsvValidationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public Cafe convertCsvToCafe(String[] row) {
        String name = ""; // [1] + [2]
        String roadNamedAddress = ""; //[31] + [30] + [35] + [36]
        String zipCode = ""; // [33]
        double longitude = 0; // [37]
        double latitude = 0; // [38]
        for (int i = 1; i < row.length; i++) {
            name = row[1] + " " + row[2];
            roadNamedAddress = row[31] + " " + row[30];
            zipCode = row[33];
            longitude = Double.valueOf(row[37]);
            latitude = Double.valueOf(row[38]);
        }
        return Cafe.builder()
            .name(name)
            .roadNamedAddress(roadNamedAddress)
            .zipCode(zipCode)
            .longitude(longitude)
            .latitude(latitude)
            .build();
    }
}
