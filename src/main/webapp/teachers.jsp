<!DOCTYPE html>
<html lang="en">
<%@ page import="java.util.List" %>
<%@ page import="model.Teacher" %>
<%@ page import="model.Clase" %>


<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@include file="includes/header.jsp" %>


<body>

<%@include file="includes/topNav.jsp" %>

<br /><br />
<div class="container">
    <div class="col s12 m7">
        <h2 class="header">Teachers</h2>
        <div class="card horizontal">
            <div class="card-image">
                <img src="https://placeimg.com/250/250/nature">
            </div>
            <div class="card-stacked">
                <div class="card-content">

                    <table class="striped card-panel highlight" id="myTable">
                        <thead>
                        <tr>
                            <th class="center-align">Edit</th>
                            <th class="center-align">ID</th>
                            <th class="center-align">&nbsp;</th>
                            <th class="center-align">Teacher Name</th>
                            <th class="center-align">Delete</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            List<Teacher> teachersList = (List<Teacher>) request.getAttribute("teachersList");

                            // Paint the rows of the teacher table
                            for (Teacher teacher : teachersList) {
                                out.println("");
                                out.println("<tr><td class=\"center-align\">");
                                out.println("<a class=\"modal-trigger\" href='javascript:fOpenEdit(\"" + teacher.getTeacherId() +
                                        "\")'>" +
                                        "<i class=\"material-icons\">edit</i></a>");
                                out.println("</td><td class=\"center-align\">");
                                out.println(teacher.getTeacherId());
                                out.println("</td><td>");
                                out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                out.println("</td><td>");
                                out.println(teacher.getTeacherName());
                                out.println("</td><td class=\"center-align\">");
                                out.println("<a href='javascript:fOpenDelete(\"" + teacher.getTeacherId() +
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
        <h4>Teacher Detail</h4>
        <div id="contenido">
                <div class="row">
                    <div class="input-field col s12">
                        <input type="text" id="teacherId" name="teacherId" value="">
                        <label for="teacherId" class="active">Teacher</label>
                    </div>
                </div>
            <div class="row">
                <div class="input-field col s12">
                    <input type="text" id="teacherName" name="teacherName" value="">
                    <label for="teacherName" class="active">Teacher Name</label>
                </div>
            </div>
            <input type="hidden" id="action" name="action" value="">
            <input type="hidden" id="deleteTeacherId" name="deleteTeacherId" value="">

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
        console.log("----> about to saveOneTeacher: " + oFormEntries.toString());
        await saveOneTeacher(oFormEntries);
    }

    const form = document.querySelector('#form1');
    form.addEventListener('submit', handleSubmit);
</script>


<!--  Scripts-->
<script>
    async function fOpenEdit(pId) {

        // Set the action
        document.getElementById("action").value = "updateOneTeacher";

        // Trigger the Modal to open
        document.getElementById('modalLink').click();
        document.getElementById("preloader").style.display = "flex"; // this centers the spinner in the modal window

        fetch("http://localhost:8080/sl_project2_learners_academy/teacher-controller", {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            method: "POST",
            body: JSON.stringify({action: "fetchOneTeacher", teacherId: pId})

        }).then(res => res.text()).then(async (jsonString) => {
            console.log(" -------> fetch: " + jsonString);
            try {

                //Painting the values
                const jsonObject = JSON.parse(jsonString);
                console.log("---> jsonString: " + jsonString);
                document.querySelector("#teacherId").value = jsonObject.teacherId;
                document.querySelector("#teacherName").value = jsonObject.teacherName;
                // Display the modal, hide the spinner
                M.updateTextFields();

                await delay(1000);
                document.getElementById("preloader").style.display = "none";

            } catch (e) {
                // On Error
                console.log("ERROR: fetchOneTeacher/querySelector - " + e);
            }

        });
    }

    async function fOpenDelete(pId) {

        // Set the action
        document.getElementById("action").value = "deleteOneTeacher";

        // Trigger the Modal to open
        document.getElementById('modalLink').click();
        document.getElementById("preloader").style.display = "flex"; // this centers the spinner in the modal window

        fetch("http://localhost:8080/sl_project2_learners_academy/teacher-controller", {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            method: "POST",
            body: JSON.stringify({action: "fetchOneTeacher", teacherId: pId})

        }).then(res => res.text()).then(async (jsonString) => {
            console.log(" -------> fetch: " + jsonString);
            try {

                //Painting the values AND disabling the controls
                const jsonObject = JSON.parse(jsonString);
                console.log("---> jsonString: " + jsonString);
                document.querySelector("#teacherId").value = jsonObject.teacherId;
                document.querySelector("#teacherName").value = jsonObject.teacherName;
                // Display the modal, hide the spinner
                M.updateTextFields();

                // Disabling the controls
                document.querySelector("#teacherId").disabled  = true;
                document.querySelector("#teacherName").disabled  = true;

                //Setting the Id to delete
                document.querySelector("#deleteTeacherId").value = jsonObject.teacherId;
                await delay(1000);
                document.getElementById("preloader").style.display = "none";

            } catch (e) {
                // On Error
                console.log("ERROR: fetchOneTeacher/querySelector - " + e);
            }

        });
    }

    async function fOpenNew() {

        // Set the action
        document.getElementById("action").value = "saveNewTeacher";

        //Clean and enabling the controls
        document.querySelector("#teacherId").disabled  = false;
        document.querySelector("#teacherName").disabled  = false;
        M.updateTextFields();

        // Trigger the Modal to open
        document.getElementById('modalLink').click();
        document.getElementById("preloader").style.display = "none";

    }

    //ACTION: updateOneTeacher or saveNewTeacher
    function saveOneTeacher(oFormEntries) {
        console.log(" -----> saveOneTeacher:" + "");
        console.log(" -----> action: " + document.getElementById('action').value);
        try {
            fetch("http://localhost:8080/sl_project2_learners_academy/teacher-controller", {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                method: "POST",
                body: JSON.stringify(oFormEntries)
            })
                .then(res => res.text()).then((jsonReturnString) => {
                console.log(" -------> teacherjsp fetch: " + jsonReturnString);
                try {

                    //Answer received from the servlet
                    const jsonObject = JSON.parse(jsonReturnString);
                    M.toast({
                        html:
                            jsonObject.code === 0 ? jsonObject.message : "<table><tr><td class=\"center-align\">Teacher NOT UPDATED</td></tr><tr><td>CODE: " + jsonObject.code +
                                " - " +
                                jsonObject.message + "</td></tr></table>"
                        , completeCallback: function(){location.reload()}
                    })
                } catch (e) {
                    // On Error
                    console.log("ERROR: fetchTeachers/querySelector" + e);
                }

            });
        } catch (e) {
            console.log(e);
        }
    }

    //ACTION: updateOneTeacher or saveNewTeacher
    function deleteOneTeacher(oFormEntries) {
        console.log(" -----> deleteOneTeacher:" + "");
        console.log(" -----> action: " + document.getElementById('action').value);
        try {
            fetch("http://localhost:8080/sl_project2_learners_academy/teacher-controller", {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                method: "POST",
                body: JSON.stringify(oFormEntries)
            })
                .then(res => res.text()).then((jsonReturnString) => {
                console.log(" -------> teacherjsp fetch: " + jsonReturnString);
                try {

                    //Answer received from the servlet
                    const jsonObject = JSON.parse(jsonReturnString);
                    M.toast({
                        html:
                            jsonObject.code === 0 ? jsonObject.message : "<table><tr><td class=\"center-align\">Teacher NOT UPDATED</td></tr><tr><td>CODE: " + jsonObject.code +
                                " - " +
                                jsonObject.message + "</td></tr></table>"
                        , completeCallback: function(){location.reload()}
                    })
                } catch (e) {
                    // On Error
                    console.log("ERROR: fetchTeachers/querySelector" + e);
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

    });
</script>

<%@include file="includes/footer.jsp" %>


</body>
</html>

