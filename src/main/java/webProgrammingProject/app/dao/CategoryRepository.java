package webProgrammingProject.app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import webProgrammingProject.app.model.Category;


@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
	
	@Query(value="select * from categories where id = :id", nativeQuery = true)
	public Category getCatogryById(@Param("id")Long id);
	
	@Query(value="select * from categories order by title asc", nativeQuery = true)
	public List<Category> findAllAlphabetic();
	
	@Query(value="select title from categories where id = :cId", nativeQuery = true)
	public String findCategoryTitle(@Param("cId")long cId);
}
