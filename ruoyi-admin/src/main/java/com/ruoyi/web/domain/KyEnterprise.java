package com.ruoyi.web.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


/**
 * 企业对象 ky_enterprise
 * 
 * @author ruoyi
 * @date 2023-03-08
 */
public class KyEnterprise extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 企业名称 */
    @Excel(name = "企业名称")
    private String name;

    /** 注册企业地址 */
    @Excel(name = "注册企业地址")
    private String address;

    /** 企业法人 */
    @Excel(name = "企业法人",prompt="企业法人不能为空")
    private String juridicalPerson;

    /** 企业法人电话 */
    @Excel(name = "企业法人电话")
    private String juridicalPersonPhone;

    /** 注册时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "注册时间", width = 30, dateFormat = "yyyy-MM-dd",height = 28)
    private Date registrationTime;

    /** 注册省份 */
    private String registrationProvince;

    /** 注册市 */
    private String registrationCity;

    /** 注册区 */
    private String registrationRegion;

    /** 经营地址 */
    private String manageAddress;

    /** 社会统一信用代码号 */
    @Excel(name = "社会统一信用代码号",width = 30)
    private String socialUnifiedCreditCodeNumber;

    /** 企业性质或控股类型 */
    @Excel(name = "企业性质或控股类型", dictType = "enterprise_nature",prompt = "请填写系统字典管理中的企业性质或控股类型名称")
    private String enterpriseNature;

    /** 登记注册类型 */
    @Excel(name = "登记注册类型")
    private String registrationType;

    /** 资产总额区间：1. 0---50万，2. 50---200万,3.200--500万，4.500--1000万，5.1000万--1亿，6.1亿以上 */
    @Excel(name = "资产总额区间",readConverterExp = "1=0到50万,2=50到200万,3=200到500万,4=500到1000万,5=1000万到1亿,6=1亿以上",combo = {"0到50万","50到200万","200到500万","500到1000万","1000万到1亿","1亿以上"})
    private String totalAssetsRange;

    /** 注册资本(万元) */
    @Excel(name = "注册资本(万元)",cellType = Excel.ColumnType.NUMERIC)
    private Long registeredCapital;

    /** 实收资本(万元) */
    @Excel(name = "实收资本(万元)",cellType = Excel.ColumnType.NUMERIC)
    private Long seeAlso;

    /** 官方网站(没有写无) */
    @Excel(name = "官方网站(没有写无)")
    private String officialWebsite;

    /** 企业邮箱 */
    @Excel(name = "企业邮箱")
    private String enterpriseMailbox;

    /** 所属行业一级 */
    private String industryOneLevel;

    /** 所属行业二级 */
    private String industryTwoLevel;

    /** 在职人数
    */
    @Excel(name = "在职人数",cellType = Excel.ColumnType.NUMERIC)
    private Long numberOfEmployees;

    /** 营业执照文件地址 */
    private String businessLicenseUrl;
    /** 企业标签集合 */
    @Excel(name = "企业标签(标签id集合，例:1,2)",width = 30)
    private String tagIds;

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }
    @NotBlank(message= "企业标签不能为空")
    public String getTagIds() {
        return tagIds;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }
    @NotBlank(message= "企业名称不能为空")
    public String getName() 
    {
        return name;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setJuridicalPerson(String juridicalPerson) 
    {
        this.juridicalPerson = juridicalPerson;
    }
    @NotBlank(message= "企业法人不能为空")
    public String getJuridicalPerson() 
    {
        return juridicalPerson;
    }
    public void setJuridicalPersonPhone(String juridicalPersonPhone) 
    {
        this.juridicalPersonPhone = juridicalPersonPhone;
    }
    @NotBlank(message= "企业法人电话不能为空")
    @Size(min = 11, max = 11, message = "手机号码长度只能是11个字符")
    public String getJuridicalPersonPhone() 
    {
        return juridicalPersonPhone;
    }
    public void setRegistrationTime(Date registrationTime) 
    {
        this.registrationTime = registrationTime;
    }

    public Date getRegistrationTime() 
    {
        return registrationTime;
    }
    public void setRegistrationProvince(String registrationProvince) 
    {
        this.registrationProvince = registrationProvince;
    }

    public String getRegistrationProvince() 
    {
        return registrationProvince;
    }
    public void setRegistrationCity(String registrationCity) 
    {
        this.registrationCity = registrationCity;
    }

    public String getRegistrationCity() 
    {
        return registrationCity;
    }
    public void setRegistrationRegion(String registrationRegion) 
    {
        this.registrationRegion = registrationRegion;
    }

    public String getRegistrationRegion() 
    {
        return registrationRegion;
    }
    public void setManageAddress(String manageAddress) 
    {
        this.manageAddress = manageAddress;
    }

    public String getManageAddress() 
    {
        return manageAddress;
    }
    public void setSocialUnifiedCreditCodeNumber(String socialUnifiedCreditCodeNumber) 
    {
        this.socialUnifiedCreditCodeNumber = socialUnifiedCreditCodeNumber;
    }
    @NotBlank(message= "社会统一信用代码号不能为空")
    public String getSocialUnifiedCreditCodeNumber() 
    {
        return socialUnifiedCreditCodeNumber;
    }
    public void setEnterpriseNature(String enterpriseNature) 
    {
        this.enterpriseNature = enterpriseNature;
    }

    public String getEnterpriseNature() 
    {
        return enterpriseNature;
    }
    public void setRegistrationType(String registrationType) 
    {
        this.registrationType = registrationType;
    }

    public String getRegistrationType() 
    {
        return registrationType;
    }
    public void setTotalAssetsRange(String totalAssetsRange)
    {
        this.totalAssetsRange = totalAssetsRange;
    }

    public String getTotalAssetsRange()
    {
        return totalAssetsRange;
    }
    public void setRegisteredCapital(Long registeredCapital) 
    {
        this.registeredCapital = registeredCapital;
    }

    public Long getRegisteredCapital() 
    {
        return registeredCapital;
    }
    public void setSeeAlso(Long seeAlso) 
    {
        this.seeAlso = seeAlso;
    }

    public Long getSeeAlso() 
    {
        return seeAlso;
    }
    public void setOfficialWebsite(String officialWebsite) 
    {
        this.officialWebsite = officialWebsite;
    }

    public String getOfficialWebsite() 
    {
        return officialWebsite;
    }
    public void setEnterpriseMailbox(String enterpriseMailbox) 
    {
        this.enterpriseMailbox = enterpriseMailbox;
    }
    @Email(message = "邮箱格式不正确")
    public String getEnterpriseMailbox() 
    {
        return enterpriseMailbox;
    }
    public void setIndustryOneLevel(String industryOneLevel) 
    {
        this.industryOneLevel = industryOneLevel;
    }

    public String getIndustryOneLevel() 
    {
        return industryOneLevel;
    }
    public void setIndustryTwoLevel(String industryTwoLevel) 
    {
        this.industryTwoLevel = industryTwoLevel;
    }

    public String getIndustryTwoLevel() 
    {
        return industryTwoLevel;
    }
    public void setNumberOfEmployees(Long numberOfEmployees) 
    {
        this.numberOfEmployees = numberOfEmployees;
    }

    public Long getNumberOfEmployees() 
    {
        return numberOfEmployees;
    }
    public void setBusinessLicenseUrl(String businessLicenseUrl)
    {
        this.businessLicenseUrl = businessLicenseUrl;
    }

    public String getBusinessLicenseUrl()
    {
        return businessLicenseUrl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("address", getAddress())
            .append("juridicalPerson", getJuridicalPerson())
            .append("juridicalPersonPhone", getJuridicalPersonPhone())
            .append("registrationTime", getRegistrationTime())
            .append("registrationProvince", getRegistrationProvince())
            .append("registrationCity", getRegistrationCity())
            .append("registrationRegion", getRegistrationRegion())
            .append("manageAddress", getManageAddress())
            .append("socialUnifiedCreditCodeNumber", getSocialUnifiedCreditCodeNumber())
            .append("enterpriseNature", getEnterpriseNature())
            .append("registrationType", getRegistrationType())
            .append("totalAssetsRange", getTotalAssetsRange())
            .append("registeredCapital", getRegisteredCapital())
            .append("seeAlso", getSeeAlso())
            .append("officialWebsite", getOfficialWebsite())
            .append("enterpriseMailbox", getEnterpriseMailbox())
            .append("industryOneLevel", getIndustryOneLevel())
            .append("industryTwoLevel", getIndustryTwoLevel())
            .append("numberOfEmployees", getNumberOfEmployees())
            .append("business licenseUrl", getBusinessLicenseUrl())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("tagIds", getTagIds())
            .toString();
    }
}
