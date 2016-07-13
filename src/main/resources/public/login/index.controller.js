(function () {
    'use strict';

    angular
        .module('app')
        .controller('Login.IndexController', Controller);

    function Controller($location, AuthenticationService, $scope, $auth, toastr) {
        var vm = this;

        vm.login = login;

        initController();

        function initController() {
            // reset login status
            AuthenticationService.Logout();
        };

        $scope.authenticate = function(provider) {
          $auth.authenticate(provider)
            .then(function() {
              toastr.success('You have successfully signed in with ' + provider + '!');
              $location.path('/');
            })
            .catch(function(error) {
              if (error.error) {
                // Popup error - invalid redirect_uri, pressed cancel button, etc.
                toastr.error(error.error);
              } else if (error.data) {
                // HTTP response error from server
                toastr.error(error.data.message, error.status);
              } else {
                toastr.error(error);
              }
            });
        };
    
        function login() {
            vm.loading = true;
            AuthenticationService.Login(vm.username, vm.password, function (result) {
                if (result === true) {
                    $location.path('/');
                } else {
                    vm.error = 'Username or password is incorrect';
                    vm.loading = false;
                }
            });
        };
    }

})();