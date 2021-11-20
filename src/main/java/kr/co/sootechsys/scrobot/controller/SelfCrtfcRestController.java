package kr.co.sootechsys.scrobot.controller;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import kcb.module.v3.exception.OkCertException;
import kcb.org.json.JSONObject;
import lombok.extern.slf4j.Slf4j;


/**
 * 실명 인증
 */
@RestController
@RequestMapping("/self-crtfc")
@Slf4j
@Api(value = "실명인증 CONTROLLER")
public class SelfCrtfcRestController {

  @Value("${app.license.file.path}")
  private String licenseFilePath;

  @Value("${app.license.file.name}")
  private String licenseFileName;

  @Value("${app.target}")
  private String target;

  @Value("${app.svc.name.start}")
  private String svcNameStart;

  @Value("${app.svc.name.result}")
  private String svcNameResult;

  @Value("${app.cp.cd}")
  private String cpCd;

  @Value("${app.return.url}")
  private String returnUrl;


  @GetMapping("/step4")
  public ResponseEntity<Map<String, Object>> step4(@RequestParam String mdlTkn) throws OkCertException {

    JSONObject reqJson = new JSONObject();
    reqJson.put("MDL_TKN", mdlTkn);
    // reqJson.put("SITE_NAME", "수테크시스템즈(주)");
    // reqJson.put("SITE_URL", "sootechsys.co.kr");
    // reqJson.put("RQST_CAUS_CD", "00");
    // reqJson.put("RETURN_URL", returnUrl);
    String reqStr = reqJson.toString();

    //
    kcb.module.v3.OkCert okCert = new kcb.module.v3.OkCert();
    String resultStr = okCert.callOkCert(target, cpCd, svcNameResult, getLicenseFilePath().toString(), reqStr);

    //
    JSONObject resJson = new JSONObject(resultStr);

    log.debug("요청 : {}", reqJson);
    log.debug("응답 : {}", resJson);

    //
    String rsltCd = resJson.getString("RSLT_CD");
    String rsltMsg = resJson.getString("RSLT_MSG");
    String txSeqNo = resJson.getString("TX_SEQ_NO");

    return ResponseEntity.ok(Map.of("rsltCd", rsltCd, "rsltMsg", rsltMsg, "txSeqNo", txSeqNo));
  }



  @GetMapping("/step1")
  public ResponseEntity<Map<String, Object>> step1() throws URISyntaxException, OkCertException {

    JSONObject reqJson = new JSONObject();
    reqJson.put("RETURN_URL", returnUrl);
    reqJson.put("SITE_NAME", "수테크시스템즈(주)");
    reqJson.put("SITE_URL", "sootechsys.co.kr");
    reqJson.put("RQST_CAUS_CD", "00");
    String reqStr = reqJson.toString();

    kcb.module.v3.OkCert okCert = new kcb.module.v3.OkCert();

    String resultStr = okCert.callOkCert(target, cpCd, svcNameStart, getLicenseFilePath().toString(), reqStr);

    //
    JSONObject resJson = new JSONObject(resultStr);

    log.debug("요청 : {}", reqJson);
    log.debug("응답 : {}", resJson);

    String rsltCd = resJson.getString("RSLT_CD");
    String rsltMsg = resJson.getString("RSLT_MSG");
    String mdlTkn = "";
    boolean succ = false;

    if ("B000".equals(rsltCd) && resJson.has("MDL_TKN")) {
      mdlTkn = resJson.getString("MDL_TKN");
      succ = true;
    }

    //
    return ResponseEntity.ok(Map.of("mdlTkn", mdlTkn, "cpCd", cpCd));


  }


  private Path getLicenseFilePath() {
    return Paths.get(licenseFilePath, licenseFileName);

  }
}
