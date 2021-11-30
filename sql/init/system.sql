/*
 Navicat Premium Data Transfer

 Source Server         : cloud-vue
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : 127.0.0.1:3306
 Source Schema         : vue_cli_2021

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 29/07/2021 10:32:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
  `sort` int(11) NOT NULL COMMENT '序号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `code_unique`(`code`) USING BTREE,
  UNIQUE INDEX `name_unique`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES (1, 'system', '系统管理', 1, NULL, 1, '2021-07-29 10:30:40', NULL, NULL, '/system', 'el-icon-s-tools', 1);
INSERT INTO `t_menu` VALUES (11, 'menu', '菜单管理', 2, 1, 1, '2021-07-29 10:30:40', NULL, NULL, '/index/menu', 'el-icon-menu', 1);
INSERT INTO `t_menu` VALUES (22, 'role', '角色管理', 2, 1, 1, '2021-07-29 10:30:40', NULL, NULL, '/index/role', 'el-icon-s-custom', 2);
INSERT INTO `t_menu` VALUES (33, 'user', '用户管理', 2, 1, 1, '2021-07-29 10:30:40', NULL, NULL, '/index/user', 'el-icon-user-solid', 3);
INSERT INTO `t_menu` VALUES (1101, 'menu-add', '添加菜单', 3, 11, 1, '2021-07-29 10:30:40', NULL, NULL, NULL, NULL, 1);
INSERT INTO `t_menu` VALUES (1102, 'menu-update', '修改菜单', 3, 11, 1, '2021-07-29 10:30:40', NULL, NULL, NULL, NULL, 2);
INSERT INTO `t_menu` VALUES (1103, 'menu-delete', '删除菜单', 3, 11, 1, '2021-07-29 10:30:40', NULL, NULL, NULL, NULL, 3);
INSERT INTO `t_menu` VALUES (2201, 'role-add', '新增角色', 3, 22, 1, '2021-07-29 10:30:40', NULL, NULL, NULL, NULL, 1);
INSERT INTO `t_menu` VALUES (2202, 'role-update', '修改角色', 3, 22, 1, '2021-07-29 10:30:40', NULL, NULL, NULL, NULL, 2);
INSERT INTO `t_menu` VALUES (2203, 'role-delete', '删除角色', 3, 22, 1, '2021-07-29 10:30:40', NULL, NULL, NULL, NULL, 3);
INSERT INTO `t_menu` VALUES (2204, 'role-permission-menu', '分配菜单权限', 3, 22, 1, '2021-07-29 10:30:40', NULL, NULL, NULL, NULL, 4);
INSERT INTO `t_menu` VALUES (3301, 'user-add', '新增用户', 3, 33, 1, '2021-07-29 10:30:40', NULL, NULL, NULL, NULL, 1);
INSERT INTO `t_menu` VALUES (3302, 'user-update', '修改用户', 3, 33, 1, '2021-07-29 10:30:40', NULL, NULL, NULL, NULL, 2);
INSERT INTO `t_menu` VALUES (3303, 'user-delete', '删除用户', 3, 33, 1, '2021-07-29 10:30:40', NULL, NULL, NULL, NULL, 3);

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
INSERT INTO `t_role` VALUES (1, '管理员', '2021-07-29 10:30:46', 1, NULL, NULL, '[1, 11, 22, 33, 1101, 1102, 1103, 2201, 2202, 2203, 2204, 3301, 3302, 3303]', 'manager');

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
INSERT INTO `t_role_menu` VALUES (1, 1, 1, 1, '2021-07-29 10:31:15');
INSERT INTO `t_role_menu` VALUES (2, 1, 11, 1, '2021-07-29 10:31:15');
INSERT INTO `t_role_menu` VALUES (3, 1, 22, 1, '2021-07-29 10:31:15');
INSERT INTO `t_role_menu` VALUES (4, 1, 33, 1, '2021-07-29 10:31:15');
INSERT INTO `t_role_menu` VALUES (5, 1, 1101, 1, '2021-07-29 10:31:15');
INSERT INTO `t_role_menu` VALUES (6, 1, 1102, 1, '2021-07-29 10:31:15');
INSERT INTO `t_role_menu` VALUES (7, 1, 1103, 1, '2021-07-29 10:31:15');
INSERT INTO `t_role_menu` VALUES (8, 1, 2201, 1, '2021-07-29 10:31:15');
INSERT INTO `t_role_menu` VALUES (9, 1, 2202, 1, '2021-07-29 10:31:15');
INSERT INTO `t_role_menu` VALUES (10, 1, 2203, 1, '2021-07-29 10:31:15');
INSERT INTO `t_role_menu` VALUES (11, 1, 2204, 1, '2021-07-29 10:31:15');
INSERT INTO `t_role_menu` VALUES (12, 1, 3301, 1, '2021-07-29 10:31:15');
INSERT INTO `t_role_menu` VALUES (13, 1, 3302, 1, '2021-07-29 10:31:15');
INSERT INTO `t_role_menu` VALUES (14, 1, 3303, 1, '2021-07-29 10:31:15');

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
INSERT INTO `t_user` VALUES (1, '管理员', '男', '18255555555', NULL, NULL, 'admin', '53f139e187e60f8376343990737b8fec', '2021-07-29 10:31:27', 1, NULL, NULL);

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
INSERT INTO `t_user_role` VALUES (1, 1, 1, '2021-07-29 10:31:33', 1);

SET FOREIGN_KEY_CHECKS = 1;
