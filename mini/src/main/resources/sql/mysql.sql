

 drop DATABASE vtc;
CREATE DATABASE vtc;

USE vtc;
CREATE TABLE `employee_t` (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT,
                              `username` varchar(40) DEFAULT NULL,
                              `password` varchar(40) DEFAULT NULL,
                              `phone` varchar(20) DEFAULT NULL,
                              `img` varchar(60) DEFAULT NULL,
                              `utype` varchar(60) DEFAULT '2',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
# type 用户类型 1学生 2管理员
CREATE TABLE `user_type_t` (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT,
                               `type` varchar(40) DEFAULT '2',
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


CREATE TABLE `order_t` (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT,
                               `fid` bigint(20) DEFAULT  NULL,
                               `sid` bigint(20) DEFAULT  NULL,
                               `rid` bigint(20) DEFAULT  NULL,
                               `status` int(0) DEFAULT '1',
                               `price` bigint(20) DEFAULT NULL,
                               `createTime` long DEFAULT NULL,
                               `updateTime` long DEFAULT NULL,
                               `createName` varchar(40) DEFAULT NULL,
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

INSERT INTO employee_t (id, username, password,utype) VALUES (10, 'admin', 'admin','2');
INSERT INTO employee_t (id, username, password,utype) VALUES (20, '1', '1','1');
INSERT INTO employee_t (id, username, password,utype) VALUES (30, '2', '2','2');
SELECT * FROM employee_t;

# 房子列表
CREATE TABLE `school_t` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT,
                             `uid` bigint(20) NOT NULL,
                              `name` varchar(40) DEFAULT NULL,
                              `alias` varchar(200) DEFAULT NULL,
                              `imgs` varchar(200) DEFAULT NULL,
                              `content` varchar(200) DEFAULT NULL,
                              `content2` varchar(200) DEFAULT NULL,
                              `content3` varchar(200) DEFAULT NULL,
                                `createTime` long DEFAULT NULL,
                                `updateTime` long DEFAULT NULL,
                              `latitude` double DEFAULT NULL,
                              `longitude` double DEFAULT NULL,
                              `des` varchar(500) DEFAULT NULL,
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

# 系统配置表
CREATE TABLE `config_t` (
                                 `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                 `name` varchar(50) DEFAULT NULL,
                                 `value` varchar(400) DEFAULT NULL,
                                 `url` varchar(200) DEFAULT NULL,
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


# 关于我们表
CREATE TABLE `system_info_t` (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT,
                               `content` varchar(1500) DEFAULT NULL,
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

 INSERT INTO system_info_t ( content) VALUES
     ( 'Vermont Technical College (Vermont Tech or VTC) was a public technical college in Vermont. Its main residential campuses were located in Randolph Center and Williston. In addition, there were regional campuses distance sites, and nursing campuses in locations throughout the state.[1]
Founded in 1866 as the Randolph Normal School, the mission of the school evolved through time, finally becoming a technical institute in 1957.[2] On July 1, 2023, VTC merged with Northern Vermont University and Castleton University to become Vermont State University.[3][4');


# drop table news_t;
# 公告表类型表
CREATE TABLE `news_type_t` (
                          `id` bigint(20) NOT NULL AUTO_INCREMENT,
                          `type` long DEFAULT null,
                          `name` varchar(50) DEFAULT NULL,
                          PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

# 公告表
CREATE TABLE `news_t` (
                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                         `createTime` long DEFAULT NULL,
                         `updateTime` long DEFAULT NULL,
                         `content` varchar(500) DEFAULT NULL,
                         `des` varchar(500) DEFAULT NULL,
                         `name` varchar(50) DEFAULT NULL,
                         `url` varchar(150) DEFAULT NULL,
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

INSERT INTO news_t ( content) VALUES
    ( 'VTC will offer language and literature courses. The application deadline is September 20, 2025. Students who need to report are requested to register quickly.');
#模型表
CREATE TABLE `stl_t` (
                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                         `name` varchar(40) DEFAULT NULL,
                         `dirName` varchar(40) DEFAULT NULL,
                         `createTime` long DEFAULT NULL,
                         `updateTime` long DEFAULT NULL,
                         `des` varchar(500) DEFAULT NULL,
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

#消息列表
CREATE TABLE `msg_t` (
                          `id` bigint(20) NOT NULL AUTO_INCREMENT,
                          `sid` bigint(20) NOT NULL,
                          `rid` bigint(20) NOT NULL,
                          `fid` bigint(20) NOT NULL,
                          `createTime` long DEFAULT NULL,
                          `updateTime` long DEFAULT NULL,
                          `des` varchar(500) DEFAULT NULL,
                          `name` varchar(50) DEFAULT NULL,
                          `rurl` varchar(150) DEFAULT NULL,
                          `surl` varchar(150) DEFAULT NULL,
                          PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
 select * from msg_t;
select  *from news_t  ;

#  drop table msg_t;
#收藏表
CREATE TABLE `save_t` (
                          `id` bigint(20) NOT NULL AUTO_INCREMENT,
                          `uid` bigint(20) NOT NULL,
                          `fid` bigint(20) NOT NULL,
                          `createTime` long DEFAULT NULL,
                          `updateTime` long DEFAULT NULL,
                          `des` varchar(500) DEFAULT NULL,
                          PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

INSERT INTO school_t ( name, uid,alias,imgs,content,content2,content3,createTime,updateTime,des,latitude,longitude) VALUES
    ( 'VTC School No.1',20, 'Spring road leak street','',
      '89', '63', '',null,null,'description',21,123);

 INSERT INTO school_t ( name,  uid,alias,imgs,content,content2,content3,createTime,updateTime,des,latitude,longitude) VALUES
     ( 'Old VTC School No.2', 30,'Summer road ice street','',
       '89', '66', '',null,null,'des',31,133);
 INSERT INTO school_t ( name,  uid,alias,imgs,content,content2,content3,createTime,updateTime,des,latitude,longitude) VALUES
     ( 'Old VTC School No.3', 30,'New road ice street','',
       '89', '66', '',null,null,'des',51,143);
select * from school_t;

select * from school_t as t1 INNER JOIN  save_t as t2 on t2.uid = 10 and t1.id=t2.fid  ;
select * from school_t where id in (select save_t.fid from save_t where  save_t.uid=60);

INSERT INTO save_t (uid,fid) VALUES (10,10);
INSERT INTO save_t (uid,fid) VALUES (10,11);
INSERT INTO save_t (uid,fid) VALUES (10,12);
select * from save_t;

