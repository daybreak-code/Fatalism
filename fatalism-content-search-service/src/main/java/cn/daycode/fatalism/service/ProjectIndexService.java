package cn.daycode.fatalism.service;

import cn.daycode.fatalism.api.search.model.ProjectQueryParamsDTO;
import cn.daycode.fatalism.api.transaction.model.ProjectDTO;
import cn.daycode.fatalism.common.domain.PageVO;

public interface ProjectIndexService {

    PageVO<ProjectDTO> queryProjectIndex(ProjectQueryParamsDTO
                                                 projectQueryParamsDTO,
                                         Integer pageNo, Integer pageSize,
                                         String sortBy, String order);
}
