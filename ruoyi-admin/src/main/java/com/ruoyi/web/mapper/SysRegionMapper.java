package com.ruoyi.web.mapper;

import com.ruoyi.web.domain.SysRegion;

import java.util.List;

/**
 * 地区Mapper接口
 * 
 * @author ruoyi
 * @date 2023-03-09
 */
public interface SysRegionMapper 
{
    /**
     * 查询地区
     * 
     * @param regionId 地区主键
     * @return 地区
     */
    public SysRegion selectSysRegionByRegionId(String regionId);

    /**
     * 查询地区列表
     * 
     * @param sysRegion 地区
     * @return 地区集合
     */
    public List<SysRegion> selectSysRegionList(SysRegion sysRegion);


}
