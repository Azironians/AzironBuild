package managment.playerManagement;

public enum GameMode {
    _1x1 {
        @Override
        public String toString() {
            return "1x1";
        }
    }, _2x2 {
        @Override
        public String toString() {
            return "2x2";
        }
    }
}
