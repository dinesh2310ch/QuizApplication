public class SessionManager {
    private static Integer currentUserId;

    public static void setCurrentUserId(Integer userId) {
        currentUserId = userId;
    }

    public static Integer getCurrentUserId() {
        return currentUserId;
    }
}
