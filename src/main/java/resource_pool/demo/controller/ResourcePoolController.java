package resource_pool.demo.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import resource_pool.demo.connection.SqlConnection;
import resource_pool.demo.domain.Table_name;
import resource_pool.demo.domain.table_information;
import resource_pool.demo.mapper.RPMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import resource_pool.demo.connection.SqlConnection;

import javax.annotation.Resource;

import static java.util.Arrays.asList;


@RestController
@RequestMapping("/ResourcePool")
public class ResourcePoolController {

    private int InitConnection = 5;

    private SqlConnection sqlconnection;

    @Resource
    public RPMapper rpMapper;

    public ResourcePoolController() throws SQLException {
        this.sqlconnection = new SqlConnection(this.InitConnection);
    }

    @PostMapping("init")
    public Object init() {
        Map<String, Object> ans =new HashMap();
        ans.put("platform1", "平台A");
        ans.put("platform2", "平台B");
        ans.put("platform3", "总平台");
        return ans;
    }

    @PostMapping("changePlatform")
    public Object changePlatform(@RequestBody LinkedHashMap user) {
        String choose1 = user.get("choose1").toString();
        String choose2 = user.get("choose2").toString();
        Map<String, Object> ans = getAnswer(choose1, choose2);
        return ans;
    }

    private Map<String, Object> getAnswer(String choose1, String choose2) {
        Map<String, Object> ans = new HashMap();
        List<String> titles1 = getTitles(choose1);
        ans.put("titles1", titles1);
        List<Map<String, String>> items1 = getItems(choose1);
        ans.put("items1", items1);

        List<String> titles2 = getTitles(choose2);
        ans.put("titles2", titles2);
        List<Map<String, String>> items2 = getItems(choose2);
        ans.put("items2", items2);

        List<String> titles3 = getTitles("总平台");
        ans.put("titles3", titles3);
        List<Map<String, String>> items3 = getItems(choose1 + "," + choose2);
        ans.put("items3", items3);

        List<String> titles4 = new ArrayList();
        if (titles1.size() != 0) {
            titles4.add(choose1);
        }
        if (titles2.size() != 0 && !choose1.equals(choose2)) {
            titles4.add(choose2);
        }
        titles4.add("总平台");
        ans.put("titles4", titles4);

        List<String> title1 = new ArrayList();
        title1.addAll(titles1);
        if (title1.size() == 5) {
            title1.add(1, "无该字段");
        }
        List<String> title2 = new ArrayList();
        title2.addAll(titles2);
        if (title2.size() == 5) {
            title2.add(1, "无该字段");
        }
        List<Map<String, String>> items4 = new ArrayList();
        for (int i = 0; i < titles3.size(); i++) {
            Map<String, String> map = new LinkedHashMap();
            if (title1.size() != 0) {
                map.put("platform1", title1.get(i));
            }
            if (title2.size() != 0 && !choose1.equals(choose2)) {
                map.put("platform2", title2.get(i));
            }
            map.put("总平台", titles3.get(i));
            items4.add(map);
        }
        ans.put("items4", items4);

        Map<String, String> map = getMap();
        ans.put("platform1", choose1);
        ans.put("ratio1", map.get(choose1));
        ans.put("platform2", choose2);
        ans.put("ratio2", map.get(choose2));
        ans.put("platform3", "总平台");
        ans.put("ratio3", map.get("总平台"));
        return ans;
    }

    private Map<String,String> getMap() {
        Map<String, String> map = new HashMap();
        map.put("好专家网", "14.34%");
        map.put("重庆科技服务平台", "22.38%");
        map.put("黑龙江科技服务平台", "24.61%");
        map.put("哈长城市群科技服务平台", "38.67%");
        map.put("总平台", "100%");
        return map;
    }

