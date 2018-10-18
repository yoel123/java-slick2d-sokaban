package entities;

import org.newdawn.slick.Game;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import screens.game;
import yengine.yengine;
import yengine.yentity;

public class player extends yentity 
{
    public int tilex,tiley;//the position on tilmap
	public float height2,width2;
	
	public player(int tx, int ty) throws SlickException {
		super(0,0, 0, "player.png");
		tilex = tx;
		tiley = ty;
		z=99;
		no_cam = true;
		type="player";
		init();
	}//end constructor
	
	@Override
	public void init() throws SlickException 
	{
		
		set_tile_pos();
		
		height2 = game.tile_height;
		width2 = game.tile_width;
		set_w_h(width2,height2);
		set_img_type("sprite");
		// TODO Auto-generated method stub
		super.init();
	}//end init
	
	
	
	@Override
	public void update() throws SlickException {
		// TODO Auto-generated method stub
		super.update();
		move();
		move_box();
		
	}

	public void set_tile_pos() 
	{
		
		x = tilex*game.tile_width+game.margin_left;
		y = tiley*game.tile_height+game.margin_up;
		
	}//end set_tile_pos
	
	public void move() throws SlickException 
	{
	   boolean up,down,left,right,w,a,s,d,r;
		
		up = key_released(Input.KEY_UP) ;//key released 
		down = key_released(Input.KEY_DOWN) ;
		left = key_released(Input.KEY_LEFT) ;
		right = key_released(Input.KEY_RIGHT) ;
		w = key_released(Input.KEY_W) ;
		a = key_released(Input.KEY_A);
		s = key_released(Input.KEY_S);
		d = key_released(Input.KEY_D);
		r = key_released(Input.KEY_R);
		float[] cam = world.get_camera();
		//move on tilmap only
		if(up || w)
		{   
			//cam[0] -=30;//test cam
			dir = "up";
			if(is_walkble())//if tile in direction is floor move
				tiley -=1; //move by one up
			
		}
		if(down || s)
		{
			dir = "down";
			if(is_walkble())
				tiley +=1; 
			
		}
		if(left || a)
		{
			
			dir = "left";
			if(is_walkble())
				tilex -=1;
			
		}
		if(right || d)
		{
			dir = "right";
			if(is_walkble())
				tilex +=1; 
			
		}
		
		//reset game
		if(r)
		{
			level l = (level)get_by_type("level").get(0);
			l.reset();
		}
	
		set_tile_pos();
		
		
	}//end move
	
	
	//get this or neighbor tile type
	public int get_tile_type(int tx,int ty) 
	{
		//incase array is out of bounds return an unlickly number
		if((tiley+ty>game.current_map.length || tiley+ty<0) || (tilex+tx<0 || tilex+tx>game.current_map[0].length)) 
		{return 999999;}
		//yengine.o(tiley+ty);
		//get tile type
		int tile = game.current_map[tiley+ty][tilex+tx];
		return tile;
		//return 0;
	}
	
	public void move_box() throws SlickException 
	{
		int tile = get_tile_type(0,0);
		
		
		
		//if right tile is box when moving right,and thers no wall or other box
		if(dir.equals("right") && can_box_move(1, 0) )
		{
			 move_box_do(1,0);
		}
		if(dir.equals("left") && can_box_move(-1, 0))
		{
			 move_box_do(-1,0);
		}
		if(dir.equals("up") && can_box_move(0, -1))
		{
			 move_box_do(0,-1);
		}
		if( dir.equals("down") && can_box_move(0, 1))
		{
			 move_box_do(0,1);
		}
		
	}
	
	public void swap_tiles(int x1,int y1,int x2,int y2) 
	{
		int old_tile = game.current_map[y1][x1];
		game.current_map[y1][x1] =game.current_map[y2][x2];
		game.current_map[y2][x2] = old_tile;
	}//end swap_tiles
	
	public boolean is_tile(String dir2,int tile_type) 
	{
		//if dir up and the tile above equals to tile_type (rest is the same)
		if(dir2.equals("up") && get_tile_type(0,-1)==tile_type) {return true;}
		if(dir2.equals("down") && get_tile_type(0,1)==tile_type) {return true;}
		if(dir2.equals("left") && get_tile_type(-1,0)==tile_type) {return true;}
		if(dir2.equals("right") && get_tile_type(1,0)==tile_type) {return true;}
		if(dir2.equals("this") && get_tile_type(0,0)==tile_type) {return true;}

		return false;
		
	}
	
	public boolean is_tile(String dir2,int tile_type,int mod) 
	{
		//mod = modifier
		
		//if dir up and the tile above equals to tile_type (rest is the same)
		if(dir2.equals("up") && get_tile_type(0,-mod)==tile_type) {return true;}
		if(dir2.equals("down") && get_tile_type(0,mod)==tile_type) {return true;}
		if(dir2.equals("left") && get_tile_type(-mod,0)==tile_type) {return true;}
		if(dir2.equals("right") && get_tile_type(mod,0)==tile_type) {return true;}
		

		return false;
		
	}
	
	public boolean is_walkble() 
	{
		//can walk on floor(0) gem(4) and start(6)
		//if(is_tile(dir,0) ||is_tile(dir,4)||is_tile(dir,6)||is_tile(dir,2) ||is_tile(dir,5) ) {return true;}\
		
		//box near wall cant walk over it
		if((is_tile(dir,2)|| is_tile(dir,5))&& is_tile(dir,1,2)){return false;}
		
		//box near box, cant walk over it
		if((is_tile(dir,2)|| is_tile(dir,5))&& (is_tile(dir,2,2)|| is_tile(dir,5,2))){return false;}
		
		if(!is_tile(dir,1) ) {return true;}
		
		return false;
	}//end is_walkble
	
	public boolean is_wall(int x1, int y1) 
	{
		//if its a wall return true 
		if( game.current_map[tiley+y1][tilex+x1]==1 ) {return true;}
		return false;
	}
	
	public boolean is_box(int x1, int y1) 
	{
		//if its a box return true (or a box on a gem or starting tile...bugs bugs everywere)
		if( (game.current_map[tiley+y1][tilex+x1]==2 || game.current_map[tiley+y1][tilex+x1]==5)
				|| game.current_map[tiley+y1][tilex+x1]==6) {return true;}
		return false;
	}
	public boolean can_box_move(int x1, int y1) 
	{
		if((is_tile("this",2) || is_tile("this",5)) && !is_wall(x1,y1) && !is_box(x1,y1)) {return true;}
		return false;
	}
	public void move_box_do(int x1,int y1) throws SlickException 
	{
	
		
		//if the tile is box on a gem change it to gem else leave empthy floor
		if(game.current_map[tiley][tilex] ==5) {game.current_map[tiley][tilex]=4;}
		else {game.current_map[tiley][tilex]=0;}//if its just box leave an empthy floor
		
		//if next tile is gem change it to box on a gem else change to box
		if(game.current_map[tiley+y1][tilex+x1]==4) {game.current_map[tiley+y1][tilex+x1]=5;}
		else {game.current_map[tiley+y1][tilex+x1]=2;}
		//yengine.o(get_tile_type(0,0));
		game.l.genrate_map(game.current_map,false);//false is for level started argument
		
	}
	
	
	
	
}
