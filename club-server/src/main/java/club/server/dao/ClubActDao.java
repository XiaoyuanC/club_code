package club.server.dao;

import club.server.model.entity.ClubActEntity;
import club.server.model.entity.ClubInfoTempEntity;
import club.server.model.query.ClubActQuery;
import club.server.model.vo.ClubActVo;
import club.server.model.vo.ClubInfoTempVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/*
 *@Description
 *@Author Chen
 *@Date 2021/10/27 21:54
 */
public interface ClubActDao extends BaseMapper<ClubActEntity> {
    IPage<ClubActVo> queryClubAct(@Param("page") Page<ClubActEntity> page,
                                  @Param(Constants.WRAPPER) Wrapper wrapper);
}
