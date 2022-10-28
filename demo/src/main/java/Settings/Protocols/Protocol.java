package Settings.Protocols;



import Settings.ClusterStore.ClusterOne;

import java.sql.ResultSet;
import java.sql.SQLException;

/*
	Материнский класс протокола
	
	Протокол создаётся в мэнэджере на короткое время для обслуживания определенной задачи.
	Отвечает на запросы
 */

public abstract class Protocol
{
	public abstract ClusterOne Request (ProtocolRequest prequest) throws ProtocolException, SQLException;
}
