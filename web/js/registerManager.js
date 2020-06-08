$(document).ready(function() {
    $("#register-button").on("click", function (e) {
        e.preventDefault();
        let form = document.getElementById("registerForm");
        if(form.checkValidity()) {
            let username = document.getElementById("register-username").value;
            let email = document.getElementById("register-email").value;
            // MD5 digest the password for safer communication between server and client
            let passwordHash = CryptoJS.MD5(document.getElementById("register-psw").value).toString();
            let passwordRepeatHash = CryptoJS.MD5(document.getElementById("register-psw-repeat").value).toString();
            let formData = {username: username, email:email, passwordHash: passwordHash, passwordRepeatHash: passwordRepeatHash};
            $.ajax({
                type: "POST",
                url: 'Register',
                data: JSON.stringify(formData),
                success: function (response) {
                    document.getElementById("registermessage").textContent = response.responseText;
                },
                error: function (response) {
                    document.getElementById("registermessage").textContent = response.responseText;
                },
                dataType: "json"
            });
        } else {
            form.reportValidity();
        }

    })
})