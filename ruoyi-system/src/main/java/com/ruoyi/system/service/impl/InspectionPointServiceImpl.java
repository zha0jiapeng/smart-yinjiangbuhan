package com.ruoyi.system.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import com.ruoyi.system.domain.vo.PointProjectVO;
import com.ruoyi.system.entity.InspectionPoint;
import com.ruoyi.system.mapper.InspectionPointMapper;
import com.ruoyi.system.service.InspectionPointService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * (InspectionPoint)表服务实现类
 *
 * @author makejava
 * @since 2022-11-25 17:10:29
 */
@Service("inspectionPointService")
public class InspectionPointServiceImpl implements InspectionPointService {
    @Resource
    private InspectionPointMapper inspectionPointMapper;

//    @Autowired
//    private OssTemplate template;
//
//    @Autowired
//    private OssProperties ossProperties;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public InspectionPoint queryById(Integer id) {
        return this.inspectionPointMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param inspectionPoint 筛选条件
     * @return 查询结果
     */
    @Override
    public List<InspectionPoint> queryByPage(InspectionPoint inspectionPoint) {
        return this.inspectionPointMapper.queryAllByLimit(inspectionPoint);
    }

    /**
     * 新增数据
     *
     * @param inspectionPoint 实例对象
     * @return 实例对象
     */
    @Override
    public InspectionPoint insert(InspectionPoint inspectionPoint){
        this.inspectionPointMapper.insert(inspectionPoint);
        String id = inspectionPoint.getId().toString();
        File file = new File(id + ".jpg");
        File generate = QrCodeUtil.generate(id, 300, 300, file);
        InputStream in = null;
        try {
            in = new FileInputStream(generate);
            //template.putObject(ossProperties.getBucketName(),id + ".jpg",in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IoUtil.close(in);
            boolean delete = file.delete();
            if (delete) {
                System.out.println("删除临时二维码文件成功");
            } else {
                System.out.println("删除临时二维码文件失败");
            }
        }
        //String objectURL = ossProperties.getEndpoint() + "/" + ossProperties.getBucketName() + "/" + id + ".jpg";
        //inspectionPoint.setCodeImageFileUrl(objectURL);
        inspectionPointMapper.update(inspectionPoint);
        return inspectionPoint;
    }

    /**
     * 修改数据
     *
     * @param inspectionPoint 实例对象
     * @return 实例对象
     */
    @Override
    public InspectionPoint update(InspectionPoint inspectionPoint) {
        this.inspectionPointMapper.update(inspectionPoint);
        return this.queryById(inspectionPoint.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.inspectionPointMapper.deleteById(id) > 0;
    }


    @Override
    public PointProjectVO getPointAndProjectById(Integer id) {
        return inspectionPointMapper.getPointAndProjectById(id);
    }

    @Override
    public void updateStopCheck(InspectionPoint inspectionPoint) {
        inspectionPointMapper.update(inspectionPoint);
    }
}
