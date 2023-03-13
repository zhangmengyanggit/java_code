package com.ruoyi.web.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 地区对象 sys_region
 * 
 * @author ruoyi
 * @date 2023-03-09
 */
public class SysRegion extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 地区主键编号 */
    private String regionId;

    /** 地区名称 */
    @Excel(name = "地区名称")
    private String regionName;

    /** 地区缩写 */
    @Excel(name = "地区缩写")
    private String regionShortName;

    /** 行政地区编号 */
    @Excel(name = "行政地区编号")
    private String regionCode;

    /** 地区父id */
    @Excel(name = "地区父id")
    private String regionParentId;

    /** 地区级别 1-省、自治区、直辖市 2-地级市、地区、自治州、盟 3-市辖区、县级市、县 */
    @Excel(name = "地区级别 1-省、自治区、直辖市 2-地级市、地区、自治州、盟 3-市辖区、县级市、县")
    private Long regionLevel;

    public void setRegionId(String regionId) 
    {
        this.regionId = regionId;
    }

    public String getRegionId() 
    {
        return regionId;
    }
    public void setRegionName(String regionName) 
    {
        this.regionName = regionName;
    }

    public String getRegionName() 
    {
        return regionName;
    }
    public void setRegionShortName(String regionShortName) 
    {
        this.regionShortName = regionShortName;
    }

    public String getRegionShortName() 
    {
        return regionShortName;
    }
    public void setRegionCode(String regionCode) 
    {
        this.regionCode = regionCode;
    }

    public String getRegionCode() 
    {
        return regionCode;
    }
    public void setRegionParentId(String regionParentId) 
    {
        this.regionParentId = regionParentId;
    }

    public String getRegionParentId() 
    {
        return regionParentId;
    }
    public void setRegionLevel(Long regionLevel) 
    {
        this.regionLevel = regionLevel;
    }

    public Long getRegionLevel() 
    {
        return regionLevel;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("regionId", getRegionId())
            .append("regionName", getRegionName())
            .append("regionShortName", getRegionShortName())
            .append("regionCode", getRegionCode())
            .append("regionParentId", getRegionParentId())
            .append("regionLevel", getRegionLevel())
            .toString();
    }
}
