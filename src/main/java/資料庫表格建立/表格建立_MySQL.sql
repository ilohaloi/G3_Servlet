DROP TABLE IF EXISTS member_data;

CREATE TABLE member_data (
    memb_id INT PRIMARY KEY auto_increment,
    memb_name VARCHAR(10) NOT null,
    memb_email VARCHAR(50) NOT null unique key,
    memb_tell VARCHAR(10),
    memb_address VARCHAR(80),
    memb_birthday date,
 	memb_password varchar(24) NOT null
 );

SELECT * FROM CruiseShip.member_data;
INSERT INTO member_data (memb_name, memb_email, memb_tell, memb_address, memb_birthday, memb_password)
VALUES
('John', 'john@example.com', '0912345678', '彰化縣員林市中山路79號', '1990-05-15', 'password123'),
('Jane', 'jane@example.com', '0987654321', '苗栗縣竹南鎮和平路25號', '1985-10-20', 'pass456word'),
('Alice', 'alice@example.com', '0922333444', '桃園市中壢區中華路123號', '1992-07-08', 'alice789pass');
('Bob', 'bob@example.com', '0922334455', '台中市西屯區中港路456號', '1985-05-23', 'password234'),
('Charlie', 'charlie@example.com', '0933445566', '高雄市左營區博愛路789號', '1992-10-05', 'password345'),
('David', 'david@example.com', '0944556677', '台南市中西區府前路321號', '1988-12-12', 'password456'),
('Eva', 'eva@example.com', '0955667788', '新竹市東區光復路654號', '1995-07-22', 'password567'),
('Frank', 'frank@example.com', '0966778899', '基隆市仁愛區孝三路987號', '1993-03-18', 'password678'),
('Grace', 'grace@example.com', '0977889900', '桃園市中壢區中華路111號', '1987-09-08', 'password789'),
('Hank', 'hank@example.com', '0988990011', '苗栗縣竹南鎮和平路222號', '1991-06-30', 'password890'),
('Ivy', 'ivy@example.com', '0911223344', '屏東縣屏東市自由路333號', '1996-04-14', 'password901'),
('Jack', 'jack@example.com', '0922334455', '彰化縣員林市中山路444號', '1989-08-25', 'password012'),
('Kelly', 'kelly@example.com', '0933445566', '南投縣南投市中正路555號', '1994-11-11', 'password1234'),
('Leo', 'leo@example.com', '0944556677', '宜蘭縣宜蘭市復興路666號', '1986-02-19', 'password2345'),
('Mia', 'mia@example.com', '0955667788', '台東縣台東市中華路777號', '1997-09-27', 'password3456'),
('Nick', 'nick@example.com', '0966778899', '花蓮縣花蓮市中山路888號', '1990-10-10', 'password4567'),
('Olivia', 'olivia@example.com', '0977889900', '嘉義市西區文化路999號', '1992-12-21', 'password5678'),
('Paul', 'paul@example.com', '0988990011', '新北市板橋區文化路123號', '1984-03-03', 'password6789'),
('Quinn', 'quinn@example.com', '0911223344', '台中市北區英才路456號', '1995-05-15', 'password7890');