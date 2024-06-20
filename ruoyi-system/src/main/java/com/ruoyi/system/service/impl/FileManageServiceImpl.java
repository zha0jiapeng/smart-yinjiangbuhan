package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.FileManage;
import com.ruoyi.system.mapper.FileManageMapper;
import com.ruoyi.system.service.IFileManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileManageServiceImpl implements IFileManageService {

    @Autowired
    private FileManageMapper fileManageMapper;

    @Override
    public Integer create(FileManage fileManage) {
        return fileManageMapper.create(fileManage);
    }

    @Override
    public FileManage getById(Integer id) {
        return fileManageMapper.getById(id);
    }

    @Override
    public void deleteById(Integer id) {
        fileManageMapper.deleteById(id);
    }

    @Override
    public List<FileManage> getListByIds(List<Integer> ids) {
        return fileManageMapper.getListByIds(ids);
    }

}
