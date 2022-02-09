with 
    avg_man as(
        select trunc(avg(salary),2) sal from employee50 where mgr_id is null
    ),
    all_man as(
        select e_id,name from employee50 where mgr_id is null
    )
select t1.e_id,t1.name from all_man t1, employee50 t2
where t1.e_id = t2.mgr_id group by t1.e_id,t1.name having sum(t2.salary) > (select t3.sal from avg_man t3)
