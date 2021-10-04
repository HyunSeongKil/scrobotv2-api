package kr.co.sootechsys.scrobot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordDicaryDto {
    private Integer wordDicaryId;

    private String wordNm;

    private String wordEngNm;

    private String engAbrvNm;
}
