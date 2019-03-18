package linktest;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

//当数据量小的时候使用邻接矩阵表示更直观, 如果数据量大,使用链表则会节省空间
public class MatrixAdjacency {
    private int[] vertex;//存储点的数组, vertex顶点
    private int[][] edges;//邻接矩阵，用来存储边, edge边
    private int numOfEdges;//边的数目
    private int maxNumOfEdges;//最多边的数目
    private ArrayList<Point> saveDeleteEdges_ArrayList=new ArrayList<>();  //使用集合储存被删的边
    private ArrayList<Point3D> NotConnectedEdges_ArrayList=new ArrayList<>();//保存没有连接的边

    //构造器
    public MatrixAdjacency(int n){
        vertex=new int[n];
        edges=new int[n][n];
        numOfEdges=0;
        maxNumOfEdges=n*(n-1)/2;
    }
    public MatrixAdjacency(int n1[],int n2[][]){//提供一个矩阵,顶点和矩阵
        vertex=new int[n1.length];
        edges=new int[n1.length][n1.length];
        numOfEdges=0;
        maxNumOfEdges=n1.length*(n1.length-1)/2;
        //可以使用arraycopy方法,当然,也要循环找出边,所以不用arraycopy
        for (int i = 0; i <edges.length ; i++) {//将传进来的矩阵打印到定义的矩阵edges
            for (int j = 0; j <edges[i].length ; j++) {
                edges[i][j]=n2[i][j];
                if(edges[i][j]==1){//计算边数
                    numOfEdges++;
                }
                if (edges[i][j]==0&& i!=j){//保存没有连接的边
                    NotConnectedEdges_ArrayList.add(new Point3D(i,j,0));
                }
            }
        }
        numOfEdges=numOfEdges/2;//无向图,所以边除以2
    }
    //访问器

    public int[] getVertex() {
        return vertex;
    }
    public int getNumOfVertex(){//得到顶点数
        return vertex.length;
    }
    public int getNumOfEdges(){//得到边的数
        return numOfEdges;
    }
    public int[][] getEdges() {//得到一个表示邻接矩阵的二维数组
        return edges;
    }
    public int getMaxNumOfEdges() {
        return maxNumOfEdges;
    }
    public ArrayList<Point> getSaveDeleteEdges_ArrayList() {
        return saveDeleteEdges_ArrayList;
    }

    public ArrayList<Point3D> getNotConnectedEdges_ArrayList() {//得到没有连接的边
        if (NotConnectedEdges_ArrayList.size()>0){//事先清空
            NotConnectedEdges_ArrayList.clear();
        }
        for (int i = 1,len=edges.length; i <len ; i++) {
            for (int j = 0; j <i; j++) {
                if (edges[i][j]==0 && (i!=j)){
                    NotConnectedEdges_ArrayList.add(new Point3D(j,i,0));
                }
            }
        }
        return NotConnectedEdges_ArrayList;
    }

    /*相关的方法*/
    //删除一条边
    public void deleteEdges(int row,int column){
        row--;
        column--;
        if (numOfEdges==0){
            System.out.println("当前没有任何边,请初始化邻接矩阵");
        }
        if(edges[row][column]==0 ||row>vertex.length||column>vertex.length) {
            System.out.println("不存在这一条边,或者已经被删除");return;
        }else{
            edges[row][column]=0;//把某条边删掉
            edges[column][row]=0;//把某条边删掉,需要重复删除对称的值
            numOfEdges=numOfEdges-1;
            System.out.println("已删除!!!");
            saveDeleteEdges_ArrayList.add(new Point(row,column));//记录被删除的边((row+1)*10+(column+1))
        }
    }
    //打印邻接矩阵
    public void printMatrixAdjacency(){
        for (int i = 0; i <edges.length ; i++) {
            for (int j = 0; j <edges[i].length ; j++) {
                System.out.print("    "+edges[i][j]);
            }
            System.out.println();
        }
    }
    //随机生成连接的邻接矩阵的方法
    public void randonMatrixAdjacency(int aedges){//边数
        if(aedges>vertex.length*(vertex.length-1)/2){
            System.out.println("输入的边数大于最大边");
            return;
        }
        numOfEdges=0;
        int rand;
        while (numOfEdges<aedges){//当边数为想要的边数时结束循环
            for (int i = 0; i <edges.length ; i++) {//遍历矩阵,
                for (int j = 0; j <edges.length ; j++) {
                    if(numOfEdges==aedges)break;
                    //rand=Math.random()>0.5?1:0;//生成随机0和1,概论为50%
                    //rand=Math.random()>0.8?1:0;//生成随机0到1,概论为20%
                    rand=Math.random()>0.9?1:0;//生成随机0到1,概论为10%
                    if(rand==1&&edges[i][j]==0&&(i!=j)&&numOfEdges<aedges){
                        edges[i][j]=rand;
                        edges[j][i]=rand;
                        numOfEdges++;
                    }
                }
            }
        }
    }
}
