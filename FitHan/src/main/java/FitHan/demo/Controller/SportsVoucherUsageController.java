package FitHan.demo.Controller;

import FitHan.demo.Model.SportsVoucherUsage;
import FitHan.demo.Service.SportsVoucherUsageService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sports-voucher")
public class SportsVoucherUsageController {

    private final SportsVoucherUsageService sportsVoucherUsageService;

    public SportsVoucherUsageController(SportsVoucherUsageService sportsVoucherUsageService) {
        this.sportsVoucherUsageService = sportsVoucherUsageService;
    }

    /**
     * 지역 정보를 기반으로 스포츠 바우처 사용 정보를 조회.
     *
     * @param cityName   시/도 이름 (예: 서울)
     * @param districtName 시군구 이름 (선택적, 예: 강서구)
     * @param itemName    종목 이름 (선택적, 예: 요가)
     * @return 지역에 해당하는 스포츠 바우처 정보 리스트
     */
    @GetMapping("/search")
    public ResponseEntity<List<SportsVoucherUsage>> getSportsVoucherUsageByRegionAndItem(
        @RequestParam String cityName, // 필수 파라미터: 시/도명 (all인 경우 전체 데이터 반환)
        @RequestParam(required = false) String districtName, // 선택 파라미터: 시군구명
        @RequestParam(required = false) String itemName) { // 선택 파라미터: 종목명
        // 서비스 레이어 호출
        List<SportsVoucherUsage> results = sportsVoucherUsageService.findByRegionAndItem(cityName, districtName, itemName);

        // 결과를 반환
        return ResponseEntity.ok(results);
    }

    /**
     * 예외 처리: IllegalArgumentException
     * 잘못된 요청에 대한 에러 응답을 반환합니다.
     *
     * @param ex 발생한 예외
     * @return 에러 메시지를 포함한 응답
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청: " + ex.getMessage());
    }

    /**
     * 예외 처리: RuntimeException
     * 서버 내부 에러에 대한 응답을 반환합니다.
     *
     * @param ex 발생한 예외
     * @return 에러 메시지를 포함한 응답
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류: " + ex.getMessage());
    }





}
