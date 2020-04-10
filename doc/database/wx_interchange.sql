create table `user_info` (
    `id` int not null auto_increment comment '代理主键',
    `uid` varchar(32) not null comment '用户uid，随机生成，唯一键',
    `username` varchar(64) not null comment '用户名',
    `openid` varchar(64) not null comment '微信用户openid，唯一键',
    `avatar_url` varchar(1024) not null comment '用户头像URL，头像文件储存在微信服务器',
    `created_team_counts` int not null default 0 comment '创建的项目组数量',
    `managed_team_counts` int not null default 0 comment '管理的项目组数量',
    `joined_team_counts` int not null default 0 comment '加入的项目组数量',
    `creation_time` timestamp not null default current_timestamp comment '创建时间，自动写入',
    `modified_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间，自动写入',
    primary key (`id`),
    unique key `unique_uid` (`uid`),
    unique key `unique_openid` (`openid`)
) comment '用户表';

create table `team_info` (
    `id` int not null auto_increment comment '代理主键',
    `tid` varchar(32) not null comment '项目组tid，随机生成，唯一键',
    `gid` varchar(32) null comment '绑定的微信群gid，非必须，外键',
    `team_name` varchar(64) not null comment '项目组名称',
    `team_avatar_url` varchar(1024) null comment '项目组头像URL，非必须',
    `number_counts` int not null default 1 comment '项目组成员数量',
    `created_number_counts` int not null default 1 comment '项目组创建者数量',
    `managed_number_counts` int not null default 1 comment '项目组管理者数量',
    `joined_number_counts` int not null default 1 comment '项目组加入者数量',
    `file_counts` int not null default 0 comment '项目组文件数量',
    `creation_time` timestamp not null default current_timestamp comment '创建时间，自动写入',
    `modified_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间，自动写入',
    primary key (`id`),
    unique key `unique_tid` (`tid`),
    constraint `fk_gid` foreign key (gid) references wx_interchange.wx_group_info(`gid`)
) comment '项目组表';

create table `wx_group_info` (
    `id` int not null auto_increment comment '代理主键',
    `gid` varchar(32) not null comment '微信群gid，随机生成，唯一键',
    `opengid` varchar(64) not null comment '微信群opengid，唯一键',
    `wx_group_name` varchar(64) not null comment '微信群名称',
    `creation_time` timestamp not null default current_timestamp comment '创建时间，自动写入',
    `modified_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间，自动写入',
    primary key (`id`),
    unique key `unique_gid`(gid),
    unique key `unique_opengid`(opengid)
) comment '微信群信息';