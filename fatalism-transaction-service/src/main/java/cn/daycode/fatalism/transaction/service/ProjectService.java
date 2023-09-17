package cn.daycode.fatalism.transaction.service;

import cn.daycode.fatalism.api.transaction.model.*;
import cn.daycode.fatalism.common.domain.PageVO;
import cn.daycode.fatalism.transaction.entity.Project;

import java.util.List;

public interface ProjectService {

    ProjectDTO createProject(ProjectDTO project);


    PageVO<ProjectDTO> queryProjectsByQueryDTO(ProjectQueryDTO projectQueryDTO,
                                               String order, Integer pageNo, Integer pageSize, String sortBy);


    String projectsApprovalStatus(Long id, String approveStatus);


    PageVO<ProjectDTO> queryProjects(ProjectQueryDTO projectQueryDTO, String order, Integer pageNo, Integer pageSize, String sortBy);


    List<ProjectDTO> queryProjectsIds(String ids);


    List<TenderOverviewDTO> queryTendersByProjectId(Long id);


    TenderDTO createTender(ProjectInvestDTO projectInvestDTO);


    String loansApprovalStatus(Long id, String approveStatus, String commission);


    Boolean updateProjectStatusAndStartRepayment(Project project);
}
