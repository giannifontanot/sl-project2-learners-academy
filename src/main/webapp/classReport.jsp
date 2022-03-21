<!DOCTYPE html>
<html lang="en">
<%@ page import="java.util.List" %>
<%@ page import="model.Student" %>
<%@ page import="model.Clase" %>
<%@ page import="model.ClassFull" %>


<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@include file="includes/header.jsp" %>


<body>

<%@include file="includes/topNav.jsp" %>

<br /><br />
<div class="container">
    <h3>Class Report</h3>
    <br />
    <table class="striped card-panel highlight">
        <thead>
        <tr>
            <th class="center-align">Class Id</th>
            <th class="center-align">Class Name</th>
            <th class="center-align">Student Id</th>
            <th class="center-align">Student Name</th>
<%--            <th class="center-align">Teacher Id</th>--%>
            <th class="center-align">Teacher Name</th>
            <th class="center-align">Subject</th>
            <th class="center-align">Subject CODE</th>

        </tr>
        </thead>
        <tbody>
        <%
            List<ClassFull> studentsList = (List<ClassFull>) request.getAttribute("studentsList");

            // Paint the rows of the student table
            for (ClassFull student : studentsList) {
                out.println("<tr>");
                out.println("<td class=\"center-align\">"+student.getClassId()+"</td>");
                out.println("<td class=\"center-align\">"+student.getClassName()+"</td>");
                out.println("<td class=\"center-align\">"+student.getStudentId()+"</td>");
                out.println("<td class=\"center-align\">"+student.getStudentName()+"</td>");
//                out.println("<td class=\"center-align\">"+student.getTeacherId()+"</td>");
                out.println("<td class=\"center-align\">"+student.getTeacherName()+"</td>");
                out.println("<td class=\"center-align\">"+student.getSubjectName()+"</td>");
                out.println("<td class=\"center-align\">"+student.getSubjectId()+"</td>");

                out.println("</tr>");
            }
        %>
        </tbody>
    </table>

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
        document.getElementById("preloader").style.display = "flex"; // this centers the spinner in the modal window

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
                console.log("---> jsonString: " + jsonString);
                document.querySelector("#studentId").value = jsonObject.studentId;
                document.querySelector("#studentName").value = jsonObject.studentName;
                // Display the modal, hide the spinner
                //document.querySelector("#classId").value = jsonObject.classId;
                const classEl = document.getElementById("classId");

                for(var i=0; i<classEl.options.length; i++){
                    if(classEl.options[i].value == jsonObject.classId){
                        console.log(classEl.options[i].value + " - " + jsonObject.classId);
                        classEl.options.selectedIndex = i;
                    }
                }
                //Re-initialize the select controls
                M.FormSelect.init(document.querySelectorAll('.select'), {classes: ""});
                M.updateTextFields();



                await delay(1000);
                document.getElementById("preloader").style.display = "none";

            } catch (e) {
                // On Error
                console.log("ERROR: fetchOneStudent/querySelector - " + e);
            }

        });
    }

    async function fOpenDelete(pId) {

        // Set the action
        document.getElementById("action").value = "deleteOneStudent";

        // Trigger the Modal to open
        document.getElementById('modalLink').click();
        document.getElementById("preloader").style.display = "flex"; // this centers the spinner in the modal window

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

                //Painting the values AND disabling the controls
                const jsonObject = JSON.parse(jsonString);
                console.log("---> jsonString: " + jsonString);
                document.querySelector("#studentId").value = jsonObject.studentId;
                document.querySelector("#studentName").value = jsonObject.studentName;
                // Display the modal, hide the spinner
                //document.querySelector("#classId").value = jsonObject.classId;
                const classEl = document.getElementById("classId");

                for(var i=0; i<classEl.options.length; i++){
                    if(classEl.options[i].value == jsonObject.classId){
                        console.log(classEl.options[i].value + " - " + jsonObject.classId);
                        classEl.options.selectedIndex = i;
                    }
                }
                //Re-initialize the select controls
                classEl.disabled = true;
                M.FormSelect.init(document.querySelectorAll('.select'), {classes: ""});
                M.updateTextFields();

                // Disabling the controls
                document.querySelector("#studentId").disabled  = true;
                document.querySelector("#studentName").disabled  = true;

                //Setting the Id to delete
                document.querySelector("#deleteStudentId").value = jsonObject.studentId;
                await delay(1000);
                document.getElementById("preloader").style.display = "none";

            } catch (e) {
                // On Error
                console.log("ERROR: fetchOneStudent/querySelector - " + e);
            }

        });
    }

    async function fOpenNew() {

        // Set the action
        document.getElementById("action").value = "saveNewStudent";

        //Clean and enabling the controls
        document.querySelector("#studentId").disabled  = false;
        document.querySelector("#studentName").disabled  = false;
        document.getElementById("classId").disabled = false;

        M.FormSelect.init(document.querySelectorAll('.select'), {classes: ""});
        M.updateTextFields();

        // Trigger the Modal to open
        document.getElementById('modalLink').click();
        document.getElementById("preloader").style.display = "none";

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
                        , completeCallback: function(){location.reload()}
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

    //ACTION: updateOneStudent or saveNewStudent
    function deleteOneStudent(oFormEntries) {
        console.log(" -----> deleteOneStudent:" + "");
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
                        , completeCallback: function(){location.reload()}
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

<%@include file="includes/footer.jsp" %>
</body>
</html>

