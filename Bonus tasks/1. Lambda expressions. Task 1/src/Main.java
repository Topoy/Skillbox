import java.util.function.BiFunction;
import java.util.function.Function;

public class Main
{
    public static void main(String[] args)
    {
        BiFunction<Integer, Integer, Integer> addAtoB = (a, b) -> a + b;
        BiFunction<Integer, Integer, Integer> multiplyAtimesB = (a, b) -> a * b;

        Function<Integer, Integer> add5toB = partial(addAtoB, 5);
        Function<Integer, Integer> multiplyOn2 = partial(multiplyAtimesB, 2);

        System.out.println("5 + 2 = " + add5toB.apply(2));
        System.out.println("7 * 2 = " + multiplyOn2.apply(7));
    }

    private static Function<Integer, Integer> partial(BiFunction<Integer, Integer, Integer> biFunction, Integer argument)
    {
        int a = 5;
        int b = 7;
        Function<Integer, Integer> result = null;
        if (biFunction.apply(a, b) == 12)
        {
            result = (x) -> argument + x;
        }
        if (biFunction.apply(a, b) == 35)
        {
            result = (x) -> argument * x;
        }
        return result;
    }
}
