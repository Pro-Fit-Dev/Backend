package FitHan.demo.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommunitySaveDTO {

        private String tag;
        private String title;
        private String contents;
        private Integer headCount;
        private Integer userId;

        public CommunitySaveDTO() {}

        public CommunitySaveDTO(String tag, String title, String contents, Integer headCount,
            Integer userId) {
                this.tag = tag;
                this.title = title;
                this.contents = contents;
                this.headCount = headCount;
                this.userId = userId;
        }

}
