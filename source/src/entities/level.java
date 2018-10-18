package entities;

import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.SlickException;

import screens.game;
import yengine.yengine;
import yengine.yentity;

public class level extends yentity {

	public float tile_width=47,tile_height=54;
	public int[][] map;
	public static int player_x,player_y;
	public level(float x, float y) throws SlickException {
		super(x, y, 0, "");
		type = "level";
		
	}//end constructor
	
	@Override
	public void update() throws SlickException {
		// TODO Auto-generated method stub
		super.update();
		is_compleate();
	}

	public void genrate_map(int[][] map2,boolean start_level) throws SlickException 
	{
		remove_old_tiles();	//remove old tles
		//no need to deep copy map each time only at start
		if(start_level) {map = maps.deepCopyIntMatrix(map2);}else {map=map2;}
		int map_height,map_width;
		float colx,coly;//tilmap cordenates holder
		int ts;//tile symbole
		
		map_height = map2.length;
		map_width = map2[0].length;
		
	
		
		for(int i = 0; i < map_height; i++) //loop rows
		{
		    for(int j = 0; j < map_width; j++) //loop cols
		    {
		    	ts = map2[i][j];//the tile symbol
		        colx = j*tile_width+game.margin_left;//x pos for tle is tile col number * tile width
		        coly = i*tile_height+game.margin_up;//same as with col x
		     
		        if(ts ==6) //if its 6 its player elst its just a tile
		        {
		        	player p;
		        	if(start_level)//had to create two becuse of flickering effect when grafics are created
		        	{
		        		 p = new player(j,i);
		        	}else
		        	{
		        		 p = new player(level.player_x,level.player_y);
		        		
		        		 
		        	}
		        	world.add(p);
		    		create_tile(colx,coly,0);//create floor tile
		    		
		    		
		    		
		        }
		        else 
		        {
		        	create_tile(colx,coly,ts);
		        }
		    }//loop cols
		}//loop rows
		
	
	
		
	}//end genrate_map
	
	public void remove_old_tiles() 
	{
		ArrayList<yentity> mcs = get_by_type("tile");
		for(yentity e : mcs)
		{
			world.remove(e);
		}
		
		ArrayList<yentity> mcs2 = get_by_type("player");
		
		if(!mcs2.isEmpty()) //chack if player exists
		{
			//save player pos before you remove it
			player p = (player)mcs2.get(0);
			level.player_x = p.tilex;
			level.player_y = p.tiley;
		}
		
		for(yentity e2 : mcs2)
		{
			world.remove(e2);
		}
	}//end remove_old_tiles
	
	public void create_tile(float x,float y, int ts) throws SlickException 
	{
	     tile t = new tile(x,y);//create tile and pass pos
	     t.type_tile = Integer.toString(ts);//set type
	        
	      t.set_tile_img(ts);//change tile img
	      world.add(t);//add tile to world
	}//end create_tile
	
	public void reset() throws SlickException 
	{
		
		//genrate_map(game.current_map_start,true);
		//System.out.println(game.current_map_start[2][3]);
		world.yinit(gc, sbg);//reset game world...
	}//end reset
	
	public void is_compleate() throws SlickException 
	{
		boolean end = true;
		ArrayList<yentity> mcs = get_by_type("tile");
		for(yentity e : mcs)
		{
			tile ytile = (tile)e;
			if(ytile.type_tile.equals("2")) {end=false; }//if thers one empthy box its not over
			
			
		}
	
		if(end)//no boxes means they are all on gems 
		{
			yengine.o("next level");
			game.stage++;
			world.yinit(gc, sbg);
		}
	}//end is_compleate
	
	
}//end level
