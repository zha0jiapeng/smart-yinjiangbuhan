package com.ruoyi.system.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.entity.QualityProblem;
import com.ruoyi.system.entity.QualityTesting;
import com.ruoyi.system.mapper.QualityProblemMapper;
import com.ruoyi.system.mapper.QualityTestingMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.QualityImportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class QualityImportServiceImpl implements QualityImportService {
    @Value("${fastdfs.query.url}")
    private String url;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private QualityProblemMapper qualityProblemMapper;
    @Resource
    private QualityTestingMapper qualityTestingMapper;

    @Override
    public void importTestingDoc(List<List<String>> listInfo) {
        if (CollectionUtils.isEmpty(listInfo)) {
            log.error("质量检查 数据导入失败 无解析到数据:{}", JSONUtil.toJsonStr(listInfo));
            return;
        }

        try {
            for (List<String> var : listInfo) {
                String string = var.get(0);
                if (StringUtils.isBlank(string) || string.equals("序号")) {
                    continue;
                }

                QualityProblem qualityProblem = new QualityProblem();
                qualityProblem.setProblemInfo(var.get(3));
                qualityProblem.setProblemProgress("问题审核");

                qualityProblem.setProblemType(1);
                qualityProblem.setCheckStatus(0);
                qualityProblem.setProblemLevel("低");
                qualityProblem.setProblemName(var.get(3));

                if (var.size() > 8) {
                    qualityProblem.setProblemFileUrl(url + var.get(8));
                }

                String userName = var.get(5);
                SysUser user;
                if (userName.contains("/")) {
                    String[] split = userName.split("/");
                    user = sysUserMapper.selectUserByNickName(split[0]);
                } else {
                    user = sysUserMapper.selectUserByNickName(userName);
                }
                qualityProblem.setProblemAuditUserId(user.getUserId().intValue());
                qualityProblem.setProblemAuditUserName(userName);
                qualityProblem.setAbarbeitungTime(new Date());

                Calendar calendar = Calendar.getInstance();
                int month = calendar.get(Calendar.MONTH) + 1;
                qualityProblem.setMoonBase(String.valueOf(month));
                qualityProblem.setPatternBase("其它");
                qualityProblem.setRectificationMeasure(var.get(4));
                qualityProblem.setProblemTypeBase(var.get(3));
                qualityProblem.setRegion("默认");

                qualityProblem.setCreateUserId(SecurityUtils.getLoginUser().getUser().getUserId().intValue());
                qualityProblem.setCreateUserName(SecurityUtils.getLoginUser().getUsername());
                qualityProblem.setCreateTime(LocalDateTime.now());
                qualityProblemMapper.insert(qualityProblem);

                //先查询质量检查任务是否存在
                QueryWrapper<QualityTesting> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("testing_name", var.get(2));
                QualityTesting qualityTesting = qualityTestingMapper.selectOne(queryWrapper);
                if (null == qualityTesting) {
                    QualityTesting qualityTestingSave = new QualityTesting();
                    qualityTestingSave.setTestingName(var.get(2));
                    qualityTestingSave.setQualityProblemsId(qualityProblem.getId() + ",");
                    qualityTestingSave.setTaskDescribe(var.get(2));
                    qualityTestingSave.setCreateBy(SecurityUtils.getLoginUser().getUsername());
                    qualityTestingSave.setCreateTime(new Date());
                    qualityTestingMapper.insert(qualityTestingSave);
                } else {
                    String key = qualityTesting.getQualityProblemsId() + qualityProblem.getId() + ",";
                    qualityTesting.setQualityProblemsId(key);
                    qualityTestingMapper.updateById(qualityTesting);
                }
            }
        } catch (Exception e) {
            log.error("质量检查 质量问题导入 异常:", e);
        }
    }

    @Override
    public void importTestingDocx(Map<String, List<String>> mapInfo) {

    }

    @Override
    public void importTestingReplyDoc(List<List<String>> listInfo) {
        if (CollectionUtils.isEmpty(listInfo)) {
            log.error("质量检查 数据导入失败 无解析到数据:{}", JSONUtil.toJsonStr(listInfo));
            return;
        }

        try {
            for (List<String> var : listInfo) {
                String string = var.get(0);
                if (StringUtils.isBlank(string) || string.equals("序号")) {
                    continue;
                }

                QualityProblem qualityProblem = new QualityProblem();
                qualityProblem.setProblemInfo(var.get(3));
                String userName = var.get(5);
                qualityProblem.setProblemAuditUserName(userName);
                List<QualityProblem> qualityProblems = qualityProblemMapper.queryAllByLimit(qualityProblem);
                if (CollectionUtils.isNotEmpty(qualityProblems)) {
                    QualityProblem updateQualityProblem = qualityProblems.stream().findFirst().orElse(null);
                    if (null != updateQualityProblem) {
                        updateQualityProblem.setCheckStatus(2);
                        updateQualityProblem.setCheckContent(var.get(4));
                        if (var.size() > 8) {
                            updateQualityProblem.setFileUrl(url + var.get(8));
                        }
                        qualityProblem.setId(updateQualityProblem.getId());
                        qualityProblemMapper.update(updateQualityProblem);
                    }
                }
            }
        } catch (Exception e) {
            log.error("质量检查 质量问题导入 异常:", e);
        }


    }

    @Override
    public void importTestingReplyDocx(Map<String, List<String>> mapInfo) {

    }

}
