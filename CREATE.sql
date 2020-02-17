-- CREATE TABLE
create table movie
(movieid number(4) not null,
moviename varchar(100) not null,
releasedyear number(4),
director varchar(100),
personalratings number(2),
status varchar(20) default 'Not yet watched' not null, 
constraint movie_pk primary key (movieid),
constraint year_range check (releasedyear between 1900 and 2100),
constraint rating_range check (personalratings between 1 and 10),
constraint valid_status_input check (status in('Watched', 'Watching', 'Not yet watched')));

select * from movie;