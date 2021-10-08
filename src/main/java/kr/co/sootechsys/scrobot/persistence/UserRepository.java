package kr.co.sootechsys.scrobot.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import kr.co.sootechsys.scrobot.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

}
