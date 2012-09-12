<%@ page import="org.apache.commons.logging.LogFactory" %>
<%@ page import="org.apache.commons.logging.Log" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%  // Registra la traza del error en los ficheros y DB
    Log log = LogFactory.getLog("GENERIC_ERROR");
    log.error("Exception: "+request.getAttribute("exception"));
    log.error("Exception: "+request.getAttribute("exceptionStack"));
    
    System.out.println("Exception: "+ request.getAttribute("exception"));
    System.out.println("Exception: "+request.getAttribute("exceptionStack"));
//    DBLogger.log(DBLogger.LEVEL_ERROR, "EXCEPTION", Constants.APP_NAME, ""+request.getAttribute("exception"));
%>

<p>&nbsp;
<div align="center">
    <div class="ui-widget" style="width:500px">
        <div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">
            <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span></p>
            <strong><s:text name="appbs.exception"/></strong>
            <br><strong><s:text name="appbs.exception.error"/>:<br>
            <s:property value="%{exception.message}"/></strong>
        </div>
    </div>
</div>
    