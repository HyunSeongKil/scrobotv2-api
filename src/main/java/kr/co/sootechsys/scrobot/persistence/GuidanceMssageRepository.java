package kr.co.sootechsys.scrobot.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.sootechsys.scrobot.entity.GuidanceMssage;

public interface GuidanceMssageRepository extends JpaRepository<GuidanceMssage, Long> {

}