    private List<Map<String, String>> getItems(String platform) {
        List<Map<String, String>> items = new ArrayList();
        Map<String, String> map;
        if ("好专家网".equals(platform)) {
            map = new LinkedHashMap();
            map.put("姓名", "陈军");
            map.put("从事专业", "机械设计及制造专业");
            map.put("技术职称", "副高");
            map.put("所在单位", "中国水利水电科学研究院渔机所");
            map.put("最高学历", "本科");
            items.add(map);
        } else if ("重庆科技服务平台".equals(platform)) {
            map = new LinkedHashMap();
            map.put("人才姓名", "杨永斌");
            map.put("所学专业", "土木工程");
            map.put("人才类别", "科学研究类");
            map.put("职称", "正高");
            map.put("所属单位", "重庆大学(土木工程学院)");
            map.put("最高学历", "博士");
            items.add(map);
        } else if ("黑龙江科技服务平台".equals(platform)) {
            map = new LinkedHashMap();
            map.put("姓名", "吴偶");
            map.put("从事专业", "人工智能、大数据");
            map.put("技术职称", "正高");
            map.put("所在单位", "天津大学");
            map.put("最高学历", "博士研究生");
            items.add(map);
        } else if ("哈长城市群科技服务平台".equals(platform)) {
            map = new LinkedHashMap();
            map.put("人才姓名", "李洪平");
            map.put("所学专业", "药物化学");
            map.put("人才类别", "工程开发类");
            map.put("职称", "副高");
            map.put("所属单位", "重庆万利康制药有限公司");
            map.put("最高学历", "本科");
            items.add(map);
        } else {
            String[] platforms = platform.split(",");
            String flag = "init";
            for (String plat : platforms) {
                if (flag.equals(plat)) {
                    continue;
                }
                flag = plat;
                if ("好专家网".equals(plat)) {
                    map = new LinkedHashMap();
                    map.put("专家姓名", "陈军");
                    map.put("研究方向", "暂无数据");
                    map.put("专业领域", "机械设计及制造专业");
                    map.put("专家职称", "副高");
                    map.put("单位", "中国水利水电科学研究院渔机所");
                    map.put("最高学历", "本科");
                    items.add(map);
                } else if ("重庆科技服务平台".equals(plat)) {
                    map = new LinkedHashMap();
                    map.put("专家姓名", "杨永斌");
                    map.put("研究方向", "土木工程");
                    map.put("专业领域", "科学研究类");
                    map.put("专家职称", "正高");
                    map.put("单位", "重庆大学(土木工程学院)");
                    map.put("最高学历", "博士");
                    items.add(map);
                } else if ("黑龙江科技服务平台".equals(plat)) {
                    map = new LinkedHashMap();
                    map.put("专家姓名", "吴偶");
                    map.put("研究方向", "暂无数据");
                    map.put("专业领域", "人工智能、大数据");
                    map.put("专家职称", "正高");
                    map.put("单位", "天津大学");
                    map.put("最高学历", "博士研究生");
                    items.add(map);
                } else if ("哈长城市群科技服务平台".equals(plat)) {
                    map = new LinkedHashMap();
                    map.put("专家姓名", "李洪平");
                    map.put("研究方向", "药物化学");
                    map.put("专业领域", "副高");
                    map.put("专家职称", "工程开发类");
                    map.put("单位", "重庆大学(重庆万利康制药有限公司)");
                    map.put("最高学历", "本科");
                    items.add(map);
                }
            }
        }
        return items;
    }

    private List<String> getTitles(String platform) {
        List<String> titles = new ArrayList();
        if ("好专家网".equals(platform) || "黑龙江科技服务平台".equals(platform)) {
            titles.add("姓名");
            titles.add("从事专业");
            titles.add("技术职称");
            titles.add("所在单位");
            titles.add("最高学历");
        } else if ("总平台".equals(platform)) {
            titles.add("专家姓名");
            titles.add("研究方向");
            titles.add("专业领域");
            titles.add("专家职称");
            titles.add("单位");
            titles.add("最高学历");
        } else if("重庆科技服务平台".equals(platform) || "哈长城市群科技服务平台".equals(platform)){
            titles.add("人才姓名");
            titles.add("所学专业");
            titles.add("人才类别");
            titles.add("职称");
            titles.add("所属单位");
            titles.add("最高学历");
        }
        return titles;
    }

