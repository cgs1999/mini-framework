create database if not exists `permission_3level` default character set utf8;
use `permission_3level`;

drop table if exists `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `account` varchar(30) NOT NULL COMMENT '帐号',
  `name` varchar(30) NOT NULL COMMENT '姓名',
  `password` varchar(40) NOT NULL COMMENT '密码',
  `salt` varchar(40) DEFAULT NULL COMMENT '加密私钥',
  `email` varchar(50) NOT NULL COMMENT '电子邮箱',
  `phone` varchar(30) DEFAULT NULL COMMENT '联系电话',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `account` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

drop table if exists `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `name` varchar(30) NOT NULL COMMENT '角色名称',
  `type` varchar(20) NOT NULL COMMENT '角色类型，系统和自定义',
  `enable` int(1) NOT NULL DEFAULT '0' COMMENT '启停状态',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

drop table if exists `sys_resource`;
CREATE TABLE `sys_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `name` varchar(30) NOT NULL COMMENT '资源名称',
  `type` varchar(20) NOT NULL COMMENT '资源类型，菜单和操作',
  `url` varchar(200) DEFAULT NULL COMMENT '链接地址',
  `parent_id` int(11) DEFAULT NULL COMMENT '所属父资源ID',
  `parent_ids` varchar(200) DEFAULT NULL COMMENT '所有父资源ID，以半角逗号分隔',
  `order_index` int(11) NOT NULL DEFAULT '0' COMMENT '排序索引',
  `enable` int(1) NOT NULL DEFAULT '0' COMMENT '启停状态',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源表';

drop table if exists `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关系表';

drop table if exists `sys_role_resource`;
CREATE TABLE `sys_role_resource` (
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `resource_id` int(11) NOT NULL COMMENT '资源ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色资源关系表';

