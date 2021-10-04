package kr.co.sootechsys.scrobot.service;

import java.util.List;

import kr.co.sootechsys.scrobot.domain.WordDicaryDto;

public interface WordDicaryService {
    List<WordDicaryDto> findAllByWordNmLike(String wordNm);
}
