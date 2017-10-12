package pl.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.project.dao.ProjectDao;
import pl.project.model.Project;
import pl.project.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectDao projectDao;
	
	@Override
	public Project get(Integer id) {
		return projectDao.findOne(id);
	}
	
	@Override
	public List<Project> list() {
		return projectDao.findAllByOrderByDateDesc();
	}
	
	@Override
	public Project save(Project project) {
		return projectDao.save(project);
	}
	
	@Override
	public void delete(Integer id) {
		projectDao.delete(id);
	}
}
