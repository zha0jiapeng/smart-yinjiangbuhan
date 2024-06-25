create table sys_work_people_inout_log
(
    id                 int auto_increment comment '主键'
        primary key,
    inout_id           varchar(128) null comment '日志唯一标识',
    sys_work_people_id int          null comment '人员ID',
    sn                 varchar(128) null comment '设备序列号',
    id_card            varchar(20)  null comment '身份证号',
    mode               tinyint      null comment '出入方式 1:进 0:出',
    log_time           datetime     null comment '记录时间',
    name               varchar(128) null comment '用户姓名',
    phone              varchar(20)  null comment '用户电话',
    created_by         varchar(20)  null comment '创建人',
    created_date       datetime     null comment '创建时间',
    modify_by          varchar(20)  null comment '更新人',
    modify_date        datetime     null comment '修改时间',
    yn                 tinyint      null comment '逻辑删除表示 1:正常 0:删除'
)
    comment '人员出入记录表' charset = utf8mb4
                             row_format = DYNAMIC;
