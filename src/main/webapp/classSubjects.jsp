<!DOCTYPE html>
<html lang="en">
<%@ page import="java.util.List" %>
<%@ page import="model.ClassSubject" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.concurrent.ThreadLocalRandom" %>


<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@include file="includes/header.jsp" %>


<body>

<%@include file="includes/topNav.jsp" %>

<div class="container">
    <h3 class='header'>Classes Report</h3>
    <%
        List<ClassSubject> classSubjectsList = (List<ClassSubject>) request.getAttribute("classSubjectsList");
        // Paint the rows of the subject table
        String classNameOriginal = "";
        int counter = 0;
        String[] imageArray = {"tech","nature","animals"};

        for (ClassSubject classSubject : classSubjectsList) {
            int randomCategory = ThreadLocalRandom.current().nextInt(0, 3);
            String className = classSubject.getClassName();

            if (!classNameOriginal.equals(className)) {
                if (counter != 0) {
                    out.println("                </div>");
                    out.println("            </div>");
                    out.println("        </div>");
                    out.println("    </div>");
                    out.println("</div>");
                }
                out.println("<div class=\"container\">");
                out.println("    <div class=\"col s12 m7\">");
                out.println("        <h5 class=\"header\">" + classSubject.getClassName() + "</h5>");
                out.println("        <div class=\"card horizontal\">");
                out.println("            <div class=\"card-image\">");
                out.println("<img src=\"https://placeimg.com/250/150/"+imageArray[randomCategory]+"\">");
                out.println("</div>");
                out.println("<div class=\"card-stacked\">");
                out.println("<div class=\"card-content\">");

                classNameOriginal = className;
                //Encabezado
            }
            out.println(classSubject.getSubjectName() + "<br />");
            //Lista

            //Footer
            counter++;
        }
    %>


</div>
</div>
</div>
</div>
</div>
</div>
<%@include file="includes/footer.jsp" %>
</body>
</html>

