(function () {
    'use strict';

    angular
        .module('app')
        .factory('AuthenticationService', Service);

    var config = require('../../../config/config'); // get our config file
    
    function Service($http, $localStorage, supersecret) {
        var service = {};

        service.Login = Login;
        service.Logout = Logout;

        return service;

        function Login(username, password, callback) {
            $http.post('http://52.10.50.20/api/authenticate', { name: username, password: password })
                .success(function (response) {
                    // login successful if there's a token in the response
                    if (response.token) {
                        
                        var isValid = KJUR.jws.JWS.verify(response.token, {utf8: config.secret}, ["HS256"]);
                        
                        if(isValid) {
                        
                            // store username and token in local storage to keep user logged in between page refreshes
                            $localStorage.currentUser = { username: username, token: response.token };
    
                            // add jwt token to auth header for all requests made by the $http service
                            $http.defaults.headers.common.Authorization = 'Bearer ' + response.token;
    
                            // execute callback with true to indicate successful login
                            callback(true);
                        } else {
                            callback(false);
                        }
                    } else {
                        // execute callback with false to indicate failed login
                        callback(false);
                    }
                });
                
        }

        function Logout() {
            // remove user from local storage and clear http auth header
            delete $localStorage.currentUser;
            $http.defaults.headers.common.Authorization = '';
        }
    }
})();