package kr.co.sootechsys.scrobot.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.sootechsys.scrobot.entity.Scrin;

public interface ScrinRepository extends JpaRepository<Scrin, String> {

  List<Scrin> findAllByScrinGroupId(String scrinGroupId);
  
}
