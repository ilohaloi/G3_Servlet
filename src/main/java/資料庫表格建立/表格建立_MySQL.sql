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
('John', 'john@example.com', '0912345678', '���ƿ����L�����s��79��', '1990-05-15', 'password123'),
('Jane', 'jane@example.com', '0987654321', '�]�߿��˫n��M����25��', '1985-10-20', 'pass456word'),
('Alice', 'alice@example.com', '0922333444', '��饫���c�Ϥ��ظ�123��', '1992-07-08', 'alice789pass');
('Bob', 'bob@example.com', '0922334455', '�x������ٰϤ����456��', '1985-05-23', 'password234'),
('Charlie', 'charlie@example.com', '0933445566', '����������ϳշR��789��', '1992-10-05', 'password345'),
('David', 'david@example.com', '0944556677', '�x�n������ϩ��e��321��', '1988-12-12', 'password456'),
('Eva', 'eva@example.com', '0955667788', '�s�˥��F�ϥ��_��654��', '1995-07-22', 'password567'),
('Frank', 'frank@example.com', '0966778899', '�򶩥����R�ϧ��T��987��', '1993-03-18', 'password678'),
('Grace', 'grace@example.com', '0977889900', '��饫���c�Ϥ��ظ�111��', '1987-09-08', 'password789'),
('Hank', 'hank@example.com', '0988990011', '�]�߿��˫n��M����222��', '1991-06-30', 'password890'),
('Ivy', 'ivy@example.com', '0911223344', '�̪F���̪F���ۥѸ�333��', '1996-04-14', 'password901'),
('Jack', 'jack@example.com', '0922334455', '���ƿ����L�����s��444��', '1989-08-25', 'password012'),
('Kelly', 'kelly@example.com', '0933445566', '�n�뿤�n�륫������555��', '1994-11-11', 'password1234'),
('Leo', 'leo@example.com', '0944556677', '�y�����y�����_����666��', '1986-02-19', 'password2345'),
('Mia', 'mia@example.com', '0955667788', '�x�F���x�F�����ظ�777��', '1997-09-27', 'password3456'),
('Nick', 'nick@example.com', '0966778899', '�Ὤ���Ὤ�����s��888��', '1990-10-10', 'password4567'),
('Olivia', 'olivia@example.com', '0977889900', '�Ÿq����Ϥ�Ƹ�999��', '1992-12-21', 'password5678'),
('Paul', 'paul@example.com', '0988990011', '�s�_���O���Ϥ�Ƹ�123��', '1984-03-03', 'password6789'),
('Quinn', 'quinn@example.com', '0911223344', '�x�����_�ϭ^�~��456��', '1995-05-15', 'password7890');