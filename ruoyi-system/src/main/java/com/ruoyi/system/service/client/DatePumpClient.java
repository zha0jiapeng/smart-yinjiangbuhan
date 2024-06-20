/*
package com.ruoyi.system.service.client;
import com.mctech.sdk.openapi.OpenApiClient;
import com.mctech.sdk.openapi.RequestResult;
import cn.hutool.json.JSONObject;
import com.ruoyi.system.entity.SdkDataAccess;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class DatePumpClient {

    private static final String accessId = "R5kqByQr0otY3hR99ctY";

    private static final String secretKey = "n+HR8rqPWMVYRVsOR1joj7Ypo94cCt6BPnH4j128";

    private static final String baseUrl = "https://api.mctech.vip";

    private static final String projectsApi = "/org-api/projects?start=%d&limit=%d";

    private static final String pcrApi = "/external/project-construction-record?startId=%d&startVersion=%d&limit=%d&orgId=%d";

    private static final String orderListApi = "/mquantity/get-receive-weight-order-list?offset=%d&orgId=1513684503083008&limit=%d&version=%d&origin=%d";

    private static String outputFile = "project-construction-record.txt";

    private static Object lockObj = new Object();

    // 20线程
    private static int threadCount = 20;

    private static ExecutorService service = Executors.newFixedThreadPool(threadCount);

    private static OpenApiClient client = new OpenApiClient(baseUrl, accessId, secretKey);

    private static AtomicInteger totalCount = new AtomicInteger(0);

    private static final Semaphore sem = new Semaphore(threadCount);

    public static void main(String[] args) {
        try {
            sem.acquire();
            service.submit(() ->{
                try {
                    String apiUrl = "/mquantity/get-receive-weight-order-list?offset=0&orgId=15136811114503083008&limit=100&version=0";
                    String apiUrl = "/mquantity/get-receive-weight-order-list?offset=0&orgId=1513684503083008&limit=100&version=0";

                    try (RequestResult requestResult = client.get(apiUrl)) {
                        // 调用result.GetJsonObject方法可以使用强类型的实体，也可以使用Newtonsoft.json.dll中的JArray类型
                        com.alibaba.fastjson.JSON jsonObject = requestResult.getJsonObject();
                        SdkDataAccess sdkDataAccess = new SdkDataAccess();
                        sdkDataAccess.setSId(jsonObject.getStr("id"));
                        sdkDataAccess.setOrgId(jsonObject.getStr("orgId"));
                        sdkDataAccess.setOriRedId(jsonObject.getStr("oriRedId"));
                        sdkDataAccess.setOriOrgId(jsonObject.getStr("oriOrgId"));
                        sdkDataAccess.setRealOrgId(jsonObject.getStr("realOrgId"));
                        sdkDataAccess.setSupplierId(jsonObject.getStr("supplierId"));
                        sdkDataAccess.setStockbinId(jsonObject.getStr("stockbinId"));
                        sdkDataAccess.setOriOrderId(jsonObject.getStr("oriOrderId"));
                        sdkDataAccess.setOrderDataId(jsonObject.getStr("orderDataId"));
                        sdkDataAccess.setOriStockbinId(jsonObject.getStr("oriStockbinId"));
                        sdkDataAccess.setOrgName(jsonObject.getStr("orgName"));
                        sdkDataAccess.setOriOrgName(jsonObject.getStr("oriOrgName"));
                        sdkDataAccess.setStockbinName(jsonObject.getStr("stockbinName"));
                        sdkDataAccess.setSupplierName(jsonObject.getStr("supplierName"));
                        sdkDataAccess.setStockbinFullName(jsonObject.getStr("stockbinFullName"));
                        sdkDataAccess.setOrderCode(jsonObject.getStr("orderCode"));
                        sdkDataAccess.setSupplierOrgCode(jsonObject.getStr("supplierOrgCode"));
                        sdkDataAccess.setAuditDate(jsonObject.getStr("auditDate"));
                        sdkDataAccess.setOrderDate(jsonObject.getStr("orderDate"));
                        sdkDataAccess.setMakerDate(jsonObject.getStr("makerDate"));
                        sdkDataAccess.setRecordedDate(jsonObject.getStr("recordedDate"));
                        sdkDataAccess.setRemark(jsonObject.getStr("remark"));
                        sdkDataAccess.setAuditor(jsonObject.getStr("auditor"));
                        sdkDataAccess.setMaker(jsonObject.getStr("maker"));
                        sdkDataAccess.setPlateNumber(jsonObject.getStr("plateNumber"));
                        sdkDataAccess.setWeightType(jsonObject.getStr("weightType"));
                        sdkDataAccess.setExitTime(jsonObject.getStr("exitTime"));
                        sdkDataAccess.setEnterTime(jsonObject.getStr("enterTime"));
                        sdkDataAccess.setWaybillWeight(jsonObject.getInt("waybillWeight"));
                        sdkDataAccess.setPrintTimes(jsonObject.getInt("printTimes"));
                        sdkDataAccess.setServiceType(jsonObject.getInt("serviceType"));
                        sdkDataAccess.setOrderType(jsonObject.getInt("orderType"));
                        sdkDataAccess.setAuxiliaryNetQuantity(jsonObject.getInt("auxiliaryNetQuantity"));
                        sdkDataAccess.setDeductQuantity(jsonObject.getInt("deductQuantity"));
                        sdkDataAccess.setDeductRate(jsonObject.getInt("deductRate"));
                        sdkDataAccess.setOrderOrigin(jsonObject.getInt("orderOrigin"));
                        sdkDataAccess.setPhoto(jsonObject.getInt("photo"));
                        sdkDataAccess.setGpyPhoto(jsonObject.getInt("gpyPhoto"));
                        sdkDataAccess.setSignaturePhoto(jsonObject.getInt("signaturePhoto"));
                        sdkDataAccess.setMaterial(jsonObject.getInt("material"));
                        sdkDataAccess.setTareQuantity(jsonObject.getFloat("tareQuantity"));
                        sdkDataAccess.setRoughQuantity(jsonObject.getFloat("roughQuantity"));
                        sdkDataAccess.setIsRed(jsonObject.getInt("isRed"));
                        sdkDataAccess.setIsExit(jsonObject.getInt("isExit"));
                        sdkDataAccess.setIsTare(jsonObject.getInt("isTare"));
                        sdkDataAccess.setIsAudit(jsonObject.getInt("isAudit"));
                        sdkDataAccess.setIsReturn(jsonObject.getInt("isReturn"));
                        sdkDataAccess.setIsAffirm(jsonObject.getInt("isAffirm"));
                        sdkDataAccess.setIsRemoved(jsonObject.getInt("isRemoved"));
                        sdkDataAccess.setIsMultiplication(jsonObject.getInt("isMultiplication"));
                        sdkDataAccess.setIsUseOrinetQuantity(jsonObject.getInt("isUseOrinetQuantity"));
                        sdkDataAccess.setCreatedAt(jsonObject.getDate("createdAt"));
                        sdkDataAccess.setUpdatedAt(jsonObject.getDate("updatedAt"));
                        sdkDataAccess.setVersion(jsonObject.getStr("version"));
                        System.out.println(jsonObject);
                    }catch (Exception e){

                    }
                } finally {
                    sem.release();
                }
            });
            sem.acquire(threadCount);
        }catch (Exception e){
        }
    }
}
*/
