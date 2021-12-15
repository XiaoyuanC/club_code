package club.server.dao;

import club.server.model.entity.ClubInfoTempEntity;
import club.server.model.query.ClubInfoTempQuery;
import club.server.model.query.StudentClubQuery;
import club.server.model.vo.ClubInfoTempVo;
import club.server.model.vo.StudentClubVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/*
 *@Description
 *@Author Chen
 *@Date 2021/10/27 18:10
 */
public interface ClubInfoTempDao extends BaseMapper<ClubInfoTempEntity> {
    IPage<ClubInfoTempVo> queryClubInfoTemp(@Param("page") Page<ClubInfoTempEntity> page,
                                                 @Param(Constants.WRAPPER) Wrapper wrapper);
}
