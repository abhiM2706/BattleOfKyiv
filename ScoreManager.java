package com.stem.game;

import java.io.*;
import java.util.*;

public class ScoreManager {//file name path
	
	private static String scoreFileName = BattleOfKyiv.imagePath + "scores.csv";
   
    public void addScore(String name, int score){ //adds name and score to the scores.csv
        try {
            File yourFile = new File(scoreFileName);
            if (!yourFile.exists()) {
                yourFile.createNewFile();
            }
            StringBuilder sb = new StringBuilder();
            sb.append(name + "," + score + '\n');
            List<String> existingNames = getNames();
            FileWriter pw = new FileWriter(scoreFileName,true);
            pw.append(sb.toString());
            pw.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public List<String> getNames() { //gets all names from csv which holds scores
        List<String> names = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(scoreFileName));
            String line = null;
            while ((line = reader.readLine()) != null) {
                int commaIndex = line.indexOf(',');
                String name = line.substring(0,commaIndex);
                names.add(name);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return names;
    }

    public LinkedHashMap<String, Integer> returnScores(){ //reads all scores from the csv and uses a custom comparator to find top 10 scores in descending order
        LinkedHashMap<String, Integer> lhm = new LinkedHashMap<>();
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer> >(lhm.entrySet());
        try {
            BufferedReader reader = new BufferedReader(new FileReader(scoreFileName));
            String line = null;
            while ((line = reader.readLine()) != null) {
                int commaIndex = line.indexOf(',');
                String name = line.substring(0,commaIndex);
                Integer score = Integer.parseInt(line.substring(commaIndex + 1, line.length()));
                lhm.put(name,score);
            }
            list = new ArrayList<Map.Entry<String, Integer> >(lhm.entrySet());
            Collections.sort(
                    list, new Comparator<Map.Entry<String, Integer> >() {
                        public int compare(
                                Map.Entry<String, Integer> entry1,
                                Map.Entry<String, Integer> entry2)
                        {
                            return entry2.getValue()
                                    - entry1.getValue();
                        }
                    });
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (list.size() > 10) {
            list.subList(10, list.size()).clear();
        }

        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

}

