package kr.co.sootechsys.scrobot.domain;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Api(value = "배포 DTO")
public class DeployDto {

    private String prjctId;

    private String trgetSysId;
}
