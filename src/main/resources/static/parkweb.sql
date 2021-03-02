/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.19 : Database - parkweb
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`parkweb` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `parkweb`;

/*Table structure for table `carorder` */

DROP TABLE IF EXISTS `carorder`;

CREATE TABLE `carorder` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `province` varchar(25) NOT NULL,
  `carNumber` varchar(25) NOT NULL,
  `customerName` varchar(25) DEFAULT NULL,
  `customerPhone` varchar(25) DEFAULT NULL,
  `startTime` datetime DEFAULT NULL,
  `endTime` datetime DEFAULT NULL,
  `cost` double(10,2) DEFAULT NULL,
  `time` double(10,2) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `cid` int(11) DEFAULT NULL,
  `sid` int(11) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  `is` int(11) DEFAULT '0' COMMENT '是否校内',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

/*Data for the table `carorder` */

insert  into `carorder`(`id`,`province`,`carNumber`,`customerName`,`customerPhone`,`startTime`,`endTime`,`cost`,`time`,`state`,`cid`,`sid`,`uid`,`is`) values (20,'粤','PAQ177','wwl','13690935027','2020-12-09 15:30:18','2020-12-09 15:31:08',0.00,0.00,1,15,223,9,0),(21,'粤','PAQ177','','','2020-12-09 15:45:30',NULL,0.00,0.00,0,12,166,6,0),(22,'粤','A88888','','','2020-12-09 15:46:05','2020-12-14 13:19:40',47.02,117.55,1,12,167,6,0),(23,'京','A66666','','','2020-12-14 13:20:51',NULL,0.00,0.00,0,15,221,9,0),(25,'粤','PAQ178','','','2020-12-15 22:54:23',NULL,0.00,0.00,0,12,167,7,0),(26,'京','PAQ777','','','2020-12-20 23:50:31','2020-12-29 17:16:38',3350.93,209.43,1,15,222,9,1),(27,'京','A88888','','','2020-12-20 23:50:42','2020-12-20 23:51:13',0.00,0.00,1,15,223,9,0),(28,'粤','P88888','wwl','13690935027','2020-12-30 23:07:37','2020-12-30 23:10:38',0.10,0.03,1,12,1777,40,1),(29,'粤','P88888','wwl','13690935027','2020-12-30 23:11:27',NULL,0.00,0.00,0,12,1780,40,1),(30,'京','A11111','','','2020-12-30 23:51:31',NULL,0.00,0.00,0,12,168,40,0),(31,'苏','B79999','','','2020-12-31 16:47:28','2020-12-31 18:01:24',18.25,1.22,1,12,1777,40,0),(32,'赣','S88888','拍照识别','13690935027','2020-12-31 16:50:22',NULL,0.00,0.00,0,12,1779,40,0),(33,'津','A11111','','','2020-12-31 17:22:56','2020-12-31 17:58:41',8.75,0.58,1,12,1778,40,0),(34,'苏','B79999','','','2020-12-31 18:02:37',NULL,0.00,0.00,0,12,1777,40,0),(35,'陕','AL308X','','','2020-12-31 18:07:19',NULL,0.00,0.00,0,12,1778,40,0),(36,'粤','K44444','','','2020-12-31 18:27:11',NULL,0.00,0.00,0,12,1781,40,0),(37,'陕','AL307X','','','2021-02-06 15:22:47',NULL,0.00,0.00,0,12,1782,40,0);

/*Table structure for table `carspace` */

DROP TABLE IF EXISTS `carspace`;

