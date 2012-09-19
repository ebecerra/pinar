/*
 *
 * Funciones de uso general
 *
 */

var app_error_title, app_error_server_title, app_msg_server_title, app_error_server_msg;
var app_done_title, app_done_msg, app_done_show, app_form_yes, app_form_no;
var app_week_days, app_today;

//------------------------------------------------------------------
// Pluggin Boolean renderer into formatter
//------------------------------------------------------------------
Ext.util.Format.Boolean = function(v) {
    return v == undefined ? '' : ((v == true || v == 'true' || v == 'yes' || v == 'on' || v == '1' || v == 'S' || v == 'Y') ? app_form_yes : app_form_no);
};

function formatBoolean(v) {
    return v == undefined ? '' : ((v == true || v == 'true' || v == 'yes' || v == 'on' || v == '1' || v == 'S' || v == 'Y') ? app_form_yes : app_form_no);
}

//------------------------------------------------------------------
// plug European currency renderer into formatter
//------------------------------------------------------------------
Ext.util.Format.Currency = function(v) {
    v = (Math.round((v-0)*100))/100;
    v = (v == Math.floor(v)) ? v + ".00" : ((v*10 == Math.floor(v*10)) ? v + "0" : v);
    return ('&euro; ' + v).replace(/\./, ',');
};

//------------------------------------------------------------------
// plug DateTime render into formatter
//------------------------------------------------------------------
function formatDateTime(value) {
    return value ? value.dateFormat('d/m/Y H:i:s') : '';
}

function formatDateTime2(value) {
    var d = new Date(value);
    return d ? d.dateFormat("d/m/Y H:i:s") : "";
}

function formatDateTime3(value) {
    if (!value) {
        return '';
    }
    var now = new Date();
    var d = now.clearTime(true);
    var notime = value.clearTime(true).getTime();
    if (notime == d.getTime()) {
        return app_today + " " + value.dateFormat('g:i a');
    }
    return value.dateFormat('d/m/y g:i a');
}

//------------------------------------------------------------------
// plug DateTime render into formatter
//------------------------------------------------------------------
function formatDate(value) {
    return value ? value.dateFormat('d/m/Y') : '';
}

//------------------------------------------------------------------
// plug DateTime render into formatter
//------------------------------------------------------------------
function formatTime(value) {
    return value ? value.dateFormat('H:i:s') : '';
}

//------------------------------------------------------------------
// plug Weekday render into formatter
//------------------------------------------------------------------
function formatWeekday(value) {
    if (value >= 0 && value < 7)
        return app_week_days[value];
    else
        return "--";
}

//------------------------------------------------------------------
// plug Float render into formatter
//------------------------------------------------------------------
function formatFloat(value) {
    return value.toFixed(2);
}

//------------------------------------------------------------------
// Muestra un mensaje con los errores devueltos
//------------------------------------------------------------------
function getActionResponse(action) {
    if (action.status == 200)
        return Ext.decode(action.responseText);
    else
        return null;
}

//------------------------------------------------------------------
// Muestra un mensaje con los errores devueltos
//------------------------------------------------------------------
function showErrorResponse(action) {
    var msg = "Unknow Error";
    if (action.status == 404)
        msg = "Error al cargar la URL:"+action.statusText;
    else {
        var obj = Ext.decode(action.responseText);
        if (obj != null)
            msg = obj.mensaje;
    }
    Ext.example.msg(app_error_title, msg);
    return msg;
}

//------------------------------------------------------------------
// Muestra un mensaje con los errores devueltos
//------------------------------------------------------------------
function showHint(caption, msg) {
    Ext.example.msg(caption, msg);
}

//------------------------------------------------------------------
// Adiciona un registro a un data store (en una una ventana nueva)
//------------------------------------------------------------------
function formGetNewRecord(form) {
    var data = {};
    var values = form.getForm().getValues();
    for (var val in values) {
        data[val] = null;
    }
    return new Ext.data.Record(data);
}

