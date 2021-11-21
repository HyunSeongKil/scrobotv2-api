package kr.co.sootechsys.scrobot.controller;

import java.util.Map;
import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.co.sootechsys.scrobot.domain.BbsDto;
import kr.co.sootechsys.scrobot.domain.SearchBbsDto;
import kr.co.sootechsys.scrobot.service.BbsService;

@RestController
@RequestMapping("/bbses")
public class BbsRestController {

  private BbsService service;

  public BbsRestController(BbsService service) {
    this.service = service;
  }

  @PostMapping()
  public ResponseEntity<Map<String, Object>> regist(@RequestBody BbsDto dto) throws IllegalStateException, IOException {

    return ResponseEntity.ok(Map.of("data", service.regist(dto)));
  }

  @PutMapping()
  public void updt(@RequestBody BbsDto dto) {
    service.updt(dto);
  }

  @DeleteMapping("/{bbsId}")
  public void delete(@PathVariable Long bbsId) {
    service.deleteById(bbsId);
  }

  @GetMapping()
  public ResponseEntity<Map<String, Object>> findAllBySearchDto(SearchBbsDto searchDto, Pageable pageable) {
    return ResponseEntity.ok(service.findAll(searchDto, pageable));
  }

  @GetMapping("/{bbsId}")
  public ResponseEntity<Map<String, Object>> findById(@PathVariable Long bbsId) {
    return ResponseEntity.ok(Map.of("data", service.findById(bbsId)));
  }
}
