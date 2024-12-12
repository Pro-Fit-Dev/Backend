package com.profit.ProFit.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommunityResponseDTO {
    private Integer communityId;
    private String tag;
    private String title;
    private String contents;
    private Integer CommentCount;

    public CommunityResponseDTO(Integer communityId, String tag, String title, String contents,
        Integer commentCount) {
        this.communityId = communityId;
        this.tag = tag;
        this.title = title;
        this.contents = contents;
        this.CommentCount = commentCount;
    }
}
