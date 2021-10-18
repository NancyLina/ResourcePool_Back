package resource_pool.demo.domain;

import java.util.LinkedHashMap;

public class Patent {
    public void setPatentType(String patentType) {
        this.patentType = patentType;
    }

    public void setAPD(String APD) {
        this.APD = APD;
    }

    public void setPR(String PR) {
        this.PR = PR;
    }

    public void setINVIEW(String INVIEW) {
        this.INVIEW = INVIEW;
    }

    public void setPAVIEW(String PAVIEW) {
        this.PAVIEW = PAVIEW;
    }

    public void setTIVIEW(String TIVIEW) {
        this.TIVIEW = TIVIEW;
    }

    public void setAGY(String AGY) {
        this.AGY = AGY;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    private int number;

    private String TIVIEW;//专利名

    public String getTIVIEW() {
        return TIVIEW;
    }

    public String getAGY() {
        return AGY;
    }

    private String AGY;//代理人

    private String patentType;//类型

    private String APD;//申请日期

    private String PR;//IPC

    private String INVIEW;//发明人

    private String PAVIEW;//申请人

    public String getPatentType() {
        return patentType;
    }

    public String getAPD() {
        return APD;
    }

    public String getPR() {
        return PR;
    }

    public String getINVIEW() {
        return INVIEW;
    }

    public String getPAVIEW() {
        return PAVIEW;
    }

    private String ID;

    private String FNUM;

    private String CPNUM;

    private String VID;

    private String LANG;

    public String getName() {
        return name;
    }

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFNUM() {
        return FNUM;
    }

    public void setFNUM(String FNUM) {
        this.FNUM = FNUM;
    }

    public String getCPNUM() {
        return CPNUM;
    }

    public void setCPNUM(String CPNUM) {
        this.CPNUM = CPNUM;
    }

    public String getVID() {
        return VID;
    }

    public void setVID(String VID) {
        this.VID = VID;
    }

    public String getLANG() {
        return LANG;
    }

    public void setLANG(String LANG) {
        this.LANG = LANG;
    }

    public String toString()
    {
        return "{\"ID\":\""+this.ID+"\",\"FNUM\":\""+this.FNUM+"\",\"CPNUM\":\""+this.CPNUM+"\",\"VID\":\""+this.VID+"\",\"LANG\":\""+this.LANG+"\"}";
    }

    public LinkedHashMap tohashmap()
    {
        LinkedHashMap map=new LinkedHashMap();
        map.put("类型",this.patentType);
        map.put("申请日期",this.APD);
        map.put("IPC",this.PR);
        map.put("发明人",this.INVIEW);
        map.put("申请人",this.PAVIEW);
        map.put("ID",this.ID);
        map.put("代理人",this.AGY);
        map.put("专利名",this.TIVIEW);
        return map;
    }

    public LinkedHashMap toObject()
    {
        LinkedHashMap map=new LinkedHashMap();
        map.put("number",this.number);
        map.put("lang",this.LANG);
        return map;
    }

    public LinkedHashMap querys(String name){
        String result="";
        LinkedHashMap<String,String> store=new LinkedHashMap();
        store.put("LANG",this.LANG);
        store.put("TIVIEW",this.TIVIEW);
        store.put("AGY",this.AGY);
        store.put("patentType",this.patentType);
        store.put("APD",this.APD);
        store.put("PR",this.PR);
        store.put("INVIEW",this.INVIEW);
        store.put("PAVIEW",this.PAVIEW);
        store.put("ID",this.ID);
        store.put("FNUM",this.FNUM);
        store.put("CPNUM",this.CPNUM);
        store.put("VID",this.VID);
        store.put("name",this.name);
        result=store.get(name);
        if(result==null)
            result="其他";
        System.out.println(result);
        LinkedHashMap map=new LinkedHashMap();
        map.put("number",this.number);
        map.put("result",result);
        return map;
    }
}

