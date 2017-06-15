<%@ include file="header.jspf" %>

<%
    if (application.getAttribute("n") == null) {
        application.setAttribute("n", 1);
    }

    Integer n = (Integer) application.getAttribute("n");
    int i = n.intValue() + 1;
    application.setAttribute("n", Integer.valueOf(i));
    out.println("Aufrufe: " + n);
%>

<%-- Dies ist ein Kommentar --%>

<c:out value="Hallo"/>
<c:forEach items="Kirschlorbeer, Lavendel, Bambus" var="offer">${offer}</c:forEach>

<%@ include file="footer.jspf" %>


