package com.befasoft.common.model.appbs;

import java.lang.annotation.*;

/*
   Todos los campos marcados con @ExcludeJSON no se retornan en el JSON.

 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcludeJSON {
}