    @PostMapping("add_infor")
    public Object add_infor(@RequestBody LinkedHashMap user) {
        int type = (Integer) user.get("type");
        if (type >= 0 && type <= 2) {
            user.put("information", "没有添加权限");
        } else {
            String title = user.get("title").toString();
            List content = (ArrayList) user.get("content");
            String sql = "select *from table_form where title=\"" + title + "\"";
            System.out.println(sql);
            List<table_information> list = rpMapper.search_table(sql);
            String table_name = list.get(0).getName();
            String infor = "(";
            String name = "(";
            sql = "Insert into " + table_name;
            for (int i = 0; i < content.size(); i++) {
                LinkedHashMap data = (LinkedHashMap) content.get(i);

                if (i == 0) {
                    infor += data.get("infor");
                    name += "\"" + data.get("name") + "\"";
                } else {
                    infor += "," + data.get("infor");
                    name += ",\"" + data.get("name") + "\"";
                }
            }
            if (infor.equals("(")) {
                user.put("information", "no input");
                return user;
            }
            infor += ")";
            name += ")";
            sql += " " + infor + " values " + name;
            System.out.println(sql);
            rpMapper.insert(sql);
            user.put("information", "success");
        }
        return user;
    }

    @PostMapping("create_database")
    public Object databases(@RequestBody LinkedHashMap user) {
        System.out.println(user.get("title"));
        String title = user.get("title").toString();
        System.out.println(user.get("content"));
        List list = (ArrayList) user.get("content");
        String sql = "create table " + title + "(";
        int num = 0;
        for (int i = 0; i < list.size(); i++) {
            LinkedHashMap data = (LinkedHashMap) list.get(i);
            String record = data.get("name").toString();
            String type = data.get("type").toString();
            if (num == 0) {
                sql += record + " " + type;
                num++;
            } else sql += "," + record + " " + type;
        }
        sql += ");";
        System.out.println(sql);
        rpMapper.create(sql);
        sql = "insert into table_form(name,title,del) values(\"" + title + "\",\"" + title + "资源池\",1)";
        System.out.println(sql);
        rpMapper.insert(sql);
        System.out.println("finished");

        user.put("information", "success");
        return user;
    }

    @PostMapping("take_tables")
    public Object taketables() {
        List<String> result = new ArrayList<>();
        List<table_information> list = rpMapper.take_table();
        for (int i = 0; i < list.size(); i++) {
            String title = list.get(i).getTitle();
            result.add(title);
        }
        return result;
    }

    @PostMapping("findByGroup")
    public Object findByGroup(@RequestBody LinkedHashMap user) {
        HashMap result = new HashMap();
        List<String> records = new ArrayList<>();
        String title = user.get("title").toString();
        String record = user.get("record").toString();
        if ("一级分类".equals(record)) {
            record = "machine_big_type";
        } else if ("二级分类".equals(record)) {
            record = "machine_sim_type";
        } else if ("所属公司".equals(record)) {
            record = "company";
        } else if ("浏览量".equals(record)) {
            record = "view_info";
        } else if ("所属地".equals(record)) {
            record = "place";
        } else if ("购置日期".equals(record)) {
            record = "buydate";
        } else if ("学历".equals(record)) {
            record = "degreesName";
        } else if ("性别".equals(record)) {
            record = "sex";
        } else if ("职称".equals(record)) {
            record = "tecProfessionalName";
        } else if ("擅长领域".equals(record)) {
            record = "expertTypeText";
        } else if ("专利类型".equals(record)) {
            record = "patentType";
        } else if ("语言".equals(record)) {
            record = "LANG";
        } else if ("地区".equals(record)) {
            record = "AC";
        } else if ("申请人".equals(record)) {
            record = "INVIEW";
        } else if ("代理公司".equals(record)) {
            record = "AGY";
        } else if ("申请状态".equals(record)) {
            record = "lawStatus";
        }
        String sql = "select *from table_form where title=\"" + title + "\"";
        System.out.println(sql);
        List<table_information> list = rpMapper.search_table(sql);
        String table_name = list.get(0).getName();
        sql = "select count(*) as value," + record + " as name from " + table_name + " group by " + record + " limit 10";
        System.out.println(sql);
        List<HashMap<String, Object>> lists = rpMapper.find(sql);
        for (int i = 0; i < lists.size(); i++) {
            if (lists.get(i).get("name") != null)
                records.add(lists.get(i).get("name").toString());
            else records.add("其他");
        }
        result.put("records", records);
        result.put("values", lists);
        return result;
    }

