package FitHan.demo.Service;

import FitHan.demo.Model.User;
import FitHan.demo.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /** 사용자 회원가입
     *
     * @param user 유저 객체
     * @return user 저장한 유저 객체
     */
    @Transactional
    public User join(User user) {
        // 1. 필수 필드 검증
        if (user.getPhoneNumber() == null || user.getPhoneNumber().isBlank()) {
            throw new IllegalArgumentException("휴대폰 번호는 필수 입력 사항입니다.");
        }
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new IllegalArgumentException("사용자 이름은 필수 입력 사항입니다.");
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("비밀번호는 필수 입력 사항입니다.");
        }

        // 1. 휴대폰 번호 중복 확인
        if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            throw new IllegalArgumentException("이미 가입된 휴대폰 번호입니다: " + user.getPhoneNumber());
        }

        // 2. 사용자 저장
        return userRepository.save(user);
    }

    /** 로그인
     *
     * @param phoneNumber 유저의 휴대폰 번호
     * @param password  유저의 비밀번호
     * @return 사용자 ID
     */
    @Transactional
    public User login(String phoneNumber, String password) {
        // 1. 휴대폰 번호로 사용자 조회
        User user = userRepository.findByPhoneNumber(phoneNumber);

        // 2. 사용자 존재 여부 확인
        if (user == null) {
            throw new IllegalArgumentException("해당 휴대폰 번호로 가입된 사용자가 없습니다.");
        }

        // 3. 비밀번호 일치 여부 확인
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 4. 로그인 성공, User 객체 반환
        return user;
    }


    /** 닉네임 변경
     *
     * @param userId  사용자 개인 ID (유저 확인용)
     * @param nickName  변경할 닉네임
     * @return 저장한 유저 객체
     */
    @Transactional
    public User updateNickName(Integer userId, String nickName) {
        // 1. 휴대폰 번호로 사용자 조회
        User user = userRepository.findByUserId(userId);

        if (user == null) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }

        // 2. 닉네임 중복 확인 (필요시 추가)
        if (userRepository.existsByNickName(nickName)) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다. 다른 닉네임을 입력해주세요.");
        }

        // 3. 닉네임 변경
        user.setNickName(nickName);

        // 4. 변경된 사용자 저장
        return userRepository.save(user);
    }


    /** 비밀번호 재설정
     *
     * @param userId 사용자 개인 ID (확인용)
     * @param password    사용자 기존 비밀번호
     * @param newPassword 새로운 비밀번호
     */
    @Transactional
    public void updatePassword(Integer userId, String password, String newPassword) {
        // 1. 휴대폰 번호로 사용자 조회
        User user = userRepository.findByUserId(userId);

        if (user == null) {
            throw new IllegalArgumentException("해당 휴대폰 번호를 가진 사용자가 존재하지 않습니다.");
        }

        // 2. 비밀번호 일치 여부 확인
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 3. 새 비밀번호 설정
        user.setPassword(newPassword);

        // 4. 변경된 사용자 저장
        userRepository.save(user);
    }

    /**
     * 사용자 ID를 통해 장애 여부를 확인합니다.
     *
     * @param userId 사용자 ID
     * @return "Yes": 장애인, "No": 비장애인
     */
    public String getUserDisabilityStatus(Integer userId) {
        // 예제: 데이터베이스에서 사용자 장애 여부를 확인하는 로직
        // 실제로는 사용자 정보를 DB에서 조회하여 disability 값을 반환하도록 구현
        // 예: return userRepository.findById(userId).map(User::getDisability).orElse("No");

        // 테스트용으로 임의의 값 반환
        return userId % 2 == 0 ? "Yes" : "No"; // 예: 짝수 ID는 장애인(Yes), 홀수 ID는 비장애인(No)
    }


}
