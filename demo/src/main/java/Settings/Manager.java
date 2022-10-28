package Settings;

import Settings.Protocols.ProtocolSet;
import Settings.TxtLogger.TextLogger;

public class Manager {
    public TextLogger logger;
    public		Manager	() {}

    public		void		Log(Object exec, String message)			//Запись в лог
    {
        String user ="logon";
        if (user == null) user = " - ";
        if (user.equals(" - "))
            logger.put(exec, message);
        else
            logger.put(exec, message, user);

    }
}
