import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

class Recurrence_formula{
    public static void main(String[] args){
        System.out.println("初項aを入力してください");
        float first = getFloat();
        System.out.println("αを入力してください");
        float ratio= getFloat();
        System.out.println("βを入力してください");
        float cons = getFloat();
        float ans = first;
        int i = 1;
        List<Float> l = new ArrayList<Float>(); 
        l.add(ans);
        while(i < 20 && ans <= 100){
            i += 1;
            ans = ans* ratio + cons;
            l.add(ans);
        }
        System.out.println("n="+ans+" 項数="+i);
        System.out.println("すべての項"+l);
    }

    public static float getFloat(){
        while(true){
            try{
                Scanner sc = new Scanner(System.in);
                return sc.nextFloat();
            }catch (InputMismatchException e){
                System.out.println("数字で入力してください");
            }
        }
    }
}