package com.ruoyi.web.service.impl;

import java.util.List;

import com.ruoyi.web.domain.SysRegion;
import com.ruoyi.web.mapper.SysRegionMapper;
import com.ruoyi.web.service.ISysRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 地区Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-03-09
 */
@Service
public class SysRegionServiceImpl implements ISysRegionService
{
    @Autowired
    private SysRegionMapper sysRegionMapper;

    /**
     * 查询地区
     * 
     * @param regionId 地区主键
     * @return 地区
     */
    @Override
    public SysRegion selectSysRegionByRegionId(String regionId)
    {
        return sysRegionMapper.selectSysRegionByRegionId(regionId);
    }

    /**
     * 查询地区列表
     * 
     * @param sysRegion 地区
     * @return 地区
     */
    @Override
    public List<SysRegion> selectSysRegionList(SysRegion sysRegion)
    {
        return sysRegionMapper.selectSysRegionList(sysRegion);
    }
}
