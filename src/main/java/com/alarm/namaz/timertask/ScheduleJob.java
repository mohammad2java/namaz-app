package com.alarm.namaz.timertask;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alarm.namaz.NamazApp;
import com.alarm.namaz.media.MediaPlayer;
import com.alarm.namaz.model.Namaz;
import com.alarm.namaz.util.NamazUtil;
import com.alarm.wtc.WTCUtils;

@Component
public class ScheduleJob {
	final static Logger logger = Logger.getLogger(ScheduleJob.class);
	private MediaPlayer mediaPlayer = new MediaPlayer();
	
	@Scheduled(fixedRate = 1000)
	public void reportCurrentTime() {
		JLabel cLabel = (JLabel) NamazUtil.getResource("clock");
		if (cLabel != null) {
			cLabel.setText(new Date().toString());
		}
		JTextField textWorkedTime = (JTextField) NamazUtil.getResource("workedTime");
		if (textWorkedTime != null) {
			textWorkedTime.setText(WTCUtils.workedTime());
		}
    }
	
	
	
	
	@Scheduled(fixedRate = 60000)
	public void executeMedia() {
		logger.info("alarm scheduler executed .....");
		Namaz namaz =(Namaz)NamazUtil.getResource("curNamaz");
		String curTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(
				new Date());
		System.out.println(curTime);
		
		if (namaz != null
				&& (curTime.equalsIgnoreCase(namaz.getFajr())
						|| curTime.equalsIgnoreCase(namaz.getDhuhr()) || curTime
							.equalsIgnoreCase(namaz.getMaghrib()))) {
			
	    	int hrs24 =  Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	    	String message  = "Reminder:- please go for %s Namaz";
	    	if (hrs24>2 && hrs24<12 ) {
	    		message  = String.format(message, "Fajr");
	    	} else if (hrs24>11 && hrs24<17)  {
	    		message  = String.format(message, "Zohr");
	    	} else if (hrs24>17 && hrs24<20 ){
	    		message  = String.format(message, "Maghrib");
	    	}
			NamazApp frame =(NamazApp)NamazUtil.getResource("frame");
			if (frame != null) 
				frame.setVisible(true);
			frame.setTitle("NamazTiming: "+message);
			if (StringUtils.equals(System.getProperty("alarm"), "ON")) {
			Thread mediaThread = new Thread () {
				@Override
				public void run() {
					mediaPlayer.play(null);
				}		
			};
			NamazUtil.setResource("mediaThread", mediaThread);
			mediaThread.start();
			}
		}
    }
		
	
	@Scheduled(cron="* */1 * * * *")
	public void LeaveTime() {
		logger.info("timer task LeaveTimer executed .....");
		 String outTime = WTCUtils.getOutTime().toString();
		 String now = new Date().toString();
		 if (StringUtils.equalsIgnoreCase(now, outTime))
		 {
			NamazApp frame =(NamazApp)NamazUtil.getResource("frame");
			if (frame != null) {
				frame.setVisible(true);
				frame.setTitle("NamazTiming"+": Reminder:- you have completed your 8.30 hrs");
			}
		 }
    }
	
	
}
