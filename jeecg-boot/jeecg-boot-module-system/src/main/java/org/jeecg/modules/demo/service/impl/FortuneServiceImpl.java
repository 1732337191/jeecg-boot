package org.jeecg.modules.demo.service.impl;

import org.jeecg.modules.demo.entity.Fortune;
import org.jeecg.modules.demo.mapper.FortuneMapper;
import org.jeecg.modules.demo.service.IFortuneService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 运程
 * @Author: jeecg-boot
 * @Date:   2021-12-16
 * @Version: V1.0
 */
@Service
public class FortuneServiceImpl extends ServiceImpl<FortuneMapper, Fortune> implements IFortuneService {

}
