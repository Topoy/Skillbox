import java.util.function.BiFunction;
import java.util.function.Function;

public class Main
{
    public static void main(String[] args)
    {
        //addAtoB принимает на вход 2 параметра типа Integer, складывает их и возвращает результат-сумму типа Integer
        BiFunction<Integer, Integer, Integer> addAtoB = (a, b) -> a + b;
        //multiplyAtimesB принимает на вход два параметра типа Integer, перемножает их и возвращает результат-произведение типа Integer
        BiFunction<Integer, Integer, Integer> multiplyAtimesB = (a, b) -> a * b; //

        BiFunction<Integer, Integer, Integer> printWithComma = (a, b) ->
        {
            System.out.println(a + "," + b);
            return 0;
        };


        Function<Integer, Integer> add5toB = advancedPartial(addAtoB, 5); // = (x) -> 5 + x;   addAtoB - маркер
        Function<Integer, Integer> multiplyOn2 = advancedPartial(multiplyAtimesB, 2); // = (x) -> 2 * x   ; multiplyAtimesB - маркер
        Function<Integer, Integer> add5toBWithComma = advancedPartial(printWithComma, 5);


        System.out.println("5 + 2 = " + add5toB.apply(2)); // x = 2; 5 + 2; 2 - параметр на входе
        System.out.println("7 * 2 = " + multiplyOn2.apply(7)); // x = 7; 2 * 7; 7 - параметр на входе
        System.out.println(add5toBWithComma.apply(7));

    }

    //biFunction - это маркер, с помощью которого определяется какая будет произведена операция: сложение или умножение.
    private static Function<Integer, Integer> partial(BiFunction<Integer, Integer, Integer> biFunction, Integer argument)
    {
        int a = 5;
        int b = 7;
        Function<Integer, Integer> result = null;
        if (biFunction.apply(a, b) == 12) //проверка, сложение ли внутри biFunction
        {
            result = (x) -> argument + x;
        }
        if (biFunction.apply(a, b) == 35) //проверка, умножение ли внутри biFunction
        {
            result = (x) -> argument * x;
        }
        return result;
    }

    //Метод advancedPartial является более универсальным по сравнению с методом partial из-за способа реализации.
    //biFunction выполняет роль маркера. От него зависит всё поведение метода. Какая функциональность была заложена в
    //biFunction, такая функциональность и будет в основе метода advancedPartial. Будь то сложение, умножение, деление и др.
    private static Function<Integer, Integer> advancedPartial(BiFunction<Integer, Integer, Integer> biFunction, Integer argument)
    {
        Function<Integer, Integer> result;
        result = (x) -> biFunction.apply(x, argument);
        return result;
    }
}
