insert into User (full_name,password,email,last_online,gender,sexuality,role)
values
('Bela','$2a$04$YDiv9c./ytEGZQopFfExoOgGlJL6/o0er0K.hiGb5TGKHUL8Ebn..','admin@admin.hu',CURRENT_TIMESTAMP,'MALE','HETEROSEXUAL','ROLE_ADMIN');

insert into User (full_name,password,email,last_online,gender,sexuality,role)
values
('Zoey','$2a$10$feZYzOIejt/Odd/lCNqlN.c7NrRe77ktkFrAjByoW8POJwCUgU5MS','user@user.com',CURRENT_TIMESTAMP,'FEMALE','HETEROSEXUAL','ROLE_USER');

insert into User (full_name,password,email,last_online,gender,sexuality,role)
values
('Cheers Mate','$2a$10$feZYzOIejt/Odd/lCNqlN.c7NrRe77ktkFrAjByoW8POJwCUgU5MS','test@test.hu',CURRENT_TIMESTAMP,'MALE','HETEROSEXUAL','ROLE_USER');

insert into User (full_name,password,email,last_online,gender,sexuality,role)
values
('Cheers Mate','$2a$10$feZYzOIejt/Odd/lCNqlN.c7NrRe77ktkFrAjByoW8POJwCUgU5MS','test@test.com',CURRENT_TIMESTAMP,'MALE','HETEROSEXUAL','ROLE_USER');

insert into Picture (picture,user_id) values ('sajt.jpg',3);

insert into Interest (from_user_id, to_user_id) values (2,3);
insert into Interest (from_user_id, to_user_id) values (3,2);
insert into Interest (from_user_id, to_user_id) values (3,1);
insert into Interest (from_user_id, to_user_id) values (1,3);

insert into Match (usera_id, userb_id) values (3,2);
insert into Match (usera_id, userb_id) values (1,3);
insert into Match (usera_id, userb_id) values (2,1);
insert into Match (usera_id, userb_id) values (1,4);

insert into Message (match_id, sender_id, message, send_time) values (1,3,'Hello',CURRENT_TIMESTAMP);
insert into Message (match_id, sender_id, message, send_time) values (1,2,'Heya',CURRENT_TIMESTAMP);
insert into Message (match_id, sender_id, message, send_time) values (1,3,'Sup?',CURRENT_TIMESTAMP);
insert into Message (match_id, sender_id, message, send_time) values (1,2,'Nothing!',CURRENT_TIMESTAMP);