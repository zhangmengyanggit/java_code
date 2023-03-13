package com.ruoyi.web.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.web.domain.KyOriginalPolicy;
import com.ruoyi.web.mapper.KyOriginalPolicyMapper;
import com.ruoyi.web.service.IKyOriginalPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 政策管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-03-11
 */
@Service
public class KyOriginalPolicyServiceImpl implements IKyOriginalPolicyService
{
    @Autowired
    private KyOriginalPolicyMapper kyOriginalPolicyMapper;

    /**
     * 查询政策管理
     * 
     * @param id 政策管理主键
     * @return 政策管理
     */
    @Override
    public KyOriginalPolicy selectKyOriginalPolicyById(Long id)
    {
        return kyOriginalPolicyMapper.selectKyOriginalPolicyById(id);
    }

    /**
     * 查询政策管理列表
     * 
     * @param kyOriginalPolicy 政策管理
     * @return 政策管理
     */
    @Override
    public List<KyOriginalPolicy> selectKyOriginalPolicyList(KyOriginalPolicy kyOriginalPolicy)
    {
        return kyOriginalPolicyMapper.selectKyOriginalPolicyList(kyOriginalPolicy);
    }

    /**
     * 新增政策管理
     * 
     * @param kyOriginalPolicy 政策管理
     * @return 结果
     */
    @Override
    public int insertKyOriginalPolicy(KyOriginalPolicy kyOriginalPolicy)
    {
        kyOriginalPolicy.setCreateTime(DateUtils.getNowDate());
        return kyOriginalPolicyMapper.insertKyOriginalPolicy(kyOriginalPolicy);
    }

    /**
     * 修改政策管理
     * 
     * @param kyOriginalPolicy 政策管理
     * @return 结果
     */
    @Override
    public int updateKyOriginalPolicy(KyOriginalPolicy kyOriginalPolicy)
    {
        return kyOriginalPolicyMapper.updateKyOriginalPolicy(kyOriginalPolicy);
    }

    /**
     * 批量删除政策管理
     * 
     * @param ids 需要删除的政策管理主键
     * @return 结果
     */
    @Override
    public int deleteKyOriginalPolicyByIds(Long[] ids)
    {
        return kyOriginalPolicyMapper.deleteKyOriginalPolicyByIds(ids);
    }

    /**
     * 删除政策管理信息
     * 
     * @param id 政策管理主键
     * @return 结果
     */
    @Override
    public int deleteKyOriginalPolicyById(Long id)
    {
        return kyOriginalPolicyMapper.deleteKyOriginalPolicyById(id);
    }
}