//------------------------------------------------------------------
// Adiciona un registro a un data store (en una una ventana nueva)
//------------------------------------------------------------------
function formAddRecord(win, form) {
    win.show();
    var emptyRecord = formGetNewRecord(form);
    form.getForm().loadRecord(emptyRecord);
    return emptyRecord;
}

//------------------------------------------------------------------
// Adiciona un registro a un data store (en una Grid)
//------------------------------------------------------------------
function gridNotEditAddRecord(grid) {
    var RecordType = grid.store.recordType;
    var newRecord = new RecordType();
    var rowIndex = grid.store.getTotalCount();
    var currentRecord = grid.getSelectionModel().getSelected();
    if (currentRecord) {
        rowIndex = grid.getStore().indexOf(currentRecord);
    }
    return newRecord;
}

function gridAddRecord(grid) {
    var newRecord = gridNotEditAddRecord(grid);
    var rowIndex = grid.store.getTotalCount();
    var currentRecord = grid.getSelectionModel().getSelected();
    if (currentRecord) {
        rowIndex = grid.getStore().indexOf(currentRecord);
    }
    grid.stopEditing();
    grid.store.insert(rowIndex, newRecord);
    grid.startEditing(rowIndex, 0);
    return newRecord;
}

//------------------------------------------------------------------
// Elimina un registro a un data store
//------------------------------------------------------------------
function gridDeleteRecord(grid, deleteItems, id) {
    grid.stopEditing();

    var selectedItems = grid.getSelectionModel().getSelections();

    for (var i = 0; i < selectedItems.length; i++) {
        var item = {};
        item[id] = selectedItems[i].get(id);

        deleteItems.push(item);
        grid.getStore().remove(selectedItems[i]);
    }
}

function gridDeleteRecord2(grid, deleteItems, id1, id2) {
    grid.stopEditing();

    var selectedItems = grid.getSelectionModel().getSelections();

    for (var i = 0; i < selectedItems.length; i++) {
        var item = {};
        item[id1] = selectedItems[i].get(id1);
        item[id2] = selectedItems[i].get(id2);

        deleteItems.push(item);
        grid.getStore().remove(selectedItems[i]);
    }
}

function gridDeleteRecord3(grid, deleteItems, id1, id2, id3) {
    grid.stopEditing();

    var selectedItems = grid.getSelectionModel().getSelections();

    for (var i = 0; i < selectedItems.length; i++) {
        var item = {};
        item[id1] = selectedItems[i].get(id1);
        item[id2] = selectedItems[i].get(id2);
        item[id3] = selectedItems[i].get(id3);

        deleteItems.push(item);
        grid.getStore().remove(selectedItems[i]);
    }
}

//------------------------------------------------------------------
// Obtiene los registros modificados
//------------------------------------------------------------------
function gridGetModifiedRecords(grid) {
    grid.stopEditing();
    var data = [];
    Ext.each(grid.getStore().getModifiedRecords(), function(record) {
        data.push(record.data);
    });
    return data;
}
//------------------------------------------------------------------
// Comprueba la fortaleza de una clave
//------------------------------------------------------------------
function passwordStrong(passwd, info, dst) {
    // TODO esto se puede hacer con la validacion NORMAL de los campos.
    if (passwd.length < info.min) {
        dst.setText(info.err_min);
        return false;
    }
    if (passwd.length > info.max) {
        dst.setText(info.err_max);
        return false;
    }
    var pattern1 = /[0-9]/;
    var pattern2 = /[a-zA-Z]/;
    var pattern3 = /[A-Z]/;
    var pattern4 = /[a-z]/;
    var pattern5 = /[@$%&_]/;
    switch (info.check) {
        case 2:
            if (passwd.match(pattern1) == null || passwd.match(pattern2) == null) {
                dst.setText(info.err_check2);
                return false;
            }
            break;

        case 3:
            if (passwd.match(pattern1) == null || passwd.match(pattern3) == null || passwd.match(pattern4) == null) {
                dst.setText(info.err_check3);
                return false;
            }
            break;

        case 4:
            if (passwd.match(pattern1) == null || passwd.match(pattern3) == null || passwd.match(pattern4) == null || passwd.match(pattern5) == null) {
                dst.setText(info.err_check4);
                return false;
            }
            break;
    }
    return true;
}

