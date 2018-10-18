package entities;

import org.newdawn.slick.SlickException;

import screens.game;
import yengine.yentity;

public class tile extends yentity {
	
	public int ts;
	public float height2,width2;
	public String type_tile;
	
	public tile(float x, float y) throws SlickException {
		super(x, y, 0, "level.png");
		type = "tile";
	
	}//end constructor

	@Override
	public void init() throws SlickException {
		
		height2 = game.tile_height;
		width2 = game.tile_width;
		set_w_h(width2,height2);
		set_img_type("sprite");
		super.init();
	}

	public void set_tile_img(int ts) 
	{
		if(ts ==0) {set_sxy(3,0);}//floor
		if(ts ==1) {set_sxy(4,0);}//wall
		if(ts ==2) {set_sxy(2,0);}//box
		
		if(ts ==4) {set_sxy(1,0);}// jewel
		if(ts ==5) {set_sxy(0,0);}//box n jewel
	
		
	}//end set_tile_img

}//end tile
