public class Lab1 {
    private static short[] shortCast(int[] a) {
        short[] b = new short[a.length];
        for (int i =0; i<a.length; i++) {
            b[i] = (short)a[i];
        }
        return b;
    }
    private static boolean contains(short[] arr, short val) {
        boolean a = false;
        for (short i : arr) {
            if (i == val) {
                a = true;
            }
        }
        return a;
    }
    private static int[] rangeClosed(int a, int b, int step) {
        int[] temp = new int[(b-a)/2+1];
        for (int i=0; i<temp.length; i+=1) {
            temp[i] = (a+i*2);
        }
        return temp;
    }
    private static double[] fillRand(int len, double minV, double maxV) {
        double[] temp = new double[len];
        for (int i=0; i<len; i++) {
            temp[i] = minV + Math.random()*(maxV-minV);
        }
        return temp;
    }
    public static void main(String[] args) {
        short[] d = shortCast(rangeClosed(3, 23, 2));
        double[] x = fillRand(16, -15.0, 14.0);
        double[][] b = new double[11][16];
        for (int i=0; i<b.length; i++) {
            for (int j=0; j<b[0].length; j++) {
                if (d[i]==7) {
                    b[i][j] = Math.pow((Math.log(Math.abs(x[j]))*(Math.asin((x[j]-0.5)/29)-1)-1/2)/2, 3);
                } else if (contains(new short[]{3, 5, 11, 15, 21}, d[i])) {
                    b[i][j] = (Math.tan(x[j]/2)+1)/Math.exp(Math.exp(x[j]));
                } else {
                    b[i][j] = Math.asin(0.1*Math.pow(Math.cos(x[j]),2));
                }
            }
        }

        for (var i : b) {
            for (var j : i) {
                String curNum = String.format("%6s", String.format("%.2f", j));
                curNum = curNum.replace("0,00", "----");
                System.out.print(curNum);
            }
            System.out.print("\n");
        }
    }
}