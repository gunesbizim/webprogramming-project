package webProgrammingProject.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import webProgrammingProject.app.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	public List<Product> findAll();
	
	@Query(value = "select * from products where category_id = :cId", nativeQuery = true)
	public List<Product> findByCategoryId(@Param("cId") long cId);
	
	@Query(value = "select * from products where id = :pid", nativeQuery = true)
	public Product findSingleProductById(@Param("pid") long pid);
}
