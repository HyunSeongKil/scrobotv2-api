package kr.co.sootechsys.scrobot.domain;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Api(value = "용어사전 DTO")
public class WordDicaryDto {
    private Integer wordDicaryId;

    private String wordNm;

    private String wordEngNm;

    private String engAbrvNm;
}
