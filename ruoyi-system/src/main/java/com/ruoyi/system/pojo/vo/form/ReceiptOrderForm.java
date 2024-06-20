package com.ruoyi.system.pojo.vo.form;

import com.ruoyi.system.domain.ReceiptOrder;
import com.ruoyi.system.pojo.vo.ItemVO;
import com.ruoyi.system.pojo.vo.ReceiptOrderDetailVO;
import lombok.Data;

import java.util.List;

@Data
public class ReceiptOrderForm extends ReceiptOrder {
    // 入库单详情
    private List<ReceiptOrderDetailVO> details;
    // 所有商品
    private List<ItemVO> items;
}
