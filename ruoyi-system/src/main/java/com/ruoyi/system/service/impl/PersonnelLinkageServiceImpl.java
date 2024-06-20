package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.PersonnelLinkage;
import com.ruoyi.system.domain.ElPaper;
import com.ruoyi.system.mapper.ElPaperMapper;
import com.ruoyi.system.service.PersonnelLinkageService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonnelLinkageServiceImpl implements PersonnelLinkageService {

    @Resource
    private ElPaperMapper elPaperMapper;

    @Override
    public List<PersonnelLinkage.Examination> queryUserExams(String phoneNumber) {
        List<PersonnelLinkage.Examination> exams = new ArrayList<>();

        List<ElPaper> userExams = elPaperMapper.selectUserList(phoneNumber);
        if (CollectionUtils.isEmpty(userExams)) {
            return exams;
        }

        userExams.forEach(obj -> {
            PersonnelLinkage.Examination examination = new PersonnelLinkage.Examination();
            examination.setPaper(obj.getTitle());
            examination.setAchievement(obj.getUserScore());
            examination.setAnswerDate(obj.getCreateTime());
            exams.add(examination);
        });

        return exams;
    }
}
