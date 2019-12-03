import tasks.Task02Reduce;
import tasks.Task03TruckTypes;

import java.util.Arrays;
import java.util.HashMap;

import static tasks.Task03TruckTypes.TruckType.SemiTrailer;
import static tasks.Task03TruckTypes.TruckType.SmallBoxTruck;

class Main {
    public static void main(String[] args)
    {
        //System.out.println("Please use tests to run the program");
        //System.out.println(Task02Reduce.multiply(Arrays.asList()));

        var a = new Task03TruckTypes.Truck(5_000);
        var b = new Task03TruckTypes.Truck(5_100);
        var c = new Task03TruckTypes.Truck(20_000);

        System.out.println(Task03TruckTypes.groupTrucksByType(Arrays.asList(a, b, c)));
    }
}
