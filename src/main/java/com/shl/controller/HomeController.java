package com.shl.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.shl.util.DataProcesser;

@Controller
public class HomeController {
    DataProcesser dp = DataProcesser.getInstance();

    @Autowired
    RestTemplate restTemplate;

    String cookie;

    boolean control=true;
    //String endPoint="http://localhost:8083";

    @RequestMapping("/setMail")
    public @ResponseBody String sendMail(@RequestParam("to") String to) {
        DataProcesser dataProcesser = DataProcesser.getInstance();
        dataProcesser.setData(DataProcesser._MAIL_TO, to);
        return HttpStatus.OK.toString();
    }

    @RequestMapping("/Util")
    public ModelAndView Util() {
        ModelAndView mav = new ModelAndView("Util");
        return mav;
    }
    @RequestMapping("/admin")
    public ModelAndView new_admin() {
        ModelAndView mav = new ModelAndView("newAdmin");
        mav.addObject("user", readFromDataFile(DataProcesser.USER));
        mav.addObject("subs", readFromDataFile(DataProcesser.SUBS));

        return mav;
    }


    /**
     * App config.
     * @return the response
     */
    @RequestMapping("/application/version")
    public @ResponseBody Map appConfig() {
        Map<String, Object> versionInfo = new HashMap<String, Object>();
        versionInfo.put("isNewFeature", false);
        versionInfo.put("accreditionFlag", "false");
        versionInfo.put("build-date", "14-01-2017");
        versionInfo.put("TimeStamp", "timeStamp");
        versionInfo.put("ocisId", "2038748630");
        return versionInfo;
    }

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name,
            Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @RequestMapping("/submit")
    String result(@RequestParam("key") String key, @RequestParam("input") String value) {
        System.out.println("I got " + key + " = " + value);
        dp.setData(key, value);
        return "result";
    }

    @RequestMapping("/service")
    String service(@RequestParam("key") String key, @RequestParam("input") String value) {
        System.out.println("I got " + key + " = " + value);
        dp.setData(key, value);
        return "result";
    }

    
    @RequestMapping("/encodeDecode")
    public @ResponseBody String executeSell(@RequestBody String command) {
        System.out.println(command);
        String data=command.substring(7);
        System.out.println(data);
        if(command.startsWith("encode")){
            return new String(Base64.encodeBase64(data.getBytes()));
        }else{
            return new String(Base64.decodeBase64(data.getBytes()));
        }
        
    }

    private String polpulateToken(String fileName) {
        InputStreamReader reader = null;
        reader = new InputStreamReader(this.getClass().getResourceAsStream(fileName));
        if (null != reader) {
            BufferedReader br1 = new BufferedReader(reader);
            String sCurrentLine;
            StringBuffer token = new StringBuffer();
            try {
                while ((sCurrentLine = br1.readLine()) != null) {
                    token.append(sCurrentLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return token.toString();
        }
        return null;
    }

    @RequestMapping("/route")
    public @ResponseBody String setRoute() {

        return "";
    }

    private String readFromDataFile(String key) {
        String value= dp.getData(key);
        System.out.println(key + ":" + value);
        return value;
    }


    public <T, U> HttpEntity<String> getResponse(String url, HttpMethod method, HttpEntity<U> payLoadEntity) throws Exception {
        long startTime = System.currentTimeMillis();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String request = objectMapper.writeValueAsString(payLoadEntity.getBody());
            System.out.println("URL:" + url);
            System.out.println("Request Body: " + request);
            HttpEntity<String> response = restTemplate.exchange(url, method, payLoadEntity, String.class);
            return response;
        } catch (IOException ioe) {
            throw ioe;
        } catch(HttpClientErrorException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }
}
