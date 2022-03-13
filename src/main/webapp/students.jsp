<% %>
<%@ page import="java.util.List" %>
<%@ page import="model.Student" %>


<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@include file="includes/header.jsp" %>


<body>
<%@include file="includes/topNav.jsp" %>


<div class="container">
    <h3>Students</h3>
    <table class="striped card-panel highlight">
        <thead>
        <tr>
            <th>Edit</th>
            <th>Student Name</th>
            <th>Delete</th>
        </tr>
        </thead>

        <tbody>
        <%
            List<Student> studentsList = (List<Student>) request.getAttribute("studentsList");

            for (Student student : studentsList) {
                out.println("");
                out.println("<tr><td>");
                out.println("<a class=\"modal-trigger\" href='javascript:fOpenEdit(\"" +
                        student.getStudentId() + "\")'><i class=\"material-icons\">edit</i></a>");
                out.println("</td><td>");
                out.println(student.getStudentName());
                out.println("</td><td>");
                out.println("<a href='javascript:fOpenDelete(\"" + student.getStudentId() + "\")'><i class=\"material-icons\">delete</i></a>");
                out.println("</td></tr>");
            }
        %>
        </tbody>
    </table>
</div>
<a href="https://www.flaticon.com/free-icons/edit" title="edit icons">Edit icons created by Freepik - Flaticon</a>


<script>

</script>
<a id='modalLink' class="waves-effect waves-light btn modal-trigger" href="#modal1">Modal</a>
<!-- Modal Structure -->
<div id="modal1" class="modal">
<%--    <div class="preloader-background">--%>
<%--        <div class="preloader-wrapper big active">--%>
<%--            <div class="spinner-layer spinner-blue-only">--%>
<%--                <div class="circle-clipper left">--%>
<%--                    <div class="circle"></div>--%>
<%--                </div>--%>
<%--                <div class="gap-patch">--%>
<%--                    <div class="circle"></div>--%>
<%--                </div>--%>
<%--                <div class="circle-clipper right">--%>
<%--                    <div class="circle"></div>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>
    <div class="modal-content">
        <h4>Student Detail</h4>
        <div id="contenido">
            <form name="form1">
                <span>Student ID: <input type="text" id="studentId" name="studentId" value=""></span><br/>
                Class ID: <input type="text" id="classId" name="classId" value=""><br/>
                Name: <input type="text" id="studentName" name="studentName" value=""><br/>
                <input type="hidden" id="action" name="action" value="">
                <button>enviar</button>
            </form>
        </div>
    </div>
    <div class="modal-footer">
        <a href="#!" class="modal-close waves-effect waves-green btn-flat">Agree</a>
    </div>
</div>


<!--  Scripts-->
<script>
    //Configuration of Modals
    document.addEventListener('DOMContentLoaded', function () {
        var elems = document.querySelectorAll('.modal');
        var options = {opacity: 0.5}//, onOpenStart: fOpenEdit};
        var instances = M.Modal.init(elems, options);
        var instance = M.Modal.getInstance(elems);
        //instance.open();
        //


    });


    async function fOpenEdit(id) {
        console.log(" -------> fOpenEdit: " + id);
        // Trigger the Modal to open
        document.getElementById('modalLink').click();
        //Fill the data
        await fetchOneStudent(id);


    }

     function fetchOneStudent(id) {

        //First we build the JSON from the form
         const data = new FormData();


        fetch("http://localhost:8080/sl_project2_learners_academy/student-controller", {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            method: "POST",
            body: JSON.stringify({studentId: id})
        })
            .then(res => res.text()).then((jsonString) => {
            console.log(" -------> fetch: " + jsonString);
            try {

                const jsonObject = JSON.parse(jsonString);
                document.querySelector("#studentId").value = jsonObject.studentId;
                document.querySelector("#classId").value = jsonObject.classId;
                document.querySelector("#studentName").value = jsonObject.studentName;

            } catch (e) {
                // On Error
                console.log("fetchStudents/querySelector" + e);
            }

        });
    }
</script>

<script>
    // NOTE: RUN WITH HTTP://, NOT FILE://
    window.addEventListener("load", () => {

    });
</script>


<button onclick="javascript:\" fSpinner();\"">fSpinner();</button>

<script>
    function fSpinner() {

        $('.preloader-background').delay(1700).fadeOut('slow');
        //
        $('.preloader-wrapper').delay(1700).fadeOut();

    }
</script>
</body>
</html>

<%@include file="includes/footer.jsp" %>
