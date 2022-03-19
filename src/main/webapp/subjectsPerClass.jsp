<!DOCTYPE html>
<html lang="en">
<%@ page import="java.util.List" %>
<%@ page import="model.Subject" %>
<%@ page import="model.Clase" %>
<%@ page import="model.Teacher" %>


<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@include file="includes/header.jsp" %>


<body>

<%@include file="includes/topNav.jsp" %>

<br />
<div class="container">
    <div class="col s12 m7">
        <h2 class="header">Subjects</h2>
        <div class="card horizontal">
            <div class="card-image">
                <img src="https://placeimg.com/250/450/nature">
            </div>
            <div class="card-stacked">
                <div class="card-content">
    <table class="striped card-panel highlight" id="myTable">
        <thead>
        <tr>
            <th class="center-align">Edit</th>
            <th class="center-align">ID</th>
            <th class="center-align">Subject Name</th>
            <th class="center-align">Teacher</th>
            <th class="center-align">Delete</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Subject> subjectsList = (List<Subject>) request.getAttribute("subjectsList");

            // Paint the rows of the subject table
            for (Subject subject : subjectsList) {
                out.println("");
                out.println("<tr><td class=\"center-align\">");
                out.println("<a class=\"modal-trigger\" href='javascript:fOpenEdit(\"" + subject.getSubjectId() +
                        "\")'>" +
                        "<i class=\"material-icons\">edit</i></a>");
                out.println("</td><td class=\"center-align\">");
                out.println(subject.getSubjectId());
                out.println("</td><td>");
                out.println(subject.getSubjectName());
                out.println("</td><td class=\"center-align\">");
                out.println(subject.getTeacherName());
                out.println("</td><td class=\"center-align\">");
                out.println("<a href='javascript:fOpenDelete(\"" + subject.getSubjectId() +
                        "\")'><i class=\"material-icons\">delete</i></a>");
                out.println("</td></tr>");
            }
        %>
        </tbody>
    </table>


                </div>
                <div class="card-action">
                    <div class="col-md-12 center text-center">
                        <span class="left" id="total_reg"></span>
                        <ul class="pagination pager" id="myPager"></ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <a class="btn-floating btn-large right waves-effect waves-light red" href="javascript:fOpenNew();"><i class="material-icons">add</i></a>
</div>
<a id='modalLink' class="waves-effect waves-light btn hide modal-trigger" href="#modal1">Modal</a>
<!-- Modal Structure -->
<div id="modal1" class="modal">
    <form id="form1" class="col s12">
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
        <h4>Subject Detail</h4>
        <div id="contenido">
                <div class="row">
                    <div class="input-field col s12">
                        <input type="text" id="subjectId" name="subjectId" value="">
                        <label for="subjectId" class="active">Subject</label>
                    </div>
                </div>
                <div class="row">
                    <div class="input-field col s12">
                        <select id="teacherId" name="teacherId" class="select">
                            <option value="" disabled selected>Select the subject's teacher</option>

    <%
        List<Teacher> teachersList = (List<Teacher>) request.getAttribute("teachersList");

        // Paint the rows of the subject table
        for (Teacher teacher : teachersList) {
            out.println("<option value=\"" + teacher.getTeacherId() + "\">");
            out.println(teacher.getTeacherName());
            out.println("</option>");
        }
    %>
                        </select>
                        <label for="teacherId" class="active">Subject's Teacher</label>
                    </div>
                </div>
                <div class="row">
                    <div class="input-field col s12">
                        <input type="text" id="subjectName" name="subjectName" value="">
                        <label for="subjectName" class="active">Subject Name</label>
                </div>
            </div>
                <input type="hidden" id="action" name="action" value="">
                <input type="hidden" id="deleteSubjectId" name="deleteSubjectId" value="">

        </div>
    </div>
    <div class="modal-footer">
        <button class="modal-close waves-effect waves-light btn accent-color" type="submit"><i class="material-icons left">save
        </i>Save Changes</button>
    </div>
    </form>
</div>

<script>
    async function handleSubmit(event) {
        event.preventDefault();

        const data = new FormData(event.target);
        const oFormEntries = Object.fromEntries(data.entries());
        console.log("----> about to saveOneSubject: " + oFormEntries.toString());
        await saveOneSubject(oFormEntries);
    }

    const form = document.querySelector('#form1');
    form.addEventListener('submit', handleSubmit);
</script>


