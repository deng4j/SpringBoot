/*
 Navicat Premium Data Transfer

 Source Server         : mydatabase
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : ssm_db

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 26/06/2022 16:36:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tbl_book
-- ----------------------------
DROP TABLE IF EXISTS `tbl_book`;
CREATE TABLE `tbl_book`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `TYPE` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_book
-- ----------------------------
INSERT INTO `tbl_book` VALUES (1, '计算机理论', 'Spring实战 第5版', 'Spring入门经典教材，深入理解Spring原理技术内幕');
INSERT INTO `tbl_book` VALUES (2, '计算机理论', 'Spring 5核心原理与30个类手写实战', '十年沉淀之作,手写Spring精华思想');
INSERT INTO `tbl_book` VALUES (3, '计算机理论', 'Spring 5设计模式', '深入Spring源码剖析,Spring源码蕴含的10大设计模式');
INSERT INTO `tbl_book` VALUES (4, '市场营销', '直播就该这么做:主播高效沟通实战指南', '李子柒、李佳琦、薇娅成长为网红的秘密都在书中');
INSERT INTO `tbl_book` VALUES (5, '市场营销', '直播销讲实战一本通', '和秋叶一起学系列网络营销书籍');
INSERT INTO `tbl_book` VALUES (6, '市场营销', '直播带货:淘宝、天猫直播从新手到高手', '一本教你如何玩转直播的书,10堂课轻松实现带货月入3W+');
INSERT INTO `tbl_book` VALUES (7, '儿童读物', '格林童话', '儿童最好的书');
INSERT INTO `tbl_book` VALUES (8, '历史', '二战风云', '描述二战');
INSERT INTO `tbl_book` VALUES (9, '计算机理论', 'Spring实战 第5版', 'Spring入门经典教材，深入理解Spring原理技术内幕');
INSERT INTO `tbl_book` VALUES (10, '人微权轻', '大啊大大', '女被警方');
INSERT INTO `tbl_book` VALUES (11, '儿童读物', '龙珠', '热血');
INSERT INTO `tbl_book` VALUES (15, '儿童读物', '格林童话', '培育孩子纯真善良的儿童读物');

SET FOREIGN_KEY_CHECKS = 1;
