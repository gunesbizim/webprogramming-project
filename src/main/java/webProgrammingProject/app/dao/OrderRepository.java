package webProgrammingProject.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import webProgrammingProject.app.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
	
	public long count();
	
	public <S extends Order> S save(S entity);
	
	public List<Order> findAll();
	
	@Query(value = "select * from orders where id = :id", nativeQuery = true)
	public Order findById(@Param("id") long id);
	
	public void deleteById(Long id);
	
	@Query(value = "select * from orders order by 'ordertime' asc", nativeQuery = true)
	public List<Order> orderByOrderDateASC();
	
	@Query(value = "select * from orders order by 'ordertime' desc", nativeQuery = true)
	public List<Order> orderByOrderDateDESC();
	
	
	@Query(value = "select * from orders where email = :email order by 'ordertime' desc", nativeQuery = true)
	public List<Order> findByEmail(@Param("email") String email);
	
		
}