<!--  Scripts-->
<script>
    async function fOpenEdit(pId) {

        // Set the action
        document.getElementById("action").value = "updateOneSubject";

        // Trigger the Modal to open
        document.getElementById('modalLink').click();
        document.getElementById("preloader").style.display = "flex"; // this centers the spinner in the modal window

        fetch("http://localhost:8080/sl_project2_learners_academy/subject-controller", {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            method: "POST",
            body: JSON.stringify({action: "fetchOneSubject", subjectId: pId})

        }).then(res => res.text()).then(async (jsonString) => {
            console.log(" -------> fetch: " + jsonString);
            try {

                //Painting the values
                const jsonObject = JSON.parse(jsonString);
                console.log("---> jsonString: " + jsonString);
                document.querySelector("#subjectId").value = jsonObject.subjectId;
                document.querySelector("#subjectName").value = jsonObject.subjectName;
                // Display the modal, hide the spinner
                //document.querySelector("#teacherId").value = jsonObject.teacherId;
                const classEl = document.getElementById("teacherId");

                for(var i=0; i<classEl.options.length; i++){
                    if(classEl.options[i].value == jsonObject.teacherId){
                        console.log(classEl.options[i].value + " - " + jsonObject.teacherId);
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
                console.log("ERROR: fetchOneSubject/querySelector - " + e);
            }

        });
    }

    async function fOpenDelete(pId) {

        // Set the action
        document.getElementById("action").value = "deleteOneSubject";

        // Trigger the Modal to open
        document.getElementById('modalLink').click();
        document.getElementById("preloader").style.display = "flex"; // this centers the spinner in the modal window

        fetch("http://localhost:8080/sl_project2_learners_academy/subject-controller", {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            method: "POST",
            body: JSON.stringify({action: "fetchOneSubject", subjectId: pId})

        }).then(res => res.text()).then(async (jsonString) => {
            console.log(" -------> fetch: " + jsonString);
            try {

                //Painting the values AND disabling the controls
                const jsonObject = JSON.parse(jsonString);
                console.log("---> jsonString: " + jsonString);
                document.querySelector("#subjectId").value = jsonObject.subjectId;
                document.querySelector("#subjectName").value = jsonObject.subjectName;
                // Display the modal, hide the spinner
                //document.querySelector("#teacherId").value = jsonObject.teacherId;
                const teacherEl = document.getElementById("teacherId");

                for(var i=0; i<teacherEl.options.length; i++){
                    if(teacherEl.options[i].value == jsonObject.teacherId){
                        console.log(teacherEl.options[i].value + " - " + jsonObject.teacherId);
                        teacherEl.options.selectedIndex = i;
                    }
                }
                //Re-initialize the select controls
                teacherEl.disabled = true;
                M.FormSelect.init(document.querySelectorAll('.select'), {classes: ""});
                M.updateTextFields();

                // Disabling the controls
                document.querySelector("#subjectId").disabled  = true;
                document.querySelector("#subjectName").disabled  = true;

                //Setting the Id to delete
                document.querySelector("#deleteSubjectId").value = jsonObject.subjectId;
                await delay(1000);
                document.getElementById("preloader").style.display = "none";

            } catch (e) {
                // On Error
                console.log("ERROR: fetchOneSubject/querySelector - " + e);
            }

        });
    }

    async function fOpenNew() {

        // Set the action
        document.getElementById("action").value = "saveNewSubject";

        //Clean and enabling the controls
        document.querySelector("#subjectId").disabled  = false;
        document.querySelector("#subjectName").disabled  = false;
        document.getElementById("teacherId").disabled = false;

        M.FormSelect.init(document.querySelectorAll('.select'), {classes: ""});
        M.updateTextFields();

        // Trigger the Modal to open
        document.getElementById('modalLink').click();
        document.getElementById("preloader").style.display = "none";

    }

    //ACTION: updateOneSubject or saveNewSubject
    function saveOneSubject(oFormEntries) {
        console.log(" -----> saveOneSubject:" + "");
        console.log(" -----> action: " + document.getElementById('action').value);
        try {
            fetch("http://localhost:8080/sl_project2_learners_academy/subject-controller", {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                method: "POST",
                body: JSON.stringify(oFormEntries)
            })
                .then(res => res.text()).then((jsonReturnString) => {
                console.log(" -------> subjectjsp fetch: " + jsonReturnString);
                try {

                    //Answer received from the servlet
                    const jsonObject = JSON.parse(jsonReturnString);
                    M.toast({
                        html:
                            jsonObject.code === 0 ? jsonObject.message : "<table><tr><td class=\"center-align\">Subject NOT UPDATED</td></tr><tr><td>CODE: " + jsonObject.code +
                                " - " +
                                jsonObject.message + "</td></tr></table>"
                        , completeCallback: function(){location.reload()}
                    })
                } catch (e) {
                    // On Error
                    console.log("ERROR: fetchSubjects/querySelector" + e);
                }

            });
        } catch (e) {
            console.log(e);
        }
    }

    //ACTION: updateOneSubject or saveNewSubject
    function deleteOneSubject(oFormEntries) {
        console.log(" -----> deleteOneSubject:" + "");
        console.log(" -----> action: " + document.getElementById('action').value);
        try {
            fetch("http://localhost:8080/sl_project2_learners_academy/subject-controller", {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                method: "POST",
                body: JSON.stringify(oFormEntries)
            })
                .then(res => res.text()).then((jsonReturnString) => {
                console.log(" -------> subjectjsp fetch: " + jsonReturnString);
                try {

                    //Answer received from the servlet
                    const jsonObject = JSON.parse(jsonReturnString);
                    M.toast({
                        html:
                            jsonObject.code === 0 ? jsonObject.message : "<table><tr><td class=\"center-align\">Subject NOT UPDATED</td></tr><tr><td>CODE: " + jsonObject.code +
                                " - " +
                                jsonObject.message + "</td></tr></table>"
                        , completeCallback: function(){location.reload()}
                    })
                } catch (e) {
                    // On Error
                    console.log("ERROR: fetchSubjects/querySelector" + e);
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

