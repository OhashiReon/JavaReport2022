import java.util.ArrayList;
import java.util.List;
class Recurrence_formula{
    public static void main(String[] args){
        float first = 1;
        float ratio= 2;
        float cons = 1;
        float ans = first;
        int i = 1;
        List<Float> l = new ArrayList<Float>(); 
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