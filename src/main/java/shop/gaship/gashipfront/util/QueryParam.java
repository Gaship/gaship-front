package shop.gaship.gashipfront.util;

/**
 * @author 조재철
 * @since 1.0
 */
public class QueryParam {

    /**
     * The Name.
     */
    String name;
    /**
     * The Values.
     */
    Object[] values;

    /**
     * Instantiates a new Query param.
     *
     * @param name   the name
     * @param values the values
     */
    public QueryParam(String name, Object... values) {
        this.name = name;
        this.values = values;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return object [ ]
     * @author 조재철
     */
    public Object[] getValues() {
        return values;
    }
}