    @PostMapping("delete_database")
    public Object delete_databases(@RequestBody LinkedHashMap user) {
        if (!user.containsKey("title")) {
            user.put("information", "没有删除权限");
            return user;
        }
        String title = user.get("title").toString();
        String sql = "select *from table_form where title=\"" + title + "\"";
        System.out.println(sql);
        List<table_information> list = rpMapper.search_table(sql);
        if (list.get(0).getDel() == 1) {
            sql = "delete from table_form where title=\"" + title + "\"";
            System.out.println(sql);
            rpMapper.delete(sql);
            sql = "drop table " + list.get(0).getName();
            System.out.println(sql);
            rpMapper.delete(sql);
            user.put("information", "删除成功，请刷新");
        } else user.put("information", "没有删除权限");
        return user;
    }

    @PostMapping("findContent")
    public Object findContents(@RequestBody LinkedHashMap user) {
        String title = user.get("title").toString();
        List<String> ans = new ArrayList();
        if ("仪器资源".equals(title)) {
            ans.add("一级分类");
            ans.add("二级分类");
            ans.add("所属公司");
            ans.add("浏览量");
            ans.add("所属地");
            ans.add("购置日期");
        } else if ("专家资源".equals(title)) {
            ans.add("性别");
            ans.add("职称");
            ans.add("学历");
            ans.add("擅长领域");
        } else if ("专利资源".equals(title)) {
            ans.add("专利类型");
            ans.add("语言");
            ans.add("地区");
            ans.add("申请人");
            ans.add("代理公司");
            ans.add("申请状态");
        } else {
            String sql = "select *from table_form where title=\"" + title + "\"";
            System.out.println(sql);
            List<table_information> list = rpMapper.search_table(sql);
            String table_name = list.get(0).getName();
            sql = "select COLUMN_NAME from information_schema.COLUMNS where table_name=\"" + table_name + "\"";
            System.out.println(sql);
            List<Table_name> result = rpMapper.search_tablename(sql);
            for (int i = 0; i < result.size(); i++) {
                ans.add(result.get(i).getColumn_name());
            }
        }
        return ans;
    }

