package com.yupi.springbootinit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.yupi.springbootinit.model.entity.Chart;
import com.yupi.springbootinit.mapper.ChartMapper;
import com.yupi.springbootinit.service.ChartService;
@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart> implements ChartService{


}
