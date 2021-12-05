package kr.co.sootechsys.scrobot.domain;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageableResult {
  private List<?> data;

  private Page<?> page;

  private Integer number;

  private Integer numberOfElements;

  private Pageable pageable;

  private Integer size;

  private Sort sort;

  private Long totalElements;

  private Integer totalPages;
}
