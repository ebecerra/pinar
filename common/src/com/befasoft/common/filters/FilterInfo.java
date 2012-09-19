package com.befasoft.common.filters;

/**
 * User: Eduardo
 * Date: 13-feb-2006
 * Time: 5:59:17
 */
public abstract class FilterInfo {
    public final static int FLT_EQUAL       = 1;
    public final static int FLT_LESS        = 2;
    public final static int FLT_GREAT       = 3;
    public final static int FLT_LESSEQUAL   = 4;
    public final static int FLT_GREATEQUAL  = 5;
    public final static int FLT_NOTEQUAL    = 6;
    public final static int FLT_NULL        = 7;
    public final static int FLT_NOTNULL     = 8;
    public final static int FLT_IN          = 9;
    public final static int FLT_NOTIN       = 10;
    public final static int FLT_LIKE        = 11;
    public final static int FLT_NOTLIKE     = 12;
    
    public final static int FLT_AND         = 100;
    public final static int FLT_OR          = 101;
    public final static int FLT_OPEN        = 102;
    public final static int FLT_CLOSE       = 103;

    int cond;       // Expresion condicional
    boolean empty;  // Indica si el item es vacio
    String field;   // Campo por el que se filtra
    String keymap;  // Nombre del campo llave
    Object value;   // Valor por el que se filtra

    abstract public String getSQL(int index);
    abstract public boolean isParameters();

    /**
     * Retorna la forma de comparacion
     * @return Expresion de comparacion SQL
     */
    protected String getCondExpr() {
        switch (cond) {
            case FLT_EQUAL: return " = ";
            case FLT_LESS: return " < ";
            case FLT_GREAT: return " > ";
            case FLT_LESSEQUAL: return " <= ";
            case FLT_GREATEQUAL: return " >= ";
            case FLT_NOTEQUAL: return " <> ";
            case FLT_NULL: return " is null ";
            case FLT_NOTNULL: return " is not null ";
            case FLT_IN: return " in ";
            case FLT_NOTIN: return " not in ";
            case FLT_LIKE: return " like ";
            case FLT_NOTLIKE: return " not like ";
        default:
            return " ";
        }
    }

    /*
     * Metodos Get/Set
     */
    public int getCond() {
        return cond;
    }

    public void setCond(int cond) {
        this.cond = cond;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public String getField() {
        return field;
    }

    public String getFieldkey() {
        String key = "";
        for (int i = 0; i < field.length(); i++) {
            if (field.charAt(i) == '(' || field.charAt(i) == ')' || field.charAt(i) == '_')
                key += "_";
            else
                key += field.charAt(i);
        }
        return key;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getKeymap() {
        return keymap;
    }

    public void setKeymap(String keymap) {
        this.keymap = keymap;
    }
}
