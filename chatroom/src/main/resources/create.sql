create table if not EXISTS userinfo (
    id int PRIMARY key AUTO_INCREMENT,
    name VARCHAR(30),
    macadd varchar(20),
    passwd varchar(20),
    ip varchar(15),
    token varchar(32)
)