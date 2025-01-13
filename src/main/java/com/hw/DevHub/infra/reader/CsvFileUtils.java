package com.hw.DevHub.infra.reader;

import static com.hw.DevHub.global.constant.PathConst.PATH_ROOT;

import com.hw.DevHub.domain.cafe.dao.CafeJdbcRepository;
import com.hw.DevHub.domain.cafe.domain.Cafe;
import com.hw.DevHub.global.exception.ErrorCode;
import com.hw.DevHub.global.exception.GlobalException;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import jakarta.transaction.Transactional;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CsvFileUtils {

    private final CafeJdbcRepository cafeJdbcRepository;
    public String PATH = PATH_ROOT + "/cafe_list_seoul.csv";

    @Transactional
    public void readCsv() {
        try {
            CSVReader reader = new CSVReader(new FileReader(PATH));
            System.out.println(PATH);
            String[] header = reader.readNext(); // 헤더 스킵
            List<Cafe> cafeList = csvMapToCafe(reader);
            log.info("read cafe list : {}", cafeList.toString());
            cafeJdbcRepository.bulkInsert(cafeList);
            reader.close();
        } catch (IOException | CsvException e) {
            log.error(e.getMessage());
            throw new GlobalException(ErrorCode.CSV_READ_ERROR);
        }
    }

    @Transactional
    public void rewriteCSV() {
        try {
            CSVReader reader = new CSVReader(new FileReader(PATH));
            CSVWriter writer = new CSVWriter(new FileWriter(PATH_ROOT + "/cafe_list_seoul.csv"));

            String[] headers = {"이름", "도로명주소", "우편번호", "경도", "위도"};
            writer.writeNext(headers);
            List<String[]> cafeInfos = rewriteCafeInfos(reader);
            writer.writeAll(cafeInfos);
            writer.close();
            reader.close();
        } catch (IOException | CsvException e) {
            log.error(e.getMessage());
            throw new GlobalException(ErrorCode.CSV_REFACTOR_ERROR);
        }
    }

    /*
     * 원본 CSV 파일에서 필요 데이터만 추출 및 정제하는 메서드
     * 필요 데이터(이름, 도로명 주소, 우편 번호, 경도, 위도
     */
    private List<String[]> rewriteCafeInfos(CSVReader reader) throws IOException, CsvException {
        return reader.readAll().stream()
            .filter(row -> "카페".equals(row[8]))
            .map(row -> new String[]{
                row[1] + " " + row[2],
                row[30] + " " + row[31],
                row[33],
                row[37],
                row[38]
            })
            .toList();
    }


    private List<Cafe> csvMapToCafe(CSVReader rows) throws IOException, CsvException {
        return rows.readAll().stream()
            .map(row -> Cafe.builder()
                .name(row[0])
                .roadNamedAddress(row[1])
                .zipCode(row[2])
                .longitude(Double.parseDouble(row[3]))
                .latitude(Double.parseDouble(row[4]))
                .build())
            .toList();
    }
}
