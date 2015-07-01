function validateForm() {
    var form = document.getElementById("login_form");
    var login = form["login"];
    var password = form["password"];

    if (login == null || login == "") {
        alert("login must be filled out");
        return false;
    }

    if (password == null || password =="") {
        // may be driver
        alert("trying to sing in as driver");
    }
    else
    {
        alert("truing to sign in as manager");
    }
    return true;
}