package kr.co.sootechsys.scrobot.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import kr.co.sootechsys.scrobot.domain.PageableResult;
import kr.co.sootechsys.scrobot.domain.SearchStplatDto;
import kr.co.sootechsys.scrobot.domain.StplatDto;

public interface StplatService {

  Long regist(StplatDto dto);

  void update(StplatDto dto);

  void deleteById(Long stplatId);

  StplatDto findById(Long stplatId);

  PageableResult findAll(SearchStplatDto searchDto, Pageable pageable);
}
