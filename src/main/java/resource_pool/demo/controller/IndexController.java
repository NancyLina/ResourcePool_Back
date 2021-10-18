package resource_pool.demo.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import resource_pool.demo.domain.Setting;

//import java.util.HashMap;
//import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
//import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/thymeleaf/")
public class IndexController {

    Setting setting=new Setting();

    @GetMapping("index")
    public String index(ModelMap modelmap)
    {

        String set="12345";
        modelmap.addAttribute("setting",set);
        modelmap.addAttribute("settings",setting);
        return "index";
    }


    @PostMapping("forms")
    public String forms(@RequestBody JSONObject jsonObject){
        System.out.println(jsonObject);
//        modelmap.addAttribute("user",jsonObject.get("user").toString());
        return "form";
    }

    @PostMapping("sqls")
    public String sqls(@RequestBody JSONObject jsonObject)
    {

        return "sql";
    }

//    @PostMapping("database")
//    public String databases(@RequestBody Object user,ModelMap modelmap){
////        HashMap<Object,Object> map=new HashMap<>();
////        map.put();
//        modelmap.addAttribute("username","123");
//        System.out.println("asldjfladjldaja");
//        System.out.println(user);
////        JSONObject object = new JSONObject();
////        object.put("123","456");
//        return "adsf";
//    }
}
