package com.ruoyi.web.controller.notice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.assessment.core.utils.DateUtils;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.SysUserNotice;
import com.ruoyi.system.service.impl.SysUserNoticeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController extends BaseController {

    @Autowired
    private SysUserNoticeImpl sysUserNoticeImpl;

    @GetMapping("/count")
    public ResponseEntity<Integer> count() {
        // TODO 查询用户未读消息列表（暂时读取所有）
        int count = sysUserNoticeImpl.selectUnReadCount(null);
        return ResponseEntity.ok(count);
    }

    /**
     * 加载所有信息（滑动分页）
     */
    @GetMapping("/list")
    public ResponseEntity<List<SysUserNotice>> list(@RequestParam("lastId") Long lastId,
                                                    @RequestParam("limit") Integer limit,
                                                    @RequestParam("userId") Long userId) {
        if (limit == null) {
            limit = 10;
        }

        // TODO 查询用户所有消息列表
        List<SysUserNotice> notices = sysUserNoticeImpl.selectList(lastId, limit, userId);
        return ResponseEntity.ok(notices);
    }

    /**
     * 推送测试
     */
    @GetMapping("/push")
    public String push() {
        // TODO 查询用户未读消息列表（推送未读消息数量）
        int count = sysUserNoticeImpl.selectUnReadCount(null);

        if (count > 0) {
            try {
                //解析发送的报文
                final JSONObject jsonObject = new JSONObject();
                jsonObject.put("count", count);
                //jsonObject.put("userId", userId);
                //sendMessage(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
                //sendMessage("程序出现错误，报错信息为："+e.getMessage());
                //sendMessage("请刷新后重试。。。");
            }
        }

        return "push ok";
    }

    @GetMapping("/list/by/user")
    public TableDataInfo list(SysUserNotice sysUserNotice) {
        startPage();
        //根据用户查询未读
        List<SysUserNotice> sysUserNotices = sysUserNoticeImpl.selectUnReadList(getUserId());
        System.out.println(JSON.toJSONString(sysUserNotices));
        return getDataTable(sysUserNotices);
    }

    @GetMapping("/info")
    public AjaxResult info(Integer id) {
        SysUserNotice sysUserNotice = sysUserNoticeImpl.selectById(Long.valueOf(id));
        SysUserNotice updSysUserNotice = new SysUserNotice();
        updSysUserNotice.setId(id);
        updSysUserNotice.setReadUserId(getUserId().toString());

        updSysUserNotice.setReadTime(DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        sysUserNoticeImpl.update(updSysUserNotice);
        return AjaxResult.success(sysUserNotice);
    }

}
