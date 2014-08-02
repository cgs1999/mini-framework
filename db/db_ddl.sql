create database if not exists `permission_4level` default character set utf8;
use `permission_4level`;

drop table if exists `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id编号',
  `account` varchar(30) NOT NULL COMMENT '帐号',
  `name` varchar(30) NOT NULL COMMENT '姓名',
  `password` varchar(40) NOT NULL COMMENT '密码',
  `salt` varchar(40) DEFAULT NULL COMMENT '加密私钥',
  `email` varchar(50) NOT NULL COMMENT '电子邮箱',
  `phone` varchar(30) DEFAULT NULL COMMENT '联系电话',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态',
  `create_time` timestamp DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp DEFAULT '0000-00-00 00:00:00' COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `account` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

drop table if exists `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `name` varchar(30) NOT NULL COMMENT '角色名称',
  `type` varchar(20) NOT NULL COMMENT '角色类型，系统和自定义',
  `enable` int(1) NOT NULL DEFAULT '0' COMMENT '启停状态',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp DEFAULT '0000-00-00 00:00:00' COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

drop table if exists `sys_permission_category`;
CREATE TABLE `sys_permission_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id编号',
  `name` varchar(40) NOT NULL COMMENT '分类名称',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp DEFAULT '0000-00-00 00:00:00' COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限分类表';

drop table if exists `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id编号',
  `name` varchar(40) DEFAULT NULL COMMENT '权限名称',
  `permission_category_id` bigint(20) DEFAULT NULL,
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp DEFAULT '0000-00-00 00:00:00' COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

drop table if exists `sys_resource`;
CREATE TABLE `sys_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `name` varchar(30) NOT NULL COMMENT '资源名称',
  `type` varchar(20) NOT NULL COMMENT '资源类型，菜单和操作',
  `url` varchar(200) DEFAULT NULL COMMENT '链接地址',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '所属父资源ID',
  `parent_ids` varchar(200) DEFAULT NULL COMMENT '所有父资源ID，以半角逗号分隔',
  `order_index` int(11) NOT NULL DEFAULT '0' COMMENT '排序编号，值越大越靠后',
  `enable` int(1) NOT NULL DEFAULT '0' COMMENT '启停状态',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp DEFAULT '0000-00-00 00:00:00' COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源表';

drop table if exists `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户id编号',
  `role_id` bigint(20) NOT NULL COMMENT '角色id编号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关系表';

drop table if exists `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色id编号',
  `permission_id` bigint(20) DEFAULT NULL COMMENT '权限id编号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限关系表';

drop table if exists `sys_permission_resource`;
CREATE TABLE `sys_permission_resource` (
  `permission_id` bigint(20) DEFAULT NULL COMMENT '权限id编号',
  `resource_id` bigint(20) DEFAULT NULL COMMENT '资源id编号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限资源关系表';

