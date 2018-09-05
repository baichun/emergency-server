/*
Navicat MySQL Data Transfer

Source Server         : tsbc_70
Source Server Version : 50173
Source Host           : 192.168.107.70:3306
Source Database       : aos_bc_170110

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2017-02-13 20:06:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `aos_log_session`
-- ----------------------------
DROP TABLE IF EXISTS `aos_log_session`;
CREATE TABLE `aos_log_session` (
  `id_` varchar(64) NOT NULL COMMENT '会话ID',
  `user_id_` varchar(64) NOT NULL COMMENT '用户ID',
  `account_` varchar(255) NOT NULL COMMENT '登录账户',
  `user_name_` varchar(255) NOT NULL COMMENT '用户姓名',
  `ip_address_` varchar(255) DEFAULT NULL COMMENT '客户端IP地址',
  `client_type_` varchar(255) DEFAULT NULL COMMENT '客户端类型',
  `owner_` varchar(255) DEFAULT NULL COMMENT '宿主',
  `create_time_` datetime NOT NULL COMMENT '登录时间',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会话日志信息表';

-- ----------------------------
-- Records of aos_log_session
-- ----------------------------
INSERT INTO `aos_log_session` VALUES ('4DC36ED8C9197631B6023C4FCB106FB4', 'fa04db9dd2f54d61b0c8202a25de2dc6', 'root', '系统管理员', '192.168.107.50', 'Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36', 'null:9090', '2017-02-13 17:17:31');

-- ----------------------------
-- Table structure for `aos_sys_catalog`
-- ----------------------------
DROP TABLE IF EXISTS `aos_sys_catalog`;
CREATE TABLE `aos_sys_catalog` (
  `id_` varchar(64) NOT NULL COMMENT '流水号',
  `cascade_id_` varchar(255) NOT NULL COMMENT '节点语义ID',
  `root_key_` varchar(255) NOT NULL COMMENT '科目标识键',
  `root_name_` varchar(255) NOT NULL COMMENT '科目名称',
  `name_` varchar(255) NOT NULL COMMENT '分类名称',
  `hotkey_` varchar(255) DEFAULT NULL COMMENT '热键',
  `parent_id_` varchar(64) NOT NULL COMMENT '父节点流水号',
  `is_leaf_` varchar(255) NOT NULL DEFAULT '1' COMMENT '是否叶子节点',
  `is_auto_expand_` varchar(255) NOT NULL DEFAULT '0' COMMENT '是否自动展开',
  `icon_name_` varchar(255) DEFAULT NULL COMMENT '图标文件名称',
  `sort_no_` int(10) DEFAULT NULL COMMENT '排序号',
  PRIMARY KEY (`id_`),
  UNIQUE KEY `aos_sys_catalog_ukey` (`cascade_id_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分类表';

-- ----------------------------
-- Records of aos_sys_catalog
-- ----------------------------
INSERT INTO `aos_sys_catalog` VALUES ('01afa7f4bb4d4189a4817ade7b5c1b8d', '0.001.002.003', 'PARAM_TYPE', '参数分类科目', '其它', null, '4018f65de18043c899b5e21ce7328df7', '1', '0', 'icon150.png', '9');
INSERT INTO `aos_sys_catalog` VALUES ('21d03054afdf43d69972cf6f7db6cfa3', '0.002', 'DIC_TYPE', '词典分类科目', '数据字典分类', null, '0', '0', '0', 'book.png', '2');
INSERT INTO `aos_sys_catalog` VALUES ('4018a2217b0542059a26ecf3f605d60f', '0.002.001', 'DIC_TYPE', '词典分类科目', '系统管理', '', '21d03054afdf43d69972cf6f7db6cfa3', '1', '1', 'folder22.png', '3');
INSERT INTO `aos_sys_catalog` VALUES ('4018f65de18043c899b5e21ce7328df7', '0.001.002', 'PARAM_TYPE', '参数分类科目', '系统参数', null, 'cd14928a239b448b867c2e45246cd038', '0', '1', 'folder22.png', '1');
INSERT INTO `aos_sys_catalog` VALUES ('4ee1d6e48b31487b849a72cd03d2512a', '0.001.001', 'PARAM_TYPE', '参数分类科目', '业务参数', null, 'cd14928a239b448b867c2e45246cd038', '1', '0', 'user20.png', '2');
INSERT INTO `aos_sys_catalog` VALUES ('56814a2aa9324ee1ab7433fcce6258f6', '0.002.007', 'DIC_TYPE', '词典分类科目', '业务流程', '', '21d03054afdf43d69972cf6f7db6cfa3', '1', '1', 'folder23.png', '4');
INSERT INTO `aos_sys_catalog` VALUES ('6f94ef4ac17d4d098f91f0a4579f8591', '0.001.002.001', 'PARAM_TYPE', '参数分类科目', '验证码', null, '4018f65de18043c899b5e21ce7328df7', '1', '0', 'ok3.png', '2');
INSERT INTO `aos_sys_catalog` VALUES ('a072411787f545edb7e7c3c23fa6c0ff', '0.001.002.004', 'PARAM_TYPE', '参数分类科目', '导航与菜单', null, '4018f65de18043c899b5e21ce7328df7', '1', '0', 'icon152.png', '3');
INSERT INTO `aos_sys_catalog` VALUES ('aa3082ab67e24d8b884bc4504d3d9945', '0.002.002', 'DIC_TYPE', '词典分类科目', '全局通用', '', '21d03054afdf43d69972cf6f7db6cfa3', '1', '0', 'folder24.png', '2');
INSERT INTO `aos_sys_catalog` VALUES ('c0c43dea5ab6419dad37e459213d7bb5', '0.002.006', 'DIC_TYPE', '词典分类科目', '平台配置', null, '21d03054afdf43d69972cf6f7db6cfa3', '1', '0', 'folder2.png', '1');
INSERT INTO `aos_sys_catalog` VALUES ('cd14928a239b448b867c2e45246cd038', '0.001', 'PARAM_TYPE', '参数分类科目', '参数分类', null, '0', '0', '0', 'book.png', '1');
INSERT INTO `aos_sys_catalog` VALUES ('f22f39171b484d81a604d3eb50b33584', '0.001.002.002', 'PARAM_TYPE', '参数分类科目', '界面显示', null, '4018f65de18043c899b5e21ce7328df7', '1', '0', 'icon59.png', '1');

-- ----------------------------
-- Table structure for `aos_sys_dic`
-- ----------------------------
DROP TABLE IF EXISTS `aos_sys_dic`;
CREATE TABLE `aos_sys_dic` (
  `id_` varchar(64) NOT NULL COMMENT '流水号',
  `code_` varchar(255) NOT NULL COMMENT '字典对照码',
  `desc_` varchar(255) NOT NULL COMMENT '字典对照值',
  `hotkey_` varchar(255) DEFAULT NULL COMMENT ' 热键',
  `status_` varchar(255) NOT NULL DEFAULT '1' COMMENT '当前状态',
  `dic_index_id_` varchar(64) NOT NULL COMMENT '所属字典流水号',
  `remark_` varchar(4000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id_`),
  KEY `FK_3s644j5yuu0t6j30oiye0hwa5` (`dic_index_id_`),
  CONSTRAINT `FK_3s644j5yuu0t6j30oiye0hwa5` FOREIGN KEY (`dic_index_id_`) REFERENCES `aos_sys_dic_index` (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典明细表';

-- ----------------------------
-- Records of aos_sys_dic
-- ----------------------------
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('03bb3e7e665e4f84894f5471e9fda6cc', '15', '测试事件', NULL, '1', '338a60b8da9348e587817a127f070293', '测试事件');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('040e1d4d8a914145884553f170e1bcfd', '1', '中控', NULL, '1', 'cfcbc48fd8ad42678a52334bac8aa299', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('040fbde5cca746e7a90a9f888c033897', '4', '只读', NULL, '1', '2794338c8b4946b99c361dae80da71b1', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('074c63e541174ed0b7c71588936ae453', '1', '平铺', NULL, '1', 'ffc94f2b7ad14c78b14c46f8fab17543', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('075a268cd7e14a728ca841e769b9d5a6', '1', '缺省', NULL, '1', '79004d064e954e55b832a8a9a4332576', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('0825b66058c14bc5bfacdba1fd055af4', '2', '浮动菜单', NULL, '1', 'a9fd5aa3b7654326902a94e70bccedd8', '欢迎页显示的Mac风格的浮动菜单');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('0c22b79ca3ed4360b7b7479292eeb3ba', '3', '预警处理', NULL, '1', '81eff0bb4bfb43aea201ea3c77af0b8c', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('0ccc4a6e3df246d8bbd3ccb1d29eb4c4', '0', '停用', NULL, '1', '10a113b1dd6d492dad27c8b0a0fb7efc', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('0f22876d27354e848d02c129e295b5cc', '1', '请求播发', NULL, '1', 'f68bc10cafd745c0af9c8a1bfa896ea2', '请求播发');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('0f81f7042b77414e8742e6397bc000dc', '6', '星期六', NULL, '1', '4bf11442379e4feabdb8392259a175ab', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('14c0c8c3a6ba4e9784d54e7d709f9c53', '2', '成功', NULL, '1', 'df6f7d0efe41420e952251b99f9f14f7', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('15a4be61546147298d0ef38b2d096841', '0302', '有线数字电视', NULL, '1', '27d6f817c9534488a8a9f4f555bb4624', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('15f728ef5143444f9bfafa1c5c8a6a3b', '3', '隐藏', NULL, '1', '2794338c8b4946b99c361dae80da71b1', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('1690f621c5ce4ce8b9d10a8038fa7903', '02', '采集终端', NULL, '1', 'd6fe9d54e6114d6683c08c09857ad6f6', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('1722aa532c4044ac8087579feac85f7f', 'neptune', 'neptune.png', NULL, '1', 'e9e9cfc195514b05890a2249b81dfe73', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('1776f65145544534b483655781d59868', '0', '未审核', NULL, '1', '77e7fce05577431c993fcc4139beb284', '未审核');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('17b249a462e7409baa99f36057fa79e7', '1', '是', NULL, '1', '91bc8a695b744b27ba66a94561fd6dbc', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('1915d507f36d4ae688267ebca838d39f', 'blue', '#3399FF', NULL, '1', '61fd319a23b04bd1bdbce7037002976a', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('1971e6c11ed74085aaa6816ce901d147', '4', '预警完成', NULL, '1', '81eff0bb4bfb43aea201ea3c77af0b8c', '预警完成');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('1bb70ce5d6e649fd9179f3a3d35367f9', 'blue', 'left-logo.png', NULL, '1', 'fa8097dd670643f9a03722c4074ecba5', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('1c7c9275a54a46bdaeb6f8edd13c2b67', '1', 'Ⅰ级事件', NULL, '1', '338a60b8da9348e587817a127f070293', 'Ⅰ级事件');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('1eb28d3c22d647e0b406b902143e8b32', '22', '模拟演练', NULL, '1', '338a60b8da9348e587817a127f070293', '系统模拟演练');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('1fb134407f364baf8b857235c7828059', '2', '停用', NULL, '1', '0744eedeaaa143afa499254866aa75c9', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('1fc3d6b67ce04462a0d4bc7277d4ad29', '2', '发送', NULL, '1', 'f0e43eb73cf34af4a7f9b4ec0dc3d009', '发送');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('2180244734f947e6a79c7c1c517b8126', '0309', '卫星电视', NULL, '1', '9837cb554eb74e2ea46283d0f266f8b6', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('226489afb7ab4cf69107a78c60ed2267', 'PENDING', '代理中', NULL, '1', 'c66f06c069224241a4785e77d1406f9c', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('22dafa8e8bbd4ae8bc823ddf1e306afa', '0304', '有线模拟电视', NULL, '1', '27d6f817c9534488a8a9f4f555bb4624', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('23910ad1f9684644b214ff99209e94d4', '3', '关联页面', NULL, '0', '38841cd4f85f4ffb8c07c74650a130dc', '主页面上跳转出去的页面');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('24bca6d79bb14b5f97fbddbe5219705b', '1', '按钮|菜单下拉项', NULL, '1', '7d048854ccdc4eab996dcb703722065b', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('260733ec6a0f4901b232308fed503c23', '0306', '中波广播', NULL, '1', '9837cb554eb74e2ea46283d0f266f8b6', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('263686e53bf34b549168c5e66f135c0c', '2', '制播', NULL, '1', 'cfcbc48fd8ad42678a52334bac8aa299', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('28ff4f950a39446bbfc3c633b1c7df44', '0201', '广播电视台', NULL, '1', 'e9a3d79f257b4230a8af39b14c86a781', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('29462076b88f4facb86f7647c04119d0', '2', '子页面', NULL, '1', '38841cd4f85f4ffb8c07c74650a130dc', '在主页面上的子导航打开的页面(内嵌式的)');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('2b8c7b9cf9844eb7adf484750d1a0926', 'fre', '法语', NULL, '1', 'fc5496ad028849339e7f2c25914f3535', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('2bd5fc7756d74fe59f5b9372fd83d03c', '4', '分割符', NULL, '1', '38841cd4f85f4ffb8c07c74650a130dc', '子页面生成的二级导航菜单的分割符标识');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('2c6cb513c12a4506aef54ef1a47df8aa', '1', '审核通过', NULL, '1', '77e7fce05577431c993fcc4139beb284', '审核通过');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('2ce972620b1d484e880ae17113f4cf05', 'tight', '组合按钮', NULL, '1', 'a19ac2af964c4a6e8af9c6f1c0d2673f', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('2d67dfa3a4a14a009c742cef2e8cd169', '3', '星期三', NULL, '1', '4bf11442379e4feabdb8392259a175ab', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('2e2ef7ebcd5449a1a1b593f63ddb17ba', '3', '管理员封锁', NULL, '1', '0744eedeaaa143afa499254866aa75c9', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('2ef4d36610334dc6bc9bd153be594da2', '3', 'DBSequence', NULL, '1', '2a9ac1749d224bef924cd258bf35254a', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('2f9994a32b3e4afd96420c180155065a', '3', '矢量图标', NULL, '1', '21136c3dc660464d92fccd66408e149b', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('2fe1fb52ca984a98be82c9aca216ef58', '3', 'Ⅲ级事件', NULL, '1', '338a60b8da9348e587817a127f070293', 'Ⅲ级事件');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('303024106f4c45ea8de806a615923796', '01', '应急广播平台', NULL, '1', '93ca157eaac3434ba1fbeca047d2d92f', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('3058d4cd6c514d23a27eee77b140a349', 'aos', 'left-logo.png', NULL, '1', 'fa8097dd670643f9a03722c4074ecba5', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('314e6f3501664c0787fdfd8a2f05bb9a', '0303', '地面数字电视', NULL, '1', '04bb8141aef049c9a8c8e64faca0a0a1', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('36efe0785e24415e86d5513f9b016a8c', '3', '故障', NULL, '1', 'e8312d9272bd4db8accd8db793bc58e9', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('37d2d28ac8104f6481997b640c5d4fd0', '4', '其它', NULL, '1', '58b28eade8604dffa7c531b7b42af2fa', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('3a4466b864744a0da3b93eec3f24dfff', 'blue', '天蓝', NULL, '1', '3ba3a682c2ac4d52982411dd045fae32', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('3c56f07c48b746698591068c308064a7', 'gray', '银灰', NULL, '1', '3ba3a682c2ac4d52982411dd045fae32', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('3ccd2abe3df14a469bf48f46e2862510', '25', '方案提交', NULL, '1', '3405701c6f8245168d4b9fdfc7a40d07', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('3df9ed514f1a4dc8b33048d49ea24d69', 'eng', '英文', NULL, '1', 'fc5496ad028849339e7f2c25914f3535', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('3efdcab72fc04033b3a5e203df00bb43', '3', '市', NULL, '1', '32168ca5857c4236ad6ac884f5e05853', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('3f1b50a0f5be46a69597fca95c1e82d7', '2', '大图标[64X64]', NULL, '1', '21136c3dc660464d92fccd66408e149b', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('3fe8b48ab1ed4ed29adc9e43bd3f1a39', '0311', '数字音频广播', NULL, '1', '9837cb554eb74e2ea46283d0f266f8b6', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('4113d10127a34b4fa88b2d6b118accf0', '0', '否', NULL, '1', '91bc8a695b744b27ba66a94561fd6dbc', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('431ff959e3a04dcbb26b2f4064416ee9', '1', '中控管理系统', NULL, '1', '4707ef6d229c49808be8df98658bf2ae', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('466346b19a7644ccaad02f8510014b1b', '1', '是', NULL, '1', '811791160f3348c6b4d0cbc96aeb5630', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('480d752b8c0b43c9adb5bb50a5c50db2', '6', '年度报', NULL, '1', '5c58a404a396435e88047e21c991f0ef', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('48a913aac02045819a6e967d8199331a', '4', 'Ⅳ级事件', NULL, '1', '338a60b8da9348e587817a127f070293', 'Ⅳ级事件');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('4ae7b6f7d54a4236ba9b18a9e05d7fb4', '4', '容器组件(窗口|面板|树|表格|工具栏等)', NULL, '1', '7d048854ccdc4eab996dcb703722065b', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('4af1d58e374149f38c5ae9f76674a79f', 'aos', '#FAFAFA', NULL, '1', '61fd319a23b04bd1bdbce7037002976a', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('4c5816e8db1c4f0995fbeb7a1b840685', '1', '主页面', NULL, '1', '38841cd4f85f4ffb8c07c74650a130dc', '点击主导航菜单打开的页面');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('4d858593c00a4cbcb1703f9e9ebdbd50', '01', '监管平台', NULL, '1', 'd6fe9d54e6114d6683c08c09857ad6f6', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('4fc24ecc03494ca4b0340ce60b5f3602', '0313', 'IPTV', NULL, '1', '27d6f817c9534488a8a9f4f555bb4624', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('518c7f52052740779e1cb145b4eca18b', '0312', '机动应急广播', NULL, '1', '9837cb554eb74e2ea46283d0f266f8b6', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('51994b6c434a4120822a9fc4e14470df', '21', '方案生成', NULL, '1', '3405701c6f8245168d4b9fdfc7a40d07', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('52963aebd4c9459fb02969a067bee19f', '0303', '地面数字电视', NULL, '1', '9837cb554eb74e2ea46283d0f266f8b6', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('569b3bab75734c628a9bb0b3fbcd8922', '2', '显示', NULL, '1', '2794338c8b4946b99c361dae80da71b1', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('56e5bf20859742029cd479f50cd57183', '0304', '有线模拟电视', NULL, '1', '9837cb554eb74e2ea46283d0f266f8b6', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('56fedce975e54020aba5c27dec8539cc', '5', '乡镇', NULL, '1', '32168ca5857c4236ad6ac884f5e05853', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('57de22992dac4d278cb307899b2e6ee6', '3', '月报', NULL, '1', '5c58a404a396435e88047e21c991f0ef', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('58195ff7a3084df2a2dd5b401aa29a77', '0', '系统导航', NULL, '1', '4c2a12d3890a449fa91521a29a3c2436', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('5a53887315f7450e96816c1a46dc5b5e', '2', '技术部门', NULL, '1', '58b28eade8604dffa7c531b7b42af2fa', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('5be37f4d697b49bdb990a05626244083', '0305', '地面模拟电视', NULL, '1', '9837cb554eb74e2ea46283d0f266f8b6', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('5c4196e15f47466ea19137666e455504', 'neptune', 'left-logo.png', NULL, '1', 'fa8097dd670643f9a03722c4074ecba5', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('5dd1c5a1610544b490af86cc826e3663', '2', '表格列', NULL, '1', '7d048854ccdc4eab996dcb703722065b', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('5de14be720b546ebb9676db86cdc5f6c', '2', '女', NULL, '1', 'f59d8693d8cf4f10850914e09ae9b93c', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('5dfc1a71df524cd68311a95e7176cab7', 'gray', '#BCB0B0', NULL, '1', '1ec5d3c945de40469595c85955196e76', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('5e5072fe358a422ea629ec4170727c08', '0203', '电视台', NULL, '1', 'e9a3d79f257b4230a8af39b14c86a781', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('602ccbad67c845cab9d4660fa8aa1d6c', '0304', '有线模拟电视', NULL, '1', '04bb8141aef049c9a8c8e64faca0a0a1', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('6543eb6308244de5addc157c1527a054', '0313', 'IPTV', NULL, '1', '9837cb554eb74e2ea46283d0f266f8b6', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('6674a99ac3a4421cbd09451428a294f4', '1', '创建', NULL, '1', 'df6f7d0efe41420e952251b99f9f14f7', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('66cd8527098e48208cca3b7667054957', '0310', '移动多媒体广播电视', NULL, '1', '9837cb554eb74e2ea46283d0f266f8b6', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('6713ffce7f2e4338be676235ed3fc8d6', '2', '制播管理系统', NULL, '1', '4707ef6d229c49808be8df98658bf2ae', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('6a05b8c8ce244c3f9b4872f812c689ec', '1', '经办权限', NULL, '0', 'dd9f74d16be14db5b0f6d79720c8f177', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('6ac835b7d5704b89ba3dec455bc77a47', '3', '调度', NULL, '1', '1e6aaee85c38476b9b223317f683fd66', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('6b61de340dea4f878513d8811cb57622', '0101', '通用平台', NULL, '1', '951febb8407e42d4b6a3b23a0941db1d', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('6c485c9928234845a3ebd92ce4052f78', '1', '开机运行正常', NULL, '1', 'e8312d9272bd4db8accd8db793bc58e9', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('6c575847953847ceb7d4675e94d78547', 'aos', '#99bce8', NULL, '1', '1ec5d3c945de40469595c85955196e76', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('6ddb820f8c224829b336459d97c56ac4', 'text', '3', NULL, '1', '1c4686285f4a42ed8083c0e3bd858984', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('6e018ced3ae046de93d74630a97361a1', 'aos', '#006699', NULL, '0', '54e48c66b3ef4d99823ca8217a2abf43', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('6ee1e045566d4d62902f79edd913856c', '23', '方案调整', NULL, '1', '3405701c6f8245168d4b9fdfc7a40d07', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('718937cd693641219370c5082d96abac', '22', '方案优化', NULL, '1', '3405701c6f8245168d4b9fdfc7a40d07', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('72aa0f4407194fd4bf30c5e1a9ff7245', '0307', '短波广播', NULL, '1', '27d6f817c9534488a8a9f4f555bb4624', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('72c322da7c5c43709d3bddda617c5fc0', '0305', '地面模拟电视', NULL, '1', '27d6f817c9534488a8a9f4f555bb4624', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('735edf2e8a1242078141405d344d3c84', '1', '未授权', NULL, '1', '2794338c8b4946b99c361dae80da71b1', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('74ed6c96a0cf43d8a544bf5953492b87', '2', '已同步', NULL, '1', 'd9ba4077623c4fe1aeb643ccf877aea6', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('754a84e438744f60ab0d47f50d697a5d', '2', '关机停止运行', NULL, '1', 'e8312d9272bd4db8accd8db793bc58e9', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('766bda93b7864b679557d22c406ad297', '26', '方案审核通过', NULL, '1', '3405701c6f8245168d4b9fdfc7a40d07', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('786d09295c904608868807b9e08238ee', 'neptune', '#c0c0c0', NULL, '1', '1ec5d3c945de40469595c85955196e76', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('7b6226ba4b6b404bab3cc520d5c1e6b5', 'neptune', '海王星(水蓝)', NULL, '1', '3ba3a682c2ac4d52982411dd045fae32', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('7d1869d323f644749ca07ca59a74e954', '0309', '卫星电视', NULL, '1', '27d6f817c9534488a8a9f4f555bb4624', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('7d5dc44a823a419cb17632cb90afcd86', '23', '实际演练', NULL, '1', '338a60b8da9348e587817a127f070293', '全系统实际演练');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('7f6c29f70cbf4b0db35dea19d2d30084', '0301', '调频广播', NULL, '1', '9837cb554eb74e2ea46283d0f266f8b6', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('84552cfdf36f4fee92d81a49340495ca', 'blue', '#DFE8F6', NULL, '1', '3146aefcb39344cebe6e0a9baa7a4a48', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('84cdfdec00064929b47d1a27de546861', '41', '预警完成', NULL, '1', '3405701c6f8245168d4b9fdfc7a40d07', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('84ee7e35ab134ec1acd2cfd28d3915f6', 'standalone', '独立按钮', NULL, '1', 'a19ac2af964c4a6e8af9c6f1c0d2673f', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('85940815cb37477bad41a9d468d9530b', '1', '试验', NULL, '1', '20374dcc52df45a4b945d88a9e3f67bd', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('85f5017c1860434eacfceb519b8ad8cd', 'blue', 'blue.png', NULL, '1', 'e9e9cfc195514b05890a2249b81dfe73', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('8644bb8cc0144cc9972e73286a68f1fb', '2', '省', NULL, '1', '32168ca5857c4236ad6ac884f5e05853', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('867c0aacba654feea60c22c5e4be9368', 'aos', 'red', NULL, '1', '3146aefcb39344cebe6e0a9baa7a4a48', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('871d7ac8ec704614a15d29f540b8b439', 'true', '显示', NULL, '1', '33a73af4b729448b91c13acf4d0a31e1', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('87923302cb424da9aa55b6a9ce77090b', '7', '激活', NULL, '1', '2794338c8b4946b99c361dae80da71b1', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('89d12bf48e2144fb83103c7056149fa7', '4', '故障恢复', NULL, '1', 'e8312d9272bd4db8accd8db793bc58e9', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('89f8ab9369a64de09a8207829f520297', 'gray', 'gray.png', NULL, '1', 'e9e9cfc195514b05890a2249b81dfe73', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('8a73d45c653e47d9bf6a5a17d85ae3bd', '0104', '效果监测评估系统', NULL, '1', '951febb8407e42d4b6a3b23a0941db1d', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('8b536d0c9a2e4e80b1edf91190b98253', '5', '编辑', NULL, '1', '2794338c8b4946b99c361dae80da71b1', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('8bb8c7e9ce69412c88f04f7a42de10c6', '1', '预警触发', NULL, '1', '81eff0bb4bfb43aea201ea3c77af0b8c', '预警触发');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('8bce3d681ef64843a04bd280b7c28bef', '0102', '制作播发系统', NULL, '1', '951febb8407e42d4b6a3b23a0941db1d', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('8d5e6e8d349241fe89df0a8cd855eb19', '1', '日报', NULL, '1', '5c58a404a396435e88047e21c991f0ef', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('8da661446e6d4e9cb49d2eb5ab225cd0', '2', '树状', NULL, '0', 'ffc94f2b7ad14c78b14c46f8fab17543', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('8e42e7df7bd044b89c7a5d5b59b44b18', '1', '男', NULL, '1', 'f59d8693d8cf4f10850914e09ae9b93c', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('8e9f8e5c645e459986f4696f9b509d31', '13', '审核通过', NULL, '1', '3405701c6f8245168d4b9fdfc7a40d07', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('92dfd82860a0474da735575e9b91f6de', 'classic', '经典主题', NULL, '1', 'a44dd9bbf55041368882e88821309355', '上Banner-左导航-中经办。');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('958235d21e2f4d44babd8ae7da7dd7f8', '99', '撤消授权', NULL, '1', '2794338c8b4946b99c361dae80da71b1', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('99419e3ea3d64507aa507854fd210a75', '3', '表单元素(输入框|下拉框|选择框等)', NULL, '1', '7d048854ccdc4eab996dcb703722065b', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('9a9595fcf0e742bc9ab418270db48390', '0314', '大喇叭系统终端', NULL, '1', 'a43ef5d2f87c4b29bef0816799656b29', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('9b0296df11424156a59164f652a97f64', '0305', '地面模拟电视', NULL, '1', '04bb8141aef049c9a8c8e64faca0a0a1', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('9bde789667544b24ade31a69d8a12dfc', '3', '调度控制系统', NULL, '1', '4707ef6d229c49808be8df98658bf2ae', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('9d1c5da19d0b4ad9b97b570ed4481bf2', 'false', '隐藏', NULL, '1', '33a73af4b729448b91c13acf4d0a31e1', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('9db937d7ceb048e3983d28408a796925', '4', '县', NULL, '1', '32168ca5857c4236ad6ac884f5e05853', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('9eb0145e02ed42f8bd399a36e81ee93d', '4', '星期四', NULL, '1', '4bf11442379e4feabdb8392259a175ab', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('9eb462a5fd0547b9898540b6e5a4ca98', 'gray', 'left-logo.png', NULL, '1', 'fa8097dd670643f9a03722c4074ecba5', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('a11e07b67c3f48b5a36d028e19b08ac1', '2', '周报', NULL, '1', '5c58a404a396435e88047e21c991f0ef', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('a21c2b9e1674486282aeef6b1a385252', '14', '审核不通过', NULL, '1', '3405701c6f8245168d4b9fdfc7a40d07', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('a389bf7b8b3e44a199d2f2a505d02422', '1', '接收', NULL, '1', 'f0e43eb73cf34af4a7f9b4ec0dc3d009', '接收');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('a49eca4fbb2c4c2097865cd57b49ef71', '21', '平台演练', NULL, '1', '338a60b8da9348e587817a127f070293', '平台应急演练');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('a603159d47b94145a32b51f659b47c09', '4', '系统封锁', NULL, '1', '0744eedeaaa143afa499254866aa75c9', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('a6488704e8734e968685b35995488090', '2', '取消播发', NULL, '1', 'f68bc10cafd745c0af9c8a1bfa896ea2', '取消播发');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('a7e57b3f079842939016374c7786d504', '0307', '短波广播', NULL, '1', '9837cb554eb74e2ea46283d0f266f8b6', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('a97934b734a94d70a71353bc78e572df', 'neptune', '#FAFAFA', NULL, '1', '61fd319a23b04bd1bdbce7037002976a', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('a9bef6122ad7444999ce17b4ad5a0628', '2', '发现', NULL, '0', '4c2a12d3890a449fa91521a29a3c2436', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('ab0d81387ab1462882717dca80b08103', '32', '预警发布', NULL, '1', '3405701c6f8245168d4b9fdfc7a40d07', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('ac75e2d6bcc04a08aa35cbb4a5af8fa7', 'zho', '中文', NULL, '1', 'fc5496ad028849339e7f2c25914f3535', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('ac8049fc0ded42b09a3654ef547ac772', '1', '小图标[16X16]', NULL, '1', '21136c3dc660464d92fccd66408e149b', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('b18435d1752a4a8c96628402298c0493', 'gray', '#555555', NULL, '1', '61fd319a23b04bd1bdbce7037002976a', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('b259adcf5d9f4ae6ab8a2f785984dfa7', '0312', '机动应急广播', NULL, '1', '27d6f817c9534488a8a9f4f555bb4624', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('b2d1ce58683e4ff4a8d60d924c4d3335', '2', '预警响应', NULL, '1', '81eff0bb4bfb43aea201ea3c77af0b8c', '预警响应');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('b38c4336480044d1a40b73488aef2bf8', '0302', '有线数字电视', NULL, '1', '04bb8141aef049c9a8c8e64faca0a0a1', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('b5027dc427584f329d9ebc4592f3e79f', '12', '提交', NULL, '1', '3405701c6f8245168d4b9fdfc7a40d07', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('b6a703bba79f4831b8968fda9f982ff7', '0314', '应急广播大喇叭系统', NULL, '1', '27d6f817c9534488a8a9f4f555bb4624', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('b704414a4d294760928b6857b7c85ca4', '0202', '电台', NULL, '1', 'e9a3d79f257b4230a8af39b14c86a781', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('b8fd3743a9d74cbd8bee7c4533739abd', '24', '方案制作', NULL, '1', '3405701c6f8245168d4b9fdfc7a40d07', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('ba5b0493f0544278a980e0f268baf83c', '1', '快捷菜单', NULL, '1', '4c2a12d3890a449fa91521a29a3c2436', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('bbef93ea807847029e98ed7023b47c6a', '1', '正常', NULL, '1', '0744eedeaaa143afa499254866aa75c9', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('bc17f4f03f0d415c97a026e8ec5a999d', 'blue', '#6699CC', NULL, '1', '54e48c66b3ef4d99823ca8217a2abf43', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('bd271c14c54e4590b599a6e0627163cc', '0308', '直播卫星', NULL, '1', '27d6f817c9534488a8a9f4f555bb4624', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('bd30f1afd1474eb2a1eee577a9067f6a', 'rus', '俄语', NULL, '1', 'fc5496ad028849339e7f2c25914f3535', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('c091d9a7f1cd47e8b8bb696b1a49a414', '3', '失败', NULL, '1', 'df6f7d0efe41420e952251b99f9f14f7', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('c172686bc21e40efacca63c5b7e1c9d3', 'blue', '#99bce8', NULL, '1', '1ec5d3c945de40469595c85955196e76', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('c1c92ade06b94969b2b066184845781c', '6', '禁用', NULL, '1', '2794338c8b4946b99c361dae80da71b1', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('c20e301598954f0ab33445680ca1db27', 'gray', '#F0F0F0', NULL, '1', '3146aefcb39344cebe6e0a9baa7a4a48', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('c3e008a5ba864e21b5a66230e389926c', '1', '行政部门', NULL, '1', '58b28eade8604dffa7c531b7b42af2fa', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('c422649d172740a6b931b449ab5d1a1a', '0308', '直播卫星', NULL, '1', '9837cb554eb74e2ea46283d0f266f8b6', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('c5409280633b47828e41c89aac14a9ca', '31', '预警发送', NULL, '1', '3405701c6f8245168d4b9fdfc7a40d07', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('c85b485719c2431c801a755aac816376', '0103', '调度控制系统', NULL, '1', '951febb8407e42d4b6a3b23a0941db1d', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('c8db519f21dc497ba178a557cf4e2368', 'video', '4', NULL, '1', '1c4686285f4a42ed8083c0e3bd858984', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('cc8d6e844c3c46e0bfc973ef51dcb4a3', 'neptune', '#D0DDF2', NULL, '1', '3146aefcb39344cebe6e0a9baa7a4a48', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('cde729266d09465ca624939dc05e6b4d', '2', 'UUID', NULL, '1', '2a9ac1749d224bef924cd258bf35254a', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('ce49529f302f446d8e6e4576578ad459', '3', '注册用户', NULL, '1', '0475e71c145f49acbe781b2e7310ab64', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('d017ce97266c40db8f11509bd27aa6c5', '0301', '调频广播', NULL, '1', '27d6f817c9534488a8a9f4f555bb4624', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('d34c144805514d8eac6fa2f73396e0ca', '05', '播出系统', NULL, '1', '93ca157eaac3434ba1fbeca047d2d92f', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('d3fc826856b240799c35ab5f4aefb362', '4', '季度报', NULL, '1', '5c58a404a396435e88047e21c991f0ef', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('d57dd2373fe040668629e97e6d5faa55', '0310', '移动多媒体广播电视', NULL, '1', '27d6f817c9534488a8a9f4f555bb4624', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('d61968e9f4c448cd87a8fb95dc3e9c80', '2', 'Ⅱ级事件', NULL, '1', '338a60b8da9348e587817a127f070293', 'Ⅱ级事件');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('d61d1a8175a049bb8572be3f737ef5c5', '2', '审核不通过', NULL, '1', '77e7fce05577431c993fcc4139beb284', '审核不通过');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('d86b2633aa4c4323beaf2b3b0360a5e4', '0307', '短波广播', NULL, '1', '04bb8141aef049c9a8c8e64faca0a0a1', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('d87786de378442f8b07a950171c6f6ea', '27', '方案审核不通过', NULL, '1', '3405701c6f8245168d4b9fdfc7a40d07', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('d93b044fec9941cd87ec8260fab2c9b9', 'gray', '#888888', NULL, '1', '54e48c66b3ef4d99823ca8217a2abf43', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('d964422544514cb19bffd4e1e7879d28', '0303', '地面数字电视', NULL, '1', '27d6f817c9534488a8a9f4f555bb4624', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('da65be57d25d49faaa2c144f1584cd93', '0311', '数字音频广播', NULL, '1', '27d6f817c9534488a8a9f4f555bb4624', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('da82c6fd28e24cb58e5a004733e3d01f', '1', '中控', NULL, '1', '1e6aaee85c38476b9b223317f683fd66', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('dc1965d5b3ab4db48ee60a21ca36e9a1', '11', '创建', NULL, '1', '3405701c6f8245168d4b9fdfc7a40d07', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('dc931ac944a74b19b92b80169ebc7f6a', '0306', '中波广播', NULL, '1', '27d6f817c9534488a8a9f4f555bb4624', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('dd313f6d6378475ebd66298acaa7b43a', 'audio', '1', NULL, '1', '1c4686285f4a42ed8083c0e3bd858984', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('dd4cb19a47a1447392d23192c7520b3d', '2', '覆盖', NULL, '1', '20374dcc52df45a4b945d88a9e3f67bd', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('dd667c87794446c3b7e356fbbc9f2571', 'record', '2', NULL, '1', '1c4686285f4a42ed8083c0e3bd858984', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('e4e8672d55574b928ae0efdb2ba52358', '0302', '有线数字电视', NULL, '1', '9837cb554eb74e2ea46283d0f266f8b6', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('e81beb66246441228ba1bb17190db222', '2', '管理权限', NULL, '1', 'dd9f74d16be14db5b0f6d79720c8f177', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('e96de9e90616433c999a452e87b45118', '0308', '直播卫星', NULL, '1', '04bb8141aef049c9a8c8e64faca0a0a1', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('eaffa2091df242258c0ed6a420b0210b', '4', '取消', NULL, '1', 'df6f7d0efe41420e952251b99f9f14f7', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('ebe726f2cfa2434dbb7f943f6f42c3ad', '2', '超级用户', NULL, '1', '0475e71c145f49acbe781b2e7310ab64', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('ec5af73484cc43ea80823386ae967c32', '6', '村', NULL, '1', '32168ca5857c4236ad6ac884f5e05853', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('ec9bd8bcf01a49cdb1f791cf26e34f8b', '2', '制播', NULL, '1', '1e6aaee85c38476b9b223317f683fd66', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('ed7ddfdc9f1e4f7687bce204e1ddb79b', '1', '未同步', NULL, '1', 'd9ba4077623c4fe1aeb643ccf877aea6', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('edb40bce176b4fb28f3111c5552484bc', '00', '所有子类型', NULL, '1', 'd6fe9d54e6114d6683c08c09857ad6f6', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('efd8ac434bcd4b1da354b246bc820637', '0301', '调频广播', NULL, '1', '04bb8141aef049c9a8c8e64faca0a0a1', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('efe3e1c84a3c4d6ca6fd5001b6032fc9', '3', '调度', NULL, '1', 'cfcbc48fd8ad42678a52334bac8aa299', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('f08565c1fd7444bcb24645e52335a1e1', '1', '快捷菜单', NULL, '1', 'a9fd5aa3b7654326902a94e70bccedd8', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('f1f754ae30d54e2895c32050ff4c1d3d', 'aos', 'aos.png', NULL, '1', 'e9e9cfc195514b05890a2249b81dfe73', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('f22b386f40cd4c33841e212ab921be52', '5', '星期五', NULL, '1', '4bf11442379e4feabdb8392259a175ab', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('f3e22b388d244169a67d1a8077853c74', '10', '日常事件', NULL, '1', '338a60b8da9348e587817a127f070293', '日常事件');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('f403b42551db47d9b9483199214802f3', '5', '半年报', NULL, '1', '5c58a404a396435e88047e21c991f0ef', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('f4108659f1f8461db381c2808b82e038', '1', '启用', NULL, '1', '10a113b1dd6d492dad27c8b0a0fb7efc', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('f4836a7d2d3e4ae89a34932bc4c01ef9', 'kor', '朝鲜语', NULL, '1', 'fc5496ad028849339e7f2c25914f3535', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('f624177f00ba4f15a60a8188856b5425', '1', 'APPID', NULL, '1', '2a9ac1749d224bef924cd258bf35254a', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('f6ce6e9628de40daa2c4b9fab26518f5', '0306', '中波广播', NULL, '1', '04bb8141aef049c9a8c8e64faca0a0a1', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('f7b6f63fc5de469aaf90986188037516', 'neptune', '#006699', NULL, '1', '54e48c66b3ef4d99823ca8217a2abf43', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('f7eb274feaf848b09a308a554d7f037d', '7', '星期日', NULL, '1', '4bf11442379e4feabdb8392259a175ab', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('f827be13833849e080f89b333c9e7c2a', '2', '否', NULL, '1', '811791160f3348c6b4d0cbc96aeb5630', '');
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('f97e6ea7f6fe4f6e8b7fde75f0074e2c', '1', '缺省', NULL, '1', '0475e71c145f49acbe781b2e7310ab64', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('fde277e457e345038b66162a934a0b38', '1', '星期一', NULL, '1', '4bf11442379e4feabdb8392259a175ab', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('ffa4599963064f268f8155f85402b4be', '2', '星期二', NULL, '1', '4bf11442379e4feabdb8392259a175ab', NULL);
INSERT INTO `aos_sys_dic` (`id_`, `code_`, `desc_`, `hotkey_`, `status_`, `dic_index_id_`, `remark_`) VALUES ('fffe9eeaac6a465a9b4e1d6ac96efbd1', 'deu', '德语', NULL, '1', 'fc5496ad028849339e7f2c25914f3535', NULL);



-- ----------------------------
-- Table structure for `aos_sys_dic_index`
-- ----------------------------
DROP TABLE IF EXISTS `aos_sys_dic_index`;
CREATE TABLE `aos_sys_dic_index` (
  `id_` varchar(64) NOT NULL COMMENT '流水号',
  `key_` varchar(255) NOT NULL COMMENT '字典标识',
  `name_` varchar(255) NOT NULL COMMENT '字典名称',
  `hotkey_` varchar(255) DEFAULT NULL COMMENT '热键',
  `catalog_id_` varchar(64) NOT NULL COMMENT '所属分类流水号',
  `catalog_cascade_id_` varchar(255) NOT NULL COMMENT '所属分类流节点语义ID',
  `remark_` varchar(4000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id_`),
  UNIQUE KEY `aos_sys_dic_index_ukey` (`key_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典索引表';

-- ----------------------------
-- Records of aos_sys_dic_index
-- ----------------------------
INSERT INTO `aos_sys_dic_index` VALUES ('0475e71c145f49acbe781b2e7310ab64', 'user_type_', '用户类型', null, '4018a2217b0542059a26ecf3f605d60f', '0.002.001', null);
INSERT INTO `aos_sys_dic_index` VALUES ('04bb8141aef049c9a8c8e64faca0a0a1', 'adapter_type', '消息接收设备类型', null, '56814a2aa9324ee1ab7433fcce6258f6', '0.002.007', '消息接收设备类型');
INSERT INTO `aos_sys_dic_index` VALUES ('0744eedeaaa143afa499254866aa75c9', 'user_status_', '用户状态', null, '4018a2217b0542059a26ecf3f605d60f', '0.002.001', null);
INSERT INTO `aos_sys_dic_index` VALUES ('10a113b1dd6d492dad27c8b0a0fb7efc', 'enabled_', '使能状态', null, 'aa3082ab67e24d8b884bc4504d3d9945', '0.002.002', '');
INSERT INTO `aos_sys_dic_index` VALUES ('1c4686285f4a42ed8083c0e3bd858984', 'file_type_', '媒资文件类型', null, '56814a2aa9324ee1ab7433fcce6258f6', '0.002.007', null);
INSERT INTO `aos_sys_dic_index` VALUES ('1e6aaee85c38476b9b223317f683fd66', 'portal_type_', '客户端类型', null, 'c0c43dea5ab6419dad37e459213d7bb5', '0.002.006', null);
INSERT INTO `aos_sys_dic_index` VALUES ('1ec5d3c945de40469595c85955196e76', 'cmp_border_color_', '组件边框颜色', null, 'c0c43dea5ab6419dad37e459213d7bb5', '0.002.006', null);
INSERT INTO `aos_sys_dic_index` VALUES ('20374dcc52df45a4b945d88a9e3f67bd', 'experiment', '试验/覆盖标志', null, '56814a2aa9324ee1ab7433fcce6258f6', '0.002.007', null);
INSERT INTO `aos_sys_dic_index` VALUES ('21136c3dc660464d92fccd66408e149b', 'icon_type_', '图标类型', null, '4018a2217b0542059a26ecf3f605d60f', '0.002.001', null);
INSERT INTO `aos_sys_dic_index` VALUES ('2794338c8b4946b99c361dae80da71b1', 'cmp_grant_type_', '界面元素授权类型', null, '4018a2217b0542059a26ecf3f605d60f', '0.002.001', '');
INSERT INTO `aos_sys_dic_index` VALUES ('27d6f817c9534488a8a9f4f555bb4624', 'equipment_type', '传输覆盖播出设备', null, '56814a2aa9324ee1ab7433fcce6258f6', '0.002.007', null);
INSERT INTO `aos_sys_dic_index` VALUES ('2a9ac1749d224bef924cd258bf35254a', 'sequence_type_', 'ID类型', null, '4018a2217b0542059a26ecf3f605d60f', '0.002.001', '');
INSERT INTO `aos_sys_dic_index` VALUES ('3146aefcb39344cebe6e0a9baa7a4a48', 'south_back_color_', '主页面south区域背景颜色', null, 'c0c43dea5ab6419dad37e459213d7bb5', '0.002.006', null);
INSERT INTO `aos_sys_dic_index` VALUES ('32168ca5857c4236ad6ac884f5e05853', 'platform_level', '平台级别', null, '56814a2aa9324ee1ab7433fcce6258f6', '0.002.007', null);
INSERT INTO `aos_sys_dic_index` VALUES ('338a60b8da9348e587817a127f070293', 'event_severity_', '事件级别', null, '56814a2aa9324ee1ab7433fcce6258f6', '0.002.007', '事件级别');
INSERT INTO `aos_sys_dic_index` VALUES ('33a73af4b729448b91c13acf4d0a31e1', 'is_show_top_nav_', '是否显示顶部导航条', null, 'c0c43dea5ab6419dad37e459213d7bb5', '0.002.006', '是否显示水平导航条。(只有在导航模式为1的情况，此设置才有效,导航模式为2，则水平导航条不会消失)。可选值：true| false。');
INSERT INTO `aos_sys_dic_index` VALUES ('3405701c6f8245168d4b9fdfc7a40d07', 'flow_state_', '流程状态', null, '56814a2aa9324ee1ab7433fcce6258f6', '0.002.007', '流程状态');
INSERT INTO `aos_sys_dic_index` VALUES ('38841cd4f85f4ffb8c07c74650a130dc', 'page_type_', '页面类型', null, '4018a2217b0542059a26ecf3f605d60f', '0.002.001', null);
INSERT INTO `aos_sys_dic_index` VALUES ('3ba3a682c2ac4d52982411dd045fae32', 'skin_', '界面皮肤', null, 'c0c43dea5ab6419dad37e459213d7bb5', '0.002.006', '');
INSERT INTO `aos_sys_dic_index` VALUES ('4707ef6d229c49808be8df98658bf2ae', 'portal_type', '客户端类型', null, '4018a2217b0542059a26ecf3f605d60f', '0.002.001', null);
INSERT INTO `aos_sys_dic_index` VALUES ('4bf11442379e4feabdb8392259a175ab', 'week_day_', '星期几', null, 'aa3082ab67e24d8b884bc4504d3d9945', '0.002.002', null);
INSERT INTO `aos_sys_dic_index` VALUES ('4c2a12d3890a449fa91521a29a3c2436', 'nav_tab_index_', '导航缺省活动页', null, 'c0c43dea5ab6419dad37e459213d7bb5', '0.002.006', null);
INSERT INTO `aos_sys_dic_index` VALUES ('54e48c66b3ef4d99823ca8217a2abf43', 'main_text_color_', '边界区域字体主颜色', null, 'c0c43dea5ab6419dad37e459213d7bb5', '0.002.006', null);
INSERT INTO `aos_sys_dic_index` VALUES ('58b28eade8604dffa7c531b7b42af2fa', 'org_type_', '组织类型', null, '4018a2217b0542059a26ecf3f605d60f', '0.002.001', null);
INSERT INTO `aos_sys_dic_index` VALUES ('5c58a404a396435e88047e21c991f0ef', 'report_type', '报表类型', null, '56814a2aa9324ee1ab7433fcce6258f6', '0.002.007', null);
INSERT INTO `aos_sys_dic_index` VALUES ('61fd319a23b04bd1bdbce7037002976a', 'nav_text_color_', '导航文字颜色', null, 'c0c43dea5ab6419dad37e459213d7bb5', '0.002.006', null);
INSERT INTO `aos_sys_dic_index` VALUES ('77e7fce05577431c993fcc4139beb284', 'audit_status_', '审核结果', null, '56814a2aa9324ee1ab7433fcce6258f6', '0.002.007', '审核结果');
INSERT INTO `aos_sys_dic_index` VALUES ('79004d064e954e55b832a8a9a4332576', 'post_type_', '岗位类型', null, '4018a2217b0542059a26ecf3f605d60f', '0.002.001', null);
INSERT INTO `aos_sys_dic_index` VALUES ('7d048854ccdc4eab996dcb703722065b', 'page_el_type_', '页面元素类型', null, '4018a2217b0542059a26ecf3f605d60f', '0.002.001', null);
INSERT INTO `aos_sys_dic_index` VALUES ('811791160f3348c6b4d0cbc96aeb5630', 'interface_enabled_', '联动接口能否状态', null, '56814a2aa9324ee1ab7433fcce6258f6', '0.002.007', '根据联动接口传输协议定义能否状态字\n');
INSERT INTO `aos_sys_dic_index` VALUES ('81eff0bb4bfb43aea201ea3c77af0b8c', 'flow_stage_', '流程阶段', null, '56814a2aa9324ee1ab7433fcce6258f6', '0.002.007', '');
INSERT INTO `aos_sys_dic_index` VALUES ('91bc8a695b744b27ba66a94561fd6dbc', 'bool_', '是否布尔值字典', null, 'aa3082ab67e24d8b884bc4504d3d9945', '0.002.002', '');
INSERT INTO `aos_sys_dic_index` VALUES ('93ca157eaac3434ba1fbeca047d2d92f', 'resource_type_', '资源类型', null, '56814a2aa9324ee1ab7433fcce6258f6', '0.002.007', null);
INSERT INTO `aos_sys_dic_index` VALUES ('951febb8407e42d4b6a3b23a0941db1d', 'platform_type', '应急广播平台类型', null, '56814a2aa9324ee1ab7433fcce6258f6', '0.002.007', null);
INSERT INTO `aos_sys_dic_index` VALUES ('9837cb554eb74e2ea46283d0f266f8b6', 'station_type', '传输覆盖台站', null, '56814a2aa9324ee1ab7433fcce6258f6', '0.002.007', null);
INSERT INTO `aos_sys_dic_index` VALUES ('a19ac2af964c4a6e8af9c6f1c0d2673f', 'navbar_btn_style_', '顶部导航条按钮风格', null, 'c0c43dea5ab6419dad37e459213d7bb5', '0.002.006', null);
INSERT INTO `aos_sys_dic_index` VALUES ('a43ef5d2f87c4b29bef0816799656b29', 'receiver_type', '接收终端', null, '56814a2aa9324ee1ab7433fcce6258f6', '0.002.007', null);
INSERT INTO `aos_sys_dic_index` VALUES ('a44dd9bbf55041368882e88821309355', 'theme_', '系统主题', null, 'c0c43dea5ab6419dad37e459213d7bb5', '0.002.006', '');
INSERT INTO `aos_sys_dic_index` VALUES ('a9fd5aa3b7654326902a94e70bccedd8', 'module_user_nav_type', '导航菜单类型', null, '4018a2217b0542059a26ecf3f605d60f', '0.002.001', '');
INSERT INTO `aos_sys_dic_index` VALUES ('c66f06c069224241a4785e77d1406f9c', 'delegation_', '委派状态', null, '38368661c53c40b6824869a0ccffad05', '0.002.001.001', '流程任务的委派代理状态');
INSERT INTO `aos_sys_dic_index` VALUES ('cfcbc48fd8ad42678a52334bac8aa299', 'role_type_', '角色类型', null, '4018a2217b0542059a26ecf3f605d60f', '0.002.001', null);
INSERT INTO `aos_sys_dic_index` VALUES ('d6fe9d54e6114d6683c08c09857ad6f6', 'monitor_type', '应急广播监管系统', null, '56814a2aa9324ee1ab7433fcce6258f6', '0.002.007', null);
INSERT INTO `aos_sys_dic_index` VALUES ('d9ba4077623c4fe1aeb643ccf877aea6', 'ewbsres_sync', '同步状态', null, '56814a2aa9324ee1ab7433fcce6258f6', '0.002.007', '');
INSERT INTO `aos_sys_dic_index` VALUES ('dd9f74d16be14db5b0f6d79720c8f177', 'grant_type_', '权限类型', null, '4018a2217b0542059a26ecf3f605d60f', '0.002.001', null);
INSERT INTO `aos_sys_dic_index` VALUES ('df6f7d0efe41420e952251b99f9f14f7', 'ebm_state_', '消息状态', null, '56814a2aa9324ee1ab7433fcce6258f6', '0.002.007', '消息状态');
INSERT INTO `aos_sys_dic_index` VALUES ('e8312d9272bd4db8accd8db793bc58e9', 'ewbsres_state', '资源状态', null, '56814a2aa9324ee1ab7433fcce6258f6', '0.002.007', '资源指的是应急广播平台，播出系统，消息接收设备 和 台站');
INSERT INTO `aos_sys_dic_index` VALUES ('e9a3d79f257b4230a8af39b14c86a781', 'air_type', '电台或电视台', null, '56814a2aa9324ee1ab7433fcce6258f6', '0.002.007', null);
INSERT INTO `aos_sys_dic_index` VALUES ('e9e9cfc195514b05890a2249b81dfe73', 'north_back_img_', '主页面north区域背景图片', null, 'c0c43dea5ab6419dad37e459213d7bb5', '0.002.006', null);
INSERT INTO `aos_sys_dic_index` VALUES ('f0e43eb73cf34af4a7f9b4ec0dc3d009', 'send_flag_', '收发标识', null, '56814a2aa9324ee1ab7433fcce6258f6', '0.002.007', '收发标识');
INSERT INTO `aos_sys_dic_index` VALUES ('f59d8693d8cf4f10850914e09ae9b93c', 'sex_', '性别', null, 'aa3082ab67e24d8b884bc4504d3d9945', '0.002.002', null);
INSERT INTO `aos_sys_dic_index` VALUES ('fa8097dd670643f9a03722c4074ecba5', 'left_logo_', 'Banner左边logo图标', null, 'c0c43dea5ab6419dad37e459213d7bb5', '0.002.006', null);
INSERT INTO `aos_sys_dic_index` VALUES ('fc5496ad028849339e7f2c25914f3535', 'language_code', '语种代码', null, '56814a2aa9324ee1ab7433fcce6258f6', '0.002.007', '符合GB/T 4880.2-2000标准的 3 字母语种代码');
INSERT INTO `aos_sys_dic_index` VALUES ('ffc94f2b7ad14c78b14c46f8fab17543', 'nav_quick_layout_', '快捷菜单布局风格', null, 'c0c43dea5ab6419dad37e459213d7bb5', '0.002.006', '快捷菜单布局风格。');
INSERT INTO aos_sys_dic_index ( `id_`, `key_`, `name_`, `hotkey_`, `catalog_id_`, `catalog_cascade_id_`, `remark_` ) VALUES ( 'f68bc10cafd745c0af9c8a1bfa896ea2', 'msg_type_', '消息类型', NULL, '56814a2aa9324ee1ab7433fcce6258f6', '0.002.007', '消息的类型，\n1：请求播发\n2：取消播发\n' );

-- ----------------------------
-- Table structure for `aos_sys_icon`
-- ----------------------------
DROP TABLE IF EXISTS `aos_sys_icon`;
CREATE TABLE `aos_sys_icon` (
  `id_` varchar(64) NOT NULL COMMENT '流水号',
  `name_` varchar(150) NOT NULL COMMENT '名称',
  `type_` varchar(150) NOT NULL COMMENT '类型',
  PRIMARY KEY (`id_`),
  UNIQUE KEY `aos_sys_icon_ukey` (`name_`,`type_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图标大全';

-- ----------------------------
-- Records of aos_sys_icon
-- ----------------------------
INSERT INTO `aos_sys_icon` VALUES ('3f049b0055134d7a88f93cd4dab7ddf5', '10.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('0afbb447614c462f9f3d922a6e5bb9cc', '11.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('86f8d17f5e1a4c17a1489501e282c458', '13.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('8406dd74b65e4c0db151f9c8fd071e0f', '14.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('29fa6edb7f664ea497a0badab93db90b', '15.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('6710c2f5cc8d48e1a8d6df1a3c20198e', '17.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('c0ae7a5d746e4916825eab267ca87cd2', '19.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('e1d21e10c34446f78882212fe6906462', '2.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('8184974ef0f94d70a1e026bf9a7b8b68', '21.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('10201cddd4bf40a59ad4d0c8ac09cda4', '22.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('3cdb943106074fef8ce848aaad5288e4', '23.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('2ef9066a297140edb320b8bd657d1b91', '24.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('248acf10547b451c84530724bc84f533', '25.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('51ca3056c75b4f68ad7e88908badd49f', '26.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('d18fba1fee23414489d20e4d0c86048f', '28.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('943f48298eb940c9a1b1118eb077e120', '3.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('b0d8247f6057470fb3ed57b9f512d79d', '30.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('715513fb13af4e5bb133f3c0d839b642', '32.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('67cc81beadfe46008b09d2d314e93cc9', '34.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('27aa96b610344dfcbd6881981d1ff2bb', '35.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('7d193427c3dc4970af2a7d7c04c6db23', '36.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('cb41e3b728d14ea3a1a2332198513cd6', '37.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('38a4e4933f804ce0b1d95c55d82179bf', '38.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('b2e00a83db3640abb3118f6a042c5aaf', '39.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('d05e3ef0885f43c29b407885a154afd1', '4.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('78c8186bd7da4d669ade8a4e7d654bd4', '40.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('3cc7a158cff34150b79cce2c1bfbacd4', '41.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('1b61880f6e2a43e99235427dc573a36e', '42.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('e61df29311ae48eb9a7005df109d9c14', '43.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('ed764fb84ff64291a45d80d894fed910', '44.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('4f78fca6fd4644c786c92d05cebdd15e', '45.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('aea3785b0fb94e83a04a85af0d88588f', '46.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('5266c2b271254f1fb31ff13fb8cff429', '47.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('83007223e47a49a89d2c1c24cff64df0', '48.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('8e66d17be46149d89070649ad6687260', '49.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('713e0a0e12b2400da2630e40caefc96b', '5.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('1bbea50d745240a3940d453a3e4f88a3', '51.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('d76f829b0556443bad44aad50f20a22e', '52.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('7197a940e66d4636b2e7caf8885fcc3e', '53.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('8b7565daff18457d97aa2d1a2c954c2c', '54.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('e2398976c15946599da4d95744f6681d', '55.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('63d2b46442264e56a3c1645dd693244e', '56.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('e79c3031290141d4b6a0851b281464af', '6.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('295789d04eae43008a1430690afaa7ce', '7.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('9b6498cd377e4602af8d8b60b668cc4c', '8.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('6499b85a080247c0917e0ea5a2e449e6', '9.png', '2');
INSERT INTO `aos_sys_icon` VALUES ('9539769e3b874394bc6c43e8826c0774', 'add.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('a0f77bc3c45d49b89573439de2e0423f', 'add2.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('d86ada8c680b4f688c4055954c7c57fd', 'against.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('86d04d387b4c4c529f98e4d0c072763f', 'agree.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('ca4a2e1abb4c47b9bf46f912e83daf32', 'app_columns.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('25b3fc9313d54f8692bdee55cb5d92a1', 'app_list.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('68a9b180c4b64bd7b6004df9c92965ed', 'bars.gif', '1');
INSERT INTO `aos_sys_icon` VALUES ('47e12c6954614f60bb198019c5188ee3', 'basket.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('cd9e6ca889164331802ed2a86b42f59f', 'book.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('ea7948d027cb4f51aed5a834af023a03', 'book_user.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('34626c78669f4fe8b4bee14b22a44e3f', 'bug.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('158a8edea2ba4b6eba00ba56ecdb7080', 'bullet_black.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('fd8224e56d4944008839bc73265d7181', 'bullet_blue.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('259628500cdc41f29a4e7df6109a4a5d', 'bullet_green.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('e461bd373f5b428099d1410f438c6566', 'bullet_group.gif', '1');
INSERT INTO `aos_sys_icon` VALUES ('8980faa8228c4a428780494fd6dea34e', 'bullet_group2.gif', '1');
INSERT INTO `aos_sys_icon` VALUES ('1cdd9f968c2a4d41bfdcd15c525d59e8', 'bullet_group3.gif', '1');
INSERT INTO `aos_sys_icon` VALUES ('261c5859245d444b8ab30eb3cc9980e2', 'bullet_group4.gif', '1');
INSERT INTO `aos_sys_icon` VALUES ('c1d7f8b119f14187a0079cff0bb1bd2a', 'bullet_purple.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('8fa00ff3c1464fa79862273ae9e2561c', 'bullet_red.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('1db9b8f31f30479c94752156ac3bda27', 'bullet_yellow.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('92b53bbe0b7b4a34899828f3a3b42359', 'chart_curve.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('9e2010ebd58b4544be3c289cabb913a4', 'chart_flipped.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('8588b294d9274d6c90414b10c7ea1970', 'chart_line.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('580dde4ed5bd46d2831f0ecdbeeb9af0', 'chart_pie.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('23938530df7d4aed9b52bbf6a713c0d0', 'close.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('d4b94d3d33bc4cd18f66bd65b25a62aa', 'close2.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('f20220889f104a8192623b51f971c416', 'config.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('b95ae7101e2941e282dda72ed492fdfe', 'config1.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('7f4d6a427ba24b18bdceeae439b83d71', 'config3.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('117e2414259141fb95beb1599eef88a4', 'connect.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('d943d83a197e416582b2ac17a9c5809d', 'copy.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('ed5d53088d79490a8bf1fa10725045ea', 'cut.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('7965391404cb4ff5a1fd0713ab2a4a6f', 'cv.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('c35ebffa83374738ac7ec5be093ecd7b', 'c_key.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('e61fa08de81d4957bb0b1ed536274675', 'database.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('0d86c088ceb841edaac0c1b757915e64', 'database_add.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('d91ab34e64a744218cac025956542f85', 'del.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('41e273ab37934f86b4a4b09cd7d6a3e3', 'del1.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('406d14328e394ff1af0f6a255795b007', 'del2.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('078757e450cb47e4a603c22be0bb0480', 'del_folder.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('87d5fb4ec43b4357a0a443b8385c3d8f', 'del_page.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('8c136835c6f54c6a8e076d79abd8487a', 'detail.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('d006df515110474a9532c4399feae39b', 'disconnect.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('6a06e1ae78df4627a8a6a84aa2bb94cf', 'doc_ok.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('a10b17d29d674e088535773676994fd6', 'down.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('87dba5a3a22c440ca82a39c183ab2ed7', 'down_left.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('d4855a81f22a419bad35fd69dabdfd7c', 'down_right.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('017162081fe641ce8601867511860599', 'edit.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('91905bfb708f45dd97affb9fe9c6a2b3', 'edit2.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('01f41f88508648599967a9e400e7b6e9', 'email.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('4ee61f0a3d024d84b4d47bbd72e67e38', 'email2.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('8a9589d3b43c4a19b387e6ab214b4e39', 'email3.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('03a808862e194043a7c586878eb7d97c', 'email_go.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('523072274220476ba906d6da44206a83', 'email_open.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('827', 'fa-adjust', '3');
INSERT INTO `aos_sys_icon` VALUES ('1096', 'fa-adn', '3');
INSERT INTO `aos_sys_icon` VALUES ('817', 'fa-align-center', '3');
INSERT INTO `aos_sys_icon` VALUES ('819', 'fa-align-justify', '3');
INSERT INTO `aos_sys_icon` VALUES ('816', 'fa-align-left', '3');
INSERT INTO `aos_sys_icon` VALUES ('818', 'fa-align-right', '3');
INSERT INTO `aos_sys_icon` VALUES ('987', 'fa-ambulance', '3');
INSERT INTO `aos_sys_icon` VALUES ('1049', 'fa-anchor', '3');
INSERT INTO `aos_sys_icon` VALUES ('1107', 'fa-android', '3');
INSERT INTO `aos_sys_icon` VALUES ('1240', 'fa-angellist', '3');
INSERT INTO `aos_sys_icon` VALUES ('996', 'fa-angle-double-down', '3');
INSERT INTO `aos_sys_icon` VALUES ('993', 'fa-angle-double-left', '3');
INSERT INTO `aos_sys_icon` VALUES ('994', 'fa-angle-double-right', '3');
INSERT INTO `aos_sys_icon` VALUES ('995', 'fa-angle-double-up', '3');
INSERT INTO `aos_sys_icon` VALUES ('1000', 'fa-angle-down', '3');
INSERT INTO `aos_sys_icon` VALUES ('997', 'fa-angle-left', '3');
INSERT INTO `aos_sys_icon` VALUES ('998', 'fa-angle-right', '3');
INSERT INTO `aos_sys_icon` VALUES ('999', 'fa-angle-up', '3');
INSERT INTO `aos_sys_icon` VALUES ('1105', 'fa-apple', '3');
INSERT INTO `aos_sys_icon` VALUES ('1118', 'fa-archive', '3');
INSERT INTO `aos_sys_icon` VALUES ('1230', 'fa-area-chart', '3');
INSERT INTO `aos_sys_icon` VALUES ('926', 'fa-arrow-circle-down', '3');
INSERT INTO `aos_sys_icon` VALUES ('923', 'fa-arrow-circle-left', '3');
INSERT INTO `aos_sys_icon` VALUES ('790', 'fa-arrow-circle-o-down', '3');
INSERT INTO `aos_sys_icon` VALUES ('1126', 'fa-arrow-circle-o-left', '3');
INSERT INTO `aos_sys_icon` VALUES ('1125', 'fa-arrow-circle-o-right', '3');
INSERT INTO `aos_sys_icon` VALUES ('791', 'fa-arrow-circle-o-up', '3');
INSERT INTO `aos_sys_icon` VALUES ('924', 'fa-arrow-circle-right', '3');
INSERT INTO `aos_sys_icon` VALUES ('925', 'fa-arrow-circle-up', '3');
INSERT INTO `aos_sys_icon` VALUES ('858', 'fa-arrow-down', '3');
INSERT INTO `aos_sys_icon` VALUES ('855', 'fa-arrow-left', '3');
INSERT INTO `aos_sys_icon` VALUES ('856', 'fa-arrow-right', '3');
INSERT INTO `aos_sys_icon` VALUES ('857', 'fa-arrow-up', '3');
INSERT INTO `aos_sys_icon` VALUES ('832', 'fa-arrows', '3');
INSERT INTO `aos_sys_icon` VALUES ('932', 'fa-arrows-alt', '3');
INSERT INTO `aos_sys_icon` VALUES ('884', 'fa-arrows-h', '3');
INSERT INTO `aos_sys_icon` VALUES ('883', 'fa-arrows-v', '3');
INSERT INTO `aos_sys_icon` VALUES ('864', 'fa-asterisk', '3');
INSERT INTO `aos_sys_icon` VALUES ('1226', 'fa-at', '3');
INSERT INTO `aos_sys_icon` VALUES ('835', 'fa-backward', '3');
INSERT INTO `aos_sys_icon` VALUES ('854', 'fa-ban', '3');
INSERT INTO `aos_sys_icon` VALUES ('885', 'fa-bar-chart', '3');
INSERT INTO `aos_sys_icon` VALUES ('804', 'fa-barcode', '3');
INSERT INTO `aos_sys_icon` VALUES ('942', 'fa-bars', '3');
INSERT INTO `aos_sys_icon` VALUES ('990', 'fa-beer', '3');
INSERT INTO `aos_sys_icon` VALUES ('1160', 'fa-behance', '3');
INSERT INTO `aos_sys_icon` VALUES ('1161', 'fa-behance-square', '3');
INSERT INTO `aos_sys_icon` VALUES ('917', 'fa-bell', '3');
INSERT INTO `aos_sys_icon` VALUES ('981', 'fa-bell-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('1222', 'fa-bell-slash', '3');
INSERT INTO `aos_sys_icon` VALUES ('1223', 'fa-bell-slash-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('1237', 'fa-bicycle', '3');
INSERT INTO `aos_sys_icon` VALUES ('1206', 'fa-binoculars', '3');
INSERT INTO `aos_sys_icon` VALUES ('1229', 'fa-birthday-cake', '3');
INSERT INTO `aos_sys_icon` VALUES ('1097', 'fa-bitbucket', '3');
INSERT INTO `aos_sys_icon` VALUES ('1098', 'fa-bitbucket-square', '3');
INSERT INTO `aos_sys_icon` VALUES ('812', 'fa-bold', '3');
INSERT INTO `aos_sys_icon` VALUES ('970', 'fa-bolt', '3');
INSERT INTO `aos_sys_icon` VALUES ('1203', 'fa-bomb', '3');
INSERT INTO `aos_sys_icon` VALUES ('807', 'fa-book', '3');
INSERT INTO `aos_sys_icon` VALUES ('808', 'fa-bookmark', '3');
INSERT INTO `aos_sys_icon` VALUES ('907', 'fa-bookmark-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('931', 'fa-briefcase', '3');
INSERT INTO `aos_sys_icon` VALUES ('1076', 'fa-btc', '3');
INSERT INTO `aos_sys_icon` VALUES ('1119', 'fa-bug', '3');
INSERT INTO `aos_sys_icon` VALUES ('1154', 'fa-building', '3');
INSERT INTO `aos_sys_icon` VALUES ('985', 'fa-building-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('916', 'fa-bullhorn', '3');
INSERT INTO `aos_sys_icon` VALUES ('1051', 'fa-bullseye', '3');
INSERT INTO `aos_sys_icon` VALUES ('1238', 'fa-bus', '3');
INSERT INTO `aos_sys_icon` VALUES ('1213', 'fa-calculator', '3');
INSERT INTO `aos_sys_icon` VALUES ('873', 'fa-calendar', '3');
INSERT INTO `aos_sys_icon` VALUES ('1039', 'fa-calendar-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('810', 'fa-camera', '3');
INSERT INTO `aos_sys_icon` VALUES ('888', 'fa-camera-retro', '3');
INSERT INTO `aos_sys_icon` VALUES ('1165', 'fa-car', '3');
INSERT INTO `aos_sys_icon` VALUES ('955', 'fa-caret-down', '3');
INSERT INTO `aos_sys_icon` VALUES ('957', 'fa-caret-left', '3');
INSERT INTO `aos_sys_icon` VALUES ('958', 'fa-caret-right', '3');
INSERT INTO `aos_sys_icon` VALUES ('1066', 'fa-caret-square-o-down', '3');
INSERT INTO `aos_sys_icon` VALUES ('1127', 'fa-caret-square-o-left', '3');
INSERT INTO `aos_sys_icon` VALUES ('1068', 'fa-caret-square-o-right', '3');
INSERT INTO `aos_sys_icon` VALUES ('1067', 'fa-caret-square-o-up', '3');
INSERT INTO `aos_sys_icon` VALUES ('956', 'fa-caret-up', '3');
INSERT INTO `aos_sys_icon` VALUES ('1241', 'fa-cc', '3');
INSERT INTO `aos_sys_icon` VALUES ('1219', 'fa-cc-amex', '3');
INSERT INTO `aos_sys_icon` VALUES ('1218', 'fa-cc-discover', '3');
INSERT INTO `aos_sys_icon` VALUES ('1217', 'fa-cc-mastercard', '3');
INSERT INTO `aos_sys_icon` VALUES ('1220', 'fa-cc-paypal', '3');
INSERT INTO `aos_sys_icon` VALUES ('1221', 'fa-cc-stripe', '3');
INSERT INTO `aos_sys_icon` VALUES ('1216', 'fa-cc-visa', '3');
INSERT INTO `aos_sys_icon` VALUES ('918', 'fa-certificate', '3');
INSERT INTO `aos_sys_icon` VALUES ('1028', 'fa-chain-broken', '3');
INSERT INTO `aos_sys_icon` VALUES ('777', 'fa-check', '3');
INSERT INTO `aos_sys_icon` VALUES ('848', 'fa-check-circle', '3');
INSERT INTO `aos_sys_icon` VALUES ('853', 'fa-check-circle-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('1061', 'fa-check-square', '3');
INSERT INTO `aos_sys_icon` VALUES ('831', 'fa-check-square-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('1046', 'fa-chevron-circle-down', '3');
INSERT INTO `aos_sys_icon` VALUES ('1043', 'fa-chevron-circle-left', '3');
INSERT INTO `aos_sys_icon` VALUES ('1044', 'fa-chevron-circle-right', '3');
INSERT INTO `aos_sys_icon` VALUES ('1045', 'fa-chevron-circle-up', '3');
INSERT INTO `aos_sys_icon` VALUES ('878', 'fa-chevron-down', '3');
INSERT INTO `aos_sys_icon` VALUES ('843', 'fa-chevron-left', '3');
INSERT INTO `aos_sys_icon` VALUES ('844', 'fa-chevron-right', '3');
INSERT INTO `aos_sys_icon` VALUES ('877', 'fa-chevron-up', '3');
INSERT INTO `aos_sys_icon` VALUES ('1155', 'fa-child', '3');
INSERT INTO `aos_sys_icon` VALUES ('1009', 'fa-circle', '3');
INSERT INTO `aos_sys_icon` VALUES ('1005', 'fa-circle-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('1185', 'fa-circle-o-notch', '3');
INSERT INTO `aos_sys_icon` VALUES ('1197', 'fa-circle-thin', '3');
INSERT INTO `aos_sys_icon` VALUES ('973', 'fa-clipboard', '3');
INSERT INTO `aos_sys_icon` VALUES ('787', 'fa-clock-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('935', 'fa-cloud', '3');
INSERT INTO `aos_sys_icon` VALUES ('976', 'fa-cloud-download', '3');
INSERT INTO `aos_sys_icon` VALUES ('977', 'fa-cloud-upload', '3');
INSERT INTO `aos_sys_icon` VALUES ('1022', 'fa-code', '3');
INSERT INTO `aos_sys_icon` VALUES ('1027', 'fa-code-fork', '3');
INSERT INTO `aos_sys_icon` VALUES ('1182', 'fa-codepen', '3');
INSERT INTO `aos_sys_icon` VALUES ('982', 'fa-coffee', '3');
INSERT INTO `aos_sys_icon` VALUES ('783', 'fa-cog', '3');
INSERT INTO `aos_sys_icon` VALUES ('890', 'fa-cogs', '3');
INSERT INTO `aos_sys_icon` VALUES ('959', 'fa-columns', '3');
INSERT INTO `aos_sys_icon` VALUES ('875', 'fa-comment', '3');
INSERT INTO `aos_sys_icon` VALUES ('968', 'fa-comment-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('891', 'fa-comments', '3');
INSERT INTO `aos_sys_icon` VALUES ('969', 'fa-comments-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('1065', 'fa-compass', '3');
INSERT INTO `aos_sys_icon` VALUES ('861', 'fa-compress', '3');
INSERT INTO `aos_sys_icon` VALUES ('1225', 'fa-copyright', '3');
INSERT INTO `aos_sys_icon` VALUES ('913', 'fa-credit-card', '3');
INSERT INTO `aos_sys_icon` VALUES ('1026', 'fa-crop', '3');
INSERT INTO `aos_sys_icon` VALUES ('851', 'fa-crosshairs', '3');
INSERT INTO `aos_sys_icon` VALUES ('1048', 'fa-css3', '3');
INSERT INTO `aos_sys_icon` VALUES ('1158', 'fa-cube', '3');
INSERT INTO `aos_sys_icon` VALUES ('1159', 'fa-cubes', '3');
INSERT INTO `aos_sys_icon` VALUES ('983', 'fa-cutlery', '3');
INSERT INTO `aos_sys_icon` VALUES ('1171', 'fa-database', '3');
INSERT INTO `aos_sys_icon` VALUES ('1146', 'fa-delicious', '3');
INSERT INTO `aos_sys_icon` VALUES ('1001', 'fa-desktop', '3');
INSERT INTO `aos_sys_icon` VALUES ('1169', 'fa-deviantart', '3');
INSERT INTO `aos_sys_icon` VALUES ('1147', 'fa-digg', '3');
INSERT INTO `aos_sys_icon` VALUES ('1128', 'fa-dot-circle-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('789', 'fa-download', '3');
INSERT INTO `aos_sys_icon` VALUES ('1109', 'fa-dribbble', '3');
INSERT INTO `aos_sys_icon` VALUES ('1092', 'fa-dropbox', '3');
INSERT INTO `aos_sys_icon` VALUES ('1150', 'fa-drupal', '3');
INSERT INTO `aos_sys_icon` VALUES ('842', 'fa-eject', '3');
INSERT INTO `aos_sys_icon` VALUES ('1052', 'fa-ellipsis-h', '3');
INSERT INTO `aos_sys_icon` VALUES ('1053', 'fa-ellipsis-v', '3');
INSERT INTO `aos_sys_icon` VALUES ('1187', 'fa-empire', '3');
INSERT INTO `aos_sys_icon` VALUES ('963', 'fa-envelope', '3');
INSERT INTO `aos_sys_icon` VALUES ('768', 'fa-envelope-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('1135', 'fa-envelope-square', '3');
INSERT INTO `aos_sys_icon` VALUES ('1034', 'fa-eraser', '3');
INSERT INTO `aos_sys_icon` VALUES ('1069', 'fa-eur', '3');
INSERT INTO `aos_sys_icon` VALUES ('975', 'fa-exchange', '3');
INSERT INTO `aos_sys_icon` VALUES ('1031', 'fa-exclamation', '3');
INSERT INTO `aos_sys_icon` VALUES ('865', 'fa-exclamation-circle', '3');
INSERT INTO `aos_sys_icon` VALUES ('871', 'fa-exclamation-triangle', '3');
INSERT INTO `aos_sys_icon` VALUES ('860', 'fa-expand', '3');
INSERT INTO `aos_sys_icon` VALUES ('899', 'fa-external-link', '3');
INSERT INTO `aos_sys_icon` VALUES ('1063', 'fa-external-link-square', '3');
INSERT INTO `aos_sys_icon` VALUES ('869', 'fa-eye', '3');
INSERT INTO `aos_sys_icon` VALUES ('870', 'fa-eye-slash', '3');
INSERT INTO `aos_sys_icon` VALUES ('1227', 'fa-eyedropper', '3');
INSERT INTO `aos_sys_icon` VALUES ('910', 'fa-facebook', '3');
INSERT INTO `aos_sys_icon` VALUES ('887', 'fa-facebook-square', '3');
INSERT INTO `aos_sys_icon` VALUES ('834', 'fa-fast-backward', '3');
INSERT INTO `aos_sys_icon` VALUES ('840', 'fa-fast-forward', '3');
INSERT INTO `aos_sys_icon` VALUES ('1153', 'fa-fax', '3');
INSERT INTO `aos_sys_icon` VALUES ('1113', 'fa-female', '3');
INSERT INTO `aos_sys_icon` VALUES ('989', 'fa-fighter-jet', '3');
INSERT INTO `aos_sys_icon` VALUES ('1077', 'fa-file', '3');
INSERT INTO `aos_sys_icon` VALUES ('1177', 'fa-file-archive-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('1178', 'fa-file-audio-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('1180', 'fa-file-code-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('1174', 'fa-file-excel-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('1176', 'fa-file-image-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('786', 'fa-file-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('1172', 'fa-file-pdf-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('1175', 'fa-file-powerpoint-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('1078', 'fa-file-text', '3');
INSERT INTO `aos_sys_icon` VALUES ('984', 'fa-file-text-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('1179', 'fa-file-video-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('1173', 'fa-file-word-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('938', 'fa-files-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('773', 'fa-film', '3');
INSERT INTO `aos_sys_icon` VALUES ('930', 'fa-filter', '3');
INSERT INTO `aos_sys_icon` VALUES ('868', 'fa-fire', '3');
INSERT INTO `aos_sys_icon` VALUES ('1040', 'fa-fire-extinguisher', '3');
INSERT INTO `aos_sys_icon` VALUES ('798', 'fa-flag', '3');
INSERT INTO `aos_sys_icon` VALUES ('1020', 'fa-flag-checkered', '3');
INSERT INTO `aos_sys_icon` VALUES ('1019', 'fa-flag-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('936', 'fa-flask', '3');
INSERT INTO `aos_sys_icon` VALUES ('1095', 'fa-flickr', '3');
INSERT INTO `aos_sys_icon` VALUES ('940', 'fa-floppy-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('881', 'fa-folder', '3');
INSERT INTO `aos_sys_icon` VALUES ('1012', 'fa-folder-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('882', 'fa-folder-open', '3');
INSERT INTO `aos_sys_icon` VALUES ('1013', 'fa-folder-open-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('811', 'fa-font', '3');
INSERT INTO `aos_sys_icon` VALUES ('839', 'fa-forward', '3');
INSERT INTO `aos_sys_icon` VALUES ('1111', 'fa-foursquare', '3');
INSERT INTO `aos_sys_icon` VALUES ('1015', 'fa-frown-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('1204', 'fa-futbol-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('1017', 'fa-gamepad', '3');
INSERT INTO `aos_sys_icon` VALUES ('966', 'fa-gavel', '3');
INSERT INTO `aos_sys_icon` VALUES ('1070', 'fa-gbp', '3');
INSERT INTO `aos_sys_icon` VALUES ('866', 'fa-gift', '3');
INSERT INTO `aos_sys_icon` VALUES ('1189', 'fa-git', '3');
INSERT INTO `aos_sys_icon` VALUES ('1188', 'fa-git-square', '3');
INSERT INTO `aos_sys_icon` VALUES ('911', 'fa-github', '3');
INSERT INTO `aos_sys_icon` VALUES ('1011', 'fa-github-alt', '3');
INSERT INTO `aos_sys_icon` VALUES ('902', 'fa-github-square', '3');
INSERT INTO `aos_sys_icon` VALUES ('1115', 'fa-gittip', '3');
INSERT INTO `aos_sys_icon` VALUES ('765', 'fa-glass', '3');
INSERT INTO `aos_sys_icon` VALUES ('927', 'fa-globe', '3');
INSERT INTO `aos_sys_icon` VALUES ('1141', 'fa-google', '3');
INSERT INTO `aos_sys_icon` VALUES ('953', 'fa-google-plus', '3');
INSERT INTO `aos_sys_icon` VALUES ('952', 'fa-google-plus-square', '3');
INSERT INTO `aos_sys_icon` VALUES ('1215', 'fa-google-wallet', '3');
INSERT INTO `aos_sys_icon` VALUES ('1139', 'fa-graduation-cap', '3');
INSERT INTO `aos_sys_icon` VALUES ('991', 'fa-h-square', '3');
INSERT INTO `aos_sys_icon` VALUES ('1190', 'fa-hacker-news', '3');
INSERT INTO `aos_sys_icon` VALUES ('922', 'fa-hand-o-down', '3');
INSERT INTO `aos_sys_icon` VALUES ('920', 'fa-hand-o-left', '3');
INSERT INTO `aos_sys_icon` VALUES ('919', 'fa-hand-o-right', '3');
INSERT INTO `aos_sys_icon` VALUES ('921', 'fa-hand-o-up', '3');
INSERT INTO `aos_sys_icon` VALUES ('915', 'fa-hdd-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('1198', 'fa-header', '3');
INSERT INTO `aos_sys_icon` VALUES ('799', 'fa-headphones', '3');
INSERT INTO `aos_sys_icon` VALUES ('769', 'fa-heart', '3');
INSERT INTO `aos_sys_icon` VALUES ('895', 'fa-heart-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('1196', 'fa-history', '3');
INSERT INTO `aos_sys_icon` VALUES ('785', 'fa-home', '3');
INSERT INTO `aos_sys_icon` VALUES ('986', 'fa-hospital-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('1047', 'fa-html5', '3');
INSERT INTO `aos_sys_icon` VALUES ('1242', 'fa-ils', '3');
INSERT INTO `aos_sys_icon` VALUES ('792', 'fa-inbox', '3');
INSERT INTO `aos_sys_icon` VALUES ('822', 'fa-indent', '3');
INSERT INTO `aos_sys_icon` VALUES ('1030', 'fa-info', '3');
INSERT INTO `aos_sys_icon` VALUES ('850', 'fa-info-circle', '3');
INSERT INTO `aos_sys_icon` VALUES ('1072', 'fa-inr', '3');
INSERT INTO `aos_sys_icon` VALUES ('1094', 'fa-instagram', '3');
INSERT INTO `aos_sys_icon` VALUES ('1239', 'fa-ioxhost', '3');
INSERT INTO `aos_sys_icon` VALUES ('813', 'fa-italic', '3');
INSERT INTO `aos_sys_icon` VALUES ('1151', 'fa-joomla', '3');
INSERT INTO `aos_sys_icon` VALUES ('1073', 'fa-jpy', '3');
INSERT INTO `aos_sys_icon` VALUES ('1183', 'fa-jsfiddle', '3');
INSERT INTO `aos_sys_icon` VALUES ('889', 'fa-key', '3');
INSERT INTO `aos_sys_icon` VALUES ('1018', 'fa-keyboard-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('1075', 'fa-krw', '3');
INSERT INTO `aos_sys_icon` VALUES ('1152', 'fa-language', '3');
INSERT INTO `aos_sys_icon` VALUES ('1002', 'fa-laptop', '3');
INSERT INTO `aos_sys_icon` VALUES ('1233', 'fa-lastfm', '3');
INSERT INTO `aos_sys_icon` VALUES ('1234', 'fa-lastfm-square', '3');
INSERT INTO `aos_sys_icon` VALUES ('867', 'fa-leaf', '3');
INSERT INTO `aos_sys_icon` VALUES ('904', 'fa-lemon-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('1060', 'fa-level-down', '3');
INSERT INTO `aos_sys_icon` VALUES ('1059', 'fa-level-up', '3');
INSERT INTO `aos_sys_icon` VALUES ('1184', 'fa-life-ring', '3');
INSERT INTO `aos_sys_icon` VALUES ('974', 'fa-lightbulb-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('1232', 'fa-line-chart', '3');
INSERT INTO `aos_sys_icon` VALUES ('934', 'fa-link', '3');
INSERT INTO `aos_sys_icon` VALUES ('964', 'fa-linkedin', '3');
INSERT INTO `aos_sys_icon` VALUES ('897', 'fa-linkedin-square', '3');
INSERT INTO `aos_sys_icon` VALUES ('1108', 'fa-linux', '3');
INSERT INTO `aos_sys_icon` VALUES ('820', 'fa-list', '3');
INSERT INTO `aos_sys_icon` VALUES ('796', 'fa-list-alt', '3');
INSERT INTO `aos_sys_icon` VALUES ('944', 'fa-list-ol', '3');
INSERT INTO `aos_sys_icon` VALUES ('943', 'fa-list-ul', '3');
INSERT INTO `aos_sys_icon` VALUES ('1025', 'fa-location-arrow', '3');
INSERT INTO `aos_sys_icon` VALUES ('797', 'fa-lock', '3');
INSERT INTO `aos_sys_icon` VALUES ('1101', 'fa-long-arrow-down', '3');
INSERT INTO `aos_sys_icon` VALUES ('1103', 'fa-long-arrow-left', '3');
INSERT INTO `aos_sys_icon` VALUES ('1104', 'fa-long-arrow-right', '3');
INSERT INTO `aos_sys_icon` VALUES ('1102', 'fa-long-arrow-up', '3');
INSERT INTO `aos_sys_icon` VALUES ('948', 'fa-magic', '3');
INSERT INTO `aos_sys_icon` VALUES ('876', 'fa-magnet', '3');
INSERT INTO `aos_sys_icon` VALUES ('1114', 'fa-male', '3');
INSERT INTO `aos_sys_icon` VALUES ('826', 'fa-map-marker', '3');
INSERT INTO `aos_sys_icon` VALUES ('1042', 'fa-maxcdn', '3');
INSERT INTO `aos_sys_icon` VALUES ('1243', 'fa-meanpath', '3');
INSERT INTO `aos_sys_icon` VALUES ('988', 'fa-medkit', '3');
INSERT INTO `aos_sys_icon` VALUES ('1016', 'fa-meh-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('1036', 'fa-microphone', '3');
INSERT INTO `aos_sys_icon` VALUES ('1037', 'fa-microphone-slash', '3');
INSERT INTO `aos_sys_icon` VALUES ('863', 'fa-minus', '3');
INSERT INTO `aos_sys_icon` VALUES ('846', 'fa-minus-circle', '3');
INSERT INTO `aos_sys_icon` VALUES ('1057', 'fa-minus-square', '3');
INSERT INTO `aos_sys_icon` VALUES ('1058', 'fa-minus-square-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('1004', 'fa-mobile', '3');
INSERT INTO `aos_sys_icon` VALUES ('954', 'fa-money', '3');
INSERT INTO `aos_sys_icon` VALUES ('1117', 'fa-moon-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('766', 'fa-music', '3');
INSERT INTO `aos_sys_icon` VALUES ('1211', 'fa-newspaper-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('1137', 'fa-openid', '3');
INSERT INTO `aos_sys_icon` VALUES ('821', 'fa-outdent', '3');
INSERT INTO `aos_sys_icon` VALUES ('1123', 'fa-pagelines', '3');
INSERT INTO `aos_sys_icon` VALUES ('1228', 'fa-paint-brush', '3');
INSERT INTO `aos_sys_icon` VALUES ('1194', 'fa-paper-plane', '3');
INSERT INTO `aos_sys_icon` VALUES ('1195', 'fa-paper-plane-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('939', 'fa-paperclip', '3');
INSERT INTO `aos_sys_icon` VALUES ('1199', 'fa-paragraph', '3');
INSERT INTO `aos_sys_icon` VALUES ('837', 'fa-pause', '3');
INSERT INTO `aos_sys_icon` VALUES ('1156', 'fa-paw', '3');
INSERT INTO `aos_sys_icon` VALUES ('1214', 'fa-paypal', '3');
INSERT INTO `aos_sys_icon` VALUES ('825', 'fa-pencil', '3');
INSERT INTO `aos_sys_icon` VALUES ('1062', 'fa-pencil-square', '3');
INSERT INTO `aos_sys_icon` VALUES ('829', 'fa-pencil-square-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('905', 'fa-phone', '3');
INSERT INTO `aos_sys_icon` VALUES ('908', 'fa-phone-square', '3');
INSERT INTO `aos_sys_icon` VALUES ('824', 'fa-picture-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('1231', 'fa-pie-chart', '3');
INSERT INTO `aos_sys_icon` VALUES ('1148', 'fa-pied-piper', '3');
INSERT INTO `aos_sys_icon` VALUES ('1149', 'fa-pied-piper-alt', '3');
INSERT INTO `aos_sys_icon` VALUES ('950', 'fa-pinterest', '3');
INSERT INTO `aos_sys_icon` VALUES ('951', 'fa-pinterest-square', '3');
INSERT INTO `aos_sys_icon` VALUES ('872', 'fa-plane', '3');
INSERT INTO `aos_sys_icon` VALUES ('836', 'fa-play', '3');
INSERT INTO `aos_sys_icon` VALUES ('1055', 'fa-play-circle', '3');
INSERT INTO `aos_sys_icon` VALUES ('793', 'fa-play-circle-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('1207', 'fa-plug', '3');
INSERT INTO `aos_sys_icon` VALUES ('862', 'fa-plus', '3');
INSERT INTO `aos_sys_icon` VALUES ('845', 'fa-plus-circle', '3');
INSERT INTO `aos_sys_icon` VALUES ('992', 'fa-plus-square', '3');
INSERT INTO `aos_sys_icon` VALUES ('1132', 'fa-plus-square-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('781', 'fa-power-off', '3');
INSERT INTO `aos_sys_icon` VALUES ('809', 'fa-print', '3');
INSERT INTO `aos_sys_icon` VALUES ('1035', 'fa-puzzle-piece', '3');
INSERT INTO `aos_sys_icon` VALUES ('1192', 'fa-qq', '3');
INSERT INTO `aos_sys_icon` VALUES ('803', 'fa-qrcode', '3');
INSERT INTO `aos_sys_icon` VALUES ('1029', 'fa-question', '3');
INSERT INTO `aos_sys_icon` VALUES ('849', 'fa-question-circle', '3');
INSERT INTO `aos_sys_icon` VALUES ('1006', 'fa-quote-left', '3');
INSERT INTO `aos_sys_icon` VALUES ('1007', 'fa-quote-right', '3');
INSERT INTO `aos_sys_icon` VALUES ('874', 'fa-random', '3');
INSERT INTO `aos_sys_icon` VALUES ('1186', 'fa-rebel', '3');
INSERT INTO `aos_sys_icon` VALUES ('1164', 'fa-recycle', '3');
INSERT INTO `aos_sys_icon` VALUES ('1142', 'fa-reddit', '3');
INSERT INTO `aos_sys_icon` VALUES ('1143', 'fa-reddit-square', '3');
INSERT INTO `aos_sys_icon` VALUES ('795', 'fa-refresh', '3');
INSERT INTO `aos_sys_icon` VALUES ('1122', 'fa-renren', '3');
INSERT INTO `aos_sys_icon` VALUES ('794', 'fa-repeat', '3');
INSERT INTO `aos_sys_icon` VALUES ('1010', 'fa-reply', '3');
INSERT INTO `aos_sys_icon` VALUES ('1023', 'fa-reply-all', '3');
INSERT INTO `aos_sys_icon` VALUES ('879', 'fa-retweet', '3');
INSERT INTO `aos_sys_icon` VALUES ('788', 'fa-road', '3');
INSERT INTO `aos_sys_icon` VALUES ('1041', 'fa-rocket', '3');
INSERT INTO `aos_sys_icon` VALUES ('914', 'fa-rss', '3');
INSERT INTO `aos_sys_icon` VALUES ('1054', 'fa-rss-square', '3');
INSERT INTO `aos_sys_icon` VALUES ('1074', 'fa-rub', '3');
INSERT INTO `aos_sys_icon` VALUES ('937', 'fa-scissors', '3');
INSERT INTO `aos_sys_icon` VALUES ('767', 'fa-search', '3');
INSERT INTO `aos_sys_icon` VALUES ('780', 'fa-search-minus', '3');
INSERT INTO `aos_sys_icon` VALUES ('779', 'fa-search-plus', '3');
INSERT INTO `aos_sys_icon` VALUES ('859', 'fa-share', '3');
INSERT INTO `aos_sys_icon` VALUES ('1201', 'fa-share-alt', '3');
INSERT INTO `aos_sys_icon` VALUES ('1202', 'fa-share-alt-square', '3');
INSERT INTO `aos_sys_icon` VALUES ('1064', 'fa-share-square', '3');
INSERT INTO `aos_sys_icon` VALUES ('830', 'fa-share-square-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('1038', 'fa-shield', '3');
INSERT INTO `aos_sys_icon` VALUES ('880', 'fa-shopping-cart', '3');
INSERT INTO `aos_sys_icon` VALUES ('900', 'fa-sign-in', '3');
INSERT INTO `aos_sys_icon` VALUES ('896', 'fa-sign-out', '3');
INSERT INTO `aos_sys_icon` VALUES ('782', 'fa-signal', '3');
INSERT INTO `aos_sys_icon` VALUES ('971', 'fa-sitemap', '3');
INSERT INTO `aos_sys_icon` VALUES ('1110', 'fa-skype', '3');
INSERT INTO `aos_sys_icon` VALUES ('1134', 'fa-slack', '3');
INSERT INTO `aos_sys_icon` VALUES ('1200', 'fa-sliders', '3');
INSERT INTO `aos_sys_icon` VALUES ('1208', 'fa-slideshare', '3');
INSERT INTO `aos_sys_icon` VALUES ('1014', 'fa-smile-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('960', 'fa-sort', '3');
INSERT INTO `aos_sys_icon` VALUES ('1079', 'fa-sort-alpha-asc', '3');
INSERT INTO `aos_sys_icon` VALUES ('1080', 'fa-sort-alpha-desc', '3');
INSERT INTO `aos_sys_icon` VALUES ('1081', 'fa-sort-amount-asc', '3');
INSERT INTO `aos_sys_icon` VALUES ('1082', 'fa-sort-amount-desc', '3');
INSERT INTO `aos_sys_icon` VALUES ('962', 'fa-sort-asc', '3');
INSERT INTO `aos_sys_icon` VALUES ('961', 'fa-sort-desc', '3');
INSERT INTO `aos_sys_icon` VALUES ('1083', 'fa-sort-numeric-asc', '3');
INSERT INTO `aos_sys_icon` VALUES ('1084', 'fa-sort-numeric-desc', '3');
INSERT INTO `aos_sys_icon` VALUES ('1170', 'fa-soundcloud', '3');
INSERT INTO `aos_sys_icon` VALUES ('1133', 'fa-space-shuttle', '3');
INSERT INTO `aos_sys_icon` VALUES ('1008', 'fa-spinner', '3');
INSERT INTO `aos_sys_icon` VALUES ('1157', 'fa-spoon', '3');
INSERT INTO `aos_sys_icon` VALUES ('1168', 'fa-spotify', '3');
INSERT INTO `aos_sys_icon` VALUES ('941', 'fa-square', '3');
INSERT INTO `aos_sys_icon` VALUES ('906', 'fa-square-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('1124', 'fa-stack-exchange', '3');
INSERT INTO `aos_sys_icon` VALUES ('1093', 'fa-stack-overflow', '3');
INSERT INTO `aos_sys_icon` VALUES ('770', 'fa-star', '3');
INSERT INTO `aos_sys_icon` VALUES ('894', 'fa-star-half', '3');
INSERT INTO `aos_sys_icon` VALUES ('1024', 'fa-star-half-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('771', 'fa-star-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('1162', 'fa-steam', '3');
INSERT INTO `aos_sys_icon` VALUES ('1163', 'fa-steam-square', '3');
INSERT INTO `aos_sys_icon` VALUES ('833', 'fa-step-backward', '3');
INSERT INTO `aos_sys_icon` VALUES ('841', 'fa-step-forward', '3');
INSERT INTO `aos_sys_icon` VALUES ('979', 'fa-stethoscope', '3');
INSERT INTO `aos_sys_icon` VALUES ('838', 'fa-stop', '3');
INSERT INTO `aos_sys_icon` VALUES ('945', 'fa-strikethrough', '3');
INSERT INTO `aos_sys_icon` VALUES ('1145', 'fa-stumbleupon', '3');
INSERT INTO `aos_sys_icon` VALUES ('1144', 'fa-stumbleupon-circle', '3');
INSERT INTO `aos_sys_icon` VALUES ('1033', 'fa-subscript', '3');
INSERT INTO `aos_sys_icon` VALUES ('980', 'fa-suitcase', '3');
INSERT INTO `aos_sys_icon` VALUES ('1116', 'fa-sun-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('1032', 'fa-superscript', '3');
INSERT INTO `aos_sys_icon` VALUES ('947', 'fa-table', '3');
INSERT INTO `aos_sys_icon` VALUES ('1003', 'fa-tablet', '3');
INSERT INTO `aos_sys_icon` VALUES ('967', 'fa-tachometer', '3');
INSERT INTO `aos_sys_icon` VALUES ('805', 'fa-tag', '3');
INSERT INTO `aos_sys_icon` VALUES ('806', 'fa-tags', '3');
INSERT INTO `aos_sys_icon` VALUES ('929', 'fa-tasks', '3');
INSERT INTO `aos_sys_icon` VALUES ('1166', 'fa-taxi', '3');
INSERT INTO `aos_sys_icon` VALUES ('1191', 'fa-tencent-weibo', '3');
INSERT INTO `aos_sys_icon` VALUES ('1021', 'fa-terminal', '3');
INSERT INTO `aos_sys_icon` VALUES ('814', 'fa-text-height', '3');
INSERT INTO `aos_sys_icon` VALUES ('815', 'fa-text-width', '3');
INSERT INTO `aos_sys_icon` VALUES ('775', 'fa-th', '3');
INSERT INTO `aos_sys_icon` VALUES ('774', 'fa-th-large', '3');
INSERT INTO `aos_sys_icon` VALUES ('776', 'fa-th-list', '3');
INSERT INTO `aos_sys_icon` VALUES ('898', 'fa-thumb-tack', '3');
INSERT INTO `aos_sys_icon` VALUES ('1086', 'fa-thumbs-down', '3');
INSERT INTO `aos_sys_icon` VALUES ('893', 'fa-thumbs-o-down', '3');
INSERT INTO `aos_sys_icon` VALUES ('892', 'fa-thumbs-o-up', '3');
INSERT INTO `aos_sys_icon` VALUES ('1085', 'fa-thumbs-up', '3');
INSERT INTO `aos_sys_icon` VALUES ('1056', 'fa-ticket', '3');
INSERT INTO `aos_sys_icon` VALUES ('778', 'fa-times', '3');
INSERT INTO `aos_sys_icon` VALUES ('847', 'fa-times-circle', '3');
INSERT INTO `aos_sys_icon` VALUES ('852', 'fa-times-circle-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('828', 'fa-tint', '3');
INSERT INTO `aos_sys_icon` VALUES ('1235', 'fa-toggle-off', '3');
INSERT INTO `aos_sys_icon` VALUES ('1236', 'fa-toggle-on', '3');
INSERT INTO `aos_sys_icon` VALUES ('1224', 'fa-trash', '3');
INSERT INTO `aos_sys_icon` VALUES ('784', 'fa-trash-o', '3');
INSERT INTO `aos_sys_icon` VALUES ('1167', 'fa-tree', '3');
INSERT INTO `aos_sys_icon` VALUES ('1112', 'fa-trello', '3');
INSERT INTO `aos_sys_icon` VALUES ('901', 'fa-trophy', '3');
INSERT INTO `aos_sys_icon` VALUES ('949', 'fa-truck', '3');
INSERT INTO `aos_sys_icon` VALUES ('1131', 'fa-try', '3');
INSERT INTO `aos_sys_icon` VALUES ('1205', 'fa-tty', '3');
INSERT INTO `aos_sys_icon` VALUES ('1099', 'fa-tumblr', '3');
INSERT INTO `aos_sys_icon` VALUES ('1100', 'fa-tumblr-square', '3');
INSERT INTO `aos_sys_icon` VALUES ('1209', 'fa-twitch', '3');
INSERT INTO `aos_sys_icon` VALUES ('909', 'fa-twitter', '3');
INSERT INTO `aos_sys_icon` VALUES ('886', 'fa-twitter-square', '3');
INSERT INTO `aos_sys_icon` VALUES ('972', 'fa-umbrella', '3');
INSERT INTO `aos_sys_icon` VALUES ('946', 'fa-underline', '3');
INSERT INTO `aos_sys_icon` VALUES ('965', 'fa-undo', '3');
INSERT INTO `aos_sys_icon` VALUES ('1138', 'fa-university', '3');
INSERT INTO `aos_sys_icon` VALUES ('912', 'fa-unlock', '3');
INSERT INTO `aos_sys_icon` VALUES ('1050', 'fa-unlock-alt', '3');
INSERT INTO `aos_sys_icon` VALUES ('903', 'fa-upload', '3');
INSERT INTO `aos_sys_icon` VALUES ('1071', 'fa-usd', '3');
INSERT INTO `aos_sys_icon` VALUES ('772', 'fa-user', '3');
INSERT INTO `aos_sys_icon` VALUES ('978', 'fa-user-md', '3');
INSERT INTO `aos_sys_icon` VALUES ('933', 'fa-users', '3');
INSERT INTO `aos_sys_icon` VALUES ('823', 'fa-video-camera', '3');
INSERT INTO `aos_sys_icon` VALUES ('1130', 'fa-vimeo-square', '3');
INSERT INTO `aos_sys_icon` VALUES ('1181', 'fa-vine', '3');
INSERT INTO `aos_sys_icon` VALUES ('1120', 'fa-vk', '3');
INSERT INTO `aos_sys_icon` VALUES ('801', 'fa-volume-down', '3');
INSERT INTO `aos_sys_icon` VALUES ('800', 'fa-volume-off', '3');
INSERT INTO `aos_sys_icon` VALUES ('802', 'fa-volume-up', '3');
INSERT INTO `aos_sys_icon` VALUES ('1121', 'fa-weibo', '3');
INSERT INTO `aos_sys_icon` VALUES ('1193', 'fa-weixin', '3');
INSERT INTO `aos_sys_icon` VALUES ('1129', 'fa-wheelchair', '3');
INSERT INTO `aos_sys_icon` VALUES ('1212', 'fa-wifi', '3');
INSERT INTO `aos_sys_icon` VALUES ('1106', 'fa-windows', '3');
INSERT INTO `aos_sys_icon` VALUES ('1136', 'fa-wordpress', '3');
INSERT INTO `aos_sys_icon` VALUES ('928', 'fa-wrench', '3');
INSERT INTO `aos_sys_icon` VALUES ('1089', 'fa-xing', '3');
INSERT INTO `aos_sys_icon` VALUES ('1090', 'fa-xing-square', '3');
INSERT INTO `aos_sys_icon` VALUES ('1140', 'fa-yahoo', '3');
INSERT INTO `aos_sys_icon` VALUES ('1210', 'fa-yelp', '3');
INSERT INTO `aos_sys_icon` VALUES ('1088', 'fa-youtube', '3');
INSERT INTO `aos_sys_icon` VALUES ('1091', 'fa-youtube-play', '3');
INSERT INTO `aos_sys_icon` VALUES ('1087', 'fa-youtube-square', '3');
INSERT INTO `aos_sys_icon` VALUES ('e168d971c2754e50affde4576491d1ad', 'filter.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('1720162acf9c4efe8e756cf30add130d', 'folder1.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('c336ce03735342a4a45cbd7354d1551a', 'folder10.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('acd8e6b5220a478087cfa974b932d052', 'folder11.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('f949fe6c441d4f21b214820b2cb0772f', 'folder12.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('9e470992c7c248eea3cccd68a5720a97', 'folder13.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('3e1844b1a3f24874a5c654d4cd260c0e', 'folder14.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('a291d5842d064797b464e43111d59037', 'folder15.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('d1062c84d9fb43e1b5855e7edbe3e077', 'folder16.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('51aaeb59bd524aa1bf32ce1640bbef64', 'folder17.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('c4e7f7096f234e22bd65278dc562d527', 'folder18.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('ec0150f73a1e49b58c1e84a7b4eb6df0', 'folder19.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('425904b9cfb841ad9e6a4b8294d5d7c6', 'folder2.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('a3d68007ed4e46638f38dacd2e4aefa3', 'folder20.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('2502a8ec93f242b7bd835bc3a28f650c', 'folder21.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('5e2faf7bf4e04c5eabc76b6eef11f135', 'folder22.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('9cdee1792d2a459cb1d333d30171fb14', 'folder23.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('3f518fa6b54240d0b162c58c81353e0d', 'folder24.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('711e1bba99e9498289e50c1511ce9f0e', 'folder25.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('75dfb0b4f8ba417e919ff4dd3f06269f', 'folder26.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('1b70b5b1d29e4a4b89009a02aa2c3b20', 'folder27.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('c4fc1069257042258a8959990ec36282', 'folder28.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('f76e66ae09a641d88ccf1653ba596176', 'folder4.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('8112504c85de4eb9950b2cf7c50d495c', 'folder5.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('fcdcf717e45047bf9a68a3dcfc9d4378', 'folder6.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('24c2dcb8d8bc46ad97ad49728edeec07', 'folder7.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('2924672818844b4ca0a8922e8652ab8d', 'folder8.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('607bbe4dc5394a13aca183acc687d228', 'folder9.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('f807aa9802854787bcb066dc76fd3cb0', 'forward.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('a51a44e23d824c69a6121360076a3acf', 'freelance.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('fa3d23dd7b334e3c8b68a614c9b29584', 'go.gif', '1');
INSERT INTO `aos_sys_icon` VALUES ('d168c1698cf04a65baf6bbee913e112c', 'go.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('2e676ebddefd4db7ad39c9b0b18dab0a', 'go1.gif', '1');
INSERT INTO `aos_sys_icon` VALUES ('c4b0144f2e2c4b6e9e9cf9a986b496b5', 'go1.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('c9a96cec5cb1422cbc3d91396bbd420f', 'help.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('4f9f82c7c3f94a58b57f9d07c6411196', 'home.gif', '1');
INSERT INTO `aos_sys_icon` VALUES ('aa853cefe933450abc14536e566bc562', 'home.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('2547c0c164b34b6a9a84380a9ce9eb0c', 'icon134.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('8117c3a0360a4db78033cc11e9e95643', 'icon137.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('3f67bc59382b487090369acbcbf86913', 'icon140.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('41678f6325b94388b75ca194211c13ca', 'icon141.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('54ac7d2561284df7892d58a49140b1c5', 'icon146.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('151f3f59d0c541b7a07f0f572011fcef', 'icon15.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('6f487ee0760a4d7397663cc90902dceb', 'icon150.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('38e44f628bbb4dedac5fff6ccaae7467', 'icon152.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('462eb8e964674b4cb1345622ced97f39', 'icon153.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('42b8a28462f8467ca7ecfb132c051f1a', 'icon154.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('ae66049e1ee64740b1027a72637916c3', 'icon17.gif', '1');
INSERT INTO `aos_sys_icon` VALUES ('fed1c338adc948c3bab3359d427cd855', 'icon25.gif', '1');
INSERT INTO `aos_sys_icon` VALUES ('8b20677643f7491f8e3f3b51d893c627', 'icon26.gif', '1');
INSERT INTO `aos_sys_icon` VALUES ('01b00835ae9c4b1f8b8136a5fbaacd9c', 'icon31.gif', '1');
INSERT INTO `aos_sys_icon` VALUES ('e902c8e49dfb47db91c0199570c23820', 'icon32.gif', '1');
INSERT INTO `aos_sys_icon` VALUES ('1040beb1184b45f19c355b42764b3d62', 'icon34.gif', '1');
INSERT INTO `aos_sys_icon` VALUES ('7924f1c6d56b4b2db3070c818c6c17c0', 'icon35.gif', '1');
INSERT INTO `aos_sys_icon` VALUES ('b147b6cddcff4a5ea9a0c3a07dfb6f67', 'icon36.gif', '1');
INSERT INTO `aos_sys_icon` VALUES ('e12f95d8273f45cb85d0b5b93780aec4', 'icon37.gif', '1');
INSERT INTO `aos_sys_icon` VALUES ('49101113663842069e1f9adea7d2dc32', 'icon38.gif', '1');
INSERT INTO `aos_sys_icon` VALUES ('297812d143e440d5ac62584fe3233236', 'icon39.gif', '1');
INSERT INTO `aos_sys_icon` VALUES ('83d77e2a3af14d878bdccf1295c4aa7c', 'icon41.gif', '1');
INSERT INTO `aos_sys_icon` VALUES ('01752afd87c24368b8fd97a7d20e041e', 'icon42.gif', '1');
INSERT INTO `aos_sys_icon` VALUES ('f6d6a0efc139468395e4a98ec318e96b', 'icon5.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('e4fb0e99cad2416099ea106e38cc3672', 'icon56.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('81b94e4255f2440dbfd5dc3f1dd99c4c', 'icon59.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('90addf9e1ebc4cbf89d21a87658a4fb2', 'icon63.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('b31df2867c3a42848cbd063fe962b0eb', 'icon65.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('e3e8e3b7314b4284828b168edb043cd1', 'icon66.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('c0abb37b923342f59c6f7a646be02914', 'icon67.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('e1ab9bca12a141158597e441e0671e7d', 'icon68.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('90f5a09c03ea40409ce52697b3ce3456', 'icon7.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('d44bca01485f4ba7b1f89734b785e144', 'icon70.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('a3544ccab552494980686137d86e213c', 'icon71.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('c52cf77b1bb04839b720af42ac4f032c', 'icon72.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('28bebb82b3654a72900701cd49c57dca', 'icon75.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('1814adadd96642d5aeed536ac84ba815', 'icon76.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('900891ebafff431b832c8c2359a48004', 'icon77.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('8c118469f1034ef69045f899d146d16b', 'icon78.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('f8dda1c1d3824c77ae42a2f9a63d80a3', 'icon79.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('de683ce30e7f47f19fa72d58953c9d31', 'icon8.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('12d1b219a7fb41ecb90393be92053657', 'icon80.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('4218496dbfcc4d67ab3866d28ef3d452', 'icon82.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('a3ae7add18964105aef250e4db8f7f36', 'icon85.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('34c8cf2d91a3447fbe51c582c57de0ef', 'icon86.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('7b360e91728f4f439db49a4082992762', 'icon88.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('3c6b63ef6f494b8c9a1fdc3a59467641', 'icon9.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('21819040e7854b9c80f8e49c982a11fa', 'icon96.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('5aec3b1f2f504f5e98dae723973177d3', 'icon_19.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('452d7bf0da8f4232a00f3b36c0dcffea', 'icq.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('aff4e1962d4c469ba22f98678e5ea435', 'id.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('6ec45ee43a5a4c05924b88d5661482e6', 'ins.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('597a3c1902bd40499913fa759ddc3325', 'jar.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('f11b52c1ecfe4aa8887c4c813e853b9f', 'key.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('c8b0ea8cb34e4546a005e10ea6de8249', 'layout.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('d1d053db446d41549e4580809b1cfcfb', 'layout2.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('a12e604eaa25495fa79c08a36a027707', 'left.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('b03d4ad23c7b469cbe43752232601f54', 'lightbulb.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('b2d96c0a48d446c7bbbc2321d22278b3', 'lock.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('7fda8a40ee2547538cee919edac1c7d4', 'lock3.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('83f873247d6143468b34af3854ec9701', 'modules_wizard.gif', '1');
INSERT INTO `aos_sys_icon` VALUES ('3a6663647dc546cf872baa2c41aac253', 'monitor.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('98ec9ce433df480a90f81bb694a03076', 'node.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('ffe804844a564f5b8c552d5e55b40024', 'ok.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('95ea017c9fb3474490ece66329407802', 'ok1.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('db59230fabe949a89e930a6939509df9', 'ok3.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('63dcb8a261bb4e5b8c878846f7845e4a', 'ok4.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('86a754008e4644f29251ce638ec4f92e', 'ok5.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('04077aa3e97f4ec2b248ac3a0fffbf2d', 'org.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('6c352fd5fb924d9cbbe3680be5ebe23a', 'org2.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('4d302f4e14bc4f2094f3b961116d09e2', 'outs.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('2dd05ee74381475ca02c21e6636a7209', 'own.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('853b890f9c5d421aae04af10fc0f1d8f', 'page.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('1c48271330a24253a7531834acfe3d55', 'page2.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('34dbf8f4e5c34bb4a26a6c82a158c8cd', 'page3.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('be387784d3134d6090b858a2f4091b2d', 'page_code.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('76517177842f4d02a39735e6c0c291db', 'page_font.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('cc2d3b7defeb46aa8d37ca130f077b04', 'page_next.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('3b6f712abe434792a2208770f3a5ca7f', 'page_office.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('688c51a8d4554a7c8837983a6889d249', 'page_paint.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('fff4806883b94012b903d2e9a45a606e', 'page_picture.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('7c0c2f323cb34f2295263d86b7664921', 'page_vector.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('00c4008b1bb14108a1aefbaefded12b2', 'paint2.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('05909aaf29d8449f825c91a8a1b7e4d9', 'paste.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('6742fc79682a48118258a5934bcbd28a', 'picture.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('01521152e0e445c5b0ba5d6768ef1824', 'pictures.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('5db2349fb3d74a438d566c39f533004e', 'picture_empty.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('a8ab4b7e742e4b89817c0ce0294521a8', 'plugin.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('fb4cdcc3c580417ea97507b2ec38f08f', 'plugin2.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('1436548df45442afb3dd141cbb6eaabc', 'printer.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('2178a1f483b94161b765af5f3fbe8ec6', 'query.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('25083db6b5d948ed8c189fe083b6ade0', 'redo.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('a3f1f0c0fc824e87bfb4831e20905908', 'refresh.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('3e548caffee84453a64e210511e62d8b', 'refresh2.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('4e745649ab1342078196c56407b11638', 'right.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('8d087f8438d54179a5bcc61963b27642', 'save.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('a311f4a7a8174a3fa4f40134fe8c73b2', 'save_all.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('d385e92097a740a5a59b92ee852bc5b5', 'search.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('215156c679844e30bd3c6bd0b79975b5', 'security.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('73efa89be6054af689f60c0af27a43e9', 'send_receive.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('c9cb894ded24490baf1fc08be2cc5ae9', 'shape_align_bottom.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('bbac0c8bf7f44be2b12467ff7b5fbe70', 'shape_align_center.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('505e098ae3f74f03b34b5e654648e2f6', 'shape_align_middle.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('5d8e60b5a6dd49f89dd1ab1a6063f0bc', 'shape_group.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('eb4b294abe5744f280e5dadf3540db74', 'shape_handles.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('7c29ecf440944225b1772391c4c9f2a9', 'shape_move_back.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('a2d06f588bb64800ad90426cbcae1f17', 'share.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('b1e0ba6fbac94dc0b9b92f6bdfda5a87', 'sitemap.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('622a600f6ad04fcd87e1bda2f0163e23', 'sql.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('fdb63eab5df34dc1bc5715552b4836e8', 'sql2.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('737cb927006348a08c5a9ba4b324e9c4', 'sql3.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('64e476b6e598405ba2708e145431c207', 'stop.gif', '1');
INSERT INTO `aos_sys_icon` VALUES ('54135d4ec727445aa2f1afc8bdddb59e', 'stop2.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('8d6dfd1e72f44ccfa1bfeb293e33aa8e', 'stop3.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('5429a072e98b42909d457bf9691d6bfd', 'system.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('3bf45c10a2bf49dd9c766805c3a8a74a', 'table.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('0ef38349886f4b22bb191057215e342c', 'table2.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('928bf243560f4756999fb12ca6ff95a6', 'tables.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('910515a5df9d4832a0a8d869771d5493', 'tables_relation.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('b52d37dc2613458e9c2e06c23570c41c', 'table_edit.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('c8a5f37ee7024753806461a7d0c8fbad', 'tag.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('379eb2bec13048798291e6b94de1e1c5', 'task.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('73c499e7f1c1484a8f25a9ebfb2520a1', 'task1.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('19aa674d6f514fe583b858c556cd0b13', 'task_finish.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('f4472fd8526e47cda5001b6fb463c66f', 'task_list.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('025b3c044bd54947b100a7f3b4e8c397', 'terminal.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('05e97bf338e9467393d39e4d73f2c273', 'textfield.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('0231586301354702bd1470afab9ad784', 'text_cap.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('712b8d256bb34398ba74420bba7bebde', 'text_col.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('1ec85427f4484764a591a75dd128a458', 'text_list.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('48ea6b83e39e4d44a9ab460704f85d03', 'text_upper.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('9c9fa42f7d914aff81cd6d998dc6f4a3', 'theme.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('7c1c735325d946cd85845aaf23fc6cb5', 'time.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('f0dd3c2a81274820ab496b0f79a28c58', 'timeline.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('2c393d3743474a6da31292f56c9c5d1a', 'undo.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('e26f02718dfa4c7191c327cb7302764e', 'up.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('113a21bf852a4a96806cb95a914ee2d4', 'up2.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('f3765853a0564d18853ee2eee97e577f', 'up_left.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('1a29ed6ceaa3403790f96749ddc59214', 'up_right.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('1d7d2f0f0363406285a0440ca9907db0', 'user1.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('021ab0dc441742d287f48edce9570378', 'user2.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('06fd8195933640179cc04d630f8a448e', 'user20.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('6ba5726318a84e6c8d7161b18fc64e24', 'user3.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('3e98fcd9809947e39bf04f86354363de', 'user6.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('e3706cd137f1441bb7df047f232dd1b3', 'user8.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('ce4541e38bcc45d6b7b9f8449a98494b', 'vcard.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('99c4d6e84ad94842a4f5fa904a1774e2', 'vector.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('724869c8c8d248e4a592e60a81de0469', 'wand.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('02d21afddef449198d752284fdb27286', 'webcam.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('ab78000604d441e9922d3971d717cbd6', 'zoom_in.png', '1');
INSERT INTO `aos_sys_icon` VALUES ('95284be0d229425b80194a52ba5c8a6c', 'zoom_out.png', '1');

-- ----------------------------
-- Table structure for `aos_sys_module`
-- ----------------------------
DROP TABLE IF EXISTS `aos_sys_module`;
CREATE TABLE `aos_sys_module` (
  `id_` varchar(64) NOT NULL COMMENT '功能模块流水号',
  `cascade_id_` varchar(255) NOT NULL COMMENT '节点语义ID',
  `name_` varchar(255) NOT NULL COMMENT '功能模块名称',
  `url_` varchar(255) DEFAULT NULL COMMENT '主页面URL',
  `hotkey_` varchar(255) DEFAULT NULL COMMENT '热键',
  `parent_id_` varchar(64) NOT NULL COMMENT '父节点流水号',
  `is_leaf_` varchar(255) NOT NULL DEFAULT '1' COMMENT '是否叶子节点',
  `is_auto_expand_` varchar(255) NOT NULL DEFAULT '0' COMMENT '是否自动展开',
  `icon_name_` varchar(255) DEFAULT NULL COMMENT '节点图标文件名称',
  `status_` varchar(255) NOT NULL DEFAULT '1' COMMENT '当前状态',
  `parent_name_` varchar(255) NOT NULL COMMENT '父节点名称',
  `vector_` varchar(255) DEFAULT NULL COMMENT '矢量图标',
  `sort_no_` int(10) DEFAULT '0' COMMENT '排序号',
  `portal_type_` tinyint(4) DEFAULT '1' COMMENT '模块所属客户端类型（1：中控 2：制播 3：调度）',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='功能模块表';

-- ----------------------------
-- Records of aos_sys_module
-- ----------------------------
INSERT INTO `aos_sys_module` VALUES ('02a7b2d36ab24ca385aa7651765a3e9f', '0.007.006.003', '网络管理', null, 'MonitorConnection', '2e34933a80084a16ac47d6fe71aadb6b', '1', '0', null, '1', '监管全部', null, '1', '3');
INSERT INTO `aos_sys_module` VALUES ('0937f3035a6b4f588e1dd885a8f1c6e1', '0.004.002', '调度流程', 'ewbsres/flow/init.jhtml', '', '13d8c02dcd764cd18105e271ef7f3899', '1', '0', 'task.png', '1', '业务流程', '', '1', '1');
INSERT INTO `aos_sys_module` VALUES ('0b1da58efe7849fb9ed243e072fbac56', '0.007.006.001', '资源监管', '', 'MonitorResource', '2e34933a80084a16ac47d6fe71aadb6b', '1', '0', '', '1', '监管全部', '', '1', '3');
INSERT INTO `aos_sys_module` VALUES ('0b99e9d118b54ee084d9e74bd72cdd1c', '0.001.002', '功能管理', '', '', '7a6d3674e5204937951d01544e18e3aa', '0', '0', 'folder14.png', '1', '系统管理', '', '102', '1');
INSERT INTO `aos_sys_module` VALUES ('0d14198a16b1415e97425529c8fb6394', '0.006.001.002', '日常节目修改', '', 'NormalProgramModify', '52a41422bc6149d394730450a405dab0', '1', '0', '', '1', '日常节目全部', '', '1', '2');
INSERT INTO `aos_sys_module` VALUES ('13d8c02dcd764cd18105e271ef7f3899', '0.004', '业务流程', '', '', '2a4c060f44d944ea926fe44522ce7b39', '0', '1', 'folder6.png', '1', '应急广播系统', 'fa-exchange', '40', '1');
INSERT INTO `aos_sys_module` VALUES ('187494f4c4e24686ab9f58d0500ce2ca', '0.006.005.001', '日常节目取消', '', 'NormalProgramCancel', '187494f4c4e24686ab9f58d0500ce2ca', '0', '0', '', '1', '日常节目取消', '', '1', '2');
INSERT INTO `aos_sys_module` VALUES ('1886bf699ec74f79959bdc8ca8e5371a', '0.001.006.004', '在线用户', 'system/sessionLog/init.jhtml', '', '5b9a439743f141729f46a07e8e222a47', '1', '0', 'user2.png', '1', '监控日志', '', '1041', '1');
INSERT INTO `aos_sys_module` VALUES ('18ea9cfffdce434697a9518901e8cd8b', '0.006.006', '应急节目全部', '', 'EmergencyProgramAdmin', 'fc011d0003624cc281413d469c25b0c0', '0', '0', '', '1', '制作播发', '', '2', '2');
INSERT INTO `aos_sys_module` VALUES ('1977a24b708a48afacf9cb7a3e3dc2d2', '0.005.001', '应急消息播发统计', 'ewbsres/statsicEbm/init.jhtml', '', '8028f31a08524a9e93db3878d994ec0a', '1', '0', 'chart_flipped.png', '1', '报表统计', '', '2', '1');
INSERT INTO `aos_sys_module` VALUES ('1e700591cab9450aa4c10c96bb427156', '0.003.005', '终端管理', '/ewbsres/terminal/init.jhtml', '', '8f322f24d4f14be88aa5187e4b66334d', '1', '0', 'terminal.png', '1', '资源管理', '', '5', '1');
INSERT INTO `aos_sys_module` VALUES ('209cc34300964916954173ecff64c2aa', '0.006.001.003', '日常节目查看', '', 'NormalProgramView', '52a41422bc6149d394730450a405dab0', '1', '0', '', '1', '日常节目全部', '', '1', '2');
INSERT INTO `aos_sys_module` VALUES ('2433f646ba2f492299e63fcaebc6c4f0', '0.007.005', '应急广播综合评估', '', 'EBEvaluation', 'afe6ef70388e4dc1a6928ed2faad733b', '1', '0', '', '1', '调度控制', '', '5', '3');
INSERT INTO `aos_sys_module` VALUES ('26257320db6d4506b60d5d073a646fcf', '0.002.002', '事件分类', 'ewbsres/eventType/init.jhtml', '', '29873ca569fb46c3bbd951a8a1df2472', '1', '0', 'icon59.png', '1', '基础数据', '', '1', '1');
INSERT INTO `aos_sys_module` VALUES ('27adfb1826224a04913aa0ce1d3f96c7', '0.006.008.001', '媒资查看', null, 'MediaView', '647e7336ce634fe498d40ac545b0092c', '1', '0', null, '1', '媒资全部', null, '1', '2');
INSERT INTO `aos_sys_module` VALUES ('29873ca569fb46c3bbd951a8a1df2472', '0.002', '基础数据', '', '', '2a4c060f44d944ea926fe44522ce7b39', '0', '1', 'folder7.png', '1', '应急广播系统', 'fa-th-list', '20', '1');
INSERT INTO `aos_sys_module` VALUES ('2a4c060f44d944ea926fe44522ce7b39', '0', '应急广播系统', '', '', 'p', '0', '1', 'home.png', '1', 'root', '', '1', '1');
INSERT INTO `aos_sys_module` VALUES ('2e34933a80084a16ac47d6fe71aadb6b', '0.007.006', '监管全部', '', 'MonitorAdmin', 'afe6ef70388e4dc1a6928ed2faad733b', '0', '0', '', '1', '调度控制', '', '6', '3');
INSERT INTO `aos_sys_module` VALUES ('36564e8d59ec4e87a3b3f0e86294dead', '0.006.006.005', '应急节目修改', null, 'EmergencyProgramModify', '18ea9cfffdce434697a9518901e8cd8b', '1', '0', null, '1', '应急节目全部', null, '1', '2');
INSERT INTO `aos_sys_module` VALUES ('3708e677df1149e7835425590fb4a880', '0.007.006.002', '终端监管', null, 'MonitorTerminal', '2e34933a80084a16ac47d6fe71aadb6b', '1', '0', null, '1', '监管全部', null, '1', '3');
INSERT INTO `aos_sys_module` VALUES ('43e3a0234bbd4e09b7494ad4f9038670', '0.008.001', '中控日志', 'ewbsres/userLog/control.jhtml', '', '7042ba7115124981b5ea07816f6ec153', '1', '0', 'monitor.png', '1', '用户日志', '', '1', '1');
INSERT INTO `aos_sys_module` VALUES ('4d3cfce7b9b146d2bc8482ec477517aa', '0.001.007.001', '首选项', 'system/preference/init.jhtml', '', '8cead061762d476aa8dc8aae36a8ea74', '1', '0', 'config1.png', '1', '用户设置', '', '1051', '1');
INSERT INTO `aos_sys_module` VALUES ('52a41422bc6149d394730450a405dab0', '0.006.001', '日常节目全部', null, 'NormalProgramAdmin', 'fc011d0003624cc281413d469c25b0c0', '0', '0', null, '1', '制作播发', null, '1', '2');
INSERT INTO `aos_sys_module` VALUES ('59d37a2f11374ca5b01d75e54e2f73af', '0.007.003.001', '应急演练查看', null, 'ExerciseView', '79887bf247b24c60802b9d301b831ca3', '1', '0', null, '1', '应急演练全部', null, '1', '3');
INSERT INTO `aos_sys_module` VALUES ('5b650f89002d46589fc04e405253834f', '0.008.002', '制播日志', 'ewbsres/userLog/broadcast.jhtml', '', '7042ba7115124981b5ea07816f6ec153', '1', '0', 'icon63.png', '1', '用户日志', '', '1', '1');
INSERT INTO `aos_sys_module` VALUES ('5b9a439743f141729f46a07e8e222a47', '0.001.006', '监控日志', '', '', '7a6d3674e5204937951d01544e18e3aa', '0', '0', 'folder27.png', '1', '系统管理', '', '104', '1');
INSERT INTO `aos_sys_module` VALUES ('5fb994e71cb14a77891670990d7f554d', '0.001.001', '参数管理', '', '', '7a6d3674e5204937951d01544e18e3aa', '0', '0', 'folder2.png', '1', '系统管理', '', '101', '1');
INSERT INTO `aos_sys_module` VALUES ('61f4b7ccce334913820bfcffc11f68ae', '0.007.004.002', '应急信息发布', null, 'EmergencyMsgCreate', '7ce7019f3ac04c36bbd97c90760d1ea3', '1', '0', null, '1', '应急信息全部', null, '1', '3');
INSERT INTO `aos_sys_module` VALUES ('63ed1ba5512840849bf65ae5c58c29cd', '0.002.003', '运行图', 'ewbsres/worktime/init.jhtml', '', '29873ca569fb46c3bbd951a8a1df2472', '1', '0', 'icon78.png', '1', '基础数据', '', '1', '1');
INSERT INTO `aos_sys_module` VALUES ('647e7336ce634fe498d40ac545b0092c', '0.006.008', '媒资全部', '', 'MediaAdmin', 'fc011d0003624cc281413d469c25b0c0', '0', '0', '', '1', '制作播发', '', '4', '2');
INSERT INTO `aos_sys_module` VALUES ('6484b63c63b94173b608928c09398318', '0.008.003', '调度日志', 'ewbsres/userLog/dispatch.jhtml', '', '7042ba7115124981b5ea07816f6ec153', '1', '0', 'layout.png', '1', '用户日志', '', '1', '1');
INSERT INTO `aos_sys_module` VALUES ('65320b914c18453f9ca0a84d3f8a0a7b', '0.001.003.001', '组织架构', 'system/org/init.jhtml', '', 'b656d6afbbe844d0a6626d0eb590643f', '1', '0', 'icon56.png', '1', '组织权限', '', '1031', '1');
INSERT INTO `aos_sys_module` VALUES ('658f67153070490e9ea33645513be3da', '0.006.001.004', '日常节目取消', null, 'NormalProgramCancel', '52a41422bc6149d394730450a405dab0', '1', '0', null, '1', '日常节目全部', null, '1', '2');
INSERT INTO `aos_sys_module` VALUES ('6ca6bec748d14e9fae2b765d7bc4da6b', '0.004.003', '调度方案', 'ewbsres/scheme/init.jhtml', '', '13d8c02dcd764cd18105e271ef7f3899', '1', '0', 'task1.png', '1', '业务流程', '', '2', '1');
INSERT INTO `aos_sys_module` VALUES ('7042ba7115124981b5ea07816f6ec153', '0.008', '用户日志', '', '', '2a4c060f44d944ea926fe44522ce7b39', '0', '1', 'folder10.png', '1', '应急广播系统', 'fa-history', '60', '1');
INSERT INTO `aos_sys_module` VALUES ('715f6cf8d2ce4fa694baf2259327f6b1', '0.001.003.002', '岗位与授权', 'system/post/init.jhtml', '', 'b656d6afbbe844d0a6626d0eb590643f', '1', '0', 'icon137.png', '0', '组织权限', '', '1032', '1');
INSERT INTO `aos_sys_module` VALUES ('77ddc49fb68a498db7b384e1dfae65b4', '0.002.001', '区域信息', 'ewbsres/area/init.jhtml', '', '29873ca569fb46c3bbd951a8a1df2472', '1', '0', 'icon79.png', '1', '基础数据', '', '1', '1');
INSERT INTO `aos_sys_module` VALUES ('79887bf247b24c60802b9d301b831ca3', '0.007.003', '应急演练全部', '', 'ExerciseAdmin', 'afe6ef70388e4dc1a6928ed2faad733b', '0', '0', '', '1', '调度控制', '', '3', '3');
INSERT INTO `aos_sys_module` VALUES ('7a6d3674e5204937951d01544e18e3aa', '0.001', '系统管理', '', '', '2a4c060f44d944ea926fe44522ce7b39', '0', '1', 'folder22.png', '1', '应急广播系统', 'fa-cogs', '10', '1');
INSERT INTO `aos_sys_module` VALUES ('7aea5b37e4c544e286aa36d52705f3ad', '0.006.006.004', '应急节目创建', '', 'EmergencyProgramCreate', '18ea9cfffdce434697a9518901e8cd8b', '1', '0', '', '1', '应急节目全部', '', '1', '2');
INSERT INTO `aos_sys_module` VALUES ('7ce7019f3ac04c36bbd97c90760d1ea3', '0.007.004', '应急信息全部', '', 'EmergencyMsgAdmin', 'afe6ef70388e4dc1a6928ed2faad733b', '0', '0', '', '1', '调度控制', '', '4', '3');
INSERT INTO `aos_sys_module` VALUES ('8028f31a08524a9e93db3878d994ec0a', '0.005', '报表统计', '', '', '2a4c060f44d944ea926fe44522ce7b39', '0', '1', 'folder26.png', '1', '应急广播系统', 'fa-table', '50', '1');
INSERT INTO `aos_sys_module` VALUES ('8060277091f14ddf9250493dd7746cfb', '0.006.006.006', '应急节目取消', null, 'EmergencyProgramCancel', '18ea9cfffdce434697a9518901e8cd8b', '1', '0', null, '1', '应急节目全部', null, '1', '2');
INSERT INTO `aos_sys_module` VALUES ('81fc0e05049e467d976ec523182421f2', '0.001.002.004', '序列号', 'system/idMgr/init.jhtml', '', '0b99e9d118b54ee084d9e74bd72cdd1c', '1', '0', 'id.png', '1', '功能管理', '', '1023', '1');
INSERT INTO `aos_sys_module` VALUES ('86a5466853424a9aac72c91cc95f4ecb', '0.006.007', '节目审核全部', '', 'AuditProgramAdmin', 'fc011d0003624cc281413d469c25b0c0', '0', '0', '', '1', '制作播发', '', '3', '2');
INSERT INTO `aos_sys_module` VALUES ('8946d163a7bc460f8c68e4f6123af60f', '0.006.009', '制播日志查看', '', 'ProgramLogView', 'fc011d0003624cc281413d469c25b0c0', '1', '0', '', '1', '制作播发', '', '5', '2');
INSERT INTO `aos_sys_module` VALUES ('8cead061762d476aa8dc8aae36a8ea74', '0.001.007', '用户设置', '', '', '7a6d3674e5204937951d01544e18e3aa', '0', '0', 'folder24.png', '1', '系统管理', '', '105', '1');
INSERT INTO `aos_sys_module` VALUES ('8f322f24d4f14be88aa5187e4b66334d', '0.003', '资源管理', '', '', '2a4c060f44d944ea926fe44522ce7b39', '0', '1', 'folder8.png', '1', '应急广播系统', 'fa-yelp', '30', '1');
INSERT INTO `aos_sys_module` VALUES ('8f34f38212c44ad8b7116cb52a5750be', '0.004.001', '应急消息', 'ewbsres/ebmInfo/init.jhtml', '', '13d8c02dcd764cd18105e271ef7f3899', '1', '0', 'task_finish.png', '1', '业务流程', '', '3', '1');
INSERT INTO `aos_sys_module` VALUES ('8fb8dc1def1544959e561938cf4e72a0', '0.007.004.001', '应急信息查看', null, 'EmergencyMsgView', '7ce7019f3ac04c36bbd97c90760d1ea3', '1', '0', null, '1', '应急信息全部', null, '1', '3');
INSERT INTO `aos_sys_module` VALUES ('91606cd27f574cf68e3d36f95b12b984', '0.007.002.002', '调度方案审核', null, 'DispatchAudit', 'e2b94e3ddf394423a7e828f333ada9ad', '1', '0', null, '1', '调度方案全部', null, '1', '3');
INSERT INTO `aos_sys_module` VALUES ('952ad1957e064e3ba5951d3b572077ae', '0.005.002', '数据包信息统计', 'ewbsres/statsicEbd/init.jhtml', '', '8028f31a08524a9e93db3878d994ec0a', '1', '0', 'chart_curve.png', '1', '报表统计', '', '1', '1');
INSERT INTO `aos_sys_module` VALUES ('98d19d98d5d547e883c63968225759e0', '0.001.001.004', '图标参数', 'system/icon/init.jhtml', '', '5fb994e71cb14a77891670990d7f554d', '1', '0', 'picture.png', '1', '参数管理', '', '1014', '1');
INSERT INTO `aos_sys_module` VALUES ('9a8e5cedcd514af2b37cf5828f509f94', '0.001.001.003', '分类科目', 'system/catalog/init.jhtml', '', '5fb994e71cb14a77891670990d7f554d', '1', '0', 'icon146.png', '1', '参数管理', '', '1013', '1');
INSERT INTO `aos_sys_module` VALUES ('9ad2b405ade74286ad5b804df7eb0085', '0.006.007.001', '日常节目审核', null, 'AuditProgramNormal', '86a5466853424a9aac72c91cc95f4ecb', '1', '0', null, '1', '节目审核全部', null, '1', '2');
INSERT INTO `aos_sys_module` VALUES ('9e4cf77815dd49fbac4158c62af3197a', '0.001.001.001', '键值参数', 'system/param/init.jhtml', '', '5fb994e71cb14a77891670990d7f554d', '1', '0', 'icon79.png', '1', '参数管理', '', '1011', '1');
INSERT INTO `aos_sys_module` VALUES ('a528c7d50502423faa103de70e71cd82', '0.006.006.003', '应急节目查看', '', 'EmergencyProgramView', '18ea9cfffdce434697a9518901e8cd8b', '1', '0', '', '1', '应急节目全部', '', '1', '2');
INSERT INTO `aos_sys_module` VALUES ('a780e48f9692452b95d016dcd3fe1d17', '0.006.007.002', '应急节目审核', null, 'AuditProgramEmergency', '86a5466853424a9aac72c91cc95f4ecb', '1', '0', null, '1', '节目审核全部', null, '1', '2');
INSERT INTO `aos_sys_module` VALUES ('ab56284155e64e60a285df8b64f14471', '0.007.001', '会话/流程查看', '', 'SessionView', 'afe6ef70388e4dc1a6928ed2faad733b', '1', '0', '', '1', '调度控制', '', '1', '3');
INSERT INTO `aos_sys_module` VALUES ('acab16122c3340f4859138d471a27bdc', '0.006.001.001', '日常节目创建', '', 'NormalProgramCreate', '52a41422bc6149d394730450a405dab0', '1', '0', '', '1', '日常节目全部', '', '1', '2');
INSERT INTO `aos_sys_module` VALUES ('afe6ef70388e4dc1a6928ed2faad733b', '0.007', '调度控制', '', '', '2a4c060f44d944ea926fe44522ce7b39', '0', '0', 'folder26.png', '1', '应急广播系统', 'fa-table', '90', '3');
INSERT INTO `aos_sys_module` VALUES ('b5f76c34a58f4c35b2d995d06f59d9c9', '0.007.007', '调度控制日志查看', '', 'DispatchLogView', 'afe6ef70388e4dc1a6928ed2faad733b', '1', '0', '', '1', '调度控制', '', '7', '3');
INSERT INTO `aos_sys_module` VALUES ('b656d6afbbe844d0a6626d0eb590643f', '0.001.003', '组织权限', '', '', '7a6d3674e5204937951d01544e18e3aa', '0', '0', 'folder23.png', '1', '系统管理', '', '103', '1');
INSERT INTO `aos_sys_module` VALUES ('bf530e26480d43b08dd20e31e31274af', '0.001.003.006', '角色与授权', 'system/role/init.jhtml', '', 'b656d6afbbe844d0a6626d0eb590643f', '1', '0', 'icon134.png', '1', '组织权限', '', '1034', '1');
INSERT INTO `aos_sys_module` VALUES ('c6afcd4bb7304d49b2626c0940f6792a', '0.006.008.002', '媒资上传', null, 'MediaUpload', '647e7336ce634fe498d40ac545b0092c', '1', '0', null, '1', '媒资全部', null, '1', '2');
INSERT INTO `aos_sys_module` VALUES ('c79a041c8be54e0697623e3dc7e0e484', '0.003.001', '台站管理', '/ewbsres/station/init.jhtml', '', '8f322f24d4f14be88aa5187e4b66334d', '1', '0', 'icon153.png', '1', '资源管理', '', '2', '1');
INSERT INTO `aos_sys_module` VALUES ('cd55639a893f4e239057dec5f2593b08', '0.003.002', '应急平台', '/ewbsres/platform/init.jhtml', '', '8f322f24d4f14be88aa5187e4b66334d', '1', '0', 'icon152.png', '1', '资源管理', '', '1', '1');
INSERT INTO `aos_sys_module` VALUES ('d8e4e6d75fd74604807989fbbf6eb6ee', '0.003.003', '适配器管理', '/ewbsres/adapter/init.jhtml', '', '8f322f24d4f14be88aa5187e4b66334d', '1', '0', 'icon154.png', '1', '资源管理', '', '3', '1');
INSERT INTO `aos_sys_module` VALUES ('e2b94e3ddf394423a7e828f333ada9ad', '0.007.002', '调度方案全部', '', 'DispatchAdmin', 'afe6ef70388e4dc1a6928ed2faad733b', '0', '0', '', '1', '调度控制', '', '2', '3');
INSERT INTO `aos_sys_module` VALUES ('e7cce0aee68645b386d315c6f47dc794', '0.003.004', '播出系统', '/ewbsres/broadcast/init.jhtml', '', '8f322f24d4f14be88aa5187e4b66334d', '1', '0', 'icon76.png', '1', '资源管理', '', '4', '1');
INSERT INTO `aos_sys_module` VALUES ('ed5d66b851804873951e03ccdbea3f96', '0.007.002.001', '调度方案调整', null, 'DispatchAdjust', 'e2b94e3ddf394423a7e828f333ada9ad', '1', '0', null, '1', '调度方案全部', null, '1', '3');
INSERT INTO `aos_sys_module` VALUES ('edd5fa2592f246718c5836dbbd168a26', '0.007.003.002', '应急演练创建', null, 'ExerciseCreate', '79887bf247b24c60802b9d301b831ca3', '1', '0', null, '1', '应急演练全部', null, '1', '3');
INSERT INTO `aos_sys_module` VALUES ('f107450058a2438eb6ad9bf6a4d7e967', '0.001.001.002', '字典参数', 'system/dictionary/init.jhtml', '', '5fb994e71cb14a77891670990d7f554d', '1', '0', 'icon154.png', '1', '参数管理', '', '1012', '1');
INSERT INTO `aos_sys_module` VALUES ('f405a849f27d4ddebf7f8a2dfbf30bea', '0.001.002.001', '功能模块', 'system/module/init.jhtml', '', '0b99e9d118b54ee084d9e74bd72cdd1c', '1', '0', 'icon75.png', '1', '功能管理', '', '1021', '1');
INSERT INTO `aos_sys_module` VALUES ('f81608054a714b60adb0b1ed574c6b6c', '0.001.003.003', '用户管理', 'system/user/init.jhtml', '', 'b656d6afbbe844d0a6626d0eb590643f', '1', '0', 'user6.png', '1', '组织权限', '', '1033', '1');
INSERT INTO `aos_sys_module` VALUES ('fc011d0003624cc281413d469c25b0c0', '0.006', '制作播发', '', '', '2a4c060f44d944ea926fe44522ce7b39', '0', '0', 'folder26.png', '1', '应急广播系统', 'fa-table', '80', '2');
INSERT INTO `aos_sys_module` VALUES ('fd88d0de59024e4db81ead8510b6bd98', '0.001.002.003', '页面组件', 'system/page/init.jhtml', '', '0b99e9d118b54ee084d9e74bd72cdd1c', '1', '0', 'icon59.png', '1', '功能管理', '', '1022', '1');
INSERT INTO aos_sys_module ( `id_`, `cascade_id_`, `name_`, `url_`, `hotkey_`, `parent_id_`, `is_leaf_`, `is_auto_expand_`, `icon_name_`, `status_`, `parent_name_`, `vector_`, `sort_no_`, `portal_type_` ) VALUES ( '1951e6a7d856412dab9d726565bf5487', '0.003.007', '消息播发记录', '/ewbsres/brRecord/init.jhtml', '', '8f322f24d4f14be88aa5187e4b66334d', '1', '0', 'table2.png', '1', '资源管理', '', 6, 1 );
INSERT INTO aos_sys_module ( `id_`, `cascade_id_`, `name_`, `url_`, `hotkey_`, `parent_id_`, `is_leaf_`, `is_auto_expand_`, `icon_name_`, `status_`, `parent_name_`, `vector_`, `sort_no_`, `portal_type_` ) VALUES ( '73e50f6e8747473f915763a74bbb2598', '0.003.006', '定时调度', 'http://192.168.107.144:7070/ewbsserver/JobManager.html', '', '8f322f24d4f14be88aa5187e4b66334d', '1', '0', 'send_receive.png', '1', '资源管理', '', 7, 1 );
INSERT INTO aos_sys_module ( `id_`, `cascade_id_`, `name_`, `url_`, `hotkey_`, `parent_id_`, `is_leaf_`, `is_auto_expand_`, `icon_name_`, `status_`, `parent_name_`, `vector_`, `sort_no_`, `portal_type_` ) VALUES ( '866eb794c59f4f3db7179defca727bfa', '0.008.004', '业务数据日志', 'ewbsres/ebdLog/init.jhtml', '', '7042ba7115124981b5ea07816f6ec153', '1', '1', 'text_col.png', '1', '用户日志', '', 1, 1 );
-- ----------------------------
-- Table structure for `aos_sys_module_post`
-- ----------------------------
DROP TABLE IF EXISTS `aos_sys_module_post`;
CREATE TABLE `aos_sys_module_post` (
  `id_` varchar(64) NOT NULL COMMENT '流水号',
  `post_id_` varchar(64) NOT NULL COMMENT '岗位流水号',
  `module_id_` varchar(64) NOT NULL COMMENT '功能模块流水号',
  `grant_type_` varchar(200) NOT NULL COMMENT '权限类型(岗位仅提供经办权限)',
  `operate_time_` varchar(255) NOT NULL COMMENT '授权时间',
  `operator_id_` varchar(64) NOT NULL COMMENT '授权人流水号',
  PRIMARY KEY (`id_`),
  UNIQUE KEY `aos_sys_module_post_ukey` (`post_id_`,`module_id_`,`grant_type_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='功能模块-岗位关联表';

-- ----------------------------
-- Records of aos_sys_module_post
-- ----------------------------
INSERT INTO `aos_sys_module_post` VALUES ('168b035de53f40eba7ec8070d6a6e22c', 'af98180624e743b6ba1051d10bee0100', '2a4c060f44d944ea926fe44522ce7b39', '1', '2016-12-01 09:13:09', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_post` VALUES ('2014c3f0a03b46f4967df85fc1cf91b9', 'af98180624e743b6ba1051d10bee0100', '65320b914c18453f9ca0a84d3f8a0a7b', '1', '2016-12-01 09:13:09', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_post` VALUES ('31f08568b8ca4f9ab1f8d40ccbe043bf', 'af98180624e743b6ba1051d10bee0100', 'bf530e26480d43b08dd20e31e31274af', '1', '2016-12-01 09:13:09', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_post` VALUES ('6b934521bd864dc2a1e38d8241791a8e', 'af98180624e743b6ba1051d10bee0100', 'b656d6afbbe844d0a6626d0eb590643f', '1', '2016-12-01 09:13:09', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_post` VALUES ('7b8eee071c4844959ef6926c89c3afb4', 'af98180624e743b6ba1051d10bee0100', '7a6d3674e5204937951d01544e18e3aa', '1', '2016-12-01 09:13:09', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_post` VALUES ('dc4316ea38bb4b4d82bdd75a701e8a0f', 'af98180624e743b6ba1051d10bee0100', '715f6cf8d2ce4fa694baf2259327f6b1', '1', '2016-12-01 09:13:09', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_post` VALUES ('fa16d5d735f642b1a154fdc442e1695f', 'af98180624e743b6ba1051d10bee0100', 'f81608054a714b60adb0b1ed574c6b6c', '1', '2016-12-01 09:13:09', 'fa04db9dd2f54d61b0c8202a25de2dc6');

-- ----------------------------
-- Table structure for `aos_sys_module_role`
-- ----------------------------
DROP TABLE IF EXISTS `aos_sys_module_role`;
CREATE TABLE `aos_sys_module_role` (
  `id_` varchar(64) NOT NULL COMMENT '流水号',
  `role_id_` varchar(64) NOT NULL COMMENT ' 角色流水号',
  `module_id_` varchar(64) NOT NULL COMMENT '功能模块流水号',
  `grant_type_` varchar(200) NOT NULL COMMENT '权限类型',
  `operate_time_` varchar(255) NOT NULL COMMENT '授权时间',
  `operator_id_` varchar(64) NOT NULL COMMENT '授权人流水号',
  PRIMARY KEY (`id_`),
  UNIQUE KEY `aos_sys_module_role_ukey` (`role_id_`,`module_id_`,`grant_type_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='功能模块-角色关联表';

-- ----------------------------
-- Records of aos_sys_module_role
-- ----------------------------
INSERT INTO `aos_sys_module_role` VALUES ('0138a83866bd47b7836b08c8dec3dd85', '73fb7232de754987aa269eaa47d0d9d5', '8fb8dc1def1544959e561938cf4e72a0', '1', '2016-12-12 21:15:38', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('043d65c1a1be4a3597ab8eeb5c1c587e', '84f923b5e334498494eda38d8e86ef80', 'e7cce0aee68645b386d315c6f47dc794', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('07732842f7e44a6785616a5662a80189', '84f923b5e334498494eda38d8e86ef80', '8f34f38212c44ad8b7116cb52a5750be', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('0836e63f68074a5d848e1ccdfb99c8fc', '84f923b5e334498494eda38d8e86ef80', '9e4cf77815dd49fbac4158c62af3197a', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('114f273427714471881e4d346ea9ec7d', '73fb7232de754987aa269eaa47d0d9d5', '3708e677df1149e7835425590fb4a880', '1', '2016-12-12 21:15:38', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('1c3a6bbdc9534f6caa7692e9224d2585', '84f923b5e334498494eda38d8e86ef80', 'f405a849f27d4ddebf7f8a2dfbf30bea', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('1d6c4abb903945f29734d2cbdb98a28b', '84f923b5e334498494eda38d8e86ef80', 'cd55639a893f4e239057dec5f2593b08', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('1e78608b18744ff2b2c66679f16c8692', '73fb7232de754987aa269eaa47d0d9d5', '91606cd27f574cf68e3d36f95b12b984', '1', '2016-12-12 21:15:38', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('2a95bcfb71ad49f182299b9fa9222e2d', '73fb7232de754987aa269eaa47d0d9d5', '02a7b2d36ab24ca385aa7651765a3e9f', '1', '2016-12-12 21:15:38', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('2dee281ef8a64214885b3985e3e16262', '84f923b5e334498494eda38d8e86ef80', '8cead061762d476aa8dc8aae36a8ea74', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('3129d48b0f1a4dd9af6c9d5e9f73a35e', '84f923b5e334498494eda38d8e86ef80', '5b650f89002d46589fc04e405253834f', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('316f9525821c4b1ab719878541ab5af6', '84f923b5e334498494eda38d8e86ef80', '1e700591cab9450aa4c10c96bb427156', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('33d5249f05564e26b0e0cab2e5b64195', '73fb7232de754987aa269eaa47d0d9d5', 'ed5d66b851804873951e03ccdbea3f96', '1', '2016-12-12 21:15:38', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('35db8825bbf34278917ad05eb1d87624', '84f923b5e334498494eda38d8e86ef80', '5b9a439743f141729f46a07e8e222a47', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('37e19b575b634722841f65a6fcea7517', '73fb7232de754987aa269eaa47d0d9d5', '0b1da58efe7849fb9ed243e072fbac56', '1', '2016-12-12 21:15:38', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('38e061d6b0074429bc9fc05e9ec9b981', '84f923b5e334498494eda38d8e86ef80', '7042ba7115124981b5ea07816f6ec153', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('3ea16fd786f34115a6aff5d9c9e02bcb', '84f923b5e334498494eda38d8e86ef80', '5fb994e71cb14a77891670990d7f554d', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('3fb69342120a439fbf1af7ddce73f5bf', '84f923b5e334498494eda38d8e86ef80', '63ed1ba5512840849bf65ae5c58c29cd', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('40fb851af32d45189a134a0664d35cd4', '84f923b5e334498494eda38d8e86ef80', '77ddc49fb68a498db7b384e1dfae65b4', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('4820dc69a14940199577e24be586d36e', '84f923b5e334498494eda38d8e86ef80', '0b99e9d118b54ee084d9e74bd72cdd1c', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('4da3685321b34f7c8b55170ed015a8c9', '84f923b5e334498494eda38d8e86ef80', 'c79a041c8be54e0697623e3dc7e0e484', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('4e46512dcd8945409b0357de3b4d0570', '5bc5263e901042aa88785f2411268426', '52a41422bc6149d394730450a405dab0', '1', '2016-12-12 21:15:01', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('50fd682bc975474fa2877fce44dc0997', '84f923b5e334498494eda38d8e86ef80', '43e3a0234bbd4e09b7494ad4f9038670', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('5588a31aa2524733bbe18ebcd7af61a0', '5bc5263e901042aa88785f2411268426', 'acab16122c3340f4859138d471a27bdc', '1', '2016-12-12 21:15:01', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('618cd31bc6ca4907974a4c34be47b813', '5bc5263e901042aa88785f2411268426', '647e7336ce634fe498d40ac545b0092c', '1', '2016-12-12 21:15:01', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('632c69509ca54ade9b42297bce152299', '84f923b5e334498494eda38d8e86ef80', '2a4c060f44d944ea926fe44522ce7b39', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('6436eae242e34e44862602e04265238e', '73fb7232de754987aa269eaa47d0d9d5', 'ab56284155e64e60a285df8b64f14471', '1', '2016-12-12 21:15:38', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('6825a7ea612f4114adb2f963c686fab0', '84f923b5e334498494eda38d8e86ef80', '65320b914c18453f9ca0a84d3f8a0a7b', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('6c3b81da19e64fdb9a7fd37f39490983', '84f923b5e334498494eda38d8e86ef80', '4d3cfce7b9b146d2bc8482ec477517aa', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('7414f1d5b7fa4dae8e3a251441e67e8a', '84f923b5e334498494eda38d8e86ef80', '29873ca569fb46c3bbd951a8a1df2472', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('74b71e45f50c423ba7269360ea600c90', '5bc5263e901042aa88785f2411268426', '0d14198a16b1415e97425529c8fb6394', '1', '2016-12-12 21:15:01', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('74e19028ebbf4092884bfe95b9cec66c', '73fb7232de754987aa269eaa47d0d9d5', '2433f646ba2f492299e63fcaebc6c4f0', '1', '2016-12-12 21:15:38', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('7a8b2857277147189d76f17a179b5e4d', '5bc5263e901042aa88785f2411268426', '7aea5b37e4c544e286aa36d52705f3ad', '1', '2016-12-12 21:15:01', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('7b0045ff92ae43c5bf5878cc550a26b5', '73fb7232de754987aa269eaa47d0d9d5', '79887bf247b24c60802b9d301b831ca3', '1', '2016-12-12 21:15:38', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('8d16e2ac82594771906ddba798aaba38', '84f923b5e334498494eda38d8e86ef80', '26257320db6d4506b60d5d073a646fcf', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('8d193639679c4ceeb8f0f23def3f86c5', '73fb7232de754987aa269eaa47d0d9d5', '7ce7019f3ac04c36bbd97c90760d1ea3', '1', '2016-12-12 21:15:38', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('92ccd5f0d1a84633be6bde515dad3356', '5bc5263e901042aa88785f2411268426', '9ad2b405ade74286ad5b804df7eb0085', '1', '2016-12-12 21:15:01', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('93d0a8353b9b4a2ca00980d7e649932c', '5bc5263e901042aa88785f2411268426', '36564e8d59ec4e87a3b3f0e86294dead', '1', '2016-12-12 21:15:01', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('9535ab444cce414a93a3e66fd78464ba', '84f923b5e334498494eda38d8e86ef80', 'd8e4e6d75fd74604807989fbbf6eb6ee', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('9704a4e3d0094ae9a16106aec82d6cab', '5bc5263e901042aa88785f2411268426', '18ea9cfffdce434697a9518901e8cd8b', '1', '2016-12-12 21:15:01', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('982d67a57ba54ba887441fd228424319', '84f923b5e334498494eda38d8e86ef80', '7a6d3674e5204937951d01544e18e3aa', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('99606bd28f3240c29d3ff4408e40359d', '5bc5263e901042aa88785f2411268426', '8060277091f14ddf9250493dd7746cfb', '1', '2016-12-12 21:15:01', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('9a0683e5716a409185837a7e08640f52', '84f923b5e334498494eda38d8e86ef80', 'f81608054a714b60adb0b1ed574c6b6c', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('9f11dec5eb1541b99704f861bc1add33', '5bc5263e901042aa88785f2411268426', 'fc011d0003624cc281413d469c25b0c0', '1', '2016-12-12 21:15:01', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('a61d05ed75d646c185197bd0c9d4c490', '84f923b5e334498494eda38d8e86ef80', '8028f31a08524a9e93db3878d994ec0a', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('a73c8cd5d5064b15829ffd902819d777', '5bc5263e901042aa88785f2411268426', 'a528c7d50502423faa103de70e71cd82', '1', '2016-12-12 21:15:01', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('a85446ca421945f78773f023a68eb2a9', '5bc5263e901042aa88785f2411268426', 'a780e48f9692452b95d016dcd3fe1d17', '1', '2016-12-12 21:15:01', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('ae385f4ecc1149da9ea30e4cab989081', '84f923b5e334498494eda38d8e86ef80', '8f322f24d4f14be88aa5187e4b66334d', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('b093ede4a81c49c6b73ac8761780499a', '73fb7232de754987aa269eaa47d0d9d5', '59d37a2f11374ca5b01d75e54e2f73af', '1', '2016-12-12 21:15:38', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('b0eaf3847514418ea87ed58839cee740', '84f923b5e334498494eda38d8e86ef80', '1977a24b708a48afacf9cb7a3e3dc2d2', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('b932b1e6af4a421b93cbab92c8a8d0c0', '84f923b5e334498494eda38d8e86ef80', '1886bf699ec74f79959bdc8ca8e5371a', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('c19e5fb483f2419b82c900410187c407', '73fb7232de754987aa269eaa47d0d9d5', 'afe6ef70388e4dc1a6928ed2faad733b', '1', '2016-12-12 21:15:38', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('c1fca7b7b28d49529a39a315ffe83e07', '73fb7232de754987aa269eaa47d0d9d5', '2e34933a80084a16ac47d6fe71aadb6b', '1', '2016-12-12 21:15:38', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('c4cb615ad87544faa631ec207563e29f', '84f923b5e334498494eda38d8e86ef80', 'f107450058a2438eb6ad9bf6a4d7e967', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('c88fe115c1064e309679782c21a745de', '5bc5263e901042aa88785f2411268426', '86a5466853424a9aac72c91cc95f4ecb', '1', '2016-12-12 21:15:01', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('cad06e0e1af4431b99286bf27df31831', '84f923b5e334498494eda38d8e86ef80', 'fd88d0de59024e4db81ead8510b6bd98', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('cb73e3c3a3384c6ca1e252afe271700b', '5bc5263e901042aa88785f2411268426', '8946d163a7bc460f8c68e4f6123af60f', '1', '2016-12-12 21:15:01', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('cc97389da01c44d686c8095362509cc4', '5bc5263e901042aa88785f2411268426', '209cc34300964916954173ecff64c2aa', '1', '2016-12-12 21:15:01', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('cd91429cbb2d442c9a2260a63f79bc9f', '84f923b5e334498494eda38d8e86ef80', 'b656d6afbbe844d0a6626d0eb590643f', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('cf2718d91d924a47b47df45306f94777', '73fb7232de754987aa269eaa47d0d9d5', 'e2b94e3ddf394423a7e828f333ada9ad', '1', '2016-12-12 21:15:38', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('d2b1bd9182c141cfb1d1ae7ba1e2cbcf', '84f923b5e334498494eda38d8e86ef80', '9a8e5cedcd514af2b37cf5828f509f94', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('d3bd0d6d39b24113999a6b22657bc346', '84f923b5e334498494eda38d8e86ef80', '952ad1957e064e3ba5951d3b572077ae', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('d3bf3f08cda6418ba6311fef319ef7d8', '84f923b5e334498494eda38d8e86ef80', 'bf530e26480d43b08dd20e31e31274af', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('d8d1b437c8814c10a2effcd48e57719a', '5bc5263e901042aa88785f2411268426', 'c6afcd4bb7304d49b2626c0940f6792a', '1', '2016-12-12 21:15:01', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('df3b792aeab44ad39e435253267fe70a', '84f923b5e334498494eda38d8e86ef80', '81fc0e05049e467d976ec523182421f2', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('e39a537f23864287a9803d65c97030fd', '5bc5263e901042aa88785f2411268426', '27adfb1826224a04913aa0ce1d3f96c7', '1', '2016-12-12 21:15:01', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('e74c77c9ff15440e86c1de5102a27513', '84f923b5e334498494eda38d8e86ef80', '6ca6bec748d14e9fae2b765d7bc4da6b', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('e8f53b0238de4b149244c070a810ef8f', '84f923b5e334498494eda38d8e86ef80', '13d8c02dcd764cd18105e271ef7f3899', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('eb2e9eb7e3094655b06fd07006632353', '73fb7232de754987aa269eaa47d0d9d5', 'edd5fa2592f246718c5836dbbd168a26', '1', '2016-12-12 21:15:38', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('ee9d72a9ef054b549fce6e8604567dc0', '5bc5263e901042aa88785f2411268426', '2a4c060f44d944ea926fe44522ce7b39', '1', '2016-12-12 21:15:01', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('f03d6577d2fb46d8b97bff1c28f6171a', '84f923b5e334498494eda38d8e86ef80', '0937f3035a6b4f588e1dd885a8f1c6e1', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('f22fe10f24104296bbb58a006a2bc856', '73fb7232de754987aa269eaa47d0d9d5', '2a4c060f44d944ea926fe44522ce7b39', '1', '2016-12-12 21:15:38', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('fb6585e6baf54b2f95d99886a984edd3', '73fb7232de754987aa269eaa47d0d9d5', 'b5f76c34a58f4c35b2d995d06f59d9c9', '1', '2016-12-12 21:15:38', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('fd2f1dd35aa8411fb3b613971222bbb3', '5bc5263e901042aa88785f2411268426', '658f67153070490e9ea33645513be3da', '1', '2016-12-12 21:15:01', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('fdd2cfcc21fa44648c89a1b63942c674', '84f923b5e334498494eda38d8e86ef80', '6484b63c63b94173b608928c09398318', '1', '2017-02-15 16:15:44', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_module_role` VALUES ('fe7007e370d94a78978fec802d9a26aa', '73fb7232de754987aa269eaa47d0d9d5', '61f4b7ccce334913820bfcffc11f68ae', '1', '2016-12-12 21:15:38', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO aos_sys_module_role ( `id_`, `role_id_`, `module_id_`, `grant_type_`, `operate_time_`, `operator_id_` ) VALUES ( 'c4002fb34bd04f568578e6f861dae40b', '84f923b5e334498494eda38d8e86ef80', '866eb794c59f4f3db7179defca727bfa', '1', '2018-03-06 10:15:43', 'fa04db9dd2f54d61b0c8202a25de2dc6' );
INSERT INTO aos_sys_module_role ( `id_`, `role_id_`, `module_id_`, `grant_type_`, `operate_time_`, `operator_id_` ) VALUES ( 'caf3699aaca048c9a807f5df57987750', '84f923b5e334498494eda38d8e86ef80', '1951e6a7d856412dab9d726565bf5487', '1', '2018-03-06 10:15:43', 'fa04db9dd2f54d61b0c8202a25de2dc6' );
INSERT INTO aos_sys_module_role ( `id_`, `role_id_`, `module_id_`, `grant_type_`, `operate_time_`, `operator_id_` ) VALUES ( 'e32a03adc97e4667823c454bf4c76d23', '84f923b5e334498494eda38d8e86ef80', '73e50f6e8747473f915763a74bbb2598', '1', '2018-03-06 10:15:43', 'fa04db9dd2f54d61b0c8202a25de2dc6' );

-- ----------------------------
-- Table structure for `aos_sys_module_user`
-- ----------------------------
DROP TABLE IF EXISTS `aos_sys_module_user`;
CREATE TABLE `aos_sys_module_user` (
  `id_` varchar(64) NOT NULL COMMENT '流水号',
  `user_id_` varchar(64) NOT NULL COMMENT ' 用户流水号',
  `module_id_` varchar(64) NOT NULL COMMENT '功能模块流水号',
  `grant_type_` varchar(200) NOT NULL COMMENT '权限类型',
  `operate_time_` varchar(255) NOT NULL COMMENT '授权时间',
  `operator_id_` varchar(64) NOT NULL COMMENT '授权人',
  PRIMARY KEY (`id_`),
  UNIQUE KEY `aos_sys_module_user_ukey` (`user_id_`,`module_id_`,`grant_type_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='功能模块-用户关联表';

-- ----------------------------
-- Table structure for `aos_sys_module_user_nav`
-- ----------------------------
DROP TABLE IF EXISTS `aos_sys_module_user_nav`;
CREATE TABLE `aos_sys_module_user_nav` (
  `id_` varchar(64) NOT NULL COMMENT '流水号',
  `module_id_` varchar(64) NOT NULL COMMENT '功能模块流水号',
  `user_id_` varchar(64) NOT NULL COMMENT '人员流水号',
  `nav_icon_` varchar(255) DEFAULT NULL COMMENT '浮动导航图标文件',
  `type_` varchar(200) NOT NULL COMMENT '导航类型',
  `sort_no_` int(10) DEFAULT NULL COMMENT '排序号',
  PRIMARY KEY (`id_`),
  UNIQUE KEY `aos_sys_module_user_nav_ukey` (`module_id_`,`user_id_`,`type_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='功能模块-用户关联表(浮动导航|快捷导航)';

-- ----------------------------
-- Records of aos_sys_module_user_nav
-- ----------------------------
INSERT INTO `aos_sys_module_user_nav` VALUES ('05b510ce5e354f2783e731a910c3ff18', '13d8c02dcd764cd18105e271ef7f3899', 'fa04db9dd2f54d61b0c8202a25de2dc6', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('125ac4de4ade4e889d2bd56b2639c18e', '8f34f38212c44ad8b7116cb52a5750be', 'fa04db9dd2f54d61b0c8202a25de2dc6', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('14e6eb9199ec427a85d82db54d4032e4', '36564e8d59ec4e87a3b3f0e86294dead', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('15e768c6d57b432880fc270cac5beb3f', '8f322f24d4f14be88aa5187e4b66334d', 'fa04db9dd2f54d61b0c8202a25de2dc6', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('1c43925782e44cc3a2c5bd5d70304faa', '2433f646ba2f492299e63fcaebc6c4f0', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('1f2091219b1c4e92863d24a0c7fb269e', '52a41422bc6149d394730450a405dab0', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('1f2aeba1f9d34e1ebc6618883b33626c', 'f81608054a714b60adb0b1ed574c6b6c', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('2a7c762879cf4e7f86e62028c78460bd', '5fb994e71cb14a77891670990d7f554d', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('2c02ec5dc95245248e32df88ec5589a3', '26257320db6d4506b60d5d073a646fcf', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('3093fd67db2d4b0b931e1cca30a4d424', 'afe6ef70388e4dc1a6928ed2faad733b', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('30bdd979d8b24d6a8e724b291980f211', 'f405a849f27d4ddebf7f8a2dfbf30bea', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('32450e6493004574a34f2766526d4397', 'c79a041c8be54e0697623e3dc7e0e484', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('372c0c13c6df49cb91216cc68c78759c', 'fc011d0003624cc281413d469c25b0c0', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('397de09638544fc3b495b4fe5e052948', '0b99e9d118b54ee084d9e74bd72cdd1c', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('3ab992f61bb34ec4a7b6250dca4a8e85', 'a780e48f9692452b95d016dcd3fe1d17', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('41f3fac5517f4cbb8b1a752e2aea9037', '6ca6bec748d14e9fae2b765d7bc4da6b', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('426536a116494aa79c73ffa28ba44894', '7a6d3674e5204937951d01544e18e3aa', 'fa04db9dd2f54d61b0c8202a25de2dc6', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('45c345a6777d47da8ee20fcbd0880cba', '9ad2b405ade74286ad5b804df7eb0085', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('482fbdff696449c6b79cd4dbe264f9d3', '1e700591cab9450aa4c10c96bb427156', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('489676ec3cb04669977023b7ccd9f93d', 'e7cce0aee68645b386d315c6f47dc794', 'fa04db9dd2f54d61b0c8202a25de2dc6', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('4a94d009a7bb49268bfdce5b73d5f137', '65320b914c18453f9ca0a84d3f8a0a7b', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('4e47ff14aeec4715817e1f574f70e51b', 'a528c7d50502423faa103de70e71cd82', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('5223335a894a456aa113e5b0363b29b2', '81fc0e05049e467d976ec523182421f2', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('5298b94933e44fddb951877e1c636f23', '29873ca569fb46c3bbd951a8a1df2472', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('5634e1f45c0840968998f1402ae25fe5', 'd8e4e6d75fd74604807989fbbf6eb6ee', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('579837db13ce4f8e88ec7641a32ca735', '9e4cf77815dd49fbac4158c62af3197a', 'fa04db9dd2f54d61b0c8202a25de2dc6', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('59319a1e83b147b7b537448695535cf9', 'b5f76c34a58f4c35b2d995d06f59d9c9', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('5bbbabaaf00b4f609e20b4241c1f8364', '86a5466853424a9aac72c91cc95f4ecb', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('5dbdf30710254e78b4841d3f7473f208', '61f4b7ccce334913820bfcffc11f68ae', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('6237d65bbbf74e92b879511c8b2228c9', '1886bf699ec74f79959bdc8ca8e5371a', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('6483f9d54a7e45f19e2c7a0a815cf8d2', 'd8e4e6d75fd74604807989fbbf6eb6ee', 'fa04db9dd2f54d61b0c8202a25de2dc6', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('67672ff7e39e4a70a42e50939b29b7de', 'cd55639a893f4e239057dec5f2593b08', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('68018a911b604b9c94e77bb73bc548f7', 'bf530e26480d43b08dd20e31e31274af', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('7217751e5af545d781641874c7d024c1', '0937f3035a6b4f588e1dd885a8f1c6e1', 'fa04db9dd2f54d61b0c8202a25de2dc6', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('72badcc04a3b4c14ab2f20258ca55438', '658f67153070490e9ea33645513be3da', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('752c799ff82a4e13906d51cd414865ea', '209cc34300964916954173ecff64c2aa', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('7c863aa137264f19a6a1f9b7de0ba48e', '4d3cfce7b9b146d2bc8482ec477517aa', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('7cb94a1516794c10be790d9eced8b353', 'ed5d66b851804873951e03ccdbea3f96', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('7d66e62c8fb441369788401f407d5c89', '8946d163a7bc460f8c68e4f6123af60f', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('862943b5788d476f82dfc9d8736c2b9b', '02a7b2d36ab24ca385aa7651765a3e9f', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('8708787fa4a2494698b2817bf93f6e62', '7a6d3674e5204937951d01544e18e3aa', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('8934e606165748559238c71a7f9c377b', '2a4c060f44d944ea926fe44522ce7b39', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('8a25abf9b82540859671789dc368344c', 'fd88d0de59024e4db81ead8510b6bd98', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('90366cc5f74a4e80898c8dce3640ffe7', '8f322f24d4f14be88aa5187e4b66334d', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('90756611e0454144a9a2ef8da28a0918', '13d8c02dcd764cd18105e271ef7f3899', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('9376101dcde542fa9fcf386effafcecc', 'cd55639a893f4e239057dec5f2593b08', 'fa04db9dd2f54d61b0c8202a25de2dc6', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('97a5864c4ed944c19db3cf22b5dd7888', '59d37a2f11374ca5b01d75e54e2f73af', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('99c3ee8a51e847b0bf4a7cb6ba4a9771', '8cead061762d476aa8dc8aae36a8ea74', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('9c012ed2cf224d9ab6980b82992b6273', '3708e677df1149e7835425590fb4a880', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('9ca8c55000c9436c815e13554c2804ef', 'c6afcd4bb7304d49b2626c0940f6792a', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('a23ff59a23b04108b31629127ee01cb4', '79887bf247b24c60802b9d301b831ca3', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('a535c2081df0449caac6de53d2f735ed', '7ce7019f3ac04c36bbd97c90760d1ea3', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('a75455f40fec4c52af47026189318cee', '0d14198a16b1415e97425529c8fb6394', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('b05dfc60e300411ca83f6fa1ec2a733a', '1e700591cab9450aa4c10c96bb427156', 'fa04db9dd2f54d61b0c8202a25de2dc6', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('bed8f2ffe7bc442e80b3630da6e3d83f', 'f107450058a2438eb6ad9bf6a4d7e967', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('c2b444f360294743aee3415fd809f937', '7aea5b37e4c544e286aa36d52705f3ad', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('c30e2ccd178f4a1abed406de17fcf909', 'e2b94e3ddf394423a7e828f333ada9ad', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('c52f0fa52e8f4e08b5df462c40fe56f2', '18ea9cfffdce434697a9518901e8cd8b', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('c727ffe4909648cf8d5a5286d28d689d', 'c79a041c8be54e0697623e3dc7e0e484', 'fa04db9dd2f54d61b0c8202a25de2dc6', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('c971748a6c094d61bd0b5915d35cbed4', '5b9a439743f141729f46a07e8e222a47', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('c98686ae3bd347a6931dce89a9827a0b', 'ab56284155e64e60a285df8b64f14471', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('cbf04c497c0f4880a92f8e120a42fc55', '6ca6bec748d14e9fae2b765d7bc4da6b', 'fa04db9dd2f54d61b0c8202a25de2dc6', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('ce514e8c8dac432ab7299d94590830d1', 'edd5fa2592f246718c5836dbbd168a26', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('d3824ceaddf84d228102fab163baba4e', '9e4cf77815dd49fbac4158c62af3197a', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('d49ca6c6c1f949769cdf78514318c216', 'e7cce0aee68645b386d315c6f47dc794', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('d57c819c7e064d5984133f090e959e74', '8fb8dc1def1544959e561938cf4e72a0', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('d731ca6a06614e4abcbf180a1fe06cc6', '8060277091f14ddf9250493dd7746cfb', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('d8fd749c78424d2e9157a605186a7160', '27adfb1826224a04913aa0ce1d3f96c7', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('dad4900718714ecb88507292799e1508', '91606cd27f574cf68e3d36f95b12b984', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('dbc9b01434c14e9ca749c882bfd11ccb', '5fb994e71cb14a77891670990d7f554d', 'fa04db9dd2f54d61b0c8202a25de2dc6', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('dbd61ba98dc846eea71c72cdbae1c31f', '8f34f38212c44ad8b7116cb52a5750be', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('dd56d251c05f4bf9aaae34920b4e8712', '2e34933a80084a16ac47d6fe71aadb6b', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('df6ccf2093eb48338bf17ddaf83859e4', '0937f3035a6b4f588e1dd885a8f1c6e1', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('e9a3ef3099a14d7ea5039e8ece61fa97', 'acab16122c3340f4859138d471a27bdc', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('f2830047560b401ba1bd75016603bf62', 'b656d6afbbe844d0a6626d0eb590643f', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('f2d07817ed8f47d5bc72210acd7c0809', '647e7336ce634fe498d40ac545b0092c', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('f5781f8e252147309e1326bb4a754f91', '77ddc49fb68a498db7b384e1dfae65b4', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('f722466a3ec044ec81de32d3f19532a5', '9a8e5cedcd514af2b37cf5828f509f94', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('fe5c24cf1c99419da78dd9f71ee305c8', '2a4c060f44d944ea926fe44522ce7b39', 'fa04db9dd2f54d61b0c8202a25de2dc6', null, '1', null);
INSERT INTO `aos_sys_module_user_nav` VALUES ('fee72371300e4955ad5d9b0aa23200ff', '0b1da58efe7849fb9ed243e072fbac56', '5c9846f8ef7f47c5a16305160532b757', null, '1', null);

-- ----------------------------
-- Table structure for `aos_sys_org`
-- ----------------------------
DROP TABLE IF EXISTS `aos_sys_org`;
CREATE TABLE `aos_sys_org` (
  `id_` varchar(64) NOT NULL COMMENT '流水号',
  `cascade_id_` varchar(255) NOT NULL COMMENT '节点语义ID',
  `name_` varchar(255) NOT NULL COMMENT '组织名称',
  `hotkey_` varchar(255) DEFAULT NULL COMMENT '热键',
  `parent_id_` varchar(64) NOT NULL COMMENT '父节点流水号',
  `pareant_name_` varchar(255) DEFAULT NULL COMMENT '父节点名称',
  `is_leaf_` varchar(255) NOT NULL DEFAULT '1' COMMENT '是否叶子节点',
  `is_auto_expand_` varchar(255) NOT NULL DEFAULT '0' COMMENT '是否自动展开',
  `icon_name_` varchar(255) DEFAULT NULL COMMENT '节点图标文件名称',
  `status_` varchar(255) NOT NULL DEFAULT '1' COMMENT '当前状态',
  `type_` varchar(255) NOT NULL COMMENT '组织类型',
  `biz_code_` varchar(255) DEFAULT NULL COMMENT '业务对照码',
  `custom_code_` varchar(4000) DEFAULT NULL COMMENT '自定义扩展码',
  `create_time_` varchar(255) NOT NULL COMMENT '创建时间',
  `creater_id_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `sort_no_` int(10) DEFAULT NULL COMMENT '排序号',
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织表';

-- ----------------------------
-- Records of aos_sys_org
-- ----------------------------
INSERT INTO `aos_sys_org` VALUES ('0965ef0ffd294a838f53012e3ef9bfd0', '0.006.001', '应急广播产品线', '', 'bd48c11cfd6f48e196c5fa399c410329', '创新中心', '1', '0', '', '1', '1', '111', '', '2016-02-01 23:56:02', 'fa04db9dd2f54d61b0c8202a25de2dc6', '1', null);
INSERT INTO `aos_sys_org` VALUES ('1e1441c6be2b4ffdb3f73759cb1e207b', '0.002', '财务部', '', '63cf387a243d4d9799367d773b853346', '湖南康通电子股份有限公司', '1', '0', '', '1', '1', '136', '', '2016-02-01 23:53:10', 'fa04db9dd2f54d61b0c8202a25de2dc6', '10', null);
INSERT INTO `aos_sys_org` VALUES ('4be918af97ee491aa71378d065a8c7f7', '0.006.002', '公共广播产品线', '', 'bd48c11cfd6f48e196c5fa399c410329', '创新中心', '1', '0', '', '1', '1', '112', '', '2016-02-01 23:56:15', 'fa04db9dd2f54d61b0c8202a25de2dc6', '10', null);
INSERT INTO `aos_sys_org` VALUES ('63cf387a243d4d9799367d773b853346', '0', '湖南康通电子股份有限公司', '', 'p', 'root', '0', '1', 'home.png', '1', '1', '123', '', '2012-01-01 12:12:12', 'fa04db9dd2f54d61b0c8202a25de2dc6', '0', null);
INSERT INTO `aos_sys_org` VALUES ('b2fa97f8bed34eee86201f01fa786ed4', '0.001', '生产中心', '', '63cf387a243d4d9799367d773b853346', '湖南康通电子股份有限公司', '1', '0', '', '1', '1', '134', '', '2016-02-01 20:53:34', 'fa04db9dd2f54d61b0c8202a25de2dc6', '1', null);
INSERT INTO `aos_sys_org` VALUES ('bd48c11cfd6f48e196c5fa399c410329', '0.006', '创新中心', '', '63cf387a243d4d9799367d773b853346', '湖南康通电子股份有限公司', '0', '1', '', '1', '1', '888', '', '2016-02-01 23:55:46', 'fa04db9dd2f54d61b0c8202a25de2dc6', '50', null);

-- ----------------------------
-- Table structure for `aos_sys_page`
-- ----------------------------
DROP TABLE IF EXISTS `aos_sys_page`;
CREATE TABLE `aos_sys_page` (
  `id_` varchar(64) NOT NULL COMMENT '流水号',
  `module_id_` varchar(64) NOT NULL COMMENT '功能模块ID',
  `name_` varchar(255) DEFAULT NULL COMMENT '名称',
  `url_` varchar(255) DEFAULT NULL COMMENT '页面URL',
  `type_` varchar(255) NOT NULL COMMENT '类型',
  `enabled_` varchar(255) NOT NULL COMMENT '使能状态',
  `is_default_` varchar(255) NOT NULL DEFAULT '0' COMMENT '是否缺省子页面',
  `icon_` varchar(255) DEFAULT NULL COMMENT '小图标',
  `icon_big_` varchar(255) DEFAULT NULL COMMENT '大图标',
  `vector_` varchar(255) DEFAULT NULL COMMENT '矢量图标',
  `sort_no_` int(10) DEFAULT '0' COMMENT '排序号',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='模块页面表';

-- ----------------------------
-- Records of aos_sys_page
-- ----------------------------
INSERT INTO `aos_sys_page` VALUES ('19041a2cb7264838b48288ce9b0b8e80', '98d19d98d5d547e883c63968225759e0', '大图标[64X64]', 'initIconList.jhtml?type_=2', '2', '1', '0', 'picture.png', '', '', '3');
INSERT INTO `aos_sys_page` VALUES ('3d27639e05c04e22a8914a7570c42e20', '98d19d98d5d547e883c63968225759e0', '分割符2', null, '4', '1', '0', null, null, null, '4');
INSERT INTO `aos_sys_page` VALUES ('9a99bb312d6b4678b31660385636e126', '98d19d98d5d547e883c63968225759e0', '分割符1', null, '4', '1', '0', null, null, null, '2');
INSERT INTO `aos_sys_page` VALUES ('9e30077a6bb043cc8db8c8d4b5fa5c91', '98d19d98d5d547e883c63968225759e0', '小图标[16X16]', 'initIconList.jhtml?type_=1', '2', '1', '1', 'pictures.png', null, null, '1');
INSERT INTO `aos_sys_page` VALUES ('bcb82c68976d4527a8f1c8bdbb8b4a04', '98d19d98d5d547e883c63968225759e0', '矢量图标', 'initIconList.jhtml?type_=3', '2', '1', '0', 'vector.png', '', '', '5');
INSERT INTO `aos_sys_page` VALUES ('c47935773e9443dd9a6ceecb69982ac2', '4d3cfce7b9b146d2bc8482ec477517aa', '我的快捷菜单', 'initMyNav.jhtml', '2', '1', '0', 'org.png', '', '', '4');
INSERT INTO `aos_sys_page` VALUES ('cacc4fcdc5454f1ebab63b00e4bf56cc', '4d3cfce7b9b146d2bc8482ec477517aa', '我的个人设置', 'initMyInfo.jhtml', '2', '1', '1', 'icon_19.png', '', '', '1');
INSERT INTO `aos_sys_page` VALUES ('dd3d77605b824ca18357222128aebc5b', '4d3cfce7b9b146d2bc8482ec477517aa', '分割符', '', '4', '0', '0', '', '', '', '5');

-- ----------------------------
-- Table structure for `aos_sys_page_el`
-- ----------------------------
DROP TABLE IF EXISTS `aos_sys_page_el`;
CREATE TABLE `aos_sys_page_el` (
  `id_` varchar(64) NOT NULL COMMENT '流水号',
  `dom_id_` varchar(255) NOT NULL COMMENT 'DOM ID',
  `name_` varchar(255) NOT NULL COMMENT '名称',
  `type_` varchar(255) NOT NULL COMMENT '类型',
  `module_id_` varchar(64) NOT NULL COMMENT '所属功能模块流水号',
  `page_id_` varchar(64) NOT NULL COMMENT '页面ID',
  `remark_` varchar(4000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id_`),
  UNIQUE KEY `aos_sys_page_el_ukey` (`dom_id_`,`module_id_`,`page_id_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='页面元素表';

-- ----------------------------
-- Records of aos_sys_page_el
-- ----------------------------
INSERT INTO `aos_sys_page_el` VALUES ('aecaf19e40c74fa385ff2c1c476a5412', '_btn_add_demo', '新增', '1', '9e4cf77815dd49fbac4158c62af3197a', '9e4cf77815dd49fbac4158c62af3197a', null);

-- ----------------------------
-- Table structure for `aos_sys_page_el_grant`
-- ----------------------------
DROP TABLE IF EXISTS `aos_sys_page_el_grant`;
CREATE TABLE `aos_sys_page_el_grant` (
  `id_` varchar(64) NOT NULL COMMENT '流水号',
  `el_id_` varchar(64) NOT NULL COMMENT '页面元素流水号',
  `user_id_` varchar(64) DEFAULT NULL COMMENT '用户流水号',
  `role_id_` varchar(64) DEFAULT NULL COMMENT '角色流水号',
  `post_id_` varchar(64) DEFAULT NULL COMMENT '岗位流水号',
  `grant_type_` varchar(255) NOT NULL COMMENT '权限类型',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='页面元素授权表';

-- ----------------------------
-- Records of aos_sys_page_el_grant
-- ----------------------------
INSERT INTO `aos_sys_page_el_grant` VALUES ('49ba555ae83a46a78bb78c7c9bd97778', 'aecaf19e40c74fa385ff2c1c476a5412', 'ace5e18c7bd34c88885f62cf5538712b', null, null, '3');

-- ----------------------------
-- Table structure for `aos_sys_param`
-- ----------------------------
DROP TABLE IF EXISTS `aos_sys_param`;
CREATE TABLE `aos_sys_param` (
  `id_` varchar(64) NOT NULL COMMENT '流水号',
  `key_` varchar(255) NOT NULL COMMENT '参数键',
  `value_` varchar(255) DEFAULT NULL COMMENT '参数值',
  `catalog_id_` varchar(64) NOT NULL COMMENT '所属分类流水号',
  `catalog_cascade_id_` varchar(255) NOT NULL COMMENT '所属分类节点语义ID',
  `name_` varchar(255) NOT NULL COMMENT '参数名称',
  `is_overwrite_` varchar(255) NOT NULL COMMENT '是否可覆盖',
  `overwrite_field_` varchar(255) DEFAULT NULL COMMENT '覆盖来源字段',
  `remark_` varchar(4000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id_`),
  UNIQUE KEY `aos_sys_param_ukey` (`key_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='参数表';

-- ----------------------------
-- Records of aos_sys_param
-- ----------------------------
INSERT INTO `aos_sys_param` VALUES ('0dabca413781404b9cbfaae3881fc5a6', 'app_ico', '/static/icon/ico/aos2.ico', 'f22f39171b484d81a604d3eb50b33584', '0.001.002.002', '应用系统小图标', '0', null, '浏览器标题栏左上角显示');
INSERT INTO `aos_sys_param` VALUES ('19ad8d57d72b4ecf98ad133ebb6492bd', 'page_load_gif_', 'wheel.gif', 'f22f39171b484d81a604d3eb50b33584', '0.001.002.002', '页面刷新动画文件', '0', '', '页面刷新动画文件。可选的预设动画文件：run.gif、wheel.gif。动画path：/static/image/gif/pageload。');
INSERT INTO `aos_sys_param` VALUES ('1bd1c0a5849d4a30b3f1eb2761b2865d', 'show_login_win_head_', 'true', 'f22f39171b484d81a604d3eb50b33584', '0.001.002.002', '是否显示登录窗口标题栏', '0', '', '是否显示登录窗口标题栏(Ext登录风格)。缺省值：false。可选值：true|false。');
INSERT INTO `aos_sys_param` VALUES ('1cc1a1bee27f44389e7e64a89d9ccc76', 'msgtarget_', 'qtip', 'f22f39171b484d81a604d3eb50b33584', '0.001.002.002', '表单校验消息提示方式', '0', null, '可选值必须是下列值之一 (1)、qtip 当用户鼠标悬停在该域之上时显示包含了消息的迅速提示。(2)、title 显示消息以浏览器默认的title属性弹出。 (3)、under 在域下方添加一块包含了错误消息的div。(4)、side 添加一个错误图标在域的右边，鼠标悬停上面时弹出显示消息。');
INSERT INTO `aos_sys_param` VALUES ('1f2adf3aa726447e9728a3f67b718b41', 'vercode_show_', '1', '6f94ef4ac17d4d098f91f0a4579f8591', '0.001.002.001', '验证码开关', '0', '', '是否在登录页面显示验证码及后台验证码验证。可选值：0(否) | 1(是)。');
INSERT INTO `aos_sys_param` VALUES ('2ca0aa5b3442421bb6fdd754cbe539b0', 'default_level_', '11', 'f22f39171b484d81a604d3eb50b33584', '0.001.002.002', '地图缩放级别', '0', null, '百度地图缩放级别');
INSERT INTO `aos_sys_param` VALUES ('2ced63b04ad44c59a17ead04d9a1f324', 'session_timeout_', '3600', '4ee1d6e48b31487b849a72cd03d2512a', '0.001.001', '会话超时时间', '0', '', '单位：秒');
INSERT INTO `aos_sys_param` VALUES ('2e45751d623f48389e12bfb7c3891597', 'treenode_cursor_', 'pointer', 'f22f39171b484d81a604d3eb50b33584', '0.001.002.002', '树节点鼠标光标样式', '0', '', '缺省值：pointer。可选值：任何符合Css的cursor属性定义的值。default | pointer | auto等。');
INSERT INTO `aos_sys_param` VALUES ('376a372433634af4b6d2d558f1959f5b', 'role_grant_mode_', '1', '01afa7f4bb4d4189a4817ade7b5c1b8d', '0.001.002.003', '角色授权模式', '0', null, '角色授权模式(角色可见度)。可选值：1|2。1：可见当前管理员创建的角色和当前管理员所属组织的其他管理员创建的角色。2：可见当前管理员创建的角色和当前管理员所属组织及其下级子孙组织的其他管理员创建的角色。');
INSERT INTO `aos_sys_param` VALUES ('3ff54d336e57461d8cd708a8bad8bc36', 'combobox_emptytext', '请选择...', 'f22f39171b484d81a604d3eb50b33584', '0.001.002.002', '下拉选择框的缺省提示信息', '0', null, null);
INSERT INTO `aos_sys_param` VALUES ('43fb125bed224cbbaf2390df1041c3b0', 'dev_account_', 'root', '01afa7f4bb4d4189a4817ade7b5c1b8d', '0.001.002.003', '开发者登录帐号', '0', '', '登录页面开发者按钮登录帐号。可以配置为任何一个存在的帐号。');
INSERT INTO `aos_sys_param` VALUES ('47bd798e93314882af7244ba1ae9fbc8', 'vercode_characters_', 'abcdetx2345678', '6f94ef4ac17d4d098f91f0a4579f8591', '0.001.002.001', '验证码待选字符集', '0', '', '验证码待选字符集。');
INSERT INTO `aos_sys_param` VALUES ('53267cbf8e9f4fc490798054fbe85c7e', 'copyright_', '2008-2017 Comtom', 'f22f39171b484d81a604d3eb50b33584', '0.001.002.002', '版权信息', '0', '', '版权信息');
INSERT INTO `aos_sys_param` VALUES ('58099059c3004213ae0977d4ed5551f5', 'prevent_rightclick_', 'false', '01afa7f4bb4d4189a4817ade7b5c1b8d', '0.001.002.003', '全局右键', '0', null, '阻止浏览器缺省鼠标右键事件。可选值：true|false。缺省值：true。');
INSERT INTO `aos_sys_param` VALUES ('6032c7e953994a718f6072c956906538', 'app_title_', '中控管理系统', 'f22f39171b484d81a604d3eb50b33584', '0.001.002.002', '首页浏览器标题内容', '0', '', '首页浏览器标题内容');
INSERT INTO `aos_sys_param` VALUES ('714d370fa7f54b01ae9a006806c60135', 'is_show_top_nav_', 'true', 'a072411787f545edb7e7c3c23fa6c0ff', '0.001.002.004', '水平导航条', '1', 'is_show_top_nav_', '是否显示水平导航条。(只有在导航模式为1的情况，此设置才有效,导航模式为2，则水平导航条不会消失)。可选值：true| false。');
INSERT INTO `aos_sys_param` VALUES ('7290d8299cba41dc9e23323a0db22a95', 'json_format', '1', 'f22f39171b484d81a604d3eb50b33584', '0.001.002.002', 'JSON输出模式', '0', '', ' json输出模式。0：格式化输出模式；1：压缩模式。');
INSERT INTO `aos_sys_param` VALUES ('74d65234606c4f448eb06d6a07a6c138', 'user_head_catalog_id_', '613', '01afa7f4bb4d4189a4817ade7b5c1b8d', '0.001.002.003', '用户头像流文件分类ID', '0', null, '用户头像流文件分类ID，分类科目中的用户头像分类ID。用户上传文件时使用。');
INSERT INTO `aos_sys_param` VALUES ('757f555c0d734539baa70e8a1788cc62', 'is_show_statusbar_', 'true', 'f22f39171b484d81a604d3eb50b33584', '0.001.002.002', '是否显示主界面状态栏', '0', '', '是否显示主界面状态栏。可选值：true|false。');
INSERT INTO `aos_sys_param` VALUES ('7882ac5560ef4a04993279dfc24b6f23', 'user_obj_del_mode_', 'update', '01afa7f4bb4d4189a4817ade7b5c1b8d', '0.001.002.003', '用户对象删除模式', '0', null, '物理删除：delete；逻辑删除：update。');
INSERT INTO `aos_sys_param` VALUES ('7bc3899d2fc24e4b804e6f763c3d6b36', 'dev_account_login_', '0', '01afa7f4bb4d4189a4817ade7b5c1b8d', '0.001.002.003', '开发者登录帐号开关', '0', null, '是否启用开发者登录帐号功能,如启用则在登录界面会出现[开发者]按钮。可选值：1 | 0。提示：系统若在生产模式下运行，则此配置忽略，自动关闭开发者登录功能。');
INSERT INTO `aos_sys_param` VALUES ('7e4d320f454843739f40476dcbcdc91a', 'run_mode_', '1', '01afa7f4bb4d4189a4817ade7b5c1b8d', '0.001.002.003', '运行模式', '0', '', '0：开发模式；1：生产模式；2：在线体验模式');
INSERT INTO `aos_sys_param` VALUES ('873c2cb1dde54b308211e8a2bc6edbb5', 'theme_', 'classic', 'f22f39171b484d81a604d3eb50b33584', '0.001.002.002', '系统主题风格', '1', 'theme_', '可选值：classic。缺省值：classic。');
INSERT INTO `aos_sys_param` VALUES ('9cb03933f61e4c68a58e50873ac38fbd', 'page_load_msg_', '正在拼命加载页面, 请稍等...', 'f22f39171b484d81a604d3eb50b33584', '0.001.002.002', '页面加载等待提示信息', '0', null, null);
INSERT INTO `aos_sys_param` VALUES ('a19b559ab8804f5887867df4151ad631', 'page_size_', '50', 'f22f39171b484d81a604d3eb50b33584', '0.001.002.002', '分页大小', '0', null, '缺省的分页大小。JSP页面的属性设置会覆盖这个配置。');
INSERT INTO `aos_sys_param` VALUES ('a1ad9c90f524467180bceecd1f02b8b8', 'nav_mode_', '1', 'a072411787f545edb7e7c3c23fa6c0ff', '0.001.002.004', '导航模式', '1', 'nav_mode_', '可选值，1：水平导航按钮和垂直导航的卡片属于统一层级；2：水平导航按钮和垂直导航的卡片有上下级级联导航关系。(当前模式2未实现)');
INSERT INTO `aos_sys_param` VALUES ('a843396066db4e77a7b9ed7ae4cdda37', 'login_page_', 'login.ext.jsp', '01afa7f4bb4d4189a4817ade7b5c1b8d', '0.001.002.003', '登录页面', '0', '', 'Ext原生风格的登录界面');
INSERT INTO `aos_sys_param` VALUES ('ad9cb685144c4e36b19b31e53190221a', 'skin_', 'neptune', 'f22f39171b484d81a604d3eb50b33584', '0.001.002.002', '系统缺省皮肤', '1', 'skin_', '可选值：blue|gray|neptune|aos。缺省值：blue。');
INSERT INTO `aos_sys_param` VALUES ('b07b8e2cc1284d14b9e680a5cac0722a', 'app_name_', '中控管理系统', 'f22f39171b484d81a604d3eb50b33584', '0.001.002.002', '应用系统名称', '0', '', '应用系统名称');
INSERT INTO `aos_sys_param` VALUES ('be82f58309a24ee397d3a24631e48405', 'vercode_length_', '4', '6f94ef4ac17d4d098f91f0a4579f8591', '0.001.002.001', '验证码长度', '0', '', '验证码长度');
INSERT INTO `aos_sys_param` VALUES ('c744b99660c74302b7a3ae5834a7b6eb', 'grid_column_algin_', 'left', 'f22f39171b484d81a604d3eb50b33584', '0.001.002.002', '表格列内容对齐方式', '0', '', '表格列对齐模式。有效值：left|center|right。');
INSERT INTO `aos_sys_param` VALUES ('d2a8d045aa7e44ef93f0735a4c486bc6', 'nav_tab_index_', '0', 'a072411787f545edb7e7c3c23fa6c0ff', '0.001.002.004', '导航缺省活动页', '1', 'nav_tab_index_', '左侧布局的导航卡片缺省激活的卡片索引号，索引下标从0开始。');
INSERT INTO `aos_sys_param` VALUES ('d326dd010e5346d5be2004107ab440fc', 'default_load_level_', '2', '4ee1d6e48b31487b849a72cd03d2512a', '0.001.001', '区域默认加载级别', '0', '', '客户端加载区域树时，默认加载区域级别。');
INSERT INTO `aos_sys_param` VALUES ('d3b33d5dee5b46738f9525b8977d7673', 'login_back_img_', '-1', 'f22f39171b484d81a604d3eb50b33584', '0.001.002.002', '登录页面的背景图片', '0', '', '登录页面的背景图片。可选的预设背景图片为：0.jpg、1.jpg、2.jpg。如果需要随机出现背景，则将其设置为-1。');
INSERT INTO `aos_sys_param` VALUES ('d8016e270b0e465b83c9a72b4a1785a8', 'scheme_audit_', '2', '4ee1d6e48b31487b849a72cd03d2512a', '0.001.001', '方案审核', '0', null, '方案审核标识（1：需要审核 2：不需要审核）');
INSERT INTO `aos_sys_param` VALUES ('eeb66260a2c141b2a0bb06bb41d7803b', 'navbar_btn_style_', 'tight', 'a072411787f545edb7e7c3c23fa6c0ff', '0.001.002.004', '导航条按钮风格', '1', 'navbar_btn_style_', '顶部水平导航条的按钮样式风格。tight：组合按钮；standalone：独立按钮。');
INSERT INTO `aos_sys_param` VALUES ('f18649a182a946a693b36f817374cb1c', 'welcome_page_title_', '中控管理系统', 'f22f39171b484d81a604d3eb50b33584', '0.001.002.002', '欢迎页标题', '0', '', '系统登录后第一个缺省打开的Tab页面的标题。缺省：\"欢迎\"。');
INSERT INTO `aos_sys_param` VALUES ('fcf73fa9129649a08ebb41e1ec8b5340', 'layout_', 'tab', 'f22f39171b484d81a604d3eb50b33584', '0.001.002.002', 'Center区域布局风格', '1', 'layout_', '系统业务经办区域(center)布局风格。可选值：tab|page。缺省值：tab。tab：支持同时打开多个功能模块界面；page：当前只能打开1个功能界面。');
INSERT INTO `aos_sys_param` VALUES ('fdd95913b7e54e3ca6be9557484159f5', 'tab_focus_color_', '#0099FF', 'f22f39171b484d81a604d3eb50b33584', '0.001.002.002', 'Tab高亮颜色', '1', 'tab_focus_color_', '缺省的当前Tab卡片高亮提示颜色');
INSERT INTO `aos_sys_param` VALUES ('661e5606d1204c4d984d379a91389ba4', 'file_path_', '/var/lib/emergency/file', '4ee1d6e48b31487b849a72cd03d2512a', '0.001.001', '媒资文件地址', '0', '', '媒资文件地址');
INSERT INTO `aos_sys_param` VALUES ('8c0b8a63e5414e1f9f7c5a771b161260', 'send_file_path_', '/var/lib/emergency/send', '4ee1d6e48b31487b849a72cd03d2512a', '0.001.001', '联动发送文件地址', '0', '', '联动发送文件地址');
INSERT INTO `aos_sys_param` VALUES ('9d61164117bb4cdca3f47ff469b12c35', 'receive_file_path_', '/var/lib/emergency/receive', '4ee1d6e48b31487b849a72cd03d2512a', '0.001.001', '联动接收文件地址', '0', '', '联动接收文件地址');
INSERT INTO `aos_sys_param` VALUES ('264713ed320f4ac6a9eb4541486e9814', 'ebr_platform_id_', '', '4ee1d6e48b31487b849a72cd03d2512a', '0.001.001', '应急广播平台ID', '0', null, '应急广播平台ID');
INSERT INTO `aos_sys_param` VALUES ('ceb5bcdbdf054d838a46cfa972a73edf', 'parent_platform_url_', '', '4ee1d6e48b31487b849a72cd03d2512a', '0.001.001', '上级平台URL', '0', '', '上级平台URL');
INSERT INTO `aos_sys_param` VALUES ('666a3a3e76864629ba937de64f811ed7', 'user_login_', '2', '4ee1d6e48b31487b849a72cd03d2512a', '0.001.001', '重复登录', '0', '', '是否允许用户重复登录（1：允许 2：不允许）');
INSERT INTO `aos_sys_param` VALUES ('57282f5d5f964df2aa4d0f979b5b8579', 'ewbsserver_url', 'http://localhost:7070/ewbsserver', '4ee1d6e48b31487b849a72cd03d2512a', '0.001.001', '应急广播server地址', '0', '', '自己的应急广播server地址');
INSERT INTO `aos_sys_param`  VALUES ('a7e8a6f653d6445e93f1daba2d21720b', 'info_access_audit_', '1', '4ee1d6e48b31487b849a72cd03d2512a', '0.001.001', '信息接入审核', '0', '', '信息接入审核标识(1:自动审核；2: 手动审核)');

-- ----------------------------
-- Table structure for `aos_sys_post`
-- ----------------------------
DROP TABLE IF EXISTS `aos_sys_post`;
CREATE TABLE `aos_sys_post` (
  `id_` varchar(64) NOT NULL COMMENT ' 流水号',
  `name_` varchar(255) NOT NULL COMMENT '岗位名称',
  `status_` varchar(255) NOT NULL DEFAULT '1' COMMENT '当前状态',
  `type_` varchar(255) NOT NULL COMMENT '岗位类型',
  `org_id_` varchar(64) NOT NULL COMMENT '所属部门流水号',
  `create_time_` varchar(255) NOT NULL COMMENT '创建时间',
  `creater_id_` varchar(64) NOT NULL COMMENT '创建人ID',
  `org_cascade_id_` varchar(255) NOT NULL COMMENT '所属部门节点语义ID',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='岗位表';

-- ----------------------------
-- Records of aos_sys_post
-- ----------------------------
INSERT INTO `aos_sys_post` VALUES ('af98180624e743b6ba1051d10bee0100', 'yrdy', '1', '1', 'b2fa97f8bed34eee86201f01fa786ed4', '2016-11-30 14:34:07', 'fa04db9dd2f54d61b0c8202a25de2dc6', '0.001');

-- ----------------------------
-- Table structure for `aos_sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `aos_sys_role`;
CREATE TABLE `aos_sys_role` (
  `id_` varchar(64) NOT NULL COMMENT ' 流水号',
  `name_` varchar(255) NOT NULL COMMENT '角色名称',
  
  `status_` varchar(255) NOT NULL DEFAULT '1' COMMENT '当前状态',
  `type_` varchar(255) NOT NULL COMMENT '角色类型',
  `create_time_` varchar(255) NOT NULL COMMENT '创建时间',
  `creater_id_` varchar(64) NOT NULL COMMENT '创建人ID',
  `creater_org_id_` varchar(64) NOT NULL COMMENT '创建人所属部门流水号',
  `creater_org_cascade_id_` varchar(255) NOT NULL COMMENT '创建人所属部门节点语义ID',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of aos_sys_role
-- ----------------------------
INSERT INTO `aos_sys_role` VALUES ('5bc5263e901042aa88785f2411268426', '制播管理员', '1', '1', '2016-12-12 21:13:23', 'fa04db9dd2f54d61b0c8202a25de2dc6', '63cf387a243d4d9799367d773b853346', '0');
INSERT INTO `aos_sys_role` VALUES ('73fb7232de754987aa269eaa47d0d9d5', '调度管理员', '1', '1', '2016-12-12 21:13:58', 'fa04db9dd2f54d61b0c8202a25de2dc6', '63cf387a243d4d9799367d773b853346', '0');
INSERT INTO `aos_sys_role` VALUES ('84f923b5e334498494eda38d8e86ef80', '中控管理员', '1', '1', '2016-02-02 21:56:20', 'fa04db9dd2f54d61b0c8202a25de2dc6', '63cf387a243d4d9799367d773b853346', '0');

-- ----------------------------
-- Table structure for `aos_sys_sequence`
-- ----------------------------
DROP TABLE IF EXISTS `aos_sys_sequence`;
CREATE TABLE `aos_sys_sequence` (
  `id_` varchar(64) NOT NULL COMMENT '流水号',
  `name_` varchar(255) NOT NULL COMMENT '名称',
  `type_` varchar(255) DEFAULT NULL COMMENT '类型',
  `prefix_` varchar(255) DEFAULT NULL COMMENT '前缀',
  `start_` varchar(255) DEFAULT '1' COMMENT '起始值',
  `step_` varchar(255) DEFAULT '1' COMMENT '递增步长',
  `cur_value_` varchar(255) DEFAULT '0' COMMENT '当前值',
  `status_` varchar(255) NOT NULL DEFAULT '1' COMMENT '当前状态',
  `connector_` varchar(255) DEFAULT NULL COMMENT '连接符',
  `suffix_` varchar(255) DEFAULT NULL COMMENT '后缀',
  `db_seq_name_` varchar(255) DEFAULT NULL COMMENT 'DBSequence名称',
  `max_value_` varchar(255) DEFAULT '9223372036854775807' COMMENT '最大值',
  `is_circul_` varchar(255) DEFAULT '0' COMMENT '是否循环',
  `min_value_` varchar(255) DEFAULT '1' COMMENT '最小值',
  `is_leftpad_` varchar(255) DEFAULT '0' COMMENT '是否左补足',
  `format_value_` varchar(255) DEFAULT NULL COMMENT '当前格式化值',
  `remark_` varchar(4000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id_`),
  UNIQUE KEY `aos_sys_sequence_name_ukey` (`name_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ID配置表';

-- ----------------------------
-- Records of aos_sys_sequence
-- ----------------------------
INSERT INTO `aos_sys_sequence` VALUES ('216ebbbad70d48648e506d05c6cef713', 'EBMID', '1', null, '1', '1', '0060', '1', null, null, null, '9999', '1', '1', '1', '0060', 'EBM消息后四位顺序码');
INSERT INTO `aos_sys_sequence` VALUES ('603229e2988d48958cd04caf598cbe40', 'EBDID', '1', null, '1', '1', '0000000000083217', '1', null, null, null, '9999999999999999', '1', '0', '1', '0000000000083217', null);
INSERT INTO `aos_sys_sequence` VALUES ('7487db32b061465da1af721e84e9942a', 'GUUID', '2', null, '1', '1', 'd7123d2c8af04dafab5d37d6d03937bb', '1', null, null, null, '9223372036854775807', '0', '1', '0', 'd7123d2c8af04dafab5d37d6d03937bb', '通用UUID。');

-- ----------------------------
-- Table structure for `aos_sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `aos_sys_user`;
CREATE TABLE `aos_sys_user` (
  `id_` varchar(64) NOT NULL COMMENT '流水号',
  `account_` varchar(255) NOT NULL COMMENT '用户登录帐号',
  `password_` varchar(255) NOT NULL COMMENT '密码',
  `name_` varchar(255) NOT NULL COMMENT '用户姓名',
  `sex_` varchar(255) NOT NULL DEFAULT '0' COMMENT '性别',
  `org_id_` varchar(64) NOT NULL COMMENT '所属主部门流水号',
  `status_` varchar(255) NOT NULL COMMENT '用户状态',
  `type_` varchar(255) NOT NULL COMMENT '用户类型',
  `biz_code_` varchar(255) DEFAULT NULL COMMENT '业务对照码',
  `create_time_` varchar(255) NOT NULL COMMENT ' 经办时间',
  `creater_id_` varchar(64) NOT NULL COMMENT '经办人流水号',
  `org_cascade_id_` varchar(255) NOT NULL COMMENT '所属部门节点语义ID',
  `delete_flag_` varchar(255) NOT NULL COMMENT '逻辑删除标识',
  `biz_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户基本信息表';

-- ----------------------------
-- Records of aos_sys_user
-- ----------------------------
INSERT INTO `aos_sys_user` VALUES ('31ae374673c84f5482182806ac2bd23b', 'caiyujun', 'w0b3xIFLj1u61k0EfIqwIA==', '蔡宇军', '1', '4be918af97ee491aa71378d065a8c7f7', '1', '1', '', '2016-12-17 16:48:47', 'fa04db9dd2f54d61b0c8202a25de2dc6', '0.006.002', '0', null);
INSERT INTO `aos_sys_user` VALUES ('5c9846f8ef7f47c5a16305160532b757', 'admin', 'x+LXkEOu/0c=', 'admin', '1', '63cf387a243d4d9799367d773b853346', '1', '1', null, '2016-12-12 21:20:43', 'fa04db9dd2f54d61b0c8202a25de2dc6', '0', '0', null);
INSERT INTO `aos_sys_user` VALUES ('a069a8dd57cc4f1fafa408281b2c1660', 'chengyu', 'cmzfhSe3xss=', '程煜', '1', '0965ef0ffd294a838f53012e3ef9bfd0', '1', '1', '', '2016-12-17 16:47:56', 'fa04db9dd2f54d61b0c8202a25de2dc6', '0.006.001', '0', null);
INSERT INTO `aos_sys_user` VALUES ('ace5e18c7bd34c88885f62cf5538712b', 'zhucanhui', 'EffPtixplPo=', '朱灿辉', '1', '0965ef0ffd294a838f53012e3ef9bfd0', '1', '1', '', '2016-06-13 15:59:15', 'fa04db9dd2f54d61b0c8202a25de2dc6', '0.006.001', '0', null);
INSERT INTO `aos_sys_user` VALUES ('fa04db9dd2f54d61b0c8202a25de2dc6', 'root', 'OaQR5PVWJjVuIK9vBWRSVw==', '系统管理员', '1', '63cf387a243d4d9799367d773b853346', '1', '2', '', '2014-09-27 22:12:56', 'fa04db9dd2f54d61b0c8202a25de2dc6', '0', '0', null);
INSERT INTO `aos_sys_user` VALUES ('fe24c4ac34444902a2129ec1debf9ed4', 'caocao', 'EffPtixplPo=', '曹操', '3', 'b2fa97f8bed34eee86201f01fa786ed4', '1', '1', '', '2016-02-02 21:55:57', 'fa04db9dd2f54d61b0c8202a25de2dc6', '0.001', '1', null);

-- ----------------------------
-- Table structure for `aos_sys_user_cfg`
-- ----------------------------
DROP TABLE IF EXISTS `aos_sys_user_cfg`;
CREATE TABLE `aos_sys_user_cfg` (
  `id_` varchar(64) NOT NULL COMMENT '用户ID',
  `theme_` varchar(255) DEFAULT NULL COMMENT '用户界面主题',
  `skin_` varchar(255) DEFAULT NULL COMMENT '用户界面皮肤',
  `is_show_top_nav_` varchar(255) DEFAULT NULL COMMENT '是否显示水平导航条',
  `navbar_btn_style_` varchar(255) DEFAULT NULL COMMENT '导航条按钮风格',
  `tab_focus_color_` varchar(255) DEFAULT NULL COMMENT 'Tab高亮颜色',
  `nav_tab_index_` varchar(255) DEFAULT NULL COMMENT '导航缺省活动页',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户配置表';

-- ----------------------------
-- Records of aos_sys_user_cfg
-- ----------------------------
INSERT INTO `aos_sys_user_cfg` VALUES ('31ae374673c84f5482182806ac2bd23b', 'classic', 'neptune', 'true', 'tight', '#0099FF', '0');
INSERT INTO `aos_sys_user_cfg` VALUES ('5c9846f8ef7f47c5a16305160532b757', 'classic', 'neptune', 'true', 'tight', '#0099FF', '0');
INSERT INTO `aos_sys_user_cfg` VALUES ('a069a8dd57cc4f1fafa408281b2c1660', 'classic', 'neptune', 'true', 'tight', '#0099FF', '0');
INSERT INTO `aos_sys_user_cfg` VALUES ('ace5e18c7bd34c88885f62cf5538712b', 'classic', 'neptune', 'true', 'tight', '#0099FF', '1');
INSERT INTO `aos_sys_user_cfg` VALUES ('f04f7d87958a4d53aaaca70c8a3fdafe', 'classic', 'blue', 'true', 'tight', '#0099FF', '0');
INSERT INTO `aos_sys_user_cfg` VALUES ('fa04db9dd2f54d61b0c8202a25de2dc6', 'classic', 'neptune', 'true', 'tight', '#0099FF', '1');
INSERT INTO `aos_sys_user_cfg` VALUES ('fe24c4ac34444902a2129ec1debf9ed4', 'classic', 'blue', 'true', 'tight', '#0099FF', '0');

-- ----------------------------
-- Table structure for `aos_sys_user_ext`
-- ----------------------------
DROP TABLE IF EXISTS `aos_sys_user_ext`;
CREATE TABLE `aos_sys_user_ext` (
  `id_` varchar(64) NOT NULL COMMENT '用户ID',
  `email_` varchar(255) DEFAULT NULL COMMENT '电子邮件',
  `fixed_phone_` varchar(255) DEFAULT NULL COMMENT '固定电话',
  `mobile_phone_` varchar(255) DEFAULT NULL COMMENT '移动电话',
  `address_` varchar(255) DEFAULT NULL COMMENT '联系地址',
  `zip_` varchar(255) DEFAULT NULL COMMENT '邮编',
  `birthday_` varchar(255) DEFAULT NULL COMMENT '生日',
  `idno_` varchar(255) DEFAULT NULL COMMENT '身份证号',
  `qq_` varchar(255) DEFAULT NULL COMMENT 'QQ',
  `dynamic_field_` varchar(4000) DEFAULT NULL COMMENT '动态扩展字段',
  `remark_` varchar(4000) DEFAULT NULL COMMENT '备注',
  `filed1_` varchar(255) DEFAULT NULL COMMENT '静态扩展字段1',
  `filed2_` varchar(255) DEFAULT NULL COMMENT '静态扩展字段2',
  `filed3_` varchar(255) DEFAULT NULL COMMENT '静态扩展字段3',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户扩展信息表';

-- ----------------------------
-- Records of aos_sys_user_ext
-- ----------------------------
INSERT INTO `aos_sys_user_ext` VALUES ('31ae374673c84f5482182806ac2bd23b', '', '', '', '', '', '', '', '', '', '', '', '', null);
INSERT INTO `aos_sys_user_ext` VALUES ('5c9846f8ef7f47c5a16305160532b757', null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `aos_sys_user_ext` VALUES ('a069a8dd57cc4f1fafa408281b2c1660', '', '', '', '', '', '', '', '', '', '', '', '', null);
INSERT INTO `aos_sys_user_ext` VALUES ('ace5e18c7bd34c88885f62cf5538712b', '', '', '', '', '', '', '', '', '', '', '', '', null);
INSERT INTO `aos_sys_user_ext` VALUES ('f04f7d87958a4d53aaaca70c8a3fdafe', null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `aos_sys_user_ext` VALUES ('fa04db9dd2f54d61b0c8202a25de2dc6', 'zhucanhui@comtom.cn', '', '18973151562', '', '', '', '', '455941783', '', '超级用户拥有系统最高权限。', '', '', null);
INSERT INTO `aos_sys_user_ext` VALUES ('fe24c4ac34444902a2129ec1debf9ed4', '', '', '', '', '', '', '', '', '', '', '', '', null);

-- ----------------------------
-- Table structure for `aos_sys_user_post`
-- ----------------------------
DROP TABLE IF EXISTS `aos_sys_user_post`;
CREATE TABLE `aos_sys_user_post` (
  `id_` varchar(64) NOT NULL COMMENT '流水号',
  `user_id_` varchar(64) NOT NULL COMMENT '用户流水号',
  `post_id_` varchar(64) NOT NULL COMMENT '岗位流水号',
  `operate_time_` varchar(255) NOT NULL COMMENT '授权时间',
  `operator_id_` varchar(64) NOT NULL COMMENT '授权人流水号',
  PRIMARY KEY (`id_`),
  UNIQUE KEY `aos_sys_user_post_ukey` (`user_id_`,`post_id_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户-岗位关联表';

-- ----------------------------
-- Records of aos_sys_user_post
-- ----------------------------

-- ----------------------------
-- Table structure for `aos_sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `aos_sys_user_role`;
CREATE TABLE `aos_sys_user_role` (
  `id_` varchar(64) NOT NULL COMMENT '流水号',
  `user_id_` varchar(64) NOT NULL COMMENT '用户流水号',
  `role_id_` varchar(64) NOT NULL COMMENT '角色流水号',
  `operate_time_` varchar(255) NOT NULL COMMENT '授权时间',
  `operator_id_` varchar(64) NOT NULL COMMENT '授权人流水号',
  PRIMARY KEY (`id_`),
  UNIQUE KEY `aos_sys_user_role_ukey` (`user_id_`,`role_id_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户-角色关联表';

-- ----------------------------
-- Records of aos_sys_user_role
-- ----------------------------
INSERT INTO `aos_sys_user_role` VALUES ('0c2d0b53a40f41aaa60b008b850b1016', '31ae374673c84f5482182806ac2bd23b', '73fb7232de754987aa269eaa47d0d9d5', '2016-12-17 16:49:48', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_user_role` VALUES ('5a77de4588b54b81a5a84ad3de00f603', '5c9846f8ef7f47c5a16305160532b757', '73fb7232de754987aa269eaa47d0d9d5', '2016-12-12 21:20:52', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_user_role` VALUES ('86d4ebbde4a9452b807bfbf5764190a3', '5c9846f8ef7f47c5a16305160532b757', '84f923b5e334498494eda38d8e86ef80', '2016-12-12 21:20:52', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_user_role` VALUES ('92bf64a115834ddeba1dc715a59d7138', 'a069a8dd57cc4f1fafa408281b2c1660', '73fb7232de754987aa269eaa47d0d9d5', '2016-12-17 16:49:27', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_user_role` VALUES ('96b6ec9bbe4e4b41875ebd6e289449b8', 'a069a8dd57cc4f1fafa408281b2c1660', '5bc5263e901042aa88785f2411268426', '2016-12-17 16:49:27', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_user_role` VALUES ('b61b02108a2c4059aa6cc9d48721e517', '31ae374673c84f5482182806ac2bd23b', '5bc5263e901042aa88785f2411268426', '2016-12-17 16:49:48', 'fa04db9dd2f54d61b0c8202a25de2dc6');
INSERT INTO `aos_sys_user_role` VALUES ('dbbc1b2c54304efa82e5f0a7136ac7fb', '5c9846f8ef7f47c5a16305160532b757', '5bc5263e901042aa88785f2411268426', '2016-12-12 21:20:52', 'fa04db9dd2f54d61b0c8202a25de2dc6');

-- ----------------------------
-- Table structure for `bc_dispatch_flow`
-- ----------------------------
DROP TABLE IF EXISTS `bc_dispatch_flow`;
CREATE TABLE `bc_dispatch_flow` (
  `flowId` int(20) NOT NULL AUTO_INCREMENT COMMENT '流程（会话）编号',
  `flowStage` tinyint(4) DEFAULT '1' COMMENT '流程阶段（1:预警触发2:预警响应3:预警处理4:预警完成）',
  `flowState` tinyint(4) DEFAULT NULL COMMENT '流程状态',
  `relatedProgramId` int(20) DEFAULT NULL COMMENT '关联节目编号',
  `relatedEbdId` varchar(64) DEFAULT NULL COMMENT '关联业务数据包编号',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`flowId`)
) ENGINE=InnoDB AUTO_INCREMENT=182 DEFAULT CHARSET=utf8 COMMENT='调度流程（会话）表';

-- ----------------------------
-- Table structure for `bc_ebd`
-- ----------------------------
DROP TABLE IF EXISTS `bc_ebd`;
CREATE TABLE `bc_ebd` (
  `ebdId` varchar(64) NOT NULL COMMENT '业务数据包Id',
  `ebdVersion` varchar(10) DEFAULT NULL COMMENT '数据包版本',
  `ebdType` varchar(20) DEFAULT NULL COMMENT '业务数据类型',
  `edbName` varchar(100) DEFAULT NULL COMMENT '数据包名称',
  `ebdSrcEbrId` varchar(64) DEFAULT NULL COMMENT '来源对象资源Id',
  `ebdDestEbrId` varchar(64) DEFAULT NULL COMMENT '目标对象资源Id',
  `ebdTime` datetime DEFAULT NULL COMMENT '生成时间',
  `relateEbdId` varchar(64) DEFAULT NULL COMMENT '关联业务数据包Id',
  `ebdSrcUrl` varchar(100) DEFAULT NULL COMMENT '业务数据包来源对象URL',
  `sendFlag` tinyint(1) DEFAULT NULL COMMENT '业务数据包标识（1：接收 2：发送）',
  `ebdState` tinyint(1) DEFAULT NULL COMMENT '业务数据包状态(1:创建 2:发送成功 3:发送失败 4:取消)',
  `ebdRecvTime` datetime DEFAULT NULL COMMENT '业务数据包接收时间',
  `ebdSendTime` datetime DEFAULT NULL COMMENT '业务数据包发送时间',
  `flowId` varchar(64) DEFAULT NULL COMMENT '关联会话Id',
  `ebmId` varchar(64) DEFAULT NULL COMMENT '关联EBM消息Id',
  PRIMARY KEY (`ebdId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务数据包信息表';

-- ----------------------------
-- Table structure for `bc_ebd_files`
-- ----------------------------
DROP TABLE IF EXISTS `bc_ebd_files`;
CREATE TABLE `bc_ebd_files` (
  `fileId` int(20) NOT NULL AUTO_INCREMENT COMMENT '文件Id',
  `ebdId` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '关联数据包Id',
  `fileUrl` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '文件URL',
  PRIMARY KEY (`fileId`)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='EBD数据包关联文件表';

-- ----------------------------
-- Table structure for `bc_ebd_response`
-- ----------------------------
DROP TABLE IF EXISTS `bc_ebd_response`;
CREATE TABLE `bc_ebd_response` (
  `ebdId` varchar(64) NOT NULL COMMENT '业务数据包Id',
  `ebdVersion` varchar(10) DEFAULT NULL COMMENT '业务数据包版本',
  `ebdType` varchar(20) DEFAULT 'EBDResponse' COMMENT '业务数据类型',
  `ebdSrcEbrId` varchar(64) DEFAULT NULL COMMENT '来源对象资源Id',
  `ebdTime` datetime DEFAULT NULL COMMENT '业务数据包生成时间',
  `relatedEbdId` varchar(64) DEFAULT NULL COMMENT '关联业务数据包编号',
  `resultCode` tinyint(4) DEFAULT NULL COMMENT '状态码',
  `resultDesc` varchar(100) DEFAULT NULL COMMENT '状态描述',
  `sendFlag` tinyint(4) DEFAULT NULL COMMENT '业务数据包标识（1：接收 2：发送）',
  PRIMARY KEY (`ebdId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务数据包通用反馈表';

-- ----------------------------
-- Table structure for `bc_ebm`
-- ----------------------------
DROP TABLE IF EXISTS `bc_ebm`;
CREATE TABLE `bc_ebm` (
  `ebmId` varchar(64) NOT NULL COMMENT '广播消息Id',
  `ebmVersion` varchar(10) DEFAULT NULL COMMENT '广播消息协议版本号',
  `relatedEbmId` varchar(64) DEFAULT NULL COMMENT '关联广播消息Id',
  `relatedEbIId` varchar(64) DEFAULT NULL COMMENT '关联广播信息Id',
  `msgType` int(8) DEFAULT NULL COMMENT '消息类型',
  `sendName` varchar(100) DEFAULT NULL COMMENT '发布机构名称',
  `senderCode` varchar(50) DEFAULT NULL COMMENT '发布机构编码',
  `sendTime` datetime DEFAULT NULL COMMENT '发布时间',
  `eventType` varchar(10) DEFAULT NULL COMMENT '事件类型编码',
  `severity` tinyint(4) DEFAULT NULL COMMENT '事件级别',
  `startTime` datetime DEFAULT NULL COMMENT '播发开始时间',
  `endTime` datetime DEFAULT NULL COMMENT '播发结束时间',
  `msgLanguageCode` varchar(10) DEFAULT NULL COMMENT '语种代码',
  `msgTitle` varchar(100) DEFAULT NULL COMMENT '消息标题文本',
  `msgDesc` varchar(500) DEFAULT NULL COMMENT '消息内容文本',
  `areaCode` varchar(2000) DEFAULT NULL COMMENT '覆盖区域编码',
  `programNum` int(20) DEFAULT NULL COMMENT '参考业务节目Id',
  `flowId` varchar(64) DEFAULT NULL COMMENT '关联会话Id',
  `ebmState` tinyint(4) DEFAULT NULL COMMENT '消息状态(1:创建 2:发送成功 3:发送失败 4:取消)',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `schemeId` int(20) DEFAULT NULL COMMENT '关联调度方案Id',
  `sendFlag` tinyint(4) DEFAULT NULL COMMENT '发送标志（1：接收 2：发送）',
  `timeOut`  tinyint(4) NULL DEFAULT '1' COMMENT '用于标记该条消息是否超过开始时间(1: 未超过；2：已超过;)',
  PRIMARY KEY (`ebmId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应急广播消息表';

-- ----------------------------
-- Table structure for `bc_ebm_auxiliary`
-- ----------------------------
DROP TABLE IF EXISTS `bc_ebm_auxiliary`;
CREATE TABLE `bc_ebm_auxiliary` (
  `auxiliaryId` int(20) NOT NULL AUTO_INCREMENT,
  `auxiliaryType` int(10) DEFAULT NULL COMMENT '辅助数据类型',
  `auxiliaryDesc` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '辅助数据描述',
  `auxiliarySize` int(10) DEFAULT NULL COMMENT '辅助数据文件大小',
  `auxiliaryDigest` varchar(300) COLLATE utf8_bin DEFAULT NULL COMMENT '辅助数据文件摘要',
  `ebmId` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '消息ID',
  PRIMARY KEY (`auxiliaryId`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='应急消息辅助数据';

-- ----------------------------
-- Table structure for `bc_ebm_brd_item_unit`
-- ----------------------------
DROP TABLE IF EXISTS `bc_ebm_brd_item_unit`;
CREATE TABLE `bc_ebm_brd_item_unit` (
  `brdItemId` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '播发记录ID',
  `unitId` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '部门ID',
  `unitName` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '部门名称',
  `persionId` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '播放人员ID',
  `persionName` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '播放人员名称',
  `ebrpsId` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '应急广播平台ID',
  PRIMARY KEY (`brdItemId`,`unitId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='播发记录的播放部门人员信息';

-- ----------------------------
-- Records of bc_ebm_brd_item_unit
-- ----------------------------

-- ----------------------------
-- Table structure for `bc_ebm_brd_record`
-- ----------------------------
DROP TABLE IF EXISTS `bc_ebm_brd_record`;
CREATE TABLE `bc_ebm_brd_record` (
  `brdItemId` varchar(64) NOT NULL COMMENT '播发记录条目ID',
  `resourceId` varchar(255) NOT NULL COMMENT '信息上报资源的ID',
  `ebmId` varchar(64) NOT NULL COMMENT '消息ID',
  `msgType` tinyint(4) DEFAULT NULL COMMENT '消息类型',
  `sendName` varchar(100) DEFAULT NULL COMMENT '发布机构名称',
  `senderCode` varchar(50) DEFAULT NULL COMMENT '发布机构编码',
  `sendTime` datetime DEFAULT NULL COMMENT '发布时间',
  `eventType` varchar(10) DEFAULT NULL COMMENT '事件类型编码',
  `severity` tinyint(4) DEFAULT NULL COMMENT '事件级别',
  `startTime` datetime DEFAULT NULL COMMENT '播发开始时间',
  `endTime` datetime DEFAULT NULL COMMENT '播发结束时间',
  `languageCode` varchar(10) DEFAULT NULL COMMENT '语种代码',
  `msgTitle` varchar(100) DEFAULT NULL COMMENT '消息标题文本',
  `msgDesc` varchar(1000) DEFAULT NULL COMMENT '消息内容文本',
  `areaCode` varchar(2000) DEFAULT NULL COMMENT '覆盖区域编码',
  `programNum` int(11) DEFAULT NULL COMMENT '参考业务节目号',
  `brdStateCode` tinyint(4) DEFAULT NULL COMMENT '播发状态码',
  `brdStateDesc` varchar(255) DEFAULT NULL COMMENT '播发状态描述',
  `coveragePercent` double(8,2) DEFAULT NULL COMMENT '覆盖率',
  `coverageAreaCode` varchar(255) DEFAULT NULL,
   `updateTime`  datetime NULL COMMENT '更新时间',
   `syncFlag`  tinyint(4)  DEFAULT '1' COMMENT '记录是否已同步标识（1：未同步 2：已同步）',
  PRIMARY KEY (`brdItemId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='播发记录表';

-- ----------------------------
-- Table structure for `bc_ebm_dispatch`
-- ----------------------------
DROP TABLE IF EXISTS `bc_ebm_dispatch`;
CREATE TABLE `bc_ebm_dispatch` (
  `dispatchId` int(11) NOT NULL AUTO_INCREMENT COMMENT '调用资源数据Id',
  `languageCode` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '语种代码',
  `psEbrId` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '应急广播平台Id',
  `bsEbrId` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '播出系统Id',
  `brdSysType` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '播出系统类型',
  `brdSysInfo` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '播出系统信息',
  `ebdId` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '数据包Id',
  `ebmId` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '关联EBM',
  `state` tinyint(4) DEFAULT NULL COMMENT '调度分发状态（0:待调度 1:已调度）',
  `dispatchTime` datetime DEFAULT NULL COMMENT '调度分发时间',
  PRIMARY KEY (`dispatchId`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='应急消息资源调用';

-- ----------------------------
-- Table structure for `bc_ebm_event_type`
-- ----------------------------
DROP TABLE IF EXISTS `bc_ebm_event_type`;
CREATE TABLE `bc_ebm_event_type` (
  `eventCode` varchar(5) NOT NULL DEFAULT '' COMMENT '事件编码',
  `eventDesc` varchar(255) NOT NULL COMMENT '事件描述',
  `isLeaf` varchar(1) NOT NULL COMMENT '是否叶子节点（0不是1是）',
  `parentCode` varchar(5) NOT NULL COMMENT '父类编码',
  PRIMARY KEY (`eventCode`),
  KEY `eventCode` (`eventCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应急广播事件';

-- ----------------------------
-- Records of bc_ebm_event_type
-- ----------------------------
INSERT INTO `bc_ebm_event_type` VALUES ('00000', '测试专用', '1', '0');
INSERT INTO `bc_ebm_event_type` VALUES ('10000', '突发事件', '0', '0');
INSERT INTO `bc_ebm_event_type` VALUES ('11000', '自然灾害', '0', '10000');
INSERT INTO `bc_ebm_event_type` VALUES ('11A00', '水旱灾害', '0', '11000');
INSERT INTO `bc_ebm_event_type` VALUES ('11A01', '洪水', '1', '11A00');
INSERT INTO `bc_ebm_event_type` VALUES ('11A02', '内涝', '1', '11A00');
INSERT INTO `bc_ebm_event_type` VALUES ('11A03', '水库重大险情', '1', '11A00');
INSERT INTO `bc_ebm_event_type` VALUES ('11A04', '堤防重大险情', '1', '11A00');
INSERT INTO `bc_ebm_event_type` VALUES ('11A05', '凌汛灾害', '1', '11A00');
INSERT INTO `bc_ebm_event_type` VALUES ('11A10', '农村人畜饮水困难', '1', '11A00');
INSERT INTO `bc_ebm_event_type` VALUES ('11A51', '山洪灾害事件', '1', '11A00');
INSERT INTO `bc_ebm_event_type` VALUES ('11A52', '农业干旱', '1', '11A00');
INSERT INTO `bc_ebm_event_type` VALUES ('11A53', '城镇缺水', '1', '11A00');
INSERT INTO `bc_ebm_event_type` VALUES ('11A54', '生态干旱', '1', '11A00');
INSERT INTO `bc_ebm_event_type` VALUES ('11A99', '其它水旱灾害', '1', '11A00');
INSERT INTO `bc_ebm_event_type` VALUES ('11B00', '气象灾害', '0', '11000');
INSERT INTO `bc_ebm_event_type` VALUES ('11B01', '台风事件', '1', '11B00');
INSERT INTO `bc_ebm_event_type` VALUES ('11B02', '龙卷风事件', '1', '11B00');
INSERT INTO `bc_ebm_event_type` VALUES ('11B03', '暴雨事件', '1', '11B00');
INSERT INTO `bc_ebm_event_type` VALUES ('11B04', '暴雪事件', '1', '11B00');
INSERT INTO `bc_ebm_event_type` VALUES ('11B05', '寒潮事件', '1', '11B00');
INSERT INTO `bc_ebm_event_type` VALUES ('11B06', '大风事件', '1', '11B00');
INSERT INTO `bc_ebm_event_type` VALUES ('11B07', '沙尘暴事件', '1', '11B00');
INSERT INTO `bc_ebm_event_type` VALUES ('11B08', '低温冻害事件', '1', '11B00');
INSERT INTO `bc_ebm_event_type` VALUES ('11B09', '巾高温事件', '1', '11B00');
INSERT INTO `bc_ebm_event_type` VALUES ('11B10', '热浪事件', '1', '11B00');
INSERT INTO `bc_ebm_event_type` VALUES ('11B11', '干热风', '1', '11B00');
INSERT INTO `bc_ebm_event_type` VALUES ('11B12', '下击暴流事件', '1', '11B00');
INSERT INTO `bc_ebm_event_type` VALUES ('11B13', '雪崩事件', '1', '11B00');
INSERT INTO `bc_ebm_event_type` VALUES ('11B14', '雷电事件', '1', '11B00');
INSERT INTO `bc_ebm_event_type` VALUES ('11B15', '冰雹事件', '1', '11B00');
INSERT INTO `bc_ebm_event_type` VALUES ('11B16', '霜冻事件', '1', '11B00');
INSERT INTO `bc_ebm_event_type` VALUES ('11B17', '大雾事件', '1', '11B00');
INSERT INTO `bc_ebm_event_type` VALUES ('11B18', '低空风切变事件', '1', '11B00');
INSERT INTO `bc_ebm_event_type` VALUES ('11B99', '其它气象灾害事件', '1', '11B00');
INSERT INTO `bc_ebm_event_type` VALUES ('11C00', '地震灾害', '0', '11000');
INSERT INTO `bc_ebm_event_type` VALUES ('11C01', '人工地震事件', '1', '11C00');
INSERT INTO `bc_ebm_event_type` VALUES ('11C02', '天然地震事件', '1', '11C00');
INSERT INTO `bc_ebm_event_type` VALUES ('11C99', '其它地震灾害', '1', '11C00');
INSERT INTO `bc_ebm_event_type` VALUES ('11D00', '地质灾害', '0', '11000');
INSERT INTO `bc_ebm_event_type` VALUES ('11D01', '滑坡事件', '1', '11D00');
INSERT INTO `bc_ebm_event_type` VALUES ('11D02', '泥石流事件', '1', '11D00');
INSERT INTO `bc_ebm_event_type` VALUES ('11D03', '山体崩塌事件', '1', '11D00');
INSERT INTO `bc_ebm_event_type` VALUES ('11D04', '地面塌陷事件', '1', '11D00');
INSERT INTO `bc_ebm_event_type` VALUES ('11D05', '地裂缝事件', '1', '11D00');
INSERT INTO `bc_ebm_event_type` VALUES ('11D06', '地面沉降事件', '1', '11D00');
INSERT INTO `bc_ebm_event_type` VALUES ('11D07', '火山喷发事件', '1', '11D00');
INSERT INTO `bc_ebm_event_type` VALUES ('11D99', '其它地质灾害事件', '1', '11D00');
INSERT INTO `bc_ebm_event_type` VALUES ('11E00', '海洋灾害事件', '0', '11000');
INSERT INTO `bc_ebm_event_type` VALUES ('11E01', '海啸事件', '1', '11E00');
INSERT INTO `bc_ebm_event_type` VALUES ('11E02', '风暴潮事件', '1', '11E00');
INSERT INTO `bc_ebm_event_type` VALUES ('11E03', '海冰事件', '1', '11E00');
INSERT INTO `bc_ebm_event_type` VALUES ('11E04', '巨浪事件', '1', '11E00');
INSERT INTO `bc_ebm_event_type` VALUES ('11E05', '赤潮事件', '1', '11E00');
INSERT INTO `bc_ebm_event_type` VALUES ('11E99', '其它海洋灾害事件', '1', '11E00');
INSERT INTO `bc_ebm_event_type` VALUES ('11F00', '生物灾害事件', '0', '11000');
INSERT INTO `bc_ebm_event_type` VALUES ('11F01', '农业病害事件', '1', '11F00');
INSERT INTO `bc_ebm_event_type` VALUES ('11F02', '农业虫害事件', '1', '11F00');
INSERT INTO `bc_ebm_event_type` VALUES ('11F03', '农业草害事件', '1', '11F00');
INSERT INTO `bc_ebm_event_type` VALUES ('11F04', '农业鼠害事件', '1', '11F00');
INSERT INTO `bc_ebm_event_type` VALUES ('11F05', '森林病害事件', '1', '11F00');
INSERT INTO `bc_ebm_event_type` VALUES ('11F06', '森林虫害事件', '1', '11F00');
INSERT INTO `bc_ebm_event_type` VALUES ('11F07', '森林鼠害事件', '1', '11F00');
INSERT INTO `bc_ebm_event_type` VALUES ('11F08', '农业转基因生物安全突发事件', '1', '11F00');
INSERT INTO `bc_ebm_event_type` VALUES ('11F09', '林业转基因生物安全突发事件', '1', '11F00');
INSERT INTO `bc_ebm_event_type` VALUES ('11F10', '林业有害植物事件', '1', '11F00');
INSERT INTO `bc_ebm_event_type` VALUES ('11F11', '外来有害动植物威胁农业生产事件', '1', '11F00');
INSERT INTO `bc_ebm_event_type` VALUES ('11F12', '外来有害动植物威胁林业生产事件', '1', '11F00');
INSERT INTO `bc_ebm_event_type` VALUES ('11F99', '其它生物灾害', '1', '11F00');
INSERT INTO `bc_ebm_event_type` VALUES ('11G00', '森林草原火灾', '0', '11000');
INSERT INTO `bc_ebm_event_type` VALUES ('11G01', '境内森林火灾事件', '1', '11G00');
INSERT INTO `bc_ebm_event_type` VALUES ('11G02', '跨境森林火灾事件', '1', '11G00');
INSERT INTO `bc_ebm_event_type` VALUES ('11G03', '境外威胁我国境内的森林火灾', '1', '11G00');
INSERT INTO `bc_ebm_event_type` VALUES ('11G04', '其他森林火灾事件', '1', '11G00');
INSERT INTO `bc_ebm_event_type` VALUES ('11G05', '境内草原火灾事件', '1', '11G00');
INSERT INTO `bc_ebm_event_type` VALUES ('11G06', '跨境草原火灾事件', '1', '11G00');
INSERT INTO `bc_ebm_event_type` VALUES ('11G07', '境外威胁我国境内的草原火灾', '1', '11G00');
INSERT INTO `bc_ebm_event_type` VALUES ('11G08', '其他草原火灾事件', '1', '11G00');
INSERT INTO `bc_ebm_event_type` VALUES ('11Y00', '其他自然灾害事件', '0', '11000');
INSERT INTO `bc_ebm_event_type` VALUES ('12000', '事故灾难', '0', '10000');
INSERT INTO `bc_ebm_event_type` VALUES ('12099', '其它火灾事故', '1', '12000');
INSERT INTO `bc_ebm_event_type` VALUES ('12800', '踩踏事件', '0', '12000');
INSERT INTO `bc_ebm_event_type` VALUES ('12801', '公园组织大型群众性活动或聚会踩踏事件', '1', '12800');
INSERT INTO `bc_ebm_event_type` VALUES ('12802', '校园踩踏事件', '1', '12800');
INSERT INTO `bc_ebm_event_type` VALUES ('12899', '其他踩踏事件', '1', '12800');
INSERT INTO `bc_ebm_event_type` VALUES ('12A00', '煤矿事故', '0', '12000');
INSERT INTO `bc_ebm_event_type` VALUES ('12A01', '煤矿瓦斯事故', '1', '12A00');
INSERT INTO `bc_ebm_event_type` VALUES ('12A02', '煤矿顶板事故', '1', '12A00');
INSERT INTO `bc_ebm_event_type` VALUES ('12A03', '煤矿运输事故', '1', '12A00');
INSERT INTO `bc_ebm_event_type` VALUES ('12A04', '煤矿水害事故', '1', '12A00');
INSERT INTO `bc_ebm_event_type` VALUES ('12A05', '煤矿机电事故', '1', '12A00');
INSERT INTO `bc_ebm_event_type` VALUES ('12A06', '煤矿放炮事故', '1', '12A00');
INSERT INTO `bc_ebm_event_type` VALUES ('12A07', '煤矿火灾事故', '1', '12A00');
INSERT INTO `bc_ebm_event_type` VALUES ('12A99', '煤矿其他事故', '1', '12A00');
INSERT INTO `bc_ebm_event_type` VALUES ('12B00', '金属与非金属矿山事故', '0', '12000');
INSERT INTO `bc_ebm_event_type` VALUES ('12B01', '金属与非金属矿顶板事故', '1', '12B00');
INSERT INTO `bc_ebm_event_type` VALUES ('12B02', '金属与非金属矿水害事故', '1', '12B00');
INSERT INTO `bc_ebm_event_type` VALUES ('12B03', '金属与非金属矿中毒和窒息事故', '1', '12B00');
INSERT INTO `bc_ebm_event_type` VALUES ('12B04', '金属与非金属矿尾矿库垮坝事故', '1', '12B00');
INSERT INTO `bc_ebm_event_type` VALUES ('12B05', '金属与非金属矿火灾事故', '1', '12B00');
INSERT INTO `bc_ebm_event_type` VALUES ('12B06', '金属与非金属矿机电事故', '1', '12B00');
INSERT INTO `bc_ebm_event_type` VALUES ('12B07', '金属与非金属矿运输事故', '1', '12B00');
INSERT INTO `bc_ebm_event_type` VALUES ('12B08', '金属与非金属矿放炮事故', '1', '12B00');
INSERT INTO `bc_ebm_event_type` VALUES ('12B09', '金属与非金属矿火药爆炸事故', '1', '12B00');
INSERT INTO `bc_ebm_event_type` VALUES ('12B99', '金属与非金属矿山其他事故', '1', '12B00');
INSERT INTO `bc_ebm_event_type` VALUES ('12C00', '建筑业事故', '0', '12000');
INSERT INTO `bc_ebm_event_type` VALUES ('12C01', '房屋建筑与市政工程施工安全事故', '1', '12C00');
INSERT INTO `bc_ebm_event_type` VALUES ('12C02', '其他建筑施工安全事故', '1', '12C00');
INSERT INTO `bc_ebm_event_type` VALUES ('12D00', '危险化学品事故', '0', '12000');
INSERT INTO `bc_ebm_event_type` VALUES ('12D01', '危险化学品爆炸事故', '1', '12D00');
INSERT INTO `bc_ebm_event_type` VALUES ('12D02', '危险化学品泄漏事故', '1', '12D00');
INSERT INTO `bc_ebm_event_type` VALUES ('12D03', '危险化学品火灾事故', '1', '12D00');
INSERT INTO `bc_ebm_event_type` VALUES ('12D04', '危险化学品中毒和窒息事故', '1', '12D00');
INSERT INTO `bc_ebm_event_type` VALUES ('12D05', '危险化学品灼烫事故', '1', '12D00');
INSERT INTO `bc_ebm_event_type` VALUES ('12D99', '危险化学品其他事故', '1', '12D00');
INSERT INTO `bc_ebm_event_type` VALUES ('12E00', '烟花爆竹和民用爆炸物事故', '0', '12000');
INSERT INTO `bc_ebm_event_type` VALUES ('12E01', '烟花爆竹生产企业爆炸事故', '1', '12E00');
INSERT INTO `bc_ebm_event_type` VALUES ('12E02', '烟花爆竹运输爆炸事故', '1', '12E00');
INSERT INTO `bc_ebm_event_type` VALUES ('12E03', '民用爆炸物爆炸事故', '1', '12E00');
INSERT INTO `bc_ebm_event_type` VALUES ('12E99', '其他烟花爆竹事故', '1', '12E00');
INSERT INTO `bc_ebm_event_type` VALUES ('12F00', '其他工矿商贸事故', '0', '12000');
INSERT INTO `bc_ebm_event_type` VALUES ('12G00', '火灾事故', '0', '12000');
INSERT INTO `bc_ebm_event_type` VALUES ('12G01', '一般工业建筑火灾', '1', '12G00');
INSERT INTO `bc_ebm_event_type` VALUES ('12G02', '特种工业建筑火灾', '1', '12G00');
INSERT INTO `bc_ebm_event_type` VALUES ('12G03', '一般民用建筑火灾', '1', '12G00');
INSERT INTO `bc_ebm_event_type` VALUES ('12G04', '高层民用建筑火灾', '1', '12G00');
INSERT INTO `bc_ebm_event_type` VALUES ('12G05', '地下建筑火灾', '1', '12G00');
INSERT INTO `bc_ebm_event_type` VALUES ('12G06', '公用建筑火灾', '1', '12G00');
INSERT INTO `bc_ebm_event_type` VALUES ('12G07', '隧道火灾', '1', '12G00');
INSERT INTO `bc_ebm_event_type` VALUES ('12H00', '道路交通事故', '0', '12000');
INSERT INTO `bc_ebm_event_type` VALUES ('12H01', '翻车事件', '1', '12H00');
INSERT INTO `bc_ebm_event_type` VALUES ('12H02', '撞车事件', '1', '12H00');
INSERT INTO `bc_ebm_event_type` VALUES ('12H03', '车辆坠水、坠沟事件', '1', '12H00');
INSERT INTO `bc_ebm_event_type` VALUES ('12H04', '车辆起火事件', '1', '12H00');
INSERT INTO `bc_ebm_event_type` VALUES ('12H05', '校车交通事故', '1', '12H00');
INSERT INTO `bc_ebm_event_type` VALUES ('12H99', '其他道路交通事故', '1', '12H00');
INSERT INTO `bc_ebm_event_type` VALUES ('12J00', '水上交通事故', '0', '12000');
INSERT INTO `bc_ebm_event_type` VALUES ('12J01', '船舶碰撞事故', '1', '12J00');
INSERT INTO `bc_ebm_event_type` VALUES ('12J02', '船舶触礁事故', '1', '12J00');
INSERT INTO `bc_ebm_event_type` VALUES ('12J03', '船舶触损事故', '1', '12J00');
INSERT INTO `bc_ebm_event_type` VALUES ('12J04', '船舶搁浅事故', '1', '12J00');
INSERT INTO `bc_ebm_event_type` VALUES ('12J05', '船舶遭受风灾事故', '1', '12J00');
INSERT INTO `bc_ebm_event_type` VALUES ('12J06', '船舶火灾事故', '1', '12J00');
INSERT INTO `bc_ebm_event_type` VALUES ('12J07', '船舶失踪事故', '1', '12J00');
INSERT INTO `bc_ebm_event_type` VALUES ('12J08', '船舶海上遇险事故', '1', '12J00');
INSERT INTO `bc_ebm_event_type` VALUES ('12J09', '水上保安事件', '1', '12J00');
INSERT INTO `bc_ebm_event_type` VALUES ('12J10', '沿海渔业设施事故', '1', '12J00');
INSERT INTO `bc_ebm_event_type` VALUES ('12J99', '其他水上交通事故', '1', '12J00');
INSERT INTO `bc_ebm_event_type` VALUES ('12K00', '铁路交通事故', '0', '12000');
INSERT INTO `bc_ebm_event_type` VALUES ('12K01', '列车脱轨事故', '1', '12K00');
INSERT INTO `bc_ebm_event_type` VALUES ('12K02', '列车追尾事故', '1', '12K00');
INSERT INTO `bc_ebm_event_type` VALUES ('12K03', '列车撞车事故', '1', '12K00');
INSERT INTO `bc_ebm_event_type` VALUES ('12K04', '列车撞人事故', '1', '12K00');
INSERT INTO `bc_ebm_event_type` VALUES ('12K05', '列车火灾、爆炸事故', '1', '12K00');
INSERT INTO `bc_ebm_event_type` VALUES ('12K99', '其他铁路交通事故', '1', '12K00');
INSERT INTO `bc_ebm_event_type` VALUES ('12L00', '城市轨道交通事故', '0', '12000');
INSERT INTO `bc_ebm_event_type` VALUES ('12L01', '地铁、轻轨、单轨列车脱轨事故', '1', '12L00');
INSERT INTO `bc_ebm_event_type` VALUES ('12L02', '地铁、轻轨、单轨列车追尾事故', '1', '12L00');
INSERT INTO `bc_ebm_event_type` VALUES ('12L03', '地铁、轻轨、单轨列车撞车事故', '1', '12L00');
INSERT INTO `bc_ebm_event_type` VALUES ('12L04', '地铁、轻轨、单轨列车撞人事故', '1', '12L00');
INSERT INTO `bc_ebm_event_type` VALUES ('12L05', '地铁、轻轨、单轨列车火灾、爆炸事故', '1', '12L00');
INSERT INTO `bc_ebm_event_type` VALUES ('12L99', '其他城市轨道交通事故', '1', '12L00');
INSERT INTO `bc_ebm_event_type` VALUES ('12M00', '民用航空器事故', '0', '12000');
INSERT INTO `bc_ebm_event_type` VALUES ('12M01', '坠机事件', '1', '12M00');
INSERT INTO `bc_ebm_event_type` VALUES ('12M02', '撞机事件', '1', '12M00');
INSERT INTO `bc_ebm_event_type` VALUES ('12M99', '其他民用航空器飞行事故', '1', '12M00');
INSERT INTO `bc_ebm_event_type` VALUES ('12N00', '特种设备事故', '0', '12000');
INSERT INTO `bc_ebm_event_type` VALUES ('12N01', '锅炉事故', '1', '12N00');
INSERT INTO `bc_ebm_event_type` VALUES ('12N02', '压力容器事故', '1', '12N00');
INSERT INTO `bc_ebm_event_type` VALUES ('12N03', '压力管道事故', '1', '12N00');
INSERT INTO `bc_ebm_event_type` VALUES ('12N04', '电梯事故', '1', '12N00');
INSERT INTO `bc_ebm_event_type` VALUES ('12N05', '起重机械事故', '1', '12N00');
INSERT INTO `bc_ebm_event_type` VALUES ('12N06', '客运索道事故', '1', '12N00');
INSERT INTO `bc_ebm_event_type` VALUES ('12N07', '大型游乐设施事故', '1', '12N00');
INSERT INTO `bc_ebm_event_type` VALUES ('12N99', '其他特种设备事故', '1', '12N00');
INSERT INTO `bc_ebm_event_type` VALUES ('12P00', '基础设施和公用设施事故', '0', '12000');
INSERT INTO `bc_ebm_event_type` VALUES ('12P01', '公路交通设施事故', '1', '12P00');
INSERT INTO `bc_ebm_event_type` VALUES ('12P02', '铁路交通设施事故', '1', '12P00');
INSERT INTO `bc_ebm_event_type` VALUES ('12P03', '城市轨道交通设施事故', '1', '12P00');
INSERT INTO `bc_ebm_event_type` VALUES ('12P04', '城市桥梁隧道设施事故', '1', '12P00');
INSERT INTO `bc_ebm_event_type` VALUES ('12P05', '水运交通设施事故', '1', '12P00');
INSERT INTO `bc_ebm_event_type` VALUES ('12P06', '民航交通设施事故', '1', '12P00');
INSERT INTO `bc_ebm_event_type` VALUES ('12P07', '水利设施事故', '1', '12P00');
INSERT INTO `bc_ebm_event_type` VALUES ('12P08', '电力基础设施事故', '1', '12P00');
INSERT INTO `bc_ebm_event_type` VALUES ('12P09', '石油天然气基础设施事故', '1', '12P00');
INSERT INTO `bc_ebm_event_type` VALUES ('12P10', '通讯基础设施事故', '1', '12P00');
INSERT INTO `bc_ebm_event_type` VALUES ('12P11', '金融基础设施事故', '1', '12P00');
INSERT INTO `bc_ebm_event_type` VALUES ('12P12', '城市生命线基础设施事故', '1', '12P00');
INSERT INTO `bc_ebm_event_type` VALUES ('12P13', '建筑垮塌事故', '1', '12P00');
INSERT INTO `bc_ebm_event_type` VALUES ('12P99', '其他公用设施和设备事故', '1', '12P00');
INSERT INTO `bc_ebm_event_type` VALUES ('12Q00', '环境污染和生态破坏事件', '0', '12000');
INSERT INTO `bc_ebm_event_type` VALUES ('12Q01', '水域污染事件', '1', '12Q00');
INSERT INTO `bc_ebm_event_type` VALUES ('12Q02', '空气污染事件', '1', '12Q00');
INSERT INTO `bc_ebm_event_type` VALUES ('12Q03', '土壤污染事件', '1', '12Q00');
INSERT INTO `bc_ebm_event_type` VALUES ('12Q04', '海上溢油事件', '1', '12Q00');
INSERT INTO `bc_ebm_event_type` VALUES ('12Q05', '污染导致城市水源供水中断事故', '1', '12Q00');
INSERT INTO `bc_ebm_event_type` VALUES ('12Q06', '转基因生物生态破坏事件', '1', '12Q00');
INSERT INTO `bc_ebm_event_type` VALUES ('12Q07', '盗伐、滥伐、哄抢森林事件', '1', '12Q00');
INSERT INTO `bc_ebm_event_type` VALUES ('12Q10', '毁林、乱占林地、非法改变林地用途事件', '1', '12Q00');
INSERT INTO `bc_ebm_event_type` VALUES ('12Q11', '濒危物种生存环境遭受环境污染事件', '1', '12Q00');
INSERT INTO `bc_ebm_event_type` VALUES ('12Q12', '野生动（植）物种群大批死亡事件', '1', '12Q00');
INSERT INTO `bc_ebm_event_type` VALUES ('12Q13', '自然保护区、风景名胜区生态破坏', '1', '12Q00');
INSERT INTO `bc_ebm_event_type` VALUES ('12Q14', '进口再生原料污染事件', '1', '12Q00');
INSERT INTO `bc_ebm_event_type` VALUES ('12Q15', '非法倾倒、埋藏剧毒危险废物事件', '1', '12Q00');
INSERT INTO `bc_ebm_event_type` VALUES ('12Q99', '其它环境污染和生态破坏事件', '1', '12Q00');
INSERT INTO `bc_ebm_event_type` VALUES ('12R00', '农业机械事故', '0', '12000');
INSERT INTO `bc_ebm_event_type` VALUES ('12R01', '农业机械行驶事故', '1', '12R00');
INSERT INTO `bc_ebm_event_type` VALUES ('12R02', '农业机械作业事故', '1', '12R00');
INSERT INTO `bc_ebm_event_type` VALUES ('12R03', '农业机械碾压事件', '1', '12R00');
INSERT INTO `bc_ebm_event_type` VALUES ('12R04', '农业机械碰撞事件', '1', '12R00');
INSERT INTO `bc_ebm_event_type` VALUES ('12R05', '农业机械翻车事件', '1', '12R00');
INSERT INTO `bc_ebm_event_type` VALUES ('12R06', '农业机械落车事件', '1', '12R00');
INSERT INTO `bc_ebm_event_type` VALUES ('12R07', '农业机械火灾事件', '1', '12R00');
INSERT INTO `bc_ebm_event_type` VALUES ('12R99', '其他农业机械事故', '1', '12R00');
INSERT INTO `bc_ebm_event_type` VALUES ('12T00', '核与辐射事故', '0', '12000');
INSERT INTO `bc_ebm_event_type` VALUES ('12T01', '核设施事故', '1', '12T00');
INSERT INTO `bc_ebm_event_type` VALUES ('12T02', '放射性物质运输事故', '1', '12T00');
INSERT INTO `bc_ebm_event_type` VALUES ('12T03', '放射源事故', '1', '12T00');
INSERT INTO `bc_ebm_event_type` VALUES ('12T99', '射线装置事故', '1', '12T00');
INSERT INTO `bc_ebm_event_type` VALUES ('12U00', '能源供应中断事故', '0', '12000');
INSERT INTO `bc_ebm_event_type` VALUES ('12Y00', '其他事故灾难', '0', '12000');
INSERT INTO `bc_ebm_event_type` VALUES ('13000', '公共卫生事件', '0', '10000');
INSERT INTO `bc_ebm_event_type` VALUES ('13600', '其它严重影响公共健康和卫生安全事件', '0', '13000');
INSERT INTO `bc_ebm_event_type` VALUES ('13A00', '传染病事件', '0', '13000');
INSERT INTO `bc_ebm_event_type` VALUES ('13A01', '鼠疫流行事件', '1', '13A00');
INSERT INTO `bc_ebm_event_type` VALUES ('13A02', '霍乱流行事件', '1', '13A00');
INSERT INTO `bc_ebm_event_type` VALUES ('13A03', '肺炭疽流行事件', '1', '13A00');
INSERT INTO `bc_ebm_event_type` VALUES ('13A04', '传染性非典型肺炎流行事件', '1', '13A00');
INSERT INTO `bc_ebm_event_type` VALUES ('13A05', '大感染高致病性禽流感流行事件', '1', '13A00');
INSERT INTO `bc_ebm_event_type` VALUES ('13A06', '其他甲类或甲类管理传染病流行事件', '1', '13A00');
INSERT INTO `bc_ebm_event_type` VALUES ('13A07', '其他乙类传染病流行事件', '1', '13A00');
INSERT INTO `bc_ebm_event_type` VALUES ('13A08', '新传染病或我国尚未发现的传染病传入事件', '1', '13A00');
INSERT INTO `bc_ebm_event_type` VALUES ('13A09', '我国己消灭传染病重新流行事件', '1', '13A00');
INSERT INTO `bc_ebm_event_type` VALUES ('13A99', '其它传染病事件流行事件', '1', '13A00');
INSERT INTO `bc_ebm_event_type` VALUES ('13B00', '食品药品安全事件', '0', '13000');
INSERT INTO `bc_ebm_event_type` VALUES ('13B01', '药品安全事件', '1', '13B00');
INSERT INTO `bc_ebm_event_type` VALUES ('13B02', '群体性预防接种反应', '1', '13B00');
INSERT INTO `bc_ebm_event_type` VALUES ('13B03', '食品安全事件', '1', '13B00');
INSERT INTO `bc_ebm_event_type` VALUES ('13B04', '农产品质量安全事件', '1', '13B00');
INSERT INTO `bc_ebm_event_type` VALUES ('13B99', '其它食品药品安全事件', '1', '13B00');
INSERT INTO `bc_ebm_event_type` VALUES ('13C00', '群体性中毒、感染事件', '0', '13000');
INSERT INTO `bc_ebm_event_type` VALUES ('13C01', '急性职业中毒事件', '1', '13C00');
INSERT INTO `bc_ebm_event_type` VALUES ('13C02', '重金属中毒事件', '1', '13C00');
INSERT INTO `bc_ebm_event_type` VALUES ('13C03', '非职业性一氧化碳中毒事件', '1', '13C00');
INSERT INTO `bc_ebm_event_type` VALUES ('13C99', '其他群体性中毒感染事件', '1', '13C00');
INSERT INTO `bc_ebm_event_type` VALUES ('13D00', '病原微生物、菌毒株事件', '0', '13000');
INSERT INTO `bc_ebm_event_type` VALUES ('13D01', '菌株、毒株、致病因子丢失事件', '1', '13D00');
INSERT INTO `bc_ebm_event_type` VALUES ('13D02', '隐匿运输、邮寄病原体、生物毒素', '1', '13D00');
INSERT INTO `bc_ebm_event_type` VALUES ('13D03', '医源性感染事件', '1', '13D00');
INSERT INTO `bc_ebm_event_type` VALUES ('13D99', '其他病原微生物、菌毒株事件', '1', '13D00');
INSERT INTO `bc_ebm_event_type` VALUES ('13E00', '动物疫情事件', '0', '13000');
INSERT INTO `bc_ebm_event_type` VALUES ('13E01', '高致病性禽流感', '1', '13E00');
INSERT INTO `bc_ebm_event_type` VALUES ('13E02', '口蹄疫', '1', '13E00');
INSERT INTO `bc_ebm_event_type` VALUES ('13E03', '疯牛病', '1', '13E00');
INSERT INTO `bc_ebm_event_type` VALUES ('13E04', '猪瘟', '1', '13E00');
INSERT INTO `bc_ebm_event_type` VALUES ('13E05', '新城疫', '1', '13E00');
INSERT INTO `bc_ebm_event_type` VALUES ('13E06', '蓝舌病', '1', '13E00');
INSERT INTO `bc_ebm_event_type` VALUES ('13E07', '动物布鲁氏菌病', '1', '13E00');
INSERT INTO `bc_ebm_event_type` VALUES ('13E08', '动物结核病', '1', '13E00');
INSERT INTO `bc_ebm_event_type` VALUES ('13E09', '狂犬病', '1', '13E00');
INSERT INTO `bc_ebm_event_type` VALUES ('13E10', '动物炭瘟病', '1', '13E00');
INSERT INTO `bc_ebm_event_type` VALUES ('13E11', '反鱼兽疫', '1', '13E00');
INSERT INTO `bc_ebm_event_type` VALUES ('13E12', '我国未发的动物疫病传入事件', '1', '13E00');
INSERT INTO `bc_ebm_event_type` VALUES ('13E13', '我国己消灭动物疫病重新流行事件', '1', '13E00');
INSERT INTO `bc_ebm_event_type` VALUES ('13E99', '其他动物疫情事件', '1', '13E00');
INSERT INTO `bc_ebm_event_type` VALUES ('13F00', '群体性不明原因疾病', '0', '13000');
INSERT INTO `bc_ebm_event_type` VALUES ('13Y00', '其它社会公共卫生事件', '0', '13000');
INSERT INTO `bc_ebm_event_type` VALUES ('14000', '安全事件', '0', '10000');
INSERT INTO `bc_ebm_event_type` VALUES ('14A00', '群体性事件', '0', '14000');
INSERT INTO `bc_ebm_event_type` VALUES ('14A01', '非法集会游行示威', '1', '14A00');
INSERT INTO `bc_ebm_event_type` VALUES ('14A02', '集体上访请愿', '1', '14A00');
INSERT INTO `bc_ebm_event_type` VALUES ('14A03', '冲击、围攻党政军机关和要害部门事件', '1', '14A00');
INSERT INTO `bc_ebm_event_type` VALUES ('14A04', '大规模打、砸、抢、烧犯罪事件', '1', '14A00');
INSERT INTO `bc_ebm_event_type` VALUES ('14A05', '群体性械斗、冲突事件', '1', '14A00');
INSERT INTO `bc_ebm_event_type` VALUES ('14A06', '静坐事件', '1', '14A00');
INSERT INTO `bc_ebm_event_type` VALUES ('14A07', '罢市、罢工', '1', '14A00');
INSERT INTO `bc_ebm_event_type` VALUES ('14A10', '罢课', '1', '14A00');
INSERT INTO `bc_ebm_event_type` VALUES ('14A11', '高校内聚集事件失控', '1', '14A00');
INSERT INTO `bc_ebm_event_type` VALUES ('14A12', '高校校园网大范围串联、煽动和蛊惑信息事件', '1', '14A00');
INSERT INTO `bc_ebm_event_type` VALUES ('14A13', '阻断交通事件', '1', '14A00');
INSERT INTO `bc_ebm_event_type` VALUES ('14A14', '阻挠、妨碍国家重点建设工程施工事件', '1', '14A00');
INSERT INTO `bc_ebm_event_type` VALUES ('14A15', '暴狱事件', '1', '14A00');
INSERT INTO `bc_ebm_event_type` VALUES ('14A16', '聚众闹事', '1', '14A00');
INSERT INTO `bc_ebm_event_type` VALUES ('14A99', '其它群体事件', '1', '14A00');
INSERT INTO `bc_ebm_event_type` VALUES ('14B00', '刑事案件', '0', '14000');
INSERT INTO `bc_ebm_event_type` VALUES ('14B01', '杀人案件', '1', '14B00');
INSERT INTO `bc_ebm_event_type` VALUES ('14B02', '爆炸案件', '1', '14B00');
INSERT INTO `bc_ebm_event_type` VALUES ('14B03', '放火案件', '1', '14B00');
INSERT INTO `bc_ebm_event_type` VALUES ('14B04', '投放危险物质案件', '1', '14B00');
INSERT INTO `bc_ebm_event_type` VALUES ('14B05', '以危害方法危害公共安全案件', '1', '14B00');
INSERT INTO `bc_ebm_event_type` VALUES ('14B06', '绑架案件', '1', '14B00');
INSERT INTO `bc_ebm_event_type` VALUES ('14B07', '抢劫、盗窃金融机构或运钞车案件', '1', '14B00');
INSERT INTO `bc_ebm_event_type` VALUES ('14B08', '抢劫、走私、盗窃军(警)用枪械案件', '1', '14B00');
INSERT INTO `bc_ebm_event_type` VALUES ('14B09', '放射性材料被盗、丢失案件', '1', '14B00');
INSERT INTO `bc_ebm_event_type` VALUES ('14B10', '炸药、雷管被盗、丢失案件', '1', '14B00');
INSERT INTO `bc_ebm_event_type` VALUES ('14B11', '走私放射性材料案件', '1', '14B00');
INSERT INTO `bc_ebm_event_type` VALUES ('14B12', '走私固体废物案件', '1', '14B00');
INSERT INTO `bc_ebm_event_type` VALUES ('14B13', '制贩毒品案件', '1', '14B00');
INSERT INTO `bc_ebm_event_type` VALUES ('14B14', '盗窃、出卖、泄露及丢失国家秘密案件', '1', '14B00');
INSERT INTO `bc_ebm_event_type` VALUES ('14B16', '攻击破坏卫星通信、广播电视传输系统案件', '1', '14B00');
INSERT INTO `bc_ebm_event_type` VALUES ('14B17', '制售假劣药品、医疗器械案件', '1', '14B00');
INSERT INTO `bc_ebm_event_type` VALUES ('14B18', '制售不符合卫生标准、有毒有害食品', '1', '14B00');
INSERT INTO `bc_ebm_event_type` VALUES ('14B19', '走私、骗汇、逃汇、洗钱案件', '1', '14B00');
INSERT INTO `bc_ebm_event_type` VALUES ('14B20', '金融诈骗案', '1', '14B00');
INSERT INTO `bc_ebm_event_type` VALUES ('14B21', '增值税发票及其他票证案', '1', '14B00');
INSERT INTO `bc_ebm_event_type` VALUES ('14B22', '假劣种子、化肥、农药坑农案件', '1', '14B00');
INSERT INTO `bc_ebm_event_type` VALUES ('14B23', '非法狗猎、采集保护野生动植物案件', '1', '14B00');
INSERT INTO `bc_ebm_event_type` VALUES ('14B24', '破坏物种资源案件', '1', '14B00');
INSERT INTO `bc_ebm_event_type` VALUES ('14B25', '偷渡案件', '1', '14B00');
INSERT INTO `bc_ebm_event_type` VALUES ('14B99', '其它刑事案件', '1', '14B00');
INSERT INTO `bc_ebm_event_type` VALUES ('14BJ5', '攻击破坏计算机网络案件', '1', '14B00');
INSERT INTO `bc_ebm_event_type` VALUES ('14C00', '金融突发事件', '0', '14000');
INSERT INTO `bc_ebm_event_type` VALUES ('14C01', '银行业金融突发事件', '1', '14C00');
INSERT INTO `bc_ebm_event_type` VALUES ('14C02', '证券业金融突发事件', '1', '14C00');
INSERT INTO `bc_ebm_event_type` VALUES ('14C03', '保险业金融突发事件', '1', '14C00');
INSERT INTO `bc_ebm_event_type` VALUES ('14C04', '外汇类突发事件', '1', '14C00');
INSERT INTO `bc_ebm_event_type` VALUES ('14C05', '货币发行类突发事件', '1', '14C00');
INSERT INTO `bc_ebm_event_type` VALUES ('14C06', '支付结算类突发事件', '1', '14C00');
INSERT INTO `bc_ebm_event_type` VALUES ('14C99', '其它金融突发事件', '1', '14C00');
INSERT INTO `bc_ebm_event_type` VALUES ('14D00', '影响市场稳定的突发事件', '0', '14000');
INSERT INTO `bc_ebm_event_type` VALUES ('14D01', '粮食市场异常波动', '1', '14D00');
INSERT INTO `bc_ebm_event_type` VALUES ('14D02', '生活必需品市场异常波动', '1', '14D00');
INSERT INTO `bc_ebm_event_type` VALUES ('14D99', '其他影响市场稳定的突发事件', '1', '14D00');
INSERT INTO `bc_ebm_event_type` VALUES ('14E00', '民族和宗教事件', '0', '14000');
INSERT INTO `bc_ebm_event_type` VALUES ('14E01', '民族分裂活动', '1', '14E00');
INSERT INTO `bc_ebm_event_type` VALUES ('14E02', '宗教大规模非法聚会', '1', '14E00');
INSERT INTO `bc_ebm_event_type` VALUES ('14E03', '民族冲突事件', '1', '14E00');
INSERT INTO `bc_ebm_event_type` VALUES ('14E04', '宗教冲突事件', '1', '14E00');
INSERT INTO `bc_ebm_event_type` VALUES ('14E99', '其它民族宗教事件', '1', '14E00');
INSERT INTO `bc_ebm_event_type` VALUES ('14F00', '恐怖袭击事件', '0', '14000');
INSERT INTO `bc_ebm_event_type` VALUES ('14F01', '袭击公共聚集场所事件', '1', '14F00');
INSERT INTO `bc_ebm_event_type` VALUES ('14F02', '袭击党政军首脑机关事件', '1', '14F00');
INSERT INTO `bc_ebm_event_type` VALUES ('14F03', '袭击城市标志性建筑物事件', '1', '14F00');
INSERT INTO `bc_ebm_event_type` VALUES ('14F04', '袭击国防设施事件', '1', '14F00');
INSERT INTO `bc_ebm_event_type` VALUES ('14F05', '袭击宗教场所事件', '1', '14F00');
INSERT INTO `bc_ebm_event_type` VALUES ('14F06', '袭击外交机构或国际组织事件', '1', '14F00');
INSERT INTO `bc_ebm_event_type` VALUES ('14F07', '袭击重要经济目标事件', '1', '14F00');
INSERT INTO `bc_ebm_event_type` VALUES ('14F08', '袭击重要基础设施事件', '1', '14F00');
INSERT INTO `bc_ebm_event_type` VALUES ('14F09', '袭击城市基础设施事件', '1', '14F00');
INSERT INTO `bc_ebm_event_type` VALUES ('14F10', '袭击交通工具事件', '1', '14F00');
INSERT INTO `bc_ebm_event_type` VALUES ('14F11', '袭击重要计算机信息网络系统事件', '1', '14F00');
INSERT INTO `bc_ebm_event_type` VALUES ('14F12', '袭击通信或新闻中枢事件', '1', '14F00');
INSERT INTO `bc_ebm_event_type` VALUES ('14F13', '袭击重要核生化设施事件', '1', '14F00');
INSERT INTO `bc_ebm_event_type` VALUES ('14F14', '袭击党政军要员事件', '1', '14F00');
INSERT INTO `bc_ebm_event_type` VALUES ('14F15', '袭击外交人员事件', '1', '14F00');
INSERT INTO `bc_ebm_event_type` VALUES ('14F16', '袭击平民事件', '1', '14F00');
INSERT INTO `bc_ebm_event_type` VALUES ('14F17', '袭击宗教人士事件', '1', '14F00');
INSERT INTO `bc_ebm_event_type` VALUES ('14F18', '袭击知名人士事件', '1', '14F00');
INSERT INTO `bc_ebm_event_type` VALUES ('14F19', '袭击外国公民事件', '1', '14F00');
INSERT INTO `bc_ebm_event_type` VALUES ('14F20', '核生化战剂袭击事件', '1', '14F00');
INSERT INTO `bc_ebm_event_type` VALUES ('14F21', '劫持航空器事件', '1', '14F00');
INSERT INTO `bc_ebm_event_type` VALUES ('14F22', '劫持船舶事件', '1', '14F00');
INSERT INTO `bc_ebm_event_type` VALUES ('14F23', '劫持火车事件', '1', '14F00');
INSERT INTO `bc_ebm_event_type` VALUES ('14F24', '袭击警卫对象、警卫现场事件', '1', '14F00');
INSERT INTO `bc_ebm_event_type` VALUES ('14F99', '其它恐怖袭击事件', '1', '14F00');
INSERT INTO `bc_ebm_event_type` VALUES ('14G00', '涉外事件', '0', '14000');
INSERT INTO `bc_ebm_event_type` VALUES ('14G01', '政治类涉外事件', '1', '14G00');
INSERT INTO `bc_ebm_event_type` VALUES ('14G02', '经济类涉外事件', '1', '14G00');
INSERT INTO `bc_ebm_event_type` VALUES ('14G03', '灾害事故卫生类涉外事件', '1', '14G00');
INSERT INTO `bc_ebm_event_type` VALUES ('14G04', '恐怖暴力类涉外事件', '1', '14G00');
INSERT INTO `bc_ebm_event_type` VALUES ('14G05', '境外敌对势力类涉外事件', '1', '14G00');
INSERT INTO `bc_ebm_event_type` VALUES ('14G06', '社会安全类涉外事件', '1', '14G00');
INSERT INTO `bc_ebm_event_type` VALUES ('14G99', '其它涉外事件', '1', '14G00');
INSERT INTO `bc_ebm_event_type` VALUES ('14H00', '信息安全事件', '0', '14000');
INSERT INTO `bc_ebm_event_type` VALUES ('14H01', '影响公共互联网骨干网的大规模网络病毒传播事件', '1', '14H00');
INSERT INTO `bc_ebm_event_type` VALUES ('14H02', '针对公共互联网核心设备的网络攻击事件', '1', '14H00');
INSERT INTO `bc_ebm_event_type` VALUES ('14H03', '针对国家重要信息系统的网络攻击入侵事件', '1', '14H00');
INSERT INTO `bc_ebm_event_type` VALUES ('14H99', '其他信息安全事件', '1', '14H00');
INSERT INTO `bc_ebm_event_type` VALUES ('14Y00', '其他社会安全事件', '0', '14000');
INSERT INTO `bc_ebm_event_type` VALUES ('14Y01', '行政区划界限纠纷事件', '1', '14Y00');
INSERT INTO `bc_ebm_event_type` VALUES ('14Y02', '行政区划调整和地名变更引发事件', '1', '14Y00');
INSERT INTO `bc_ebm_event_type` VALUES ('19000', '其它突发事件', '0', '10000');
INSERT INTO `bc_ebm_event_type` VALUES ('20000', '日常事件', '0', '0');

-- ----------------------------
-- Table structure for `bc_ebm_res`
-- ----------------------------
DROP TABLE IF EXISTS `bc_ebm_res`;
CREATE TABLE `bc_ebm_res` (
  `ebmResourceId` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '消息资源ID',
  `brdItemId` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '关联记录ID',
  `ebrpsId` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '平台ID',
  `ebrstId` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '台站ID',
  `ebrasId` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '接收设备ID',
  PRIMARY KEY (`ebmResourceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='消息关联资源信息表';

-- ----------------------------
-- Table structure for `bc_ebm_res_bs`
-- ----------------------------
DROP TABLE IF EXISTS `bc_ebm_res_bs`;
CREATE TABLE `bc_ebm_res_bs` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `ebmResourceId` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '消息关联资源ID',
  `rptTime` datetime NOT NULL,
  `brdSysType` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `brdSysInfo` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `startTime` datetime DEFAULT NULL,
  `endTime` datetime DEFAULT NULL,
  `fileURL` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `brdStateCode` int(11) DEFAULT NULL,
  `brdStateDesc` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='消息资源关联播出系统的记录表';

-- ----------------------------
-- Table structure for `bc_ebm_state_request`
-- ----------------------------
DROP TABLE IF EXISTS `bc_ebm_state_request`;
CREATE TABLE `bc_ebm_state_request` (
  `ebmStateRequestId` varchar(64) NOT NULL COMMENT '消息播发状态请求编号',
  `ebmId` varchar(64) NOT NULL COMMENT '消息编号',
  `relatedEbrId` varchar(64) DEFAULT NULL COMMENT '关联的资源编号',
  `relatedEbdId` varchar(64) DEFAULT NULL COMMENT '关联数据包编号',
  PRIMARY KEY (`ebmStateRequestId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='播发状态查询请求表';

-- ----------------------------
-- Records of bc_ebm_state_request
-- ----------------------------

-- ----------------------------
-- Table structure for `bc_ebr_adaptor`
-- ----------------------------
DROP TABLE IF EXISTS `bc_ebr_adaptor`;
CREATE TABLE `bc_ebr_adaptor` (
  `asEbrId` varchar(64) NOT NULL COMMENT '接收设备ID',
  `asEbrName` varchar(100) NOT NULL COMMENT '接收设备名称',
  `asUrl` varchar(255) NOT NULL COMMENT '联动对接地址',
  `asType` varchar(10) DEFAULT NULL COMMENT '接收消息设备类型',
  `relatedRsEbrId` varchar(64) DEFAULT NULL COMMENT '关联台站编号',
  `relatedPsEbrId` varchar(64) DEFAULT NULL COMMENT '关联平台编号',
  `areaCode` varchar(20) DEFAULT NULL COMMENT '覆盖区域编码',
  `longitude` varchar(10) DEFAULT NULL COMMENT '接收设备经度',
  `latitude` varchar(10) DEFAULT NULL COMMENT '接收设备纬度',
  `asState` tinyint(4) DEFAULT '1' COMMENT '适配器状态（1:运行2:停止3:故障4:故障恢复）',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `syncFlag` tinyint(4) DEFAULT '1' COMMENT '同步标识（1：未同步 2：已同步）',
  `param1` varchar(20) DEFAULT NULL COMMENT '扩展字段',
  `param2` varchar(20) DEFAULT NULL COMMENT '扩展字段',
  PRIMARY KEY (`asEbrId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息接收设备表';

-- ----------------------------
-- Table structure for `bc_ebr_broadcast`
-- ----------------------------
DROP TABLE IF EXISTS `bc_ebr_broadcast`;
CREATE TABLE `bc_ebr_broadcast` (
  `bsEbrId` varchar(64) NOT NULL COMMENT '播出系统ID',
  `bsName` varchar(100) NOT NULL COMMENT '播出系统名称',
  `bsUrl` varchar(255) DEFAULT NULL COMMENT '播出系统URL',
  `bsType` varchar(10) DEFAULT NULL COMMENT '播出系统类型',
  `longitude` varchar(10) DEFAULT NULL COMMENT '播出系统经度',
  `latitude` varchar(10) DEFAULT NULL COMMENT '播出系统纬度',
  `square` decimal(24,0) DEFAULT NULL COMMENT '播放系统覆盖面积',
  `areaCode` varchar(20) DEFAULT NULL COMMENT '覆盖区域',
  `population` decimal(24,2) DEFAULT NULL COMMENT '覆盖人口',
  `languageCode` varchar(10) DEFAULT NULL COMMENT '原播语种',
  `equipRoom` varchar(200) DEFAULT NULL COMMENT '机房名称',
  `radioChannelName` varchar(50) DEFAULT NULL COMMENT '电台频道名称',
  `radioFreq` decimal(10,0) DEFAULT NULL COMMENT '电台频道频率',
  `radioPower` int(10) DEFAULT NULL COMMENT '电台发射功率',
  `backup` tinyint(1) DEFAULT NULL COMMENT '是否是备机',
  `autoSwitch` tinyint(1) DEFAULT NULL COMMENT '是否自动切换备机',
  `remoteControl` tinyint(1) DEFAULT NULL COMMENT '能否遥控开机',
  `experiment` tinyint(1) DEFAULT NULL COMMENT '实验/覆盖发射',
  `tvChannelName` varchar(20) DEFAULT NULL COMMENT '电视台频道名称',
  `tvFreq` int(10) DEFAULT NULL COMMENT '电视台频道频率',
  `programNum` varchar(20) DEFAULT NULL COMMENT '节目号',
  `tvChannelNum` varchar(20) DEFAULT NULL COMMENT '频道号',
  `bsState` tinyint(1) DEFAULT '1' COMMENT '播出系统状态（1:运行2:停止3:故障4:故障恢复）',
  `relatedPsEbrId` varchar(64) DEFAULT NULL COMMENT '关联平台资源编码',
  `relatedRsEbrId` varchar(64) DEFAULT NULL COMMENT '关联台站资源编码',
  `relatedAsEbrId` varchar(64) DEFAULT NULL COMMENT '关联适配器资源编码',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `syncFlag` tinyint(4) DEFAULT '1' COMMENT '是否已同步标识（1：未同步 2：已同步）',
  `statusSyncFlag`  tinyint(4) DEFAULT '1' COMMENT '播出系统状态是否已同步标识（1：未同步 2：已同步）',
  PRIMARY KEY (`bsEbrId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='播出系统表';

-- ----------------------------
-- Table structure for `bc_ebr_check`
-- ----------------------------
DROP TABLE IF EXISTS `bc_ebr_check`;
CREATE TABLE `bc_ebr_check` (
  `ebrId` varchar(24) COLLATE utf8_bin NOT NULL COMMENT '资源ID',
  `rptTime` datetime NOT NULL COMMENT '心跳上报时间',
  PRIMARY KEY (`ebrId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='资源心跳检测';

-- ----------------------------
-- Table structure for `bc_ebr_platform`
-- ----------------------------
DROP TABLE IF EXISTS `bc_ebr_platform`;
CREATE TABLE `bc_ebr_platform` (
  `psEbrId` varchar(64) NOT NULL COMMENT '平台ID',
  `psUrl` varchar(255) NOT NULL COMMENT '平台网络地址',
  `psEbrName` varchar(100) NOT NULL COMMENT '平台名称',
  `psState` tinyint(4) DEFAULT '1' COMMENT '平台状态（1:运行2:停止3:故障4:故障恢复）',
  `areaCode` varchar(20) DEFAULT NULL COMMENT '平台覆盖区域编码',
  `psType` varchar(10) DEFAULT NULL COMMENT '平台类型',
  `parentPsEbrId` varchar(64) DEFAULT NULL COMMENT '父平台资源编号',
  `psAddress` varchar(200) NOT NULL COMMENT '平台地址',
  `contact` varchar(50) DEFAULT NULL COMMENT '联系人名称',
  `phoneNumber` varchar(64) DEFAULT NULL COMMENT '联系人电话',
  `longitude` varchar(20) DEFAULT NULL COMMENT '平台经度',
  `latitude` varchar(20) DEFAULT NULL COMMENT '平台纬度',
  `platLevel` tinyint(4) DEFAULT NULL COMMENT '平台级别',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `syncFlag` tinyint(4) DEFAULT '1' COMMENT '是否已同步标识（1：未同步 2：已同步）',
  `statusSyncFlag`  tinyint(4) DEFAULT '1' COMMENT '平台状态是否已同步标识（1：未同步 2：已同步）',
  `square` decimal(24,0) DEFAULT NULL COMMENT '覆盖面积',
  `population` decimal(24,0) DEFAULT NULL COMMENT '覆盖人口',
  PRIMARY KEY (`psEbrId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='平台信息表';

-- ----------------------------
-- Table structure for `bc_ebr_resourceid_record`
-- ----------------------------
DROP TABLE IF EXISTS `bc_ebr_resourceid_record`;
CREATE TABLE `bc_ebr_resourceid_record` (
  `resourceType` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '资源类型(资源类型和地址编码合成)',
  `idrecord` varchar(99) COLLATE utf8_bin NOT NULL COMMENT '资源ID标识',
  PRIMARY KEY (`resourceType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='资源ID生成记录表';

-- ----------------------------
-- Table structure for `bc_ebr_state_response`
-- ----------------------------
DROP TABLE IF EXISTS `bc_ebr_state_response`;
CREATE TABLE `bc_ebr_state_response` (
  `ebrStateId` varchar(64) NOT NULL DEFAULT '' COMMENT '资源状态编号',
  `ebrId` varchar(64) NOT NULL COMMENT '资源编号',
  `rptTime` datetime DEFAULT NULL COMMENT '生成时间',
  `ebrType` tinyint(4) DEFAULT NULL COMMENT '资源类型（1:平台2:适配器3:播出系统4:终端）',
  `stateCode` tinyint(4) DEFAULT NULL COMMENT '状态代码',
  `stateDesc` varchar(100) DEFAULT NULL COMMENT '状态描述',
  PRIMARY KEY (`ebrStateId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源状态上报信息表';

-- ----------------------------
-- Records of bc_ebr_state_response
-- ----------------------------

-- ----------------------------
-- Table structure for `bc_ebr_station`
-- ----------------------------
DROP TABLE IF EXISTS `bc_ebr_station`;
CREATE TABLE `bc_ebr_station` (
  `stationEbrId` varchar(64) NOT NULL COMMENT '台站ID',
  `stationName` varchar(100) DEFAULT NULL COMMENT '台站名称',
  `stationAddress` varchar(200) DEFAULT NULL COMMENT '台站地址',
  `stationType` varchar(10) NOT NULL COMMENT '台站类型',
  `contact` varchar(50) DEFAULT NULL COMMENT '台站联系人',
  `phoneNumber` varchar(20) DEFAULT NULL COMMENT '联系人电话',
  `longitude` varchar(10) DEFAULT NULL COMMENT '台站经度',
  `latitude` varchar(10) DEFAULT NULL COMMENT '台站纬度',
  `areaCode` varchar(20) DEFAULT NULL COMMENT '覆盖区域编码',
  `relatedPsEbrId` varchar(64) DEFAULT NULL COMMENT 'EWBS平台ID',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `syncFlag` tinyint(4) DEFAULT '1' COMMENT '是否已同步标识（1：未同步 2：已同步）',
  PRIMARY KEY (`stationEbrId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='台站信息表';

-- ----------------------------
-- Table structure for `bc_ebr_terminal`
-- ----------------------------
DROP TABLE IF EXISTS `bc_ebr_terminal`;
CREATE TABLE `bc_ebr_terminal` (
  `terminalEbrId` varchar(64) NOT NULL COMMENT '终端资源编号',
  `terminalEbrName` varchar(100) DEFAULT NULL COMMENT '终端资源名称',
  `relatedPsEbrId` varchar(64) DEFAULT NULL COMMENT '关联平台编号',
  `longitude` varchar(20) DEFAULT NULL COMMENT '经度',
  `latitude` varchar(20) DEFAULT NULL COMMENT '纬度',
  `terminalType` varchar(10) DEFAULT NULL COMMENT '终端类型',
  `terminalState` tinyint(4) DEFAULT '1' COMMENT '终端状态（1:运行2:停止3:故障4:故障恢复）',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `syncFlag` tinyint(4) DEFAULT '1' COMMENT '终端信息同步标识（1：未同步 2：已同步）',
  `statusSyncFlag` tinyint(4) DEFAULT '1' COMMENT '终端状态同步标识（1：未同步 2：已同步）',
  `param1` varchar(20) DEFAULT NULL COMMENT '扩展参数',
  `param2` varchar(20) DEFAULT NULL COMMENT '扩展参数',
  PRIMARY KEY (`terminalEbrId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='终端信息表';

-- ----------------------------
-- Table structure for `bc_ebr_worktime`
-- ----------------------------
DROP TABLE IF EXISTS `bc_ebr_worktime`;
CREATE TABLE `bc_ebr_worktime` (
  `id` varchar(64) NOT NULL,
  `ebrId` varchar(18) DEFAULT NULL COMMENT '资源ID',
  `worktimeId` varchar(64) DEFAULT NULL COMMENT '工作时间表ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='运行图，资源工作时间表';

-- ----------------------------
-- Table structure for `bc_file_info`
-- ----------------------------
DROP TABLE IF EXISTS `bc_file_info`;
CREATE TABLE `bc_file_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `originName` varchar(260) DEFAULT NULL COMMENT '原始文件名',
  `uploadedName` varchar(260) DEFAULT NULL COMMENT '上传后的文件名',
  `fileType` tinyint(4) DEFAULT '1' COMMENT '文件类型 1-媒体文件 2-文本文件',
  `fileExt` varchar(32) DEFAULT 'mp3' COMMENT '文件后缀名',
  `md5Code` varchar(64) DEFAULT NULL COMMENT '文件MD5值',
  `secondLength` int(11) DEFAULT NULL COMMENT '文件播放时长，单位秒',
  `byteSize` int(11) DEFAULT NULL COMMENT '文件大小，单位字节',
  `libId` int(11) DEFAULT NULL COMMENT '文件夹ID',
  `fileText` varchar(1024) DEFAULT NULL COMMENT '文本广播内容',
  `fileDesc` varchar(64) DEFAULT NULL COMMENT '文件描述',
  `createUser` varchar(64) DEFAULT NULL COMMENT '创建人',
  `createDate` datetime DEFAULT NULL COMMENT '文件创建日期',
  `auditState` int(1) DEFAULT '0' COMMENT '审核状态 0-未审核 1-审核通过 2-审核未通过',
  `auditComment` varchar(1024) DEFAULT NULL COMMENT '审核意见',
  `auditDate` datetime DEFAULT NULL COMMENT '审核时间',
  `auditUser` varchar(50) DEFAULT NULL COMMENT '审核人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8 COMMENT='媒体资源表';

-- ----------------------------
-- Table structure for `bc_file_library`
-- ----------------------------
DROP TABLE IF EXISTS `bc_file_library`;
CREATE TABLE `bc_file_library` (
  `libId` int(11) NOT NULL AUTO_INCREMENT COMMENT '文件库ID',
  `libName` varchar(64) DEFAULT NULL COMMENT '文件库名称',
  `libURI` varchar(64) DEFAULT NULL COMMENT '文件库地址',
  `createUser` varchar(64) DEFAULT NULL COMMENT '创建人',
  `createDate` datetime DEFAULT NULL COMMENT '文件库创建时间',
  `parentLibId` int(11) DEFAULT NULL COMMENT '父文件库ID',
  PRIMARY KEY (`libId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='媒体资源目录表';

-- ----------------------------
-- Records of bc_file_library
-- ----------------------------
INSERT INTO `bc_file_library` VALUES ('1', '媒资文件', '媒资文件', 'admin', '2016-12-16 09:28:24', '0');
INSERT INTO `bc_file_library` VALUES ('2', '音频文件', '音频文件', 'admin', '2016-12-16 09:29:12', '1');
INSERT INTO `bc_file_library` VALUES ('3', '录音文件', '录音文件', 'admin', '2016-12-16 09:29:49', '1');

-- ----------------------------
-- Table structure for `bc_log_user`
-- ----------------------------
DROP TABLE IF EXISTS `bc_log_user`;
CREATE TABLE `bc_log_user` (
  `logId` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志编号',
  `userName` varchar(255) DEFAULT NULL COMMENT '用户名',
  `module` varchar(50) DEFAULT NULL COMMENT '模块',
  `operation` varchar(50) DEFAULT NULL COMMENT '操作',
  `content` varchar(255) DEFAULT NULL COMMENT '内容',
  `logTime` varchar(100) DEFAULT NULL COMMENT '记录时间',
  `portalType` tinyint(4) DEFAULT NULL COMMENT '1-中控管理系统，2-制播管理系统，3-调度控制系统',
  `clientIp` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB AUTO_INCREMENT=2090 DEFAULT CHARSET=utf8 COMMENT='用户操作日志表';

-- ----------------------------
-- Table structure for `bc_log_user_his`
-- ----------------------------
DROP TABLE IF EXISTS `bc_log_user_his`;
CREATE TABLE `bc_log_user_his` (
  `logId` int(11) NOT NULL,
  `clientIp` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `logTime` varchar(255) DEFAULT NULL,
  `module` varchar(255) DEFAULT NULL,
  `operation` varchar(255) DEFAULT NULL,
  `portalType` int(11) DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bc_log_user_his
-- ----------------------------

-- ----------------------------
-- Table structure for `bc_omd_request`
-- ----------------------------
DROP TABLE IF EXISTS `bc_omd_request`;
CREATE TABLE `bc_omd_request` (
  `omdRequestId` varchar(64) NOT NULL COMMENT '运维数据请求编号',
  `omdType` varchar(10) NOT NULL COMMENT '运维数据类型',
  `rptStartTime` datetime DEFAULT NULL COMMENT '记录开始时间',
  `rptEndTime` datetime DEFAULT NULL COMMENT '记录结束时间',
  `rptType` varchar(20) DEFAULT NULL COMMENT '数据包操作类型',
  `relatedEbdId` varchar(64) DEFAULT NULL COMMENT '关联业务数据包编号（发送）',
  `relatedEbrId` varchar(64) DEFAULT NULL COMMENT '关联运维数据请求资源编号（发送）',
  PRIMARY KEY (`omdRequestId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='运维数据请求信息表';

-- ----------------------------
-- Table structure for `bc_program`
-- ----------------------------
DROP TABLE IF EXISTS `bc_program`;
CREATE TABLE `bc_program` (
  `programId` int(20) NOT NULL AUTO_INCREMENT COMMENT '节目编号',
  `programType` tinyint(4) DEFAULT NULL COMMENT '节目类型（1:应急 2:日常 3:演练）',
  `programName` varchar(100) DEFAULT NULL COMMENT '节目名称',
  `programLevel` int(10) DEFAULT NULL COMMENT '节目级别（1,2,3,4,10）',
  `createTime` datetime DEFAULT NULL COMMENT '节目创建时间',
  `programContent` varchar(1000) DEFAULT NULL COMMENT '节目',
  `updateTime` datetime DEFAULT NULL COMMENT '节目更新时间',
  `auditResult` tinyint(4) DEFAULT NULL COMMENT '审核结果(0:未审核 1:审核通过 2:审核不通过)',
  `auditOpinion` varchar(100) DEFAULT NULL COMMENT '审核意见',
  `auditTime` datetime DEFAULT NULL COMMENT '审核时间',
  `auditUser` varchar(64) DEFAULT NULL COMMENT '审核人',
  `srcType` tinyint(4) DEFAULT NULL COMMENT '节目来源（1:调控 2:制播）',
  `languageCode` varchar(20) DEFAULT NULL COMMENT '语种代码',
  `createUser` varchar(64) DEFAULT NULL COMMENT '创建用户',
  `state` tinyint(4) DEFAULT '1' COMMENT '状态(1:新建 2:提交 3:取消)',
  `ebmEventType` varchar(5) DEFAULT NULL COMMENT '事件分类',
  `ebmEventDesc` varchar(255) DEFAULT NULL COMMENT '事件描述',
  `releaseOrgCode` varchar(20) DEFAULT NULL COMMENT '发布结构代码',
  `releaseOrgName` varchar(50) DEFAULT NULL COMMENT '发布机构名称',
  PRIMARY KEY (`programId`)
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=utf8 COMMENT='节目信息表';

-- ----------------------------
-- Table structure for `bc_program_area`
-- ----------------------------
DROP TABLE IF EXISTS `bc_program_area`;
CREATE TABLE `bc_program_area` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'uuid',
  `programId` int(20) NOT NULL COMMENT '节目编号',
  `areaCode` varchar(12) NOT NULL COMMENT '关联区域编码',
  PRIMARY KEY (`id`,`programId`)
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8 COMMENT='节目关联区域编码表';

-- ----------------------------
-- Table structure for `bc_program_files`
-- ----------------------------
DROP TABLE IF EXISTS `bc_program_files`;
CREATE TABLE `bc_program_files` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'uuid',
  `programId` int(20) NOT NULL COMMENT '节目编号',
  `fileId` int(20) NOT NULL COMMENT '关联文件编号',
  `fileName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`programId`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8 COMMENT='节目关联媒资信息表';

-- ----------------------------
-- Table structure for `bc_program_strategy`
-- ----------------------------
DROP TABLE IF EXISTS `bc_program_strategy`;
CREATE TABLE `bc_program_strategy` (
  `strategyId` int(11) NOT NULL AUTO_INCREMENT,
  `strategyType` tinyint(4) DEFAULT NULL COMMENT '策略类型 (1:一次性 2:每天 3:每周)',
  `playTime` date DEFAULT NULL COMMENT '播发日期',
  `vStartTime` date DEFAULT NULL COMMENT '有效开始日期',
  `vOverTime` date DEFAULT NULL COMMENT '有效结束日期',
  `weekMask` int(11) DEFAULT NULL COMMENT '周掩码(位运算)',
  `programId` int(11) DEFAULT NULL COMMENT '关联节目Id',
  PRIMARY KEY (`strategyId`)
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='节目策略信息表';

-- ----------------------------
-- Table structure for `bc_program_time`
-- ----------------------------
DROP TABLE IF EXISTS `bc_program_time`;
CREATE TABLE `bc_program_time` (
  `timeId` int(11) NOT NULL AUTO_INCREMENT COMMENT '时间段Id',
  `startTime` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '开始时间(HH:MI)',
  `overTime` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '结束时间(HH:MI)',
  `durationTime` int(11) DEFAULT NULL COMMENT '持续时间',
  `strategyId` int(11) DEFAULT NULL COMMENT '关联节目策略Id',
  `handleFlag` tinyint(4) DEFAULT '1' COMMENT '处理标识(1:未处理 2:已处理)',
  PRIMARY KEY (`timeId`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='节目策略关联时间段信息表';

-- ----------------------------
-- Table structure for `bc_region`
-- ----------------------------
DROP TABLE IF EXISTS `bc_region`;
CREATE TABLE `bc_region` (
  `areaCode` varchar(20) NOT NULL COMMENT '区域编码',
  `areaName` varchar(100) NOT NULL COMMENT '区域名称',
  `parentAreaCode` varchar(20) DEFAULT NULL COMMENT '父区域编码',
  `areaLevel` int(11) DEFAULT NULL COMMENT '区域级别',
  `areaSquare` decimal(8,2) DEFAULT NULL COMMENT '区域覆盖面积',
  `areaPopulation` decimal(8,2) DEFAULT NULL COMMENT '区域覆盖人口',
  `centerLongitude` varchar(30) DEFAULT NULL COMMENT '经度',
  `centerLatitude` varchar(30) DEFAULT NULL COMMENT '纬度',
  PRIMARY KEY (`areaCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='全国行政区划数据表';

-- ----------------------------
-- Table structure for `bc_region_area`
-- ----------------------------
DROP TABLE IF EXISTS `bc_region_area`;
CREATE TABLE `bc_region_area` (
  `areaCode` varchar(20) NOT NULL COMMENT '区域编码',
  `areaName` varchar(100) NOT NULL COMMENT '区域名称',
  `parentAreaCode` varchar(20) DEFAULT NULL COMMENT '上级区域',
  `areaLevel` int(11) DEFAULT NULL COMMENT '区域级别(省、市、县...）',
  `areaSquare` decimal(8,2) DEFAULT NULL COMMENT '区域面积， 单位平方公里',
  `areaPopulation` decimal(8,2) DEFAULT NULL COMMENT '区域人口， 单位万',
  `centerLongitude` varchar(30) DEFAULT NULL COMMENT '应急中心经度',
  `centerLatitude` varchar(30) DEFAULT NULL COMMENT '应急中心纬度',
  PRIMARY KEY (`areaCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='行政区划表';

-- ----------------------------
-- Table structure for `bc_scheme`
-- ----------------------------
DROP TABLE IF EXISTS `bc_scheme`;
CREATE TABLE `bc_scheme` (
  `schemeId` int(20) NOT NULL AUTO_INCREMENT COMMENT '调度方案编号',
  `schemeTitle` varchar(100) DEFAULT NULL COMMENT '方案标题',
  `createTime` datetime DEFAULT NULL COMMENT '生成时间',
  `flowId` varchar(64) DEFAULT NULL COMMENT '关联会话编号',
  `totalArea` decimal(24,2) DEFAULT NULL COMMENT '覆盖面积',
  `totalPopu` decimal(24,2) DEFAULT NULL COMMENT '覆盖人口',
  `areaPercent` decimal(24,2) DEFAULT NULL COMMENT '覆盖区域百分比',
  `popuPercent` decimal(24,2) DEFAULT NULL COMMENT '覆盖人口百分比',
  `auditResult` int(10) DEFAULT NULL COMMENT '审核结果(0:未审核 1:审核通过 2:审核不通过)',
  `auditOpinion` varchar(100) DEFAULT NULL COMMENT '审核意见',
  `auditTime` datetime DEFAULT NULL COMMENT '审核时间',
  `auditUser` varchar(20) DEFAULT NULL COMMENT '审核人员',
  `state` tinyint(4) DEFAULT '1' COMMENT '方案状态(1:新建 2:提交 3:取消)',
  `ebmId` varchar(64) DEFAULT NULL COMMENT '关联消息编号',
  `programId` int(20) DEFAULT NULL COMMENT '关联节目Id',
  `ebdId` varchar(64) DEFAULT NULL COMMENT '关联数据包Id',
   `infoId` int(11) DEFAULT NULL COMMENT '关联信息Id',
  `schemeType` int(11) DEFAULT NULL,
  `ebrPopu` decimal(19,2) DEFAULT NULL COMMENT '资源覆盖人口',
  `ebrArea` decimal(19,2) DEFAULT NULL COMMENT '资源覆盖区域',
  PRIMARY KEY (`schemeId`)
) ENGINE=InnoDB AUTO_INCREMENT=145 DEFAULT CHARSET=utf8 COMMENT='调度方案表';

-- ----------------------------
-- Table structure for `bc_scheme_ebr`
-- ----------------------------
DROP TABLE IF EXISTS `bc_scheme_ebr`;
CREATE TABLE `bc_scheme_ebr` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '方案资源关联编号',
  `ebrId` varchar(64) NOT NULL COMMENT '关联资源编号',
  `schemeId` int(20) NOT NULL COMMENT '关联调度方案编号',
  `ebrType` varchar(10) DEFAULT NULL COMMENT '资源类型',
  `ebrArea` varchar(20) DEFAULT NULL COMMENT '资源覆盖区域',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8 COMMENT='调度方案关联资源信息表';

-- ----------------------------
-- Table structure for `bc_stat_broadcast`
-- ----------------------------
DROP TABLE IF EXISTS `bc_stat_broadcast`;
CREATE TABLE `bc_stat_broadcast` (
  `statsicId` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '广播数据统计ID',
  `statsicType` tinyint(4) DEFAULT '3' COMMENT '3-月，4-季度，5-半年，6-年度',
  `statsicDuation` varchar(20) DEFAULT NULL COMMENT 'x年x月，x年上半年/下半年，x年',
  `totalNum` bigint(11) DEFAULT NULL COMMENT '广播总次数',
  `selfMadeNum` bigint(11) DEFAULT NULL COMMENT '本系统内发起（制播系统）次数',
  `parentPushNum` bigint(11) DEFAULT NULL COMMENT '上级应急广播平台下发次数',
  `otherAlertNum` bigint(11) DEFAULT NULL COMMENT '其他预警部门发起次数',
  `childApplyNum` bigint(11) DEFAULT NULL COMMENT '下级平台申请发起',
  `statsicTime` datetime DEFAULT NULL,
  PRIMARY KEY (`statsicId`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='广播数目统计表';

-- ----------------------------
-- Table structure for `bc_stat_broadcast_area`
-- ----------------------------
DROP TABLE IF EXISTS `bc_stat_broadcast_area`;
CREATE TABLE `bc_stat_broadcast_area` (
  `statBdAreaId` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '广播数据分区域统计ID',
  `statsicId` bigint(11) NOT NULL COMMENT '广播数据统计ID',
  `areaCode` varchar(20) DEFAULT NULL COMMENT '管辖区域代码',
  `areaName` varchar(100) DEFAULT NULL COMMENT '管辖区域名称',
  `totalNum` bigint(11) DEFAULT NULL COMMENT '广播总次数',
  `commonNum` bigint(11) DEFAULT NULL COMMENT '日常广播次数',
  `redNum` bigint(11) DEFAULT NULL COMMENT 'Ⅰ级事件应急广播',
  `orangeNum` bigint(11) DEFAULT NULL COMMENT 'Ⅱ级事件应急广播',
  `yellowNum` bigint(11) DEFAULT NULL COMMENT 'Ⅲ级事件应急广播',
  `blueNum` bigint(11) DEFAULT NULL COMMENT 'Ⅳ级事件应急广播',
  PRIMARY KEY (`statBdAreaId`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8 COMMENT='广播数目分区统计表';

-- ----------------------------
-- Table structure for `bc_stat_ebd`
-- ----------------------------
DROP TABLE IF EXISTS `bc_stat_ebd`;
CREATE TABLE `bc_stat_ebd` (
  `statsicId` bigint(11) NOT NULL AUTO_INCREMENT,
  `statsicTime` datetime DEFAULT NULL COMMENT '统计时间戳',
  `sendFlag` tinyint(1) DEFAULT NULL COMMENT '业务数据包标识（1：接收 2：发送）',
  `totalNum` bigint(11) DEFAULT NULL COMMENT '业务数据包总数',
  `ebmNum` bigint(11) DEFAULT NULL COMMENT '应急广播消息数目',
  `ebmStatReqNum` bigint(11) DEFAULT NULL COMMENT '应急广播消息播发状态查询数目',
  `ebmStatResNum` bigint(11) DEFAULT NULL COMMENT '应急广播消息播发状态反馈数目',
  `omdReqNum` bigint(11) DEFAULT NULL COMMENT '运维数据请求数目',
  `ebrPSInfoNum` bigint(11) DEFAULT NULL COMMENT '应急广播平台信息数目',
  `ebrSTInfoNum` bigint(11) DEFAULT NULL COMMENT '台站前端信息数目',
  `ebrASInfoNum` bigint(11) DEFAULT NULL COMMENT '消息接收设备信息数目',
  `ebrBSInfoNum` bigint(11) DEFAULT NULL COMMENT '播出系统信息数目',
  `ebrDTInfoNum` bigint(11) DEFAULT NULL COMMENT '平台设备及终端信息数目',
  `ebmBrdLogNum` bigint(11) DEFAULT NULL COMMENT '播发记录数目',
  `ebrPSStateNum` bigint(11) DEFAULT NULL COMMENT '应急广播平台状态数目',
  `ebrASStateNum` bigint(11) DEFAULT NULL COMMENT '消息接收设备状态数目',
  `ebrBSStateNum` bigint(11) DEFAULT NULL COMMENT '播出系统状态数目',
  `ebrDTStateNum` bigint(11) DEFAULT NULL COMMENT '平台设备及终端状态数目',
  `statsicType` tinyint(4) DEFAULT NULL COMMENT '1-日，2-周，3-月，4-季度，5-半年，6-年度',
  `statsicDuation` varchar(30) DEFAULT NULL COMMENT 'x年x月x日，x年x周，x年x月，x年上半年/下半年，x年',
  PRIMARY KEY (`statsicId`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='业务数据包分类统计表';

-- ----------------------------
-- Table structure for `bc_stat_ebm`
-- ----------------------------
DROP TABLE IF EXISTS `bc_stat_ebm`;
CREATE TABLE `bc_stat_ebm` (
  `statsicId` bigint(11) NOT NULL AUTO_INCREMENT,
  `statsicTime` datetime DEFAULT NULL COMMENT '统计时间戳',
  `totalNum` bigint(11) DEFAULT NULL COMMENT '应急消息总数',
  `succeededNum` bigint(11) DEFAULT NULL COMMENT '播发成功数目',
  `failedNum` bigint(11) DEFAULT NULL COMMENT '播发失败数目',
  `unstatedNum` bigint(11) DEFAULT NULL COMMENT '未知状态数目',
  `noprogressNum` bigint(11) DEFAULT NULL COMMENT '未处理数目',
  `timetogoNum` bigint(11) DEFAULT NULL COMMENT '等待播发数目',
  `inprogressNum` bigint(11) DEFAULT NULL COMMENT '正在播发数目',
  `cancelledNum` bigint(11) DEFAULT NULL COMMENT '播发取消数目',
  `statsicType` tinyint(4) DEFAULT NULL COMMENT '1-日，2-周，3-月，4-季度，5-半年，6-年度',
  `statsicDuation` varchar(30) DEFAULT NULL COMMENT 'x年x月x日，x年x周，x年x月，x年上半年/下半年，x年',
  PRIMARY KEY (`statsicId`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8 COMMENT='应急消息播发统计表';

-- ----------------------------
-- Table structure for `bc_stat_scheme_effect`
-- ----------------------------
DROP TABLE IF EXISTS `bc_stat_scheme_effect`;
CREATE TABLE `bc_stat_scheme_effect` (
  `statsicId` int(11) NOT NULL AUTO_INCREMENT,
  `statsicTime` datetime DEFAULT NULL COMMENT '统计时间戳',
  `schemeId` int(20) DEFAULT NULL COMMENT '调度方案ID',
  `schemeTitle` varchar(100) DEFAULT NULL COMMENT '方案标题',
  `areaSizeExpect` int(11) DEFAULT NULL COMMENT '预计覆盖面积，单位平方米',
  `areaSizeReal` int(11) DEFAULT NULL COMMENT '实际覆盖面积，单位平方米',
  `populationExpect` int(11) DEFAULT NULL COMMENT '预计覆盖人口，单位人',
  `populationReal` int(11) DEFAULT NULL COMMENT '实际覆盖人口，单位人',
  `stNum` int(11) DEFAULT NULL COMMENT '台站数目',
  `stAreaSizeExpect` int(11) DEFAULT NULL COMMENT '台站预计覆盖面积，单位平方米',
  `stAreaSizeReal` int(11) DEFAULT NULL COMMENT '台站实际覆盖面积，单位平方米',
  `stResponseSpeed` int(11) DEFAULT NULL COMMENT '台站响应速度，单位秒',
  `asNum` int(11) DEFAULT NULL COMMENT '消息接收设备数目',
  `asAreaSizeExpect` int(11) DEFAULT NULL COMMENT '消息接收设备预计覆盖面积，单位平方米',
  `asAreaSizeReal` int(11) DEFAULT NULL COMMENT '消息接收设备实际覆盖面积，单位平方米',
  `asResponseSpeed` int(11) DEFAULT NULL COMMENT '消息接收设备响应速度，单位秒',
  `psNum` int(11) DEFAULT NULL COMMENT '应急广播平台数目',
  `psAreaSizeExpect` int(11) DEFAULT NULL COMMENT '应急广播平台预计覆盖面积，单位平方米',
  `psAreaSizeReal` int(11) DEFAULT NULL COMMENT '应急广播平台实际覆盖面积，单位平方米',
  `psResponseSpeed` int(11) DEFAULT NULL COMMENT '应急广播平台响应速度，单位秒',
  `bsNum` int(11) DEFAULT NULL COMMENT '播出系统数目',
  `bsAreaSizeExpect` int(11) DEFAULT NULL COMMENT '播出系统预计覆盖面积，单位平方米',
  `bsAreaSizeReal` int(11) DEFAULT NULL COMMENT '播出系统实际覆盖面积，单位平方米',
  `bsResponseSpeed` int(11) DEFAULT NULL COMMENT '播出系统响应速度，单位秒',
  PRIMARY KEY (`statsicId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='调度方案效果统计表';

-- ----------------------------
-- Records of bc_stat_scheme_effect
-- ----------------------------

-- ----------------------------
-- Table structure for `bc_worktime`
-- ----------------------------
DROP TABLE IF EXISTS `bc_worktime`;
CREATE TABLE `bc_worktime` (
  `id` varchar(64) NOT NULL,
  `weekDay` tinyint(4) NOT NULL COMMENT '1-星期一，2-星期二，3-星期三，4-星期四， 5-星期五， 6-星期六， 7-星期日',
  `startTime` varchar(8) NOT NULL COMMENT '开始时间，固定格式HH:mm:ss',
  `stopTime` varchar(8) NOT NULL COMMENT '结束时间，固定格式HH:mm:ss',
  `remark` varchar(128) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_index01` (`weekDay`,`startTime`,`stopTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工作时间表';

-- ----------------------------
-- Records of bc_worktime
-- ----------------------------
INSERT INTO `bc_worktime` VALUES ('7711482390444547', '1', '19:00:00', '21:00:00', '晚间黄金时段');
INSERT INTO `bc_worktime` VALUES ('7711482390607939', '1', '21:00:00', '23:00:00', '深夜新闻');
INSERT INTO `bc_worktime` VALUES ('7711482396050601', '2', '08:00:00', '18:00:00', '正常工作时间');

-- ----------------------------
-- Table structure for `bc_access_account`
-- ----------------------------
DROP TABLE IF EXISTS `bc_access_account`;
CREATE TABLE `bc_access_account` (
  `account` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '平台账号',
  `salt` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '盐',
  `password` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '密码',
  PRIMARY KEY (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='平台接入账号表';

DROP TABLE IF EXISTS `bc_info_access`;
CREATE TABLE `bc_info_access` (
  `infoId` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `relatedEbmId` varchar(64) NOT NULL COMMENT '关联的消息id or 节目id',
  `msgType` int(8) DEFAULT NULL COMMENT '消息类型',
  `msgSource` int(8) DEFAULT NULL COMMENT '消息来源(1:上级平台 2:自制作应急信息 3:其他第三方预警部门)',
  `sendName` varchar(100) DEFAULT NULL COMMENT '发布机构名称',
  `senderCode` varchar(50) DEFAULT NULL COMMENT '发布机构编码',
  `sendTime` datetime DEFAULT NULL COMMENT '发布时间',
  `eventType` varchar(10) DEFAULT NULL COMMENT '事件类型编码',
  `severity` tinyint(4) DEFAULT NULL COMMENT '事件级别',
  `startTime` datetime DEFAULT NULL COMMENT '播发开始时间',
  `endTime` datetime DEFAULT NULL COMMENT '播发结束时间',
  `infoType` int(8) DEFAULT '1' COMMENT '信息类型（1:应急 2:日常 3:演练）',
  `msgLanguageCode` varchar(10) DEFAULT NULL COMMENT '语种代码',
  `msgTitle` varchar(100) DEFAULT NULL COMMENT '消息标题文本',
  `msgDesc` varchar(500) DEFAULT NULL COMMENT '消息内容文本',
  `areaCode` varchar(2000) DEFAULT NULL COMMENT '覆盖区域编码',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `auditResult` tinyint(4) DEFAULT NULL COMMENT '审核结果(0:未审核 1:审核通过 2:审核不通过)',
  `auditOpinion` varchar(100) DEFAULT NULL COMMENT '审核意见',
  `auditType` tinyint(4) DEFAULT NULL COMMENT '审核类型(1:人工审核、2:自动审核)',
  `auditTime` datetime DEFAULT NULL COMMENT '审核时间',
  `auditor` varchar(32) DEFAULT NULL COMMENT '审核人',
  PRIMARY KEY (`infoId`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='应急广播信息接入表';



-- ----------------------------
-- View structure for `v_bc_ebm_dispatch_info`
-- ----------------------------
DROP VIEW IF EXISTS `v_bc_ebm_dispatch_info`;
CREATE ALGORITHM=UNDEFINED DEFINER=`skip-grants user`@`skip-grants host` SQL SECURITY INVOKER VIEW `v_bc_ebm_dispatch_info` AS select `bc_ebm_dispatch`.`dispatchId` AS `dispatchId`,`bc_ebm_dispatch`.`ebmId` AS `ebmId`,`bc_ebm`.`senderCode` AS `senderCode`,`bc_ebm`.`sendName` AS `sendName`,`bc_ebm`.`msgType` AS `msgType`,`bc_ebm`.`severity` AS `severity`,`bc_ebm`.`msgTitle` AS `msgTitle`,`bc_ebm`.`msgDesc` AS `msgDesc`,`bc_ebm_dispatch`.`psEbrId` AS `psEbrId`,`bc_ebr_platform`.`psEbrName` AS `psEbrName`,`bc_ebm_dispatch`.`bsEbrId` AS `bsEbrId`,`bc_ebr_broadcast`.`bsName` AS `bsName`,`bc_ebm`.`createTime` AS `createTime`,`bc_ebm`.`startTime` AS `startTime`,`bc_ebm`.`endTime` AS `endTime`,`bc_ebr_platform`.`areaCode` AS `PsAreaCode`,`bc_ebr_broadcast`.`areaCode` AS `BsAreaCode`,`bc_ebm`.`sendTime` AS `sendTime` from (((`bc_ebm_dispatch` left join `bc_ebm` on((`bc_ebm_dispatch`.`ebmId` = `bc_ebm`.`ebmId`))) left join `bc_ebr_platform` on((`bc_ebm_dispatch`.`psEbrId` = `bc_ebr_platform`.`psEbrId`))) left join `bc_ebr_broadcast` on((`bc_ebm_dispatch`.`bsEbrId` = `bc_ebr_broadcast`.`bsEbrId`))) ;

-- ----------------------------
-- View structure for `v_bc_ebr_adaptor_info`
-- ----------------------------
DROP VIEW IF EXISTS `v_bc_ebr_adaptor_info`;
CREATE ALGORITHM=UNDEFINED DEFINER=`skip-grants user`@`skip-grants host` SQL SECURITY INVOKER VIEW `v_bc_ebr_adaptor_info` AS select `bc_ebr_adaptor`.`asEbrId` AS `asEbrId`,`bc_ebr_adaptor`.`asEbrName` AS `asEbrName`,`bc_ebr_adaptor`.`asUrl` AS `asUrl`,`bc_ebr_adaptor`.`asType` AS `asType`,`bc_ebr_adaptor`.`relatedRsEbrId` AS `relatedRsEbrId`,`bc_ebr_adaptor`.`relatedPsEbrId` AS `relatedPsEbrId`,`bc_ebr_adaptor`.`areaCode` AS `areaCode`,`bc_ebr_adaptor`.`longitude` AS `longitude`,`bc_ebr_adaptor`.`latitude` AS `latitude`,`bc_ebr_adaptor`.`asState` AS `asState`,`bc_ebr_adaptor`.`createTime` AS `createTime`,`bc_ebr_adaptor`.`updateTime` AS `updateTime`,`bc_ebr_adaptor`.`syncFlag` AS `syncFlag`,`bc_ebr_adaptor`.`param1` AS `param1`,`bc_ebr_adaptor`.`param2` AS `param2`,`bc_region_area`.`areaName` AS `areaName`,`bc_ebr_station`.`stationName` AS `stationName` from ((`bc_ebr_adaptor` left join `bc_ebr_station` on((`bc_ebr_adaptor`.`relatedRsEbrId` = `bc_ebr_station`.`stationEbrId`))) left join `bc_region_area` on((`bc_ebr_adaptor`.`areaCode` = `bc_region_area`.`areaCode`))) ;

-- ----------------------------
-- View structure for `v_bc_ebr_broadcast_info`
-- ----------------------------
DROP VIEW IF EXISTS `v_bc_ebr_broadcast_info`;
CREATE ALGORITHM=UNDEFINED DEFINER=`skip-grants user`@`skip-grants host` SQL SECURITY INVOKER VIEW `v_bc_ebr_broadcast_info` AS select `bc_ebr_broadcast`.`bsEbrId` AS `bsEbrId`,`bc_ebr_broadcast`.`bsName` AS `bsName`,`bc_ebr_broadcast`.`bsUrl` AS `bsUrl`,`bc_ebr_broadcast`.`bsType` AS `bsType`,`bc_ebr_broadcast`.`longitude` AS `longitude`,`bc_ebr_broadcast`.`latitude` AS `latitude`,`bc_ebr_broadcast`.`square` AS `square`,`bc_ebr_broadcast`.`areaCode` AS `areaCode`,`bc_ebr_broadcast`.`population` AS `population`,`bc_ebr_broadcast`.`languageCode` AS `languageCode`,`bc_ebr_broadcast`.`equipRoom` AS `equipRoom`,`bc_ebr_broadcast`.`radioChannelName` AS `radioChannelName`,`bc_ebr_broadcast`.`radioFreq` AS `radioFreq`,`bc_ebr_broadcast`.`radioPower` AS `radioPower`,`bc_ebr_broadcast`.`backup` AS `backup`,`bc_ebr_broadcast`.`autoSwitch` AS `autoSwitch`,`bc_ebr_broadcast`.`remoteControl` AS `remoteControl`,`bc_ebr_broadcast`.`experiment` AS `experiment`,`bc_ebr_broadcast`.`tvChannelName` AS `tvChannelName`,`bc_ebr_broadcast`.`tvFreq` AS `tvFreq`,`bc_ebr_broadcast`.`programNum` AS `programNum`,`bc_ebr_broadcast`.`tvChannelNum` AS `tvChannelNum`,`bc_ebr_broadcast`.`bsState` AS `bsState`,`bc_ebr_broadcast`.`relatedPsEbrId` AS `relatedPsEbrId`,`bc_ebr_broadcast`.`relatedRsEbrId` AS `relatedRsEbrId`,`bc_ebr_broadcast`.`relatedAsEbrId` AS `relatedAsEbrId`,`bc_ebr_broadcast`.`createTime` AS `createTime`,`bc_ebr_broadcast`.`updateTime` AS `updateTime`,`bc_ebr_broadcast`.`syncFlag` AS `syncFlag`,`bc_ebr_adaptor`.`asEbrName` AS `asEbrName`,`bc_ebr_platform`.`psEbrName` AS `psEbrName`,`bc_ebr_station`.`stationName` AS `stationName`,`bc_region_area`.`areaName` AS `areaName` from ((((`bc_ebr_broadcast` left join `bc_ebr_adaptor` on((`bc_ebr_broadcast`.`relatedAsEbrId` = `bc_ebr_adaptor`.`asEbrId`))) left join `bc_ebr_platform` on((`bc_ebr_broadcast`.`relatedPsEbrId` = `bc_ebr_platform`.`psEbrId`))) left join `bc_ebr_station` on((`bc_ebr_broadcast`.`relatedRsEbrId` = `bc_ebr_station`.`stationEbrId`))) left join `bc_region_area` on((`bc_ebr_broadcast`.`areaCode` = `bc_region_area`.`areaCode`))) ;

-- ----------------------------
-- View structure for `v_bc_ebr_platform_info`
-- ----------------------------
DROP VIEW IF EXISTS `v_bc_ebr_platform_info`;
CREATE ALGORITHM=UNDEFINED DEFINER=`skip-grants user`@`skip-grants host` SQL SECURITY INVOKER VIEW `v_bc_ebr_platform_info` AS select `bc_ebr_platform`.`psEbrId` AS `psEbrId`,`bc_ebr_platform`.`psUrl` AS `psUrl`,`bc_ebr_platform`.`psEbrName` AS `psEbrName`,`bc_ebr_platform`.`psState` AS `psState`,`bc_ebr_platform`.`areaCode` AS `areaCode`,`bc_ebr_platform`.`psType` AS `psType`,`bc_ebr_platform`.`parentPsEbrId` AS `parentPsEbrId`,`bc_ebr_platform`.`psAddress` AS `psAddress`,`bc_ebr_platform`.`contact` AS `contact`,`bc_ebr_platform`.`phoneNumber` AS `phoneNumber`,`bc_ebr_platform`.`longitude` AS `longitude`,`bc_ebr_platform`.`latitude` AS `latitude`,`bc_ebr_platform`.`platLevel` AS `platLevel`,`bc_ebr_platform`.`createTime` AS `createTime`,`bc_ebr_platform`.`updateTime` AS `updateTime`,`bc_ebr_platform`.`syncFlag` AS `syncFlag`,`bc_region_area`.`areaName` AS `areaName`,`parentplatform`.`psEbrName` AS `parentPsEbrName` from ((`bc_ebr_platform` left join `bc_region_area` on((`bc_ebr_platform`.`areaCode` = `bc_region_area`.`areaCode`))) left join `bc_ebr_platform` `parentplatform` on((`bc_ebr_platform`.`parentPsEbrId` = `parentplatform`.`psEbrId`))) ;

-- ----------------------------
-- View structure for `v_bc_ebr_station_info`
-- ----------------------------
DROP VIEW IF EXISTS `v_bc_ebr_station_info`;
CREATE ALGORITHM=UNDEFINED DEFINER=`skip-grants user`@`skip-grants host` SQL SECURITY INVOKER VIEW `v_bc_ebr_station_info` AS select `bc_ebr_station`.`stationEbrId` AS `stationEbrId`,`bc_ebr_station`.`stationName` AS `stationName`,`bc_ebr_station`.`stationAddress` AS `stationAddress`,`bc_ebr_station`.`contact` AS `contact`,`bc_ebr_station`.`phoneNumber` AS `phoneNumber`,`bc_ebr_station`.`longitude` AS `longitude`,`bc_ebr_station`.`latitude` AS `latitude`,`bc_ebr_station`.`areaCode` AS `areaCode`,`bc_ebr_station`.`relatedPsEbrId` AS `relatedPsEbrId`,`bc_ebr_station`.`createTime` AS `createTime`,`bc_ebr_station`.`updateTime` AS `updateTime`,`bc_ebr_station`.`syncFlag` AS `syncFlag`,`bc_ebr_platform`.`psEbrName` AS `psEbrName`,`bc_region_area`.`areaName` AS `areaName`,`bc_ebr_station`.`stationType` AS `stationType` from ((`bc_ebr_station` left join `bc_ebr_platform` on((`bc_ebr_station`.`relatedPsEbrId` = `bc_ebr_platform`.`psEbrId`))) left join `bc_region_area` on((`bc_ebr_station`.`areaCode` = `bc_region_area`.`areaCode`))) ;

-- ----------------------------
-- View structure for `v_bc_ebr_terminal_info`
-- ----------------------------
DROP VIEW IF EXISTS `v_bc_ebr_terminal_info`;
CREATE ALGORITHM=UNDEFINED DEFINER=`skip-grants user`@`skip-grants host` SQL SECURITY INVOKER VIEW `v_bc_ebr_terminal_info` AS select `bc_ebr_terminal`.`terminalEbrId` AS `terminalEbrId`,`bc_ebr_terminal`.`terminalEbrName` AS `terminalEbrName`,`bc_ebr_terminal`.`relatedPsEbrId` AS `relatedPsEbrId`,`bc_ebr_terminal`.`longitude` AS `longitude`,`bc_ebr_terminal`.`latitude` AS `latitude`,`bc_ebr_terminal`.`terminalType` AS `terminalType`,`bc_ebr_terminal`.`terminalState` AS `terminalState`,`bc_ebr_terminal`.`createTime` AS `createTime`,`bc_ebr_terminal`.`updateTime` AS `updateTime`,`bc_ebr_terminal`.`syncFlag` AS `syncFlag`,`bc_ebr_terminal`.`param1` AS `param1`,`bc_ebr_terminal`.`param2` AS `param2`,`bc_ebr_platform`.`psEbrName` AS `psEbrName` from (`bc_ebr_terminal` left join `bc_ebr_platform` on((`bc_ebr_terminal`.`relatedPsEbrId` = `bc_ebr_platform`.`psEbrId`))) ;

-- ----------------------------
-- View structure for `v_bc_scheme_flow_info`
-- ----------------------------
DROP VIEW IF EXISTS `v_bc_scheme_flow_info`;
CREATE ALGORITHM=UNDEFINED DEFINER=`skip-grants user`@`skip-grants host` SQL SECURITY INVOKER VIEW `v_bc_scheme_flow_info` AS select `bc_scheme`.`schemeId` AS `schemeId`,`bc_scheme`.`schemeTitle` AS `schemeTitle`,`bc_scheme`.`createTime` AS `createTime`,`bc_dispatch_flow`.`flowStage` AS `flowStage`,`bc_dispatch_flow`.`flowState` AS `flowState`,`bc_ebm`.`areaCode` AS `areaCode`,`bc_program`.`programLevel` AS `programLevel`,`bc_program`.`ebmEventDesc` AS `ebmEventDesc`,`bc_ebm`.`severity` AS `severity`,`bc_ebm`.`sendTime` AS `sendTime`,`bc_ebm`.`senderCode` AS `senderCode`,`bc_ebm`.`sendName` AS `sendName`,`bc_scheme`.`programId` AS `programId`,`bc_scheme`.`ebmId` AS `ebmId`,`bc_program`.`releaseOrgCode` AS `releaseOrgCode`,`bc_program`.`releaseOrgName` AS `releaseOrgName`,`bc_program`.`programName` AS `programName`,`bc_ebm`.`msgTitle` AS `msgTitle`,`bc_program`.`programType` AS `programType`,`bc_ebm`.`eventType` AS `eventType` from (((`bc_scheme` left join `bc_dispatch_flow` on((`bc_scheme`.`flowId` = `bc_dispatch_flow`.`flowId`))) left join `bc_program` on((`bc_scheme`.`programId` = `bc_program`.`programId`))) left join `bc_ebm` on((`bc_scheme`.`ebmId` = `bc_ebm`.`ebmId`))) ;



DROP TRIGGER IF EXISTS ps_trigger;
CREATE TRIGGER `ps_trigger` BEFORE UPDATE ON `bc_ebr_platform` FOR EACH ROW BEGIN
    IF NEW.psState<>OLD.psState then
		    set NEW.statusSyncFlag = 1;
   end if;
END;


DROP TRIGGER IF EXISTS bs_trigger;
CREATE TRIGGER `bs_trigger` BEFORE UPDATE ON `bc_ebr_broadcast` FOR EACH ROW BEGIN
    IF NEW.bsState<>OLD.bsState then
		    set NEW.statusSyncFlag = 1;
   end if;
END;