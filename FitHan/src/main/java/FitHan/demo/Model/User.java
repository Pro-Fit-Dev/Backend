package FitHan.demo.Model;


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
    private Integer user_id;

    private String username;
    private String password;
    private String phoneNumber;
    private String birthDay;
    private String gender;
    private Double height;
    private Double weight;
    private String nickName;

    public User() { }

    public User(String username, String password, String phoneNumber, String birthDay,
        String gender, Double height, Double weight, String nickName) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.nickName = nickName;
    }

}
