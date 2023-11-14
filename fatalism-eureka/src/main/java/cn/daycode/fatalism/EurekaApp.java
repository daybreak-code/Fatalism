package cn.daycode.fatalism;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

//@EnableEurekaServer
@SpringBootApplication
public class EurekaApp {

    public static void main(String[] args) {
        SpringApplication.run(EurekaApp.class);
    }

}

    /*
    *
   docker run -d -p 8080:8080 \
    -e SPRING_DATASOURCE_URL="jdbc:mysql://127.0.0.1:3306/ApolloConfigDB?characterEncoding=utf8" \
    -e SPRING_DATASOURCE_USERNAME=root \
    -e SPRING_DATASOURCE_PASSWORD=123456 \
    -e "EUREKA_INSTANCE_PREFERIPADDRESS=true" \
    -e "EUREKA_INSTANCE_IPADDRESS=127.0.0.1" \
    -e "EUREKA_INSTANCE_NONSECUREPORT=8080" \
    -e "EUREKA_INSTANCE_INSTANCEID=127.0.0.1:8080" \
    -v /tmp/logs:/opt/logs  \
    --name apollo-configservice apolloconfig/apollo-configservice
     */
