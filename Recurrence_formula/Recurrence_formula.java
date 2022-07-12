import java.util.ArrayList;
import java.util.List;
class Recurrence_formula{
    public static void main(String[] args){
        int first = 1;
        int ratio= 2;
        int cons = 1;
        int ans = first;
        int i = 1;
        List<Integer> l = new ArrayList<Integer>(); 
        l.add(ans);
        while(i < 20 && ans <= 100){
            i += 1;
            ans = ans* ratio + cons;
            l.add(ans);
        }
        System.out.println(ans+" "+i);
        System.out.println(l);
    }
}