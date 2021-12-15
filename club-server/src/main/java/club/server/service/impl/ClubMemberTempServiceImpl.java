package club.server.service.impl;

import club.common.utils.R;
import club.common.utils.StringUtil;
import club.server.dao.ClubMemberTempDao;
import club.server.model.entity.ClubMemberEntity;
import club.server.model.entity.ClubMemberTempEntity;
import club.server.model.query.ClubMemberTempQuery;
import club.server.model.query.StudentClubQuery;
import club.server.model.vo.StudentClubVo;
import club.server.service.ClubInfoService;
import club.server.service.ClubMemberService;
import club.server.service.ClubMemberTempService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/*
 *@Description
 *@Author Chen
 *@Date 2021/10/31 20:46
 */
@Service("clubMemberTempService")
public class ClubMemberTempServiceImpl extends ServiceImpl<ClubMemberTempDao, ClubMemberTempEntity> implements ClubMemberTempService {
    @Autowired
    private ClubMemberService clubMemberService;
    @Autowired
    private ClubInfoService clubInfoService;

    @Override
    public R addClubMemberTemp(ClubMemberTempEntity entity) {
        entity.setMark("0");
        boolean save = this.save(entity);
        if (save) {
            return R.success("Data submitted successfully");
        }
        return R.error("Data submitted failing");
    }

    @Override
    public R getClubMemberTempByStudentNo(StudentClubQuery query) {
        QueryWrapper<StudentClubQuery> qw = new QueryWrapper<>();
        qw.eq(StringUtil.notNullOrEmpty(query.getClubCode()), "t1.club_code", query.getClubCode());
        qw.eq(true, "t1.student_no", query.getStudentNo());
        qw.like(true, "t2.club_exit", 1);
        qw.orderByDesc("t1.create_date");
        IPage<StudentClubVo> infoByStudentOrClub = this.baseMapper.getInfoByStudentOrClub(query.getIPage(), qw);
        return R.success(infoByStudentOrClub);
    }

    @Override
    public R getClubMemberTempByClubCode(StudentClubQuery query) {
        QueryWrapper<StudentClubQuery> qw = new QueryWrapper<>();
        qw.eq(true, "t1.mark", 0);
        qw.eq(true, "t1.club_code", query.getClubCode());
        qw.eq(StringUtil.notNullOrEmpty(query.getStudentNo()), "t1.student_no", query.getStudentNo());
        qw.eq(StringUtil.notNullOrEmpty(query.getSex()), "t3.sex", query.getSex());
        qw.like(StringUtil.notNullOrEmpty(query.getClubName()), "t2.club_name", query.getClubName());
        qw.like(StringUtil.notNullOrEmpty(query.getName()), "t3.name", query.getName());
        qw.eq(true, "t2.club_exit", 1);
        qw.orderByDesc("t1.create_date");
        IPage<StudentClubVo> infoByStudentOrClub = this.baseMapper.getInfoByStudentOrClub(query.getIPage(), qw);
        return R.success(infoByStudentOrClub);
    }

    @Override
    public Boolean addClubTempMemberCheck(String studentNo, String clubCode, String type) {
        LambdaQueryWrapper<ClubMemberTempEntity> qw = new LambdaQueryWrapper<>();
        qw.eq(true, ClubMemberTempEntity::getStudentNo, studentNo);
        qw.eq(true, ClubMemberTempEntity::getClubCode, clubCode);
        qw.eq(true, ClubMemberTempEntity::getType, type);
        qw.eq(true, ClubMemberTempEntity::getMark, 0);
        if (this.list(qw).size() > 0) {
            return false;
        }
        return true;
    }

    @Override
    public R getClubMemberTempWait(ClubMemberTempQuery query) {
        LambdaQueryWrapper<ClubMemberTempEntity> qw = new LambdaQueryWrapper<>();
        qw.eq(StringUtil.notNullOrEmpty(query.getStudentNo()), ClubMemberTempEntity::getStudentNo, query.getStudentNo());
        qw.eq(true, ClubMemberTempEntity::getClubCode, query.getClubCode());
        qw.eq(true, ClubMemberTempEntity::getMark, 0);
        return R.success(this.page(query.getIPage(), qw));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R passClubMemberTemp(String id) {
        ClubMemberTempEntity byId = this.getById(id);
        if (byId == null) {
            return R.error("No relevant information is available");
        }
        //change the data
        LambdaUpdateWrapper<ClubMemberTempEntity> uw = new LambdaUpdateWrapper<>();
        uw.set(true, ClubMemberTempEntity::getMark, 1);
        uw.set(true, ClubMemberTempEntity::getUpdateDate, LocalDateTime.now());
        uw.eq(true, ClubMemberTempEntity::getMark, 0);
        uw.eq(true, ClubMemberTempEntity::getId, id);
        if (!this.update(uw)) {
            throw new RuntimeException();
        }
        //if delete member
        if (byId.getType().equals("exit")) {
            LambdaQueryWrapper<ClubMemberEntity> qw = new LambdaQueryWrapper<>();
            qw.eq(true, ClubMemberEntity::getClubCode, byId.getClubCode());
            qw.eq(true, ClubMemberEntity::getStudentNo, byId.getStudentNo());
            if (!clubMemberService.remove(qw)) {
                throw new RuntimeException();
            }
            int i = clubInfoService.subClubPeople(byId.getClubCode());
            if (i < 1) {
                throw new RuntimeException();
            }
            return R.success("Successful operation");
        } else {
            ClubMemberEntity clubMemberEntity = new ClubMemberEntity();
            clubMemberEntity.setClubCode(byId.getClubCode());
            clubMemberEntity.setStudentNo(byId.getStudentNo());
            clubMemberEntity.setPost("1");
            if (!clubMemberService.save(clubMemberEntity)) {
                throw new RuntimeException();
            }
            int i = clubInfoService.addClubPeople(byId.getClubCode());
            if (i < 1) {
                throw new RuntimeException();
            }
            return R.success("Successful operation");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R forbidClubMemberTemp(String id) {
        ClubMemberTempEntity byId = this.getById(id);
        if (byId == null) {
            return R.error("No relevant information is available");
        }
        //change the data
        LambdaUpdateWrapper<ClubMemberTempEntity> uw = new LambdaUpdateWrapper<>();
        uw.eq(true, ClubMemberTempEntity::getMark, 0);
        uw.set(true, ClubMemberTempEntity::getMark, -1);
        uw.eq(true, ClubMemberTempEntity::getId, id);
        if (!this.update(uw)) {
            return R.error("Operation failed");
        }
        return R.success("Successful operation");
    }
}