    @PostMapping("findAreaInformation")
    public Object findAreaInformation(@RequestBody LinkedHashMap user) {
        String title = user.get("title").toString();
        List<String> ans = new ArrayList();
        if ("仪器资源".equals(title)) {
            for (int i = 0; i < 35; i++) {
                if (i == 2) {
                    ans.add("39435");
                } else if (i == 24) {
                    ans.add("10461");
                } else if (i == 13) {
                    ans.add("60");
                } else if (i == 29) {
                    ans.add("37");
                } else {
                    ans.add("0");
                }
            }
            ans.add("国内仪器信息");
        } else if ("专家资源".equals(title)) {
            ans.add("8115");
            ans.add("649");
            ans.add("2349");
            ans.add("808");
            ans.add("371");
            ans.add("3159");
            ans.add("0");
            ans.add("623");
            ans.add("0");
            ans.add("872");
            ans.add("1953");
            ans.add("1689");
            ans.add("0");
            ans.add("3824");
            ans.add("2167");
            ans.add("399");
            ans.add("1674");
            ans.add("293");
            ans.add("101");
            ans.add("154");
            ans.add("272");
            ans.add("971");
            ans.add("184");
            ans.add("968");
            ans.add("223");
            ans.add("11564");
            ans.add("0");
            ans.add("0");
            ans.add("1851");
            ans.add("0");
            ans.add("0");
            ans.add("213");
            ans.add("0");
            ans.add("0");
            ans.add("0");
            ans.add("国内专家信息");
        } else if ("专利资源".equals(title)) {
            ans.add("8115");
            ans.add("649");
            ans.add("2349");
            ans.add("808");
            ans.add("371");
            ans.add("3159");
            ans.add("0");
            ans.add("623");
            ans.add("0");
            ans.add("872");
            ans.add("1953");
            ans.add("1689");
            ans.add("0");
            ans.add("3824");
            ans.add("2167");
            ans.add("399");
            ans.add("1674");
            ans.add("293");
            ans.add("101");
            ans.add("154");
            ans.add("272");
            ans.add("971");
            ans.add("184");
            ans.add("968");
            ans.add("223");
            ans.add("11564");
            ans.add("0");
            ans.add("0");
            ans.add("1851");
            ans.add("0");
            ans.add("0");
            ans.add("213");
            ans.add("0");
            ans.add("0");
            ans.add("0");
            ans.add("国内专利信息");
        } else {
            for (int i = 0; i < 35; i++) {
                ans.add("0");
            }
            ans.add(title);
        }
        return ans;
    }

    @PostMapping("write")
    public void write(@RequestBody LinkedHashMap user) {
        String name = user.get("name").toString();
        String types = user.get("type").toString();
        try {
            FileWriter writer = new FileWriter("F:\\yanxiao\\毕业设计\\demo\\query.txt");
            writer.write(name + " " + types);
            writer.close();
        } catch (IOException e) {

        }
//        System.out.println(name+" "+types);
    }

