package com.ruoyi.system.service;

import com.ruoyi.system.domain.FileManage;

import java.util.List;

public interface IFileManageService {
    Integer create(FileManage fileManage);

    FileManage getById(Integer id);

    void deleteById(Integer id);

    List<FileManage> getListByIds(List<Integer> ids);
}
