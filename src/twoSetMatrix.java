public class twoSetMatrix {
    public int[][] twoSetMatrix(int n) {
        int[][] matrix = new int[n][n];
        int num = 1;
        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) {
                for (int j = 0; j < n; j++) {
                    matrix[i][j] = num++;
                }
            } else {
                for (int j = n - 1; j >= 0; j--) {
                    matrix[i][j] = num++;
                }
            }
        }
        return matrix;
    }

    public static void main(String[] args) {
        twoSetMatrix twoSetMatrix = new twoSetMatrix();
        int n = 3;
        int[][] matrix = twoSetMatrix.twoSetMatrix(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
