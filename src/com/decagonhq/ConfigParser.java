package com.decagonhq;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


public class ConfigParser {

    private String fileName;
    private Map<String, String> check;


    public ConfigParser(String fileName) {
        this.check= new HashMap<String, String>();
        if(fileName.equalsIgnoreCase("production")){
            this.fileName = "C:\\Users\\njust\\Desktop\\task\\src\\com\\decagonhq\\config.txt";
        }else if(fileName.equalsIgnoreCase("development")){
            this.fileName = "C:\\Users\\njust\\Desktop\\task\\src\\com\\decagonhq\\config.txt.dev";
        }else if(fileName.equalsIgnoreCase("staging")) {
            this.fileName = "C:\\Users\\njust\\Desktop\\task\\src\\com\\decagonhq\\config.txt.staging";
        }

    }

    public String getFileName() {
        return fileName;
    }

    private Map<String, String> getCheck() {
        return check;
    }

    public String get(String key){
        return getCheck().get(key);
    }

    public void ReadWithInputStream() {
        String line;
        FileReader fin = null;
        BufferedReader br = null;
        InputStreamReader isr = null;
        try {
            fin = new FileReader(this.fileName);
            br = new BufferedReader(fin);
            String prefix = "";
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    if (line.startsWith("[") && line.endsWith("]")) {
                        prefix = line.substring(1, line.length() - 1);
                        continue;
                    }
                    String[] keyValue = line.split("=");
                    if(prefix.isEmpty()) {
                        String key = keyValue[0];
                        String value = keyValue[1];
                        if(!this.check.containsKey(key)){
                            this.check.put(key, value);
                        }
                    }else {
                        String key = prefix + "."+ keyValue[0];
                        String value = keyValue[1];
                        if(!this.check.containsKey(key)){
                            this.check.put(key, value);
                        }
                    }
                }else{
                    prefix ="";
                }
            }
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        } finally {
            try {
                if (fin != null)
                    fin.close();
            } catch (Exception e) {
                System.err.println("Exception: " + e.getMessage());
            }
        }
        System.out.println(this.check);
    }

    public static void main(String[] args) {
        String name;
        if(args.length == 0){
            name = "production";
        }else{
            name = args[0];
        }
        ConfigParser config = new ConfigParser(name);
        config.ReadWithInputStream();
        System.out.println(config.get("application.name"));
    }
}

