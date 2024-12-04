package FitHan.demo.Controller;

import FitHan.demo.Model.User;
import FitHan.demo.Service.UserService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 회원가입
     *
     * @param user 유저 객체
     * @return 성공 메시지 또는 오류 메시지
     */
    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody User user) {
        try {
            userService.join(user);
            return ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 로그인
     *
     * @param phoneNumber 유저의 휴대폰 번호
     * @param password    유저의 비밀번호
     * @return 로그인 성공 메시지 또는 오류 메시지
     */
    @GetMapping("/login")
    public ResponseEntity<String> login(
        @RequestParam String phoneNumber,
        @RequestParam String password) {
        String userPhoneNumber = userService.login(phoneNumber, password);
        return ResponseEntity.ok("로그인 성공! 휴대폰 번호: " + userPhoneNumber);
    }

    /**
     * 닉네임 변경
     *
     * @param userId 사용자 개인 ID
     * @param nickName    변경할 닉네임
     * @return 성공 메시지 또는 오류 메시지
     */
    @PutMapping("/nickname")
    public ResponseEntity<String> updateNickName(
        @RequestParam String userId,
        @RequestParam String nickName) {
        try {
            User updatedUser = userService.updateNickName(userId, nickName);
            return ResponseEntity.ok("닉네임이 성공적으로 변경되었습니다: " + updatedUser.getNickName());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 비밀번호 변경
     *
     * @param userId 사용자 개인 ID
     * @param password    사용자 기존 비밀번호
     * @param newPassword 새로운 비밀번호
     * @return 성공 메시지 또는 오류 메시지
     */
    @PutMapping("/password")
    public ResponseEntity<String> updatePassword(
        @RequestParam String userId,
        @RequestParam String password,
        @RequestParam String newPassword) {
        try {
            userService.updatePassword(userId, password, newPassword);
            return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 사용자 BMI 정보 반환
     *
     * @param userId 회원 개인 ID
     * @return 키와 몸무게 정보
     */
    @GetMapping("/bmi")
    public ResponseEntity<Map<String, Double>> getUserBmiInfo(@RequestParam String userId) {
        try {
            User user = userService.getUserBmiInfo(userId);

            // 키와 몸무게를 Map에 담아서 반환
            Map<String, Double> response = new HashMap<>();
            response.put("height", user.getHeight());
            response.put("weight", user.getWeight());

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }



}
