package kr.co.sootechsys.scrobot.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.sootechsys.scrobot.entity.Prjct;

public interface PrjctRepository extends JpaRepository<Prjct, String> {

  List<Prjct> findAllByUserId(String userId);
  
}
