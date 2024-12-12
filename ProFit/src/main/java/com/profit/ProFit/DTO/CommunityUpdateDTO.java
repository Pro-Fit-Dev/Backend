package com.profit.ProFit.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommunityUpdateDTO {

    private Integer communityId;
    private String tag;
    private String title;
    private String contents;
}
