package kr.co.sootechsys.scrobot.controller;

import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.co.sootechsys.scrobot.domain.DomainDto;
import kr.co.sootechsys.scrobot.domain.PageableResult;
import kr.co.sootechsys.scrobot.domain.SearchDomainDto;
import kr.co.sootechsys.scrobot.service.DomainService;

@RestController
@RequestMapping("/domains")
public class DomainRestController {

  private DomainService service;

  public DomainRestController(DomainService service) {
    this.service = service;
  }

  @PostMapping()
  @ApiOperation(value = "등록")
  public ResponseEntity<Map<String, Object>> regist(@RequestBody DomainDto dto) {
    return ResponseEntity.ok(Map.of("data", service.regist(dto)));
  }

  @PutMapping()
  @ApiOperation(value = "수정")
  public void update(@RequestBody DomainDto dto) {
    service.update(dto);
  }

  @DeleteMapping("/{domainId}")
  @ApiOperation(value = "pk로 삭제")
  public void deleteById(@PathVariable Long domainId) {
    service.deleteById(domainId);
  }

  @GetMapping("/{domainId}")
  @ApiOperation(value = "pk로 조회")
  public ResponseEntity<Map<String, Object>> findById(@PathVariable Long domainId) {
    return ResponseEntity.ok(Map.of("data", service.findById(domainId)));
  }

  @GetMapping()
  @ApiOperation(value = "목록 조회")
  public ResponseEntity<PageableResult> findAll(SearchDomainDto searchDto, Pageable pageable) {
    return ResponseEntity.ok(service.findAll(searchDto, pageable));
  }

}