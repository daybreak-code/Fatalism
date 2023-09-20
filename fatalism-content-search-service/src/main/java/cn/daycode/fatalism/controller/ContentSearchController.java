package cn.daycode.fatalism.controller;

import cn.daycode.fatalism.api.search.model.ProjectQueryParamsDTO;
import cn.daycode.fatalism.api.transaction.model.ProjectDTO;
import cn.daycode.fatalism.common.domain.PageVO;
import cn.daycode.fatalism.common.domain.RestResponse;
import cn.daycode.fatalism.service.ProjectIndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(value = "Index Service", tags = "ContentSearch", description = "Index Service API")
public class ContentSearchController {

    @Autowired
    private ProjectIndexService projectIndexService;


    @ApiOperation("Index Tender")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectQueryParamsDTO",
                    value = "projectQueryParamsDTO", required = true,
                    dataType = "ProjectQueryParamsDTO", paramType = "body"),
            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true,
                    dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize",
                    required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "sortBy", value = "sortBy",
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "order", value = "order", dataType =
                    "String",
                    paramType = "query")})
    @PostMapping(value = "/l/projects/indexes/q")
    public RestResponse<PageVO<ProjectDTO>> queryProjectIndex(
            @RequestBody ProjectQueryParamsDTO projectQueryParamsDTO,
            @RequestParam Integer pageNo,
            @RequestParam Integer pageSize,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String order){
        PageVO<ProjectDTO> pageVO=projectIndexService.queryProjectIndex(projectQueryParamsDTO,pageNo,pageSize,sortBy,order);
        return RestResponse.success(pageVO);
    }
}
