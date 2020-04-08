create table `user_info` (
    `id` int not null auto_increment comment '无实体意义主键',
    `uid` varchar(32) not null comment '用户uid，随机生成，唯一键',
    `username` varchar(64) not null comment '用户名',
    `openid` varchar(64) not null comment '微信openid',
    `session_key` varchar(64) not null comment '会话密钥，动态更新',
    `created_team_counts` int not null default 0 comment '创建的项目组数量',
    `managed_team_counts` int not null default 0 comment '管理的项目组数量',
    `joined_team_counts` int not null default 0 comment '加入的项目组数量',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`id`),
    unique key `unique_uid` (`uid`)
) comment '用户表';