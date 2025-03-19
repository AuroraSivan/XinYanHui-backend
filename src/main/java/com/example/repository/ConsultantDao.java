package com.example.repository;

import com.example.pojo.Admin;
import com.example.pojo.Consultant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Mapper
public interface ConsultantDao  {
    @Select("select consultant_id,name,professional_info,password_HashwithSalt,salt from consultants where consultant_id=#{Id} and password_HashwithSalt=#{password}")
    public Consultant getByIdAndPassword(Integer Id, String password);   //通过id和密码查询，此处密码指密码哈希值

    @Select("select salt from consultants where consultant_id=#{Id}")
    public String getSaltById(Integer Id);     //通过id查询盐
}
