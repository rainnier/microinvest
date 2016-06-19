/**
 * Created by shekhargulati on 10/06/14.
 */

var app = angular.module('microinvestapp', [
    'ngCookies',
    'ngResource',
    'ngSanitize',
    'ngRoute'
]);

app.config(function ($routeProvider) {
    $routeProvider.when('/', {
        templateUrl: 'views/holdings/list.html',
        controller: 'HoldingsListCtrl'
    }).when('/create', {
        templateUrl: 'views/holdings/create.html',
        controller: 'HoldingsCreateCtrl'
    }).otherwise({
        redirectTo: '/'
    })
});

app.controller('HoldingsListCtrl', function ($scope, $http) {
    $http.get('/api/v1/holdings').success(function (data) {
        $scope.todos = data;
    }).error(function (data, status) {
        console.log('Error ' + data)
    })

    $scope.todoStatusChanged = function (todo) {
        console.log(todo);
        $http.put('/api/v1/holdings/' + todo.id, todo).success(function (data) {
            console.log('status changed');
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
    }
});

app.controller('HoldingsCreateCtrl', function ($scope, $http, $location) {
    $scope.holding = {
        done: false
    };

    $scope.createHolding = function () {
        console.log($scope.holding);
        $http.post('/api/v1/holdings', $scope.todo).success(function (data) {
            $location.path('/');
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
    }
});