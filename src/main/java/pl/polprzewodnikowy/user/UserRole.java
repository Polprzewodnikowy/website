package pl.polprzewodnikowy.user;

public enum UserRole {
    ADMIN {
        @Override
        public String toString() {
            return "ADMIN";
        }
    },
    USER {
        @Override
        public String toString() {
            return "USER";
        }
    }
}
