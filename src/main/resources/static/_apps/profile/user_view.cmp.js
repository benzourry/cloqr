/**
 * Created by MohdRazif on 9/26/2017.
 */
angular.module('app')
    .component("userView", {
        templateUrl: "_apps/session/session_add.html",
        controller: UserView
    });

UserView.$inject = ["$http", "$routeParams", "userService"];

function UserView($http, $routeParams, userService) {
    // var vm = this;

    var vm = this;

    vm.user = {};
    vm.getAccount = getAccount;
    vm.getLogs = getLogs;
    vm.getEventList = getEventList;

    vm.$onInit = function(){
        userService.get().then(function(user){
            vm.user = user;
        });
    }

    function getAccount(){
        $http.get("api/account/" + vm.user.username + "").success(
            function (res) {
                $scope.data = res;
            }
        );

    }

    function getLogs(){
        $http.get("api/account/" + vm.user.username + "/logs").success(
            function (res) {
                $scope.logList = res.content;
            }
        );

    }

    function getEventList(){
        $http.get("api/event/list").success(
            function (res) {
                $scope.sessionList = res.content;
            }
        );

    }

}