/*function makeCall(method, url, formData, callback) {
    const req = new XMLHttpRequest(); // visible by closure
    req.onreadystatechange = function() {
        callback(req)
    }; // closure
    req.open(method, url);
    if (formData == null) {
        req.send();
    } else {
        req.send(formData);
    }
}*/
/*
function makeCall(method, url, data, callback, errorFunction = function(resp) {console.log(resp)}) {
        alert("4");
        $.ajax({
            type: method,
            url: url,
            data: data,
            success: callback,
            error: errorFunction,
            dataType: "json"
            });
}*/
