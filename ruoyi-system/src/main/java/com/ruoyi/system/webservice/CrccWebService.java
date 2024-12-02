//package com.ruoyi.system.webservice;
//
//import cn.hutool.json.JSONArray;
//import cn.hutool.json.JSONObject;
//import cn.hutool.json.XML;
//import com.alibaba.fastjson2.JSON;
//import com.ruoyi.common.enums.TBMCutterHeadStatusEnum;
//import com.ruoyi.common.enums.TBMPropelStatusEnum;
//import com.ruoyi.system.webservice.dto.DataDTO;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.http.HttpEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.util.EntityUtils;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//import java.util.Optional;
//
///**
// * @author lpeng
// * @since 2023/11/28 16:19
// */
//@Slf4j
//@Component
//public class CrccWebService {
//
//    private static final String SCHEMAS = "http://schemas.xmlsoap.org/soap/envelope/";
//    private static final String NAMESPACE = "http://www.crcc.cn/";
//    private static final String GET_DATA_ACTION = "http://www.crcc.cn/getData";
//    private static final String GET_TBM_ACTION = "http://www.crcc.cn/GetTBM";
//
//    public DataDTO crccData(String url, String mac, String psw) {
//        DataDTO dto = new DataDTO();
//        try {
//            List<String> list = wsGetData(url, mac, psw);
//            log.info("webservice接口获取数据结果：{}", JSON.toJSONString(list));
//            if (CollectionUtils.isEmpty(list)) {
//                log.info("webservice接口获取数据结果为空");
//                return dto;
//            }
//
//            dto.setTjgzzt(TBMPropelStatusEnum.getNameByValue(list.get(90)));
//            dto.setHpjsq(list.get(0));
//            dto.setTjsdpjz(list.get(93));
//            dto.setZtjl(list.get(91));
//            dto.setZtjbdjyx(TBMPropelStatusEnum.getNameByValue(list.get(99)));
//            dto.setZtjbckyl(list.get(100));
//            dto.setFztjbdjyx(TBMPropelStatusEnum.getNameByValue(list.get(111)));
//            dto.setFztjbckyl(list.get(112));
//            dto.setGrd(list.get(92));
//            dto.setTjsdsz(list.get(94));
//            dto.setPzjjd(list.get(129));
//            dto.setCxbckyl(list.get(123));
//            dto.setCxwgqyl(list.get(124));
//            dto.setCxygqyl(list.get(125));
//            dto.setScxygxcjc(list.get(126));
//            dto.setXcxygxcjc(list.get(127));
//            dto.setDbssdssygxc151(list.get(151));
//            dto.setDbssdssygxc152(list.get(152));
//            dto.setDpgzzt(TBMCutterHeadStatusEnum.getNameByValue(list.get(62)));
//            dto.setDpsdsz(list.get(63));
//            dto.setDpsd(list.get(64));
//            dto.setDpzj(list.get(65));
//            dto.setQdtqj(list.get(19));
//            dto.setQdtfzj(list.get(20));
//            dto.setYyyxwd(list.get(47));
//            dto.setClyxwd(list.get(49));
//            dto.setZjyyyxwd(list.get(255));
//            dto.setDtlc(list.get(1));
//            dto.setQdhlc(list.get(2));
//            dto.setCjdqlc(list.get(3));
//            dto.setDtgc(list.get(18));
//            dto.setQdspqx(list.get(12));
//            dto.setQdszqx(list.get(13));
//            dto.setCxdspqx(list.get(14));
//            dto.setCxdszqx(list.get(15));
//            dto.setDtsppc(list.get(4));
//            dto.setDtczpc(list.get(5));
//            dto.setQhdsppc(list.get(6));
//            dto.setQhdczpc(list.get(7));
//            dto.setCjdqsppc(list.get(8));
//            dto.setCjdqczpc(list.get(9));
//            dto.setCjdhsppc(list.get(10));
//            dto.setCjdhczpc(list.get(11));
//
//            dto.setJwc(list.get(167));
//            dto.setErwc(list.get(169));
//            dto.setYwc(list.get(166));
//            dto.setYqc(list.get(165));
//            dto.setShqc(list.get(168));
//            return dto;
//        } catch (IndexOutOfBoundsException e) {
//            log.error("数据解析异常: {}", e.toString());
//        } catch (IOException e) {
//            log.error("WebService服务连接失败:{}", e.toString());
//        } catch (Exception e) {
//            log.error("未知异常: {}", e.toString());
//        }
//
//        return dto;
//    }
//
//    private List<String> wsGetData(String url, String mac, String psw) throws IOException {
//        // 只能在三维/定位这两台服务器上调通WebService接口
//        // http://192.168.100.47 工号：DZ1188   密码：02bbii
//        // http://192.168.110.100:80/Service.asmx
//        String param = wsGetDataReqXml(mac, psw);
//        return wsResult(url, GET_DATA_ACTION, param);
//    }
//
//    public List<String> wsGetTbm(String url, String mac, String psw) throws IOException {
//        // 只能在三维/定位这两台服务器上调通WebService接口
//        // http://192.168.100.47 工号：DZ1188   密码：02bbii
//        // http://192.168.110.100:80/Service.asmx
//        String param = wsGetTbmReqXml(mac, psw);
//        return wsResult(url, GET_TBM_ACTION, param);
//    }
//
//    private List<String> wsResult(String url, String soapAction, String param) throws IOException {
//        String send = doPostSoap(url, param, soapAction);
//        JSONObject json = XML.toJSONObject(send);
//        JSONArray array = Optional.ofNullable(json)
//                .map(e -> e.getJSONObject("soap:Envelope"))
//                .map(e -> e.getJSONObject("soap:Body"))
//                .map(e -> e.getJSONObject("getDataResponse"))
//                .map(e -> e.getJSONObject("getDataResult"))
//                .map(e -> e.getJSONArray("string"))
//                .orElse(new JSONArray());
//        return array.toList(String.class);
//    }
//
//    private String doPostSoap(String url, String soap, String soapAction) throws IOException {
//        log.info("请求url:{} soap:{} soapAction:{}", url, soap, soapAction);
//
//        try {
//            // 创建HttpClientBuilder
//            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
//            // HttpClient
//            try (CloseableHttpClient closeableHttpClient = httpClientBuilder.build()) {
//                HttpPost httpPost = new HttpPost(url);
//                httpPost.setHeader("Content-Type", "text/xml;charset=UTF-8");
//                httpPost.setHeader("SOAPAction", soapAction);
//                StringEntity data = new StringEntity(soap, StandardCharsets.UTF_8);
//                httpPost.setEntity(data);
//                CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
//                HttpEntity httpEntity = response.getEntity();
//                if (httpEntity != null) {
//                    return EntityUtils.toString(httpEntity, "UTF-8");
//                }
//            }
//        } catch (Exception e) {
//            //e.printStackTrace();
//            log.error("请求异常 error:{}", e.getMessage());
//        }
//
//        return "";
//    }
//
//    private String wsGetDataReqXml(String mac, String psw) {
//        return "<soapenv:Envelope xmlns:soapenv=\"" + SCHEMAS + "\" xmlns:crcc=\"" + NAMESPACE + "\">" +
//                "   <soapenv:Header/>" +
//                "   <soapenv:Body>" +
//                "      <crcc:getData>" +
//                "         <crcc:flag>tbmData</crcc:flag>" +
//                "         <crcc:mac>" + mac + "</crcc:mac>" +
//                "         <crcc:psw>" + psw + "</crcc:psw>" +
//                "      </crcc:getData>" +
//                "   </soapenv:Body>" +
//                "</soapenv:Envelope>";
//    }
//
//    private String wsGetTbmReqXml(String mac, String psw) {
//        return "<soapenv:Envelope xmlns:soapenv=\"" + SCHEMAS + "\" xmlns:crcc=\"" + NAMESPACE + "\">" +
//                "   <soapenv:Header/>" +
//                "   <soapenv:Body>" +
//                "      <crcc:GetTBM>" +
//                "         <crcc:mac>" + mac + "</crcc:mac>" +
//                "         <crcc:psw>" + psw + "</crcc:psw>" +
//                "      </crcc:GetTBM>" +
//                "   </soapenv:Body>" +
//                "</soapenv:Envelope>";
//    }
//}
