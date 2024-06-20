package com.ruoyi.web.controller.basic.view.control;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.VerifyEnum;
import com.ruoyi.common.utils.BaseVerifyUtil;
import com.ruoyi.common.utils.FastDFSClientUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.system.domain.file.SysFileManage;
import com.ruoyi.system.domain.file.SysFilePackage;
import com.ruoyi.system.service.ISysFilePackageService;
import com.ruoyi.system.service.ISysFilesManageService;
import com.ruoyi.system.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

/**
 * @Description: 知识库-文档管理
 * @Author: jeecg-boot
 * @Date: 2022-07-21
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "知识库-文档管理")
@RestController
@RequestMapping("/sys/file")
public class SysFilesController {
    @Autowired
    private FastDFSClientUtil fastDFSClientUtil;
    @Autowired
    private ISysFilesManageService sysFilesManageService;
    @Autowired
    private ISysFilePackageService sysFilePackageService;

    @ApiOperation(value = "知识库-文档管理-文件树", notes = "知识库-文档管理-文件书")
    @GetMapping(value = "/tree")
    public Result<?> queryPageList(SysFilePackage sysFilePackage,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {

        log.info("文件信息:{}", JSON.toJSONString(sysFilePackage));
        QueryWrapper<SysFilePackage> queryWrapper = new QueryWrapper<>();
        if (null != sysFilePackage.getId()) {
            queryWrapper.eq("id", sysFilePackage.getId());
        } else {
            if (null == sysFilePackage.getPid()) {
                //文件夹id
                queryWrapper.eq("pid", 0);
            } else {
                queryWrapper.eq("pid", sysFilePackage.getPid());
            }
        }

        Page<SysFilePackage> page = new Page<SysFilePackage>(pageNo, pageSize);
        IPage<SysFilePackage> pageList = sysFilePackageService.page(page, queryWrapper);
        if (null != pageList) {
            List<SysFilePackage> records = pageList.getRecords();
            if (!CollectionUtils.isEmpty(records)) {
                records.forEach(sysFilePackageResp -> {
                    //判断当前节点下有没有下级
                    QueryWrapper<SysFilePackage> queryWrapperCase = new QueryWrapper<>();
                    queryWrapperCase.eq("pid", sysFilePackageResp.getPid());
                    List<SysFilePackage> sysFilePackages = sysFilePackageService.list(queryWrapper);
                    if (CollectionUtils.isEmpty(sysFilePackages)) {
                        sysFilePackageResp.setLeaf(false);
                    } else {
                        sysFilePackageResp.setLeaf(true);
                    }
                    Integer filePackageRespId = sysFilePackageResp.getId();
                    QueryWrapper<SysFileManage> query = new QueryWrapper<>();
                    //文件夹id
                    query.eq("file_package_id", filePackageRespId);
                    List<SysFileManage> list = sysFilesManageService.list(query);
                    sysFilePackageResp.setSysFileManages(list);
                });
            }
        }
        return Result.OK(pageList);
    }

    @ApiOperation(value = "知识库-文档管理-获取文件夹下文件", notes = "知识库-文档管理-添加")
    @PostMapping(value = "/getFileManageByPackageId")
    public Result<?> getFileManageByPackageId(@RequestBody SysFileManage sysFileManage) {
        //基础参数校验
        BaseVerifyUtil.verify(null == sysFileManage.getFilePackageId())
                .throwMessage(VerifyEnum.FILE_PACKAGE_ID.getCode(), VerifyEnum.FILE_PACKAGE_ID.getDesc());

        QueryWrapper<SysFileManage> query = new QueryWrapper<>();
        //文件夹id
        query.eq("file_package_id", sysFileManage.getFilePackageId());
        List<SysFileManage> list = sysFilesManageService.list(query);

        return Result.OK(list);
    }

    @ApiOperation(value = "知识库-文件夹-添加", notes = "知识库-文件夹-添加")
    @PostMapping(value = "/addPackage")
    public Result<?> addPackage(@RequestBody SysFilePackage sysFilePackage) {
        sysFilePackageService.save(sysFilePackage);
        return Result.OK("添加成功！");
    }

    @ApiOperation(value = "知识库-文件夹-通过id删除", notes = "知识库-文件夹-通过id删除")
    @DeleteMapping(value = "/deletePackage")
    public Result<?> deletePackage(@RequestParam(name = "id", required = true) String id) {
        QueryWrapper<SysFilePackage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid", id);
        //校验当前文件夹下是否有子文件夹
        List<SysFilePackage> sysFilePackages = sysFilePackageService.list(queryWrapper);
        if (!CollectionUtils.isEmpty(sysFilePackages)) {
            return Result.error("当前文件夹下是有子文件夹!");
        }
        //校验当前文件夹下是否有关联的文件
        QueryWrapper<SysFileManage> query = new QueryWrapper<>();
        //文件夹id
        query.eq("file_package_id", id);
        List<SysFileManage> sysFileManages = sysFilesManageService.list(query);
        if (!CollectionUtils.isEmpty(sysFileManages)) {
            return Result.error("当前文件夹下是有关联的文件!");
        }
        sysFilePackageService.removeById(id);
        return Result.OK("删除成功!");
    }

    @ApiOperation(value = "知识库-文档管理-添加", notes = "知识库-文档管理-添加")
    @PostMapping(value = "/add")
    public AjaxResult add(@RequestParam(value = "file") MultipartFile file, SysFileManage sysFileManage) {
        log.info("知识库-文档管理-添加:{}", JSON.toJSONString(sysFileManage));
        try {
            String fileName = fastDFSClientUtil.uploadFile(file);

            AjaxResult ajax = AjaxResult.success();
            //ajax.put("url", url);
            ajax.put("fileName", fileName);
            ajax.put("newFileName", FileUtils.getName(fileName));
            ajax.put("originalFilename", file.getOriginalFilename());

            //文件信息持久化
            //sysFileManage.setFileUrl(url);
            sysFileManage.setFileName(file.getOriginalFilename());
            sysFilesManageService.save(sysFileManage);
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    @ApiOperation(value = "知识库-文档管理-通过id删除", notes = "知识库-文档管理-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        log.info("知识库-文档管理-通过id删除" + id);
        QueryWrapper<SysFileManage> query = new QueryWrapper<>();
        query.eq("id", id);
        SysFileManage sysFileManage = sysFilesManageService.getOne(query);
        if (null == sysFileManage) {
            return Result.error("删除失败");
        }

        String localPath = RuoYiConfig.getProfile();
        String downLoadPath = localPath + StringUtils.substringAfter(sysFileManage.getFileUrl(), Constants.RESOURCE_PREFIX);
        boolean flag = false;
        File file = new File(downLoadPath);
        if (file.isFile() && file.exists()) {
            flag = file.delete();
        }
        if (!flag) {
            return Result.error("删除失败");
        }

        sysFilesManageService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     * @param delete   是否删除
     */
    @GetMapping("/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (!FileUtils.checkAllowDownload(fileName)) {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = RuoYiConfig.getDownloadPath() + fileName;

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete) {
                FileUtils.deleteFile(filePath);
            }
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }
}
