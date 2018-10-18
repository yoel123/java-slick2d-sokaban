package screens;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import entities.level;
import entities.maps;
import entities.player;
import yengine.y_bar;
import yengine.yengine;
import yengine.yentity;
import yengine.yworld;

public class game extends yworld {


	public static float tile_width=47,tile_height=54,margin_left=200,margin_up=200;
	public static int[][] current_map ;
	public static int[][] current_map_start ;//for reset keep origenal
	public static level l;
	public static int stage=1;
	public static int max_stages=2;
	public game(int state) throws SlickException {
		super(state);
	
	}
	
	@Override
	public void yinit(GameContainer arg0, StateBasedGame arg1) throws SlickException
	{
		mc = new ArrayList<yentity>();//reset everything
		
		
		try {
			set_level();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//add game entytis
		game.l = new level(0,0);
		add(game.l);
		game.l.genrate_map(game.current_map,true);//true means level started so render as if level started
		
		
		    
	}//end yinit

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		

		//win (next level)
		
		super.update(gc, sbg, delta);
	}
	
	public void set_level() throws FileNotFoundException 
	{
		if(game.stage>game.max_stages) 
		{
			game.stage = 1;
		}
		//set_level
		if(game.stage==1) 
		{
			//set_map(maps.one);
			set_map(read_map_file("maps/1.txt"));
		}
		//set_level
		if(game.stage==2) 
		{
			set_map(read_map_file("maps/2.txt"));
		
		}
	
	}//end set_level
	
	public void set_map(int[][] map2) 
	{

	
		game.current_map = maps.deepCopyIntMatrix(map2);	
		//game.current_map_start = Arrays.copyOf(map2, map2.length);
		game.current_map_start = maps.deepCopyIntMatrix(map2);
	}
	
	
	public int[][] read_map_file(String file_name) throws FileNotFoundException 
	{
		int col_size,row_size;
		
		FileReader file_reader = new FileReader(file_name);

        Scanner sc = new Scanner(file_reader);
        row_size = sc.nextInt();
        col_size = sc.nextInt();
        int[][] maze_new = new int[row_size][col_size];
        int row=0, col=0;
        while (sc.hasNext()) 
        {
        	String[] cur = sc.next().split(",");
            for(col = 0;col <= col_size-1;col++)
            {   
            	maze_new[row][col]=Integer.parseInt(cur[col]);
            }//end for cols

            col=0;
            row++;
            
        }//end while
        try {
			file_reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return maze_new;
	}//end read_map_file
	

}
