package Protocol;

import DB.Connect;
import Settings.ClusterStore.ClusterOne;
import Settings.Manager;
import Settings.Protocols.Protocol;
import Settings.Protocols.ProtocolException;
import Settings.Protocols.ProtocolRequest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;


public class CardPO extends Protocol {
    Connect conn = new Connect();
    private Manager  manager;
    private ProtocolRequest CurrentRequest;
    public CardPO(Manager mngr) {manager = mngr;}
    @Override
    public ClusterOne Request(ProtocolRequest request) throws ProtocolException, SQLException {

         CurrentRequest = request;
            if (request.action.equals("getList")) return getList(request.data);
            if (request.action.equals("ExtractCard")) return ExtractCard(request.data);
            if (request.action.equals("Insert")) return Insert(request.data);
            else
                throw new ProtocolException("Неизвестная операция (" + request.action + ") для протокола " + this.getClass().getName());

    }
    private ClusterOne getList(ClusterOne rdata) throws SQLException //выборка всех карточек
    {
        String select;								//буффер запросов
        select = 	"select " +
                "ID_CARD,"+
                "ADJUSTMENT_DATE,"+
                 "RESPONSIBLE,"+
                "LETTER_NUMBER,"+
                "SUBSYTE,"+
                "CREATION_DATE,"+
                "ID_CREATOR,"+
                "CARD_NUMBER,"+
                "CARD_STATUS"+
                " from " +
                "AO_CARDS" +
                " where 1=1 ";
        if (rdata.get("ID_CARD").length()>0)
        {
            select += " and upper(ID_CARD) like '%" + rdata.get("ID_CARD").toUpperCase() + "%' ";
            }

        ClusterOne cdata = conn.executeSelect(select);
        return cdata;

    }
    private ClusterOne ExtractCard(ClusterOne rdata) throws SQLException //выборка  карточки
    {
        String id ="1";
        String select;								//буффер запросов
        select = 	"select " +
                "ID_CARD,"+
                "ADJUSTMENT_DATE,"+
                "RESPONSIBLE,"+
                "LETTER_NUMBER,"+
                "SUBSYTE,"+
                "CREATION_DATE,"+
                "ID_CREATOR,"+
                "CARD_NUMBER,"+
                "CARD_STATUS"+
                " from " +
                "AO_CARDS" +
                " where id_card = "+id;


        ClusterOne cdata = conn.executeSelect(select);
        return cdata;

    }
    private ClusterOne Insert (ClusterOne rdata) throws SQLException //Добавление наряда
    {
        String ID_CARD = ExtractNextId() ;
        ClusterOne data = rdata.getPart("data");
        LinkedList<String> lupdate = new LinkedList<String>();
        StringBuffer buf;
        //В АО_CARDS
        buf = new StringBuffer();
        buf.append("insert into AO_CARDS (");
        buf.append("ID_CARD ,");
        buf.append( "ADJUSTMENT_DATE,");
        buf.append(   "RESPONSIBLE,");
        buf.append(  "LETTER_NUMBER,");
        buf.append(  "SUBSYTE,");
        buf.append(   "CREATION_DATE,");
        buf.append(   "ID_CREATOR,");
        buf.append(  "CARD_NUMBER,");
        buf.append( "CARD_STATUS");
        buf.append(") values (");
        buf.append(ID_CARD + ", ");
        buf.append("'" + data.get("ADJUSTMENT_DATE") + "', ");
        buf.append("'" + data.get( "RESPONSIBLE") + "', ");
        buf.append("'" + data.get("LETTER_NUMBER") + "', ");
        buf.append("'" + data.get("SUBSYTE") + "', ");
        buf.append("'" + data.get("CREATION_DATE") + "', ");
        buf.append("'" + data.get("ID_CREATOR") + "'");
        buf.append("'" + data.get("CARD_NUMBER") + "'");
        buf.append("'" + data.get("CARD_STATUS") + "'");
        buf.append(")");
        lupdate.add(buf.toString());


return data;
    }
    private String ExtractNextId () throws SQLException                                //выбрать айди  следующий
    {
        String Select = "select MAX(ID_CARD)+1 from AO_CARDS";
        ClusterOne res = conn.executeSelect(Select);
        return res.get("0.1");

    }

}
