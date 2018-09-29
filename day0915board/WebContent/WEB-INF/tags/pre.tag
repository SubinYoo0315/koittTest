<%@tag body-content="empty" pageEncoding="utf-8" %>
<%@tag trimDirectiveWhitespaces="true" %>
<%@attribute name="value" type="java.lang.String" required="true"%>
<%value = value.replace("&", "%amp;").replace("<", "&lt").replace(">", "&gt").replace("\r\n", "<br>").replace(" ", "&nbsp"); %>
<%=value %>