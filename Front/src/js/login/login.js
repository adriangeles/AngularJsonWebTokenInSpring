wine.directive('login', ['$http', '$mdDialog', function ($http, $mdDialog) {

    var loginFunction = function($scope, $rootScope, $element) {

        $scope.username = "admin";
        $scope.password = "admin";

        $scope.authenticated = false;


        $scope.logout = function() {
            $http.defaults.headers.common['token'] = null;
            $scope.authenticated = false;
        }

        $scope.getToken = function() {

            $scope.token = null;

            $http({
                method: 'POST',
                url: 'http://localhost:8080/auth/',
                data: {
                    username: $scope.username,
                    password: $scope.password
                },
                transformResponse: [function(data) {
                    return data;
                }],
            }).then(
                function ok(response) {
                    $scope.authenticated = true;
                    $http.defaults.headers.common['token'] = response.data;
                    $mdDialog.show(
                        $mdDialog.alert()
                            .textContent("Authenticated")
                            .ok('Close')
                    );
                },
                function error(response) {
                    $mdDialog.show(
                        $mdDialog.alert()
                            .textContent("User/password incorrect")
                            .ok('Close')
                    );
            });
        }

    };

    return {
        restrict: 'EA',
        scope: {
            wine: '='
        },
        controller: loginFunction,
        templateUrl: '/js/login/loginTemplate.html',
    }
}]);