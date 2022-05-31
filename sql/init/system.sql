CREATE DATABASE IF NOT EXISTS `system` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `system`;
-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `dictCode` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dictSort` int(4) NULL DEFAULT 0 COMMENT '字典排序',
  `dictLabel` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '字典标签',
  `dictValue` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '字典键值',
  `dictType` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '字典类型',
  `cssClass` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `listClass` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '表格回显样式',
  `isDefault` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `createBy` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '创建者',
  `createTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updateBy` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '更新者',
  `updateTime` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dictCode`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

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

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `dictId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dictName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '字典名称',
  `dictType` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '字典类型',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `createBy` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '创建者',
  `createTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updateBy` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '更新者',
  `updateTime` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dictId`) USING BTREE,
  UNIQUE INDEX `dict_type`(`dictType`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

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

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单编码',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `type` int(1) NOT NULL COMMENT '菜单类型 1：模块菜单 2：导航菜单 3：功能按钮',
  `parentId` bigint(20) NULL DEFAULT NULL COMMENT '上级菜单',
  `inputUserId` bigint(20) NOT NULL COMMENT '录入用户',
  `inputTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '录入时间',
  `updateUserId` bigint(20) NULL DEFAULT NULL COMMENT '修改用户',
  `updateTime` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单路径',
  `iconClass` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `code_unique`(`code`) USING BTREE,
  UNIQUE INDEX `name_unique`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES (1, 'system', '系统管理', 1, NULL, 1, '2021-07-29 10:30:40', 1, '2022-01-26 16:30:39', '/system', 'el-icon-s-tools');
INSERT INTO `t_menu` VALUES (2, 'menu', '菜单管理', 2, 1, 1, '2021-07-29 10:30:40', 1, '2022-05-26 15:03:59', '/system/menu', 'el-icon-menu');
INSERT INTO `t_menu` VALUES (3, 'role', '角色管理', 2, 1, 1, '2021-07-29 10:30:40', 1, '2022-05-26 15:04:06', '/system/role', 'el-icon-s-custom');
INSERT INTO `t_menu` VALUES (4, 'user', '用户管理', 2, 1, 1, '2021-07-29 10:30:40', 1, '2022-05-26 15:04:13', '/system/user', 'el-icon-user-solid');
INSERT INTO `t_menu` VALUES (5, 'menu-add', '添加菜单', 3, 2, 1, '2021-07-29 10:30:40', NULL, '2022-01-26 16:30:52', NULL, NULL);
INSERT INTO `t_menu` VALUES (6, 'menu-update', '修改菜单', 3, 2, 1, '2021-07-29 10:30:40', NULL, '2022-01-26 16:30:58', NULL, NULL);
INSERT INTO `t_menu` VALUES (7, 'menu-delete', '删除菜单', 3, 2, 1, '2021-07-29 10:30:40', NULL, '2022-01-26 16:31:01', NULL, NULL);
INSERT INTO `t_menu` VALUES (8, 'role-add', '新增角色', 3, 3, 1, '2021-07-29 10:30:40', NULL, '2022-01-26 16:31:04', NULL, NULL);
INSERT INTO `t_menu` VALUES (9, 'role-update', '修改角色', 3, 3, 1, '2021-07-29 10:30:40', NULL, '2022-01-26 16:31:06', NULL, NULL);
INSERT INTO `t_menu` VALUES (10, 'role-delete', '删除角色', 3, 3, 1, '2021-07-29 10:30:40', NULL, '2022-01-26 16:31:09', NULL, NULL);
INSERT INTO `t_menu` VALUES (11, 'role-permission-menu', '分配菜单权限', 3, 3, 1, '2021-07-29 10:30:40', NULL, '2022-01-26 16:31:11', NULL, NULL);
INSERT INTO `t_menu` VALUES (12, 'user-add', '新增用户', 3, 4, 1, '2021-07-29 10:30:40', NULL, '2022-01-26 16:31:14', NULL, NULL);
INSERT INTO `t_menu` VALUES (13, 'user-update', '修改用户', 3, 4, 1, '2021-07-29 10:30:40', NULL, '2022-01-26 16:31:17', NULL, NULL);
INSERT INTO `t_menu` VALUES (14, 'user-delete', '删除用户', 3, 4, 1, '2021-07-29 10:30:40', NULL, '2022-01-26 16:39:19', NULL, NULL);
INSERT INTO `t_menu` VALUES (15, 'dict', '字典管理', 2, 1, 1, '2022-05-27 23:06:27', NULL, NULL, '/system/dict', NULL);
INSERT INTO `t_menu` VALUES (16, 'system-dict-add', '新增', 3, 15, 1, '2022-05-27 23:14:56', NULL, NULL, NULL, NULL);
INSERT INTO `t_menu` VALUES (17, 'system-dict-edit', '修改', 3, 15, 1, '2022-05-27 23:15:38', NULL, NULL, NULL, NULL);
INSERT INTO `t_menu` VALUES (18, 'system-dict-delete', '删除', 3, 15, 1, '2022-05-27 23:16:06', NULL, NULL, NULL, NULL);
INSERT INTO `t_menu` VALUES (19, 'sysMonitor', '系统监控', 1, NULL, 1, '2022-05-31 11:32:48', NULL, NULL, '/sysMonitor', NULL);
INSERT INTO `t_menu` VALUES (20, 'sysMonitor-job', '定时任务', 2, 19, 1, '2022-05-31 11:33:39', 1, '2022-05-31 11:34:22', '/sysMonitor/job', NULL);
INSERT INTO `t_menu` VALUES (21, 'sysMonitor-nacos', 'Nacos控制台', 2, 19, 1, '2022-05-31 11:34:12', NULL, NULL, '/sysMonitor/nacos', NULL);
INSERT INTO `t_menu` VALUES (22, 'sysMonitor-monitor', '服务监控中心', 2, 19, 1, '2022-05-31 11:34:59', NULL, NULL, '/sysMonitor/monitor', NULL);

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `id` bigint(20) NOT NULL COMMENT '角色主键',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `inputTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '录入时间',
  `inputUserId` bigint(20) NOT NULL COMMENT '录入用户',
  `updateTime` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `updateUserId` bigint(20) NULL DEFAULT NULL COMMENT '修改用户',
  `checkedMenuIds` json NULL COMMENT '勾选菜单ids',
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色编码',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name_inique`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES (1, '管理员', '2021-07-29 10:30:46', 1, NULL, NULL, '[1, 2, 5, 6, 7, 3, 8, 9, 10, 11, 4, 12, 13, 14, 1530203785740910593, 1530205919072878593, 1530206096001204225, 1530206213169086466, 1531478775660527618, 1531478986667573250, 1531479128556683265, 1531479325827383297]', 'admin');

-- ----------------------------
-- Table structure for t_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE `t_role_menu`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `roleId` bigint(20) NOT NULL COMMENT '角色id',
  `menuId` bigint(20) NOT NULL COMMENT '菜单id',
  `inputUserId` bigint(20) NOT NULL COMMENT '录入用户',
  `inputTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '录入时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role_menu
-- ----------------------------
INSERT INTO `t_role_menu` VALUES (1, 1, 1, 1, '2022-05-31 11:37:00');
INSERT INTO `t_role_menu` VALUES (2, 1, 2, 1, '2022-05-31 11:37:00');
INSERT INTO `t_role_menu` VALUES (3, 1, 5, 1, '2022-05-31 11:37:00');
INSERT INTO `t_role_menu` VALUES (4, 1, 6, 1, '2022-05-31 11:37:00');
INSERT INTO `t_role_menu` VALUES (5, 1, 7, 1, '2022-05-31 11:37:00');
INSERT INTO `t_role_menu` VALUES (6, 1, 3, 1, '2022-05-31 11:37:00');
INSERT INTO `t_role_menu` VALUES (7, 1, 8, 1, '2022-05-31 11:37:00');
INSERT INTO `t_role_menu` VALUES (8, 1, 9, 1, '2022-05-31 11:37:00');
INSERT INTO `t_role_menu` VALUES (9, 1, 10, 1, '2022-05-31 11:37:00');
INSERT INTO `t_role_menu` VALUES (10, 1, 11, 1, '2022-05-31 11:37:00');
INSERT INTO `t_role_menu` VALUES (11, 1, 4, 1, '2022-05-31 11:37:00');
INSERT INTO `t_role_menu` VALUES (12, 1, 12, 1, '2022-05-31 11:37:00');
INSERT INTO `t_role_menu` VALUES (13, 1, 13, 1, '2022-05-31 11:37:00');
INSERT INTO `t_role_menu` VALUES (14, 1, 14, 1, '2022-05-31 11:37:00');
INSERT INTO `t_role_menu` VALUES (15, 1, 15, 1, '2022-05-31 11:37:00');
INSERT INTO `t_role_menu` VALUES (16, 1, 16, 1, '2022-05-31 11:37:00');
INSERT INTO `t_role_menu` VALUES (17, 1, 17, 1, '2022-05-31 11:37:00');
INSERT INTO `t_role_menu` VALUES (18, 1, 18, 1, '2022-05-31 11:37:00');
INSERT INTO `t_role_menu` VALUES (19, 1, 19, 1, '2022-05-31 11:37:00');
INSERT INTO `t_role_menu` VALUES (20, 1, 20, 1, '2022-05-31 11:37:00');
INSERT INTO `t_role_menu` VALUES (21, 1, 21, 1, '2022-05-31 11:37:00');
INSERT INTO `t_role_menu` VALUES (22, 1, 22, 1, '2022-05-31 11:37:00');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint(20) NOT NULL COMMENT '用户主键',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `sex` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '性别',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '住址',
  `loginName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录名',
  `password` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `inputTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '录入时间',
  `inputUserId` bigint(20) NOT NULL COMMENT '录入用户ID',
  `updateTime` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `updateUserId` bigint(20) NULL DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_user_phone`(`phone`) USING BTREE,
  UNIQUE INDEX `unique_user_loginName`(`loginName`) USING BTREE,
  INDEX `index_user_name`(`name`) USING BTREE,
  INDEX `index_user_phone`(`phone`) USING BTREE,
  INDEX `index_user_loginName`(`loginName`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, '管理员', '男', '18255555555', '596177831@qq.com', '中国', 'admin', '53f139e187e60f8376343990737b8fec', '2021-07-29 10:31:27', 1, '2022-05-27 22:15:31', 1);

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `userId` bigint(20) NOT NULL COMMENT '用户id',
  `roleId` bigint(20) NOT NULL COMMENT '角色id',
  `inputTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '录入时间',
  `inputUserId` bigint(20) NOT NULL COMMENT '录入用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES (1, 1, 1, '2022-05-27 22:15:31', 1);

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
