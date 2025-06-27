package com.forsale.mini.controller;

import com.forsale.mini.dao.School;
import com.forsale.mini.dao.Msg;
import com.forsale.mini.dao.R;
import com.forsale.mini.service.MiniSchoolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class MiniSchoolController {

    private final static Logger log = LoggerFactory.getLogger(MiniSchoolController.class);

    @Autowired
    private MiniSchoolService m_Service;

    @RequestMapping("/testapiSchools")
    public String test() {
        return "123";
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/getAllSchools")
    @ResponseBody
    public R getAllSchools(@RequestParam("keyword") String keyword) {
        log.error("我是 getAllSchools =" + keyword);
        if (keyword != null) {
            Integer uid = -1;
            if (keyword.indexOf("，") > 0) {
                keyword = keyword.substring(1);
                String[] strs = keyword.split("，");
                uid = Integer.parseInt(strs[0]);
                if (strs.length > 1) {
                    keyword = strs[1];
                } else {
                    keyword = "";
                }
                log.error("我是 getAllSchools =" + keyword + "  uid=" + uid + " key=" + keyword);

            } else {
                log.error("我是 getAllSchools =1");
            }
            List<School> arr = m_Service.getAllByKey(keyword);
            if (keyword.equals("")) {
                arr = m_Service.getAll();
            }
            log.error("我是 getAllSchools " + keyword + " uid=" + uid + " si-" + arr.size());
            List<EnjoyPrice> enjoyPrices=new ArrayList<>();
            if (uid != -1) {
                for (int i = 0; i < arr.size(); i++) {
                    School School = arr.get(i);
                    List<School> save = m_Service.getAllByUId(uid);
                    log.error("我是 getAllSchools save" + save.size());
                    for (School item : save) {
                        if (item.getId() == School.getId()) {
                            School.setSave(true);
                            float price=getPrice(School.getContent());
                            enjoyPrices.add(new EnjoyPrice(price-300,price+500));
                            break;
                        }

                    }
                }
            }
            ArrayList<School> newArr=new ArrayList<>();
            ArrayList<School> newArr2=new ArrayList<>();
            for (int i = 0; i < arr.size(); i++) {
                float price=getPrice(arr.get(i).getContent());
                if(isEnjoy(price,enjoyPrices)){
                    newArr.add(arr.get(i));
                }else {
                    newArr2.add(arr.get(i));
                }
            }
            newArr.addAll(newArr2);
            return R.ok().data("data", newArr);
        }
        return R.error().data("fail", "");

    }

    private boolean isEnjoy(float price, List<EnjoyPrice> enjoyPrices) {
        for (int i = 0; i <enjoyPrices.size() ; i++) {
            if(price>enjoyPrices.get(i).min&&price<enjoyPrices.get(i).max){
                return true;
            }
        }
        return false;
    }

    private float getPrice(String priceStr){
        try {
            if(priceStr==null||priceStr.isEmpty()){
                return 0;
            }
            float price = Float.parseFloat(priceStr);
            return price;
        }catch (Exception e){

        }
        return 0;
    }
    class EnjoyPrice{
       public float max=0;
        public float min=0;

        public EnjoyPrice(float max, float min) {
            this.max = max;
            this.min = min;
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/getSchoolById")
    @ResponseBody
    public R getSchoolById(@RequestParam("fid") String fid) {
        School School = m_Service.getById(fid);
        return R.ok().data("data", School);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/getAllSchoolSave")
    @ResponseBody
    public R getAllSchoolSave(@RequestParam("uid") Integer uid) {
        List<School> arr = m_Service.getAllByUId(uid);
        for (School item : arr) {
            item.setSave(true);

        }
        return R.ok().data("data", arr);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/addSave")
    @ResponseBody
    public R addSave(@RequestParam("keyword") String keyword) {
        log.error("我是 addSave 添加" + keyword);
        if (keyword != null && keyword.indexOf("null") < 0) {
            keyword = keyword.substring(1);
            Integer uid = -1;
            Integer fid = -1;
            boolean isSave = false;
            if (keyword.indexOf("，") > 0) {
                String[] strs = keyword.split("，");
                uid = Integer.parseInt(strs[0]);
                fid = Integer.parseInt(strs[1]);
                isSave = strs[2].equals("true");
            } else {

                log.error("我是 addSave 添加 err=" + keyword);
            }
            log.error("我是 addSave 添加 s=" + uid + " yid" + fid);
            int i = 0;
            if (isSave) {
                i = m_Service.add(uid, fid);
            } else {
                i = m_Service.deleteSave(uid, fid);
            }

            return R.ok().data("success", i > 0);
        }
        return R.error().data("fail", "");
    }


    @CrossOrigin(origins = "*")
    @RequestMapping("/deleteSchool")
    @ResponseBody
    public R deleteSchoolById(@RequestParam("id") Integer id) {
        int i = m_Service.deleteById(id);
        return R.ok().data("success", i > 0);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/addSchool")
    @ResponseBody
    public R addSchool(@RequestBody School School, @RequestParam("uid") Integer uid) {
        log.error("我是 addSchool 添加" + School);
        int i = 0;
        if (School.getId() == null) {
            i = m_Service.addSchool(School);
        } else {
            i = m_Service.updateById(School);
        }
        return R.ok().data("success", i > 0);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/addMsg")
    @ResponseBody
    public R addMsg(@RequestBody Msg msg) {
        log.error("我是 addMsg 添加" + msg);
        int i = m_Service.addMsg(msg);
        return R.ok().data("success", i > 0);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/getAllMsgByFid")
    @ResponseBody
    public R getAllMsgByFid(@RequestBody Msg msg) {
        log.error("我是 getAllMsgByFid =" + msg);
        if (msg != null) {
            List<Msg> arr = m_Service.getAllMsgByFid(msg.getSid(), msg.getRid(), msg.getFid());

            Map<String, Object> map = new HashMap<>();
            map.put("count", arr.size());
            map.put("data", arr);
            log.error("我是 getAllMsgByFid =" + arr.size());
            return R.ok().data(map);
        }
        return R.error().data("fail", "");

    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/getAllMsg")
    @ResponseBody
    public R getAllMsg(@RequestParam("uid") Integer uid) {
        log.error("我是 getAllMsg =" + uid);
        if (uid != null) {
            List<Msg> arr = m_Service.getAllMsg(uid);

            List<Msg> arr2 = new ArrayList<>();
            for (Msg item :
                    arr) {
                boolean isContain = false;
                for (Msg item2 :
                        arr2) {
                    if (item2.getRid() ==item.getRid()&&item2.getSid()==item.getSid()&&item2.getFid()==item.getFid()||
                            item2.getRid() ==item.getSid()&&item2.getSid()==item.getRid()&&item2.getFid()==item.getFid() ) {
                        isContain = true;
                        break;
                    }
                }
                if (!isContain) {
                    arr2.add(item);
                }
            }
            Map<String, Object> map = new HashMap<>();
            map.put("count", arr2.size());
            map.put("data", arr2);
            return R.ok().data(map);
        }
        return R.error().data("fail", "");

    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/deleteMsgById")
    @ResponseBody
    public R deleteMsgById(@RequestBody Msg msg) {
        int i = m_Service.deleteMsgById(msg);
        return R.ok().data("success", i > 0);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/getAbout")
    @ResponseBody
    public R getAbout() {
        String i = m_Service.getAbout();
        return R.ok().data("success", i);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/getNews")
    @ResponseBody
    public R getNews() {
        String i = m_Service.getNews();
        log.error("我是 getNews =" + i);
        return R.ok().data("success", i);
    }
}
