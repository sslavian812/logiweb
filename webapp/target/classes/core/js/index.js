function validateForm() {
    var form = document.getElementById("login_form");
    var login = form["login"];
    var password = form["password"];

    if (login == null || login == "") {
        alert("login must be filled out");
        return false;
    }

    // todo: this check is always false. WTF?
    if ("manager" === login) {
        if (password == null || password == "") {
            alert("password must be filled out for managers");
            return false;
        } else {
            return true;
        }
    }

    return true;
}