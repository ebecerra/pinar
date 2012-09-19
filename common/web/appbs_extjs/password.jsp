<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">

    // Variables que despues se van a acceder para validacion y el submit.
    var loginText;
    var pwdOldText;
    var pwdNewText;
    var pwdConfirmText;
    var errorLb;
    var changePasswordForm;
    var changePasswordWnd;

    Ext.onReady(function() {
        Ext.QuickTips.init();

        // Se crean los objetos
        loginText = new Ext.form.TextField({ fieldLabel:"<s:text name='appbs_password.login'/>", name: 'login', value: '', allowBlank:false });
        pwdOldText = new Ext.form.TextField({ fieldLabel:"<s:text name='appbs_password.old'/>", name: 'pw_old', value: '', inputType:'password', allowBlank:false });
        pwdNewText = new Ext.form.TextField({ fieldLabel:"<s:text name='appbs_password.new'/>", name: 'pw_new', value: '', inputType:'password', allowBlank:true });
        pwdConfirmText = new Ext.form.TextField({ fieldLabel:"<s:text name='appbs_password.confirm'/>", name: 'pw_confirm', value: '', inputType:'password', allowBlank:true });
        errorLb = new Ext.form.Label({ text:'', cls: "appbs_error" });

        changePasswordForm = new Ext.form.FormPanel({
            labelWidth:80,
            url:appPath+'/Password.action?action=submit',
            frame:true,
            title:"<s:text name='appbs_password.caption'/>",
            defaultType:'textfield',
            monitorValid:true,
            label_login:'usuario',
            items: [
                loginText
                ,pwdOldText
                ,pwdNewText
                ,pwdConfirmText
                ,errorLb
            ],
            buttons:[{
                    text:"<s:text name='appbs.btn.ok'/>",
                    formBind: true,
                    handler:function() {
                        var pw_new = pwdNewText.getValue();
                        var pw_confirm = pwdConfirmText.getValue();
                        if (pw_new != pw_confirm) {
                            errorLb.setText(params.item.err_confirm);
                            return;
                        }
                        if (passwordStrong(pw_new, params.item, errorLb)) {
                            errorLb.setText("");
                            changePasswordForm.getForm().submit({
                                method:'POST',
                                waitTitle: "<s:text name='appbs.wait'/>",
                                waitMsg:"<s:text name='appbs.wait.title'/>",
                                success:function() {
                                    showHint("<s:text name='appbs_password.info.caption'/>", "<s:text name='appbs_password.info.msg.ok'/>");
                                    mainDiv.unmask();
                                    changePasswordWnd.hide();
                                },
                                failure:function(form, action) {
                                    errorLb.setText(showErrorResponse(action.response));
                                }
                            });
                        }
                    }
                },
                {
                    text: "<s:text name='appbs.btn.cancel'/>",
                    handler:function() {
                        mainDiv.unmask();
                        changePasswordWnd.hide();
                    }
                }
            ]
        });

        changePasswordWnd = new Ext.Window({ width:300, frame:false, closable: false, resizable: false, items: [changePasswordForm] });
    });

    function formPassword() {
            Ext.Ajax.request({
            url: appPath+'/Password.action?action=params&d'+new Date().getMilliseconds(),
            success: function(response, opts) {
                params = getActionResponse(response);
                if (params.success) {
                    if (params.item.user) {
                        loginText.readonly = false;
                    }

                    // Para cambiar muchos valores en una sola llamada
                    changePasswordForm.getForm().setValues({
                        login: params.item.login,
                        pw_old: '',
                        pw_new: '',
                        pw_confirm:''
                    });

                    // Se muestra el dialogo
                    mainDiv.mask();
                    //alert('Mostrando form:'+changePasswordForm);
                    //alert('Mostrando wnd:'+changePasswordWnd);
                    changePasswordWnd.show();
                }
            },
            failure: function(response, opts) {
                showErrorResponse(response)
            }
        });
    }

</script>











