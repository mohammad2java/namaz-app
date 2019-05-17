package com.alarm.namaz;

import java.awt.AWTException;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.JLabel;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;

import com.alarm.namaz.model.Namaz;
import com.alarm.namaz.timertask.Application;
import com.alarm.namaz.util.GeneralUtil;
import com.alarm.namaz.util.NamazUtil;
import com.alarm.wtc.WTCUtils;

import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.BevelBorder;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.SystemColor;

public class NamazApp extends JFrame {

	final static Logger logger = Logger.getLogger(NamazApp.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private Namaz namaz;
	private static NamazApp frame; 
	private JTextField txtPleaseDropYour;
	private JTextField textAddmins;
	private JButton btnAddmins;
	private JButton btnSetCity ;
	private JTextField textInTime;
	private JTextField textOutTime;
	private JTextField textWorkedTime;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		logger.info("NamazApp started....");
		SpringApplication.run(Application.class, args);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					 frame = new NamazApp();
					frame.setVisible(false);
					frame.setResizable(false);
					NamazUtil.setResource("frame", frame);
					frame.startSysTray(frame);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public NamazApp() {
		
		//set in and out time for employee as per system bootup base.
		textInTime = new JTextField();
		textInTime.setEditable(false);
		textInTime.setForeground(Color.MAGENTA);
		textOutTime = new JTextField();
		textOutTime.setEditable(false);
		textOutTime.setForeground(Color.MAGENTA);
		textInTime.setText(WTCUtils.getInTime().toString());
		textOutTime.setText(WTCUtils.getOutTime().toString());
		setIconImage(Toolkit.getDefaultToolkit().getImage(NamazApp.class.getResource("/images/Allah.png")));
		logger.info("NamazApp constructer execution started....");
		 namaz= NamazUtil.findTodayNamaz();
		 if ( namaz != null )
		 NamazUtil.addminutsToNamaz(namaz);
		 NamazUtil.setResource("curNamaz", namaz);
		setTitle("NamazTiming");
		setBounds(100, 100, 653, 282);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setForeground(Color.BLUE);
		contentPane.setBorder(UIManager.getBorder("Button.border"));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(15, 36, 312, 69);
		panel.setBackground(Color.DARK_GRAY);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel clockLabel = new JLabel("      date ");
		NamazUtil.setResource("clock", clockLabel);
		clockLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		clockLabel.setText(new Date().toString());
		clockLabel.setBackground(Color.DARK_GRAY);
		clockLabel.setForeground(Color.GREEN);
		clockLabel.setBounds(12, 13, 288, 43);
		panel.add(clockLabel);
		Properties prop = GeneralUtil.loadProp("setting");
		String SetCity= prop.getProperty("SetCity");
		if (StringUtils.isBlank(SetCity)) {
			SetCity="Mumbai";
			GeneralUtil.storeIntoProp("SetCity", SetCity);
		}
			
		logger.info("SetCity: "+SetCity);
		if (StringUtils.isNotBlank(SetCity)) {
			Namaz selectedNamaz = NamazUtil.findTodayNamaz(SetCity);
			if (selectedNamaz != null) {
				namaz=selectedNamaz;
				NamazUtil.setResource("curNamaz", namaz);
				NamazUtil.addminutsToNamaz(namaz);
				List<String> cityList = GeneralUtil.getCities();
				cityList.add(0, SetCity);
				NamazUtil.setResource("cities", cityList);
			}
		}
		refreshTable();
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(400, 61, 112, 28);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
		        String selectedCity = (String)cb.getSelectedItem();
				System.setProperty("selectedCity", selectedCity);
			}
		});
		String[] cityList = new String[100000];
		List<String> list = (List<String>)NamazUtil.getResource("cities");
		comboBox.setModel(new DefaultComboBoxModel(list.toArray(cityList)));
		contentPane.add(comboBox);
		
		JLabel lblCity = new JLabel("City : ");
		lblCity.setBounds(364, 59, 45, 32);
		lblCity.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblCity.setForeground(Color.BLUE);
		contentPane.add(lblCity);
		
		btnSetCity = new JButton("SetCity");
		btnSetCity.setBounds(516, 62, 107, 26);
		btnSetCity.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnSetCity.setForeground(Color.BLUE);
		btnSetCity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				logger.info("changed selected city....");
				Namaz selectedNamaz = NamazUtil.findTodayNamaz(System.getProperty("selectedCity"));
				if (selectedNamaz != null) {
					namaz=selectedNamaz;
					NamazUtil.setResource("curNamaz", namaz);
					NamazUtil.addminutsToNamaz(namaz);
					GeneralUtil.storeIntoProp("SetCity", System.getProperty("selectedCity"));
					refreshTable();
				}
				
			}
		});
		contentPane.add(btnSetCity);
		
		final JButton alarmButton = new JButton("AlarmON");
		alarmButton.setBounds(516, 19, 107, 32);
		System.setProperty("alarm", "OFF");
		alarmButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if (StringUtils.equals(System.getProperty("alarm"), "OFF")) {
					System.setProperty("alarm", "ON");
					alarmButton.setForeground(Color.GREEN);
				}
				else {
					System.setProperty("alarm", "OFF");
					alarmButton.setForeground(Color.gray);
					Thread media = (Thread)NamazUtil.getResource("mediaThread");
					if (media != null) {
						media.stop();
					}
				}
				
				
			}
		});
		alarmButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		alarmButton.setBackground(Color.BLACK);
		alarmButton.setForeground(Color.gray);
		contentPane.add(alarmButton);
		
		txtPleaseDropYour = new JTextField();
		txtPleaseDropYour.setBounds(303, 215, 332, 22);
		txtPleaseDropYour.setText("please drop your feedback @ mar2java@gmail.com");
		txtPleaseDropYour.setEditable(false);
		contentPane.add(txtPleaseDropYour);
		txtPleaseDropYour.setColumns(10);
		btnAddmins = new JButton("AddMins");
		btnAddmins.setBounds(516, 102, 107, 28);
		btnAddmins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean proceed =  true;
				String admins = textAddmins.getText();
				Integer addMins = null;
				try {
				 addMins = Integer.parseInt(admins);}
				catch(Exception ex) {
					JOptionPane.showMessageDialog(frame,"please enter numeric value only.");
					proceed = false;
				}
				
				if(proceed) {
					GeneralUtil.storeIntoProp("at", admins);					
					JOptionPane.showMessageDialog(frame,"please restart the APP to reflect the new values.");
					System.exit(0);
				}
				
			}
		});
		btnAddmins.setVisible(false);
		textAddmins = new JTextField();
		textAddmins.setBounds(404, 101, 108, 29);
		textAddmins.setVisible(false);
		textAddmins.setToolTipText("adjust your namaz time by adding minuts");
		contentPane.add(textAddmins);
		textAddmins.setColumns(10);
		btnAddmins.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnAddmins.setForeground(Color.BLUE);
		contentPane.add(btnAddmins);
		
		JLabel lblIntime = new JLabel("InTime:");
		lblIntime.setForeground(Color.BLUE);
		lblIntime.setBackground(Color.WHITE);
		lblIntime.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblIntime.setBounds(3, 6, 52, 22);
		contentPane.add(lblIntime);
		
		
		textInTime.setBounds(53, 6, 188, 22);
		contentPane.add(textInTime);
		textInTime.setColumns(10);
		
		JLabel lblOut = new JLabel("OutTime:");
		lblOut.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblOut.setForeground(Color.BLUE);
		lblOut.setBounds(253, 3, 60, 27);
		contentPane.add(lblOut);
		
		
		textOutTime.setBounds(316, 6, 188, 22);
		contentPane.add(textOutTime);
		textOutTime.setColumns(10);
		
		textWorkedTime = new JTextField();
		textWorkedTime.setFont(new Font("Tahoma", Font.PLAIN, 17));
		textWorkedTime.setForeground(Color.WHITE);
		String workdTime = WTCUtils.workedTime();
		NamazUtil.setResource("workedTime", textWorkedTime);
		textWorkedTime.setText(workdTime);
		textWorkedTime.setBackground(Color.BLACK);
		textWorkedTime.setBounds(15, 104, 312, 36);
		contentPane.add(textWorkedTime);
		textWorkedTime.setColumns(10);
	}
	
	private void refreshTable(){
		logger.info("refreshing timing tables.......");
		namaz = (Namaz) NamazUtil.getResource("curNamaz");
		table = new JTable();
		table.setBounds(15, 150, 572, 32);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				logger.info("mouse clicked on tables");
				if (!btnAddmins.isVisible()) {
					btnAddmins.setVisible(true);
					textAddmins.setVisible(true);
				} else {
					btnAddmins.setVisible(false);
					textAddmins.setVisible(false);
				}

			}
		});
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));
		table.setBackground(Color.DARK_GRAY);
		table.setForeground(Color.YELLOW);
		table.setEnabled(false);
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		table.setFillsViewportHeight(true);
		table.setCellSelectionEnabled(true);
		table.setColumnSelectionAllowed(true);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"date_for", "fajr", "shurooq", "dhuhr", "asr", "maghrib", "isha"},
				{namaz.getDate_for(), namaz.getFajr(), namaz.getShurooq(), namaz.getDhuhr(), namaz.getAsr(), namaz.getMaghrib(), namaz.getIsha()},
			},
			new String[] {
				"date_for", "fajr", "shurooq", "dhuhr", "asr", "maghrib", "isha"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(145);
		table.getColumnModel().getColumn(1).setPreferredWidth(95);
		table.getColumnModel().getColumn(1).setMinWidth(21);
		table.getColumnModel().getColumn(2).setPreferredWidth(131);
		table.getColumnModel().getColumn(3).setPreferredWidth(107);
		table.getColumnModel().getColumn(4).setPreferredWidth(83);
		table.getColumnModel().getColumn(5).setPreferredWidth(114);
		table.getColumnModel().getColumn(6).setPreferredWidth(91);
		contentPane.add(table);
	}
	
	private  void startSysTray(final JFrame frame) {
		  	 Image dogImage;
		     SystemTray sysTray;
		     PopupMenu menu;
		     MenuItem item1;
		     MenuItem item2;
		     TrayIcon trayIcon;
		//check to see if system tray is supported on OS.
        if (SystemTray.isSupported()) {
            sysTray = SystemTray.getSystemTray();
            dogImage = Toolkit.getDefaultToolkit().getImage(NamazApp.class.getResource("/images/Allah.png"));
            menu = new PopupMenu();
            item1 = new MenuItem("Exit");
            item2 = new MenuItem("NamazApp");
            item2.addActionListener(new ActionListener()  {
                public void actionPerformed(ActionEvent e) {
                	frame.setVisible(true);
                }
            });
            menu.add(item2);
            menu.add(item1);
            item1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            trayIcon = new TrayIcon(dogImage, "NamazApp.", menu);
            trayIcon.setImageAutoSize(true);
            trayIcon.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (evt.getClickCount() >= 2) {
                    	frame.setVisible(true);
                    }
                }
                });

            
            
            try {
                sysTray.add(trayIcon);
            } catch (AWTException e) {
                System.out.println("System Tray unsupported!");
            }
        }
		
	}
}
