public class TestPlanet {
    public static void main(String[] args){
        Planet a=new Planet(1e12,2e11,0,0,2e30,"");
        Planet b=new Planet(2.3e12,9.5e11,0,0,6e26,"");
        System.out.println(a.calcForceExertedBy(b));
    }
}