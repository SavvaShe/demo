package Settings.TxtLogger;

import java.util.Calendar;

public class DTR
{
	/**
	 * Временная метка для записи
	 * @return
	 */
	public static String getTimestamp(Calendar c)
	{
		String timestamp;
		timestamp = 			c.get(Calendar.YEAR			) + 			  "-" +
					leftPadder(	(c.get(Calendar.MONTH  ) + 1) + "", 2, '0') + "-" + 
					leftPadder(	c.get(Calendar.DATE			) + "", 2, '0') + " " + 
					leftPadder(	c.get(Calendar.HOUR_OF_DAY	) + "", 2, '0') + ":" + 
					leftPadder(	c.get(Calendar.MINUTE		) + "", 2, '0') + ":" + 
					leftPadder(	c.get(Calendar.SECOND		) + "", 2, '0') + "." + 
					leftPadder(	c.get(Calendar.MILLISECOND	) + "", 3, '0');
		return timestamp;
	}
	/**
	 * Заполнитель записи слева
	 * 
	 * @param str строка
	 * @param len минимальная длина
	 * @param padder заполнитель
	 * @return сформированная строка
	 */
	public static String leftPadder(String str, int len, char padder)
	{
		return generateAddon(len - str.length(), padder) + str;
	}
	/**
	 * Заполнитель записи справа
	 * @param str строка
	 * @param len минимальная длина
	 * @param padder заполнитель
	 * @return сформированная строка
	 */
	public static String rightPadder(String str, int len, char padder)
	{
		return str + generateAddon(len - str.length(), padder);
	}
	/**
	 * Формирователь дополнения для заполнителей
	 * @param len длина
	 * @param padder заполнитель
	 * @return строка заполнения
	 */
	public static String generateAddon(int len, char padder)
	{
		if (len<=0) return "";
		StringBuffer addon = new StringBuffer("");
		for (int i=0;i<len;i++) addon.append(padder);
		return addon.toString();
	}
}
