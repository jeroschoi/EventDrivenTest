package com.event.eventdriventest.mapper;

import com.event.eventdriventest.dto.TpSsUserStngShctBtnDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PocTestMapper {


    List<TpSsUserStngShctBtnDTO> getTpSsUserStngShctBtnList();
}
