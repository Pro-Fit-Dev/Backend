package FitHan.demo.Service;

import FitHan.demo.Model.SportsVoucherUsage;
import FitHan.demo.Repository.SportsVoucherUsageRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SportsVoucherUsageService {

    private final SportsVoucherUsageRepository sportsVoucherUsageRepository;

    public SportsVoucherUsageService(SportsVoucherUsageRepository sportsVoucherUsageRepository) {
        this.sportsVoucherUsageRepository = sportsVoucherUsageRepository;
    }

    /**
     * 지역 정보를 기반으로 스포츠 바우처 사용 정보를 조회.
     *
     * @param cityName    시/도 이름 (예: 서울)
     * @param districtName 시군구 이름 (선택적, 예: 강서구)
     * @param itemName    종목 이름 (선택적, 예: 요가)
     * @return 지역에 해당하는 스포츠 바우처 정보 리스트
     */
    public List<SportsVoucherUsage> findByRegionAndItem(String cityName, String districtName, String itemName) {
        // cityName이 null이거나 비어있으면 예외 발생
        if (cityName == null || cityName.isEmpty()) {
            throw new IllegalArgumentException("시/도 정보가 누락되었습니다.");
        }

        List<SportsVoucherUsage> vouchers;

        if ("all".equalsIgnoreCase(cityName)) {
            // cityName이 "all"인 경우 전체 반환
            vouchers = sportsVoucherUsageRepository.findAll();
        } else if (districtName != null && !districtName.isEmpty() && itemName != null && !itemName.isEmpty()) {
            // 시/도 + 시군구 + 종목으로 검색
            vouchers = sportsVoucherUsageRepository.findByCityNameAndDistrictNameAndItemName(cityName, districtName, itemName);
        } else if (districtName != null && !districtName.isEmpty()) {
            // 시/도 + 시군구로 검색
            vouchers = sportsVoucherUsageRepository.findByCityNameAndDistrictName(cityName, districtName);
        } else if (itemName != null && !itemName.isEmpty()) {
            // 시/도 + 종목으로 검색
            vouchers = sportsVoucherUsageRepository.findByCityNameAndItemName(cityName, itemName);
        } else {
            // 시/도로만 검색
            vouchers = sportsVoucherUsageRepository.findByCityName(cityName);
        }

        // 결과가 없으면 예외 발생
        if (vouchers.isEmpty()) {
            throw new RuntimeException("조건에 맞는 스포츠 바우처 정보가 없습니다.");
        }

        return vouchers;
    }


}
