create table users (
  user_id number(11) not null, 
  user_name varchar(90) not null, 
  user_password varchar(45) not null, 
  constraint PK_userId primary key (user_id)
);

create sequence user_id_seq start with 1;

create or replace trigger user_bir
before insert on users
for each row
begin
select user_id_seq.nextval
into :new.user_id
from dual;
end;

GRANT SELECT, INSERT, UPDATE ON users TO app_client;