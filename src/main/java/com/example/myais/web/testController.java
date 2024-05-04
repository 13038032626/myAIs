package com.example.myais.web;

import com.example.myais.integration.AIModules;
import com.example.myais.integration.PythonEngine;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

@RestController
@CrossOrigin
public class testController {

    public static final BlockingQueue<String> currentRequestByKimi = new LinkedBlockingQueue<>();
    public static long lastTimeKimi;
    public static final BlockingQueue<String> currentRequestByTongyi = new LinkedBlockingQueue<>();
    public static final BlockingQueue<String> currentRequestByGPT = new LinkedBlockingQueue<>();
    public static final ConcurrentHashMap<String, String> result = new ConcurrentHashMap<>();

    @GetMapping("/queryFromKimi")
    public String queryFromKimi(@RequestParam String content) throws InterruptedException {

        currentRequestByKimi.put(content);
        result.put(content, "_");
        long time;
        synchronized (currentRequestByKimi) {
            if (lastTimeKimi - (lastTimeKimi = System.currentTimeMillis()) < -1000) {
                Object[] execRequests = currentRequestByKimi.toArray();
                if (execRequests.length != 0) {
                    PythonEngine.execPythonScript(execRequests, AIModules.KIMI);
                    for (int i = 0; i < execRequests.length; i++) {
                        currentRequestByKimi.poll();
                    }
                }
            }
        }
        while (result.get(content).equals("_")) {

        }
        return result.get(content);
    }

    @GetMapping("/queryFromTongyi")
    public String queryFromTongyi(@RequestParam String content) throws InterruptedException {
        currentRequestByTongyi.put(content);
        result.put(content, "_");
        while (result.get(content).equals("_")) {

        }
        String ans = result.get(content);
        System.out.println("tongyi-ans" + ans);
        return ans;
    }

    @GetMapping("/queryFromGPT")
    public String queryFromGPT(@RequestParam String content) throws InterruptedException {
        currentRequestByGPT.put(content);
        result.put(content, "_");
        while (result.get(content).equals("_")) {

        }
        String ans = result.get(content);
        System.out.println("GPT-ans" + ans);
        return ans;
    }
//    @GetMapping("/query")
//    public String[] query(@RequestParam String[] content,@RequestParam boolean[] required){
//        String[] ans = new String[3];
//        String kimi = null;
//        String tongyi = null;
//        String GPT = null;
//
//        if(required[0]){
//            kimi = PythonEngine.execPythonScript(content, AIModules.KIMI);
//        }if(required[1]){
//            tongyi = PythonEngine.execPythonScript(content, AIModules.TONGYI);
//        }if(required[2]){
//            GPT = PythonEngine.execPythonScript(content, AIModules.GPT);
//        }
//        ans[0] = kimi;
//        ans[1] = tongyi;
//        ans[2] = GPT;
//        return ans;
//    }
}
