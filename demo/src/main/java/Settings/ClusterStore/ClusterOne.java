package Settings.ClusterStore;

/*
Этот класс описывает древесные данные.
Построен на базе библиотеки SimpleJSON и требует её подключения
Поддерживает 3 типа данных:
	Ветвь - объект
	Ветвь - массив
	Лист - текстовая строка
	Доступ к любому листу через адрес, разделенный точками
	Например General.Data.Settings
	
	При доступе к ветви возвращается JSON строка описание ветви
	Обращение к корню с пустым адресом.
*/

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.LinkedList;

public class ClusterOne
{
	//			//				//														//
	private		JSONObject		root;

	//добавление в дерево данных новых элементов
	public		boolean			put (String trace, String key, JSONObject data)			//положить объект в дерево
	{
		Object current = Trace(trace);
		if (current==null) return false;
		if (current.getClass().getSimpleName().equals("JSONObject"))
		{
			JSONObject c = (JSONObject) current;
			c.put(key, data);
			return true;
		}
		else if (current.getClass().getSimpleName().equals("JSONArray"))
		{
			JSONArray c = (JSONArray) current;
			c.add(data);
			return true;
		}
		else return false;
	}
	public		boolean			put (String trace, String key, JSONArray data)			//положить массив в дерево
	{
		Object current = Trace(trace);
		if (current==null) return false;
		if (current.getClass().getSimpleName().equals("JSONObject"))
		{
			JSONObject c = (JSONObject) current;
			c.put(key, data);
			return true;
		}
		else if (current.getClass().getSimpleName().equals("JSONArray"))
		{
			JSONArray c = (JSONArray) current;
			c.add(data);
			return true;
		}
		else return false;
	}
	public		boolean			put (String trace, String key, String data)				//положить строчку в дерево
	{
		if (data==null) data = "";
		Object current = Trace(trace);
		if (current==null) return false;
		if (current.getClass().getSimpleName().equals("JSONObject"))
		{
			JSONObject c = (JSONObject) current;
			c.put(key, data);
			return true;
		}
		else if (current.getClass().getSimpleName().equals("JSONArray"))
		{
			JSONArray c = (JSONArray) current;
			c.add(data);
			return true;
		}
		else return false;
	}
	public		boolean			put (String trace, JSONObject data)						//положить объект в массив
	{
		Object current = Trace(trace);
		if (current==null) return false;
		if (current.getClass().getSimpleName().equals("JSONObject"))
		{
			JSONObject c = (JSONObject) current;
			c.put("" + c.size(), data);
			return true;
		}
		else if (current.getClass().getSimpleName().equals("JSONArray"))
		{
			JSONArray c = (JSONArray) current;
			c.add(data);
			return true;
		}
		else return false;
	}
	public		boolean			put (String trace, JSONArray data)						//положить массив в массив
	{
		Object current = Trace(trace);
		if (current==null) return false;
		if (current.getClass().getSimpleName().equals("JSONObject"))
		{
			JSONObject c = (JSONObject) current;
			c.put("" + c.size(), data);
			return true;
		}
		else if (current.getClass().getSimpleName().equals("JSONArray"))
		{
			JSONArray c = (JSONArray) current;
			c.add(data);
			return true;
		}
		else return false;
	}
	public		boolean			put (String trace, String data)							//положить строчку в массив
	{
		if (data==null) data = "";
		Object current = Trace(trace);
		if (current==null) return false;
		if (current.getClass().getSimpleName().equals("JSONObject"))
		{
			JSONObject c = (JSONObject) current;
			c.put("" + c.size(), data);
			return true;
		}
		else if (current.getClass().getSimpleName().equals("JSONArray"))
		{
			JSONArray c = (JSONArray) current;
			c.add(data);
			return true;
		}
		else return false;
	}
	//=====================
	
