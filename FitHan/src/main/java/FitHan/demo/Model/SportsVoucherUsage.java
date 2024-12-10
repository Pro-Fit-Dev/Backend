package FitHan.demo.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sports_voucher_usage")
@IdClass(SportsVoucherUsageId.class) // 복합 기본 키를 설정할 ID 클래스
public class SportsVoucherUsage {

    @Id
    @Column(name = "BSNS_NO", length = 10, nullable = false)
    private String businessNo; // 사업자등록번호

    @Id
    @Column(name = "COURSE_NO", length = 20, nullable = false)
    private String courseNo; // 강좌번호

    @Column(name = "FCLTY_NM", length = 200, nullable = false)
    private String facilityName; // 시설명

    @Column(name = "ITEM_CD", length = 30, nullable = false)
    private String itemCode; // 종목코드

    @Column(name = "ITEM_NM", length = 200, nullable = false)
    private String itemName; // 종목명

    @Column(name = "CTPRVN_CD", length = 30, nullable = false)
    private String cityCode; // 시도코드

    @Column(name = "CTPRVN_NM", length = 200, nullable = false)
    private String cityName; // 시도명

    @Column(name = "SIGNGU_CD", length = 30, nullable = false)
    private String districtCode; // 시군구코드

    @Column(name = "SIGNGU_NM", length = 200, nullable = false)
    private String districtName; // 시군구명

    @Column(name = "FCLTY_ADDR", length = 200, nullable = false)
    private String facilityAddress; // 시설주소

    @Column(name = "FCLTY_DETAIL_ADDR", length = 200)
    private String facilityDetailAddress; // 시설상세주소

    @Column(name = "ZIP_NO", length = 5, nullable = false)
    private String zipCode; // 우편번호

    @Column(name = "TEL_NO", length = 20)
    private String telephone; // 전화번호

    @Column(name = "COURSE_NM", length = 200, nullable = false)
    private String courseName; // 강좌명

    @Column(name = "COURSE_ESTBL_YEAR", length = 4, nullable = false)
    private String courseEstablishedYear; // 강좌개설년도

    @Column(name = "COURSE_ESTBL_MT", length = 2, nullable = false)
    private String courseEstablishedMonth; // 강좌개설월

    @Column(name = "COURSE_BEGIN_DE", length = 8, nullable = false)
    private String courseBeginDate; // 강좌시작일자

    @Column(name = "COURSE_END_DE", length = 8, nullable = false)
    private String courseEndDate; // 강좌종료일자

    @Column(name = "COURSE_REQST_NMPR_CO", precision = 38, scale = 0, nullable = false)
    private BigDecimal courseRequestNumber; // 강좌신청인원수

    @Column(name = "COURSE_PRC", precision = 28, scale = 5, nullable = false)
    private BigDecimal coursePrice; // 강좌가격

    public SportsVoucherUsage() {}

    public SportsVoucherUsage(String businessNo, String courseNo, String facilityName,
        String itemCode,
        String itemName, String cityCode, String cityName, String districtCode, String districtName,
        String facilityAddress, String facilityDetailAddress, String zipCode, String telephone,
        String courseName, String courseEstablishedYear, String courseEstablishedMonth,
        String courseBeginDate, String courseEndDate, BigDecimal courseRequestNumber,
        BigDecimal coursePrice) {
        this.businessNo = businessNo;
        this.courseNo = courseNo;
        this.facilityName = facilityName;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.cityCode = cityCode;
        this.cityName = cityName;
        this.districtCode = districtCode;
        this.districtName = districtName;
        this.facilityAddress = facilityAddress;
        this.facilityDetailAddress = facilityDetailAddress;
        this.zipCode = zipCode;
        this.telephone = telephone;
        this.courseName = courseName;
        this.courseEstablishedYear = courseEstablishedYear;
        this.courseEstablishedMonth = courseEstablishedMonth;
        this.courseBeginDate = courseBeginDate;
        this.courseEndDate = courseEndDate;
        this.courseRequestNumber = courseRequestNumber;
        this.coursePrice = coursePrice;
    }

}
