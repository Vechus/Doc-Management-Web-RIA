// this function is executed when document is ready and makes variables not end in public scope
$(document).ready(function() {
    $("#loginbutton").on("click", function(e) {
        e.preventDefault();
        let form = document.getElementById("loginForm");
        if (form.checkValidity()) {
            let username = document.getElementById("username").value;
            // MD5 digest the password for safer communication between server and client
            let hashedPassword = CryptoJS.MD5(document.getElementById("password").value).toString();
            let formData = {username: username, passwordHash: hashedPassword};
            $.ajax({
                type: "POST",
                url: 'CheckLogin',
                data: JSON.stringify(formData),
                success: function(response) {
                    sessionStorage.setItem('username', response);
                    window.location.href = "home.html";
                },
                error: function(req) {
                    document.getElementById("errormessage").textContent = req.responseText;
                },
                dataType: "json"
            });
        } else {
            form.reportValidity();
        }
    })
})

