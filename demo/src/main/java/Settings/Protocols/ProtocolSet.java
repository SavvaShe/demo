package Settings.Protocols;

import DB.Connect;
import Protocol.CardPO;
import Protocol.Order;
import Settings.ClusterStore.ClusterOne;
import Settings.Manager;


import java.sql.SQLException;
import java.util.HashMap;

public class ProtocolSet {
    Manager manager;
    private HashMap<String, Protocol> collection = new HashMap<String, Protocol>();

    public ProtocolSet(Manager nmgr) {
        manager = nmgr;
    }

    public Protocol ActivateProtocol(String protocol)                            //Включить протокол
            throws ProtocolException {//Здесь прописываем активатор протоколов
        //if (protocol.equals("Order")) collection.put(protocol, new Order(manager));
         if (protocol.equals("CardPO")) collection.put(protocol, new CardPO(manager));
        else
            throw new ProtocolException("Обнаружен вызов неизвестного протокола (" + protocol + ")");
        return collection.get(protocol);
    }

    public void DeactivateProtocol(String protocol)                                //Выключить протокол
    {
        //collection.remove(protocol);
        //disable deactivating for optimise usage of CPU and memory.
    }

    public ClusterOne Request(String protocol, String action, ClusterOne data)    //Запрос к протоколу
            throws ProtocolException, SQLException {
        //лог гет каунт забивает всё
        switch (protocol + "." + action) {
            case "":
                break;
            default:
                manager.Log(this, "Call protocol " + protocol + "(" + action + ")");
        }

        if (!collection.containsKey(protocol)) ActivateProtocol(protocol);

        Connect connect = new Connect();
        ProtocolRequest prequest = new ProtocolRequest(action, data, connect);
        ClusterOne ret;
        try {
            ret = collection.get(protocol).Request(prequest);
        } catch (ProtocolException | SQLException pe) {

            throw pe;
        }
        return ret;
    }
}