/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Settings.Protocols;



import DB.Connect;
import Settings.ClusterStore.ClusterOne;

import java.sql.ResultSet;


public class ProtocolRequest {
    Connect conn = new Connect();
    public String action;
    public ClusterOne data;


    
    public ProtocolRequest(String action, ClusterOne data, Connect conn) {
        this.action = action;
        this.data = data;
        this.conn = conn;

    }
}
