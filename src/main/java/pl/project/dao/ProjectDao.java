package pl.project.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.project.model.Project;

@Repository
public interface ProjectDao extends CrudRepository<Project, Integer> {
	
	List<Project> findAllByOrderByDateDesc();

}
