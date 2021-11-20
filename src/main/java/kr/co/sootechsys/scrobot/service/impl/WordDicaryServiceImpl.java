package kr.co.sootechsys.scrobot.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import io.swagger.annotations.Api;
import kr.co.sootechsys.scrobot.domain.WordDicaryDto;
import kr.co.sootechsys.scrobot.entity.WordDicary;
import kr.co.sootechsys.scrobot.persistence.WordDicaryRepository;
import kr.co.sootechsys.scrobot.service.WordDicaryService;

@Service
@Api(value = "용어사전 서비스")
public class WordDicaryServiceImpl implements WordDicaryService {
    WordDicaryRepository repo;

    public WordDicaryServiceImpl(WordDicaryRepository repo) {
        this.repo = repo;
    }

    WordDicaryDto toDto(WordDicary e) {
        WordDicaryDto dto = WordDicaryDto.builder().build();
        dto.setEngAbrvNm(e.getEngAbrvNm());
        dto.setWordNm(e.getWordNm());

        return dto;
    }


    @Override
    public List<WordDicaryDto> findAllByWordNmLike(String wordNm) {
        List<WordDicaryDto> dtos = new ArrayList<>();

        List<WordDicary> entities = repo.findAllByWordNmContains(wordNm);

        entities.forEach(e -> {
            dtos.add(toDto(e));
        });


        return dtos;
    }

}
