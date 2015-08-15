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
        alert('first name must be alphanumeric and not empty');
        return false;
    }
    if (!(/^[a-z0-9]+$/i.test(lastName))) {
        alert('last name must be alphanumeric and not empty');
        return false;
    }
    if (!(/^[a-z0-9\-]+$/i.test(personalNumber))) {
        alert('personal number must consist of only numbers, letters and dashes("-")');
        return false;
    }

    return true;
}

function validateTruckForm() {
    var form = document.getElementById("adder");
    var crewSize = form.elements.crewSize.value;
    var registrationNumber = form.elements.registrationNumber.value;
    var capacity = form.elements.capacity.value;


    if ((!(/^[0-9]+$/.test(crewSize))) || parseInt(crewSize) < 1 || parseInt(crewSize) > 10) {
        alert('crew size must be numeric at leas 1 and at most 10');
        return false;
    }
    if (!(/^[a-z]{2}[0-9]{5}$/i.test(registrationNumber))) {
        alert('Registration number must consist of 2 letters and 5 digits');
        return false;
    }
    if ((!(/^[0-9]+$/.test(capacity))) || parseInt(capacity) < 0) {
        alert('capacity must be numeric and non-negative');
        return false;
    }

    return true;
}


function validateOrderForm() {
    var form = document.getElementById("adder");
    var orderIdentifier = form.elements.orderIdentifier.value;
    var cargoIdentifier = form.elements.cargoIdentifier.value;
    var denomination = form.elements.denomination.value;
    var weight = form.elements.weight.value;


    if (! (/^[a-z0-9\-]*$/i.test(orderIdentifier))) {
        alert('Order identifier must be alphanumeric with dashes("-")');
        return false;
    }
    if (!(/^[a-z0-9\-]*$/i.test(cargoIdentifier))) {
        alert('cargo identifier must be alphanumeric with dashes("-")');
        return false;
    }
    if (!(/^[a-z0-9 ]+$/i.test(denomination))) {
        alert('denomination must be alphanumeric and not empty');
        return false;
    }
    if ((!(/^[0-9]+$/i.test(weight))) || parseInt(weight) < 1) {
        alert('weight must be numeric and at least 1');
        return false;
    }

    return true;
}

function validateCargoForm() {
    var form = document.getElementById("adder");

    var cargoIdentifier = form.elements.cargoIdentifier.value;
    var denomination = form.elements.denomination.value;
    var weight = form.elements.weight.value;


    if (! (/^[a-z0-9\-]*$/i.test(cargoIdentifier))) {
        alert('Cargo identifier must be alphanumeric with dashes("-")');
        return false;
    }
    if (!(/^[a-z0-9 ]+$/i.test(denomination))) {
        alert('denomination must be alphanumeric with spaces and not empty');
        return false;
    }
    if (!(/^[0-9]+$/i.test(weight))) {
        alert('weight must be numeric and not empty');
        return false;
    }

    return true;
}