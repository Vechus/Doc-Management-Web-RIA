// this function is executed when document is ready and makes variables not end in public scope
$(document).ready(function(){
    // first of all get the page content from server
    $.ajax({
        type: "GET",
        url: 'GetFolders',
        success: function(response) {
            // this function is run when server responds with a success code
            response.forEach(function(item, value) {
                // the server responds with a List of folders, so iterate through it.
                // First, create a folder element, and add the corresponding icon, name and creation date
                let folderListElement = document.createElement("li");
                folderListElement.id = item.id;
                folderListElement.dataset.itemid = item.id;
                folderListElement.className = "folder";

                let iconImage = document.createElement("i");
                iconImage.setAttribute("aria-hidden", 'true');

                if(item.subfolders.length === 0)
                    iconImage.className = "fa fa-folder";
                else
                    iconImage.className = "fa fa-folder-open";

                let smallDateObject = document.createElement("small");
                smallDateObject.innerText = item.creationDate;

                folderListElement.appendChild(iconImage);
                folderListElement.appendChild(document.createTextNode(item.name));
                folderListElement.appendChild(smallDateObject);

                // now create a subfolder list container, in which we store its elements
                let subfolderListContainer = document.createElement("ul");
                if(item.subfolders.length > 0) {
                    item.subfolders.forEach(function (item1, value1) {
                        // create the subfolder list element, and add the corresponding icon, name and creation date
                        let subfolderListElement = document.createElement("li");
                        subfolderListElement.id = item1.id;
                        subfolderListElement.dataset.name = item1.name;
                        subfolderListElement.dataset.itemid = item1.id;
                        subfolderListElement.setAttribute("draggable", 'true');
                        subfolderListElement.className = "subfolder";

                        let subIconImage = document.createElement("i");
                        subIconImage.setAttribute("aria-hidden", 'true');

                        if(item1.documents.length === 0)
                            subIconImage.className = "fa fa-folder-o";
                        else
                            subIconImage.className = "fa fa-folder-open-o";

                        let smallSubDateObject = document.createElement("small");
                        smallSubDateObject.innerText = item1.creationDate;

                        let subfolderMoveImage = document.createElement("i");
                        subfolderMoveImage.className = "fa fa-arrows";
                        subfolderMoveImage.setAttribute("aria-hidden", 'true');
                        subfolderListElement.appendChild(subIconImage);
                        subfolderListElement.appendChild(document.createTextNode(item1.name));
                        subfolderListElement.appendChild(smallSubDateObject);
                        subfolderListElement.appendChild(subfolderMoveImage);
                        subfolderListContainer.appendChild(subfolderListElement);

                        // create a document list container
                        let documentListContainer = document.createElement("ul");
                        if(item1.documents.length > 0) {
                            item1.documents.forEach(function (item2, value2) {
                                // create an element, and add icon, name, summary and creation date
                                let documentListElement = document.createElement("li");
                                documentListElement.id = `document${item2.id}`;
                                documentListElement.dataset.name = item2.name;
                                documentListElement.setAttribute("draggable", 'true');
                                documentListElement.dataset.itemid = item2.id;
                                documentListElement.dataset.toggle = "tooltip";
                                documentListElement.title = item2.summary;
                                documentListElement.className = "document";

                                let docImageIcon = document.createElement("i");
                                docImageIcon.className = "fa fa-file-text";
                                docImageIcon.setAttribute("aria-hidden", 'true');

                                let smallDocDate = document.createElement("small");
                                smallDocDate.innerText = item2.creationDate;

                                let docMoveImage = document.createElement("i");
                                docMoveImage.className = "fa fa-arrows";
                                docMoveImage.setAttribute("aria-hidden", 'true');

                                documentListElement.appendChild(docImageIcon);
                                documentListElement.appendChild(document.createTextNode(item2.name));
                                documentListElement.appendChild(smallDocDate);
                                documentListElement.appendChild(docMoveImage);

                                documentListContainer.appendChild(documentListElement);
                            })
                        }
                        subfolderListElement.appendChild(documentListContainer);
                    })
                }
                folderListElement.appendChild(subfolderListContainer);
                // this syntax is equivalent to `document.getElementById()`
                $("#folderlist").append(folderListElement);
            })
        },
        error: function (response) {
            // this function is run if the server responds with an error code
            document.getElementById("error-message").textContent = response.responseText;
            window.location.href = 'index.html';
        }
    });

    // set welcome message
    document.getElementById("welcome-message").textContent = `Welcome, ${sessionStorage.username}`;

    // set click event to every document, to access to its content via modal
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
        // prevent default (to allow drop)
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
            // user has moved a document to a subfolder
            let dataForm = {
                entity_id: parseInt(dragged.dataset.itemid),
                to: parseInt(event.target.dataset.itemid)
            }
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
            // user has dragged a subfolder OR a document to the wastebin
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
            }
        }
    }, false);
});


function handleLogout() {
    sessionStorage.clear();
    window.location.href = '/Logout';
}