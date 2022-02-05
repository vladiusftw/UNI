/*TASK5: create a package as following
5.1 associative array to hold dep name and total salary per department
5.2 function to return above arraye
5.3 array of records that saves depname, count of employees
5.4 function to return above array
5.5 a procedure that 
	gets array of records from function 5.4 and 
	calculates average salary per department using array returned by function 5.2
*/

set serveroutput on;

create or replace package test as
    type t_arr is table of number index by varchar(10);
    function getDepSalary return t_arr;
    type t_rec is record(
        depName varchar(20),
        empCount int
    );
    type t_arr_rec is table of t_rec index by binary_integer;
    function getDepEmpCount return t_arr_rec;
    procedure calcAverageSalByDep;
end;

create or replace package body test as
    function getDepSalary return t_arr as
        arr t_arr;
        cursor c is select depname,sum(salary) salary from emp group by depname;
    begin
        for i in c loop
            arr(i.depname) := i.salary;
        end loop;
        return arr;
    end;
    
    function getDepEmpCount return t_arr_rec as
        arr t_arr_rec;
        cursor c is select depname,count(*) counter from emp group by depname;
        temp integer := 0;
    begin
        for i in c loop
            temp := temp + 1;
            arr(temp).depName := i.depname;
            arr(temp).empCount := i.counter;
        end loop;
        return arr;
    end;
    
    procedure calcAverageSalByDep as
        dep_sal_arr t_arr;
        dep_emp_count_arr t_arr_rec;
        key integer;
    begin
        dep_sal_arr := getDepSalary;
        dep_emp_count_arr := getDepEmpCount;
        
        dbms_output.put_line('Average Salary Per Employee Per Department:');
        
        key := dep_emp_count_arr.first;
        while key is not null loop 
            dbms_output.put_line(dep_emp_count_arr(key).depName || chr(9) ||
            (dep_sal_arr(dep_emp_count_arr(key).depName) / dep_emp_count_arr(key).empCount));
            key := dep_emp_count_arr.next(key);
        end loop;
    end;
end;

exec test.calcAverageSalByDep;



    

