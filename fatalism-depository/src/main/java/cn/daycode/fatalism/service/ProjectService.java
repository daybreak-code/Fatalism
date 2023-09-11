package cn.daycode.fatalism.service;

import cn.daycode.fatalism.domain.CreateProjectResponse;
import cn.daycode.fatalism.domain.ModifyProjectResponse;
import cn.daycode.fatalism.entity.Project;
import com.baomidou.mybatisplus.extension.service.IService;


public interface ProjectService extends IService<Project> {


	CreateProjectResponse createProject(String reqData);


	ModifyProjectResponse modifyProject(String reqData);


	Project getByProjectNo(String projectNo);

}
