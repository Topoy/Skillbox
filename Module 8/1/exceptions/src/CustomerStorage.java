import java.util.HashMap;

public class CustomerStorage
{
    private HashMap<String, Customer> storage;

    public CustomerStorage()
    {
        storage = new HashMap<>();
    }

    public void addCustomer(String data)
    {
        final String NAME_PATTERN = "[а-яА-Яa-zA-Z]+";
        String[] components = data.split("\\s+");
        try {
            if (components[0].matches(NAME_PATTERN) && (components[1].matches(NAME_PATTERN))) {
                String name = components[0] + " " + components[1];
                if (isValidEmailAddress(components[2]) && isValidPhoneNumber(components[3])) {
                    storage.put(name, new Customer(name, components[3], components[2]));
                }
                else {
                    System.out.println("Вы ввели неверный адрес почты или номер телефона. Пожалуйста проверьте данные");
                }
            }
            else {
                System.out.println("Вы уверены, что ввели верные имя и фамилию? Пожалуйста введите имя и фамилию заново");
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Вы ввели не все данные. Пожалуйста, введите данные, как указано в примере. Для того, чтобы" +
                    " увидеть пример напишите \"help\" в консоли");
        }
    }

    public void listCustomers()
    {
        storage.values().forEach(System.out::println);
    }

    public void removeCustomer(String name)
    {
        if (name.isEmpty())
        {
            System.out.println("Вы не указали имя");
            return;
        }
        storage.remove(name);
    }

    public int getCount()
    {
        return storage.size();
    }

    private static boolean isValidEmailAddress(String email)
    {
        boolean result = false;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if (email.matches(EMAIL_PATTERN))
        {
            result = true;
        }
        return result;
    }

    private static boolean isValidPhoneNumber(String phoneNumber)
    {
        boolean result = false;
        final String PHONE_NUMBER_PATTERN = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";
        if (phoneNumber.matches(PHONE_NUMBER_PATTERN))
        {
            result = true;
        }
        return result;
    }


}