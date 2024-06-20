package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.ProjectMpp;
import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.system.mapper.ProjectMppMapper;
import com.ruoyi.system.service.ProjectMppService;
import lombok.extern.slf4j.Slf4j;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.ResourceAssignment;
import net.sf.mpxj.Task;
import net.sf.mpxj.mpp.MPPReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProjectMppServiceImpl extends ServiceImpl<ProjectMppMapper, ProjectMpp> implements ProjectMppService {

    @Autowired
    private ProjectMppMapper projectMppMapper;

    @Override
    @Transactional
    public void readMmpFileToDB(File file) {//如果读取的是MultipartFile，那么直接使用获取InputStream即可
        try {
            //这个是读取文件的组件
            MPPReader mppRead = new MPPReader();
            //注意，如果在这一步出现了读取异常，肯定是版本不兼容，换个版本试试
            ProjectFile pf = mppRead.read(file);
            //从文件中获取的任务对象
            List<Task> tasks = pf.getChildTasks();
            //这个可以不用，这个list只是我用来装下所有的数据，如果不需要可以不使用
            List<ProjectMpp> proList = new LinkedList<>();
            //这个是用来封装任务的对象，为了便于区别，初始化批次号，然后所有读取的数据都需要加上批次号
            ProjectMpp pro = new ProjectMpp();
            pro.setBatchNum(UUID.randomUUID().toString());//生成批次号UUID
            pro.setProjId(0);
            //这个方法是一个递归方法
            getChildrenTask(tasks.get(0), pro, proList, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String importProjectMpp(List<ProjectMpp> projectMppList, Boolean isUpdateSupport, String operName) {
        return null;
    }

    @Override
    public List<TreeSelect> queryTree(ProjectMpp projectMpp) {
        List<TreeSelect> treeSelects = new ArrayList<>();

        QueryWrapper<ProjectMpp> queryWrapper = new QueryWrapper<>();
        List<ProjectMpp> projectMppList = projectMppMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(projectMppList)) {
            return treeSelects;
        }

        List<ProjectMpp> returnList = new ArrayList<ProjectMpp>();
        List<Long> tempList = new ArrayList<Long>();
        for (ProjectMpp mpp : projectMppList) {
            tempList.add(Long.valueOf(mpp.getProjId()));
        }

        for (ProjectMpp mpp : projectMppList) {
            {
                if (!tempList.contains(Long.valueOf(mpp.getParentId()))) {
                    recursionFn(projectMppList, mpp);
                    returnList.add(mpp);
                }
            }

            if (returnList.isEmpty()) {
                returnList = projectMppList;
            }
        }


        treeSelects = returnList.stream().map(TreeSelect::new).collect(Collectors.toList());


        return treeSelects;
    }

    @Override
    public void updateProjectMpp(ProjectMpp projectMpp) {
        projectMppMapper.updateProjectMpp(projectMpp);
    }

    private void recursionFn(List<ProjectMpp> list, ProjectMpp t) {
        List<ProjectMpp> childList = getChildList(list, t);
        t.setChildren(childList);
        for (ProjectMpp tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    private List<ProjectMpp> getChildList(List<ProjectMpp> list, ProjectMpp t) {
        List<ProjectMpp> tlist = new ArrayList<ProjectMpp>();
        Iterator<ProjectMpp> it = list.iterator();
        while (it.hasNext()) {
            ProjectMpp n = (ProjectMpp) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getProjId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    private boolean hasChild(List<ProjectMpp> list, ProjectMpp t) {
        return getChildList(list, t).size() > 0;
    }

    /**
     * 这个方法是一个递归
     * 方法的原理：进行读取父任务，如果下一层任务还是父任务，那么继续调用当前方法，如果到了最后一层，调用另外一个读取底层的方法
     *
     * @param task
     * @param project
     * @param list
     * @param levelNum
     */
    public void getChildrenTask(Task task, ProjectMpp project, List<ProjectMpp> list, int levelNum) {
        if (task.getResourceAssignments().size() == 0) {//这个判断是进行是否是最后一层任务的判断==0说明是父任务
            levelNum++;//层级号需要增加，这个只是博主用来记录该层的层级数
            List<Task> tasks = task.getChildTasks();//继续获取子任务
            for (int i = 0; i < tasks.size(); i++) {//该循环是遍历所有的子任务
                if (tasks.get(i).getResourceAssignments().size() == 0) {//说明还是在父任务层
                    ProjectMpp pro = new ProjectMpp();
                    if (project.getProjId() != null) {//说明不是第一次读取了，因为如果是第一层，那么还没有进行数据库的添加，没有返回主键Id
                        pro.setParentId(project.getProjId());//将上一级目录的Id赋值给下一级的ParentId
                    }
                    pro.setBatchNum(project.getBatchNum());//批量号
                    pro.setImportTime(new Date());//导入时间
                    pro.setLevel(levelNum);//层级
                    pro.setTaskName(tasks.get(i).getName());//这个是获取文件中的“任务名称”列的数据
                    pro.setDurationDate(tasks.get(i).getDuration().toString());//获取的是文件中的“工期”
                   // pro.setStartDate(tasks.get(i).getStart());//获取文件中的 “开始时间”
                  //  pro.setEndDate(tasks.get(i).getFinish());//获取文件中的 “完成时间”
                    pro.setResourcesName(tasks.get(i).getResourceGroup());//获取文件中的 “资源名称”
                    this.addProjectInfo(pro);//将该条数据添加到数据库，并且会返回主键Id，用做子任务的ParentId,这个需要在mybatis的Mapper中设置
                    getChildrenTask(tasks.get(i), pro, list, levelNum);//继续进行递归，当前保存的只是父任务的信息
                } else {//继续进行递归
                    getChildrenTask(tasks.get(i), project, list, levelNum);
                }
            }
        } else {//说明已经到了最底层的子任务了，那么就调用进行最底层数据读取的方法
            if (project.getProjId() != null) {
                getResourceAssignment(task, project, list, levelNum);
            }
        }
    }

    public void getResourceAssignment(Task task, ProjectMpp project, List<ProjectMpp> proList, int levelNum) {
        List<ResourceAssignment> list = task.getResourceAssignments();//读取最底层的属性
        ResourceAssignment rs = list.get(0);
        ProjectMpp pro = new ProjectMpp();
        pro.setTaskName(task.getName());
        pro.setParentId(project.getProjId());
        pro.setLevel(levelNum);
        pro.setImportTime(new Date());
        pro.setBatchNum(project.getBatchNum());
        pro.setDurationDate(task.getDuration().toString());
       // pro.setStartDate(rs.getStart());//注意，这个从ResourceAssignment中读取
       // pro.setEndDate(rs.getFinish());//同上
        String resource = "";
        if (list.size() > 1) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getResource() != null) {
                    if (i < list.size() - 1) {
                        resource += list.get(i).getResource().getName() + ",";
                    } else {
                        resource += list.get(i).getResource().getName();
                    }
                }
            }
        } else {
            if (list.size() > 0 && list.get(0).getResource() != null) {
                resource = list.get(0).getResource().getName();
            }
        }
        if (!StringUtils.isEmpty(resource)) {
            pro.setResourcesName(resource);
        }
        this.addProjectInfo(pro);//将数据保存在数据库中,同样会返回主键
        proList.add(pro);
    }

    public Integer addProjectInfo(ProjectMpp project) {
        return projectMppMapper.addProjectSelective(project);
    }
}
