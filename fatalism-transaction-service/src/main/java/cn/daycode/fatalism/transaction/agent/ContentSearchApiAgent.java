package cn.daycode.fatalism.transaction.agent;

import cn.daycode.fatalism.api.transaction.model.ProjectDTO;
import cn.daycode.fatalism.api.transaction.model.ProjectQueryDTO;
import cn.daycode.fatalism.common.domain.PageVO;
import cn.daycode.fatalism.common.domain.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "content-search-service")
public interface ContentSearchApiAgent {
    @PostMapping(value = "/content-search/l/projects/indexes/q")
    RestResponse<PageVO<ProjectDTO>> queryProjectIndex(
            @RequestBody ProjectQueryDTO projectQueryParamsDTO,
            @RequestParam(value = "pageNo") Integer pageNo,
            @RequestParam(value = "pageSize") Integer pageSize,
            @RequestParam(value = "sortBy",required = false) String sortBy,
            @RequestParam(value = "order",required = false) String order);
}
