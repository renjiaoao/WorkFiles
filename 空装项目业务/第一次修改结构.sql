/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.1.1
 Source Server Type    : MySQL
 Source Server Version : 50561
 Source Host           : localhost:3306
 Source Schema         : ssboxkz

 Target Server Type    : MySQL
 Target Server Version : 50561
 File Encoding         : 65001

 Date: 24/04/2019 16:08:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for _cabinet
-- ----------------------------
DROP TABLE IF EXISTS `_cabinet`;
CREATE TABLE `_cabinet`  (
  `cabinet_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '柜子编号：参与硬件协的通讯议数据，保证系统内唯一数值',
  `number` int(11) UNSIGNED NOT NULL COMMENT '柜门编号',
  `id_dept` int(11) NOT NULL DEFAULT 0 COMMENT '所属部门ID',
  `ip_address` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'IP地址',
  `port` int(11) NOT NULL COMMENT '网络端口',
  `lattice_count` int(11) UNSIGNED DEFAULT 0 COMMENT '柜门数量',
  `id_location` int(11) DEFAULT 0 COMMENT '存放位置ID',
  `debug_status` tinyint(255) UNSIGNED DEFAULT 1 COMMENT '调试采用',
  PRIMARY KEY (`cabinet_id`) USING BTREE,
  INDEX `cabinet_id`(`cabinet_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 66 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '柜子配置表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for _carrier
-- ----------------------------
DROP TABLE IF EXISTS `_carrier`;
CREATE TABLE `_carrier`  (
  `carrier_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '涉密载体的ID编号',
  `carrier_serialnum` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'RFID卡片号',
  `dept_id` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '部门ID',
  `sub_dept_id` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '子部门ID',
  `cabinet_id` int(11) UNSIGNED ZEROFILL NOT NULL DEFAULT 00000000000 COMMENT '柜号',
  `lattice_id` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '柜门号',
  `rfid` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '黏贴到具体载体上的 RFID卡片的 ID 编号',
  `classify` tinyint(2) UNSIGNED NOT NULL DEFAULT 1 COMMENT '1(处室共享即不共享）、2（部门共享即共享）',
  `carriertype_id` int(11) UNSIGNED NOT NULL COMMENT '载体类别的ID编号',
  `safelevel_id` int(11) UNSIGNED NOT NULL COMMENT '载体安全等级的ID编号',
  `user_id` int(11) UNSIGNED DEFAULT NULL,
  `user_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `use_type_id` int(11) UNSIGNED DEFAULT NULL,
  `use_type_name` varchar(21) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status` tinyint(2) UNSIGNED ZEROFILL NOT NULL DEFAULT 01 COMMENT '1:在位；2:取走；3:外出',
  `update_time` varchar(21) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '载体状态更新时间',
  `device_serialnum` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '现实中的设备编码，如：XTS-BJB-001(B0205)',
  `delete_time` bigint(22) UNSIGNED DEFAULT NULL,
  `id_location` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`carrier_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 837 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '载体表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for _carrier_description
-- ----------------------------
DROP TABLE IF EXISTS `_carrier_description`;
CREATE TABLE `_carrier_description`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `carrier_id` int(11) UNSIGNED NOT NULL COMMENT '载体编号',
  `brandmodel` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '品牌型号',
  `configuration` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '配置',
  `memo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 837 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for _carrier_serial
-- ----------------------------
DROP TABLE IF EXISTS `_carrier_serial`;
CREATE TABLE `_carrier_serial`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `serial` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `carrier_id` int(10) UNSIGNED NOT NULL,
  `user_id` int(10) UNSIGNED NOT NULL,
  `user_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `delete_time` bigint(22) UNSIGNED DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1733 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for _carriersafelevel
-- ----------------------------
DROP TABLE IF EXISTS `_carriersafelevel`;
CREATE TABLE `_carriersafelevel`  (
  `safelevel_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '载体安全等级的ID编号',
  `safelevelname` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '安全等级的名称',
  PRIMARY KEY (`safelevel_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '涉密载体的安全等级信息' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for _carriertype
-- ----------------------------
DROP TABLE IF EXISTS `_carriertype`;
CREATE TABLE `_carriertype`  (
  `carriertype_id` int(11) NOT NULL AUTO_INCREMENT,
  `typename` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '载体的类别名称',
  `iconpath` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '载体的图标的存储路径',
  `on_iconpath` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `off_iconpath` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `ordinal` int(255) NOT NULL DEFAULT 0,
  `classify` tinyint(2) NOT NULL DEFAULT 0,
  PRIMARY KEY (`carriertype_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '涉密载体类别' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for _department
-- ----------------------------
DROP TABLE IF EXISTS `_department`;
CREATE TABLE `_department`  (
  `id_dept` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `ordinal` int(255) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_dept`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 66 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for _door_status
-- ----------------------------
DROP TABLE IF EXISTS `_door_status`;
CREATE TABLE `_door_status`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `cabinet_id` int(11) UNSIGNED NOT NULL,
  `lattice_id` int(11) UNSIGNED NOT NULL,
  `status` tinyint(4) UNSIGNED NOT NULL COMMENT '柜门状态',
  `updatetime` varchar(21) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT '柜门状态最后更新的日期时间',
  `finger_id` int(11) NOT NULL DEFAULT 0 COMMENT '指纹编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 944 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for _door_status_log
-- ----------------------------
DROP TABLE IF EXISTS `_door_status_log`;
CREATE TABLE `_door_status_log`  (
  `id` int(11) NOT NULL,
  `cabinet_id` int(11) DEFAULT NULL COMMENT '柜子ID',
  `lattice_id` int(11) DEFAULT NULL COMMENT '柜门ID',
  `status` tinyint(2) DEFAULT NULL COMMENT '柜门状态',
  `ctime` varchar(21) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '柜门状态对应的日期时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for _location
-- ----------------------------
DROP TABLE IF EXISTS `_location`;
CREATE TABLE `_location`  (
  `id_location` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id_location`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for _manager
-- ----------------------------
DROP TABLE IF EXISTS `_manager`;
CREATE TABLE `_manager`  (
  `id_manager` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '账户名称',
  `password` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '账户密码',
  `type` tinyint(4) DEFAULT NULL COMMENT '1是系统管理员\\n2是超级管理员\\n3是部门管理员\\n4审计员',
  `id_dept` int(11) DEFAULT 0 COMMENT '部门ID',
  `islocked` tinyint(4) DEFAULT NULL COMMENT '是否禁用',
  `lockedtime` varchar(21) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '禁用日期时间',
  `isshowMc` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否显示主控，1：显示',
  `isshowAc` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否显示门禁，1：显示',
  PRIMARY KEY (`id_manager`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 60 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '管理员表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for _manager_log
-- ----------------------------
DROP TABLE IF EXISTS `_manager_log`;
CREATE TABLE `_manager_log`  (
  `id` int(11) NOT NULL,
  `id_manager` int(11) DEFAULT NULL COMMENT '管理员ID',
  `behavior` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '行为内容描述',
  `btime` varchar(21) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '登记的日期时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '管理人员行为日志表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for _out
-- ----------------------------
DROP TABLE IF EXISTS `_out`;
CREATE TABLE `_out`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `dept_id` int(11) UNSIGNED DEFAULT NULL,
  `sub_dept_id` int(11) UNSIGNED DEFAULT NULL,
  `applicant_user_id` int(11) DEFAULT NULL COMMENT '申请人的用户ID编号',
  `applicant_user_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `reasonwithcarrier` varchar(201) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '携带涉密载体的外出原因',
  `follower_user_id` int(11) UNSIGNED DEFAULT NULL COMMENT '随从人员的用户ID编号',
  `follower_user_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '随从人员的名字',
  `approver_user_id` int(11) UNSIGNED DEFAULT NULL COMMENT '审批人员1的用户ID编号',
  `approver_user_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '批准人用户名',
  `planouttime` varchar(21) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `planbacktime` varchar(21) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `trans_way` varchar(101) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `dest_address` varchar(101) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `sub_dept_id`(`sub_dept_id`) USING BTREE,
  INDEX `applicant_user_id`(`applicant_user_id`) USING BTREE,
  INDEX `applicant_user_name`(`applicant_user_name`) USING BTREE,
  INDEX `approver_user_id`(`approver_user_id`) USING BTREE,
  INDEX `approver_user_name`(`approver_user_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '外出审批表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for _out_carrier
-- ----------------------------
DROP TABLE IF EXISTS `_out_carrier`;
CREATE TABLE `_out_carrier`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `carrier_id` int(10) UNSIGNED NOT NULL COMMENT '对应carrier表',
  `user_id` int(10) UNSIGNED NOT NULL,
  `out_id` int(10) UNSIGNED NOT NULL COMMENT '对应Out表',
  `dept_id` int(11) UNSIGNED NOT NULL,
  `sub_dept_id` int(11) UNSIGNED NOT NULL,
  `carrier_type` varchar(21) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '载体类型',
  `carrier_key_serialnum` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'RFID卡片号',
  `device_serialnum` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '现实中的设备编码，如：XTS-BJB-001(B0205)',
  `isapprove` tinyint(2) UNSIGNED NOT NULL,
  `isreturn` tinyint(2) UNSIGNED NOT NULL,
  `approvetime` varchar(21) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `planouttime` varchar(21) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '计划外出的日期时间',
  `planbacktime` varchar(21) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '计划返回的日期时间',
  `actualouttime` varchar(21) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `actualbacktime` varchar(21) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '实际返回时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `carrier_id`(`carrier_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `out_id`(`out_id`) USING BTREE,
  INDEX `planouttime`(`planouttime`) USING BTREE,
  INDEX `planbacktime`(`planbacktime`) USING BTREE,
  INDEX `actualouttime`(`actualouttime`) USING BTREE,
  INDEX `actualbacktime`(`actualbacktime`) USING BTREE,
  INDEX `device_serialnum`(`device_serialnum`) USING BTREE,
  INDEX `approvetime`(`approvetime`) USING BTREE,
  INDEX `isreturn`(`isreturn`) USING BTREE,
  INDEX `isapprove`(`isapprove`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `sub_dept_id`(`sub_dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for _out_from_oa
-- ----------------------------
DROP TABLE IF EXISTS `_out_from_oa`;
CREATE TABLE `_out_from_oa`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `carrier_key_serialnum` varchar(45) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT '涉密载体的字符串序列号：在系统内保持唯一性',
  `user_key_serialnum` varchar(45) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT '用户 Key 的 字符串序列号：在系统内保持唯一性',
  `applytime` int(45) NOT NULL COMMENT 'OA申请发过来的时间',
  `enpowertime` int(45) DEFAULT NULL COMMENT '31命令成功后赋值',
  `realouttime` int(45) DEFAULT NULL COMMENT '记录载体实际的外出时间，需要有个判定依据？',
  `isreturn` tinyint(2) NOT NULL DEFAULT 0 COMMENT '是否载体返回',
  `returntime` int(45) DEFAULT NULL COMMENT '载体返回时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for _rfid_status
-- ----------------------------
DROP TABLE IF EXISTS `_rfid_status`;
CREATE TABLE `_rfid_status`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `cabinet_id` int(11) DEFAULT NULL COMMENT '柜子ID',
  `lattice_id` int(11) DEFAULT NULL COMMENT '柜门ID',
  `rfid` int(11) DEFAULT NULL COMMENT '关联到具体的涉密载体上的RFID的卡片',
  `status` int(11) DEFAULT NULL COMMENT '载体状态',
  `ctime` varchar(21) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '载体状态对应的日期时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16696 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '载体状态表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for _sub_department
-- ----------------------------
DROP TABLE IF EXISTS `_sub_department`;
CREATE TABLE `_sub_department`  (
  `id_sub_department` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门名称',
  `parent_depart_id` int(11) NOT NULL COMMENT '上级部门ID',
  PRIMARY KEY (`id_sub_department`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci COMMENT = '二级部门' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for _system
-- ----------------------------
DROP TABLE IF EXISTS `_system`;
CREATE TABLE `_system`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `maxlogintimes` int(11) DEFAULT 3 COMMENT '管理员的最大尝试登录次数',
  `minpwrdlength` int(11) DEFAULT 6 COMMENT '最小的密码长度',
  `customer` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户',
  `timeoutthreshold` int(11) NOT NULL COMMENT '超时未回的阈值',
  `overduethreshold` int(11) NOT NULL COMMENT '外出逾期的阈值',
  `screen_title_size` int(11) UNSIGNED NOT NULL DEFAULT 14,
  `screen_title_text` varchar(101) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '部门',
  `screen_content_size` int(11) UNSIGNED NOT NULL DEFAULT 14,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统配置表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for _use_type
-- ----------------------------
DROP TABLE IF EXISTS `_use_type`;
CREATE TABLE `_use_type`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(21) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `delete_time` bigint(22) UNSIGNED DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for _user
-- ----------------------------
DROP TABLE IF EXISTS `_user`;
CREATE TABLE `_user`  (
  `id_user` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID：在系统内保持唯一数值',
  `type` tinyint(4) UNSIGNED NOT NULL DEFAULT 0 COMMENT '0 普通用户；1 部门领导；2 处室领导',
  `user_key_serialnum` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户 Key 的 字符串序列号：在系统内保持唯一性',
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户的姓名',
  `id_dept` int(45) NOT NULL COMMENT '部门ID',
  `sub_dept_id` int(11) NOT NULL COMMENT '子部门ID',
  `description` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述内容',
  `fingerid` int(11) NOT NULL COMMENT '用户在指纹机上录入的指纹的编号',
  PRIMARY KEY (`id_user`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 524 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for _user_cabinet
-- ----------------------------
DROP TABLE IF EXISTS `_user_cabinet`;
CREATE TABLE `_user_cabinet`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `cabinet_id` int(11) UNSIGNED NOT NULL,
  `cabinet_number` int(11) UNSIGNED NOT NULL,
  `lattice_id` int(11) UNSIGNED NOT NULL,
  `dept_id` int(11) UNSIGNED NOT NULL,
  `sub_dept_id` int(11) UNSIGNED NOT NULL,
  `user_id` int(11) UNSIGNED NOT NULL,
  `user_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `delete_time` bigint(22) UNSIGNED DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 533 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for _user_screen
-- ----------------------------
DROP TABLE IF EXISTS `_user_screen`;
CREATE TABLE `_user_screen`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(11) UNSIGNED NOT NULL,
  `title_text` varchar(101) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `title_font_name` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `title_font_size` int(11) UNSIGNED NOT NULL,
  `content_text` varchar(101) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `content_font_name` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `content_font_size` int(11) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Procedure structure for p1
-- ----------------------------
DROP PROCEDURE IF EXISTS `p1`;
delimiter ;;
CREATE DEFINER=`root`@`%` PROCEDURE `p1`()
BEGIN
	DECLARE idx int DEFAULT(1);
	DECLARE cid int DEFAULT(0);
	-- DECLARE rc int default(select count(*) from _carrier);
	DECLARE done int DEFAULT FALSE;
	DECLARE cur CURSOR FOR select carrier_id from _carrier;
	-- bind end flag to cursor
	DECLARE CONTINUE handler FOR NOT FOUND SET done = TRUE;
	-- open cursor
	OPEN cur;
		-- enumerate
		read_loop: LOOP
				FETCH NEXT from cur into cid;
				IF done THEN
					LEAVE read_loop; 
				END IF; 
				update _carrier set device_serialnum=CONCAT('XTS-BJB-',LPAD(idx,3,0)) where carrier_id = cid;
				-- select idx;
				set idx = idx +1;
		END LOOP;
	
	close cur;
	
	-- DEALLOCATE cur;
	
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for p2
-- ----------------------------
DROP PROCEDURE IF EXISTS `p2`;
delimiter ;;
CREATE DEFINER=`root`@`%` PROCEDURE `p2`()
BEGIN
	DECLARE m1 int DEFAULT(0);
	DECLARE m2  VARCHAR(50);
	
	DECLARE done int DEFAULT FALSE;
	DECLARE cur CURSOR FOR select _carrier_description.carrier_id, _carrier_description.test from _carrier_description, _carrier where _carrier.carrier_id = _carrier_description.carrier_id;
	
	DECLARE CONTINUE handler FOR NOT FOUND SET done = TRUE;
	-- open cursor
	OPEN cur;
		-- enumerate
		read_loop: LOOP
				FETCH NEXT from cur into m1, m2;
				IF done THEN
					LEAVE read_loop; 
				END IF; 
				
				-- select m1, m2;
				
				update _carrier set dest=m2 where carrier_id =m1;

		END LOOP;

	close cur;
	
	
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
