package club.server.dao;

import club.server.model.entity.ClubMemberEntity;
import club.server.model.query.StudentClubQuery;
import club.server.model.vo.StudentClubVo;
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
 *@Date 2021/10/31 22:04
 */
public interface ClubMemberDao extends BaseMapper<ClubMemberEntity> {
    IPage<StudentClubVo> getInfoByStudentOrClub(@Param("page") Page<StudentClubQuery> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
