package Protocol;

import DB.Connect;
import Settings.ClusterStore.ClusterOne;
import Settings.Manager;
import Settings.Protocols.ProtocolException;
import Settings.Protocols.ProtocolRequest;

import java.sql.SQLException;


public class Order /*extends Protocol*/{
    private Manager manager;

    private ProtocolRequest CurrentRequest;

    private boolean MailFlag = true;

    public Order(Manager mngr)
    {
        manager = mngr;
    }


//    @Override
//    public ClusterOne Request(ProtocolRequest request) throws ProtocolException {
//        CurrentRequest = request;
//        if (request.action.equals("getList")) return getList(request.data);
//        else
//        if (request.action.equals("Extract")) return Extract(request.data);
//        else
//        if (request.action.equals("Insert")) return Insert(request.data);
//        else
//        if (request.action.equals("Korr")) return Korr(request.data);
//        else
//        if (request.action.equals("Delete")) return Delete(request.data);
//        else
//            throw new ProtocolException("Неизвестная операция (" + request.action + ") для протокола " + this.getClass().getName());

        private ClusterOne getList(ClusterOne rdata) throws SQLException {//выборка всех нарядов
            String select;                                //буффер запросов
            select = "select " +
                    "ID_ORDER, " +
                    "NUMBER, " +
                    "DATE_OD, " +
                    "CONTACT, " +
                    "PROGRAMMER, " +
                    "TECHNOLOG, " +
                    "ADMIN, " +
                    "LOG_KOR, " +
                    "DATA_SOZD, " +
                    "DATA_KORRECT, " +
                    "CREATOR, " +
                    "PROD_INST_DATE, " +
                    "PRJ_NUMBER, " +
                    "STATUS" +
                    " from " +
                    "ARMNP.AO_ORDERS" +
                    " where 1=1 ";


            ClusterOne odata = Connect.executeSelect(select);    //Выборка базовых данных


            return odata;

        }
        private ClusterOne Extract (String id) throws SQLException  //Выборка наряда

