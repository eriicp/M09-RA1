public class SO {

    public static final boolean ES_WINDOWS =
            System.getProperty("os.name").toLowerCase().startsWith("win");

    public static String[] llistarFitxers() {
        if (ES_WINDOWS) {
            return new String[] {"cmd", "/c", "dir"};
        } else {
            return new String[]{"ls", "-la"};
        }
    }

    public static String[] ordenar() {
        if (ES_WINDOWS) {
            return new String[] {"cmd", "/c", "sort"};
        } else {
            return new String[]{"sort"};
        }
    }

    public static String[] filtrar(String patro) {
        if (ES_WINDOWS) {
            return new String[] {"cmd", "/c", "findstr", patro};
        } else {
            return new String[]{"grep", patro};
        }
    }

    public static String nomSO() {
        return System.getProperty("os.name");
    }
}