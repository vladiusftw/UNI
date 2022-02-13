--1.1,1.2,1.3,1.4
create or replace function getStudentsByGender(g in char) return int
as
    counter int;
    custom exception;
begin
    if(length(g) > 1) then 
        raise custom;
    end if;
    select count(*) into counter from student where gender = g;
    if(counter = 0) then
        raise no_data_found;
    end if;
    return counter;
    
    exception 
        when custom then
            dbms_output.put_line('length more than 1 exception');
            return -1;
        when no_data_found then
            dbms_output.put_line('no data found');
            return -1;
        when others then
            dbms_output.put_line('error');
            return -1;
end;

--2.1
create table transaction_history(
record_id int primary key,
timestamp date,
table_updated varchar(20),
transaction varchar(20)
);

create table transaction_history2(
record_id char(8) primary key,
timestamp date,
table_updated varchar(20),
transaction varchar(20)
);

create or replace trigger print_log_s 
after insert or update or delete on student
for each row
declare
    temp varchar(20);
    temp_id int;
begin
    case
        when inserting then 
        temp := 'Inserting'; 
        temp_id := :new.s_id;
        when updating then
        temp := 'Updating'; 
        temp_id := :new.s_id;
        when deleting then 
        temp := 'Deleting'; 
        temp_id := :old.s_id; 
    end case;
    insert into transaction_history values(temp_id,systimestamp,'student',temp);
end;

create or replace trigger print_log_c 
after insert or update or delete on course
for each row
declare
    temp varchar(20);
    temp_code char(8);
begin
    case
        when inserting then 
        temp := 'Inserting'; 
        temp_code := :new.c_code;
        when updating then
        temp := 'Updating'; 
        temp_code := :new.c_code;
        when deleting then 
        temp := 'Deleting'; 
        temp_code := :old.c_code; 
    end case;
    insert into transaction_history2 values(temp_code,systimestamp,'course',temp);
end;

--2.2
create or replace view e_view as select * from enrollment;

create or replace trigger reject_enrollment instead of insert on e_view
declare
    counter int;
begin
    select count(*) into counter from e_view where s_id = :new.s_id and c_code = :new.c_code and e_year = :new.e_year;
    if(counter = 0) then
        insert into enrollment values(:new.s_id,:new.c_code,:new.semester,:new.e_year,:new.grade);
    else dbms_output.put_line('student already took the subject this year');
    end if;
end;

--3.1,3.2,3.3
create or replace package t_package as
    type r_set is record(
        code char(8),
        year int,
        average number
    );
    type grades is table of r_set index by binary_integer;
    type arr is table of varchar(200) index by binary_integer;
    function getAverageGradePerCoursePerYear return grades;
    function getListOfStudentsByYear(year in int) return arr;
    function getMarkingByCourse(code in char,grade in number) return varchar;
    procedure printStudentMarking; 
end;

create or replace package body t_package as
    function getAverageGradePerCoursePerYear return grades
    as
        temp_list grades;
        counter int := 1;
        cursor c is select c_code,e_year,trunc(avg(grade),2) average from enrollment group by c_code,e_year order by e_year desc;
    begin
        for i in c loop
            temp_list(counter) := i;
            counter := counter + 1;
        end loop;
        return temp_list;
    end;
    
    function getListOfStudentsByYear(year in int) return arr
    as
        temp_list arr;
        cursor c is select s_id,listagg(c_code,',')temp from enrollment where e_year = year group by s_id order by s_id;
    begin
        for i in c loop
            temp_list(i.s_id) := i.temp;
        end loop;
        return temp_list;
    end;
    
    function getMarkingByCourse(code in char,grade in number) return varchar
    as
        average number;
    begin
        select avg(grade) into average from enrollment where c_code = code;
        if(grade > average) then
            return 'above average';
        else return 'below average';
        end if;
    end;
    
    procedure printStudentMarking as
        cursor c is select * from enrollment;
    begin
        for i in c loop
            dbms_output.put_line(i.s_id || chr(9) || i.c_code || chr(9) || getMarkingByCourse(i.c_code,i.grade));
        end loop;
    end;
end;



