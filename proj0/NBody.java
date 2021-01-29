public class NBody {
    public static double readRadius(String s) {
        In in=new In(s);
        in.readInt();
        return in.readDouble();
    }
    public static Planet[] readPlanets(String s) {
        In in=new In(s);
        int n=in.readInt();
        in.readDouble();
        Planet[] planets=new Planet[n];
        for(int i=0;i<n;i++){
            planets[i]=new Planet(in.readDouble(),in.readDouble(),in.readDouble(),in.readDouble(),in.readDouble(),in.readString());
        }
        return planets;
    }
    public static void main(String[] args) {
        double T=Double.parseDouble(args[0]),dt=Double.parseDouble(args[1]),t=0;
        String filename=args[2];
        double radius=readRadius(filename);
        Planet[] planets=readPlanets(filename);
        String background="images/starfield.jpg";
        double[] xForces=new double[planets.length];
        double[] yForces=new double[planets.length];
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-radius,radius);
        while(t<=T){
            for(int i=0;i<planets.length;i++) {
                xForces[i]=planets[i].calcNetForceExertedByX(planets);
                yForces[i]=planets[i].calcNetForceExertedByY(planets);
            }
            for(int i=0;i<planets.length;i++) {
                planets[i].update(dt,xForces[i],yForces[i]);
            }
            StdDraw.clear();
            StdDraw.picture(0,0,background);
            for(Planet p:planets) p.draw();
            StdDraw.show();
            StdDraw.pause(10);
            t+=dt;
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                        planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                        planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
        }
    }
}