//------------------------------------------------------------------
// Callback de los Datasource (exception handler)
//------------------------------------------------------------------
var dataSourceLoadException = function() {
    Ext.Ajax.request({
        url: appPath+'/Usuarios.action?action=getuser',
        success: function (response) {
            var params = getActionResponse(response);
            if (params.success && params.properties.user == null) {
                redirectAction("/Index.action", "");
            } else
                Ext.Msg.show({ title: app_error_server_title, msg: app_error_server_msg, buttons: Ext.Msg.OK, icon: Ext.MessageBox.ERROR  });
        },
        failure: function() {
            Ext.Msg.show({ title: app_error_server_title, msg: app_error_server_msg, buttons: Ext.Msg.OK, icon: Ext.MessageBox.ERROR  });
        }
    });
};

//------------------------------------------------------------------
// Callback del "load" de los datasource
//------------------------------------------------------------------
var dataSourceLoad = function() {
    if (app_done_show)
        Ext.example.msg(app_done_title, app_done_msg);
};

//------------------------------------------------------------------
// Render para un numero real
//------------------------------------------------------------------
function formatNumber(v) {
    v = (Math.round((v-0)*100))/100;
    v = (v == Math.floor(v)) ? v + ".00" : ((v*10 == Math.floor(v*10)) ? v + "0" : v);
    return (""+v).replace(/\./, ',');
}

//------------------------------------------------------------------
// Muestra los valores de un formulario
//------------------------------------------------------------------
function showForm(form) {
    var s = '';
    Ext.iterate(form.form.getValues(), function(key, value) {
        s += String.format("{0} = {1}<br />", key, value);
    }, this);

    Ext.Msg.show({ title: 'Valores del formulario', msg: s, buttons: Ext.Msg.OK, icon: Ext.MessageBox.INFO  } );
}

function showHtmlForm(form) {
    var text = "";
    for (var i = 0; i < form.items.keys.length; i++) {
        text += printFormItem(form, form.findField(form.items.keys[i]));
    }
    alert(text);
}

function printFormItem(form, fld) {
    var text = "";
    if (fld.xtype == "compositefield") {
        for (var i = 0; i < fld.items.keys.length; i++) {
            text += printFormItem(form, form.findField(fld.items.keys[i]))
        }
    } else
        text += "Id.: " + fld.id + ", Name: " + fld.name + ", Type: " + fld.xtype + " = " + fld.value+"\n";
    return text;
}

//------------------------------------------------------------------
// Limpia un formulario
//------------------------------------------------------------------
function clearForm(form) {
    for (var i = 0; i < form.items.keys.length; i++) {
        clearFormItem(form, form.findField(form.items.keys[i]));
    }
}

function clearFormItem(form, fld) {
    if (fld.xtype == "compositefield") {
        for (var i = 0; i < fld.items.keys.length; i++) {
            clearFormItem(form, form.findField(fld.items.keys[i]))
        }
    } else if (fld.xtype == "checkbox")
        fld.setValue(false);
    else if (fld.xtype == "textfield" || fld.xtype == "datefield")
        fld.setValue("");
}

//----------------------------------------------------------------
// Redirecciona a una URL
//----------------------------------------------------------------
function redirect(url) {
    window.location.href = url;
}

function redirectAction(pag, args) {
    if (args != null && args.length > 0) {
        if (pag.indexOf('?') == -1)
            pag = pag + '?' + args;
        else
            pag = pag + '&' + args;
    }
    window.location.href = appPath+pag;
}

