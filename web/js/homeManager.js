(function() {
    $.ajax({
        type: "GET",
        url: 'GetFolders',
        success: function(response) {
            console.log(response);
            response.forEach(function(item, value) {
                console.log(item + "    " + value);
                let folderListObject = '<li id="folder' + item.id + '" class="folder">' + (item.subfolders.length === 0 ? '<i class="fa fa-folder" aria-hidden="true"></i> ' : '<i class="fa fa-folder-open" aria-hidden="true"></i> ') + item.name + ' <small>' + item.creationDate + '</small>';
                if(item.subfolders.length > 0) {
                    folderListObject += '<ul>';
                    item.subfolders.forEach(function (item1, value1) {
                        folderListObject += '<li id="subfolder' + item1.id + '" class="subfolder">' + (item1.documents.length === 0 ? '<i class="fa fa-folder-o" aria-hidden="true"></i> ' : '<i class="fa fa-folder-open-o" aria-hidden="true"></i> ') + item1.name + ' <small>' + item1.creationDate + '</small> <i class="fa fa-arrows" aria-hidden="true"></i>';
                        if(item1.documents.length > 0) {
                            folderListObject += '<ul>'
                            item1.documents.forEach(function (item2, value2) {
                                folderListObject += '<li id="document' + item2.id + '" data-toggle="tooltip" title="' + item2.summary +'" class="document"><i class="fa fa-file-text" aria-hidden="true"></i> ' + item2.name + ' <small>' + item2.creationDate + '</small> <i class="fa fa-arrows" aria-hidden="true"></i></li>';
                            })
                            folderListObject += '</ul>'
                        }
                        folderListObject += '</li>'
                    })
                    folderListObject += '</ul>';
                }
                folderListObject += '</li>'
                $("#folderlist").append(folderListObject)
            })
        },
        error: function (response) {
            console.log(response);
            document.getElementById("error-message").textContent = response;
        }
    });
    document.getElementsByClassName("document").forEach(function(item, value) {

    })
})();