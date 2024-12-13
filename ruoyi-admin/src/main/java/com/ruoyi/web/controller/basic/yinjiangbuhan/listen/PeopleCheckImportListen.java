package com.ruoyi.web.controller.basic.yinjiangbuhan.listen;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.web.controller.basic.yinjiangbuhan.bean.Staff;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.CheckIdCard;

import java.util.ArrayList;
import java.util.List;

public class PeopleCheckImportListen extends AnalysisEventListener<Staff> {

    private List<String> errorMessages = new ArrayList<>();

    @Override
    public void invoke(Staff data, AnalysisContext context) {
        // 非空校验
        if (StringUtils.isEmpty(data.getIdCardNo())) {
            errorMessages.add("第 " + context.readRowHolder().getRowIndex() + " 行的身份证号不能为空");
        }else {
            String msg = CheckIdCard.IDCardValidate(data.getIdCardNo());
            if(!"success".equals(msg)){
                errorMessages.add("第 " + context.readRowHolder().getRowIndex() + " 行的身份证号,"+msg);
            }
//            String idCardRegex = "(^[1-9]\\d{5}(18|19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[1-2]\\d|3[0-1])\\d{3}(\\d|X|x)$)|(^[1-9]\\d{7}(0[1-9]|1[0-2])(0[1-9]|[1-2]\\d|3[0-1])\\d{3}$)";
//            boolean isValid = ReUtil.isMatch(idCardRegex, data.getIdCardNo().trim().toUpperCase());
//            if(!isValid){
//                errorMessages.add("第 " + context.readRowHolder().getRowIndex() + " 行的身份证号不符合格式");
//            }
        }

        if (StringUtils.isEmpty(data.getBimStaffType())) {
            errorMessages.add("第 " + context.readRowHolder().getRowIndex() + " 行的bim人员类型不能为空");
        }
        if (StringUtils.isEmpty(data.getLaborSubCom())) {
            errorMessages.add("第 " + context.readRowHolder().getRowIndex() + " 行的劳务分包公司不能为空");
        }
        if (StringUtils.isEmpty(data.getWorkerType())) {
            errorMessages.add("第 " + context.readRowHolder().getRowIndex() + " 行的工种不能为空");
        }
        if (StringUtils.isEmpty(data.getKeyPersonnelFlag())) {
            errorMessages.add("第 " + context.readRowHolder().getRowIndex() + " 行的是否重点人员不能为空");
        }
        if (StringUtils.isEmpty(data.getSpecialWorker())) {
            errorMessages.add("第 " + context.readRowHolder().getRowIndex() + " 行的是否特种人员不能为空");
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (!errorMessages.isEmpty()) {
            throw new RuntimeException("数据校验失败：" + errorMessages.toString());
        }
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }
}
