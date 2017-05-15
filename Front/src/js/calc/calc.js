wine.directive('calc', ['$http', '$mdDialog', function ($http, $mdDialog) {

    var calcFunction = function ($scope, $rootScope, $element) {

        $scope.numero1 = 10;
        $scope.numero2 = 20;

        $scope.sum = function () {

            $http({
                method: 'POST',
                url: 'http://localhost:8080/plus/',
                data: {
                    number1: $scope.numero1,
                    number2: $scope.numero2
                },
                transformResponse: [function (data) {
                    return data;
                }],
            }).then(
                function ok(result) {
                    $mdDialog.show(
                        $mdDialog.alert()
                            .textContent($scope.numero1 + " + " + $scope.numero2 + " =  " + result.data)
                            .ok('Close')
                    );
                },                
                function error(response) {
                    var msg = response.status == 403 ? 
                        "Authentication error":
                        "Status: " + response.status;

                    $mdDialog.show(
                        $mdDialog.alert()
                            .textContent(msg)
                            .ok('Close')
                    );
                    }
                );
        }
    };

    return {
        restrict: 'EA',
        scope: {
            wine: '='
        },
        controller: calcFunction,
        templateUrl: '/js/calc/calc.html',
    }
}]);