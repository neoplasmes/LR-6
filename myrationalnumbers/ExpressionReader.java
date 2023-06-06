package myrationalnumbers;

import java.util.*;

public class ExpressionReader{
    //костыль :(
    boolean digit_was = false;
    String first_minus = "";
    //конец костыля

    Operator operator = new Operator();

    private enum ElementType{
        DIGIT,
        L_BR, R_BR,
        PLUS, MINUS, MUL, DIV,
        END;

    }

    private class Element{

        ElementType type;
        String value;

        Element( ElementType type, String value){
            this.type = type;
            this.value = value;
            if (this.value.endsWith("/")) {
                throw new RuntimeException("ну нельзя / на конце");
            }
        }

        Element( ElementType type, Character value){
            this.type = type;
            this.value = value.toString();
            if (this.value.endsWith("/")) {
                throw new RuntimeException("ну нельзя / на конце");
            }
        }

        public Rnum to_Rnum(){
            //лютый костыль из за того, что Arrays Arrays.asList(value.split("/"))
            ArrayList<String> x = new ArrayList<>();
            Collections.addAll(x, value.split("/"));
            if(x.size() < 2){
                x.add("1");
            }
            int up = Integer.parseInt(x.get(0));
            int down = Integer.parseInt(x.get(1));

            return new Rnum(up, down);
        }

    }


    private static class ExpBuffer{

        protected static int pos = 0;

        ArrayList<Element> elements;

        ExpBuffer(ArrayList<Element> elements){
            this.elements = elements;
        }

        public Element next(){
            return elements.get(pos++);
        }



    }


    //Метод, преобразующий строку в набор элементов математического выражения с определёнными свойствами.
    private ArrayList<Element> analyze(String exp){

        ArrayList<Element> elements = new ArrayList<>();
        int pos = 0;
        exp = exp.replaceAll(" ", "");

        while (pos < exp.length()){
            char c = exp.charAt(pos);

            Element e = switch (c){
                case '(' -> new Element(ElementType.L_BR, c);
                case ')' -> new Element(ElementType.R_BR, c);
                case '+' -> new Element(ElementType.PLUS, c);
                case '-' -> {
                    //костыль
                    if (digit_was == false) {
                        first_minus = "-";
                        yield null;
                    } else {
                    //конец костыля
                        yield new Element(ElementType.MINUS, c);
                    }

                }
                case '*' -> new Element(ElementType.MUL, c);
                case ':' -> new Element(ElementType.DIV, c);
                default -> {
                    if ( (c >= '0' && c <= '9') || c == '/'){
                        String digit = "";
                        if (digit_was == false) {digit += first_minus;}
                        digit_was = true;

                        while ( (c >= '0' && c <= '9') || c == '/'){
                            digit += Character.toString(c);
                            pos++;
                            if(pos >= exp.length()) { break; }
                            c = exp.charAt(pos);
                        }

                        //костыль, который нужен, чтобы вернуть указатель(pos) в естественное положение.
                        //в неестественном он находится потому, что в цикле while мы как бы проверяем следующий идущий символ.
                        //а нам надо остаться на текущем, поэтому:
                        pos--;

                        yield new Element(ElementType.DIGIT, digit);
                    } else {
                        throw new RuntimeException("че то херня, переписывай");
                    }
                }
            };

            if (e != null) {elements.add(e);}

            pos++;

        }
        elements.add(new Element(ElementType.END, ""));
        return elements;
    }

    public Rnum count(String expression){
        ExpBuffer.pos = 0;
        ArrayList<Element> p = analyze(expression);
        ExpBuffer q = new ExpBuffer(p);

        Rnum r = plusminus(q);
        r.reduce();

        return r;
    }

    public Rnum plusminus(ExpBuffer buffer){
        Rnum n = multdiv(buffer);
        while(true){
            Element e = buffer.next();
            switch (e.type){
                case PLUS -> n = operator.addition(n, multdiv(buffer));
                case MINUS -> n = operator.subtraction(n, multdiv(buffer));
                case R_BR, END -> {
                    ExpBuffer.pos--;
                    return n;
                }
                default -> throw new RuntimeException("мама родная");
            }
        }
    }

    public Rnum multdiv(ExpBuffer buffer){
        //Element e = buffer.next();
        Rnum n = digit(buffer);
        while(true){
            Element e = buffer.next();
            switch (e.type){
                case MUL -> n = operator.multiplication(n, digit(buffer));
                case DIV -> n = operator.division(n, digit(buffer));
                case R_BR, PLUS, MINUS, END -> {
                    ExpBuffer.pos--;
                    return n;
                }
                default -> throw new RuntimeException("мама родная");
            }
        }

    }

    public Rnum digit(ExpBuffer buffer){
        Element e = buffer.next();
        switch (e.type){
            case DIGIT:
                return e.to_Rnum();
            /*case MINUS:
                e = buffer.next();
                return operator.subtraction(new Rnum(0), e.to_Rnum());*/
            case L_BR:
                Rnum val = plusminus(buffer);
                e = buffer.next();
                if (e.type != ElementType.R_BR) {
                    throw new RuntimeException("мама родная");
                }
                return val;
            default:
                throw new RuntimeException("мама родная");
        }
    }


}
