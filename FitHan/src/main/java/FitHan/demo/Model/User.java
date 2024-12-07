package FitHan.demo.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    private String username;

    @JsonIgnore
    private String password;

    private String phoneNumber;
    private String birthDay;
    private String gender;
    private String nickName;
    private String disability;

    public User() { }

    public User(String username, String password, String phoneNumber, String birthDay,
        String gender, String nickName, String disability) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.gender = gender;
        this.nickName = nickName;
        this.disability = disability;
    }

}
