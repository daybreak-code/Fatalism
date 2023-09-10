package cn.daycode.fatalism.message;

import com.ctrip.framework.apollo.ConfigService;
import io.micrometer.core.ipc.http.OkHttpSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GatewayMessageConsumer {

    private static final String CONSUMER_GROUP = "CID_DEPOSITORY_GATEWAY_NOTIFY";

    @Autowired
    private ConfigService configService;

    //private OkHttpService

}