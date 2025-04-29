package com.example.repository;

import com.example.pojo.Consultant;
import com.example.pojo.Supervisor;
import com.example.pojo.SupervisorSchedule;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SupervisorDao{
    @Select("select supervisor_id,name,professional_info,password_HashwithSalt,salt from Supervisors where supervisor_id=#{Id} and password_HashwithSalt=#{password}")
    public Supervisor getByIdAndPassword(Integer Id, String password);   //通过id和密码查询，密码为加密后的哈希值

    @Select("select salt from Supervisors where supervisor_id=#{Id}")
    public String getSaltById(Integer Id);     //通过id查询盐值

    @Select("insert into Supervisors(supervisor_id,name,professional_info,password_HashwithSalt,salt) values(#{supervisorId},#{name},#{professionalInfo},#{password},#{salt})")
    public void addSupervisor(Supervisor supervisor);

    @Insert("INSERT INTO SupervisorSchedules (supervisor_id, available_date, start_time, end_time) VALUES (#{supervisorId}, #{availableDate}, #{startTime}, #{endTime})")
    public int insertSchedule(SupervisorSchedule supervisorSchedule) ;

    @Select("select supervisor_id,name,professional_info from Supervisors where supervisor_id=#{Id}")
    List<Supervisor> getByIdAndPassword(Integer Id);
}