function openWindow(url) {
    window.open(url);
}

//----------------------------------------------------------------
// Muestra los errores del servidor
//----------------------------------------------------------------
function showServerError(response) {
    Ext.MessageBox.show({
       title: app_error_server_title,
       msg: response.errors ? response.errors[0].message : response.message,
       buttons: Ext.MessageBox.OK,
       icon: Ext.MessageBox.ERROR
    });
}

function showServerMessage(response) {
    Ext.MessageBox.show({
       title: app_msg_server_title,
       msg: response.errors ? response.errors[0].message : response.message,
       buttons: Ext.MessageBox.OK,
       icon: Ext.MessageBox.WARNING
    });
}

function showMessage(title, msg) {
    Ext.MessageBox.show({
        title: title,
        msg: msg,
       buttons: Ext.MessageBox.OK,
       icon: Ext.MessageBox.WARNING
    });
}

function showError(title, msg) {
    Ext.MessageBox.show({
       title: title,
       msg: msg,
       buttons: Ext.MessageBox.OK,
       icon: Ext.MessageBox.ERROR
    });
}

//----------------------------------------------------------------
// Emula el str_replace de PHP
//----------------------------------------------------------------
function str_replace(search, replace, subject) {
    var f = search, r = replace, s = subject;
    var ra = r instanceof Array, sa = s instanceof Array;
    f = [].concat(f); r = [].concat(r);
    var i = (s = [].concat(s)).length;

    while (j = 0, i--) {
        if (s[i]) {
            while (s[i] = s[i].split(f[j]).join(ra ? r[j] || "" : r[0]), ++j in f){};
        }
    }

    return sa ? s : s[0];
}

//----------------------------------------------------------------
// Validar C.I.F.
//      Retorna: 1 = NIF ok, 2 = CIF ok, 3 = NIE ok, -1 = NIF error, -2 = CIF error, -3 = NIE error, 0 = ??? error
//----------------------------------------------------------------
function check_Nif_Cif_Nie(value) {
    var temp = value.toUpperCase();
    var cadenadni="TRWAGMYFPDXBNJZSQVHLCKE";

    if (temp !== ''){
        //si no tiene un formato valido devuelve error
        if ((!/^[A-Z]{1}[0-9]{7}[A-Z0-9]{1}$/.test(temp) && !/^[T]{1}[A-Z0-9]{8}$/.test(temp)) && !/^[0-9]{8}[A-Z]{1}$/.test(temp)) {
            return 0;
        }

        //comprobacion de NIFs estandar
        if (/^[0-9]{8}[A-Z]{1}$/.test(temp)) {
            posicion = value.substring(8,0) % 23;
            letra = cadenadni.charAt(posicion);
            var letradni=temp.charAt(8);
            return letra == letradni ? 1 : -1;
        }

        //algoritmo para comprobacion de codigos tipo CIF
        suma = parseInt(value[2])+parseInt(value[4])+parseInt(value[6]);
        for (i = 1; i < 8; i += 2)	{
            temp1 = 2 * parseInt(value[i]);
            temp1 += '';
            temp1 = temp1.substring(0,1);
            temp2 = 2 * parseInt(value[i]);
            temp2 += '';
            temp2 = temp2.substring(1,2);
            if (temp2 == '') {
                temp2 = '0';
            }
            suma += (parseInt(temp1) + parseInt(temp2));
        }
        suma += '';
        n = 10 - parseInt(suma.substring(suma.length-1, suma.length));

        //comprobacion de NIFs especiales (se calculan como CIFs)
        if (/^[KLM]{1}/.test(temp))	{
            return (value[8] == String.fromCharCode(64 + n)) ? 1 : -1;
        }

        //comprobacion de CIFs
        if (/^[ABCDEFGHJNPQRSUVW]{1}/.test(temp)) {
            temp = n + '';
            return (value[8] == String.fromCharCode(64 + n) || value[8] == parseInt(temp.substring(temp.length-1, temp.length))) ? 2 : -2;
        }

        //comprobacion de NIEs
        //T
        if (/^[T]{1}/.test(temp)) {
            return (value[8] == /^[T]{1}[A-Z0-9]{8}$/.test(temp)) ? 3 : -3;
        }

        //XYZ
        if (/^[XYZ]{1}/.test(temp))	{
            pos = str_replace(['X', 'Y', 'Z'], ['0','1','2'], temp).substring(0, 8) % 23;
            return (value[8] == cadenadni.substring(pos, pos + 1)) ? 3 : -3;
        }
    }

    return 0;
}

