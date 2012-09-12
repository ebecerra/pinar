<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<html>
<head>
    <title><s:text name="app.title"/></title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">

    <!-- ExtJS CSS -->
    <link rel="stylesheet" type="text/css" href="ext-3.3.1/resources/css/ext-all.css"/>
    <link rel="stylesheet" type="text/css" href="ext-3.3.1/examples/shared/examples.css"/>

    <!-- Application CSS -->
    <link href="appbs.css" type="text/css" rel="stylesheet"/>
    <link href="demo.css" type="text/css" rel="stylesheet"/>

    <!-- ExtJS -->
    <script type="text/javascript" src="ext-3.3.1/adapter/ext/ext-base.js"></script>
    <script type="text/javascript" src="ext-3.3.1/ext-all.js"></script>
    <!-- extensions -->
    <script type="text/javascript" src="ext-3.3.1/examples/ux/Spinner.js"></script>
    <script type="text/javascript" src="ext-3.3.1/examples/ux/SpinnerField.js"></script>
    <script type="text/javascript" src="ext-3.3.1/examples/shared/examples.js"></script>
    <script type="text/javascript" src="ext-3.3.1/examples/ux/RowExpander.js"></script>
    <script type="text/javascript" src="ext-3.3.1/src/ext-core/src/util/JSON.js"></script>

    <!-- include the locale file -->
    <script type="text/javascript" src="ext-3.3.1/src/locale/ext-lang-es.js"></script>
    <script type="text/javascript" src="Ext.i18n.bundle.v0.2.1/PropertyReader.js"></script>
    <script type="text/javascript" src="Ext.i18n.bundle.v0.2.1/Bundle.js"></script>
    <script type="text/javascript" src="js/Language.js"></script>

    <script type="text/javascript" src="ext-3.3.1/examples/ux/fileuploadfield/FileUploadField.js"></script>
    <link rel="stylesheet" type="text/css" href="ext-3.3.1/examples/ux/fileuploadfield/css/fileuploadfield.css">

    <!-- Application scripts -->
    <script type="text/javascript" src="js/appbs_base.js"></script>
    <s:include value="appbs_extjs/basic_info.jsp"/>
    <script type="text/javascript">

        Ext.onReady(function() {
            mainDiv = Ext.get("panelMainContent");
            Ext.TaskMgr.stopAll();
            Ext.TaskMgr.start({
                run: function(){
                    Ext.Ajax.request({
                        url: appPath+'/KeepAlive.action',
                        success: function(response) { },
                        failure: function() { }
                    });
                },
                interval: 20*60*1000 // 20 minutos
            });
        });

        function loginOut(){
            window.location = appPath+'/Login.action?action=logout';
        }

    </script>
    <s:if test="#session.user != null">

        <s:if test="#session.user.perfil_tipo == 'MANAGER'">
            <script type="text/javascript" src="js/main_manager.js"></script>
            <s:include value="appbs_extjs/datasources.jsp"/>
            <s:include value="appbs_extjs/password.jsp"/>
            <s:include value="appbs_extjs/parametros.jsp"/>
            <s:include value="appbs_extjs/usuarios.jsp"/>
            <s:include value="appbs_extjs/perfiles.jsp"/>
            <s:include value="appbs_extjs/log.jsp"/>
            <s:include value="appbs_extjs/session.jsp"/>
            <s:include value="appbs_extjs/lookups.jsp"/>
            <s:include value="appbs_extjs/usuarios_log.jsp"/>
            <s:include value="appbs_extjs/usuarios_log_legend.jsp"/>
        </s:if>

    </s:if>

    <s:if test="#session.user == null || #session.user.perfil_tipo == 'MANAGER' || #session.user.perfil_tipo == 'ADMIN'">
        <s:include value="main.jsp"/>
    </s:if>

    <s:if test="#session.user == null">
        <script type="text/javascript">
            function recoverPassword() {
                Ext.get("dLogin_check").hide();
                Ext.get("dLogin_recover").show();
            }

            function showLogin(form, tipo) {
                window.location = appPath+'/Index.action';
            }

            function submitLogin(form, tipo) {
                Ext.get(tipo == 'login' ? 'tdLoginError' : tipo == 'recover' ? 'tdRecoverError' : 'tdResetError').update("");
                var params;
                if (tipo == 'reset') {
                    if (form.clave.value == '') {
                        Ext.get('tdResetError').update("<s:text name="appbs.login.msg.passwd.empty"/>");
                        return;
                    }
                    if (form.clave.value != form.confirm.value) {
                        Ext.get('tdResetError').update("<s:text name="appbs.login.msg.passwd.different"/>");
                        return;
                    }
                    params = {id_usuario: form.id_usuario.value, clave: form.clave.value};
                } else
                    params = { usuario: form.usuario.value, clave: form.clave.value, email: form.email.value, remember: form.remember.checked }
                Ext.Ajax.request({
                    url: appPath+'/Login.action?action='+tipo,
                    scope: this,
                    params: params,
                    success: function(response) {
                        var params = getActionResponse(response);
                        if (params.success) {
                            if (tipo == 'login')
                                window.location = appPath+'/Index.action';
                            else if (tipo == 'recover')
                                Ext.get('tdRecoverError').update(params.mensaje);
                            else {
                                Ext.get('dLogin_reset').hide();
                                Ext.get('dLogin_reset_ok').show();
                            }
                        } else {
                            Ext.get(tipo == 'login' ? 'tdLoginError' : tipo == 'recover' ? 'tdRecoverError' : 'tdResetError').update(params.mensaje);
                        }
                    },
                    failure: function() {
                        mainDiv.unmask();
                        Ext.Msg.alert("<s:text name='appbs.error.server.title'/>", "<s:text name='appbs.error.server.msg'/>");
                    }
                });
            }
        </script>
    </s:if>


