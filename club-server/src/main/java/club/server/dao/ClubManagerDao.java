package club.server.dao;

import club.server.model.entity.ClubManagerEntity;
import club.server.model.query.ClubManagerQuery;
import club.server.model.vo.ClubManagerVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*
 *@Description
 *@Author Chen
 *@Date 2021/10/27 22:49
 */
public interface ClubManagerDao extends BaseMapper<ClubManagerEntity> {
    public List<ClubManagerVo> getClubManagerInfo(String userId);
    public IPage<ClubManagerVo> queryClubManagerInfo(@Param("page") Page<ClubManagerQuery> page,
                                                @Param(Constants.WRAPPER) Wrapper wrapper);
}
