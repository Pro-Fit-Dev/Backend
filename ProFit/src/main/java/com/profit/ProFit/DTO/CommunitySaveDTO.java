package com.profit.ProFit.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommunitySaveDTO {

    private String tag;
    private String title;
    private String contents;
    private Integer userId;

    public CommunitySaveDTO() {}

    public CommunitySaveDTO(String tag, String title, String contents,
        Integer userId) {
        this.tag = tag;
        this.title = title;
        this.contents = contents;
        this.userId = userId;
    }
}
