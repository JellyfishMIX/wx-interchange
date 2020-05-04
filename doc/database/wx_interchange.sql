create table `user_info` (
    `id` int not null auto_increment comment '代理主键',
    `uid` varchar(32) not null comment '用户uid，随机生成，唯一键',
    `username` varchar(64) not null comment '用户名',
    `openid` varchar(64) not null comment '微信用户openid，唯一键',
    `avatar_url` varchar(1024) null comment '用户头像URL，头像文件储存在微信服务器',
    `global_grade` int not null default 3 comment '用户全局等级，超级管理员为1，官方合作者为2，普通用户为3，保留0',
    `created_team_count` int not null default 0 comment '创建的项目组数量',
    `managed_team_count` int not null default 0 comment '管理的项目组数量',
    `joined_team_count` int not null default 0 comment '加入的项目组数量',
    `creation_time` timestamp not null default current_timestamp comment '创建时间，自动写入',
    `modified_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间，自动写入',
    primary key (`id`),
    unique key `uk_user_info_uid` (`uid`),
    unique key `uk_user_info_openid` (`openid`)
) comment '用户表';

create table `team_info` (
    `id` int not null auto_increment comment '代理主键',
    `tid` varchar(32) not null comment '项目组tid，随机生成，唯一键',
#     `gid` varchar(32) null comment '绑定的微信群gid，非必须，外键',
#     `opengid` varchar(32) null comment '绑定的微信群gid，非必须，外键', 暂时不做绑定微信群相关
    `team_name` varchar(64) not null comment '项目组名称',
    `avatar_url` varchar(1024) null comment '项目组头像URL，非必须',
    `grade` int not null default 2 comment '项目组等级，官方项目组为1，普通项目组为2，保留0',
    `number_count` int not null default 1 comment '项目组成员数量',
    `created_number_count` int not null default 1 comment '项目组创建者数量',
    `managed_number_count` int not null default 0 comment '项目组管理者数量',
    `joined_number_count` int not null default 0 comment '项目组加入者数量',
    `file_count` int not null default 0 comment '项目组文件数量',
    `creation_time` timestamp not null default current_timestamp comment '创建时间，自动写入',
    `modified_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间，自动写入',
    primary key (`id`),
    unique key `uk_team_info_tid` (`tid`)
#     foreign key `fk_team_info_gid` (`gid`) references wx_interchange.wx_group_info(`gid`),
#     foreign key `fk_team_info_opengid` (`opengid`) references wx_interchange.wx_group_info(`opengid`)
) comment '项目组表';

