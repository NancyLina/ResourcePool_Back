package resource_pool.demo.connection;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class SqlConnection {
    private LinkedList<ArrayList<Object>> pool=new LinkedList<ArrayList<Object>>();
    private int ConnectNum;

    public SqlConnection(int connectNum) throws SQLException {
        this.setConnectNum(connectNum);
        for(int i=0;i<connectNum;i++){
            Connection connection=DriverManager.getConnection("jdbc:mysql://10.112.120.61:7664/science_resource?serverTimezone=UTC","root","123456");
            ArrayList<Object> l=new ArrayList();
            l.add(connection);
            l.add(1);
            pool.add(l);
        }
    }

    public void addConnection(int connectNum) throws SQLException {
        this.setConnectNum(this.ConnectNum+connectNum);
        for(int i=0;i<connectNum;i++){
            Connection connection=DriverManager.getConnection("jdbc:mysql://10.112.120.61/:7664/science_resource?serverTimezone=UTC","root","123456");
            ArrayList<Object> l=new ArrayList();
            l.add(connection);
            l.add(1);
            pool.add(l);
        }
    }

    public void close() throws SQLException {
        for(int i=0;i<pool.size();i++){
            Connection c=(Connection)pool.get(i).get(0);
            c.close();
        }
        pool.clear();
        this.setConnectNum(0);
    }

    public void ResetConnection(int connectNum) throws SQLException {
        for(int i=0;i<pool.size();i++){
            Connection c=(Connection)pool.get(i).get(0);
            c.close();
        }
        pool.clear();
        this.setConnectNum(connectNum);
        for(int i=0;i<connectNum;i++){
            Connection connection=DriverManager.getConnection("jdbc:mysql://10.112.120.61:7664/science_resource?serverTimezone=UTC","root","123456");
            ArrayList<Object> l=new ArrayList();
            l.add(connection);
            l.add(1);
            pool.add(l);
        }
    }

    public boolean deleteConnection(int index) throws SQLException {
        if((Integer)(pool.get(index).get(1))==0) return false;
        Connection c=(Connection)(pool.get(index).get(0));
        c.close();
        this.pool.remove(index);
        this.setConnectNum(this.ConnectNum-1);
        return true;
    }

    public void setConnectNum(int connectNum) {
        ConnectNum = connectNum;
    }

    public int getConnectNum(){
        return this.ConnectNum;
    }

    public LinkedList<ArrayList<Object>> getConnection(){
        return this.pool;
    }

    public boolean setUsed(int index){
        if((Integer)(pool.get(index).get(1))==0) return false;
        pool.get(index).remove(1);
        pool.get(index).add(0);
        return true;
    }

    public boolean setUnused(int index){
        if((Integer)(pool.get(index).get(1))==1) return false;
        pool.get(index).remove(1);
        pool.get(index).add(1);
        return true;
    }
}
