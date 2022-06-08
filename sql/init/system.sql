/*
 Navicat Premium Data Transfer

 Source Server         : cloud-vue
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : 127.0.0.1:3306
 Source Schema         : system

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 08/06/2022 11:09:12
*/

CREATE DATABASE IF NOT EXISTS `system` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `system`;

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table`  (
  `table_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '表描述',
  `sub_table_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '关联子表的表名',
  `sub_table_fk_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '子表关联的外键名',
  `class_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `package_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '生成功能作者',
  `gen_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
  `gen_path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
  `options` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '其它生成选项',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  `database_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '数据库名称',
  PRIMARY KEY (`table_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '代码生成业务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gen_table
-- ----------------------------
INSERT INTO `gen_table` VALUES (4, 't_user', '用户', NULL, NULL, 'TUser', 'crud', 'com.example', 'example', 'user', '用户', 'administrator', '0', '/', '{}', '管理员', '2022-06-02 10:17:48', '', '2022-06-02 16:29:55', NULL, 'system');
INSERT INTO `gen_table` VALUES (5, 'sys_dict_data', '字典数据表', NULL, NULL, 'SysDictData', 'crud', 'com.example', 'example', 'data', '字典数据', 'administrator', '0', '/', NULL, '管理员', '2022-06-02 16:07:26', '', NULL, NULL, 'system');
INSERT INTO `gen_table` VALUES (6, 'sys_dict_type', '字典类型表', NULL, NULL, 'SysDictType', 'crud', 'com.example', 'example', 'type', '字典类型', 'administrator', '0', '/', NULL, '管理员', '2022-06-02 16:07:26', '', NULL, NULL, 'system');
INSERT INTO `gen_table` VALUES (7, 't_menu', '菜单', NULL, NULL, 'Menu', 'crud', 'com.example', 'example', 'menu', '菜单', 'zww', '0', '/', '{}', '管理员', '2022-06-02 16:07:26', '', '2022-06-02 16:08:43', NULL, 'system');
INSERT INTO `gen_table` VALUES (8, 't_role', '角色', NULL, NULL, 'TRole', 'crud', 'com.example', 'example', 'role', '角色', 'administrator', '0', '/', NULL, '管理员', '2022-06-02 16:07:26', '', NULL, NULL, 'system');
INSERT INTO `gen_table` VALUES (9, 't_role_menu', '角色菜单关联关系', NULL, NULL, 'TRoleMenu', 'crud', 'com.example', 'example', 'menu', '角色菜单关联关系', 'administrator', '0', '/', NULL, '管理员', '2022-06-02 16:07:26', '', NULL, NULL, 'system');
INSERT INTO `gen_table` VALUES (10, 't_user_role', '用户角色关联关系', NULL, NULL, 'TUserRole', 'crud', 'com.example', 'example', 'role', '用户角色关联关系', 'administrator', '0', '/', NULL, '管理员', '2022-06-02 16:07:26', '', NULL, NULL, 'system');
INSERT INTO `gen_table` VALUES (11, 'sys_login_log', '登录日志记录', NULL, NULL, 'LoginLog', 'crud', 'com.example.system', 'system', 'log', '登录日志记录', '朱伟伟', '0', '/', '{}', '管理员', '2022-06-07 13:41:34', '', '2022-06-07 13:44:12', NULL, 'system');
INSERT INTO `gen_table` VALUES (12, 'sys_ope_log', '操作日志记录', NULL, NULL, 'OpeLog', 'crud', 'com.example', 'example', 'opeLog', '操作日志记录', '朱伟伟', '0', '/', '{}', '管理员', '2022-06-07 13:41:34', '', '2022-06-07 15:01:53', NULL, 'system');

-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column`  (
  `column_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '字典类型',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 109 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '代码生成业务表字段' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gen_table_column
