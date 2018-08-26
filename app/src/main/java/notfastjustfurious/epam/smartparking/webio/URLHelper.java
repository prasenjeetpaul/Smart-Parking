package notfastjustfurious.epam.smartparking.webio;

public class URLHelper {
    private static final String SERVER_URL = "http://10.71.12.253:85/";

    public static final String GET_AVAILABLE_SLOT_URL = SERVER_URL + "available_slot.php";

    public static final String GET_DEDICATED_USER = SERVER_URL + "get_user.php";

    public static final String CANCEL_DEDICATED_SLOT = SERVER_URL + "cancel_user.php";

    public static final String DELAY_DEDICATED_SLOT = SERVER_URL + "delay_user.php";

    public static final String EDIT_DEDICATED_USER = SERVER_URL + "edit_user.php";

    public static final String LOGIN = SERVER_URL + "login_user.php";

    public static final String REGISTER = SERVER_URL + "register_user.php";


}
