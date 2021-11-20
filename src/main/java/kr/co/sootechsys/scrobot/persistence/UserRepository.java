package kr.co.sootechsys.scrobot.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import io.swagger.annotations.Api;
import kr.co.sootechsys.scrobot.entity.User;

@Api(value = "회원 레포지토리")
public interface UserRepository extends JpaRepository<User, String> {

}
