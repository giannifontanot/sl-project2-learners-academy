<!DOCTYPE html>
<html lang="en">
<%@ page import="java.util.List" %>
<%@ page import="model.Student" %>
<%@ page import="model.Clase" %>


<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@include file="includes/header.jsp" %>


<body>
<script>
    //Configuration of Modals
    document.addEventListener('DOMContentLoaded', function () {
        //Modal
        var elems = document.querySelectorAll('.modal');
        var options = {opacity: 0.5}//, onOpenStart: fOpenEdit};
        var instances = M.Modal.init(elems, options);
        var instance = M.Modal.getInstance(elems);

        //Select
        var elems = document.querySelectorAll('.select');
        var options = {classes: ""}
        var instances = M.FormSelect.init(elems, options);

    });
</script>
<%@include file="includes/topNav.jsp" %>


<div class="container">
    <h3>Students</h3>
    <table class="striped card-panel highlight">
        <thead>
        <tr>
            <th class="center-align">Edit</th>
            <th class="center-align">ID</th>
            <th class="center-align">Student Name</th>
            <th class="center-align">Class</th>
            <th class="center-align">Delete</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Student> studentsList = (List<Student>) request.getAttribute("studentsList");

            // Paint the rows of the student table
            for (Student student : studentsList) {
                out.println("");
                out.println("<tr><td class=\"center-align\">");
                out.println("<a class=\"modal-trigger\" href='javascript:fOpenEdit(\"" + student.getStudentId() +
                        "\")'>" +
                        "<i class=\"material-icons\">edit</i></a>");
                out.println("</td><td class=\"center-align\">");
                out.println(student.getStudentId());
                out.println("</td><td>");
                out.println(student.getStudentName());
                out.println("</td><td class=\"center-align\">");
                out.println(student.getClassId());
                out.println("</td><td class=\"center-align\">");
                out.println("<a href='javascript:fOpenModal(\"delete\", \"" + student.getStudentId() +
                        "\")'><i class=\"material-icons\">delete</i></a>");
                out.println("</td></tr>");
            }
        %>
        </tbody>
    </table>
</div>

<a id='modalLink' class="waves-effect waves-light btn hide modal-trigger" href="#modal1">Modal</a>
<!-- Modal Structure -->
<div id="modal1" class="modal">
    <div class="preloader-background" id="preloader">
        <div class="preloader-wrapper big active">
            <div class="spinner-layer spinner-blue-only">
                <div class="circle-clipper left">
                    <div class="circle"></div>
                </div>
                <div class="gap-patch">
                    <div class="circle"></div>
                </div>
                <div class="circle-clipper right">
                    <div class="circle"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="modal-content">
        <h4>Student Detail</h4>
        <div id="contenido">
            <form id="form1">
                <span>Student ID: <input type="text" id="studentId" name="studentId" value=""></span><br/>
                <span>Class Name:
                <div class="input-field col s12">
                    <select id="selectClass" name="selectClass" value="" class="select">
                        <option value="" disabled selected>Choose your option</option>
    <%
        List<Clase> classesList = (List<Clase>) request.getAttribute("classesList");

        // Paint the rows of the student table
        for (Clase clase : classesList) {
            out.println("<option value=\"" + clase.getClassId() + "\">");
            out.println(clase.getClassName());
            out.println("</option>");
        }
    %>
                    </select>
                    <label>Materialize Select</label>
                </div>
                </span><br/>
                Name: <input type="text" id="studentName" name="studentName" value=""><br/>
                <input type="text" id="action" name="action" value="">
                <button class="modal-close" type="submit">Enviar</button>
            </form>
        </div>
    </div>
    <div class="modal-footer">
        <a href="javascript:fSubmitForm();" class="modal-close waves-effect waves-green btn-flat">Dont click</a>
    </div>
</div>

<script>
    async function handleSubmit(event) {
        event.preventDefault();

        const data = new FormData(event.target);

        const oFormEntries = Object.fromEntries(data.entries());

        console.log("----> about to saveOneStudent: " + oFormEntries.toString());

        await saveOneStudent(oFormEntries);
    }

    const form = document.querySelector('#form1');
    form.addEventListener('submit', handleSubmit);
</script>


<!--  Scripts-->
<script>
    async function fOpenEdit(pId) {

        // Set the action
        document.getElementById("action").value = "updateOneStudent";

        // Trigger the Modal to open
        document.getElementById('modalLink').click();
        document.getElementById("preloader").style.display = "flex";

        fetch("http://localhost:8080/sl_project2_learners_academy/student-controller", {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            method: "POST",
            body: JSON.stringify({action: "fetchOneStudent", studentId: pId})

        }).then(res => res.text()).then(async (jsonString) => {
            console.log(" -------> fetch: " + jsonString);
            try {

                //Painting the values
                const jsonObject = JSON.parse(jsonString);
                document.querySelector("#studentId").value = jsonObject.studentId;
                document.querySelector("#classId").value = jsonObject.classId;
                document.querySelector("#studentName").value = jsonObject.studentName;
                await delay(1000);
                document.getElementById("preloader").style.display = "none";


            } catch (e) {
                // On Error
                console.log("ERROR: fetchOneStudent/querySelector - " + e);
            }

        });
    }

    //ACTION: updateOneStudent or saveNewStudent
    function saveOneStudent(oFormEntries) {
        console.log(" -----> saveOneStudent:" + "");
        console.log(" -----> action: " + document.getElementById('action').value);
        try {
            fetch("http://localhost:8080/sl_project2_learners_academy/student-controller", {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                method: "POST",
                body: JSON.stringify(oFormEntries)
            })
                .then(res => res.text()).then((jsonReturnString) => {
                console.log(" -------> studentjsp fetch: " + jsonReturnString);
                try {

                    //Answer received from the servlet
                    const jsonObject = JSON.parse(jsonReturnString);
                    M.toast({
                        html:
                            jsonObject.code === 0 ? jsonObject.message : "<table><tr><td class=\"center-align\">Student NOT UPDATED</td></tr><tr><td>CODE: " + jsonObject.code +
                                " - " +
                                jsonObject.message + "</td></tr></table>"
                    })
                } catch (e) {
                    // On Error
                    console.log("ERROR: fetchStudents/querySelector" + e);
                }

            });
        } catch (e) {
            console.log(e);
        }
    }
</script>

</body>
</html>

<%@include file="includes/footer.jsp" %>
