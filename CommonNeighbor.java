package linktest;

public class CommonNeighbor {
    private int matrix_Similarity[][];
    private int matrixAdjacency_Train[][];
    public CommonNeighbor(int[][] matrixAdjacency){
        matrix_Similarity=new int[matrixAdjacency.length][matrixAdjacency.length];
        this.matrixAdjacency_Train=matrixAdjacency;
    }
    //访问器
    public int[][] getMatrix_Similarity() {
        return matrix_Similarity;
    }
    public int[][] multiplication(){//矩阵相乘
        for (int i = 0; i <matrix_Similarity.length ; i++) {
            for (int j = 0; j <matrix_Similarity[i].length ; j++) {
                for (int k = 0; k < matrix_Similarity[i].length; k++) {
                    matrix_Similarity[i][j]+=matrixAdjacency_Train[i][k]*matrixAdjacency_Train[k][j] ;
                }
            }
        }
        return matrix_Similarity;
    }
}
