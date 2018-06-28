package com.github.hualuomoli.sample.framework.biz.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service(value = "com.github.hualuomoli.sample.framework.biz.schedule.ScheduleService")
public class ScheduleService {

  private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);

  @Scheduled(fixedRate = 1000 * 60)
  public void run() {
    logger.debug("runing....");
  }

}
