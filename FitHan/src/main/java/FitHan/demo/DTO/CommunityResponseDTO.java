package FitHan.demo.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommunityResponseDTO {
    private Integer communityId;
    private String tag;
    private String title;
    private String contents;
    private Integer headCount;
    private Integer attendanceCount;

    public CommunityResponseDTO(Integer communityId, String tag, String title, String contents,
        Integer headCount, Integer attendanceCount) {
        this.communityId = communityId;
        this.tag = tag;
        this.title = title;
        this.contents = contents;
        this.headCount = headCount;
        this.attendanceCount = attendanceCount;
    }
}
