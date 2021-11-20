package kr.co.sootechsys.scrobot.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import io.swagger.annotations.Api;
import kr.co.sootechsys.scrobot.entity.WordDicary;

@Api(value = "용어사전 레포지토리")
public interface WordDicaryRepository extends JpaRepository<WordDicary, Integer> {

    List<WordDicary> findAllByWordNmContains(String wordNm);

}
