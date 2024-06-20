package com.ruoyi.system.service;

import com.ruoyi.system.domain.PersonnelLinkage;

import java.util.List;

public interface PersonnelLinkageService {

    List<PersonnelLinkage.Examination> queryUserExams(String phoneNumber);

}
