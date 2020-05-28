$(document).ready(function(){
    $.ajax({
        type: "GET",
        url: 'GetFolders',
        success: function(response) {
            console.log(response.responseText);
            response.forEach(function(item, value) {
                console.log(item + "    " + value);
                let folderListObject = `<li id="folder${item.id}" data-itemid="${item.id}" class="folder"> ${(item.subfolders.length === 0 ? '<i class="fa fa-folder" aria-hidden="true"></i> ' : '<i class="fa fa-folder-open" aria-hidden="true"></i> ')}${item.name}<small> ${item.creationDate} </small>`;
                folderListObject += '<ul>';
                if(item.subfolders.length > 0) {
                    item.subfolders.forEach(function (item1, value1) {
                        folderListObject += `<li id="subfolder${item1.id}" data-name="${item1.name}" data-itemid="${item1.id}" draggable="true" class="subfolder"> ${(item1.documents.length === 0 ? '<i class="fa fa-folder-o" aria-hidden="true"></i>' : '<i class="fa fa-folder-open-o" aria-hidden="true"></i>')} ${item1.name} <small> ${item1.creationDate} </small> <i class="fa fa-arrows" aria-hidden="true"></i>`;
                        folderListObject += '<ul>';
                        if(item1.documents.length > 0) {

                            item1.documents.forEach(function (item2, value2) {
                                folderListObject += `<li id="document${item2.id}" data-name="${item2.name}" draggable="true" data-itemid="${item2.id}" data-toggle="tooltip" title="${item2.summary}" class="document"><i class="fa fa-file-text" aria-hidden="true"></i>${item2.name} <small>${item2.creationDate}</small> <i class="fa fa-arrows" aria-hidden="true"></i></li>`;
                            })

                        }
                        folderListObject += '</ul>';
                        folderListObject += '</li>';
                    })
                }
                folderListObject += '</ul>';
                folderListObject += '</li>';
                $("#folderlist").append(folderListObject)
            })
        },
        error: function (response) {
            console.log(response);
            document.getElementById("error-message").textContent = response.responseText;
            window.location.href = 'index.html';
        }
    });

    document.getElementById("welcome-message").textContent = `Welcome, ${sessionStorage.username}`;

    $('body').on('click', 'li.document', function(event){
        let target = $(event.currentTarget);

        $.ajax({
            type:"GET",
            url: `GetDocumentData?documentid=${target.attr("data-itemid")}`,
            success: function(response) {
                $('#modal-title').text(response.name);
                $('#document-subtitle').text(response.summary);
                $('#document-data').text(response.data);
                $('#data-modal').modal('show');
            },
            error: function (response) {
                $('#modal-title').text("Error:");
                $('#document-data').text(response.responseText);
                $('#data-modal').modal('show');
            }
        })
    });

    let dragged;
    const errorMsg = document.getElementById("error-message");
    document.addEventListener("drag", function (event) {
        // events on drag
    }, false);
    document.addEventListener("dragstart", function (event) {
        dragged = event.target;
        console.log(dragged);
        console.log(dragged.className);
        errorMsg.textContent = `You're moving ${dragged.className} ${dragged.dataset.name}`;
        event.target.style.opacity = .5;
    }, false);

    document.addEventListener("dragend", function( event ) {
        // reset the transparency
        errorMsg.textContent = "";
        event.target.style.opacity = "";
    }, false);

    /* events fired on the drop targets */
    document.addEventListener("dragover", function( event ) {
        // prevent default to allow drop
        event.preventDefault();
    }, false);

    document.addEventListener("dragenter", function( event ) {
        // highlight potential drop target when the draggable element enters it
        if ( event.target.className === "folder" && dragged.className === "subfolder" || event.target.className === "subfolder" && dragged.className === "document" ) {
            event.target.style.background = "aqua";
        } else if (event.target.id === "wastebin") {
            event.target.style.background = "red";
        }
    }, false);

    document.addEventListener("dragleave", function( event ) {
        // reset background of potential drop target when the draggable element leaves it
        if (event.target.className === "folder" && dragged.className === "subfolder" || event.target.id === "wastebin" || event.target.className === "subfolder" && dragged.className === "document") {
            event.target.style.background = "";
        }
    }, false);

    document.addEventListener("drop", function( event ) {
        // prevent default action (open as link for some elements)
        event.preventDefault();
        // move dragged elem to the selected drop target
        if (event.target.className === "folder" && dragged.className === "subfolder") {
            let dataForm = {
                entity_id: parseInt(dragged.dataset.itemid),
                to: parseInt(event.target.dataset.itemid)
            }
            console.log(JSON.stringify(dataForm));
            event.target.style.background = "";
            $.ajax({
                type: "POST",
                url: 'MoveSubfolder',
                data: JSON.stringify(dataForm),
                success: function(response) {
                    dragged.parentNode.removeChild( dragged );
                    event.target.getElementsByTagName("ul")[0].appendChild(dragged);
                    errorMsg.textContent = response.responseText;
                },
                error: function(response) {
                    errorMsg.textContent = response.responseText;
                }
            });
        } else if(event.target.className === "subfolder" && dragged.className === "document") {
            let dataForm = {
                entity_id: parseInt(dragged.dataset.itemid),
                to: parseInt(event.target.dataset.itemid)
            }
            console.log(JSON.stringify(dataForm));
            event.target.style.background = "";
            $.ajax({
                type: "POST",
                url: 'MoveDocument',
                data: JSON.stringify(dataForm),
                success: function(response) {
                    dragged.parentNode.removeChild( dragged );
                    event.target.getElementsByTagName("ul")[0].appendChild(dragged);
                    errorMsg.textContent = response.responseText;
                },
                error: function(response) {
                    errorMsg.textContent = response.responseText;
                }
            });
        } else if (event.target.id === "wastebin") {
            event.target.style.background = "";
            let confirmValue = confirm(`Are you sure to delete ${dragged.className} ${dragged.dataset.name}?`);
            if(confirmValue === true) {
                dataForm = {
                    entity_type: dragged.className,
                    entity_id: parseInt(dragged.dataset.itemid)
                }
                $.ajax({
                    type: "POST",
                    url: 'deleteEntity',
                    data: JSON.stringify(dataForm),
                    success: function (response) {
                        dragged.parentNode.removeChild(dragged);
                        errorMsg.textContent = response.responseText;
                    },
                    error: function (response) {
                        errorMsg.textContent = response.responseText;
                    }
                })
            } else {

            }
        }
    }, false);
});


function handleLogout() {
    sessionStorage.clear();
    window.location.href = '/Logout';
}