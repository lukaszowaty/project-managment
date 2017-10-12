package pl.project.service.impl;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pl.project.dao.RoleDao;
import pl.project.dao.UserDao;
import pl.project.domain.security.UserRole;
import pl.project.model.User;
import pl.project.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	public void save(User user) {
		userDao.save(user);
	}
	
	@Override
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}
	@Override
	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}
	
	@Override
	public User createUser(User user, Set<UserRole> userRoles) {
		User localUser = userDao.findByUsername(user.getUsername());
		
		if(localUser != null) {
			LOG.info("User with isername {} already exist. Nothing will be done . ", user.getUsername());
		} else {
			String encryptedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encryptedPassword);
			
			for(UserRole ur : userRoles) {
				roleDao.save(ur.getRole());
			}
			
			user.getUserRoles().addAll(userRoles);
			
			
			localUser = userDao.save(user);
			
		}
		
		return localUser;
	}
	
	@Override
	public boolean checkUserExists(String login, String email) {
		if(checkUsernameExists(login) || checkEmailExists(email)) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean checkUsernameExists(String username) {
		if(null != findByUsername(username)) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean checkEmailExists(String email) {
		if(null != findByEmail(email)) {
			return true;
		}
		
		return false;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUsername(username);
		if(user == null) {
			LOG.warn("Login {} not found", username);
			throw new UsernameNotFoundException("Login " + username + " not found");
		}
		return user;
	}
	
	@Override
	public List<User> getUserList() {
		return userDao.findAllByOrderByLastNameAsc();
	}

	@Override
	public void delete(Integer id) {
		userDao.delete(id);
	}


}
