package Web.Controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import Web.Domain.Topic;
import Web.Repository.TopicRepository;


@Component
public class TimerTask {




    @Autowired
    private TopicRepository tr;


    @Scheduled(cron = "0/3 * * * * ?")
    public void update() throws ParseException{
        Calendar current = Calendar.getInstance();
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));
        Iterator <Topic> topic_iterator = tr.findAll().iterator();
        while(topic_iterator.hasNext()) {
            Topic temp = topic_iterator.next();
            Calendar stime =Calendar.getInstance();
            Date st=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(temp.getStime());
            stime.setTime(st);
            Calendar etime =Calendar.getInstance();
            Date et=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(temp.getEtime());
            etime.setTime(et);
            if(current.compareTo(stime)>=0&&temp.getState().equals("Pending")) {
                temp.setState("In Progress");
                tr.save(temp);
            }else
            if(current.compareTo(etime)>=0&&temp.getState().equals("In Progress")) {
                temp.setState("End");
                temp.setPrivatekey("");
                tr.save(temp);
            }
        }
    }



}

