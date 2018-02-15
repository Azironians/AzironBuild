package managment.paramManagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

public class ParamsManager {
    private final String CONFIG_PATH = "";

    public ParamsManager(){
        //read file & parse params...
    }

    public void readConfiguration(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(CONFIG_PATH)));
            List<String> configList = bufferedReader.lines().collect(Collectors.toList());
            parseParams(configList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void parseParams(List<String> configList){}
}