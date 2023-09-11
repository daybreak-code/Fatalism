package cn.daycode.fatalism.service;

import cn.daycode.fatalism.common.constant.ProjectStatusCode;
import cn.daycode.fatalism.common.util.EncryptUtil;
import cn.daycode.fatalism.domain.CreateProjectRequest;
import cn.daycode.fatalism.domain.CreateProjectResponse;
import cn.daycode.fatalism.domain.ModifyProjectRequest;
import cn.daycode.fatalism.domain.ModifyProjectResponse;
import cn.daycode.fatalism.entity.Project;
import cn.daycode.fatalism.mapper.ProjectMapper;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

	@Override
	public CreateProjectResponse createProject(String reqData) {
		String decodeReqData = EncryptUtil.decodeUTF8StringBase64(reqData);
		CreateProjectRequest request = JSON.parseObject(decodeReqData, CreateProjectRequest.class);
		String requestNo = request.getRequestNo();

		Project project = new Project();
		BeanUtils.copyProperties(request, project);
		project.setName(request.getProjectName());
		project.setDescription(request.getProjectDesc());
		project.setType(request.getProjectType());
		project.setPeriod(request.getProjectPeriod());
		project.setBorrowerAnnualRate(request.getAnnualRate());
		project.setAmount(request.getProjectAmount());
		project.setProjectStatus(ProjectStatusCode.RAISE.getCode());
		save(project);

		return new CreateProjectResponse(requestNo);
	}

	@Override
	public ModifyProjectResponse modifyProject(String reqData) {
		String decodeReqData = EncryptUtil.decodeUTF8StringBase64(reqData);
		ModifyProjectRequest request = JSON.parseObject(decodeReqData, ModifyProjectRequest.class);
		String requestNo = request.getRequestNo();

		update(Wrappers.<Project>update().lambda().eq(Project::getProjectNo, request.getProjectNo())
				.set(Project::getProjectStatus, request.getProjectStatus()));

		return new ModifyProjectResponse(requestNo);
	}

	@Override
	public Project getByProjectNo(String projectNo) {
		return getOne(Wrappers.<Project>query().lambda().eq(Project::getProjectNo, projectNo), false);
	}
}
