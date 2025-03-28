package com.example.service.serviceImpl;

import com.example.pojo.Consultant;
import com.example.repository.ConsultantDao;
import com.example.service.ViewService;
import com.example.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ViewServiceImpl implements ViewService {
    private final ConsultantDao consultantDao;

    @Autowired
    public ViewServiceImpl(ConsultantDao consultantDao) {
        this.consultantDao = consultantDao;
    }

    @Override
    public Result<List<Consultant>> viewConsultantsService(LocalDate date){
        LocalDate today = LocalDate.now();
        if(date==null){
            return Result.success(consultantDao.getScheduledConsultants());
        }
        else if(date.isEqual(today)){
            return Result.success(consultantDao.getAvailableConsultants());
        }
        else{
            return Result.success(consultantDao.getScheduledConsultantsByDate(date));
        }
    }
}
