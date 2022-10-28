package Settings.TxtLogger;

/*
Ведение текстового лога.
На стороне сервера
*/

import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TextLogger
{
	//			//			//														//
	private		String		FilePath;												//Файл лога
	
    
    /**
	 * Кноструктор логгера
	 * 
	 * @param f путь к файлу лога
	 * @throws IOException
	 */
	public		TextLogger	(String f)
	{
		File fil = new File(f);
		if (!fil.exists())
		try { fil.createNewFile(); }
		catch (IOException e) { e.printStackTrace(); }
		FilePath = f;
	}
	//******************************************************************
	/**
	 * Записать в лог от имени пользователя
	 * 
	 * @param exec объект, пишущий в лог
	 * @param message сообщение
	 * @param user пользователь
	 * @return
	 */
	public		boolean		put (Object exec, String  message, String user)
	{
		return writeString( formReportString(exec.getClass().getName(), user, message) );
	}
	/**
	 * Записать в лог стектрейс исключения
	 * 
	 * @param exception исключение
	 * @param user пользователь
	 * @return
	 */
	public		boolean		put (Exception exception, String user)
	{
		String StackTrace;
		StringWriter sw = new StringWriter();
		exception.printStackTrace(new PrintWriter(sw));
		StackTrace = sw.toString();
		return writeString( formReportString("Exception", user, StackTrace) );
	}
	/**
	 * Записать в лог от имени системы
	 * 
	 * @param exec объект, пишущий в лог
	 * @param message сообщение
	 * @return
	 */
	public		boolean		put (Object exec, String  message)
	{
		return put(exec, message, "SYSTEM");
	}
	/**
	 * Записать в лог стектрейс от имени системы
	 * 
	 * @param exception
	 * @return
	 */
	public		boolean		put (Exception exception)
	{
		return put(exception, "SYSTEM");
	}
	//******************************************************************
	/**
	 * Формирователь строки лога
	 * 
	 * @param source источник записи (обычно имя класса)
	 * @param subject субъект записи (обычно имя пользователя)
	 * @param text текст записи (описание операции или ошибки)
	 * @return строка записи
	 */
	private		String		formReportString(String source, String subject, String text)
	{
		StringBuffer s = new StringBuffer("\r\n");
		s.append(		DTR.getTimestamp(new GregorianCalendar()));
		s.append("    ");
		s.append(		DTR.leftPadder(source, 40, ' ')		);
		s.append("   [");
		s.append(		DTR.rightPadder(subject, 20, ' ')	);
		s.append("]   :");
		s.append(text);
		return s.toString();
	}
	/**
	 * Запись строки в файл
	 * @param str строка для записи
	 * @throws IOException
	 */
	private		boolean		writeString(String str)
	{
		try
		{
			String fname = dateFilePath();
			File fil = new File(fname);
			if ( !fil.exists() ) fil.createNewFile();
		
			OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(fname, true), "CP1251");
			ow.write(str);
			ow.flush();
			ow.close();
			return true;
		}
		catch (IOException e)
		{
			return false;
		}
	}
	/**
	 * Формирование имени файла по дате
	 * Берется паттерн файла, переданный в настрйоке и добавляется год и месяц перед расширением
	 * @return имя файла, куда пишем лог
	 */
	private		String		dateFilePath()
	{
		Calendar c = new GregorianCalendar();
		String hm = c.get(Calendar.YEAR) + "_" + c.get(Calendar.MONTH) + "_" + c.get(Calendar.DAY_OF_MONTH);
		String prefix = FilePath.substring(0, FilePath.lastIndexOf('.'));
		String type = FilePath.substring(FilePath.lastIndexOf('.'));
		return prefix + "_" + hm + type;
	}
    
    public		long		GetLogSize ()
	{
        String fname = dateFilePath();
		File f = new File(fname);
		return f.length();	
	}
	//******************************************************************
    /*
	public		boolean		put (Object exec, String  message, String user)			//Положить запись в лог
	{
		try
		{
			Calendar c = new GregorianCalendar();
			File f = new File(FilePath);
			FileWriter wr = new FileWriter(f, true);
			
			String timestamp;
			timestamp = c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH) + "-" + c.get(Calendar.DATE) + " " + c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND) + "." + c.get(Calendar.MILLISECOND); 
			
			wr.write(timestamp + "\t" + exec.getClass() + " [" + user + "] : " + message + "\n");
			wr.close();
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	public		boolean		put (Object exec, String  message)						//Положить запись в лог
	{
		return put(exec, message, "SYSTEM");
	}
	public		TextLogger	(String f)												//конструктор
	{
		File fil = new File(f);
		if (!fil.exists())
		try { fil.createNewFile(); }
		catch (IOException e) { e.printStackTrace(); }
		FilePath = f;
	}
	public		long		GetLogSize ()
	{
		File f = new File(FilePath);
		return f.length();	
	}
    */
}
