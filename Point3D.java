package linktest;
import java.awt.*;
import java.awt.geom.Point2D;

public class Point3D{//用来存储被无连接的边
    private int x,y,z;
    public Point3D() {
    }
    public Point3D(int x,int y,int z) {
        this.x=x;
        this.y=y;
        this.z = z;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getZ() {
        return z;
    }
    public void setZ(int z) {
        this.z = z;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
