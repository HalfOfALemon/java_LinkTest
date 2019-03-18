package linktest;

import java.awt.*;
import java.security.Key;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //创建邻接矩阵
        System.out.println("请输入邻接矩阵的顶点数:");
        Scanner scanner=new Scanner(System.in);
        int vertex=scanner.nextInt();
        MatrixAdjacency matrixAdjacency_Test=new MatrixAdjacency(vertex);
        //随机产生边
        System.out.println("只能最大的边数为:"+matrixAdjacency_Test.getMaxNumOfEdges()+",请输入边数:");
        int edges=scanner.nextInt();
        matrixAdjacency_Test.randonMatrixAdjacency(edges);
        //打印邻接矩阵
        System.out.println("1. 测试集邻接矩阵：");
        matrixAdjacency_Test.printMatrixAdjacency();
        System.out.println("一共有"+matrixAdjacency_Test.getNumOfEdges()+"条边");
        //需要得到一个训练集邻接矩阵
        int vertexs[]=new int[vertex];
        MatrixAdjacency matrixAdjacency_Train=new MatrixAdjacency(vertexs,matrixAdjacency_Test.getEdges());
        //删除指定边
        int key=1;
        while (key==1) {
            System.out.println("请输入要删除的行:(0~" + matrixAdjacency_Train.getNumOfVertex() + ")");
            int row = scanner.nextInt();
            System.out.println("请输入要删除的列:(0~" + matrixAdjacency_Train.getNumOfVertex() + ")");
            int column = scanner.nextInt();
            matrixAdjacency_Train.deleteEdges(row,column);
            System.out.println("输入0退出,1继续:");
            key=scanner.nextInt();
        }
        //输出删除边后的训练邻接矩阵
        System.out.println("2.训练集邻接矩阵：");
        matrixAdjacency_Train.printMatrixAdjacency();
        System.out.println("剩余有"+matrixAdjacency_Train.getNumOfEdges()+"条边");
        //输出被删除的边
        int deleteRows,deleteColumn;
        int deleteEdgesSize=matrixAdjacency_Train.getSaveDeleteEdges_ArrayList().size();//得到删除了几条边
        System.out.println("共删除"+deleteEdgesSize+"条边.");
        for (int i = 0; i <deleteEdgesSize ; i++) {
            deleteRows=(int)matrixAdjacency_Train.getSaveDeleteEdges_ArrayList().get(i).getX();//得到行标
            deleteColumn=(int)matrixAdjacency_Train.getSaveDeleteEdges_ArrayList().get(i).getY();//得到列标
            System.out.printf("    ["+(deleteRows+1)+" , "+(deleteColumn+1)+"]");
        }
        System.out.println();
        //输出未连接的边
        ArrayList NotConnectedEdges_ArrayList=matrixAdjacency_Train.getNotConnectedEdges_ArrayList();
        int numNotConnectedEdges=NotConnectedEdges_ArrayList.size();
        System.out.println("现在没有连接的边为:"+numNotConnectedEdges+"条");
        Point3D point3DEdges=new Point3D();
        for (int i = 0; i <numNotConnectedEdges; i++) {
            point3DEdges=(Point3D)NotConnectedEdges_ArrayList.get(i);
            System.out.printf("     ["+(point3DEdges.getX()+1)+" , "+(point3DEdges.getY()+1)+"]");
            if ((i+1)%11==0)System.out.println();
        }
        System.out.println();
        //调用共同邻居算法
        System.out.println("输出调用的方法后的结果:");
        System.out.println("1. 计算后的结果:");
        CommonNeighbor c= new CommonNeighbor(matrixAdjacency_Train.getEdges());
        MatrixAdjacency matrix_Similarity=new MatrixAdjacency(matrixAdjacency_Train.getVertex(),c.multiplication());
        matrix_Similarity.printMatrixAdjacency();
        //根据结果计算
        System.out.println("每条没有连接的边得到的值为:");
        int x,y,z;
        for (int i = 0; i <numNotConnectedEdges ; i++) {//把计算后的值放进去
            point3DEdges=(Point3D)NotConnectedEdges_ArrayList.get(i);
            point3DEdges.setZ(matrix_Similarity.getEdges()[point3DEdges.getX()][point3DEdges.getY()]);
            System.out.printf("     ["+(point3DEdges.getX()+1)+","+(point3DEdges.getY()+1)+",值:"+(point3DEdges.getZ())+"]");
            if ((i+1)%11==0)System.out.println();
        }
        System.out.println();
        //排序:
        Point3D point3DEdges2=new Point3D();
        Point3D point3DEdges3=new Point3D();
        for (int i = 0; i <numNotConnectedEdges ; i++) {
            for (int j = 0; j <numNotConnectedEdges-i-1 ; j++) {
                //System.out.println(j+"---"+numNotConnectedEdges+"----"+(j+1));
                point3DEdges=(Point3D)NotConnectedEdges_ArrayList.get(j);
                point3DEdges2=(Point3D)NotConnectedEdges_ArrayList.get(j+1);
                if (point3DEdges.getZ()<point3DEdges2.getZ()){
                    point3DEdges3.setX(point3DEdges.getX());
                    point3DEdges3.setY(point3DEdges.getY());
                    point3DEdges3.setZ(point3DEdges.getZ());
                    //
                    point3DEdges.setX(point3DEdges2.getX());
                    point3DEdges.setY(point3DEdges2.getY());
                    point3DEdges.setZ(point3DEdges2.getZ());
                    //
                    point3DEdges2.setX(point3DEdges3.getX());
                    point3DEdges2.setY(point3DEdges3.getY());
                    point3DEdges2.setZ(point3DEdges3.getZ());
                }
            }
        }
        //排完顺序再输出
        System.out.println("排完顺序后,每条没有连接的边得到的值为:");
        for (int i = 0; i <numNotConnectedEdges ; i++) {//把计算后的值放进去
            point3DEdges=(Point3D)NotConnectedEdges_ArrayList.get(i);
            point3DEdges.setZ(matrix_Similarity.getEdges()[point3DEdges.getX()][point3DEdges.getY()]);
            System.out.printf("     ["+(point3DEdges.getX()+1)+","+(point3DEdges.getY()+1)+",值:"+(point3DEdges.getZ())+"]");
            if ((i+1)%11==0)System.out.println();
        }
        System.out.println();
        //求概率
        Point pointEdges=new Point();
        System.out.println("请输入要取前百分之几的边:");
        double probability=scanner.nextDouble();//传进来概率
        int numNotConnectedEdges_probability =(int) (probability*0.01*numNotConnectedEdges);//前几条边
        probability=0;//初始化为0,等一下用来计算命中概率
        System.out.println("命中的边,和命中的概率为:");
        for (int i = 0; i <deleteEdgesSize ; i++) {//循环的次数是 删除边的次数
            for (int j = 0; j <numNotConnectedEdges_probability ; j++) {//循环的次数是 选取的前百分之几的边
                point3DEdges=(Point3D)NotConnectedEdges_ArrayList.get(j);//得到排序的边
                pointEdges=matrixAdjacency_Train.getSaveDeleteEdges_ArrayList().get(i);//得到被删除的边
                if (point3DEdges.getX()==(int) (pointEdges.getX()) && point3DEdges.getY()==(int) (pointEdges.getY())){//对比会不会命中
                    System.out.println("     ["+(point3DEdges.getX()+1)+","+(point3DEdges.getY()+1)+",值:"+(point3DEdges.getZ())+"]");
                    probability++;//命中一条加1
                }
            }
        }
        System.out.println((probability/numNotConnectedEdges_probability*100)+"%");
    }
}
