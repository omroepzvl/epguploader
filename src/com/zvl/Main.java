package com.zvl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.*;


public class Main {
  protected static final Logger log = LogManager.getLogger(Main.class);
  static Scheduler scheduler;
  
  
  public static void main(String[] args) throws IOException {
    log.trace("Initializing");
    
    try {
      scheduler = StdSchedulerFactory.getDefaultScheduler();
      scheduler.start();
      
      // define the job and tie it to our ClipImport class
      JobDetail job = JobBuilder.newJob(JobProcessEPG.class)
              .withIdentity("ProcessEPG", "group1")
              .build();
      
      // Trigger the job to run now, and then every 40 seconds
      SimpleScheduleBuilder scb = SimpleScheduleBuilder.repeatHourlyForever();
      Trigger trigger = TriggerBuilder.newTrigger().withSchedule(scb).build();
      
      // Tell quartz to schedule the job using our trigger
      scheduler.scheduleJob(job, trigger);
      
    } catch(Exception e) {
      log.error("Exception!" + e.getMessage());
      throw new RuntimeException(e);
    }
  }
  

}