    @PostMapping("findBySth")
    public Object findbysth(@RequestBody LinkedHashMap user) {
        String title = user.get("title").toString();
        List<Map<String, String>> content = (ArrayList) user.get("content");
        for (Map<String, String> map : content) {
            if (map.containsKey("infor")) {
                String record = map.get("infor");
                if ("一级分类".equals(record)) {
                    record = "machine_big_type";
                } else if ("二级分类".equals(record)) {
                    record = "machine_sim_type";
                } else if ("所属公司".equals(record)) {
                    record = "company";
                } else if ("浏览量".equals(record)) {
                    record = "view_info";
                } else if ("所属地".equals(record)) {
                    record = "place";
                } else if ("购置日期".equals(record)) {
                    record = "buydate";
                } else if ("学历".equals(record)) {
                    record = "degreesName";
                } else if ("性别".equals(record)) {
                    record = "sex";
                } else if ("职称".equals(record)) {
                    record = "tecProfessionalName";
                } else if ("擅长领域".equals(record)) {
                    record = "expertTypeText";
                } else if ("专利类型".equals(record)) {
                    record = "patentType";
                } else if ("语言".equals(record)) {
                    record = "LANG";
                } else if ("地区".equals(record)) {
                    record = "AC";
                } else if ("申请人".equals(record)) {
                    record = "INVIEW";
                } else if ("代理公司".equals(record)) {
                    record = "AGY";
                } else if ("申请状态".equals(record)) {
                    record = "lawStatus";
                }
                map.put("infor", record);
            }
        }
        String sql = "select *from table_form where title=\"" + title + "\"";
        System.out.println(sql);
        List<table_information> list = rpMapper.search_table(sql);
        String table_name = list.get(0).getName();
        int type = (Integer) user.get("type");
        List<List<String>> ans = new ArrayList<>();
        String condition = " where ";
        for (int i = 0; i < content.size(); i++) {
            LinkedHashMap data = (LinkedHashMap) content.get(i);
            if (i == 0)
                condition += data.get("infor") + "=\"" + data.get("name") + "\"";
            else
                condition += " and " + data.get("infor") + "=\"" + data.get("name") + "\"";
        }
        sql = "select *from " + table_name;
        if (!condition.equals(" where ")) sql += condition;
        sql += " limit 7";
        System.out.println(sql);
        List<HashMap> result = (ArrayList) rpMapper.find(sql);
        switch (type) {
            case 0:
                for (int i = 0; i < result.size(); i++) {
                    List lists = new ArrayList();
                    if (result.get(i).get("name") == null)
                        lists.add("暂无信息");
                    else
                        lists.add(result.get(i).get("name").toString());
                    lists.add(result.get(i).get("machine_sim_type") == null ? "暂无信息" : result.get(i).get("machine_sim_type").toString());
                    lists.add(result.get(i).get("makein") == null ? "暂无信息" : result.get(i).get("makein").toString());
                    lists.add(result.get(i).get("place") == null ? "暂无信息" : result.get(i).get("place").toString());
                    lists.add(result.get(i).get("buydate") == null ? "暂无信息" : result.get(i).get("buydate").toString());
                    lists.add(result.get(i).get("country") == null ? "暂无信息" : result.get(i).get("country").toString());
                    ans.add(lists);
                }
                break;
            case 1:
                for (int i = 0; i < result.size(); i++) {
                    List lists = new ArrayList();
                    if (result.get(i).get("name") == null)
                        lists.add("暂无信息");
                    else
                        lists.add(result.get(i).get("name").toString());
                    lists.add(result.get(i).get("specialty") == null ? "暂无信息" : result.get(i).get("specialty").toString());
                    lists.add(result.get(i).get("degreesName") == null ? "暂无信息" : result.get(i).get("degreesName").toString());
                    lists.add(result.get(i).get("workUnit") == null ? "暂无信息" : result.get(i).get("workUnit").toString());
                    ans.add(lists);
                }
                break;
            case 2:
                for (int i = 0; i < result.size(); i++) {
                    List lists = new ArrayList();
                    if (result.get(i).get("TIVIEW") == null)
                        lists.add("暂无信息");
                    else
                        lists.add(result.get(i).get("TIVIEW").toString());
                    lists.add(result.get(i).get("patentType") == null ? "暂无信息" : result.get(i).get("patentType").toString());
                    lists.add(result.get(i).get("PR") == null ? "暂无信息" : result.get(i).get("PR").toString());
                    lists.add(result.get(i).get("INVIEW") == null ? "暂无信息" : result.get(i).get("INVIEW").toString());
                    ans.add(lists);
                }
                break;
            default:
                if (type < 0) break;
                List<String> names = new ArrayList<>();
                sql = "select COLUMN_NAME from information_schema.COLUMNS where table_name=\"" + table_name + "\"";
                System.out.println(sql);
                List<Table_name> results = rpMapper.search_tablename(sql);
                for (int i = 0; i < results.size(); i++) {
                    names.add(results.get(i).getColumn_name());
                }
                for (int i = 0; i < result.size(); i++) {
                    List<String> l = new ArrayList<>();
                    for (int j = 0; j < names.size(); j++) {
                        l.add(result.get(i).get(names.get(j)).toString());
                    }
                    ans.add(l);
                }
                break;
        }
        return ans;
    }


