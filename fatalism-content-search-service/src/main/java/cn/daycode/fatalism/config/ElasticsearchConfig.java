package cn.daycode.fatalism.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

    @Value("${fatalism.es.host:127.0.0.1:8089}")
    private String es_host;

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        String[] es = es_host.split(":");
        String ip= es[0];
        int port=Integer.parseInt(es[1]);
        HttpHost httpHost=new HttpHost(ip,port);
        return new RestHighLevelClient(RestClient.builder(httpHost));
    }
}
