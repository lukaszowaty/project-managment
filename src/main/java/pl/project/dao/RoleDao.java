package pl.project.dao;

import org.springframework.data.repository.CrudRepository;

import pl.project.domain.security.Role;

public interface RoleDao extends CrudRepository<Role, Integer> {
	
	Role findByName(String name);
	

}
