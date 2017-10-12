package pl.project.service;

import java.util.List;

import pl.project.model.Project;

public interface ProjectService {

	Project get(Integer id);
	
	List<Project> list();
	
	Project save(Project project);
	
	void delete(Integer id);
}
