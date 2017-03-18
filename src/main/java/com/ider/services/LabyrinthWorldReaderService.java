package com.ider.services;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import com.ider.Params;
public class LabyrinthWorldReaderService {

	public static final String csvFile= "src/main/resources/labyrinth_world.csv";
	public static int[][] read()
	{
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        int[][] labyrinthWorld = new int[Params.VERTICAL][Params.HORIZENTAL];
        int i = 0, j = 0;

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                String[] cellValues = line.split(cvsSplitBy);
                for(String cellValue : cellValues)
                {
                	labyrinthWorld[i][j] = Integer.parseInt(cellValue);
                	j++;
                }
                j=0;
                i++;


            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
        	e.printStackTrace();
        }finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return  labyrinthWorld;

	}
}
