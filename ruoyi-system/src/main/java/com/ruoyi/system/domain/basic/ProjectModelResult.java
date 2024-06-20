package com.ruoyi.system.domain.basic;

import lombok.Builder;
import lombok.Data;

/**
 * 处理结果Dto类，用于校验返回、业务处理返回等场景
 *
 * @author liugeng
 * @date 2018-05-21
 */
@Data
@Builder
public class ProjectModelResult<T> {
    /**
     * 状态  true 通过  false  未通过
     */
    private boolean status;
    /**
     * 校验返回消息
     */
    private T message;

    /**
     * 返回数据
     */
    private T data;
}