	//абстрактное расширение
	public		boolean			CreateArray (String trace, String key)					//удалённое создание массива
	{
		JSONArray nobj = new JSONArray();
		Object current = Trace(trace);
		if (current==null) return false;
		if (current.getClass().getSimpleName().equals("JSONObject"))
		{
			JSONObject c = (JSONObject) current;
			c.put(key, nobj);
			return true;
		}
		else if (current.getClass().getSimpleName().equals("JSONArray"))
		{
			JSONArray c = (JSONArray) current;
			c.add(nobj);
			return true;
		}
		else return false;
	}
	public		boolean			CreateObject (String trace, String key)					//удалённое создание объекта
	{
		JSONObject nobj = new JSONObject();
		Object current = Trace(trace);
		if (current==null) return false;
		if (current.getClass().getSimpleName().equals("JSONObject"))
		{
			JSONObject c = (JSONObject) current;
			c.put(key, nobj);
			return true;
		}
		else if (current.getClass().getSimpleName().equals("JSONArray"))
		{
			JSONArray c = (JSONArray) current;
			c.add(nobj);
			return true;
		}
		else return false;
	}
	public		boolean			CreateArray (String trace)								//удалённое создание следующего массива
	{
		JSONArray nobj = new JSONArray();
		Object current = Trace(trace);
		if (current==null) return false;
		if (current.getClass().getSimpleName().equals("JSONObject"))
		{
			JSONObject c = (JSONObject) current;
			c.put("" + c.size(), nobj);
			return true;
		}
		else if (current.getClass().getSimpleName().equals("JSONArray"))
		{
			JSONArray c = (JSONArray) current;
			c.add(nobj);
			return true;
		}
		else return false;
	}
	public		boolean			CreateObject (String trace)								//удаленное создание следующего объекта
	{
		JSONObject nobj = new JSONObject();
		Object current = Trace(trace);
		if (current==null) return false;
		if (current.getClass().getSimpleName().equals("JSONObject"))
		{
			JSONObject c = (JSONObject) current;
			c.put("" + c.size(), nobj);
			return true;
		}
		else if (current.getClass().getSimpleName().equals("JSONArray"))
		{
			JSONArray c = (JSONArray) current;
			c.add(nobj);
			return true;
		}
		else return false;
	}
	//=====================
	
	//объектная манипуляция
	public		ClusterOne		getPart (String trace)									//получить ветку дерева
	{
		ClusterOne ret = new ClusterOne();
		String part = get(trace);
		if (part.equals(" - ")) return ret;
		ret.GetFromString(part);
		return ret;
	}
	public		boolean			putPart (String trace, String key, ClusterOne part)		//нарастить ветку
	{
		Object current = Trace(trace);
		if (current==null) return false;
		if (current.getClass().getSimpleName().equals("JSONObject"))
		{
			JSONObject c = (JSONObject) current;
			c.put(key, part.Trace(""));
			return true;
		}
		else if (current.getClass().getSimpleName().equals("JSONArray"))
		{
			JSONArray c = (JSONArray) current;
			c.add(part.Trace(""));
			return true;
		}
		else return false;
	}
	//=====================
	
	//Другие манипуляции с данными
	public		String			search (String trace, String option, String value)		//Поиск индекса по значению
	{
		Object current = Trace(trace);
		if (current==null) 
		{	
			return " - ";
		}
		if (current.getClass().getSimpleName().equals("JSONObject"))
		{
			String index = " - ";
			JSONObject c = (JSONObject) current;
			
			Object[] objs = c.keySet().toArray();
			
			for (int i=0;i<objs.length;i++)
			{
				if (get(trace + "." + objs[i].toString() + "." + option).equals(value))
					index = objs[i].toString();
			}
			return index;
		}
		else if (current.getClass().getSimpleName().equals("JSONArray"))
		{
			String index = " - ";
			JSONArray c = (JSONArray)current;
			for (int i=0;i<c.size();i++)
			{
				if (get(trace + "." + i + "." + option).equals(value))
					index = "" + i;
			}
			return index;
		}
		else
		{
			return " - ";
		}
	}
	public		boolean			createIndex (String sourceTrace, String sourceIndex, String destinationTrace, String name)//Создание индексированной копии
	{
		Object source = Trace(sourceTrace);//узнать что за массив
		if (source==null) { return false; }
		
		JSONObject nobj = new JSONObject();//новый массив
		
		if (source.getClass().getSimpleName().equals("JSONObject"))
		{//Если объект
			//System.out.println();
			
			JSONObject c = (JSONObject) source;//текущий объект
			
			Object[] objs = c.keySet().toArray();//ключи текущего объекта
			for (int i=0;i<objs.length;i++)//перебор ключей текущего объекта
			{
				Object okey = Trace(sourceTrace + "." + objs[i].toString() + "." + sourceIndex);
				if (!okey.getClass().getSimpleName().equals("String")) return false;
				String key = okey.toString();
				//узнали индексный ключ
				
				//теперь создать копию по адресу
				Object current = Trace(sourceTrace + "." + objs[i].toString());
				nobj.put(key, current);
			}
		}
		else if (source.getClass().getSimpleName().equals("JSONArray"))
		{//если массив
			JSONArray c = (JSONArray) source;//текущий массив
			
			for (int i=0;i<c.size();i++)
			{
				Object okey = Trace(sourceTrace + "." + i + "." + sourceIndex);
				if (!okey.getClass().getSimpleName().equals("String")) return false;
				String key = okey.toString();
				//узнали индексный ключ
				
				//теперь создать копию по адресу
				Object current = Trace(sourceTrace + "." + i);
				nobj.put(key, current);
			}
		}
		else return false;
		
		put(destinationTrace, name, nobj);
		return true;
	}
	
