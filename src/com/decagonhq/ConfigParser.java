package com.decagonhq;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


public class ConfigParser {
    private String fileName = "production";
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
        // FileInputStream fin = null;
        FileReader fin = null;
        BufferedReader br = null;
        InputStreamReader isr = null;
        int b = 0;
        boolean chec = true;



        try {
            fin = new FileReader(this.fileName);
            br = new BufferedReader(fin);
            while ((line = br.readLine()) != null) {
//                line.replace("\n", "");
                if (!line.isEmpty()) {
                    if (line.equals("[application]")){
                        chec = false;
                    }
                    if(chec) {
                        String[] val = line.split("=");
                        String key = val[0];
                        String value = val[1];
                        this.check.put(key, value);
                    }
                    if(!chec && b<3 && !line.equals("[application]")){
                        String[] val = line.split("=");
                        String key = "application." + val[0];
                        String value = val[1];
                        this.check.put(key, value);
                        b++;
                    }
                    if(b == 3){
                        chec =true;
                    }
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

