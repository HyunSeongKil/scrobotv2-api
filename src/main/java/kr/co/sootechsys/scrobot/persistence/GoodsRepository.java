package kr.co.sootechsys.scrobot.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import kr.co.sootechsys.scrobot.entity.Goods;

public interface GoodsRepository extends JpaRepository<Goods, Long>, JpaSpecificationExecutor<Goods> {
  /**
   * 사용자아이디로 목록 조회
   * 
   * @param userId 사용자아이디
   * @return
   */
  @Query(value = "SELECT a FROM Goods a INNER JOIN UserGoodsMapng mapng ON a.goodsId = mapng.goodsId WHERE mapng.userId = :userId ORDER BY mapng.registDt DESC")
  List<Goods> findAllByUserId(@Param("userId") String userId);
}
