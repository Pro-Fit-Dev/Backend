package FitHan.demo.Repository;

import FitHan.demo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User save(User user);

    User findByUserId(Integer userId);
    User findByPhoneNumber(String phoneNumber);

    boolean existsByNickName(String nickName);
    boolean existsByPhoneNumber(String phoneNumber);

}