//--------------------------------------------------------------------
// Valida una cuenta bancanria
//--------------------------------------------------------------------
function checkBankAccount(i_entidad, i_oficina, i_digito, i_cuenta) {
    var wtotal,wcociente, wresto;
    if (i_entidad.length != 4 || i_oficina.length != 4 || i_digito.length != 2 || i_cuenta.length != 10)
        return false;
    wtotal = i_entidad.charAt(0) * 4;
    wtotal += i_entidad.charAt(1) * 8;
    wtotal += i_entidad.charAt(2) * 5;
    wtotal += i_entidad.charAt(3) * 10;
    wtotal += i_oficina.charAt(0) * 9;
    wtotal += i_oficina.charAt(1) * 7;
    wtotal += i_oficina.charAt(2) * 3;
    wtotal += i_oficina.charAt(3) * 6;
    // busco el resto de dividir wtotal entre 11
    wcociente = Math.floor(wtotal / 11);
    wresto = wtotal - (wcociente * 11);
    wtotal = 11 - wresto;
    if (wtotal == 11)
        wtotal = 0;
    if (wtotal == 10)
        wtotal = 1;
    if (wtotal != i_digito.charAt(0))
        return false;
    //hemos validado la entidad y oficina
    //-----------------------------------
    wtotal = i_cuenta.charAt(0) * 1;
    wtotal += i_cuenta.charAt(1) * 2;
    wtotal += i_cuenta.charAt(2) * 4;
    wtotal += i_cuenta.charAt(3) * 8;
    wtotal += i_cuenta.charAt(4) * 5;
    wtotal += i_cuenta.charAt(5) * 10;
    wtotal += i_cuenta.charAt(6) * 9;
    wtotal += i_cuenta.charAt(7) * 7;
    wtotal += i_cuenta.charAt(8) * 3;
    wtotal += i_cuenta.charAt(9) * 6;

    // busco el resto de dividir wtotal entre 11
    wcociente = Math.floor(wtotal / 11);
    wresto = wtotal - (wcociente * 11);
    wtotal = 11 - wresto;
    if (wtotal == 11){wtotal=0;}
    if (wtotal == 10){wtotal=1;}

    if (wtotal != i_digito.charAt(1))
        return false;
    // hemos validado la cuenta corriente
    return true;
}

//----------------------------------------------------------------
// Graba informacion del Log del usuario en el servidor
//----------------------------------------------------------------
function writeUserLog(id_tipo, nivel, text1, text2, text3, text4, text5) {
    var data = [];
    data.push({
        id_tipo: id_tipo, nivel: nivel,
        texto1: text1, texto2: text2, texto3: text3, texto4: text4, texto5: text5
    });
    Ext.Ajax.request({
        url: appPath+'/BaseAction.action?action=logUser',
        params: { loginfo: Ext.encode(data) }
    });
}

//----------------------------------------------------------------
// Pone los separadores de miles
//----------------------------------------------------------------
function putNumberSep(nStr) {
    nStr += '';
    x = nStr.split('.');
    x1 = x[0];
    x2 = x.length > 1 ? '.' + x[1] : '';
    var rgx = /(\d+)(\d{3})/;
    while (rgx.test(x1)) {
        x1 = x1.replace(rgx, '$1' + ',' + '$2');
    }
    return x1 + x2;
}
