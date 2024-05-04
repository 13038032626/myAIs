package com.example.myais.integration;

import com.example.myais.web.testController;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;

public class PythonEngine {
    static int kimiNum = 0;

    public static volatile Runtime runtime;

    public static Runtime getRuntime() {
        // 第一次检查，如果 proc 不为空，则直接返回，避免不必要的同步
        if (runtime == null) {
            // 同步块，保证只有一个线程进入临界区
            synchronized (PythonEngine.class) {
                // 第二次检查，确保在同步块内再次检查 proc 的状态
                if (runtime == null) {
                    // 创建 Process 对象的实例
                    return Runtime.getRuntime();
                }
            }
        }
        return runtime;
    }

    public static void execPythonScript(Object[] content, AIModules modules) {
        try {
//            System.out.println("content的长度： "+content.length);
            String[] args1 = new String[4 + content.length];
            args1[0] = "C:\\Users\\吴松林\\PycharmProjects\\pythonProject\\.venv\\Scripts\\python.exe";
            args1[1] = modules.path;
            args1[2] = String.valueOf(kimiNum);
//            System.out.println("kimiNum = " + kimiNum);
            kimiNum += content.length;
            args1[3] = String.valueOf(content.length);
            System.arraycopy(content, 0, args1, 4, content.length);
            Runtime runtime = getRuntime();
            Process proc = runtime.exec(args1);
            InputStream is = proc.getInputStream();
            byte[] bytes = is.readAllBytes();
            String ansOrigin = new String(bytes, Charset.forName("gbk"));
            ansOrigin = ansOrigin.trim();
            if (ansOrigin.startsWith("[")) {
                ansOrigin = ansOrigin.substring(1);
            }
            if (ansOrigin.endsWith("]")) {
                ansOrigin = ansOrigin.substring(0, ansOrigin.length() - 1);
            }
            String[] ans = ansOrigin.split(",");
//            System.out.println("ans = " + Arrays.toString(ans));
            proc.waitFor();
            for (int i = 0; i < content.length; i++) {
                testController.result.put((String) content[i],ans[i]);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
