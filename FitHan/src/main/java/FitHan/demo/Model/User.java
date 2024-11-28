package FitHan.demo.Model;


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
    private int User_id;

    private String username;
    private String password;
    private String phoneNumber;
    private String birthDay;
    private String gender;
    private String height;
    private String weight;

    public User(String username, String password, String phoneNumber, String birthDay,
        String gender, String height, String weight) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
    }

    public User() { }


}
