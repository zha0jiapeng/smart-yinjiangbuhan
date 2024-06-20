package com.ruoyi.web.controller.basic.view.wms;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.Customer;
import com.ruoyi.system.mapper.convert.CustomerConvert;
import com.ruoyi.system.pojo.query.CustomerQuery;
import com.ruoyi.system.pojo.vo.CustomerVO;
import com.ruoyi.system.service.impl.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * 客户Controller
 * 
 * @author zcc
 * @date 2022-08-05
 */
@Api(description ="客户接口列表")
@RestController
@RequestMapping("/wms/customer")
public class CustomerController extends BaseController {
    @Autowired
    private CustomerService service;
    @Autowired
    private CustomerConvert convert;

    @ApiOperation("查询客户列表")
    //@PreAuthorize("@ss.hasPermi('wms:customer:list')")
    @PostMapping("/list")
    public ResponseEntity<Page<Customer>> list(@RequestBody CustomerQuery query, Pageable page) {
        List<Customer> list = service.selectList(query, page);
        return ResponseEntity.ok(new PageImpl<>(list, page, ((com.github.pagehelper.Page)list).getTotal()));
    }

    @ApiOperation("导出客户列表")
    @PreAuthorize("@ss.hasPermi('wms:customer:export')")
    @Log(title = "客户", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(HttpServletResponse response, CustomerQuery query) {
        List<Customer> list = service.selectList(query, null);
        ExcelUtil<CustomerVO> util = new ExcelUtil<>(CustomerVO.class);
        util.exportExcels(response, this.dos2vos(list), "客户数据");
    }

    public List<CustomerVO> dos2vos(List<Customer> list) {
        if (list == null) {
            return null;
        } else {
            List<CustomerVO> list1 = new ArrayList(list.size());
            Iterator var3 = list.iterator();

            while(var3.hasNext()) {
                Customer customer = (Customer)var3.next();
                list1.add(this.customerToCustomerVO(customer));
            }

            return list1;
        }
    }

    protected CustomerVO customerToCustomerVO(Customer customer) {
        if (customer == null) {
            return null;
        } else {
            CustomerVO customerVO = new CustomerVO();
            customerVO.setCreateBy(customer.getCreateBy());
            customerVO.setCreateTime(customer.getCreateTime());
            customerVO.setUpdateBy(customer.getUpdateBy());
            customerVO.setUpdateTime(customer.getUpdateTime());
            customerVO.setId(customer.getId());
            customerVO.setCustomerNo(customer.getCustomerNo());
            customerVO.setCustomerName(customer.getCustomerName());
            customerVO.setAddress(customer.getAddress());
            customerVO.setMobile(customer.getMobile());
            customerVO.setTel(customer.getTel());
            customerVO.setCustomerPerson(customer.getCustomerPerson());
            customerVO.setCustomerLevel(customer.getCustomerLevel());
            customerVO.setEmail(customer.getEmail());
            customerVO.setRemark(customer.getRemark());
            return customerVO;
        }
    }

    @ApiOperation("获取客户详细信息")
    @PreAuthorize("@ss.hasPermi('wms:customer:query')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Customer> getInfo(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.selectById(id));
    }

    @ApiOperation("新增客户")
    @PreAuthorize("@ss.hasPermi('wms:customer:add')")
    @Log(title = "客户", businessType = BusinessType.INSERT)
    @PostMapping
    public ResponseEntity<Integer> add(@RequestBody Customer customer) {
        return ResponseEntity.ok(service.insert(customer));
    }

    @ApiOperation("修改客户")
    @PreAuthorize("@ss.hasPermi('wms:customer:edit')")
    @Log(title = "客户", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResponseEntity<Integer> edit(@RequestBody Customer customer) {
        return ResponseEntity.ok(service.update(customer));
    }

    @ApiOperation("删除客户")
    @PreAuthorize("@ss.hasPermi('wms:customer:remove')")
    @Log(title = "客户", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public ResponseEntity<Integer> remove(@PathVariable Long[] ids) {
        return ResponseEntity.ok(service.deleteByIds(ids));
    }
}
