package netology.data;

import lombok.Value;

public class DataHelper {

    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    @Value
    public static class CardInfo {
        private String id;

        private String numberCard;
    }

    @Value
    public static class TransferInfo {
        private String from;
        private String to;
        private int amount;
    }

    @Value
    public static class InfoForToken {
        private String login;
        private String code;
    }

    @Value
    public static class Token {
        private String token;
    }

    public static Token getToken() {
        return new Token(DataGenerator.getToken(getValidInfoForToken()));
    }

    public static AuthInfo getValidAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static InfoForToken getValidInfoForToken() {
        String code = DBHelper.getCode();
        String login = getValidAuthInfo().login;
        return new InfoForToken(login, code);
    }

    public static CardInfo card1() {
        return new CardInfo("92df3f1c-a033-48e6-8390-206f6b1f56c0", "5559 0000 0000 0001");
    }

    public static CardInfo card2() {
        return new CardInfo("0f3f5c2a-249e-4c3d-8287-09f7a039391d", "5559 0000 0000 0002");
    }

    public static CardInfo invalidCard() {
        return new CardInfo("0f3f5c2a-249e-4c3d-8287-09f7a0393911", "5559 0000 0000 0000");
    }


}
