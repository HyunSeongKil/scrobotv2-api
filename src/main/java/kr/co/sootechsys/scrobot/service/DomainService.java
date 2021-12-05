package kr.co.sootechsys.scrobot.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import kr.co.sootechsys.scrobot.domain.DomainDto;
import kr.co.sootechsys.scrobot.domain.PageableResult;
import kr.co.sootechsys.scrobot.domain.SearchDomainDto;

public interface DomainService {
  Long regist(DomainDto dto);

  void update(DomainDto dto);

  void deleteById(Long domainId);

  DomainDto findById(Long domainId);

  PageableResult findAll(SearchDomainDto searchDto, Pageable pageable);
}
