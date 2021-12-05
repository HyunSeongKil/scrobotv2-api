package kr.co.sootechsys.scrobot.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import kr.co.sootechsys.scrobot.entity.Bbs;

public interface BbsRepository extends JpaRepository<Bbs, Long>, JpaSpecificationExecutor<Bbs> {

  Page<Bbs> findAllByBbsSeCdAndBbsSjNmContains(String bbsSeCd, String bbsSjNm, Pageable pageable);

  Page<Bbs> findAllByBbsSeCdAndBbsSjNmLikeAndBbsCnLike(String bbsSeCd, String bbsSjNm, String bbsCn, Pageable pageable);

  Page<Bbs> findAllByBbsSeCdAndBbsCnLike(String bbsSeCd, String bbsCn, Pageable pageable);

  Page<Bbs> findAllByBbsSeCd(String bbsSeCd, Pageable pageable);

}
