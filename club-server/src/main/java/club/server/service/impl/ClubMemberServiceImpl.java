package club.server.service.impl;

import club.common.utils.R;
import club.common.utils.StringUtil;
import club.server.dao.ClubMemberDao;
import club.server.model.entity.ClubMemberEntity;
import club.server.model.query.StudentClubQuery;
import club.server.model.vo.StudentClubVo;
import club.server.service.ClubInfoService;
import club.server.service.ClubMemberService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
 *@Description
 *@Author Chen
 *@Date 2021/11/5 19:39
 */
@Service("clubMemberService")
public class ClubMemberServiceImpl extends ServiceImpl<ClubMemberDao, ClubMemberEntity> implements ClubMemberService {
    @Autowired
    private ClubInfoService clubInfoService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R addNewMemberInfo(ClubMemberEntity entity) {
        entity.setPost("1");
        LambdaQueryWrapper<ClubMemberEntity> qw = new LambdaQueryWrapper<>();
        qw.eq(true, ClubMemberEntity::getClubCode, entity.getClubCode());
        qw.eq(true, ClubMemberEntity::getStudentNo, entity.getStudentNo());
        List<ClubMemberEntity> list = this.list(qw);
        if (!list.isEmpty()) {
            return R.error("The student is already a member of the club");
        }
        //Input data
        if (this.save(entity)) {
            //Number of people update
            clubInfoService.addClubPeople(entity.getClubCode());
            return R.success("Successful operation");
        }
        return R.error("Unsuccessful operation");
    }

    @Override
    public R updateMemberInfo(ClubMemberEntity entity) {
        LambdaUpdateWrapper<ClubMemberEntity> luw = new LambdaUpdateWrapper<>();
        if (StringUtil.isNullOrEmpty(entity.getId()) || StringUtil.isNullOrEmpty(entity.getPost())) {
            return R.error("Please bring parameters to access").code("201");
        }
        luw.eq(true, ClubMemberEntity::getId, entity.getId());
        luw.set(true, ClubMemberEntity::getPost, entity.getPost());
        if (this.update(luw)) {
            return R.success("Successfully modified");
        }
        return R.error("Unsuccessfully modified");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R deleteMemberInfo(String id) {
        ClubMemberEntity entity = this.getById(id);
        if (entity == null) {
            return R.error("No corresponding data").code("404");
        }
        if (this.removeById(id)) {
            clubInfoService.subClubPeople(entity.getClubCode());
            return R.success("Successful operation");
        }
        return R.error("Operation failed");
    }

    @Override
    public R getInfoByStudentOrClub(StudentClubQuery query) {
        QueryWrapper<StudentClubQuery> qw = new QueryWrapper<>();
        qw.eq(StringUtil.notNullOrEmpty(query.getClubCode()), "t1.club_code", query.getClubCode());
        qw.eq(StringUtil.notNullOrEmpty(query.getStudentNo()), "t1.student_no", query.getStudentNo());
        qw.eq(StringUtil.notNullOrEmpty(query.getSex()), "t3.sex", query.getSex());
        qw.like(StringUtil.notNullOrEmpty(query.getClubName()), "t2.club_name", query.getClubName());
        qw.like(StringUtil.notNullOrEmpty(query.getName()), "t3.name", query.getName());
        qw.eq(true, "t2.club_exit", 1);
        IPage<StudentClubVo> infoByStudentOrClub = this.baseMapper.getInfoByStudentOrClub(query.getIPage(), qw);
        return R.success(infoByStudentOrClub);
    }

    @Override
    public Boolean addMemberInfoCheck(String studentNo, String clubCode) {
        LambdaQueryWrapper<ClubMemberEntity> qw = new LambdaQueryWrapper<>();
        qw.eq(true, ClubMemberEntity::getClubCode, clubCode);
        qw.eq(true, ClubMemberEntity::getStudentNo, studentNo);
        List<ClubMemberEntity> list = this.list(qw);
        if (!list.isEmpty()) {
            return false;
        }
        return true;
    }
}
