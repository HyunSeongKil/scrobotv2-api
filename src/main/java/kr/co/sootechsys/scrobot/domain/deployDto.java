package kr.co.sootechsys.scrobot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class deployDto {
    private String prjctId;

    private String trgetSysId;
}
