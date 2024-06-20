package com.ruoyi.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.system.domain.vo.PreventionDeviceVO;
import com.ruoyi.system.domain.vo.PreventionSecurityRiskVO;
import com.ruoyi.system.entity.PreventionCheckTaskConfig;
import com.ruoyi.system.entity.PreventionDevice;
import com.ruoyi.system.entity.PreventionSecurityRisk;
import com.ruoyi.system.mapper.PreventionSecurityRiskMapper;
import com.ruoyi.system.service.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 双重预防-风险分析清单明细(PreventionSecurityRisk)表服务实现类
 *
 * @author makejava
 * @since 2022-11-17 11:18:54
 */
@Service("preventionSecurityRiskService")
public class PreventionSecurityRiskServiceImpl implements PreventionSecurityRiskService {
    @Resource
    private PreventionSecurityRiskMapper preventionSecurityRiskDao;

    @Resource
    private PreventionCheckTaskConfigService preventionCheckTaskConfigService;

    @Resource
    private PreventionDeviceService preventionDeviceService;

    @Resource
    private ISysUserService userService;

    @Resource
    private ISysDeptService sysDeptService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public PreventionSecurityRisk queryById(Integer id) {
        return this.preventionSecurityRiskDao.queryById(id);
    }

    /**
     * 分页查询
     * @return 查询结果
     */
    @Override
    public List<PreventionSecurityRisk> queryByPage(PreventionSecurityRisk preventionSecurityRisk) {
        return preventionSecurityRiskDao.queryAllByLimit(preventionSecurityRisk);
    }

    /**
     * 新增数据
     *
     * @param preventionSecurityRisk 实例对象
     * @return 实例对象
     */
    @Override
    public PreventionSecurityRisk insert(PreventionSecurityRisk preventionSecurityRisk) {
        this.preventionSecurityRiskDao.insert(preventionSecurityRisk);
        PreventionCheckTaskConfig preventionCheckTaskConfig = new PreventionCheckTaskConfig();
        preventionCheckTaskConfig.setSecurityRiskId(preventionSecurityRisk.getId());
        preventionCheckTaskConfig.setWhetherConfig(1);
        preventionCheckTaskConfig.setWhetherRelease(1);
        preventionCheckTaskConfigService.insert(preventionCheckTaskConfig);
        return preventionSecurityRisk;
    }

    /**
     * 修改数据
     *
     * @param preventionSecurityRisk 实例对象
     * @return 实例对象
     */
    @Override
    public PreventionSecurityRisk update(PreventionSecurityRisk preventionSecurityRisk) {
        this.preventionSecurityRiskDao.update(preventionSecurityRisk);
        return this.queryById(preventionSecurityRisk.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.preventionSecurityRiskDao.deleteById(id) > 0;
    }

    @Override
    public Integer countUnit(Integer id) {
        List<String> list = preventionSecurityRiskDao.countUnit(id);
        return CollectionUtils.isEmpty(list) ? 0 : list.size();
    }

    @Override
    public Integer countEvent(Integer id) {
        List<String> list = preventionSecurityRiskDao.countEvent(id);
        return CollectionUtils.isEmpty(list) ? 0 : list.size();
    }

    @Override
    public Integer countControl(Integer id) {
        List<String> list = preventionSecurityRiskDao.countControl(id);
        return CollectionUtils.isEmpty(list) ? 0 : list.size();
    }

    @Override
    public Integer countTask(Integer id) {
        List<String> list = preventionSecurityRiskDao.countTask(id);
        return CollectionUtils.isEmpty(list) ? 0 : list.size();
    }

    @Override
    public List<PreventionSecurityRisk> queryAll(PreventionSecurityRisk preventionSecurityRisk) {
        return preventionSecurityRiskDao.queryAll();
    }

    @Override
    public String importExcel(List<PreventionSecurityRiskVO> preventionSecurityRisks, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(preventionSecurityRisks) || preventionSecurityRisks.size() == 0) {
            throw new ServiceException("导入用户数据不能为空！");
        }
        List<PreventionCheckTaskConfig> preventionCheckTaskConfigList = new ArrayList<>();
        preventionSecurityRisks.forEach(i -> {
            PreventionSecurityRisk preventionSecurityRisk = new PreventionSecurityRisk();
            BeanUtils.copyBeanProp(preventionSecurityRisk,i);
            String deviceName = i.getDeviceName();
            PreventionDevice preventionDevice = new PreventionDevice();
            preventionDevice.setDeviceName(deviceName);
            List<PreventionDeviceVO> preventionDeviceVOS = preventionDeviceService.queryByPage(preventionDevice);
            if (CollUtil.isNotEmpty(preventionDeviceVOS)) {
                PreventionDeviceVO preventionDeviceVO = preventionDeviceVOS.get(0);
                preventionSecurityRisk.setDeviceId(preventionDeviceVO.getId());
            }
            String userName = i.getUserName();
            if (StringUtils.isNotEmpty(userName)) {
                SysUser sysUser = userService.selectUserByUserName(userName);
                if (sysUser != null) {
                    int userId = sysUser.getUserId().intValue();
                    int deptId = sysUser.getDeptId().intValue();
                    preventionSecurityRisk.setDeptId(deptId);
                    preventionSecurityRisk.setUserId(userId);
                }
            }
            preventionSecurityRiskDao.insert(preventionSecurityRisk);
            PreventionCheckTaskConfig preventionCheckTaskConfig = new PreventionCheckTaskConfig();
            preventionCheckTaskConfig.setSecurityRiskId(preventionSecurityRisk.getId());
            preventionCheckTaskConfig.setWhetherConfig(1);
            preventionCheckTaskConfig.setWhetherRelease(1);
            preventionCheckTaskConfigList.add(preventionCheckTaskConfig);
        });
        preventionCheckTaskConfigService.insertBatch(preventionCheckTaskConfigList);
        return "成功";
    }

    @Override
    public List<PreventionSecurityRiskVO> queryAllVO(PreventionSecurityRiskVO preventionSecurityRisk) {
        return preventionSecurityRiskDao.queryAllVO();
    }
}
