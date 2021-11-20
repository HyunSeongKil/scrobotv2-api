package kr.co.sootechsys.scrobot.controller;

import java.io.IOException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.sootechsys.scrobot.domain.AtchmnflDto;
import kr.co.sootechsys.scrobot.misc.CmmnFileDownloadView;
import kr.co.sootechsys.scrobot.service.AtchmnflGroupService;
import kr.co.sootechsys.scrobot.service.AtchmnflService;

@RestController
@RequestMapping("/atchmnfls")
@Api(value = "파일 첨부 CONTROLLER")
public class AtchmnflRestController {


  private AtchmnflService service;
  private AtchmnflGroupService atchmnflGroupService;

  public AtchmnflRestController(AtchmnflService service, AtchmnflGroupService atchmnflGroupService) {
    this.service = service;
    this.atchmnflGroupService = atchmnflGroupService;
  }

  @PostMapping()
  @ApiOperation(value = "등록")
  public ResponseEntity<Map<String, Object>> regist(@RequestParam String prjctId, @RequestParam MultipartFile file) throws IllegalStateException, IOException {
    Long atchmnflGroupId = atchmnflGroupService.regist(prjctId);

    return ResponseEntity.ok(Map.of("data", service.regist(atchmnflGroupId, file)));
  }

  @GetMapping("/dwld-file")
  @ApiOperation(value = "파일 다운로드")
  public ModelAndView dwldFile(@RequestParam Long atchmnflId) {
    AtchmnflDto dto = service.findById(atchmnflId);

    ModelAndView mav = new ModelAndView(new CmmnFileDownloadView());
    mav.addObject(CmmnFileDownloadView.FILE, service.getFile(atchmnflId));
    mav.addObject(CmmnFileDownloadView.FILE_NAME, dto.getOriginalFileNm());

    return mav;
  }
}
