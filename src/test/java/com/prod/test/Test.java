package com.prod.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Test {
    public static void main(String[] args) {
        try {
            // 使用 docker start 命令，替换 "container_id_or_name" 为你要启动的容器
            ProcessBuilder processBuilder = new ProcessBuilder("docker", "start", "TIA103G3_Mysql");

            // 合并错误流和输入流，方便一起读取
            processBuilder.redirectErrorStream(true);

            // 启动进程
            Process process = processBuilder.start();

            // 读取命令输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);  // 输出命令执行结果
            }

            // 等待命令执行完成
            int exitCode = process.waitFor();
            System.out.println("Exit Code: " + exitCode);  // 输出退出码

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
