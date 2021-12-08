package kr.co.sootechsys.scrobot.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import kr.co.sootechsys.scrobot.domain.DomainDto;
import kr.co.sootechsys.scrobot.domain.PageableResult;
import kr.co.sootechsys.scrobot.domain.SearchDomainDto;

public interface DomainService {
  Long regist(DomainDto dto);

  void update(DomainDto dto);

  void deleteById(Long domainId);

  DomainDto findById(Long domainId);

  PageableResult findAll(SearchDomainDto searchDto, Pageable pageable);

  /**
   * 엑셀파일 파싱 cell순서 1.도메인명 2.도메인그룹명 3.도메인분류명 4.설명 5.데이터타입 6.데이터길이 7.표준여부
   * 
   * @param excelFile
   * @return
   * @throws Exception
   */
  List<Map<String, Object>> parseExcel(MultipartFile excelFile) throws Exception;

  /**
   * 대용량 등록
   * 
   * @param dtos 값 들
   * @return 등록갯수
   */
  Integer registByBulk(List<DomainDto> dtos);
}
