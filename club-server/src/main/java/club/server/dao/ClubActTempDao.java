package club.server.dao;

import club.server.model.entity.ClubActEntity;
import club.server.model.entity.ClubActTempEntity;
import club.server.model.vo.ClubActVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/*
 *@Description
 *@Author Chen
 *@Date 2021/10/27 22:32
 */
public interface ClubActTempDao extends BaseMapper<ClubActTempEntity> {
    IPage<ClubActVo> queryClubActTemp(@Param("page") Page<ClubActTempEntity> page,
                                  @Param(Constants.WRAPPER) Wrapper wrapper);
}
