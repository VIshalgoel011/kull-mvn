package com.kull.tankz.tank;

import java.awt.Color;
import java.awt.Graphics;

import com.kull.tankz.EDirections;
import com.kull.tankz.net.Client;



public class NetTank extends Tank{

	Color c;
	public NetTank(int x, int y, Client client, boolean stand) {
		super(x, y, client, stand);
		this.tankcolor=Color.PINK;
		this.ptcolor=Color.RED;
		// TODO Auto-generated constructor stub
	}


	public void setDirection(EDirections dir) {
		// TODO Auto-generated method stub
		if(dir!=EDirections.STOP)
		{
			this.dir=this.ptdir=dir;
		}
		else
		{
			this.dir=dir;
		}
	}
	
	public void Draw(Graphics g)
	{
		if(this.alive==false)
			return;
		g.setColor(Color.PINK);
		g.drawString(""+this.tankId, x+4,y-8);
		draw(g);
		Move();
	}


	private void Move() {
	
	    move();
	    this.setDirection(dir);
	}
    

}