create table `team_user` (
     `id` int not null auto_increment comment '代理主键',
     `tid` varchar(32) not null comment '项目组tid，外键',
     `uid` varchar(32) not null comment '用户uid，外键',
     `user_grade` int not null default 3 comment '项目组成员等级，1为创建者，2为管理员，3为普通成员',
     `creation_time` timestamp not null default current_timestamp comment '创建时间，自动写入',
     `modified_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间，自动写入',
     primary key (`id`),
    foreign key `fk_team_user_tid` (`tid`) references wx_interchange.team_info(`tid`),
    foreign key `fk_team_user_uid` (`uid`) references wx_interchange.user_info(`uid`)
) comment '项目组成员表';

# create table `wx_group_info` (
#     `id` int not null auto_increment comment '代理主键',
#     `gid` varchar(32) not null comment '微信群gid，随机生成，唯一键',
#     `opengid` varchar(64) not null comment '微信群opengid，唯一键',
#     `wx_group_name` varchar(64) not null comment '微信群名称',
#     `creation_time` timestamp not null default current_timestamp comment '创建时间，自动写入',
#     `modified_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间，自动写入',
#     primary key (`id`),
#     unique key `uk_wx_group_info_gid`(`gid`),
#     unique key `uk_wx_group_info_opengid`(`opengid`)
# ) comment '微信群信息表';

create table `file_info` (
    `id` int not null auto_increment comment '代理主键',
    `file_id` varchar(128) not null comment '文件fileId',
    `file_key` varchar(128) not null comment '文件资源fileKey',
    `hash` varchar(128) not null comment '全局唯一的文件fileHash值',
    `file_name` varchar(64) not null comment '文件名',
    `file_url` varchar(1024) not null comment '文件资源URL',
    `file_size` int not null comment '文件大小, 单位为b',
    `mime_type` varchar(128) not null comment '文件类型',
    `uid` varchar(32) not null comment '上传者uid，外键',
    `creation_time` timestamp not null default current_timestamp comment '创建时间，自动写入',
    `modified_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间，自动写入',
    primary key (`id`),
    unique key `uk_file_info_file_id`(`file_id`),
    foreign key `fk_file_info_uid` (`uid`) references wx_interchange.user_info(`uid`)
) comment '文件信息表';

create table `team_file` (
    `id` int not null auto_increment comment '代理主键',
    `tid` varchar(32) not null comment '项目组tid，外键',
    `file_id` varchar(32) not null comment '文件fileId，外键',
    `uid` varchar(32) not null comment '上传者uid，外键',
    `creation_time` timestamp not null default current_timestamp comment '创建时间，自动写入',
    `modified_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间，自动写入',
    primary key (`id`),
    foreign key `fk_team_file_tid`  (`tid`) references wx_interchange.team_info(`tid`),
    foreign key `fk_team_file_file_id` (`file_id`) references wx_interchange.file_info(`file_id`),
    foreign key `fk_team_file_uid` (`uid`) references wx_interchange.user_info(`uid`)
) comment '项目组文件表';

create table `team_avatar` (
   `id` int not null auto_increment comment '代理主键',
   `avatar_id` varchar(128) not null comment '头像文件avatarId',
   `tid` varchar(32) not null comment '所属项目组tid，外键',
   `file_key` varchar(128) not null comment '文件资源fileKey',
   `file_hash` varchar(128) not null comment '全局唯一的文件Hash值',
   `file_name` varchar(64) not null comment '文件名',
   `file_url` varchar(1024) not null comment '文件资源URL',
   `file_size` int not null comment '文件大小, 单位为b',
   `mime_type` varchar(128) not null comment '文件类型',
   `uid` varchar(32) not null comment '上传者uid，外键',
   `creation_time` timestamp not null default current_timestamp comment '创建时间，自动写入',
   `modified_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间，自动写入',
   primary key (`id`),
   unique key `uk_team_avatar_avatar_id`(`avatar_id`),
   foreign key `fk_team_avatar_tid`(`tid`) references wx_interchange.team_info(`tid`)
) comment '项目组头像表';

create table `collection_info` (
    `id` int not null auto_increment comment '代理主键',
    `collection_id` varchar(32) not null comment '收藏集collection_id',
    `collection_name` varchar(64) not null default '默认收藏集' comment '收藏集名称',
    `uid` varchar(32) not null comment '创建者uid',
    `file_count` int not null default 0 comment '收藏集文件计数',
    `creation_time` timestamp not null default current_timestamp comment '创建时间，自动写入',
    `modified_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间，自动写入',
    primary key (`id`),
    unique key `uk_collection_info_collection_id`(`collection_id`),
    foreign key `fk_collection_info_uid`(`uid`) references wx_interchange.user_info(`uid`)
) comment '文件收藏集表';

create table `collection_file` (
    `id` int not null auto_increment comment '代理主键',
    `collection_id` varchar(32) not null comment '收藏集collection_id',
    `file_id` varchar(128) not null comment '文件fileId',
    `creation_time` timestamp not null default current_timestamp comment '创建时间，自动写入',
    `modified_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间，自动写入',
    primary key (`id`),
    foreign key `fk_collection_file_collection_id`(`collection_id`) references wx_interchange.collection_info(`collection_id`),
    foreign key `fk_collection_file_file_id`(`file_id`) references wx_interchange.file_info(`file_id`)
) comment '文件收藏集的文件关联表';