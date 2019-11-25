package lk.parinda.springtest.userData;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Integer> {
	
	@Query(value = "SELECT * FROM image_info WHERE id=?1", nativeQuery = true)
	List<Image> getImagesById(String id);
	
}
