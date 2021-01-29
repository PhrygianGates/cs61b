public class Planet {
	private static final double G=6.67e-11;
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	public Planet(double xP,double yP,double xV,double yV,double m,String img) {
		xxPos=xP;
		yyPos=yP;
		xxVel=xV;
		yyVel=yV;
		mass=m;
		imgFileName=img;
	}
	public Planet(Planet p) {
		xxPos=p.xxPos;
		yyPos=p.yyPos;
		xxVel=p.xxVel;
		yyVel=p.yyVel;
		mass=p.mass;
		imgFileName=p.imgFileName;
	}
	public double calcDistance(Planet p) {
		double dx=p.xxPos-xxPos;
		double dy=p.yyPos-yyPos;
		return Math.sqrt(dx*dx+dy*dy);
	}
	public double calcForceExertedBy(Planet p) {
		double r=calcDistance(p);
		return G*mass*p.mass/(r*r);
	}
	public double calcForceExertedByX(Planet p) {
		double dx=p.xxPos-xxPos;
		double r=calcDistance(p);
		return G*mass*p.mass*dx/(r*r*r);
	}
	public double calcForceExertedByY(Planet p) {
		double dy=p.yyPos-yyPos;
		double r=calcDistance(p);
		return G*mass*p.mass*dy/(r*r*r);
	}
	public double calcNetForceExertedByX(Planet[] planets) {
		double sumFx=0;
		for(Planet p:planets){
			if(!equals(p)) sumFx+=calcForceExertedByX(p);
		}
		return sumFx;
	}
	public double calcNetForceExertedByY(Planet[] planets) {
		double sumFy=0;
		for(Planet p:planets){
			if(!equals(p)) sumFy+=calcForceExertedByY(p);
		}
		return sumFy;
	}
	private boolean equals(Planet p) {
		return this==p;
	}
	public void update(double dt,double fX,double fY) {
		double aX=fX/mass,aY=fY/mass;
		xxVel+=dt*aX;
		yyVel+=dt*aY;
		xxPos+=dt*xxVel;
		yyPos+=dt*yyVel;
	}
	public void draw() {
		StdDraw.picture(xxPos,yyPos,"images/"+imgFileName);
	}
}		

			
