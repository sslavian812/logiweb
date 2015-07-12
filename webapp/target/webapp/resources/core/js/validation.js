function validateLoginForm() {
    var form = document.getElementById("login_form");
    //var login = form.elements.login.value;
    var password = form.elements.password.value;

    //if(!("manager" === login || "driver" === login))
    //{
    //    alert('login should be either \"drider\" or \"manager\"');
    //    return false;
    //}

    if (!(/^[a-z0-9]+$/i.test(password))) {
        alert('\"password\" must be alphanumeric and not empty');
        return false;
    }

    return true;
}


function validateDriverForm() {
    var form = document.getElementById("adder");
    var firstName = form.elements.firstName.value;
    var lastName = form.elements.lastName.value;
    var personalNumber = form.elements.personalNumber.value;


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

function validateTruckForm() {
    var form = document.getElementById("adder");
    var crewSize = form.elements.crewSize.value;
    var registrationNumber = form.elements.registrationNumber.value;
    var capacity = form.elements.capacity.value;


    if (! (/^[0-9]+$/.test(crewSize))) {
        alert('crew size must be numeric and not empty');
        return false;
    }
    if (!(/^[a-z]{2}[0-9]{5}$/i.test(registrationNumber))) {
        alert('Registration number must consist of 2 letters and 5 digits');
        return false;
    }
    if (!(/^[0-9]+$/.test(capacity))) {
        alert('capacity must be numeric and not empty');
        return false;
    }

    return true;
}


function validateOrderForm() {
    var form = document.getElementById("adder");
    var orderIdentifier = form.elements.orderIdentifier.value;
    //var denomination = form.elements.denomination.value;
    var weight = form.elements.weight.value;


    if (! (/^[a-z0-9]+$/i.test(orderIdentifier))) {
        alert('Order identifier must be alphanumeric and not empty');
        return false;
    }
    //if (!(/^[a-z0-9]+$/i.test(denomination))) {
    //    alert('Input must be alphanumeric and not empty');
    //    return false;
    //}
    if (!(/^[0-9]+$/i.test(weight))) {
        alert('weight must be numeric and not empty');
        return false;
    }

    return true;
}