	public		void 			empty (String trace)									//Очистить ветвь или лист
	{
		Object current = Trace(trace);
		if (current==null) return;
		if (current.getClass().getSimpleName().equals("JSONObject"))
		{
			JSONObject c = (JSONObject) current;
			c.clear();
		}
		else if (current.getClass().getSimpleName().equals("JSONArray"))
		{
			JSONArray c = (JSONArray) current;
			c.clear();
		}
		else if (current.getClass().getSimpleName().equals("String"))
		{
			current = "";
		}
	}
	public 		void 			delete (String trace, String key)						//Удалить элемент по адресу
	{
		Object current = Trace(trace);
		if (current==null) return;
		if (current.getClass().getSimpleName().equals("JSONObject"))
		{
			JSONObject c = (JSONObject) current;
			c.remove(key);
		}
		else if (current.getClass().getSimpleName().equals("JSONArray"))
		{
			JSONArray c = (JSONArray) current;
			c.remove(Integer.parseInt(key));
		}
		else if (current.getClass().getSimpleName().equals("String"))
		{
			current = "";
		}
	}
	public 		void 			delete (String trace)									//Удалить элемент по адресу
	{
		int dotindex = trace.lastIndexOf(".");
		
		String key = trace.substring(dotindex+1, trace.length());
		trace = trace.substring(dotindex, trace.length());
		//System.out.println("delete:" + trace + ":" + key);
		Object current = Trace(trace);
		if (current==null) return;
		if (current.getClass().getSimpleName().equals("JSONObject"))
		{
			JSONObject c = (JSONObject) current;
			c.remove(key);
		}
		else if (current.getClass().getSimpleName().equals("JSONArray"))
		{
			JSONArray c = (JSONArray) current;
			c.remove(Integer.parseInt(key));
		}
		else if (current.getClass().getSimpleName().equals("String"))
		{
			current = "";
		}
	}
	public		int				size (String trace)										//узнать размер ветви (количество потомков)
	{
		Object current = Trace(trace);
		if (current==null) return 0;
		if (current.getClass().getSimpleName().equals("JSONObject"))
		{
			JSONObject c = (JSONObject) current;
			return c.size();
		}
		else if (current.getClass().getSimpleName().equals("JSONArray"))
		{
			JSONArray c = (JSONArray) current;
			return c.size();
		}
		else if (current.getClass().getSimpleName().equals("String"))
		{
			String c = (String) current;
			return c.length();
		}
		else return 0;
	}
	public		String			get (String trace)										//получить значение по адресу
	{
		Object current = Trace(trace);
		if (current==null) 
		{	
			//System.out.println("NOT FOUND AT: " + trace);
			return " - ";
		}
		if (current.getClass().getSimpleName().equals("JSONObject"))
		{
			JSONObject c = (JSONObject) current;
			return c.toJSONString();
		}
		else if (current.getClass().getSimpleName().equals("JSONArray"))
		{
			JSONArray c = (JSONArray) current;
			return c.toJSONString();
		}
		else if (current.getClass().getSimpleName().equals("String"))
		{
			String c = (String) current;
			return c;
		}
		else
		{
			//System.out.println("NOT FOUND AT: " + trace);
			return " - ";
		}
	}
	//=====================
	public		String			GetJSON ()												//получить строку JSON описывающую весь объект
	{
		return root.toJSONString();
	}
	public		JSONObject		GetJSONObject ()										//получить сам JSON объект корня
	{
		return root;
	}
	public		boolean			GetFromFile (String fname)								//считать с файла (данные в формате JSON)
	{
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(fname));
			String line;
			String allData = "";
			while ((line=reader.readLine()) != null)
			{
				//System.out.print("before: " + line);
				line = line.replaceAll("//.*$", "\n");
				//System.out.println("  after: " + line);
				allData+= "\n" + line;
			}
            reader.close();
			return GetFromString (allData);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
		/*
		try
		{
			JSONParser pars = new JSONParser();
	        root = (JSONObject) pars.parse(new FileReader(fname));
	        return true;
	    }
	    catch (Exception e)
	    {
	    	e.printStackTrace();
	    	return false;
	    }
	    */
	}
	public		boolean			GetFromString (String target)							//распарсить из строки
	{
		try
		{
			JSONParser pars = new JSONParser();
			root = (JSONObject) pars.parse(target);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
	    	return false;
		}
	}
	public		boolean			GetFromRS (ResultSet rs)								//сгенерировать из набора SQL
	{
		LinkedList<String> Colnames = new LinkedList<String>();			//Набор имён столбцов
		try
		{
			ResultSetMetaData rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount();
			for (int i=1;i<=cols;i++)//считать имена колонок
			{
				Colnames.add(rsmd.getColumnLabel(i));
			}
			int cind=0;
			while (rs.next())
			{
				JSONObject cob = new JSONObject();
				for (int i=1;i<=cols;i++)
				{
					if (rs.getString(i) == null) cob.put(Colnames.get(i-1), "NULL");//Если встречаем null записываем строковый эквивалент дабы избежать вылетов
					else cob.put(Colnames.get(i-1), rs.getString(i).trim());
				}
				root.put("" + cind, cob);
				cind++;
			}
		}
		catch (Exception e)
		{
			System.out.println("Extracting data from Result Set FAILURE!");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public		boolean			SetToFile (String fname)								//Записать в файл (данные в формате JSON)
	{
		try
		{
			FileWriter wr = new FileWriter(fname);
			wr.write(root.toJSONString());
            wr.close();
	        return true;
	    }
	    catch (Exception e)
	    {
	    	e.printStackTrace();
	    	return false;
	    }
	}
	//=====служебные методы
	public		boolean			Exists(String trace)
	{
		try
		{
			if (trace.equals("")) return true;
			String arr[] = trace.split("[.]");
            
			Object current = root.get(arr[0]);
            if (current==null) return false;
			for (int i=1;i<arr.length;i++)
			{
				if (current.getClass().getSimpleName().equals("JSONObject"))
				{
					JSONObject c = (JSONObject) current;
					current = c.get(arr[i]);
				}
				else if (current.getClass().getSimpleName().equals("JSONArray"))
				{
					JSONArray c = (JSONArray) current;
					if (Integer.parseInt(arr[i]) >= c.size()) return false;
					current = c.get(Integer.parseInt(arr[i]));
				}
				else return false;
			}
            if (current==null) return false;
            else return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	public		Object			Trace(String trace)										//трассировка дерева до искомого элемента
	{
		try
		{
			if (trace.equals("")) return root;
			String arr[] = trace.split("[.]");
			Object current = root.get(arr[0]);
			for (int i=1;i<arr.length;i++)
			{
				if (current.getClass().getSimpleName().equals("JSONObject"))
				{
					JSONObject c = (JSONObject) current;
					current = c.get(arr[i]);
				}
				else if (current.getClass().getSimpleName().equals("JSONArray"))
				{
					JSONArray c = (JSONArray) current;
					if (Integer.parseInt(arr[i]) >= c.size()) return null;
					current = c.get(Integer.parseInt(arr[i]));
				}
				else return null;
			}
			return current;
		}
		catch (Exception e)
		{
			System.out.println("FAIL: " + trace);
			e.printStackTrace();
			return null;
		}
	}
	//=====================
	
	public ClusterOne()															//конструктор
	{
		root = new JSONObject();
	}
	public ClusterOne(JSONObject nobj)
	{
		root = nobj;
	}
}
