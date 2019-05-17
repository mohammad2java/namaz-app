package com.alarm.namaz.media;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import org.apache.log4j.Logger;

import javazoom.jl.player.Player;

public class MediaPlayer {
	
	final static Logger logger = Logger.getLogger(MediaPlayer.class);
	static String mp3Loc = "C:\\Users\\Public\\Music\\Sample Music\\Kalimba.mp3";
	public void play(String mp3) {
		logger.info("player started for default mp3 ..."+ mp3Loc);
		File f=new File(mp3Loc);
		Player player;
		try {
			player = new Player(new BufferedInputStream(new FileInputStream(f)));
			player.play();
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}

}
