package FitHan.demo.Controller;

import FitHan.demo.Model.User;
import FitHan.demo.Service.UserService;
import jakarta.persistence.criteria.CriteriaBuilder.In;
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
     */
    @PostMapping("/join")
    public ResponseEntity<Map<String, Object>> join(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        try {
            userService.join(user);
            response.put("success", true);
            response.put("message", "회원가입이 성공적으로 완료되었습니다.");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(
        @RequestParam String phoneNumber,
        @RequestParam String password) {
        try {
            User user = userService.login(phoneNumber, password); // User 객체 반환
            return ResponseEntity.ok(user); // User 객체를 통으로 반환
        } catch (IllegalArgumentException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }



    /**
     * 닉네임 변경
     */
    @PutMapping("/nickname")
    public ResponseEntity<Map<String, Object>> updateNickName(
        @RequestParam Integer userId,
        @RequestParam String nickName) {
        Map<String, Object> response = new HashMap<>();
        try {
            User updatedUser = userService.updateNickName(userId, nickName);
            response.put("success", true);
            response.put("message", "닉네임이 성공적으로 변경되었습니다.");
            response.put("nickName", updatedUser.getNickName());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 비밀번호 변경
     */
    @PutMapping("/password")
    public ResponseEntity<Map<String, Object>> updatePassword(
        @RequestParam Integer userId,
        @RequestParam String password,
        @RequestParam String newPassword) {
        Map<String, Object> response = new HashMap<>();
        try {
            userService.updatePassword(userId, password, newPassword);
            response.put("success", true);
            response.put("message", "비밀번호가 성공적으로 변경되었습니다.");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }



}
