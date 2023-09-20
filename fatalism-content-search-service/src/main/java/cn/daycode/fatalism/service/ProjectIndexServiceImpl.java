package cn.daycode.fatalism.service;

import cn.daycode.fatalism.api.search.model.ProjectQueryParamsDTO;
import cn.daycode.fatalism.api.transaction.model.ProjectDTO;
import cn.daycode.fatalism.common.domain.PageVO;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProjectIndexServiceImpl implements ProjectIndexService{
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Value("${wanxinp2p.es.index}")
    private String projectIndex;

    @Override
    public PageVO<ProjectDTO> queryProjectIndex(ProjectQueryParamsDTO projectQueryParamsDTO, Integer pageNo, Integer pageSize, String sortBy, String order) {

        SearchRequest searchRequest = new SearchRequest(projectIndex);


        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        if (StringUtils.isNotBlank(projectQueryParamsDTO.getName())) {
            queryBuilder.must(QueryBuilders.termQuery("name", projectQueryParamsDTO.getName()));
        }
        if (projectQueryParamsDTO.getStartPeriod() != null) {
            queryBuilder.must(QueryBuilders.rangeQuery("period").gte(projectQueryParamsDTO.getStartPeriod()));
        }
        if (projectQueryParamsDTO.getEndPeriod() != null) {
            queryBuilder.must(QueryBuilders.rangeQuery("period").lte(projectQueryParamsDTO.getEndPeriod()));
        }

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        if (StringUtils.isNotBlank(sortBy) && StringUtils.isNotBlank(order)) {
            if (order.toLowerCase().equals("asc")) {
                searchSourceBuilder.sort(sortBy, SortOrder.ASC);
            }
            if (order.toLowerCase().equals("desc")) {
                searchSourceBuilder.sort(sortBy, SortOrder.DESC);
            }
        } else {
            searchSourceBuilder.sort("createdate", SortOrder.DESC);
        }

        searchSourceBuilder.from((pageNo - 1) * pageSize);
        searchSourceBuilder.size(pageSize);

        searchRequest.source(searchSourceBuilder);

        List<ProjectDTO> list = new ArrayList<>();
        PageVO<ProjectDTO> pageVO = new PageVO<>();
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = searchResponse.getHits();
            long totalHits = hits.getTotalHits().value;
            pageVO.setTotal(totalHits);
            SearchHit[] searchHits = hits.getHits();
            for (SearchHit hit : searchHits) {
                ProjectDTO projectDTO = new ProjectDTO();
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                Double amount = (Double) sourceAsMap.get("amount");
                String projectstatus = (String) sourceAsMap.get("projectstatus");
                Integer period = Integer.parseInt(sourceAsMap.get("period").toString());
                String name = (String) sourceAsMap.get("name");
                String description = (String) sourceAsMap.get("description");
                BigDecimal annualRate = new BigDecimal(sourceAsMap.get("annualrate").toString());
                projectDTO.setAmount(new BigDecimal(amount));
                projectDTO.setProjectStatus(projectstatus);
                projectDTO.setPeriod(period);
                projectDTO.setName(name);
                projectDTO.setDescription(description);
//                projectDTO.setAnnualRate(annualRate);
                projectDTO.setId(Long.parseLong(sourceAsMap.get("id").toString()));
                list.add(projectDTO);
            }
        } catch (Exception e) {
            return null;
        }
        pageVO.setContent(list);
        pageVO.setPageSize(pageSize);
        pageVO.setPageNo(pageNo);
        return pageVO;

    }
}
