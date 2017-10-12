package pl.project.service;

import java.util.List;
import java.util.Set;
import pl.project.domain.security.UserRole;
import pl.project.model.User;

public interface UserService {

	void save(User user);
	
	User findByUsername(String username);
	
	User findByEmail(String email);
	
	User createUser(User user, Set<UserRole> userRoles);
	
	boolean checkUserExists(String username, String email);
	
	boolean checkUsernameExists(String username);
	
	boolean checkEmailExists(String email);
	
	List<User> getUserList();
	
	void delete(Integer id);
	
}
