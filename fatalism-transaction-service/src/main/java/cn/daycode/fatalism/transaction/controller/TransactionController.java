package cn.daycode.fatalism.transaction.controller;

import cn.daycode.fatalism.api.transaction.model.*;
import cn.daycode.fatalism.common.domain.PageVO;
import cn.daycode.fatalism.common.domain.RestResponse;
import cn.daycode.fatalism.transaction.api.TransactionApi;
import cn.daycode.fatalism.transaction.service.ProjectService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class TransactionController implements TransactionApi {

    @Autowired
    private ProjectService projectService;

    @Override
    @ApiOperation("Borrower send Tender")
    @ApiImplicitParam(name = "project", value = "Tender Information", required = true,
            dataType = "Project", paramType = "body")
    @PostMapping("/my/projects")
    public RestResponse<ProjectDTO> createProject(@RequestBody ProjectDTO
                                                          projectDTO) {
        ProjectDTO dto = projectService.createProject(projectDTO);
        return RestResponse.success(dto);
    }

    @Override
    @ApiOperation("Index Tender infor")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectQueryDTO",
                    required = true, dataType = "ProjectQueryDTO", paramType = "body"),
            @ApiImplicitParam(name = "order", required = false,
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "pageNo",  required = true,
                    dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", required =
                    true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "sortBy", required = true,
                    dataType = "string", paramType =   "query")})
    @PostMapping("/projects/q")
    public RestResponse<PageVO<ProjectDTO>> queryProjects(@RequestBody ProjectQueryDTO projectQueryDTO, String order,
                                                          Integer pageNo, Integer pageSize, String sortBy) {
        PageVO<ProjectDTO> projects =
                projectService.queryProjectsByQueryDTO(projectQueryDTO, order, pageNo, pageSize,sortBy);
        return RestResponse.success(projects);
    }

    @Override
    @ApiOperation("Admin check tender information")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "tender id", required = true,
                    dataType = "long", paramType = "path"),
            @ApiImplicitParam(name = "approveStatus", value = "check status",
                    required = true, dataType = "ref", paramType = "path")
    })
    @PutMapping("/m/projects/{id}/projectStatus/{approveStatus}")
    public RestResponse<String> projectsApprovalStatus(
            @PathVariable("id") Long id,
            @PathVariable("approveStatus") String approveStatus){
        String result = projectService.projectsApprovalStatus(id,approveStatus);
        return RestResponse.success(result);
    }

    @Override
    @ApiOperation("index tender information")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectQueryDTO",
                    required = true, dataType = "ProjectQueryDTO", paramType = "body"),
            @ApiImplicitParam(name = "order", required = false,
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "pageNo",  required = true,
                    dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize",  required = true,
                    dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "sortBy", required = false,
                    dataType = "string", paramType = "query")})
    @PostMapping("/projects/indexes/q")
    public RestResponse<PageVO<ProjectDTO>> queryProjects(@RequestBody ProjectQueryDTO projectQueryDTO, Integer pageNo, Integer pageSize, String sortBy, String order){
        PageVO<ProjectDTO> projects = projectService.queryProjects(projectQueryDTO,
                order, pageNo, pageSize, sortBy);
        return RestResponse.success(projects);
    }

    @Override
    @ApiOperation("got multiple tender")
    @GetMapping("/projects/{ids}")
    public RestResponse<List<ProjectDTO>> queryProjectsIds(@PathVariable String ids) {
        List<ProjectDTO> projectDTOS=projectService.queryProjectsIds(ids);
        return RestResponse.success(projectDTOS);
    }

    @Override
    @ApiOperation("search tender record by tender id")
    @GetMapping("/tenders/projects/{id}")
    public RestResponse<List<TenderOverviewDTO>> queryTendersByProjectId(@PathVariable Long id) {
        return  RestResponse.success(projectService.queryTendersByProjectId(id));
    }

    @Override
    @ApiOperation("createTender")
    @ApiImplicitParam(name = "projectInvestDTO",
            required = true, dataType = "ProjectInvestDTO", paramType =
            "body")
    @PostMapping("/my/tenders")
    public RestResponse<TenderDTO> createTender(@RequestBody ProjectInvestDTO projectInvestDTO) {
        TenderDTO tenderDTO=projectService.createTender(projectInvestDTO);
        return RestResponse.success(tenderDTO);
    }

    @Override
    @ApiOperation("loansApprovalStatus")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "tenderId", required = true,
                    dataType = "long", paramType = "path"),
            @ApiImplicitParam(name = "approveStatus", value = "tender status", required =
                    true,
                    dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "commission", value = "platform ", required = true,
                    dataType = "string", paramType = "query")
    })
    @PutMapping("/m/loans/{id}/projectStatus/{approveStatus}")
    public RestResponse<String> loansApprovalStatus(@PathVariable("id") Long id, @PathVariable("approveStatus") String approveStatus, String commission) {
        String result=projectService.loansApprovalStatus(id,approveStatus,commission);
        return RestResponse.success(result);
    }
}
