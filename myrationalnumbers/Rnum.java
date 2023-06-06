package myrationalnumbers;

public class Rnum {

    //Объекты класса Rnum - рациональные числа, представленные в виде дроби (up/down)
    protected int up;
    protected int down;


    //Конструктор, в котором задаётся число
    //Классический вариант типа a/b.
    public Rnum(int up, int down){
        if (down == 0) {
            throw new RuntimeException("Нельзя делить на ноль");
        }
        this.up = up;
        this.down = down;
    }
    //Перегрузка для создания обычных чисел типа a (а/1)
    public Rnum(int value){
        this.up = value;
        this.down = 1;
    }
    //Перегрузка для создания числа по умолчанию - 1. (требование ТЗ)
    public Rnum(){
        this.up = 1;
        this.down = 1;
    }



    //Геттер. Строковое представление числа.
    public String get_value(){
        if (this.up == 0) {return "0";}
        if(this.down == 1){
            return Integer.toString(this.up);
        } else {
            return this.up + "/" + this.down;
        }
    }
    //Перегрузка геттера для представления Rnum в виде объекта класса int или double
    public Object get_value(String modification){
        switch (modification){
            case "int":
                return this.up / this.down;
            case "double":
                return ((double)this.up / (double)this.down);
            default:
                System.out.println("Че ты написал?");
                return get_value();
        }
    }
    //Сеттер типа a/b
    public void set_value(int up, int down){
        this.up = up;
        this.down = down;
    }
    //Перегрузка. Сеттер типа a
    public void set_value(int up){
        set_value(up, 1);
    }



    //алгоритм Евклида для нахождения НОД. Необходим для нахождения числа, на которое потом будет сокращаться числитель
    //и знаменатель. Сокращать надо, чтобы число было представлено в нормальном виде.
    private int get_NOD(int a, int b){
        while ((a != 0) & (b != 0)){
            if (a >= b) {a = a % b;} else { b = b % a;}
        }
        return (a > b) ? a : b;
    }
    //Метод для непосредственного сокращения дроби
    public void reduce(int n){
        if ( Math.abs(n) > Math.abs(up) ) {
           // System.out.println("Невозможно сократить дробь на число, превышающее числитель по модулю");
        } else if ( Math.abs(n) > Math.abs(down)) {
           // System.out.println("Невозможно сократить дробь на число, превышающее знаменатель по модулю");
        } else if ( up % n != 0) {
           // System.out.println("Числитель не делится на введённое число");
        } else if ( down % n != 0) {
            //System.out.println("Знаменатель не делится на введённое число");
        } else {
            up = up / n;
            down = down / n;
        }
    }
    //Перегрузка сокращения с максимально возможным сокращением (сокращение на НОД числителя и знаменателя)
    public void reduce(){
        int nod = get_NOD(Math.abs(up), Math.abs(down));
        reduce(nod);
    }



    //Использование класса Operator для реализации математических операций над текущим объектом класса Rnum
    private Operator op = new Operator();

    //Добавить число
    public void add(Rnum n){
        Rnum result = op.addition(new Rnum(this.up, this.down), n);
        this.up = result.up;
        this.down = result.down;
    }

    //Вычесть число
    public void subtract(Rnum n){
        Rnum result = op.subtraction(new Rnum(this.up, this.down), n);
        this.up = result.up;
        this.down = result.down;
    }

    //Умножить на число
    public void multiply(Rnum n){
        Rnum result = op.multiplication(new Rnum(this.up, this.down), n);
        this.up = result.up;
        this.down = result.down;
    }

    //Поделить на число
    public void divide(Rnum n){
        Rnum result = op.division(new Rnum(this.up, this.down), n);
        this.up = result.up;
        this.down = result.down;
    }

}
