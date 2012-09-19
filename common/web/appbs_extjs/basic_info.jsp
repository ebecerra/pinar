<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">
    appPath = "<%=request.getContextPath()%>";
    appBasicPath = "<%=request.getContextPath()%>";
    app_error_title = "<s:text name='appbs.error.title'/>";
    app_error_server_title = "<s:text name='appbs.error.server.title'/>";
    app_error_server_msg = "<s:text name='appbs.error.server.msg'/>";
    app_done_title = "<s:text name='appbs.done.title'/>";
    app_done_msg = "<s:text name='appbs.done.msg'/>";
    app_form_yes = "<s:text name='appbs.form.yes'/>";
    app_form_no = "<s:text name='appbs.form.no'/>";
    app_today = "<s:text name="appbs.today"/>";
    app_done_show = false;

    app_week_days = new Array();
    app_week_days[0] = "<s:text name='appbs.week.days.0'/>";
    app_week_days[1] = "<s:text name='appbs.week.days.1'/>";
    app_week_days[2] = "<s:text name='appbs.week.days.2'/>";
    app_week_days[3] = "<s:text name='appbs.week.days.3'/>";
    app_week_days[4] = "<s:text name='appbs.week.days.4'/>";
    app_week_days[5] = "<s:text name='appbs.week.days.5'/>";
    app_week_days[6] = "<s:text name='appbs.week.days.6'/>";

    Ext.BLANK_IMAGE_URL = 'ext-3.3.1/resources/images/default/s.gif';
</script>