-- ----------------------------
INSERT INTO `gen_table_column` VALUES (23, '4', 'id', '主键id', 'bigint(20)', 'Long', 'id', '1', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, '管理员', '2022-06-02 10:17:48', '', '2022-06-02 16:29:55');
INSERT INTO `gen_table_column` VALUES (24, '4', 'name', '姓名', 'varchar(20)', 'String', 'name', '0', '0', '1', '1', '1', '1', '1', 'LIKE', 'input', '', 2, '管理员', '2022-06-02 10:17:48', '', '2022-06-02 16:29:55');
INSERT INTO `gen_table_column` VALUES (25, '4', 'sex', '性别', 'varchar(1)', 'String', 'sex', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'select', '', 3, '管理员', '2022-06-02 10:17:48', '', '2022-06-02 16:29:55');
INSERT INTO `gen_table_column` VALUES (26, '4', 'phone', '手机号', 'varchar(11)', 'String', 'phone', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 4, '管理员', '2022-06-02 10:17:48', '', '2022-06-02 16:29:55');
INSERT INTO `gen_table_column` VALUES (27, '4', 'email', '邮箱', 'varchar(30)', 'String', 'email', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 5, '管理员', '2022-06-02 10:17:48', '', '2022-06-02 16:29:55');
INSERT INTO `gen_table_column` VALUES (28, '4', 'address', '住址', 'varchar(100)', 'String', 'address', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 6, '管理员', '2022-06-02 10:17:48', '', '2022-06-02 16:29:55');
INSERT INTO `gen_table_column` VALUES (29, '4', 'loginName', '登录名', 'varchar(20)', 'String', 'loginName', '0', '0', '1', '1', '1', '1', '1', 'LIKE', 'input', '', 7, '管理员', '2022-06-02 10:17:48', '', '2022-06-02 16:29:55');
INSERT INTO `gen_table_column` VALUES (30, '4', 'password', '密码', 'varchar(200)', 'String', 'password', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 8, '管理员', '2022-06-02 10:17:48', '', '2022-06-02 16:29:55');
INSERT INTO `gen_table_column` VALUES (31, '4', 'inputTime', '录入时间', 'datetime', 'LocalDateTime', 'inputTime', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'datetime', '', 9, '管理员', '2022-06-02 10:17:48', '', '2022-06-02 16:29:55');
INSERT INTO `gen_table_column` VALUES (32, '4', 'inputUserId', '录入用户ID', 'bigint(20)', 'Long', 'inputUserId', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 10, '管理员', '2022-06-02 10:17:48', '', '2022-06-02 16:29:55');
INSERT INTO `gen_table_column` VALUES (33, '4', 'updateTime', '修改时间', 'datetime', 'LocalDateTime', 'updateTime', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'datetime', '', 11, '管理员', '2022-06-02 10:17:48', '', '2022-06-02 16:29:55');
INSERT INTO `gen_table_column` VALUES (34, '4', 'updateUserId', '修改用户', 'bigint(20)', 'Long', 'updateUserId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 12, '管理员', '2022-06-02 10:17:48', '', '2022-06-02 16:29:55');
INSERT INTO `gen_table_column` VALUES (35, '5', 'dictCode', '字典编码', 'bigint(20)', 'Long', 'dictCode', '1', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (36, '5', 'dictSort', '字典排序', 'int(4)', 'Integer', 'dictSort', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 2, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (37, '5', 'dictLabel', '字典标签', 'varchar(100)', 'String', 'dictLabel', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 3, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (38, '5', 'dictValue', '字典键值', 'varchar(100)', 'String', 'dictValue', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 4, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (39, '5', 'dictType', '字典类型', 'varchar(100)', 'String', 'dictType', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'select', '', 5, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (40, '5', 'cssClass', '样式属性（其他样式扩展）', 'varchar(100)', 'String', 'cssClass', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 6, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (41, '5', 'listClass', '表格回显样式', 'varchar(100)', 'String', 'listClass', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 7, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (42, '5', 'isDefault', '是否默认（Y是 N否）', 'char(1)', 'String', 'isDefault', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 8, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (43, '5', 'status', '状态（0正常 1停用）', 'char(1)', 'String', 'status', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'radio', '', 9, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (44, '5', 'createBy', '创建者', 'varchar(64)', 'String', 'createBy', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 10, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (45, '5', 'createTime', '创建时间', 'datetime', 'LocalDateTime', 'createTime', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'datetime', '', 11, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (46, '5', 'updateBy', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 12, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (47, '5', 'updateTime', '更新时间', 'datetime', 'LocalDateTime', 'updateTime', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'datetime', '', 13, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (48, '5', 'remark', '备注', 'varchar(500)', 'String', 'remark', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'textarea', '', 14, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (49, '6', 'dictId', '字典主键', 'bigint(20)', 'Long', 'dictId', '1', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (50, '6', 'dictName', '字典名称', 'varchar(100)', 'String', 'dictName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 2, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (51, '6', 'dictType', '字典类型', 'varchar(100)', 'String', 'dictType', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'select', '', 3, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (52, '6', 'status', '状态（0正常 1停用）', 'char(1)', 'String', 'status', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'radio', '', 4, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (53, '6', 'createBy', '创建者', 'varchar(64)', 'String', 'createBy', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 5, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (54, '6', 'createTime', '创建时间', 'datetime', 'LocalDateTime', 'createTime', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'datetime', '', 6, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (55, '6', 'updateBy', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 7, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (56, '6', 'updateTime', '更新时间', 'datetime', 'LocalDateTime', 'updateTime', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'datetime', '', 8, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (57, '6', 'remark', '备注', 'varchar(500)', 'String', 'remark', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'textarea', '', 9, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (58, '7', 'id', '主键', 'bigint(20)', 'Long', 'id', '1', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, '管理员', '2022-06-02 16:07:26', '', '2022-06-02 16:08:43');
INSERT INTO `gen_table_column` VALUES (59, '7', 'code', '菜单编码', 'varchar(50)', 'String', 'code', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 2, '管理员', '2022-06-02 16:07:26', '', '2022-06-02 16:08:43');
INSERT INTO `gen_table_column` VALUES (60, '7', 'name', '菜单名称', 'varchar(100)', 'String', 'name', '0', '0', '1', '1', '1', '1', '1', 'LIKE', 'input', '', 3, '管理员', '2022-06-02 16:07:26', '', '2022-06-02 16:08:43');
INSERT INTO `gen_table_column` VALUES (61, '7', 'type', '菜单类型 1：模块菜单 2：导航菜单 3：功能按钮', 'int(1)', 'Integer', 'type', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'select', '', 4, '管理员', '2022-06-02 16:07:26', '', '2022-06-02 16:08:43');
INSERT INTO `gen_table_column` VALUES (62, '7', 'parentId', '上级菜单', 'bigint(20)', 'Long', 'parentId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 5, '管理员', '2022-06-02 16:07:26', '', '2022-06-02 16:08:43');
INSERT INTO `gen_table_column` VALUES (63, '7', 'inputUserId', '录入用户', 'bigint(20)', 'Long', 'inputUserId', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 6, '管理员', '2022-06-02 16:07:26', '', '2022-06-02 16:08:43');
INSERT INTO `gen_table_column` VALUES (64, '7', 'inputTime', '录入时间', 'datetime', 'LocalDateTime', 'inputTime', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'datetime', '', 7, '管理员', '2022-06-02 16:07:26', '', '2022-06-02 16:08:43');
INSERT INTO `gen_table_column` VALUES (65, '7', 'updateUserId', '修改用户', 'bigint(20)', 'Long', 'updateUserId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 8, '管理员', '2022-06-02 16:07:26', '', '2022-06-02 16:08:43');
INSERT INTO `gen_table_column` VALUES (66, '7', 'updateTime', '修改时间', 'datetime', 'LocalDateTime', 'updateTime', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'datetime', '', 9, '管理员', '2022-06-02 16:07:26', '', '2022-06-02 16:08:43');
INSERT INTO `gen_table_column` VALUES (67, '7', 'path', '菜单路径', 'varchar(255)', 'String', 'path', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 10, '管理员', '2022-06-02 16:07:26', '', '2022-06-02 16:08:43');
INSERT INTO `gen_table_column` VALUES (68, '7', 'iconClass', '菜单图标', 'varchar(255)', 'String', 'iconClass', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 11, '管理员', '2022-06-02 16:07:26', '', '2022-06-02 16:08:43');
INSERT INTO `gen_table_column` VALUES (69, '8', 'id', '角色主键', 'bigint(20)', 'Long', 'id', '1', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (70, '8', 'name', '角色名称', 'varchar(20)', 'String', 'name', '0', '0', '1', '1', '1', '1', '1', 'LIKE', 'input', '', 2, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (71, '8', 'inputTime', '录入时间', 'datetime', 'LocalDateTime', 'inputTime', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'datetime', '', 3, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (72, '8', 'inputUserId', '录入用户', 'bigint(20)', 'Long', 'inputUserId', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 4, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (73, '8', 'updateTime', '修改时间', 'datetime', 'LocalDateTime', 'updateTime', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'datetime', '', 5, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (74, '8', 'updateUserId', '修改用户', 'bigint(20)', 'Long', 'updateUserId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 6, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (75, '8', 'checkedMenuIds', '勾选菜单ids', 'json', 'String', 'checkedMenuIds', '0', '0', NULL, '1', '1', '1', '1', 'EQ', NULL, '', 7, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (76, '8', 'code', '角色编码', 'varchar(20)', 'String', 'code', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 8, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (77, '9', 'id', '主键', 'bigint(20)', 'Long', 'id', '1', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (78, '9', 'roleId', '角色id', 'bigint(20)', 'Long', 'roleId', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 2, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (79, '9', 'menuId', '菜单id', 'bigint(20)', 'Long', 'menuId', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 3, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (80, '9', 'inputUserId', '录入用户', 'bigint(20)', 'Long', 'inputUserId', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 4, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (81, '9', 'inputTime', '录入时间', 'datetime', 'LocalDateTime', 'inputTime', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'datetime', '', 5, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (82, '10', 'id', '主键', 'bigint(20)', 'Long', 'id', '1', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (83, '10', 'userId', '用户id', 'bigint(20)', 'Long', 'userId', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 2, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (84, '10', 'roleId', '角色id', 'bigint(20)', 'Long', 'roleId', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 3, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (85, '10', 'inputTime', '录入时间', 'datetime', 'LocalDateTime', 'inputTime', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'datetime', '', 4, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (86, '10', 'inputUserId', '录入用户', 'bigint(20)', 'Long', 'inputUserId', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 5, '管理员', '2022-06-02 16:07:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (87, '11', 'id', '主键ID', 'bigint(20)', 'Long', 'id', '1', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, '管理员', '2022-06-07 13:41:34', '', '2022-06-07 13:44:12');
INSERT INTO `gen_table_column` VALUES (88, '11', 'login_name', '登录账号', 'varchar(50)', 'String', 'loginName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 2, '管理员', '2022-06-07 13:41:34', '', '2022-06-07 13:44:12');
INSERT INTO `gen_table_column` VALUES (89, '11', 'ip_addr', '登录IP地址', 'varchar(128)', 'String', 'ipAddr', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 3, '管理员', '2022-06-07 13:41:34', '', '2022-06-07 13:44:12');
INSERT INTO `gen_table_column` VALUES (90, '11', 'status', '登录状态（0成功 1失败）', 'char(1)', 'String', 'status', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'radio', '', 4, '管理员', '2022-06-07 13:41:34', '', '2022-06-07 13:44:12');
INSERT INTO `gen_table_column` VALUES (91, '11', 'msg', '提示信息', 'varchar(255)', 'String', 'msg', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 5, '管理员', '2022-06-07 13:41:34', '', '2022-06-07 13:44:12');
INSERT INTO `gen_table_column` VALUES (92, '11', 'access_time', '访问时间', 'datetime', 'LocalDateTime', 'accessTime', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'datetime', '', 6, '管理员', '2022-06-07 13:41:34', '', '2022-06-07 13:44:12');
INSERT INTO `gen_table_column` VALUES (93, '12', 'id', '日志主键', 'bigint(20)', 'Long', 'id', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, '管理员', '2022-06-07 13:41:34', '', '2022-06-07 15:01:53');
INSERT INTO `gen_table_column` VALUES (94, '12', 'title', '模块标题', 'varchar(50)', 'String', 'title', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 2, '管理员', '2022-06-07 13:41:34', '', '2022-06-07 15:01:53');
INSERT INTO `gen_table_column` VALUES (95, '12', 'business_type', '业务类型（0其它 1新增 2修改 3删除）', 'int(2)', 'Integer', 'businessType', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'select', '', 3, '管理员', '2022-06-07 13:41:34', '', '2022-06-07 15:01:53');
INSERT INTO `gen_table_column` VALUES (96, '12', 'method', '方法名称', 'varchar(100)', 'String', 'method', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 4, '管理员', '2022-06-07 13:41:34', '', '2022-06-07 15:01:53');
INSERT INTO `gen_table_column` VALUES (97, '12', 'request_method', '请求方式', 'varchar(10)', 'String', 'requestMethod', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 5, '管理员', '2022-06-07 13:41:34', '', '2022-06-07 15:01:53');
INSERT INTO `gen_table_column` VALUES (98, '12', 'operator_type', '操作类别（0其它 1后台用户 2手机端用户）', 'int(1)', 'Integer', 'operatorType', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'select', '', 6, '管理员', '2022-06-07 13:41:34', '', '2022-06-07 15:01:53');
INSERT INTO `gen_table_column` VALUES (99, '12', 'ope_name', '操作人员', 'varchar(50)', 'String', 'opeName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 7, '管理员', '2022-06-07 13:41:34', '', '2022-06-07 15:01:53');
INSERT INTO `gen_table_column` VALUES (100, '12', 'dept_name', '部门名称', 'varchar(50)', 'String', 'deptName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 8, '管理员', '2022-06-07 13:41:34', '', '2022-06-07 15:01:53');
INSERT INTO `gen_table_column` VALUES (101, '12', 'ope_url', '请求URL', 'varchar(255)', 'String', 'opeUrl', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 9, '管理员', '2022-06-07 13:41:34', '', '2022-06-07 15:01:53');
INSERT INTO `gen_table_column` VALUES (102, '12', 'ope_ip', '主机地址', 'varchar(128)', 'String', 'opeIp', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 10, '管理员', '2022-06-07 13:41:34', '', '2022-06-07 15:01:53');
INSERT INTO `gen_table_column` VALUES (103, '12', 'ope_location', '操作地点', 'varchar(255)', 'String', 'opeLocation', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 11, '管理员', '2022-06-07 13:41:34', '', '2022-06-07 15:01:53');
INSERT INTO `gen_table_column` VALUES (104, '12', 'ope_param', '请求参数', 'varchar(2000)', 'String', 'opeParam', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'textarea', '', 12, '管理员', '2022-06-07 13:41:34', '', '2022-06-07 15:01:53');
INSERT INTO `gen_table_column` VALUES (105, '12', 'json_result', '返回参数', 'varchar(2000)', 'String', 'jsonResult', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'textarea', '', 13, '管理员', '2022-06-07 13:41:34', '', '2022-06-07 15:01:53');
INSERT INTO `gen_table_column` VALUES (106, '12', 'status', '操作状态（0正常 1异常）', 'int(1)', 'Integer', 'status', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'radio', '', 14, '管理员', '2022-06-07 13:41:34', '', '2022-06-07 15:01:53');
INSERT INTO `gen_table_column` VALUES (107, '12', 'error_msg', '错误消息', 'varchar(2000)', 'String', 'errorMsg', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'textarea', '', 15, '管理员', '2022-06-07 13:41:34', '', '2022-06-07 15:01:53');
INSERT INTO `gen_table_column` VALUES (108, '12', 'ope_time', '操作时间', 'datetime', 'LocalDateTime', 'opeTime', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'datetime', '', 16, '管理员', '2022-06-07 13:41:34', '', '2022-06-07 15:01:53');

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `dict_code` bigint(20) NOT NULL COMMENT '字典编码',
  `dict_sort` int(4) NULL DEFAULT 0 COMMENT '字典排序',
  `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 1, '男', '0', 'sys_user_sex', '', '', 'Y', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '性别男');
INSERT INTO `sys_dict_data` VALUES (2, 2, '女', '1', 'sys_user_sex', '', '', 'N', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '性别女');
INSERT INTO `sys_dict_data` VALUES (3, 3, '未知', '2', 'sys_user_sex', '', '', 'N', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '性别未知');
INSERT INTO `sys_dict_data` VALUES (4, 1, '显示', '0', 'sys_show_hide', '', 'primary', 'Y', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '显示菜单');
INSERT INTO `sys_dict_data` VALUES (5, 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '隐藏菜单');
INSERT INTO `sys_dict_data` VALUES (6, 1, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (7, 2, '停用', '1', 'sys_normal_disable', '', 'danger', 'N', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '停用状态');
INSERT INTO `sys_dict_data` VALUES (8, 1, '正常', '0', 'sys_job_status', '', 'primary', 'Y', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (9, 2, '暂停', '1', 'sys_job_status', '', 'danger', 'N', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '停用状态');
INSERT INTO `sys_dict_data` VALUES (10, 1, '默认', 'DEFAULT', 'sys_job_group', '', '', 'Y', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '默认分组');
INSERT INTO `sys_dict_data` VALUES (11, 2, '系统', 'SYSTEM', 'sys_job_group', '', '', 'N', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '系统分组');
INSERT INTO `sys_dict_data` VALUES (12, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '系统默认是');
INSERT INTO `sys_dict_data` VALUES (13, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '系统默认否');
INSERT INTO `sys_dict_data` VALUES (14, 1, '通知', '1', 'sys_notice_type', '', 'warning', 'Y', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '通知');
INSERT INTO `sys_dict_data` VALUES (15, 2, '公告', '2', 'sys_notice_type', '', 'success', 'N', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '公告');
INSERT INTO `sys_dict_data` VALUES (16, 1, '正常', '0', 'sys_notice_status', '', 'primary', 'Y', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (17, 2, '关闭', '1', 'sys_notice_status', '', 'danger', 'N', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '关闭状态');
INSERT INTO `sys_dict_data` VALUES (18, 1, '新增', '1', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '新增操作');
INSERT INTO `sys_dict_data` VALUES (19, 2, '修改', '2', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '修改操作');
INSERT INTO `sys_dict_data` VALUES (20, 3, '删除', '3', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '删除操作');
INSERT INTO `sys_dict_data` VALUES (21, 4, '授权', '4', 'sys_oper_type', '', 'primary', 'N', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '授权操作');
INSERT INTO `sys_dict_data` VALUES (22, 5, '导出', '5', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '导出操作');
INSERT INTO `sys_dict_data` VALUES (23, 6, '导入', '6', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '导入操作');
INSERT INTO `sys_dict_data` VALUES (24, 7, '强退', '7', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '强退操作');
INSERT INTO `sys_dict_data` VALUES (25, 8, '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '生成操作');
INSERT INTO `sys_dict_data` VALUES (26, 9, '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '清空操作');
INSERT INTO `sys_dict_data` VALUES (27, 1, '成功', '0', 'sys_common_status', '', 'primary', 'N', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (28, 2, '失败', '1', 'sys_common_status', '', 'danger', 'N', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '停用状态');
INSERT INTO `sys_dict_data` VALUES (29, 0, '其他', '0', 'sys_oper_type', NULL, 'primary', 'N', '0', '管理员', '2022-06-07 17:34:04', '管理员', '2022-06-07 17:36:49', NULL);
INSERT INTO `sys_dict_data` VALUES (30, 0, '其他', '0', 'sys_oper_source', NULL, 'info', 'N', '0', '管理员', '2022-06-07 17:52:21', '管理员', '2022-06-07 17:54:13', NULL);
INSERT INTO `sys_dict_data` VALUES (31, 0, '后台', '1', 'sys_oper_source', NULL, 'primary', 'N', '0', '管理员', '2022-06-07 17:53:27', '管理员', '2022-06-07 17:54:18', NULL);
INSERT INTO `sys_dict_data` VALUES (32, 0, '移动端', '2', 'sys_oper_source', NULL, 'success', 'N', '0', '管理员', '2022-06-07 17:53:54', '管理员', '2022-06-07 17:54:06', NULL);

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `dict_id` bigint(20) NOT NULL COMMENT '字典主键',
  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '字典类型',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE INDEX `dict_type`(`dict_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, '用户性别', 'sys_user_sex', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '用户性别列表');
INSERT INTO `sys_dict_type` VALUES (2, '菜单状态', 'sys_show_hide', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '菜单状态列表');
INSERT INTO `sys_dict_type` VALUES (3, '系统开关', 'sys_normal_disable', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '系统开关列表');
INSERT INTO `sys_dict_type` VALUES (4, '任务状态', 'sys_job_status', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '任务状态列表');
INSERT INTO `sys_dict_type` VALUES (5, '任务分组', 'sys_job_group', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '任务分组列表');
INSERT INTO `sys_dict_type` VALUES (6, '系统是否', 'sys_yes_no', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '系统是否列表');
INSERT INTO `sys_dict_type` VALUES (7, '通知类型', 'sys_notice_type', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '通知类型列表');
INSERT INTO `sys_dict_type` VALUES (8, '通知状态', 'sys_notice_status', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '通知状态列表');
INSERT INTO `sys_dict_type` VALUES (9, '操作类型', 'sys_oper_type', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '操作类型列表');
INSERT INTO `sys_dict_type` VALUES (10, '系统状态', 'sys_common_status', '0', 'admin', '2022-05-25 11:10:10', '', NULL, '登录状态列表');
INSERT INTO `sys_dict_type` VALUES (11, '操作来源', 'sys_oper_source', '0', '管理员', '2022-06-07 17:52:00', '', NULL, NULL);

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log`  (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `login_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '登录账号',
  `ip_addr` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '登录IP地址',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '提示信息',
  `access_time` datetime(0) NULL DEFAULT NULL COMMENT '访问时间',
  `ip_location` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'IP归属地',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '登录日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_login_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单编码',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `type` int(1) NOT NULL COMMENT '菜单类型 1：模块菜单 2：导航菜单 3：功能按钮',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '上级菜单',
  `input_user_id` bigint(20) NOT NULL COMMENT '录入用户',
  `input_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '录入时间',
  `update_user_id` bigint(20) NULL DEFAULT NULL COMMENT '修改用户',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单路径',
  `icon_class` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `code_unique`(`code`) USING BTREE,
  UNIQUE INDEX `name_unique`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 'system', '系统管理', 1, NULL, 1, '2021-07-29 10:30:40', 1, '2022-01-26 16:30:39', '/system', 'el-icon-s-tools');
INSERT INTO `sys_menu` VALUES (2, 'system-menu', '菜单管理', 2, 1, 1, '2021-07-29 10:30:40', 1, '2022-06-02 17:17:22', '/system/menu', 'el-icon-menu');
INSERT INTO `sys_menu` VALUES (3, 'system-role', '角色管理', 2, 1, 1, '2021-07-29 10:30:40', 1, '2022-06-02 17:17:33', '/system/role', 'el-icon-s-custom');
INSERT INTO `sys_menu` VALUES (4, 'system-user', '用户管理', 2, 1, 1, '2021-07-29 10:30:40', 1, '2022-06-02 17:17:41', '/system/user', 'el-icon-user-solid');
INSERT INTO `sys_menu` VALUES (5, 'menu-add', '添加菜单', 3, 2, 1, '2021-07-29 10:30:40', NULL, '2022-01-26 16:30:52', NULL, NULL);
INSERT INTO `sys_menu` VALUES (6, 'menu-update', '修改菜单', 3, 2, 1, '2021-07-29 10:30:40', NULL, '2022-01-26 16:30:58', NULL, NULL);
INSERT INTO `sys_menu` VALUES (7, 'menu-delete', '删除菜单', 3, 2, 1, '2021-07-29 10:30:40', NULL, '2022-01-26 16:31:01', NULL, NULL);
INSERT INTO `sys_menu` VALUES (8, 'role-add', '新增角色', 3, 3, 1, '2021-07-29 10:30:40', NULL, '2022-01-26 16:31:04', NULL, NULL);
INSERT INTO `sys_menu` VALUES (9, 'role-update', '修改角色', 3, 3, 1, '2021-07-29 10:30:40', NULL, '2022-01-26 16:31:06', NULL, NULL);
INSERT INTO `sys_menu` VALUES (10, 'role-delete', '删除角色', 3, 3, 1, '2021-07-29 10:30:40', NULL, '2022-01-26 16:31:09', NULL, NULL);
INSERT INTO `sys_menu` VALUES (11, 'role-permission-menu', '分配菜单权限', 3, 3, 1, '2021-07-29 10:30:40', NULL, '2022-01-26 16:31:11', NULL, NULL);
INSERT INTO `sys_menu` VALUES (12, 'user-add', '新增用户', 3, 4, 1, '2021-07-29 10:30:40', NULL, '2022-01-26 16:31:14', NULL, NULL);
INSERT INTO `sys_menu` VALUES (13, 'user-update', '修改用户', 3, 4, 1, '2021-07-29 10:30:40', NULL, '2022-01-26 16:31:17', NULL, NULL);
INSERT INTO `sys_menu` VALUES (14, 'user-delete', '删除用户', 3, 4, 1, '2021-07-29 10:30:40', NULL, '2022-01-26 16:39:19', NULL, NULL);
INSERT INTO `sys_menu` VALUES (15, 'system-dict', '字典管理', 2, 1, 1, '2022-05-27 23:06:27', 1, '2022-06-02 17:17:48', '/system/dict', NULL);
INSERT INTO `sys_menu` VALUES (16, 'system-dict-add', '新增', 3, 15, 1, '2022-05-27 23:14:56', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (17, 'system-dict-edit', '修改', 3, 15, 1, '2022-05-27 23:15:38', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (18, 'system-dict-delete', '删除', 3, 15, 1, '2022-05-27 23:16:06', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (19, 'sysMonitor', '系统监控', 1, NULL, 1, '2022-05-31 11:32:48', 1, '2022-06-02 17:00:06', '/sysMonitor', 'el-icon-monitor');
INSERT INTO `sys_menu` VALUES (20, 'sysMonitor-job', '定时任务', 2, 19, 1, '2022-05-31 11:33:39', 1, '2022-06-02 16:59:47', '/sysMonitor/job', 'el-icon-alarm-clock');
INSERT INTO `sys_menu` VALUES (21, 'sysMonitor-nacos', 'Nacos控制台', 2, 19, 1, '2022-05-31 11:34:12', 1, '2022-06-02 16:59:39', '/sysMonitor/nacos', 'el-icon-data-board');
INSERT INTO `sys_menu` VALUES (22, 'sysMonitor-monitor', '服务监控中心', 2, 19, 1, '2022-05-31 11:34:59', 1, '2022-06-02 16:59:27', '/sysMonitor/monitor', 'el-icon-data-line');
INSERT INTO `sys_menu` VALUES (23, 'tool', '系统工具', 1, NULL, 1, '2022-06-01 10:31:40', 1, '2022-06-02 17:00:15', '/tool', 'el-icon-suitcase');
INSERT INTO `sys_menu` VALUES (24, 'tool-build', '表单构建', 2, 23, 1, '2022-06-01 10:32:10', 1, '2022-06-02 17:00:25', '/tool/build', 'el-icon-c-scale-to-original');
INSERT INTO `sys_menu` VALUES (25, 'tool-gen', '代码生成', 2, 23, 1, '2022-06-02 16:58:45', 1, '2022-06-02 17:00:34', '/tool/gen', 'el-icon-rank');
INSERT INTO `sys_menu` VALUES (26, 'system-log', '日志管理', 2, 1, 1, '2022-06-02 17:16:51', NULL, NULL, '/system/log', 'el-icon-s-claim');
INSERT INTO `sys_menu` VALUES (27, 'system-log-login', '登录日志', 2, 26, 1, '2022-06-02 17:18:36', NULL, NULL, '/system/log/login', 'el-icon-coordinate');
INSERT INTO `sys_menu` VALUES (28, 'system-log-operation', '操作日志', 2, 26, 1, '2022-06-02 17:19:10', NULL, NULL, '/system/log/operation', 'el-icon-thumb');
INSERT INTO `sys_menu` VALUES (29, 'user-reset-pwd', '重置密码', 3, 4, 1, '2022-06-06 14:26:24', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (30, 'system-login-log-delete', '删除登录日志', 3, 27, 1, '2022-06-07 17:00:31', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (31, 'system-opeLog-delete', '删除操作日志', 3, 28, 1, '2022-06-07 17:27:22', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (32, 'system-opeLog-detail', '操作日志详情', 3, 28, 1, '2022-06-07 17:43:38', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_ope_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_ope_log`;
CREATE TABLE `sys_ope_log`  (
  `id` bigint(20) NOT NULL COMMENT '日志主键',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '模块标题',
  `business_type` int(2) NULL DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '请求方式',
  `operator_type` int(1) NULL DEFAULT 0 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `ope_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '操作人员',
  `ope_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '请求URL',
  `ope_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '主机地址',
  `ope_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '操作地点',
  `ope_param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '返回参数',
  `status` int(1) NULL DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '错误消息',
  `ope_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '操作日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_ope_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) NOT NULL COMMENT '角色主键',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `input_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '录入时间',
  `input_user_id` bigint(20) NOT NULL COMMENT '录入用户',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user_id` bigint(20) NULL DEFAULT NULL COMMENT '修改用户',
  `checked_menu_Ids` json NULL COMMENT '勾选菜单ids',
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色编码',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name_inique`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '管理员', '2021-07-29 10:30:46', 1, NULL, NULL, '[1, 2, 5, 6, 7, 3, 8, 9, 10, 11, 4, 12, 13, 14, 29, 15, 16, 17, 18, 26, 27, 1534097961956134913, 28, 1534104717297963010, 1534108814331826178, 19, 20, 21, 22, 23, 24, 25]', 'admin');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单id',
  `input_user_id` bigint(20) NOT NULL COMMENT '录入用户',
  `input_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '录入时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色菜单关联关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 1, 1, 1, '2022-06-07 17:43:51');
INSERT INTO `sys_role_menu` VALUES (2, 1, 2, 1, '2022-06-07 17:43:51');
INSERT INTO `sys_role_menu` VALUES (3, 1, 5, 1, '2022-06-07 17:43:51');
INSERT INTO `sys_role_menu` VALUES (4, 1, 6, 1, '2022-06-07 17:43:51');
INSERT INTO `sys_role_menu` VALUES (5, 1, 7, 1, '2022-06-07 17:43:51');
INSERT INTO `sys_role_menu` VALUES (6, 1, 3, 1, '2022-06-07 17:43:51');
INSERT INTO `sys_role_menu` VALUES (7, 1, 8, 1, '2022-06-07 17:43:51');
INSERT INTO `sys_role_menu` VALUES (8, 1, 9, 1, '2022-06-07 17:43:51');
INSERT INTO `sys_role_menu` VALUES (9, 1, 10, 1, '2022-06-07 17:43:51');
INSERT INTO `sys_role_menu` VALUES (10, 1, 11, 1, '2022-06-07 17:43:51');
INSERT INTO `sys_role_menu` VALUES (11, 1, 4, 1, '2022-06-07 17:43:51');
INSERT INTO `sys_role_menu` VALUES (12, 1, 12, 1, '2022-06-07 17:43:51');
INSERT INTO `sys_role_menu` VALUES (13, 1, 13, 1, '2022-06-07 17:43:51');
INSERT INTO `sys_role_menu` VALUES (14, 1, 14, 1, '2022-06-07 17:43:51');
INSERT INTO `sys_role_menu` VALUES (15, 1, 29, 1, '2022-06-07 17:43:51');
INSERT INTO `sys_role_menu` VALUES (16, 1, 15, 1, '2022-06-07 17:43:51');
INSERT INTO `sys_role_menu` VALUES (17, 1, 16, 1, '2022-06-07 17:43:51');
INSERT INTO `sys_role_menu` VALUES (18, 1, 17, 1, '2022-06-07 17:43:51');
INSERT INTO `sys_role_menu` VALUES (19, 1, 18, 1, '2022-06-07 17:43:51');
INSERT INTO `sys_role_menu` VALUES (20, 1, 26, 1, '2022-06-07 17:43:51');
INSERT INTO `sys_role_menu` VALUES (21, 1, 27, 1, '2022-06-07 17:43:51');
INSERT INTO `sys_role_menu` VALUES (22, 1, 30, 1, '2022-06-07 17:43:51');
INSERT INTO `sys_role_menu` VALUES (23, 1, 28, 1, '2022-06-07 17:43:51');
INSERT INTO `sys_role_menu` VALUES (24, 1, 31, 1, '2022-06-07 17:43:51');
INSERT INTO `sys_role_menu` VALUES (25, 1, 32, 1, '2022-06-07 17:43:51');
INSERT INTO `sys_role_menu` VALUES (26, 1, 19, 1, '2022-06-07 17:43:51');
INSERT INTO `sys_role_menu` VALUES (27, 1, 20, 1, '2022-06-07 17:43:51');
INSERT INTO `sys_role_menu` VALUES (28, 1, 21, 1, '2022-06-07 17:43:51');
INSERT INTO `sys_role_menu` VALUES (29, 1, 22, 1, '2022-06-07 17:43:51');
INSERT INTO `sys_role_menu` VALUES (30, 1, 23, 1, '2022-06-07 17:43:51');
INSERT INTO `sys_role_menu` VALUES (31, 1, 24, 1, '2022-06-07 17:43:51');
INSERT INTO `sys_role_menu` VALUES (32, 1, 25, 1, '2022-06-07 17:43:51');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL COMMENT '用户主键',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `sex` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '性别',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '住址',
  `login_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录名',
  `password` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `input_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '录入时间',
  `input_user_id` bigint(20) NOT NULL COMMENT '录入用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user_id` bigint(20) NULL DEFAULT NULL COMMENT '修改用户',
  `head_image_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_user_phone`(`phone`) USING BTREE,
  UNIQUE INDEX `unique_user_loginName`(`login_name`) USING BTREE,
  INDEX `index_user_name`(`name`) USING BTREE,
  INDEX `index_user_phone`(`phone`) USING BTREE,
  INDEX `index_user_loginName`(`login_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, '管理员', '男', '18255181971', '18255181971@163.com', '中国', 'admin', 'a66abb5684c45962d887564f08346e8d', '2021-07-29 10:31:27', 1, '2022-06-06 14:12:38', 1, '');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `input_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '录入时间',
  `input_user_id` bigint(20) NOT NULL COMMENT '录入用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色关联关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, 1, '2022-05-27 22:15:31', 1);

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `branch_id` bigint(20) NOT NULL COMMENT 'branch transaction id',
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'global transaction id',
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'undo_log context,such as serialization',
  `rollback_info` longblob NOT NULL COMMENT 'rollback info',
  `log_status` int(11) NOT NULL COMMENT '0:normal status,1:defense status',
  `log_created` datetime(6) NOT NULL COMMENT 'create datetime',
  `log_modified` datetime(6) NOT NULL COMMENT 'modify datetime',
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'AT transaction mode undo table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
