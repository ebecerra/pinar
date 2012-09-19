package com.befasoft.common.business;

import com.befasoft.common.beans.ActionState;
import com.befasoft.common.beans.ActionStateElement;

public class ActionThread extends Thread {

    protected ActionState actionState;
    protected ActionStateElement element;

    public ActionThread(ActionState actionState, ActionStateElement element) {
        this.actionState = actionState;
        this.element = element;
    }


}
