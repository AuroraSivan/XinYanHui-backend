package com.example.repository;

import com.example.pojo.Consultant;
import com.example.pojo.Supervisor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Mapper
public interface SupervisorDao{
    @Select("select supervisor_id,name,professional_info,password_HashwithSalt,salt from supervisors where supervisor_id=#{Id} and password_HashwithSalt=#{password}")
    public Supervisor getByIdAndPassword(Integer Id, String password);   //通过id和密码查询，密码为加密后的哈希值

    @Select("select salt from supervisors where supervisor_id=#{Id}")
    public String getSaltById(Integer Id);     //通过id查询盐值
}
