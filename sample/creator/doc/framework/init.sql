-- 创建数据库
CREATE DATABASE `hualuomoli` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
-- 创建用户
CREATE USER 'hualuomoli'@'%'; 
-- CREATE USER 'sample'@'%' IDENTIFIED BY '123456'; 
-- 赋权
GRANT ALL ON `hualuomoli`.* to 'hualuomoli'@'%';

CREATE TABLE `hualuomoli`.`t_user` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `nickname` varchar(32) DEFAULT NULL COMMENT '昵称',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `sex` char(1) DEFAULT NULL COMMENT '性别',
  `state` int(1) DEFAULT NULL COMMENT '数据状态1=正常',
  `status` varchar(10) DEFAULT NULL COMMENT '数据状态',
  `remark` varchar(200) DEFAULT NULL COMMENT '描述信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';
ALTER TABLE `hualuomoli`.`t_user` ADD UNIQUE INDEX `user_username`(`username`);
ALTER TABLE `hualuomoli`.`t_user` ADD UNIQUE INDEX `user_nickname_age`(`nickname`, `age`);
