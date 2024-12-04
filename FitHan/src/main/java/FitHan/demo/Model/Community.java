package FitHan.demo.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "community")
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_id")
    private Integer community_id;

    private String tag;
    private String title;
    private String contents;
    private Integer headCount;      //총 참가인원
    private Integer attendanceCount;

    @ManyToOne
    @JoinColumn(name = "author", referencedColumnName = "user_id")
    private User author;

    public Community() {}

    public Community(String tag, String title, String contents, int headCount, int attendanceCount,
        User author) {
        this.tag = tag;
        this.title = title;
        this.contents = contents;
        this.headCount = headCount;
        this.attendanceCount = attendanceCount;
        this.author = author;
    }
}