    @PostMapping("table_data")
    public Object table_data(@RequestBody LinkedHashMap user) {
//        LinkedHashMap ans = new LinkedHashMap();
        HashMap<String,Object> ans = new HashMap<String,Object>();
        List<List<String>> infor = new ArrayList<>();
        List<HashMap> res = null;
        List<String> label = new ArrayList<String>();
        List<HashMap> head = new ArrayList<HashMap>();
        int type = (Integer) user.get("type");
        if (type >= 0 && type <= 2) {
            switch (type) {
                case 0:
                    head.add(new HashMap<String,String>(){{put("label","仪器名称");put("val","name");}});
                    head.add(new HashMap<String,String>(){{put("label","仪器类型");put("val","model");}});
                    head.add(new HashMap<String,String>(){{put("label","制造厂商");put("val","makein");}});
                    head.add(new HashMap<String,String>(){{put("label","所在地");put("val","place");}});
                    head.add(new HashMap<String,String>(){{put("label","购置日期");put("val","buydate");}});
                    String sql = "select id,name,model,makein,place,buydate from equipment";
                    res = (ArrayList) rpMapper.find(sql);
                    break;
                case 1:
                    head.add(new HashMap<String,String>(){{put("label","名字");put("val","name");}});
                    head.add(new HashMap<String,String>(){{put("label","专业领域");put("val","specialty");}});
                    head.add(new HashMap<String,String>(){{put("label","学历");put("val","degreesName");}});
                    head.add(new HashMap<String,String>(){{put("label","工作单位");put("val","workUnit");}});
                    sql = "select id,name,specialty,degreesName,workUnit from expert";
                    res = (ArrayList) rpMapper.find(sql);
                    break;
                case 2:
                    head.add(new HashMap<String,String>(){{put("label","专利名称");put("val","name");}});
                    head.add(new HashMap<String,String>(){{put("label","专利号");put("val","patentNumber");}});
                    head.add(new HashMap<String,String>(){{put("label","IPC");put("val","ipcType");}});
                    head.add(new HashMap<String,String>(){{put("label","授权时间");put("val","publicationDate");}});
                    head.add(new HashMap<String,String>(){{put("label","专利状态");put("val","status");}});
                    sql = "select id,patentName as name,patentNumber,ipcType,publicationDate,status from patent";
                    res = (ArrayList) rpMapper.find(sql);
                    break;
            }
        } else if (type > 2) {
            String title = user.get("title").toString();
            String sql = "select *from table_form where title=\"" + title + "\"";
            System.out.println(sql);
            List<table_information> list = rpMapper.search_table(sql);
            String table_name = list.get(0).getName();
            sql = "select COLUMN_NAME from information_schema.COLUMNS where table_name=\"" + table_name + "\"";
            System.out.println(sql);
            List<Table_name> result = rpMapper.search_tablename(sql);
            for (int i = 0; i < result.size(); i++) {
                label.add(result.get(i).getColumn_name());
            }
            sql = "select * from " + table_name + " limit 7";
            res = (ArrayList)rpMapper.find(sql);
            System.out.println(res);
            for (int i = 0; i < res.size(); i++) {
                List<String> l = new ArrayList<>();
                for (int j = 0; j < label.size(); j++) {
                    if (res.get(i).get(label.get(j)) != null)
                        l.add(res.get(i).get(label.get(j)).toString());
                }
                infor.add(l);
            }
        }
        ans.put("title",head);
        ans.put("data",res);
//        ans.put("total", count.get(0).get("count"));
        System.out.println(ans);
        return ans;
    }

    @PostMapping("CloseConnection")
    public void CloseConnection() throws SQLException {
        this.sqlconnection.close();
    }

    @PostMapping("ResetConnection")
    public void ResetConnection(@RequestBody LinkedHashMap user) throws SQLException {
        int connectNum = (Integer) user.get("connectNum");
        this.sqlconnection.ResetConnection(connectNum);
    }

    @PostMapping("AddConnection")
    public void AddConnection() throws SQLException {
        this.sqlconnection.addConnection(1);
    }

    @PostMapping("GetConnection")
    public Object GetConnection() throws SQLException {
        List<HashMap<String, String>> result = new ArrayList<>();
        LinkedList<ArrayList<Object>> list = this.sqlconnection.getConnection();
        for (int i = 0; i < list.size(); i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", list.get(i).get(0).toString());
            if ((Integer) (list.get(i).get(1)) == 1) {
                map.put("type", "可使用");
            } else {
                map.put("type", "已使用");
            }
            result.add(map);
        }
        return result;
    }

    @PostMapping("DeleteConnection")
    public Object DeleteConnection(@RequestBody LinkedHashMap user) throws SQLException {
        int index = (Integer) user.get("index");
        HashMap<Object, Object> map = new HashMap<>();
        if (!this.sqlconnection.deleteConnection(index)) {
            map.put("information", 1);//删除失败
        } else map.put("information", 0);
        return map;
    }

