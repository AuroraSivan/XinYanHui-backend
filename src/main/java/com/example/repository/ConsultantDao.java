package com.example.repository;

import com.example.pojo.Admin;
import com.example.pojo.Consultant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ConsultantDao  {
    @Select("select consultant_id,name,professional_info,password_HashwithSalt,salt from consultants where consultant_id=#{Id} and password_HashwithSalt=#{password}")
    Consultant getByIdAndPassword(Integer Id, String password);   //通过id和密码查询，此处密码指密码哈希值

    @Select("select salt from consultants where consultant_id=#{Id}")
    String getSaltById(Integer Id);     //通过id查询盐

    @Select("insert into consultants(consultant_id,name,professional_info,password_HashwithSalt,salt) values(#{consultantId},#{name},#{professionalInfo},#{password},#{salt})")
    void addConsultant(Consultant consultant);

   //Get consultant list who are scheduled for the next 6 days or available now
    List<Consultant> getScheduledConsultants();

    List<Consultant> getScheduledConsultantsByDate(LocalDate date);

    //Get consultant list who are available now
    List<Consultant> getAvailableConsultants();
}
