(function (document) {
    'use strict';

    var app = document.querySelector('#app');

    var response = [];

    // Close drawer after menu item is selected if drawerPanel is narrow
    app.handleResponse = function (request) {
        console.log(request.detail.response);
    };

    app.addEventListener('dom-change', function () {
        var solrRequest = document.querySelector("#solrRequest");
        var searchRequest = {};
        searchRequest.searchType = 'album';
        searchRequest.start = 1;
        searchRequest.numberOfRecords = 80;

        solrRequest.params = searchRequest;
        solrRequest.generateRequest();

        app.toggle();
    });


    app.handleResponse = function (data) {
        if (data !== null && data.detail !== null && data.detail.response !== null) {
            var boxList = document.querySelector("#boxList");
            if (boxList !== null && boxList !== undefined)
                boxList.albums = data.detail.response.albumJSON;
        }
    };

    app.handleError = function (error) {
        console.log('Error has happened ' + data);
    };

    app.toggle = function () {
        var sideNav = document.querySelector("#sideNav");
        var drawerMenu = document.querySelector("#toggleDrawerMenu");
        var mainMenu = document.querySelector("#toggleMainMenu");

        if (!sideNav.narrow) {
            mainMenu.toggleClass("hide-element");
            drawerMenu.toggleClass("show-element");
        } else {
            drawerMenu.toggleClass("show-element");
            mainMenu.toggleClass("hide-element");
        }

        sideNav.forceNarrow = !sideNav.forceNarrow;
    };

})(document);
