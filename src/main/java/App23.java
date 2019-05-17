import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alarm.wtc.WTCUtils;





public class App23 {
	public static void main(String[] args) throws ParseException {
		   
		//System.out.println(WTCUtils.getInTime());
		//System.out.println(WTCUtils.getOutTime());
		
		
		SimpleDateFormat  outSdf =  new SimpleDateFormat("M/d/yyyy h:m:s a");
		Date now = new Date();
		String now2 = outSdf.format(now);
		System.out.println(now2);
	}
}