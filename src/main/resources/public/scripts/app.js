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
    $routeProvider.when('/holdings/list', {
        templateUrl: 'views/holdings/list.html',
        controller: 'HoldingsListCtrl'
    }).when('/holdings/create', {
        templateUrl: 'views/holdings/create.html',
        controller: 'HoldingsCreateCtrl'
    }).when('/gholdings/list', {
        templateUrl: 'views/groupholdings/list.html',
        controller: 'GroupHoldingsListCtrl'
    }).when('/gholdings/create', {
        templateUrl: 'views/groupholdings/create.html',
        controller: 'GroupHoldingsCreateCtrl'
    }).when('/gorder/list', {
        templateUrl: 'views/grouporder/list.html',
        controller: 'GroupOrderListCtrl'
    }).when('/gorder/create', {
        templateUrl: 'views/grouporder/create.html',
        controller: 'GroupOrderCreateCtrl'
    }).when('/orderz/list', {
        templateUrl: 'views/order/list.html',
        controller: 'OrderListCtrl'
    }).when('/orderz/create', {
        templateUrl: 'views/order/create.html',
        controller: 'OrderCreateCtrl'
    }).otherwise({
        redirectTo: '/'
    })
});

app.controller('HoldingsListCtrl', function ($scope, $http) {
    $http.get('/api/v1/holdings').success(function (data) {
        $scope.todos = data;
    }).error(function (data, status) {
        console.log('Error ' + data)
    });

    $scope.todoStatusChanged = function (holding) {
        console.log(holding);
        $http.put('/api/v1/holdings/' + holding.id, holding).success(function (data) {
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
        $http.post('/api/v1/holdings', $scope.holding).success(function (data) {
            $location.path('/holdings/list');
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
    }
});

app.controller('GroupHoldingsListCtrl', function ($scope, $http) {
    $http.get('/api/v1/gholdings').success(function (data) {
        $scope.todos = data;
    }).error(function (data, status) {
        console.log('Error ' + data)
    });

    $scope.todoStatusChanged = function (gholdings) {
        console.log(gholdings);
        $http.put('/api/v1/gholdings/' + gholdings.id, gholdings).success(function (data) {
            console.log('status changed');
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
    }
});

app.controller('GroupHoldingsCreateCtrl', function ($scope, $http, $location) {
    $scope.gholding = {
        done: false
    };

    $scope.createGroupHoldings = function () {
        console.log($scope.holding);
        $http.post('/api/v1/gholdings', $scope.gholding).success(function (data) {
            $location.path('/gholdings/list');
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
    }
});

app.controller('GroupOrderListCtrl', function ($scope, $http) {
    $http.get('/api/v1/gholdings').success(function (data) {
        $scope.todos = data;
    }).error(function (data, status) {
        console.log('Error ' + data)
    });

    $scope.todoStatusChanged = function (gholdings) {
        console.log(gholdings);
        $http.put('/api/v1/gorder/' + gholdings.id, gholdings).success(function (data) {
            console.log('status changed');
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
    }
});

app.controller('GroupOrderCreateCtrl', function ($scope, $http, $location) {
    $scope.gorder = {
        done: false
    };

    $scope.createGroupOrder = function () {
        console.log($scope.gorder);
        $http.post('/api/v1/gorder', $scope.gorder).success(function (data) {
            $location.path('/gorder/list');
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
    }
});

app.controller('OrderListCtrl', function ($scope, $http) {
    $http.get('/api/v1/orderz').success(function (data) {
        $scope.orders = data;
    }).error(function (data, status) {
        console.log('Error ' + data)
    });
    
    $scope.todoStatusChanged = function (orderz) {
        console.log(orderz);
        $http.put('/api/v1/orderz/' + orderz.id, orderz).success(function (data) {
            console.log('status changed');
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
    }
});

app.controller('OrderCreateCtrl', function ($scope, $http, $location) {
    $scope.order = {
        done: false
    };
    $scope.trans = {
        type: 'buy',
        stock: 'MEG',
        price: '4.53',
        cash: '77.00',
        quantity: '',
        total: '',
        buyingPower: '77.00'
    };
    
    $scope.$watch('trans.total', function(newVal, oldVal) {
        if($scope.trans.type == 'buy') {
            $scope.trans.quantity = newVal/$scope.trans.price/1.005;
        }
    });
    
    $scope.$watch('trans.quantity', function(newVal, oldVal) {
        if($scope.trans.type == 'sell') {
            $scope.trans.total = newVal*$scope.trans.price*1.005;
        }
    });
    
    $scope.changeTransType = function (type) {
        $scope.trans.quantity = '';
        $scope.trans.total = '';
    }
    
    $scope.createOrder = function () {
        console.log($scope.order);
        $http.post('/api/v1/orderz', $scope.order).success(function (data) {
            $location.path('/orderz/list');
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
    }
});