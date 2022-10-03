class Pixel
{
	int x;
	int y;
	Pixel(int x,int y)
	{
		this.x = x;
		this.y = y;
	}
	Pixel()
	{
		this(0,0);
	}
	Pixel(Pixel px)
	{
		this(px.x,px.y);
	}
	Pixel(double x,double y)
	{
		this((int)x,(int)y);
 	}
 	Pixel(Pixel px1,Pixel px2)
 	{
 		this(px2.x - px1.x, px2.y - px1.y);
 	}
	public String toString()
	{
		return ("["+this.x+";"+this.y+"]");
	}
	public int distance2()
	{
		return (x*x+y*y);
	}
	public boolean plusLoinQue(Pixel px)
	{
		return (this.distance2()>px.distance2());
	}
	public int distance2(Pixel px)
	{
		return (x*px.x+y*px.y);
	}
	public int distanceManhattan(Pixel px)
	{
		return (px.x-x)+(px.y-y);
	}

}
