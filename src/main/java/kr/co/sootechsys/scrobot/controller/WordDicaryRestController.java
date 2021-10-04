package kr.co.sootechsys.scrobot.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.sootechsys.scrobot.service.WordDicaryService;


/**
 * 용어 사전
 */
@RestController
@RequestMapping("/word-dicarys")
public class WordDicaryRestController {

    WordDicaryService service;
    
    public WordDicaryRestController(WordDicaryService service){
        this.service = service;
    }


    /**
     * 용어별 목록 조회
     * @param words 용어. 구분자:콤마 예)코드,명칭
     * @return
     */
    @GetMapping("/by-words")
    public ResponseEntity<Map<String,Object>> listByWords(@RequestParam String words){
      String[] arr = words.split(",");

      Map<String,Object> map = new LinkedHashMap<>();

      for(int i=0; i<arr.length; i++){
        map.put(arr[i], service.findAllByWordNmLike(arr[i]));
      }

      return ResponseEntity.ok(map);
    }
}
