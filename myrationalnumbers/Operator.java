package myrationalnumbers;

public class Operator {

    public Rnum addition(Rnum first, Rnum second){
        int new_up = first.up * second.down + second.up * first.down;
        int new_down = first.down * second.down;

        return new Rnum(new_up, new_down);
    }

    public Rnum subtraction(Rnum first, Rnum second){
        int new_up = first.up * second.down - second.up * first.down;
        int new_down = first.down * second.down;

        return new Rnum(new_up, new_down);
    }

    public Rnum multiplication(Rnum first, Rnum second){
        int new_up = first.up * second.up;
        int new_down = first.down * second.down;

        return new Rnum(new_up, new_down);
    }

    public Rnum division(Rnum first, Rnum second){
        int new_up = first.up * second.down;
        int new_down = first.down * second.up;

        return new Rnum(new_up, new_down);
    }


}
