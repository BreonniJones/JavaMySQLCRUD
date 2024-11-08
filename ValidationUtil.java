package CustomerPack;

public class ValidationUtil {

    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("\\d{10,}");
    }

    public static boolean isValidAddress(String address) {
        return address != null && !address.trim().isEmpty();
    }
}
