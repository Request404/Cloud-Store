package quick.pager.shop.handler.core;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import quick.pager.shop.context.ShopSpringContext;
import quick.pager.shop.job.enums.JobEnums;
import quick.pager.shop.job.enums.JobStatusEnums;
import quick.pager.shop.handler.AbstractHandler;
import quick.pager.shop.mapper.JobInfoMapper;
import quick.pager.shop.model.JobInfo;
import quick.pager.shop.trigger.JobTrigger;

/**
 * 任务更新
 *
 * @author request404
 */
@Slf4j
public class UpdateJobHandler extends AbstractHandler {
  @Override
  public boolean support(final JobEnums jobEnums) {
    return JobEnums.UPDATE.equals(jobEnums);
  }

  @Override
  public void execute(final String jobName, final String jobGroup) {
    log.info("新增job任务 jobName = {}, jobGroup = {}", jobName, jobGroup);

    // 1. 获取数据库执行的job任务
    JobInfoMapper jobInfoMapper = ShopSpringContext.getBean(JobInfoMapper.class);
    JobInfo jobInfo = new JobInfo();
    jobInfo.setJobName(jobName);
    jobInfo.setJobGroup(jobGroup);
    jobInfo.setJobStatus(JobStatusEnums.NORMAL.getCode());
    JobInfo selectJobInfo = jobInfoMapper.selectOne(new QueryWrapper<>(jobInfo));
    Scheduler scheduler = ShopSpringContext.getBean(Scheduler.class);
    // 2. 获取数据库执行的job任务
    try {
      JobTrigger.updateJob(scheduler, selectJobInfo);
    } catch (SchedulerException e) {
      log.error("更新定时任务失败 jobName = {}, jobGroup = {}", jobName, jobGroup);
    }

  }
}
