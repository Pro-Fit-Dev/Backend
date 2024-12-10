package FitHan.demo.Repository;

import FitHan.demo.Model.SportsVoucherUsage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportsVoucherUsageRepository extends JpaRepository<SportsVoucherUsage, Long> {

    List<SportsVoucherUsage> findByCityName(String cityName);

    List<SportsVoucherUsage> findByCityNameAndDistrictName(String cityName, String districtName);

    // 시/도 + 시군구 + 종목으로 검색
    List<SportsVoucherUsage> findByCityNameAndDistrictNameAndItemName(String cityName, String districtName, String itemName);

    // 시/도 + 종목으로 검색
    List<SportsVoucherUsage> findByCityNameAndItemName(String cityName, String itemName);

    // 전체 데이터 반환
    List<SportsVoucherUsage> findAll();



}
