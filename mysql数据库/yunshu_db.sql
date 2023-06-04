/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : yunshu_db

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2018-07-04 17:34:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) default NULL,
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `car`
-- ----------------------------
DROP TABLE IF EXISTS `car`;
CREATE TABLE `car` (
  `carId` int(11) NOT NULL auto_increment,
  `carNo` varchar(20) default NULL,
  `carModel` int(11) default NULL,
  `pinpai` varchar(20) default NULL,
  `youxing` varchar(20) default NULL,
  `haoyouliang` varchar(20) default NULL,
  `chexian` varchar(30) default NULL,
  `zonglicheng` varchar(30) default NULL,
  `wxcs` varchar(20) default NULL,
  `carMemo` longtext,
  PRIMARY KEY  (`carId`),
  KEY `FK107B423847B8C` (`carModel`),
  CONSTRAINT `FK107B423847B8C` FOREIGN KEY (`carModel`) REFERENCES `carmodel` (`modelId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of car
-- ----------------------------
INSERT INTO `car` VALUES ('1', '川A-2934', '1', '长安', '90号汽油', '5L/100公里', '2018年12月15日', '9000公里', '3次', '好车子，跑得远');
INSERT INTO `car` VALUES ('2', '川B-2398', '2', '福田', '93号汽油', '8L/100公里', '2019年04月15日', '8200公里', '1次', '好货车，福田造');

-- ----------------------------
-- Table structure for `carmodel`
-- ----------------------------
DROP TABLE IF EXISTS `carmodel`;
CREATE TABLE `carmodel` (
  `modelId` int(11) NOT NULL auto_increment,
  `modelName` varchar(20) default NULL,
  PRIMARY KEY  (`modelId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of carmodel
-- ----------------------------
INSERT INTO `carmodel` VALUES ('1', '小货车');
INSERT INTO `carmodel` VALUES ('2', '大货车');

-- ----------------------------
-- Table structure for `jztype`
-- ----------------------------
DROP TABLE IF EXISTS `jztype`;
CREATE TABLE `jztype` (
  `typeId` int(11) NOT NULL auto_increment,
  `typeName` varchar(20) default NULL,
  PRIMARY KEY  (`typeId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jztype
-- ----------------------------
INSERT INTO `jztype` VALUES ('1', 'C1小车型');
INSERT INTO `jztype` VALUES ('2', 'A1大车型');

-- ----------------------------
-- Table structure for `userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `jiahao` varchar(20) NOT NULL,
  `password` varchar(20) default NULL,
  `name` varchar(20) default NULL,
  `sex` varchar(4) default NULL,
  `telephone` varchar(20) default NULL,
  `jzTypeObj` int(11) default NULL,
  `jialing` varchar(20) default NULL,
  `address` varchar(80) default NULL,
  PRIMARY KEY  (`jiahao`),
  KEY `FKF3F34B392AA96159` (`jzTypeObj`),
  CONSTRAINT `FKF3F34B392AA96159` FOREIGN KEY (`jzTypeObj`) REFERENCES `jztype` (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('jl001', '123', '王立', '男', '15983082083', '1', '3年', '四川成都');
INSERT INTO `userinfo` VALUES ('jl002', '123', '李明', '男', '13508308134', '2', '5年', '四川南充');

-- ----------------------------
-- Table structure for `yunshu`
-- ----------------------------
DROP TABLE IF EXISTS `yunshu`;
CREATE TABLE `yunshu` (
  `yunshuId` int(11) NOT NULL auto_increment,
  `userObj` varchar(20) default NULL,
  `carObj` int(11) default NULL,
  `yshw` varchar(20) default NULL,
  `zl` varchar(20) default NULL,
  `xysj` varchar(20) default NULL,
  `qsd` varchar(20) default NULL,
  `mudidi` varchar(20) default NULL,
  `gonglishu` varchar(20) default NULL,
  `yunshuMemo` longtext,
  PRIMARY KEY  (`yunshuId`),
  KEY `FK9E83CACE2309C735` (`carObj`),
  KEY `FK9E83CACEC80FC67` (`userObj`),
  CONSTRAINT `FK9E83CACE2309C735` FOREIGN KEY (`carObj`) REFERENCES `car` (`carId`),
  CONSTRAINT `FK9E83CACEC80FC67` FOREIGN KEY (`userObj`) REFERENCES `userinfo` (`jiahao`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of yunshu
-- ----------------------------
INSERT INTO `yunshu` VALUES ('1', 'jl001', '1', '铝合金', '1.5吨', '2天', '成都', '长沙', '1980公里', '2018-03-05 出发');
INSERT INTO `yunshu` VALUES ('2', 'jl002', '2', '钢筋', '3吨', '3天', '南充', '广州', '2200公里', '2018-03-11下午3点出发');
