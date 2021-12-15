package club.server.dao;

import club.server.model.entity.ClubMemberTempEntity;
import club.server.model.query.StudentClubQuery;
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
 *@Date 2021/10/31 20:47
 */
public interface ClubMemberTempDao extends BaseMapper<ClubMemberTempEntity> {
    IPage<StudentClubVo> getInfoByStudentOrClub(@Param("page") Page<StudentClubQuery> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
