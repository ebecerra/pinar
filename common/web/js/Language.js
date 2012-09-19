/*
 * This is a test file for Bundle.js
 *
 * @author: elmasse(c) Maximiliano Fierro 2008
 *
 */

var bundle;

	var lang;
	var params = Ext.urlDecode(window.location.search.substring(1));
	if(params.lang)
		lang = params.lang;

    bundle = new Ext.i18n.Bundle({bundle:'package', path:'', lang: lang});
	bundle.onReady(function(){
        //alert('Fichero idioma:'+bundle.getMsg('form_resonancia.fecha'));
	}); //bundle.onReady
