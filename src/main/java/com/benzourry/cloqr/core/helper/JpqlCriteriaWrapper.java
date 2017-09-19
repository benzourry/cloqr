package com.benzourry.cloqr.core.helper;

/**
 * Created by user on 3/25/14.
 */
public class JpqlCriteriaWrapper {
    private String property;
    private Object value;
    private String paramName;

    public JpqlCriteriaWrapper(String property, Object value, String paramName){
        this.property = property;
        this.value = value;
        this.paramName = paramName;
    }

    public String getProperty() {
        return property;
    }

    public Object getValue() {
        return value;
    }

    public String getParamName() {
        return paramName;
    }
}
