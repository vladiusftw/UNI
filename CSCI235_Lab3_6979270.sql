set serveroutput on;
--Task1 
--1.1 Write a procedure that saves the income made by a given author, display the income in an anonymous block
create or replace procedure saveIncomeByAuthor(author in varchar,incomes out number)
as
begin
    select sum(price*no_copy) into incomes from writtenBy where a_name = author group by a_name;
    exception
        when no_data_found then
            incomes := 0;
end;

declare
    incomes number;
begin
    saveIncomeByAuthor('Joseph',incomes);
    dbms_output.put_line(incomes);
end;

--1.2 Create an anonymous block to show income made by each author, use the procedure created in task 1.
declare 
    cursor c is select distinct a_name from writtenby;
    temp number;
begin
    for i in c loop
        saveIncomeByAuthor(i.a_name,temp);
        dbms_output.put_line('Name:' || i.a_name || chr(9) || 'income:' || temp);
    end loop;
end;

-- Task2
-- Write a function that returns how old a book is
-- Published before 2000 ->Book X by author Y is an old book
-- Between 2000 and 2020 -> Book X by author Y is modern
-- 2020 and later -> Book X by author Y is recently published

create or replace function getBookAge(bookName in varchar) return varchar
as
cursor c is select book.title,book.isbn,writtenby.yr_published,writtenby.a_name from book,writtenby where book.title = bookName and book.isbn = writtenby.isbn;
r c%rowtype;
temp varchar(50);
begin
    open c;
        fetch c into r;
        if r.yr_published < 2000 then temp := ' an old book';
        elsif r.yr_published >= 2000 and r.yr_published < 2020 then temp := ' modern';
        elsif r.yr_published > 2020 then temp := ' recently published';
        else return 'no book found with name ' || bookName;
        end if;
        return 'Book ' || bookName || ' by ' || r.a_name || ' is ' || temp;
   close c;
    exception
        when no_data_found then
        return 'null';   
end;

-- Task3
-- Without using max write a function to return the published with highest number of book copies published 
-- Print: X publisher has published N copies of book and made M income

create or replace function getPublisherMostCopies return varchar
as
cursor c is select book.publisher,sum(writtenby.no_copy)copies,sum(writtenby.price*writtenby.no_copy)income from book inner join writtenby on book.isbn = writtenby.isbn group by book.publisher;
temp c%rowtype;
begin
temp.copies := 0;
    for i in c loop
        if i.copies > temp.copies then temp := i;
        end if;
    end loop;
    dbms_output.put_line(temp.publisher || 'publisher has published ' || temp.copies || ' copies of book and made ' || temp.income || ' income');
    return temp.publisher;
    exception 
        when no_data_found then 
        return 'null';
end;


