package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.FileManage;

import java.util.List;

public interface FileManageMapper {
    Integer create(FileManage fileManage);

    FileManage getById(Integer id);

    void deleteById(Integer id);

    List<FileManage> getListByIds(List<Integer> list);
}
