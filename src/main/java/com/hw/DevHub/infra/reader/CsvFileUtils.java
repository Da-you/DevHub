package com.hw.DevHub.infra.reader;

import static com.hw.DevHub.global.constant.PathConst.PATH_ROOT;

import com.hw.DevHub.domain.cafe.dao.CafeRepository;
import com.hw.DevHub.domain.cafe.domain.Cafe;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.endpoints.internal.Value.Str;

@Slf4j
@Component
@RequiredArgsConstructor
public class CsvFileUtils {

    private final CafeRepository cafeRepository;
    public String PATH = PATH_ROOT + "/cafe_list.csv";

    @Transactional
//    @EventListener(ApplicationReadyEvent.class)
    public void readCsv() {
        log.info("데이터 저장 시작");
        CSVReader reader = null;
        long startTime = System.currentTimeMillis();
        try {
            reader = new CSVReader(new FileReader(PATH));
            String[] headers = reader.readNext();
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

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void refactorCSV() {
        log.info("csv read start");
        CSVReader reader = null;
        CSVWriter writer = null;
        long startTime = System.currentTimeMillis();
        try {
            reader = new CSVReader(new FileReader(PATH));
            writer = new CSVWriter(new FileWriter(PATH_ROOT + "/cafe_list_gyeong-gi_202409.csv"));

            String[] headers = {"이름", "도로명주소", "우편번호", "경도", "위도"};
            writer.writeNext(headers);
            List<String[]> data = reader.readAll().stream()
                .filter(row -> "카페".equals(row[8]))
                .map(row -> new String[]{
                    row[1] + " " + row[2], // 이름: 상호명 + 지점명
                    row[30] + " " + row[31], // 도로명주소: 도로명 + 건물명
                    row[33], // 우편번호
                    row[37], // 경도
                    row[38]  // 위도
                })
                .toList();
            writer.writeAll(data);
            long elapsedTime = System.currentTimeMillis() - startTime; // 소요 시간 계산
            log.info("데이터 수: {}, 소요 시간: {} ms", data.size(), elapsedTime);
            System.out.println("CSV 파일 생성 완료: ");

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        } finally {
            if (writer != null && reader != null) {
                try {
                    reader.close();
                    writer.close();
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
