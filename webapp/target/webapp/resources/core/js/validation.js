function validateLoginForm() {
    var form = document.getElementById("login_form");
    var login = form["login"];
    var password = form["password"];

    //if (login == null || login == "") {
    //    alert("login must be filled out");
    //    return false;
    //}

    if (/^[a-z0-9]+$/i.test(login)) {
        alert('Login must be alphanumeric and not empty');
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


function validateDriverForm() {
    var form = document.getElementById("adder");
    var firstName = form["firstName"];
    var lastName = form["lastName"];
    var personalNumber = form["personalNumber"];


    if (! (/^[a-z0-9]+$/i.test(firstName))) {
        alert('Input must be alphanumeric and not empty');
        return false;
    }
    if (!(/^[a-z0-9]+$/i.test(lastName))) {
        alert('Input must be alphanumeric and not empty');
        return false;
    }
    if (!(/^[a-z0-9]+$/i.test(personalNumber))) {
        alert('Input must be alphanumeric and not empty');
        return false;
    }

    return true;
}