package pl.project.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.project.model.User;

@Repository
public interface UserDao extends CrudRepository<User, Integer> {
	
	User findByUsername(String username);
	User findByEmail(String email);
	List<User> findAllByOrderByLastNameAsc();

}
