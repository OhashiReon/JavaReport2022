import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Hanoi{
    public static void main(String[] args){
        Towers towers = new Towers(InputIntPos());
        towers.solve();
    }
    static int InputInt(){
        while(true){
            System.out.println("ハノイの塔の段数を入力してください");
            try{
                Scanner sc = new Scanner(System.in);
                return sc.nextInt();
            }catch (InputMismatchException e){
                System.out.println("数字で入力してください");
            }
        }
    }
    static int InputIntPos(){
        int r = 0;
        while(true){
            r = InputInt();
            if(r>=0){
                break;
            }
            System.out.println("正の数で入力してください");
        }
        return r;
    }
}

class Towers{
    int n;
    List<ArrayList<Integer>> towers;
    public Towers(int n){
        this.n = n;
        towers = new ArrayList<ArrayList<Integer>>();
        towers.add(new ArrayList<>());
        towers.add(new ArrayList<>());
        towers.add(new ArrayList<>());
        for(int i = n;i>0;i--){
            towers.get(0).add(i);
        }
    }

    public void show(){
        System.out.println(repeat("=",(n+1)*3));
        for(int j = n-1;j>=0;j--){
            for(int i=0;i<3;i++){
                //System.out.println(i+" "+j);
                int num;
                if(towers.get(i).size() >j){
                    num = towers.get(i).get(j);
                }else{
                    num = 0;
                }
                System.out.print(repeat("x",num));
                System.out.print(repeat(" ",n-num));
                System.out.print(repeat(" ",1));
            }
            System.out.println();
        }
    }
    public static String repeat(String str, int n) {
        return String.join("" , Collections.nCopies(n, str));
    }
    public void solve(){
        move(n,0,2);
        show();
    }

    public void move(int num,int from,int to){
        if(num == 0) return;
        int tmp = 3-from-to;
        move(num-1,from,tmp);
        show();
        towers.get(from).remove(towers.get(from).size()-1);
        towers.get(to).add(num);
        move(num-1,tmp,to);
    }
}