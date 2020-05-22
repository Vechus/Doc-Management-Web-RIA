var legacyForm = function legacySubmit(event) {
    event.preventDefault();
    let form = event.target.closest("form");
    if (form.checkValidity()) {
        let username = document.getElementById("username").value;
        let hashedPassword = CryptoJS.MD5(document.getElementById("password").value);
        let formData = new FormData();
        formData.append("username", username);
        formData.append("passwordHash", hashedPassword);
        makeCall("POST", 'CheckLogin', formData,
            function(req) {
                if (req.readyState === XMLHttpRequest.DONE) {
                    const message = req.responseText;
                    switch (req.status) {
                        case 200:
                            sessionStorage.setItem('username', message);
                            window.location.href = "home.html";
                            break;
                        default: // general error
                            document.getElementById("errormessage").textContent = message;
                            break;
                    }
                }
            }
        );
    } else {
        form.reportValidity();
    }
}
document.getElementById("loginForm").addEventListener("submit", legacyForm, true);
/*
var submitForm = async function submitFetch(event) {
    event.preventDefault();
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    if (username == null || password == null || username.length === 0 || password.length === 0) {
        alert("You must input username and password!");
        return;
    }
    var hashedPassword = CryptoJS.MD5(password);
    const data = {
        username: username,
        passwordHash: hashedPassword.toString(CryptoJS.enc.Hex)
    }
    console.log(JSON.stringify(data));
    var statusCode = 0;
    fetch('CheckLogin', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data),
    })
        .then(response => {
            statusCode = response.status;
            return response.text();
        })
        .then(res => {
            switch(statusCode) {
                case 200:
                    sessionStorage.setItem('username', res);
                    document.location.href = "home.html";
                    break;
                default:
                    document.getElementById("errormessage").textContent = res;
                    break;
            }

        })
        .catch((error) => {
            console.error('Error: ', error);
        });
};*/

