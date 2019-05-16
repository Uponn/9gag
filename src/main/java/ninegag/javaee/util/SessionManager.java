package ninegag.javaee.util;

import ninegag.javaee.models.dto.UserSessionDTO;

import javax.servlet.http.HttpSession;

public class SessionManager {
    private static final String LOGGED = "logged";
    private static final String USER = "user";

    public static boolean isLogged(HttpSession session){
        if (session.isNew()){
            return false;
        }
        if (session.getAttribute(LOGGED) == null){
            return false;
        }
        return true;
    }
    public static void logUser(HttpSession session, UserSessionDTO user){
        session.setAttribute(LOGGED, true);
        session.setAttribute(USER, user);
    }
    public static long getUserId(HttpSession session){
        return ((UserSessionDTO) session.getAttribute(USER)).getId();
    }
}