    @PostMapping("use")
    public Object use(@RequestBody LinkedHashMap user) throws SQLException {
        int index = (Integer) user.get("index");
        HashMap<Object, Object> map = new HashMap<>();
        boolean ifused = this.sqlconnection.setUsed(index);
        if (ifused) {
            map.put("information", 1);
        } else {
            map.put("information", 0);//已经使用
        }
        return map;
    }

    @PostMapping("unuse")
    public Object unuse(@RequestBody LinkedHashMap user) throws SQLException {
        int index = (Integer) user.get("index");
        HashMap<Object, Object> map = new HashMap<>();
        boolean ifused = this.sqlconnection.setUnused(index);
        if (ifused) {
            map.put("information", 1);//已经使用
        } else {
            map.put("information", 0);
        }
        return map;
    }

    @PostMapping("check")
    public Object check() throws SQLException {
        String sql = "select name,state,staffSize,registerCapital,establishmentDate,telephone,email,businessScope from company limit 5";
        System.out.println(sql);
        List<HashMap<String, Object>> result = rpMapper.find(sql);
        System.out.println(result.toString());
        return result;
    }

    @PostMapping("intelproperty")
    public Object intelproperty() throws SQLException {
        String sql = "select name,state,staffSize,registerCapital,establishmentDate,telephone,email,businessScope from intelproperty limit 5";
        System.out.println(sql);
        List<HashMap<String, Object>> result = rpMapper.find(sql);
        System.out.println(result.toString());
        return result;
    }

    @PostMapping("map")
    public Object mapResult(@RequestBody LinkedHashMap user) throws JSONException {
        HashMap<Object, Object> map = new HashMap<>();
        String mode = (String) user.get("mode");
        String ak = (String) user.get("ak");
        String sk = (String) user.get("sk");
        if ("CNN".equals(mode)) {
            map.put("result", getAuthCNN(ak, sk));
        } else if ("GRNN".equals(mode)) {
            map.put("result", getAuthGRNN(ak, sk));
        } else {
            map.put("result", getAuthBOW(ak, sk));
        }
        System.out.println(map.get("result"));
        return map;
    }

    public double getAuthBOW(String ak, String sk) throws JSONException {
        String result = getAuth(ak, sk, "BOW");
        if (result == null) {
            return 0.0;
        }
        JSONObject jsonObject = new JSONObject(result);
        if (jsonObject == null || !jsonObject.has("score")) {
            return 0.0;
        }
        return jsonObject.getDouble("score");
    }

    public double getAuthCNN(String ak, String sk) throws JSONException {
        String result = getAuth(ak, sk, "CNN");
        if (result == null) {
            return 0.0;
        }
        JSONObject jsonObject = new JSONObject(result);
        if (jsonObject == null || !jsonObject.has("score")) {
            return 0.0;
        }
        return jsonObject.getDouble("score");
    }

    public double getAuthGRNN(String ak, String sk) throws JSONException {
        String result = getAuth(ak, sk, "GRNN");
        if (result == null) {
            return 0.0;
        }
        JSONObject jsonObject = new JSONObject(result);
        if (jsonObject == null || !jsonObject.has("score")) {
            return 0.0;
        }
        return jsonObject.getDouble("score");
    }

    public static String getAuth(String ak, String sk, String tag) {
        String getAccessTokenUrl = "https://aip.baidubce.com/rpc/2.0/nlp/v2/simnet?charset=UTF-8&access_token=24.72f60b02e90c76cfdc927cbc0ae39d6f.2592000.1621004308.282335-22992314";
        try {
            StringBuilder postData = new StringBuilder("{" +
                    "    \"text_1\":\"" + ak + "\"," +
                    "    \"text_2\":\"" + sk + "\",\"model\": \"" + tag + "\"" +
                    "}");
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");
            URL realUrl = new URL(getAccessTokenUrl);
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(
                    connection.getOutputStream());
            out.write(postData.toString());
            out.flush();
            out.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            connection.disconnect();
            return result;
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }
}
