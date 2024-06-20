package com.ruoyi.web.controller.basic.file;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.ProjectMpp;
import com.ruoyi.common.utils.FastDFSClientUtil;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.service.ProjectMppService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Slf4j
@Api(description = "project项目管理")
@RestController
@RequestMapping("/upload/project")
@SuppressWarnings("all")
public class UploadProjectController extends BaseController {
    @Autowired
    private FastDFSClientUtil fastDFSClientUtil;
    @Autowired
    private ProjectMppService projectMppService;

    @PostMapping("uploadProject")
    public AjaxResult uploadProject(@RequestParam(value = "file") MultipartFile multipartFile) throws IOException {
        //文件上传前的名称
        String fileName = multipartFile.getOriginalFilename();
        File file = new File(fileName);
        OutputStream out = null;
        try {
            //获取文件流，以文件流的方式输出到新文件
            //    InputStream in = multipartFile.getInputStream();
            out = new FileOutputStream(file);
            byte[] ss = multipartFile.getBytes();
            for (int i = 0; i < ss.length; i++) {
                out.write(ss[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        projectMppService.readMmpFileToDB(file);
        return AjaxResult.success();
    }

    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<ProjectMpp> util = new ExcelUtil<ProjectMpp>(ProjectMpp.class);
        List<ProjectMpp> userList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = projectMppService.importProjectMpp(userList, updateSupport, operName);
        return success(message);
    }

    @PostMapping("upload")
    public AjaxResult upload(@RequestParam(value = "file") MultipartFile multipartFile) throws IOException {
        String uploadFile = fastDFSClientUtil.uploadFile(multipartFile);
        log.info("文件上传 地址:{}", uploadFile);
        return success();
    }

    @PostMapping("/down")
    public AjaxResult down(HttpServletResponse response) throws Exception {
        String mppUrl =  "http://114.117.242.249:8888/group1/M00/00/00/rB4qpGO81aOAC4KxAAUOAPPeAIs836.mpp";
        return success(mppUrl);
    }

}
