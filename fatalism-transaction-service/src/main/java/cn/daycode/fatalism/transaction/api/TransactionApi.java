package cn.daycode.fatalism.transaction.api;

import cn.daycode.fatalism.api.transaction.model.*;
import cn.daycode.fatalism.common.domain.PageVO;
import cn.daycode.fatalism.common.domain.RestResponse;

import java.util.List;

public interface TransactionApi {

    RestResponse<ProjectDTO> createProject(ProjectDTO projectDTO);


    RestResponse<PageVO<ProjectDTO>> queryProjects(ProjectQueryDTO projectQueryDTO,
                                                   String order, Integer pageNo,
                                                   Integer pageSize, String sortBy);


    RestResponse<String> projectsApprovalStatus(Long id, String approveStatus);


    RestResponse<PageVO<ProjectDTO>> queryProjects(ProjectQueryDTO projectQueryDTO, Integer pageNo, Integer pageSize, String sortBy,String order);


    RestResponse<List<ProjectDTO>> queryProjectsIds(String ids);


    RestResponse<List<TenderOverviewDTO>> queryTendersByProjectId(Long id);


    RestResponse<TenderDTO> createTender(ProjectInvestDTO projectInvestDTO);


    RestResponse<String> loansApprovalStatus(Long id, String approveStatus, String  commission);

}
