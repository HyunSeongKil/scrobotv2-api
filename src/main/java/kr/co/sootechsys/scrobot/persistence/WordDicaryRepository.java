package kr.co.sootechsys.scrobot.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.sootechsys.scrobot.entity.WordDicary;

public interface WordDicaryRepository extends JpaRepository<WordDicary, Integer> {

    List<WordDicary> findAllByWordNmContains(String wordNm);
    
}
