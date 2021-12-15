package club.server.service.impl;

import club.common.utils.R;
import club.common.utils.StringUtil;
import club.server.dao.ClubInfoTempDao;
import club.server.model.entity.ClubInfoEntity;
import club.server.model.entity.ClubInfoTempEntity;
import club.server.model.query.ClubInfoTempQuery;
import club.server.model.vo.ClubInfoTempVo;
import club.server.service.ClubInfoService;
import club.server.service.ClubInfoTempService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/*
 *@Description
 *@Author Chen
 *@Date 2021/10/27 19:21
 */
@Service("clubInfoTempService")
public class ClubInfoTempServiceImpl extends ServiceImpl<ClubInfoTempDao, ClubInfoTempEntity> implements ClubInfoTempService {
    @Autowired
    private ClubInfoService clubInfoService;

    @Override
    public R addClubInfoTemp(ClubInfoTempEntity entity) {
        entity.setMark("0");
        boolean save = this.save(entity);
        if (save) {
            return R.success("Data submitted successfully");
        }
        return R.error("Data submitted failing");
    }

    @Override
    public R getClubInfoTempPending(ClubInfoTempQuery query) {
        QueryWrapper<ClubInfoTempQuery> qw = new QueryWrapper<>();
        qw.eq(true,"t1.mark",0);
        qw.eq(StringUtil.notNullOrEmpty(query.getClubCode()),"t1.club_code",query.getClubCode());
        qw.like(StringUtil.notNullOrEmpty(query.getClubName()),"t2.club_name",query.getClubName());
        qw.eq(StringUtil.notNullOrEmpty(query.getDepartment()),"t2.department",query.getDepartment());
        qw.like(StringUtil.notNullOrEmpty(query.getClubPrincipal()),"t2.club_principal",query.getClubPrincipal());
        IPage<ClubInfoTempVo> clubInfoTempVoIPage = this.baseMapper.queryClubInfoTemp(query.getIPage(), qw);
        return R.success(clubInfoTempVoIPage);
    }

    @Override
    public R getClubInfoTempByClubCodeId(ClubInfoTempQuery query) {
        //Splicing query conditions
        LambdaQueryWrapper<ClubInfoTempEntity> qw = new LambdaQueryWrapper<>();
        qw.eq(true, ClubInfoTempEntity::getClubCode, query.getClubCode());
        //Insert time descending
        qw.orderByDesc(ClubInfoTempEntity::getCreateDate);
        Page<ClubInfoTempEntity> page = this.page(query.getIPage(), qw);
        return R.success(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R pass(String id) {
        //get information
        ClubInfoTempEntity byId = this.getById(id);
        if (byId == null) {
            return R.error("No corresponding primary key information");
        }
        //Modify information
        LambdaUpdateWrapper<ClubInfoTempEntity> uw = new LambdaUpdateWrapper<>();
        uw.eq(StringUtil.notNullOrEmpty(id), ClubInfoTempEntity::getId, id);
        uw.set(true, ClubInfoTempEntity::getMark, "1");
        uw.set(true,ClubInfoTempEntity::getUpdateDate, LocalDateTime.now());
        //Modify information
        LambdaUpdateWrapper<ClubInfoEntity> cuw = new LambdaUpdateWrapper<>();
        cuw.set(StringUtil.notNullOrEmpty(byId.getClubPrincipal()),ClubInfoEntity::getClubPrincipal,byId.getClubPrincipal());
        cuw.set(StringUtil.notNullOrEmpty(byId.getClubDescribe()),ClubInfoEntity::getClubDescribe,byId.getClubDescribe());
        cuw.set(true,ClubInfoEntity::getUpdateDate, LocalDateTime.now());
        cuw.eq(true, ClubInfoEntity::getClubCode, byId.getClubCode());
        //data migration
        if (this.update(uw) && clubInfoService.update(cuw)) {
            return R.success("Successful operation");
        }
        throw new RuntimeException();
    }

    @Override
    @Transactional
    public R forbid(String id) {
        //get information
        ClubInfoTempEntity byId = this.getById(id);
        if (byId == null) {
            return R.error("No corresponding primary key information");
        }
        //Modify information
        LambdaUpdateWrapper<ClubInfoTempEntity> uw = new LambdaUpdateWrapper<>();
        uw.eq(StringUtil.notNullOrEmpty(id), ClubInfoTempEntity::getId, id);
        uw.set(true, ClubInfoTempEntity::getMark, "-1");
        uw.set(true,ClubInfoTempEntity::getUpdateDate, LocalDateTime.now());
        //data migration
        if (this.update(uw)) {
            return R.success("Successful operation");
        }
        throw new RuntimeException();
    }
}
