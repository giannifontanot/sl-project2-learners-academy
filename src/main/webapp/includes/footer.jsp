<script src="js/materialize.js"></script>
<script type="text/javascript">
    function createRequestObject()
    {
        var returnObj = false;

        if(window.XMLHttpRequest) {
            returnObj = new XMLHttpRequest();
        } else if(window.ActiveXObject) {
            try {
                returnObj = new ActiveXObject("Msxml2.XMLHTTP");

            } catch (e) {
                try {
                    returnObj = new ActiveXObject("Microsoft.XMLHTTP");
                }
                catch (e) {}
            }

        }
        return returnObj;
    }

    var http = createRequestObject();
    var target;

    // This is the function to call, give it the script file you want to run and
    // the div you want it to output to.
    function sendRequest(scriptFile, targetElement)
    {
        target = targetElement;
        try{
            http.open('get', scriptFile, true);
        }
        catch (e){
            document.getElementById(target).innerHTML = e;
            return;
        }
        http.onreadystatechange = handleResponse;
        http.send();
    }

    function handleResponse()
    {
        if(http.readyState == 4) {
            try{
                var strResponse = http.responseText;
                document.getElementById(target).innerHTML = strResponse;
            } catch (e){
                document.getElementById(target).innerHTML = e;
            }
        }
    }
</script>