CREATE TABLE `carspace` (
  `s_id` int(11) NOT NULL AUTO_INCREMENT,
  `s_name` varchar(100) DEFAULT NULL,
  `s_state` int(11) DEFAULT NULL,
  `s_type` int(11) DEFAULT NULL,
  `c_id` int(11) DEFAULT NULL,
  `s_code` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`s_id`),
  KEY `carspace_ibfk_1` (`c_id`),
  CONSTRAINT `carspace_ibfk_1` FOREIGN KEY (`c_id`) REFERENCES `carstation` (`c_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1789 DEFAULT CHARSET=utf8;

/*Data for the table `carspace` */

insert  into `carspace`(`s_id`,`s_name`,`s_state`,`s_type`,`c_id`,`s_code`) values (166,'D1--1',1,1,12,NULL),(167,'D1--2',1,2,12,NULL),(168,'D1--3',1,2,12,NULL),(221,'G1--1',1,1,15,NULL),(222,'G1--2',0,1,15,'aeef78'),(223,'G1--3',0,1,15,NULL),(224,'G1--4',0,2,15,NULL),(225,'G1--5',0,1,15,NULL),(226,'G1--6',0,1,15,NULL),(227,'G1--7',0,1,15,NULL),(228,'G1--8',0,1,15,NULL),(229,'G1--9',0,1,15,NULL),(230,'G1--10',0,1,15,NULL),(231,'G1--11',0,1,15,NULL),(232,'G1--12',0,1,15,NULL),(233,'G1--13',0,1,15,NULL),(234,'G1--14',0,1,15,NULL),(235,'G1--15',0,1,15,NULL),(236,'G1--16',0,1,15,NULL),(237,'G1--17',0,1,15,NULL),(238,'G1--18',0,1,15,NULL),(239,'G1--19',0,1,15,NULL),(240,'G1--20',0,1,15,NULL),(336,NULL,NULL,NULL,NULL,NULL),(337,NULL,NULL,NULL,NULL,NULL),(1727,'高级车库--1',0,1,41,'b91678'),(1728,'高级车库--2',0,1,41,'988b44'),(1729,'高级车库--3',0,1,41,'94e9f7'),(1730,'高级车库--4',0,1,41,'3bcd6a'),(1731,'高级车库--5',0,1,41,'21578d'),(1732,'高级车库--6',0,1,41,'aeef7a'),(1733,'高级车库--7',0,1,41,'d30c1c'),(1734,'高级车库--8',0,1,41,'139938'),(1735,'高级车库--9',0,1,41,'50f565'),(1736,'高级车库--10',0,1,41,'0ccbe7'),(1740,NULL,0,1,43,'2c2e92'),(1743,NULL,0,2,43,'1ce6ad'),(1744,NULL,0,2,43,'a7f2a4'),(1745,NULL,0,2,43,'a2ccaf'),(1746,NULL,0,1,43,'1fa5e2'),(1747,NULL,0,1,43,'0d70af'),(1755,'体育馆--1',0,1,45,'a50db4'),(1756,'体育馆--2',0,1,45,'a5887d'),(1757,'体育馆--3',0,1,45,'71150f'),(1758,'体育馆--4',0,1,45,'393133'),(1759,'体育馆--5',0,1,45,'05d7d2'),(1760,'体育馆--6',0,1,45,'405212'),(1761,'体育馆--7',0,1,45,'7f1c20'),(1762,'体育馆--8',0,1,45,'131c2b'),(1763,'体育馆--9',0,1,45,'0424b2'),(1764,'体育馆--10',0,1,45,'2f9ae4'),(1777,'D1--4',1,1,12,'c95bad'),(1778,'D1--5',1,1,12,'214de7'),(1779,'D1--6',1,1,12,'c790b1'),(1780,'D1--7',1,1,12,'aa96bd'),(1781,'D1--8',1,1,12,'3c8641'),(1782,'D1--9',1,1,12,'03a7af'),(1783,'D1--10',0,1,12,'fd02fb'),(1784,'D1--11',0,2,12,'ea518b'),(1785,'D1--12',0,2,12,'2c6c22'),(1786,'D1--13',0,2,12,'4ef2b2'),(1787,'D1--14',0,2,12,'649dca'),(1788,'D1--15',0,2,12,'dd0df8');

/*Table structure for table `carstation` */

DROP TABLE IF EXISTS `carstation`;

CREATE TABLE `carstation` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT,
  `c_name` varchar(25) DEFAULT NULL,
  `c_location` varchar(255) DEFAULT NULL,
  `c_description` varchar(255) DEFAULT NULL,
  `c_total` int(11) DEFAULT NULL,
  `c_code` varchar(25) NOT NULL,
  `c_price` double(10,2) DEFAULT NULL,
  `c_pricetime` double(10,2) DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;

/*Data for the table `carstation` */

insert  into `carstation`(`c_id`,`c_name`,`c_location`,`c_description`,`c_total`,`c_code`,`c_price`,`c_pricetime`) values (12,'D1','行政楼','新建车库     ',15,'e9053',15.00,1.00),(15,'G1','图书馆','容量大',20,'d17aa',20.00,1.00),(41,'高级车库','19栋','自动停车',10,'2aed9',50.00,1.00),(43,'修改2','19栋','修改车库2',6,'d48a2',20.00,0.50),(45,'体育馆','球馆','vip',10,'dc236',30.00,1.00);

/*Table structure for table `message` */

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `content` text,
  `creatTime` datetime DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Data for the table `message` */

insert  into `message`(`id`,`title`,`content`,`creatTime`,`uid`) values (2,'hello','<p>hhhh</p>','2020-12-19 15:44:10',9),(4,'期末','<p>期末放假</p>','2020-12-20 15:12:05',7),(5,'回校','<p>回校时间</p>','2020-12-20 15:13:00',7),(6,'测试公告','<p>测试</p>','2020-12-26 02:27:45',40),(9,'w','<p>1</p>','2020-12-26 02:44:24',7),(10,'3','<p>3</p>','2020-12-26 02:44:31',7),(11,'456','<p>45</p>','2020-12-26 02:47:50',7),(12,'sd','<p>输入公告内容.....</p>','2020-12-26 02:57:01',7),(14,'sd','<p>sdasd</p>','2020-12-26 22:13:24',40),(15,'五系','<p>吴曦</p>','2021-02-06 15:21:07',40);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(50) NOT NULL,
  `name` varchar(20) NOT NULL,
  `sex` int(11) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `phone` varchar(20) NOT NULL,
  `say` varchar(100) DEFAULT NULL,
  `state` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `headImg` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`code`,`username`,`password`,`name`,`sex`,`email`,`phone`,`say`,`state`,`type`,`headImg`) values (2,'77777','wuweilin','4eef1e1ea34879a2ae60c60815927ed9','weilin',1,'wuweilin@qq.com','13699999999','网络一线牵珍惜这份缘！',1,1,'https://parking777.oss-cn-heyuan.aliyuncs.com/user.png'),(4,'45678','123456','c4ded2b85cc5be82fa1d2464eba9a7d3','张三',0,'1518189682@qq.com','15560156626','美好的一天！',0,1,'https://parking777.oss-cn-heyuan.aliyuncs.com/user.png'),(7,'88888','harden','50e1f33e19a65aa9a5a0c78606ac3dd9','哈登',1,'harden@qq.com','13699998888','网络一线牵珍惜这份缘！',1,1,'https://parking777.oss-cn-heyuan.aliyuncs.com/2020/12/31/d56a144_88888.jpg'),(9,'82669','admin001','4eef1e1ea34879a2ae60c60815927ed9','WWL',0,'1518189682@qq.com','15560156626','道路千万条，安全第一条；行车不规范，亲人两行泪！',1,1,'https://parking777.oss-cn-heyuan.aliyuncs.com/2020/12/31/c5b21a7_82669.jpg'),(16,'77779','aaaaaa','0b4e7a0e5fe84ad35fb5f95b9ceeac79','haha',1,'haha@qq.com','13699998866','hhhhh',1,0,'https://parking777.oss-cn-heyuan.aliyuncs.com/user.png'),(39,'43534','admin001f','129b774a76131626c89a659214640134','haha',1,'harden@qq.com','13878787878','dsdsd',1,0,'https://parking777.oss-cn-heyuan.aliyuncs.com/user.png'),(40,'12345','aaa','47bce5c74f589f4867dbd57e9ca9f808','text',1,'harden@qq.com','13699998888','网络一线牵珍惜这份缘！',1,1,'https://parking777.oss-cn-heyuan.aliyuncs.com/2020/12/31/877c884_12345.jpg'),(42,'24824','kobekobe','6330faa7035c736eb911647c1cc87d41','kobe',0,'harden@qq.com','13999999999','美好的一天！',1,0,'https://parking777.oss-cn-heyuan.aliyuncs.com/user.png'),(43,'11133','aaa2','0ad346c93c16e85e2cb117ff1fcfada3','42412',1,'1120168471@qq.com','13690993502','网络一线牵珍惜这份缘！',1,0,'https://parking777.oss-cn-heyuan.aliyuncs.com/user.png');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
