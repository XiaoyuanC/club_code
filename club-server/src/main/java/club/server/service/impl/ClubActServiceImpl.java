package club.server.service.impl;

import club.common.utils.R;
import club.common.utils.StringUtil;
import club.server.dao.ClubActDao;
import club.server.model.entity.ClubActEntity;
import club.server.model.query.ClubActQuery;
import club.server.service.ClubActService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 *@Description
 *@Author Chen
 *@Date 2021/10/27 21:57
 */
@Service("clubActService")
public class ClubActServiceImpl extends ServiceImpl<ClubActDao, ClubActEntity> implements ClubActService {
    @Override
    public R addActInfo(ClubActEntity entity) {
        if (this.save(entity)) {
            return R.success("Activity information added successfully");
        }
        return R.error("Activity information added unsuccessfully");
    }

    @Override
    @Transactional
    public R deleteActInfoById(String id) {
        ClubActEntity byId = this.getById(id);
        if (byId == null) {
            return R.error("No related activity information");
        }
        if (this.removeById(id)) {
            return R.success("Successfully deleted activity information");
        }
        return R.error("Unsuccessfully deleted activity information");
    }

    @Override
    @Transactional
    public R updateActInfo(ClubActEntity entity) {
        if (!StringUtil.notNullOrEmpty(entity.getId())) {
            return R.error("Please bring the primary key parameter access");
        }
        ClubActEntity byId = this.getById(entity.getId());
        if (byId == null) {
            return R.error("No related activity information");
        }
        if (this.updateById(entity)) {
            return R.success("Successfully modify event information");
        }
        return R.error("Unsuccessfully modify event information");
    }

    @Override
    public R getActInfoById(String id) {
        ClubActEntity byId = this.getById(id);
        return R.success(byId);
    }

    @Override
    public R getActInfo(ClubActQuery query) {
        LambdaQueryWrapper<ClubActEntity> qw = new LambdaQueryWrapper<>();
        qw.eq(StringUtil.notNullOrEmpty(query.getClubCode()),
                ClubActEntity::getClubCode, query.getClubCode());
        qw.like(StringUtil.notNullOrEmpty(query.getActName()),
                ClubActEntity::getActName, query.getActName());
        qw.select(ClubActEntity::getId, ClubActEntity::getActName,
                ClubActEntity::getClubCode, ClubActEntity::getCreateDate);
        qw.orderByDesc(ClubActEntity::getCreateDate);
        Page page = this.page(query.getIPage(), qw);
        return R.success(page);
    }

    @Override
    public R queryActInfo(ClubActQuery query) {
        QueryWrapper<ClubActEntity> qw = new QueryWrapper<>();
        qw.eq(StringUtil.notNullOrEmpty(query.getClubCode()),"t1.club_code", query.getClubCode());
        qw.eq(StringUtil.notNullOrEmpty(query.getId()),"t1.id", query.getId());
        qw.eq(true,"t2.club_exit", 1);
        qw.like(StringUtil.notNullOrEmpty(query.getActName()),"t1.act_name", query.getActName());
        qw.like(StringUtil.notNullOrEmpty(query.getClubName()),"t2.club_name", query.getClubName());
        qw.orderByDesc("t1.create_date");
        IPage iPage = this.baseMapper.queryClubAct(query.getIPage(), qw);
        return R.success(iPage);
    }
}
