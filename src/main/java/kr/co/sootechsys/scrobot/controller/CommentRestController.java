package kr.co.sootechsys.scrobot.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.co.sootechsys.scrobot.domain.CommentDto;
import kr.co.sootechsys.scrobot.service.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentRestController {
  private CommentService service;

  public CommentRestController(CommentService service) {
    this.service = service;
  }

  @PostMapping("/by-bbs")
  @ApiOperation(value = "등록")
  public ResponseEntity<Map<String, Object>> registByBbsId(@RequestBody CommentDto dto, @RequestParam Long bbsId) {
    return ResponseEntity.ok(Map.of("data", service.registByBbsId(dto, bbsId)));
  }

  @PutMapping()
  @ApiOperation(value = "pk로 수정")
  public void update(@RequestBody CommentDto dto) {
    service.update(dto);
  }

  @DeleteMapping("/{commentId}")
  @ApiOperation(value = "pk로 삭제")
  public void deleteById(@PathVariable Long commentId) {
    service.deleteById(commentId);
  }

  @GetMapping("/by-bbs")
  @ApiOperation(value = "게시판아이디로 목록 조회")
  public ResponseEntity<Map<String, Object>> findAllByBbsId(@RequestParam Long bbsId) {
    return ResponseEntity.ok(Map.of("data", service.findAllByBbsId(bbsId)));
  }
}
