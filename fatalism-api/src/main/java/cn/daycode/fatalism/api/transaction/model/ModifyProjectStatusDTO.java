package cn.daycode.fatalism.api.transaction.model;

import cn.daycode.fatalism.common.util.RSAUtil;
import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
public class ModifyProjectStatusDTO {

    private String requestNo;

    private String projectNo;

    private String projectStatus;

    private Long id;

    public static void main(String[] args) throws UnsupportedEncodingException {

        ModifyProjectStatusDTO modifyProjectStatusDTO = new ModifyProjectStatusDTO();
        modifyProjectStatusDTO.setId(1130782711460204546L);
        modifyProjectStatusDTO.setProjectStatus("REPAYING");
        modifyProjectStatusDTO.setRequestNo("PRO_F6EBE6902F1D4C839EB6A9E351D27230");

        String reqData = cn.itcast.wanxinp2p.common.util.EncryptUtil.encodeUTF8StringBase64(JSON.toJSONString(modifyProjectStatusDTO));
        final String sign = RSAUtil.sign(JSON.toJSONString(modifyProjectStatusDTO), "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEApkqNoES+508OiULK5UIEuZ9WxIUG7fB92V0vEi1FyNJgzMc2gi5hy8eGcyYyLWJdEt5h1vC8jclCgEcMY3lp3QIDAQABAkAUhQia6UDBXEEH8QUGazIYEbBsSZoETHPLGbOQQ6Pj1tb6CVC57kioBjwtNBnY2jBDWi5K815LnOBcJSSjJPwhAiEA2eO6VZMTkdjQAkpB5dhy/0C3i8zs0c0M1rPoTA/RpkUCIQDDYHJPqHLkQyd//7sEeYcm8cMBTvDKBXyiuGk8eLRauQIgQo6IlalGmg+Dgp+SP5Z9kjD/oCmp0XB0UoVEGS/f140CIQCsG9YXHgi31ACD3T9eHcBVKjvidyveix7UKSdrQdl+4QIgNCtRVLV+783e7PX5hRXD+knsWTQxDEMEsHi1KsAWtPk=", "utf-8");

        String str = "serviceName=" + URLEncoder.encode("MODIFY_PROJECT", "utf-8")
                + "&platformNo=" + URLEncoder.encode("wanxinp2p", "utf-8")
                + "&reqData=" + URLEncoder.encode(reqData, "utf-8")
                + "&signature=" + URLEncoder.encode(sign, "utf-8");
        System.out.println(str);
    }
}
