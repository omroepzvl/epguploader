package com.zvl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.zvl.model.Item;
import com.zvl.model.Playlist;
import com.zvl.model2.*;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.commons.net.ftp.FTPSClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAmount;
import java.util.*;

public class JobProcessEPG implements Job {
  
  private static int nextEventId = 0;
  private static int nextProgrammeId = 0;
  
  protected static final Logger log = LogManager.getLogger(JobProcessEPG.class);
  
  @Override
  public void execute(JobExecutionContext jobExecutionContext) {
    try {
      log.error("Executing JobProcessEPG");
      
      Instant first = null;
      Instant last = null;
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssxxx")
          .withZone(ZoneId.systemDefault());
      
      String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
      String appConfigPath = "C:/apps/zvlepguploader/classes/app.properties";
      
      Properties appProps = new Properties();
      appProps.load(new FileInputStream(appConfigPath));
      
      String ignoreList = appProps.getProperty("ignorelist");
      
      nextEventId = Integer.parseInt(appProps.get("eventid").toString());
      nextProgrammeId = Integer.parseInt(appProps.get("programmeid").toString());
      
      //step 1: get the EPG data
      Map<String, Integer> programmeNameList = new HashMap<>();
      
      try {
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
        
        Calendar now = Calendar.getInstance();
        Calendar in7days = Calendar.getInstance();
        in7days.add(Calendar.DATE, 7);
        
        String url = String.format(appProps.getProperty("originalEPGUrl"), sdf2.format(now.getTime()), sdf2.format(in7days.getTime()));
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        
        Call call = client.newCall(request);
        String responseStr = "";
        try (Response response = call.execute()) {
          responseStr = response.body().string();
        }
        
        XmlMapper mapper = new XmlMapper();
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        Playlist[] playlists = mapper.readValue(responseStr, Playlist[].class);
        
        Instant nextItemShouldStartHere = null;
        
        List<Timeslot> timeslots = new ArrayList<>();
        List<Programme> programmes = new ArrayList<>();
        Instant currentItemEndDate = null;
        
        String dummyProgrammeId = getNextProgrammeId() + "";
        Programme dummyProgramme = new Programme(String.format("id-%s", dummyProgrammeId));
        dummyProgramme.setTextElements(new TextElements(appProps.getProperty("teksttvtitle"), appProps.getProperty("teksttvdescription")));
        programmes.add(dummyProgramme);
        
        for (Playlist playlist : playlists) {
          for (Item item : playlist.getItems()) {
            
            
            boolean ignore = false;
            
            for (String ignoreItem : StringUtils.split(ignoreList, ',')) {
              if (item.getName().toLowerCase().contains(ignoreItem)) {
                ignore = true;
              }
            }
            
            double durationDouble = Double.parseDouble(item.getDuration());
            long durationMillis = Math.round(durationDouble * 1000);
            
            Instant start = sdf.parse(item.getStart_time()).toInstant();
            
            //check if this item starts where the previous ended
            
            if (currentItemEndDate != null) {
              Duration dur = Duration.between(currentItemEndDate, start);
              if (dur.getSeconds() > 1) {
                //add dummy item
                Timeslot timeslot = new Timeslot();
                ProgrammeRef programmeRef = new ProgrammeRef();
                programmeRef.setIdref(String.format("id-%s", dummyProgrammeId));
                timeslot.setProgrammeref(programmeRef);
                timeslot.setDate(formatter.format(currentItemEndDate));
                String durationS2 = DurationFormatUtils.formatDuration(dur.toMillis(), "HH:mm:ss", true);
                timeslot.setOnairduration(durationS2);
                timeslot.durationMs = dur.toMillis();
                timeslot.setEventid(getNextEventId() + "");
                timeslots.add(timeslot);
              }
            }
            
            currentItemEndDate = start.plusMillis(durationMillis);
            
            if (ignore && !timeslots.isEmpty()) {
              //prolong the previous item with this one
              Timeslot previousItem = timeslots.get(timeslots.size() - 1);
              previousItem.durationMs = previousItem.durationMs + durationMillis;
              String durationS = DurationFormatUtils.formatDuration(previousItem.durationMs, "HH:mm:ss", true);
              previousItem.setOnairduration(durationS);
              timeslots.set(timeslots.size() - 1, previousItem);
            } else if (!ignore) {
              //try to find the programme in the list
              int programmeId;
              if (programmeNameList.containsKey(item.getName())) {
                programmeId = programmeNameList.get(item.getName());
              } else {
                programmeId = getNextProgrammeId();
                programmeNameList.put(item.getName(), programmeId);
                Programme programme = new Programme(String.format("id-%d", programmeId));
                programme.setTextElements(new TextElements(item.getName(), item.getDescription()));
                programme.setImageUrl("https://www.omroephulst.tv/scheldemond.png");
                programmes.add(programme);
              }
              
              //update start date if necessary
              if (first == null || start.isBefore(first)) {
                first = start;
              }
              
              if (last == null || currentItemEndDate.isAfter(last)) {
                last = currentItemEndDate;
              }
              
              Timeslot newItem = new Timeslot();
              newItem.setEventid(getNextEventId() + "");
              newItem.setDate(item.getStart_time());
              
              String durationS = DurationFormatUtils.formatDuration(durationMillis, "HH:mm:ss", true);
              newItem.setOnairduration(durationS);
              newItem.durationMs = durationMillis;
              ProgrammeRef programmeRef = new ProgrammeRef();
              programmeRef.setIdref(String.format("id-%d", programmeId));
              newItem.setProgrammeref(programmeRef);
              timeslots.add(newItem);
            }
          }
        }
        
        String channelName = appProps.getProperty("channelName");
        DataImport dataImport = new DataImport(channelName);
        Schedule schedule = new Schedule();
        Channel channel = new Channel(channelName, appProps.getProperty("contactEmail"));
        channel.setPeriodstart(formatter.format(first));
        channel.setPeriodend(formatter.format(last));
        schedule.setChannel(channel);
        channel.setTimeslot(timeslots);
        schedule.setChannel(channel);
        dataImport.setSchedule(schedule);
        dataImport.setProgramme(programmes);
        
        String xml = mapper.enable(SerializationFeature.WRAP_ROOT_VALUE).writer().withRootName("dataimport").withDefaultPrettyPrinter().writeValueAsString(dataImport);
        FileUtils.writeStringToFile(new File("guidezvl.xml"), xml, StandardCharsets.UTF_8);
        
        //write back the ids
        appProps.setProperty("eventid", nextEventId + "");
        appProps.setProperty("programmeid", nextProgrammeId + "");
        appProps.store(new FileWriter(appConfigPath), "updated ids");
        
        //upload the file
        FTPSClient ftpClient = new FTPSClient(false);
        ftpClient.connect(appProps.getProperty("ftphost"), 21);
        ftpClient.login(appProps.getProperty("ftpuser"), appProps.getProperty("ftppassword"));
        ftpClient.enterLocalPassiveMode();
        FileInputStream fis = new FileInputStream("guidezvl.xml");
        ftpClient.storeFile("/domains/omroephulst.tv/public_html/guidezvl.xml", fis);
        ftpClient.logout();
      } catch (Exception ex) {
        System.out.println("Error: " + ex.getMessage());
      }
    } catch (Exception e) {
      log.error("Exception: " + e.getMessage());
    }
  }
  
  private static int getNextEventId() {
    nextEventId++;
    return nextEventId;
  }
  
  private static int getNextProgrammeId() {
    nextProgrammeId++;
    return nextProgrammeId;
  }
}
