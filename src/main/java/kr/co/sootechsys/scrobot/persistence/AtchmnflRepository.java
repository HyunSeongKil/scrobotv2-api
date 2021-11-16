package kr.co.sootechsys.scrobot.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import kr.co.sootechsys.scrobot.entity.Atchmnfl;


public interface AtchmnflRepository extends JpaRepository<Atchmnfl, Long> {

  List<Atchmnfl> findAllByAtchmnflGroupId(Long atchmnflGroupId);

  @Transactional
  @Modifying
  @Query(value = "DELETE FROM atchmnfl WHERE atchmnfl_group_id = :apndngFileGroupId", nativeQuery = true)
  int deleteWhereGroupId(@Param("apndngFileGroupId") Long apndngFileGroupId);

  void deleteAllByAtchmnflGroupId(Long atchmnflGroupId);

  @Query("SELECT a FROM Atchmnfl a INNER JOIN AtchmnflGroup b ON a.atchmnflGroupId = b.atchmnflGroupId WHERE b.prjctId = :prjctId")
  Iterable<Atchmnfl> findAllByPrjctId(@Param("prjctId") String prjctId);

}
