package kr.co.sootechsys.scrobot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeployDto {
    private String prjctId;

    private String trgetSysId;
}
