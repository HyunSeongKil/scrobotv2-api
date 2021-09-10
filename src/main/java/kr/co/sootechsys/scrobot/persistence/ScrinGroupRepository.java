package kr.co.sootechsys.scrobot.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.sootechsys.scrobot.entity.ScrinGroup;

public interface ScrinGroupRepository extends JpaRepository<ScrinGroup, String> {

  List<ScrinGroup> findAllByPrjctId(String prjctId);
  
}
