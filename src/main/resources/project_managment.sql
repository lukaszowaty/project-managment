INSERT INTO role VALUES (1,'ROLE_ADMIN');
INSERT INTO role VALUES (2,'ROLE_USER');
INSERT INTO user VALUES(1,'jab@kowalski.pl',0b01,'Jan','Kowalski',
'$2a$12$DWCryEwHwbTYCegib92tk.Oho6B.d2In10MdB9QUaYictLJJhbSw.','123456789','admin');
INSERT INTO user VALUES(2,'user1@user.pl', 0b01,'Andrzej','Nowak',
'$2a$12$DWCryEwHwbTYCegib92tk.Rllu0dc8bBsqsNjds/wJM9tmS3YTRXy','987456321','user1');
INSERT INTO user_role VALUES(1,1,1);
INSERT INTO user_role VALUES(2,2,2);