</head>

<body scroll="no">
<s:if test="#session.user == null || #session.user.perfil_tipo == 'ADMIN' || #session.user.perfil_tipo == 'MANAGER'">
    <div id="dMainContent">
        <div id="north" class="x-hide-display<s:if test="#session.user == null"> body_top</s:if>">
            <table width="100%" cellpadding="0" cellspacing="0" border="0">
                <tr>
                    <td><img src="iconos/logo_2.png"></td>
                    <td align="right">
                        <table cellspacing="2" border="0">
                            <tr style="background-color: transparent;">
                                <td class="username"><s:property value="#session.user.nombre"/></td>
                            </tr>
                            <tr style="background-color: transparent;">
                                <td align="right"><a href="javascript:loginOut();" class="identify"><s:text name="appbs.form.logout"/></a></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>

        <div id="center_empty" class="x-hide-display body_body">
            <div id="dHomeTop"></div>
            <div id="dHomeCenter">
                <div id="dLogin">
                    <s:form action="Login" name="Login" onsubmit="return false;">
                        <s:hidden name="id_usuario" />
                        <s:if test="%{show == 'reset'}">
                            <div id="dLogin_reset" style="padding-top: 20px; padding-left: 20px;">
                                <s:if test="%{actionResponse.success}">
                                    <table cellspacing="5" cellpadding="0" border="0" width="260">
                                        <tr>
                                            <td align="left" class="bold" colspan="2"><s:text name='login.change.caption'/></td>
                                        </tr><tr>
                                            <td align="left" class="bold" colspan="2"><s:property value='nombre'/></td>
                                        </tr><tr>
                                            <td colspan="2"></td>
                                        </tr><tr>
                                            <td align="right"><s:text name='login.clave'/>:</td>
                                            <td><s:password name="clave" cssStyle="width: 150px"/> </td>
                                        </tr><tr>
                                            <td align="right"><s:text name='login.confirm'/>:</td>
                                            <td><s:password name="confirm" cssStyle="width: 150px"/> </td>
                                        </tr><tr>
                                            <td></td>
                                            <td><button class="buttons" onclick="submitLogin(this.form, 'reset');"><s:text name="login.btn.change"/></button> </td>
                                        </tr><tr>
                                            <td colspan="2" id="tdResetError" class="login_error"></td>
                                        </tr>
                                    </table>
                                </s:if>
                                <s:else>
                                    <s:property value="actionResponse.mensaje"/>
                                </s:else>
                            </div>
                            <div id="dLogin_reset_ok" style="display:none; margin-top: -100px; padding-left: 20px;">
                                <s:text name="login.change.ok"/><br><br>
                                <button class="buttons" onclick="showLogin();"><s:text name="login.btn.login"/></button>
                            </div>
                        </s:if>
                        <s:else>
                            <div id="dLogin_check" style="padding-top: 20px; padding-left: 20px;">
                                <table cellspacing="5" cellpadding="0" border="0" width="260">
                                    <tr>
                                        <td align="right"><s:text name='login.usuario'/>:</td>
                                        <td><s:textfield name="usuario" cssStyle="width: 150px"/> </td>
                                    </tr><tr>
                                        <td align="right"><s:text name='login.clave'/>:</td>
                                        <td><s:password name="clave" cssStyle="width: 150px"/> </td>
                                    </tr><tr>
                                        <td></td>
                                        <td><s:checkbox name="remember"/><s:text name="login.remember"/> </td>
                                    </tr><tr>
                                        <td></td>
                                        <td><a href="javascript:recoverPassword();"><s:text name="login.forgot"/> </a> </td>
                                    </tr><tr>
                                        <td></td>
                                        <td><button class="buttons" onclick="submitLogin(this.form, 'login');"><s:text name="login.btn.login"/></button> </td>
                                    </tr><tr>
                                        <td id="tdLoginError" colspan="2" class="login_error"></td>
                                    </tr>
                                </table>
                            </div>
                            <div id="dLogin_recover" style="padding-top: 20px; padding-left: 20px; margin-top: -130px; display:none;" >
                                <table cellspacing="5" cellpadding="0" border="0" width="280">
                                    <tr>
                                        <td align="left"><s:text name='login.email'/>:</td>
                                    </tr><tr>
                                        <td><s:textfield name="email" cssStyle="width: 200px"/> </td>
                                    </tr><tr>
                                        <td><button class="buttons" onclick="submitLogin(this.form, 'recover');"><s:text name="login.btn.recover"/></button> </td>
                                    </tr><tr>
                                        <td id="tdRecoverError" class="login_error"></td>
                                    </tr>
                                </table>
                            </div>
                        </s:else>
                    </s:form>
                </div>
            </div>
            <div id="dHomeBottom"></div>
        </div>

        <div id="west" class="x-hide-display"></div>

        <div id="panelMainContent" class="x-hide-display"></div>

        <div id="messageForm" class="x-hide-display"></div>

   
    </div>
</s:if>

</body>
</html>