        {//какая-то проверка  прав


            id = "";

            String select;                                //буффер запросов
            select = "select " +
                    "ID_ORDER, " +
                    "NUMBER, " +
                    "DATE_OD, " +
                    "SYSCOMP, " +
                    "CHANGE_OBJECT, " +
                    "VERSION, " +
                    "REASON, " +
                    "DOC_CHENGE, " +
                    "D_SRC_TEST, " +
                    "D_SRC_PROD, " +
                    "CONDITIONS, " +
                    "SYNCHRO, " +
                    "INSTALL_TEST, " +
                    "INSTALL_PROD, " +
                    "METHOD_TEST, " +
                    "METHOD_PROD, " +
                    "METHOD_TEST_F, " +
                    "METHOD_PROD_F, " +
                    "ROLLBACK, " +
                    "CONTACT, " +
                    "STOP_SYSTEM, " +
                    "DOWN_TIME, " +
                    "PROGRAMMER_POST, " +
                    "PROGRAMMER, " +
                    "PROGRAMMER_PH, " +
                    "TECHNOLOG_POST, " +
                    "TECHNOLOG, " +
                    "TECHNOLOG_PH, " +
                    "ADMIN_POST, " +
                    "ADMIN, " +
                    "ADMIN_PH, " +
                    "FZ_FTEST_POST, " +
                    "FZ_FTEST, " +
                    "FZ_FTEST_PH, " +
                    "TEST_INST_DATE, " +
                    "TEST_INST_TIME, " +
                    "ZAK_TEST_POST, " +
                    "ZAK_TEST, " +
                    "ZAK_TEST_PH, " +
                    "TEST_DATASOURCE, " +
                    "TEST_STOP_DATE, " +
                    "TEST_RESULT, " +
                    "OTV_FUNK_TEST, " +
                    "OTV_FUNK_TEST_PH, " +
                    "OTV_CTS_TEST, " +
                    "OTV_CTS_TEST_PH, " +
                    "TESTERS, " +
                    "PROD_DATASOURCE, " +
                    "PROD_INST_DATE, " +
                    "PROD_INST_TIME, " +
                    "F_CUSTOMER, " +
                    "F_CUSTOMER_PH, " +
                    "PRJ_NUMBER, " +
                    "FC_AGREEMENT, " +
                    "LOG_KOR, " +
                    "DATA_SOZD, " +
                    "DATA_KORRECT, " +
                    "STATUS, " +
                    "CREATOR, " +
                    "TEST_STOP_VIOLATION " +
                    " from " +
                    "ARMNP.AO_ORDERS" +
                    " where " +
                    "ID_ORDER = " + id;
            ClusterOne zdata = Connect.executeSelect(select);    //Выборка базовых данных


            return zdata;
        }
        private void Insert () //Добавление наряда
        {


            StringBuffer buf;
            //В АО_ORDER
            buf = new StringBuffer();
            buf.append("insert into ARMNP.AO_ORDERS (");
            buf.append("    ID_ORDER,");
            buf.append("    NUMBER,");
            buf.append("    DATE_OD,");
            buf.append("    SYSCOMP,");
            buf.append("    CHANGE_OBJECT,");
            buf.append("    VERSION,");
            buf.append("    REASON,");
            buf.append("    DOC_CHENGE,");
            buf.append("    D_SRC_TEST,");
            buf.append("    D_SRC_PROD,");
            buf.append("    CONDITIONS,");
            buf.append("    SYNCHRO,");
            buf.append("    INSTALL_TEST,");
            buf.append("    INSTALL_PROD,");
            buf.append("    METHOD_TEST,");
            buf.append("    METHOD_PROD,");
            buf.append("    METHOD_TEST_F,");
            buf.append("    METHOD_PROD_F,");
            buf.append("    ROLLBACK,");
            buf.append("    CONTACT,");
            buf.append("    STOP_SYSTEM,");
            buf.append("    DOWN_TIME,");
            buf.append("    PROGRAMMER_POST	,");
            buf.append("    PROGRAMMER,");
            buf.append("    PROGRAMMER_PH,");
            buf.append("    TECHNOLOG_POST,");
            buf.append("    TECHNOLOG,");
            buf.append("    TECHNOLOG_PH,");
            buf.append("    ADMIN_POST,");
            buf.append("    ADMIN,");
            buf.append("    ADMIN_PH,");
            buf.append("    FZ_FTEST_POST,");
            buf.append("    FZ_FTEST,");
            buf.append("    FZ_FTEST_PH,");
            buf.append("    TEST_INST_DATE,");
            buf.append("    TEST_INST_TIME,");
            buf.append("    ZAK_TEST_POST,");
            buf.append("    ZAK_TEST,");
            buf.append("    ZAK_TEST_PH,");
            buf.append("    TEST_DATASOURCE,");
            buf.append("    TEST_STOP_DATE,");
            buf.append("    TEST_RESULT,");
            buf.append("    OTV_FUNK_TEST,");
            buf.append("    OTV_FUNK_TEST_PH,");
            buf.append("    OTV_CTS_TEST,");
            buf.append("    OTV_CTS_TEST_PH,");
            buf.append("    TESTERS,");
            buf.append("    PROD_DATASOURCE,");
            buf.append("    PROD_INST_DATE,");
            buf.append("    PROD_INST_TIME,");
            buf.append("    F_CUSTOMER,");
            buf.append("    F_CUSTOMER_PH,");
            buf.append("    PRJ_NUMBER,");
            buf.append("    FC_AGREEMENT,");
            buf.append("    LOG_KOR,");
            buf.append("    DATA_SOZD,");
            buf.append("    DATA_KORRECT,");
            buf.append("    STATUS,");
            buf.append("    CREATOR, ");
            buf.append("    TEST_STOP_VIOLATION ");

            buf.append(") values (");

            //ввести значения


        }
        private void Korr (ClusterOne rdata)//Корректировка
        {


        }
        private void Delete (String id)
        {
            StringBuffer buf;
            buf = new StringBuffer();
            buf.append("delete from ARMNP.AO_ORDERS ");
            buf.append("where ID_ORDER=" + id);


        }

    }
