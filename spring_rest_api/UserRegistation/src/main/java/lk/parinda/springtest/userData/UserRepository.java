package lk.parinda.springtest.userData;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query(value = "SELECT * FROM user_info WHERE name=?1", nativeQuery = true)
	List<User> getOneByName(String name);
	
	@Query(value = "SELECT * FROM user_info WHERE email=?1", nativeQuery = true)
	List<User> getOneByEmail(String email);
	
}
