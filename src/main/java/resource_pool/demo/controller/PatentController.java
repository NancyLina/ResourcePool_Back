package resource_pool.demo.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import resource_pool.demo.domain.Patent;
import resource_pool.demo.domain.User;
import resource_pool.demo.domain.table;
import resource_pool.demo.mapper.PatentMapper;


import java.util.*;


@RestController
@RequestMapping("/patent")
public class PatentController {

    private int count=0;

    private Map<String, Object> m = new HashMap<>();

    @Autowired
    private PatentMapper patentMapper;

//    @Autowired
//    private table t;

    @GetMapping("findbyid")
    public Object Find_by_id(String id) {
        return patentMapper.findById(id).toString();
    }

    @GetMapping("findall")
    public Object Find_all() {
        System.out.println(count+"findall");
        count++;
        return patentMapper.findall().toString();
    }

    @GetMapping("create")
    public Object Create(String tableName) {
        patentMapper.update(tableName);
        return "create success";
    }

    @GetMapping("drop")
    public Object Delete(String tableName) {
        System.out.println(tableName);
        patentMapper.delete(tableName);
        return "delete success";
    }

    @PostMapping("posts")
    public Object posts(String id) {
        m.put("id", id);
        return m;
    }

    @PostMapping("test")
    public Object test(@RequestBody User user) {
        System.out.println("123");
        LinkedHashMap map = new LinkedHashMap();
        System.out.println(user.getUsername()+" "+user.getPassword());
        map.put("info", "return_information");
        return map;
    }

    @PostMapping("database")
    public Object databases(@RequestBody LinkedHashMap user) {
        System.out.println(user.get("username"));
        System.out.println(user);
        user.put("user", "yes");
//        JSONObject object = new JSONObject();
//        object.put("a","456");
        return user;
    }

    @PostMapping("resource_pool")
    public Object resource_pool(@RequestBody LinkedHashMap user) {
//        System.out.println(user.get("record").toString());
//        t.setRecord(user.get("record").toString());
//        t.setName(user.get("name").toString());
        patentMapper.insert(user.get("record").toString(), user.get("name").toString());
        user.put("information", "添加成功");
        return user;
    }

    @PostMapping("confirm")
    public Object confirm(@RequestBody LinkedHashMap user) {

        String sql = "create table " + user.get("name") + "(";
        List<table> list = new ArrayList<>();
        list = patentMapper.Choose();
        for (int i = 0; i < list.size(); i++) {
            String Record = list.get(i).getRecord();
            String Name = list.get(i).getName();
            if (i != list.size() - 1)
                sql += Record + " " + Name + ",";
            else
                sql += Record + " " + Name;
        }
        sql += ");";
        System.out.println(sql);
        patentMapper.create(sql);
        user.put("information", "创建表成功");
        return user;
    }

    @PostMapping("delete")
    public Object deletes(@RequestBody LinkedHashMap user) {
        patentMapper.clear_table();
        user.put("information", "操作成功");
        return user;
    }

    @PostMapping("drop")
    public Object drops(@RequestBody LinkedHashMap user) {
        patentMapper.delete(user.get("name").toString());
        user.put("information", "删除表成功");
        return user;
    }

    @PostMapping("draw")
    public Object draws(@RequestBody LinkedHashMap user) {
        LinkedHashMap map = new LinkedHashMap();
        String sql = "SELECT COUNT(*) AS number,"+user.get("record")+" FROM "+ user.get("title")+" group by "+ user.get("record")+" limit 5";
        System.out.println(sql);
        List<Patent> patent = new ArrayList<>();
        patent = patentMapper.findbysth(sql);
        for (int i = 0; i < patent.size(); i++) {
            map.put(i, patent.get(i).querys(user.get("record").toString()));
//            System.out.println(patent.get(i).toObject());
        }
        return map;
    }


    @PostMapping("query")
    public Object querys(@RequestBody LinkedHashMap user) {
        int tag = 0;
        String sql = "SELECT patentType,APD,PR,INVIEW,PAVIEW,ID FROM patent_infor";
        String s = " WHERE ";
        Iterator iter = user.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            if (entry.getValue() != "" && entry.getKey() != "year" && entry.getKey() != "month" && entry.getKey() != "date") {
                if (tag == 0) {
                    s += entry.getKey() + "=\'" + entry.getValue() + "\'";
                    tag = 1;
                } else
                    s += " and " + entry.getKey() + "=\'" + entry.getValue() + "\'";
            }
//                System.out.println(entry.getKey()+" "+entry.getValue());
        }
        if (user.get("year") != "" && user.get("month") != "" && user.get("date") != "") {
            if (s != " WHERE ")
                s += " and ";
            s += "APD=\'" + user.get("year") + ".";
            if (user.get("month").toString().length() == 1)
                s += "0" + user.get("month");
            else
                s += user.get("month");
            s += ".";
            if (user.get("date").toString().length() == 1)
                s += "0" + user.get("date");
            else
                s += user.get("date");
            s += "\'";
        }

        if (s != " WHERE ")
            sql += s;
        sql += " limit 20";
        System.out.println(sql);
        LinkedHashMap map = new LinkedHashMap();
        List<Patent> patent = new ArrayList<>();
        patent = patentMapper.findbysth(sql);
        for (int i = 0; i < patent.size(); i++) {
            map.put(i, patent.get(i).tohashmap());
        }
//        "year","month","date":"category1":"agent","PatentName""Inventor":,"Applicant"
//        user.put("information","操作成功");
        return map;
    }

    @GetMapping("mybatis_test")
    public Object mybatis_test(String id) {
        System.out.println(id);
        return patentMapper.Id_test(id).toString();
    }
}
