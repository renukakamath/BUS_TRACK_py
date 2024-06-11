/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 10.4.24-MariaDB : Database - py_child_at_school_sngist_mca
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`py_child_at_school_sngist_mca` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `py_child_at_school_sngist_mca`;

/*Table structure for table `app_rating` */

DROP TABLE IF EXISTS `app_rating`;

CREATE TABLE `app_rating` (
  `rating_id` int(11) NOT NULL AUTO_INCREMENT,
  `sender_id` int(11) DEFAULT NULL,
  `rate` varchar(50) DEFAULT NULL,
  `review` varchar(1000) DEFAULT NULL,
  `date_time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`rating_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `app_rating` */

insert  into `app_rating`(`rating_id`,`sender_id`,`rate`,`review`,`date_time`) values 
(1,13,'3.5','  Bf','2022-06-24');

/*Table structure for table `assign_amount` */

DROP TABLE IF EXISTS `assign_amount`;

CREATE TABLE `assign_amount` (
  `assign_id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL,
  `bus_id` int(11) DEFAULT NULL,
  `amount` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`assign_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

/*Data for the table `assign_amount` */

insert  into `assign_amount`(`assign_id`,`student_id`,`bus_id`,`amount`,`date`) values 
(1,7,3,'1000','2022-06-15'),
(2,8,3,'250','2022-06-24'),
(3,7,3,'1500','2022-07-04');

/*Table structure for table `assign_bus` */

DROP TABLE IF EXISTS `assign_bus`;

CREATE TABLE `assign_bus` (
  `assign_bus_id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL,
  `bus_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`assign_bus_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

/*Data for the table `assign_bus` */

insert  into `assign_bus`(`assign_bus_id`,`student_id`,`bus_id`) values 
(1,7,3),
(2,8,3);

/*Table structure for table `attendance` */

DROP TABLE IF EXISTS `attendance`;

CREATE TABLE `attendance` (
  `attendance_id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `in` varchar(50) DEFAULT NULL,
  `out` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`attendance_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

/*Data for the table `attendance` */

insert  into `attendance`(`attendance_id`,`student_id`,`date`,`in`,`out`) values 
(1,7,'2022-06-22','12:16:50','NA'),
(2,7,'2022-07-04','11:44:05','NA');

/*Table structure for table `bus` */

DROP TABLE IF EXISTS `bus`;

CREATE TABLE `bus` (
  `bus_id` int(11) NOT NULL AUTO_INCREMENT,
  `route_id` int(11) DEFAULT NULL,
  `register_number` varchar(100) DEFAULT NULL,
  `seat_capacity` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`bus_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

/*Data for the table `bus` */

insert  into `bus`(`bus_id`,`route_id`,`register_number`,`seat_capacity`) values 
(1,1,'KM234561','20'),
(2,1,'KM234563','23'),
(3,1,'KL42M2584','33');

/*Table structure for table `buslocation` */

DROP TABLE IF EXISTS `buslocation`;

CREATE TABLE `buslocation` (
  `buslocation_id` int(11) NOT NULL AUTO_INCREMENT,
  `bus_id` int(11) DEFAULT NULL,
  `latitude` varchar(100) DEFAULT NULL,
  `longitude` varchar(100) DEFAULT NULL,
  `location_date` varchar(100) DEFAULT NULL,
  `location_time` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`buslocation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `buslocation` */

insert  into `buslocation`(`buslocation_id`,`bus_id`,`latitude`,`longitude`,`location_date`,`location_time`) values 
(1,3,'9.9763282','76.2862461','2022-06-22','16:18:29');

/*Table structure for table `driver` */

DROP TABLE IF EXISTS `driver`;

CREATE TABLE `driver` (
  `driver_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `bus_id` int(11) DEFAULT NULL,
  `firstname` varchar(100) DEFAULT NULL,
  `lastname` varchar(100) DEFAULT NULL,
  `photo` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`driver_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `driver` */

insert  into `driver`(`driver_id`,`login_id`,`bus_id`,`firstname`,`lastname`,`photo`,`phone`) values 
(1,7,3,'Benny','Kumar','static/9e851dfe-a758-4dbb-8c63-0dc47d2b8ebdpexels-artem-podrez-7495747.jpg','9630011222');

/*Table structure for table `driver_notification` */

DROP TABLE IF EXISTS `driver_notification`;

CREATE TABLE `driver_notification` (
  `d_notification_id` int(11) NOT NULL AUTO_INCREMENT,
  `driver_id` int(11) DEFAULT NULL,
  `details` varchar(500) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`d_notification_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

/*Data for the table `driver_notification` */

insert  into `driver_notification`(`d_notification_id`,`driver_id`,`details`,`date`) values 
(1,1,'Sorry','2022-06-22'),
(2,1,'N, fjf','2022-06-24');

/*Table structure for table `feedback` */

DROP TABLE IF EXISTS `feedback`;

CREATE TABLE `feedback` (
  `feedback_id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL,
  `feedback` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`feedback_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `feedback` */

/*Table structure for table `late_information` */

DROP TABLE IF EXISTS `late_information`;

CREATE TABLE `late_information` (
  `late_id` int(11) NOT NULL AUTO_INCREMENT,
  `sender_id` int(11) DEFAULT NULL,
  `receiver_id` int(11) DEFAULT NULL,
  `details` varchar(500) DEFAULT NULL,
  `reply` varchar(500) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`late_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

/*Data for the table `late_information` */

insert  into `late_information`(`late_id`,`sender_id`,`receiver_id`,`details`,`reply`,`date`) values 
(1,13,7,'Nbgn','Fjf','2022-06-22'),
(2,13,7,'Ggg','OK fine','2022-06-22');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `usertype` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4;

/*Data for the table `login` */

insert  into `login`(`login_id`,`username`,`password`,`usertype`) values 
(1,'admin','admin','admin'),
(4,'a','a','student'),
(7,'b','b','driver'),
(8,'st','st','student'),
(9,'ss','ss','student'),
(10,'Nobis ipsa','Pa$$w0rd!','student'),
(11,'Voluptate ','Pa$$w0rd!','student'),
(12,'Eaque quia','Pa$$w0rd!','student'),
(13,'ww','ww','student'),
(14,'Pa','pa','student');

/*Table structure for table `message` */

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `message_id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL,
  `msg_des` varchar(100) DEFAULT NULL,
  `reply_des` varchar(100) DEFAULT NULL,
  `msg_date` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `message` */

/*Table structure for table `payment` */

DROP TABLE IF EXISTS `payment`;

CREATE TABLE `payment` (
  `payment_id` int(11) NOT NULL AUTO_INCREMENT,
  `assign_id` int(11) DEFAULT NULL,
  `amount` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`payment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

/*Data for the table `payment` */

insert  into `payment`(`payment_id`,`assign_id`,`amount`,`date`) values 
(1,1,'1000','2022-06-22'),
(2,3,'1500','2022-07-04');

/*Table structure for table `place` */

DROP TABLE IF EXISTS `place`;

CREATE TABLE `place` (
  `place_id` int(11) NOT NULL AUTO_INCREMENT,
  `place_name` varchar(100) DEFAULT NULL,
  `latitude` varchar(100) DEFAULT NULL,
  `longitude` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`place_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

/*Data for the table `place` */

insert  into `place`(`place_id`,`place_name`,`latitude`,`longitude`) values 
(4,'Kannur','11.868287974039434','75.37005657081163');

/*Table structure for table `routes` */

DROP TABLE IF EXISTS `routes`;

CREATE TABLE `routes` (
  `route_id` int(11) NOT NULL AUTO_INCREMENT,
  `from_place_id` int(11) DEFAULT NULL,
  `to_place_id` int(11) DEFAULT NULL,
  `route_name` varchar(100) DEFAULT NULL,
  `route_details` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`route_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `routes` */

insert  into `routes`(`route_id`,`from_place_id`,`to_place_id`,`route_name`,`route_details`) values 
(1,4,4,'aaaaaaa','bbb');

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `student_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `route_id` int(11) DEFAULT NULL,
  `place_id` int(11) DEFAULT NULL,
  `fname` varchar(100) DEFAULT NULL,
  `lname` varchar(100) DEFAULT NULL,
  `course` varchar(100) DEFAULT NULL,
  `batch` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `parent_number` varchar(20) DEFAULT NULL,
  `qr_code` varchar(500) DEFAULT NULL,
  `students_status` varchar(100) DEFAULT NULL,
  `student_image` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

/*Data for the table `student` */

insert  into `student`(`student_id`,`login_id`,`route_id`,`place_id`,`fname`,`lname`,`course`,`batch`,`phone`,`parent_number`,`qr_code`,`students_status`,`student_image`) values 
(1,4,1,2,'Alen','L','computer','cse2019','9977664411',NULL,NULL,'pending',NULL),
(2,8,1,2,'Umer Ahsan Bin','Roshan','cs','s8','9946216505',NULL,NULL,'pending',NULL),
(3,9,1,4,'Umer Ahsan Bin','Roshan','cs','s8','9946216505',NULL,NULL,'pending',NULL),
(4,10,1,4,'Ut autem exercitatio','Autem culpa et quibu','Qui dolore tempore ','Repudiandae aliquip ','7412589630',NULL,'static/qr_codee50aceb0-c20d-4e13-a603-2bc510638630.png','pending',NULL),
(5,11,1,4,'In lorem dolores vol','Qui dicta minima et ','Nam sequi voluptate ','Amet quo culpa inci','9874563210',NULL,'static/qr_code64b9c275-a8d3-4c28-a75c-dc6dbc60a302.png','pending',NULL),
(6,12,1,4,'Fugiat consectetur ','Reprehenderit duis ','Adipisci cupidatat o','Deserunt dolores acc','9632587410',NULL,'static/qr_code/b6b15677-e4a6-4046-b533-734274ee5c7b.png','pending',NULL),
(7,13,1,4,'Sed eligendi enim ad','Delectus dolore rei','Consequatur Aut con','Aspernatur praesenti','8521479630',NULL,'static/qr_code/ce8ba15d-0f02-45c9-954d-a5b76c515462.png','pending','static/qr_code/ce8ba15d-0f02-45c9-954d-a5b76c515462.png'),
(8,14,1,4,'Paul','Pj','Ba','2022','9876543210',NULL,'static/qr_code/2b6b01a2-ab56-45b0-aaf9-5ac5f63655aa.png','pending',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
