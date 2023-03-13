package com.ruoyi.web.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 政策管理对象 ky_original_policy
 * 
 * @author ruoyi
 * @date 2023-03-11
 */
public class KyOriginalPolicy extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 标题 */
    @Excel(name = "标题")
    private String tittle;

    /** 政策原文内容 */
    private String content;

    /** 政策级别 */
    @Excel(name = "政策级别")
    private String policyLevel;

    /** 发布部门 */
    @Excel(name = "发布部门")
    private String publishingDepartment;

    /** 政策类别 */
    private String policyCategory;

    /** 适用行业 */
    @Excel(name = "适用行业")
    private String applicableIndustries;

    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发布时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date publishTime;

    /** 状态 */
    @Excel(name = "状态")
    private String state;

    /** 附件地址 */
    private String appendixUrl;

    /** 发布单位 */
    private String publishUnit;

    /** 发布人 */
    @Excel(name = "发布人")
    private String publisher;

    /** 资料上传 */
    private String meansUrl;

    /** 项目状态 */
    @Excel(name = "项目状态")
    private String itemStatus;

    /** 标签id集合 */
    @Excel(name = "标签id集合")
    private String tagIds;

    /** 企业规模 */
    @Excel(name = "企业规模")
    private String enterpriseSize;

    /** 发布状态 */
    @Excel(name = "发布状态")
    private Integer publishStatus;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTittle(String tittle) 
    {
        this.tittle = tittle;
    }

    public String getTittle() 
    {
        return tittle;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setPolicyLevel(String policyLevel)
    {
        this.policyLevel = policyLevel;
    }

    public String getPolicyLevel()
    {
        return policyLevel;
    }
    public void setPublishingDepartment(String publishingDepartment) 
    {
        this.publishingDepartment = publishingDepartment;
    }

    public String getPublishingDepartment() 
    {
        return publishingDepartment;
    }
    public void setPolicyCategory(String policyCategory) 
    {
        this.policyCategory = policyCategory;
    }

    public String getPolicyCategory() 
    {
        return policyCategory;
    }
    public void setApplicableIndustries(String applicableIndustries)
    {
        this.applicableIndustries = applicableIndustries;
    }

    public String getApplicableIndustries()
    {
        return applicableIndustries;
    }
    public void setPublishTime(Date publishTime) 
    {
        this.publishTime = publishTime;
    }

    public Date getPublishTime() 
    {
        return publishTime;
    }
    public void setState(String state)
    {
        this.state = state;
    }

    public String getState()
    {
        return state;
    }
    public void setAppendixUrl(String appendixUrl) 
    {
        this.appendixUrl = appendixUrl;
    }

    public String getAppendixUrl() 
    {
        return appendixUrl;
    }
    public void setPublishUnit(String publishUnit) 
    {
        this.publishUnit = publishUnit;
    }

    public String getPublishUnit() 
    {
        return publishUnit;
    }
    public void setPublisher(String publisher) 
    {
        this.publisher = publisher;
    }

    public String getPublisher() 
    {
        return publisher;
    }
    public void setMeansUrl(String meansUrl) 
    {
        this.meansUrl = meansUrl;
    }

    public String getMeansUrl() 
    {
        return meansUrl;
    }
    public void setItemStatus(String itemStatus)
    {
        this.itemStatus = itemStatus;
    }

    public String getItemStatus()
    {
        return itemStatus;
    }
    public void setTagIds(String tagIds) 
    {
        this.tagIds = tagIds;
    }

    public String getTagIds() 
    {
        return tagIds;
    }
    public void setEnterpriseSize(String enterpriseSize) 
    {
        this.enterpriseSize = enterpriseSize;
    }

    public String getEnterpriseSize() 
    {
        return enterpriseSize;
    }
    public void setPublishStatus(Integer publishStatus) 
    {
        this.publishStatus = publishStatus;
    }

    public Integer getPublishStatus() 
    {
        return publishStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("tittle", getTittle())
            .append("content", getContent())
            .append("rank", getPolicyLevel())
            .append("publishingDepartment", getPublishingDepartment())
            .append("policyCategory", getPolicyCategory())
            .append("applicableIndustries", getApplicableIndustries())
            .append("publishTime", getPublishTime())
            .append("state", getState())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("appendixUrl", getAppendixUrl())
            .append("publishUnit", getPublishUnit())
            .append("publisher", getPublisher())
            .append("meansUrl", getMeansUrl())
            .append("itemStatus", getItemStatus())
            .append("tagIds", getTagIds())
            .append("enterpriseSize", getEnterpriseSize())
            .append("publishStatus", getPublishStatus())
            .toString();
    }
}
