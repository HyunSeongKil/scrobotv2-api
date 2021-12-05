package kr.co.sootechsys.scrobot.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import kr.co.sootechsys.scrobot.entity.Domain;

public interface DomainRepository extends JpaRepository<Domain, Long>, JpaSpecificationExecutor<Domain> {

}
